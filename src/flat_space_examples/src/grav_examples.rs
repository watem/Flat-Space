use gravity::model::physics::{Positional, System};
use view::canvas::canvas;
use view::gravity::model::physics::CentredBox;
use view::macroquad::prelude::{draw_fps, GOLD, RED, WHITE};
use view::view::view::View;
use crate::Demo;
use gravity::rand::distr::{Distribution, Uniform};

/*
 * Creates a linear distribution where p(start) = 0 and linearly increases to its max value at x=end
 * This distributes points evenly in a circle when placed radially.
 */
pub fn linear_distribution(start: &f64, end: &f64) -> impl Distribution<f64> {
    let u = Uniform::new(0.0, 1.0);
    u.unwrap().map(|num| f64::sqrt(num) * (end.clone() - start.clone()) + start.clone())
}


pub async fn demo_loop(demo: &mut Demo, bounds: CentredBox, colour_n: u32, log: bool, time_step: f64,
                       grav_loop: fn(u32, bool, usize, CentredBox, &mut System, f64, u32) -> (u32, usize, CentredBox)) {
    let mut i:u32 = 0;

    let mut node_count = demo.game().system(0).bodies.len() * 4;
    let mut bounds = bounds;
    loop {
        let speed = demo.view.speed();
        for s in 0..demo.n_systems {
            let draw = s == demo.focus_system;
            let system = demo.game.system_mut(s);
            let view = &mut demo.view;
            system_loop(system, draw, colour_n, view, i, log, time_step, speed, node_count, bounds.clone(), grav_loop);
        }
        draw_fps();
        demo.demo().await;
        canvas::reset();

        // view.draw_point_demo(&tree.com().pos(), 1.0, Some(GOLD));
    }
}

fn system_loop(system: &mut System, draw_system: bool, colour_n: u32, view: &mut View, mut i: u32,
               log: bool, time_step: f64, speed: u32, mut node_count: usize, mut bounds: CentredBox,
               grav_loop: fn(u32, bool, usize, CentredBox, &mut System, f64, u32) -> (u32, usize, CentredBox))
                    -> (u32, usize, CentredBox) {
    if (draw_system) {
        let mut c = 0;
        for body in &system.bodies {
            if c < colour_n {
                view.draw_point_demo(&body.pos(system), 8.0, Some(RED));
                c += 1;
            }
            view.draw_point_demo(&body.pos(system), 3.0, Some(WHITE));
        }
        draw_fps();
    }
    if view.is_paused()
    {
        return (i, node_count, bounds);
    }

     grav_loop(i, log, node_count, bounds, system, time_step, speed)
}

