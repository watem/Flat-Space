use macroquad::prelude::{screen_height, screen_width};
use gravity::model::physics::Focus;
use gravity::model::xy::XY;

pub struct View
{
    pub(crate) speed: u32,
    pub(crate) is_paused: bool,
    pub(crate) single_step: bool,
    pub(crate) systems: Vec<SystemView>,
    pub(crate) curr_system: usize,
}

impl View
{
    pub fn new(speed: u32, is_paused: bool, n_systems: usize, starting_zoom: f64) -> Self
    {
        let mut systems = Vec::with_capacity(n_systems);
        let s0 = SystemView::new(starting_zoom);
        systems.push(s0);
        View{speed, is_paused, single_step: false, systems, curr_system: 0}
    }

    pub fn get_system(&self) -> &SystemView
    {
        &self.systems[self.curr_system]
    }

    pub fn speed (&self) -> u32
    {
        self.speed
    }

    pub fn is_paused(&self) -> bool
    {
        self.is_paused
    }
}

pub struct SystemView
{
    pub(crate) focus: Focus,
    pub(crate) zoom: f64,
    pub(crate) id: usize
}

static mut SYSTEM_ID: usize = 0;
impl SystemView
{
    pub fn new(zoom: f64) -> Self
    {
        let sys_view = unsafe {
            let sys_view = SystemView{focus: Focus::Position(XY::zero()), zoom, id: SYSTEM_ID};
            SYSTEM_ID += 1;
            sys_view
        };
        // let sys_view = SystemView{focus: Focus::Position(XY::zero()), zoom, id: 0};
        sys_view
    }

    pub fn zoom(&self) -> f64
    {
        self.zoom
    }

    pub fn centre(&self) -> XY
    {
        match &self.focus {
            Focus::Position(pos) => {pos.clone()}
            Focus::Body(i) => {
                todo!("get body position from id");
                XY::zero()
            }
        }
    }

    pub fn screen2coords(&self, screen: &XY) -> XY {
        let screen_size = XY::new(screen_width() as f64, screen_height() as f64);
        self._screen2coords(screen, screen_size)
    }

    pub fn coords2screen(&self, coords: &XY) -> XY {
        let screen_size = XY::new(screen_width() as f64, screen_height() as f64);
        self._coords2screen(coords, screen_size)
    }

    fn _screen2coords(&self, screen: &XY, screen_size: XY) -> XY {
        let mid_x = screen_size.x() / 2.0;
        let mid_y = screen_size.y() / 2.0;

        let x = (screen.x() - mid_x) / self.zoom + self.centre().x();
        let y = (mid_y - screen.y()) / self.zoom + self.centre().y();
        XY::new(x, y)
    }

    fn _coords2screen(&self, coords: &XY, screen_size: XY) -> XY {
        let mid_x = screen_size.x() / 2.0;
        let mid_y = screen_size.y() / 2.0;

        let x = mid_x  + (coords.x() - self.centre().x()) * self.zoom;
        let y = mid_y  - (coords.y() - self.centre().y()) * self.zoom;
        XY::new(x, y)
    }
}

mod test {
    use gravity::model::xy::XY;
    use crate::view::SystemView;
    use gravity::model::physics::Focus;

    #[test]
    pub fn basic_coord_reversibility() {
        let sys = SystemView::new(1.0);
        let coords = XY::new(3.0, 4.0);
        let screen_size = XY::new(2560.0, 1440.0);
        let screen = sys._coords2screen(&coords, screen_size);
        assert_eq!(coords, sys._screen2coords(&screen, screen_size).round());
    }

    #[test]
    pub fn zoomed_coord_reversibility() {
        let sys = SystemView::new(0.1);
        let coords = XY::new(3.0, 4.0);
        let screen_size = XY::new(2560.0, 1440.0);
        let screen = sys._coords2screen(&coords, screen_size);
        assert_eq!(coords, sys._screen2coords(&screen, screen_size).round());

        let sys = SystemView::new(10.);
        let coords = XY::new(3.0, 4.0);
        let screen = sys._coords2screen(&coords, screen_size);
        assert_eq!(coords, sys._screen2coords(&screen, screen_size).round());
    }

    #[test]
    pub fn offset_coord_reversibility() {
        let mut sys = SystemView::new(0.1);
        sys.focus = Focus::Position(XY::new(-2., -3.,));
        let coords = XY::new(3.0, 4.0);
        let screen_size = XY::new(2560.0, 1440.0);
        let screen = sys._coords2screen(&coords, screen_size);
        assert_eq!(coords, sys._screen2coords(&screen, screen_size).round());

        let mut sys = SystemView::new(10.);
        sys.focus = Focus::Position(XY::new(-2., -3.,));
        let coords = XY::new(3.0, 4.0);
        let screen = sys._coords2screen(&coords, screen_size);
        assert_eq!(coords, sys._screen2coords(&screen, screen_size).round());
    }

    #[test]
    pub fn zoom_centre() {
        let mut sys = SystemView::new(1.);
        let coords_centre = XY::new(-2., -3.,);
        sys.focus = Focus::Position(coords_centre);
        let screen_size = XY::new(2560.0, 1440.0);
        let screen = sys._coords2screen(&coords_centre, screen_size);
        assert_eq!(screen, screen_size/2.0);

        sys.zoom = 10.0;
        let screen2 = sys._coords2screen(&coords_centre, screen_size);
        assert_eq!(screen, screen2);

        sys.zoom = 0.1;
        let screen2 = sys._coords2screen(&coords_centre, screen_size);
        assert_eq!(screen, screen2);
    }

    #[test]
    pub fn centre_location() {
        let mut sys = SystemView::new(1.);
        let coords_centre = XY::new(-2., -3.,);
        sys.focus = Focus::Position(coords_centre);
        let screen_size = XY::new(2560.0, 1440.0);
        let screen = sys._coords2screen(&coords_centre, screen_size);
        assert_eq!(screen, screen_size/2.0);

        let coords = XY::new(0.0, 0.0);
        let screen2 = sys._coords2screen(&coords, screen_size);
        let d = XY::new(2.0, -3.0);
        assert_eq!(screen + d, screen2);
    }
}