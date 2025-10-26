use macroquad::miniquad::CursorIcon::Default;
use crate::model::real_space::physics::{Body, System};
use game_options::Options;

pub mod game_options;

pub struct Game
{
    systems: Vec<System>,
    options: Options
}

pub struct Demo
{
    pub game: Game,
}

impl Demo
{
    pub fn new() -> Demo {
        let systems = Vec::with_capacity(1);
        Demo{game: Game{systems, options: Options::empty()}}
    }

    pub fn bodies(&self) -> &Vec<Body>
    {
        &self.game.systems[0].bodies
    }

    pub fn bodies_m(&mut self) -> &mut Vec<Body>
    {
        &mut self.game.systems[0].bodies
    }

    pub fn game(&mut self) -> &mut Game
    {
        &mut self.game
    }
}

impl Game {
    pub fn system(&self, id: usize) -> &System {
        self.systems.get(id).unwrap()
    }

    pub fn body(&self, system: usize, id: usize) -> &Body {
        self.systems.get(system).unwrap().bodies.get(id).unwrap()
    }

    pub fn mut_body(&mut self, system: usize, id: usize) -> &mut Body {
        self.systems.get_mut(system).unwrap().bodies.get_mut(id).unwrap()
    }

    pub fn add_system(&mut self, system: System) -> usize {
        let id = self.systems.len();
        self.systems.push(system);
        id
    }

    pub fn save() {
        todo!("Save not implemented yet")
    }

    pub fn close() {
        todo!("Close not implemented yet")
    }
}