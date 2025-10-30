use rand::distr::{Distribution};
use rand::{rng, Rng};
use crate::model::game::Game;
use crate::model::real_space::physics::{Accel, AccelerationObject, Body, CoM, Positional};
use crate::model::real_space::physics::Body::{MassBody, MasslessBody};
use crate::model::real_space::physics::SpaceDimension::Standard;
use crate::model::real_space::xy::XY;

mod barnes_hut;
pub mod samples;

// const G: f64 = 6.67430e-11; //m^3*kg^-1*s^-2
const G: f64 = 6.67430e-8; //m^3*(1000kg)^-1*s^-2
const SOFT: f64 = 10.0;
const TIME_STEP: f64 = 20.0;
static mut TMP_BOUNDS: f64 = 0.0;


/* Acceleration due to the force of gravity */
fn f_g(mass: f64, r: f64) -> f64 {
    G * mass / r.powi(2)
}
fn grav(game: &mut Game, system: usize, id: usize, body_pos: XY, r: f64, com: &CoM) {
    let a = com.pos().subtract(&body_pos).unit().multiply(f_g(com.mass(),r + SOFT));
    game.mut_body(system, id).accel(&a);
    // body.accel(&a);
}

fn apply_grav(bodies: &mut Vec<Body>, dt:f64) -> CoM {
    let mut com = CoM::zero();
    for body in bodies
    {
        // com.add_self(body.time_step(dt));  // TODO: is CoM update still needed?
        body.time_step(dt);
        body.reset_accel();
    }
    com
}

fn correct_com(bodies: &mut Vec<Body>, com: &CoM) {
    for body in bodies
    {
        todo!("Is this still needed?")
        // body.pos().add_self(&com.pos().multiply(-1.0))
    }
}

#[allow(dead_code)]
enum SpeedType
{
    None,
    Const(f64),
    Rand,
}

fn rotational_velocity(mass: f64, r: f64) -> f64
{
    let a = f_g(mass, r);
    (a / r).sqrt()
}

fn physics_body(mass: f64, dist_pos: &impl Distribution<f64>, dis_vel: &impl Distribution<f64>, speed_type: SpeedType, system: usize) -> Body {
    let pos = XY::random_radial(&dist_pos);
    let base_v: f64 = match speed_type {
        SpeedType::Const(mass) => mass.sqrt() * (G / pos.radius()).sqrt(),
        SpeedType::None => 0.0,
        SpeedType::Rand => rng().sample(dis_vel) * (G / pos.radius()).sqrt(),
    };

    let vel = pos.rotate_90().unit().multiply(base_v);
    let obj = Standard(AccelerationObject::new(pos, vel, XY::zero(), system));
    if mass > 0.0 {
        let b = MassBody(mass, obj);
        return b;
    }

    let b = MasslessBody(obj);
    b
}
