use macroquad::input::{is_key_down, is_key_pressed, is_mouse_button_pressed, mouse_position, mouse_wheel, KeyCode, MouseButton};
use gravity::model::physics::Focus;
use gravity::model::xy::XY;
use crate::view::view::{SystemView, View};

impl View
{
    pub fn do_key_press(self: &mut View)
    {
        if self.single_step
        {
            self.is_paused = true;
            self.single_step = false;
        }


        if is_key_pressed(KeyCode::Space)
        {
            println!("Space pressed");
            self.is_paused = !self.is_paused;
        }

        if is_key_pressed(KeyCode::Equal) && is_key_down(KeyCode::LeftShift)
        {
            if self.speed == 0
            {
                self.is_paused = false;
                self.speed = 1;
            }
            else if self.speed < 2048 {

                self.speed *= 2;
            }
            println!("+ pressed. Speed: {}", self.speed);
        }
        if is_key_pressed(KeyCode::Minus) && is_key_down(KeyCode::LeftShift)
        {
            if self.speed == 1
            {
                self.is_paused = true;
                self.speed = 0;
            }
            else {
                self.speed /= 2;
            }
            println!("- pressed. Speed: {}", self.speed);
        }

        if is_key_pressed(KeyCode::Period) && self.is_paused
        {
            self.is_paused = false;
            self.single_step = true;
        }

        if mouse_wheel().1 > 0.0 && !is_key_down(KeyCode::LeftShift)
        {
            if self.speed == 0
            {
                self.is_paused = false;
                self.speed = 1;
            }
            else if self.speed < 2048 {

                self.speed += 1;
            }
            println!("Scroll in {}. Speed: {}", mouse_wheel().1, self.speed);
        }
        if mouse_wheel().1 < 0.0 && !is_key_down(KeyCode::LeftShift)
        {
            if self.speed <= 1
            {
                self.is_paused = true;
                self.speed = 0;
            }
            else {
                self.speed -= 1;
            }
            println!("Scroll out {}. Speed: {}", mouse_wheel().1, self.speed);
        }

        let system = &mut self.systems[self.curr_system];
        system.do_key_press();

    }
}

impl SystemView
{
    pub fn do_key_press(&mut self)
    {
        // TODO: zooming in and out does not preserve the centre
        // TODO: moving with arrows does not move by the same relative amount at different zoom levels
        if is_key_pressed(KeyCode::LeftBracket) {
            self.zoom /= 2.0;
            println!("[ pressed. Zoom: {}", self.zoom);
        }
        if is_key_pressed(KeyCode::RightBracket) {
            self.zoom *= 2.0;
            println!("] pressed. Zoom: {}", self.zoom);
        }
        if is_key_pressed(KeyCode::Home) {
            self.focus = Focus::Position(XY::zero());
        }

        if mouse_wheel().1 > 0.0 && is_key_down(KeyCode::LeftShift)
        {
            self.zoom *= 1.5;
            println!("Shift-Scroll in {}. Zoom: {}", mouse_wheel().1, self.zoom);
        }
        if mouse_wheel().1 < 0.0 && is_key_down(KeyCode::LeftShift)
        {
            self.zoom /= 1.5;
            println!("Shift-Scroll out {}. Zoom: {}", mouse_wheel().1, self.zoom);
        }

        let movement_zoom = 4.0 / self.zoom;
        if is_key_pressed(KeyCode::Left) {
            let change = XY::new(-movement_zoom, 0.0);
            self.focus = Focus::Position(self.centre().plus(&change));
            println!("<- pressed: centre.x={}, d={}", self.centre().x(), 4.0/self.zoom);
        }
        if is_key_pressed(KeyCode::Right) {
            let change = XY::new(movement_zoom, 0.0);
            self.focus = Focus::Position(self.centre().plus(&change));
            println!("-> pressed: centre.x={}, d={}", self.centre().x(), 4.0/self.zoom);
        }

        if is_key_pressed(KeyCode::Up) {
            let change = XY::new(0.0, movement_zoom);
            self.focus = Focus::Position(self.centre().plus(&change));
            println!("^ pressed: centre.y={}, d={}", self.centre().y(), 4.0/self.zoom);
        }
        if is_key_pressed(KeyCode::Down) {
            let change = XY::new(0.0, -movement_zoom);
            self.focus = Focus::Position(self.centre().plus(&change));
            println!("v pressed: centre.y={}, d={}", self.centre().y(), 4.0/self.zoom);
        }

        self.do_mouse_press();
    }

    fn do_mouse_press(&mut self) {
        if is_mouse_button_pressed(MouseButton::Left) {
            let (x, y) = mouse_position();
            // TODO: convert to system space
            self.focus = Focus::Position(XY::new(x.into(), y.into()));
            // TODO: find if object is nearby enough
            // self.focus = Focus::Body(body_id);
        }

        if is_mouse_button_pressed(MouseButton::Right) {
            let (x, y) = mouse_position();
            // TODO: convert to system space
            // TODO: find if object is nearby enough
            // TODO: add/remove object from tracking
            // TODO: while tracking, store positions in Vec
            // TODO: while tracking, render past positions
        }
    }
}
