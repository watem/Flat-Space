extern crate gravity;

use rand::distr::Uniform;
use rand::{rng, Rng};
use flat_space_examples::grav_examples::{demo_loop, linear_distribution};
use flat_space_examples::Demo;
use gravity::model::physics::{AccelerationObject, Body, CentredBox, System};
use gravity::model::physics::SpaceDimension::Standard;
use gravity::model::physics::SpeedType::Rand;
use gravity::model::xy::XY;
use gravity::physics::gravity::physics_body;
use gravity::samples::sample_grav_loop;

#[macroquad::main("Gravity Demo")]
async fn main() {
    let log = true;
    let r_low = 500.0;
    let r_high = 5000.0;
    let scale = 500.0 / r_high;
    let bounds = CentredBox {centre: XY::zero(), r: r_high * 200.0};
    let centre_mass: f64 = 1e8;
    // let dist_pos = Uniform::new(r_low, r_high).unwrap();
    let dist_pos = linear_distribution(&r_low, &r_high);
    let dis_vel = Uniform::new(centre_mass.sqrt()*0.98, centre_mass.sqrt()*1.02).unwrap();
    let masses = Uniform::new(1.0, 1000.0).unwrap();
    let n_bodies: usize = 100;
    let mut bodies: Vec<Body> = Vec::with_capacity(n_bodies + 1);
    bodies.push(Body::new(centre_mass, Standard(AccelerationObject::zero(0))));
    for _ in 0..n_bodies {
        bodies.push(physics_body(rng().sample(&masses), &dist_pos, &dis_vel, Rand, 0));
    }
    let time_step = 20.0;
    let colour_n = 1;
    let mut demo = Demo::new(1, true, 1, scale);
    demo.add_system(System::new("Demo".parse().unwrap(), bodies));
    demo_loop(&mut demo, bounds, colour_n, log, time_step, sample_grav_loop).await;
}

