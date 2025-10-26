use macroquad::input::{is_key_down, is_key_pressed, mouse_wheel, KeyCode};
use crate::model::real_space::xy::XY;
use crate::view::{Focus, SystemView, View};

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
    pub fn do_key_press(self: &mut SystemView)
    {
        if is_key_pressed(KeyCode::LeftBracket) {
            self.zoom /= 2.0;
            println!("[ pressed. Zoom: {}", self.zoom);
        }
        if is_key_pressed(KeyCode::RightBracket) {
            self.zoom *= 2.0;
            println!("] pressed. Zoom: {}", self.zoom);
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
    }
}
