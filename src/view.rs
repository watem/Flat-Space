use macroquad::prelude::next_frame;
use crate::model::real_space::physics::XY;

pub mod canvas;
mod kb;

enum Focus
{
    Position(XY),
    Body(usize)
}

pub struct SystemView
{
    focus: Focus,
    zoom: f64,
    id: usize
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
}

pub struct View
{
    speed: u32,
    is_paused: bool,
    single_step: bool,
    systems: Vec<SystemView>,
    curr_system: usize,
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

    pub async fn demo(&mut self)
    {
        self.do_key_press();
        self.write_speed();
        if self.is_paused
        {
            self.write_paused();
        }
        next_frame().await;
    }
}

