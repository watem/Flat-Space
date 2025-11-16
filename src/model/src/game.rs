use std::process::id;
use game_options::Options;
use gravity::model::physics::{Body, System};

pub mod game_options;

pub struct Game
{
    systems: Vec<System>,
    options: Options
}

impl Game {
    pub fn new(n_systems: usize) -> Game {
        let mut systems: Vec<System> = Vec::with_capacity(n_systems);

        Game{systems, options: Options::empty()}
    }

    pub fn system(&self, id: usize) -> &System {
        self.systems.get(id).unwrap()
    }

    pub fn system_mut(&mut self, id: usize) -> &mut System {
        self.systems.get_mut(id).unwrap()
    }

    pub fn systems_mut(&mut self) -> &mut Vec<System> {
        &mut self.systems
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