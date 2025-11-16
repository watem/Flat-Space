use macroquad::window::next_frame;
use gravity::model::physics::System;
use model::game::Game;
use view::view::view::View;

pub mod grav_examples;
pub mod demo_view;
pub struct Demo {
    pub game: Game,
    pub view: View,
    focus_system: usize,
    n_systems: usize,
}

impl Demo {
    pub fn new(speed: u32, is_paused: bool, n_systems: usize, starting_zoom: f64) -> Demo {
        let game: Game = Game::new(n_systems);
        let view = View::new(speed, is_paused, n_systems, starting_zoom);
        Demo {game, view, n_systems, focus_system: 0}
    }

    pub async fn demo(&mut self)
    {
        self.do_key_press();
        self.write_speed();
        if self.view.is_paused()
        {
            self.write_paused();
        }
        next_frame().await;
    }

    pub fn game(&mut self) -> &mut Game
    {
        &mut self.game
    }

    pub fn view(&mut self) -> &mut View
    {
        &mut self.view
    }

    pub fn systems_m(&mut self) -> &mut Vec<System> {
        self.game.systems_mut()
    }

    pub fn add_system(&mut self, system: System) {
        self.game.add_system(system);
    }
}
