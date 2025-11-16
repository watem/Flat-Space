use macroquad::color::{ORANGE, WHITE};
use macroquad::prelude::{draw_text, screen_height, screen_width};
use model::game::Game;
use crate::Demo;

impl Demo {
    pub fn write_speed(&self)
    {
        self.view.write_speed();
    }

    pub fn write_paused(&self)
    {
        self.view.write_paused();
    }

    pub fn do_key_press(&mut self) {
        self.view.do_key_press();
    }
}