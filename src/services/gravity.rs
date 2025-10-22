use rand::distr::{Distribution};
use rand::{rng, Rng};
use crate::model::real_space::physics::{Body, CoM, XY};

mod barnes_hut;
pub mod samples;

// const G: f64 = 6.67430e-11; //m^3*kg^-1*s^-2
const G: f64 = 6.67430e-8; //m^3*(1000kg)^-1*s^-2
const SOFT: f64 = 10.0;
const TIME_STEP: f64 = 20.0;
static mut TMP_BOUNDS: f64 = 0.0;
fn grav(body: &mut Body, com: &XY, mass: f64) {
    let r = com.distance(&body.pos);
    let a = com.subtract(&body.pos).unit().multiply(G * mass / (r + SOFT).powi(2));
    body.accel.add_self(&a);
}

fn apply_grav(bodies: &mut Vec<Body>, dt:f64) -> CoM {
    let mut com = CoM::zero();
    for body in bodies
    {
        com.add_self(body.step(dt));
        body.accel.set(&XY::zero());
        unsafe {
            let max = TMP_BOUNDS * 0.9;
            if body.pos.get_x().abs() >= max {
                let sgn = body.pos.get_x().signum();
                println!("Updating x pos of object {}", body.pos.str(Some(3)));
                body.pos.set(&XY::new(sgn * max, body.pos.get_y()));
                body.vel.set(&XY::new(-sgn/100., body.vel.get_y()));
                println!("Updated x pos of object {}", body.pos.str(Some(3)));
            }
            if body.pos.get_y().abs() >= max {
                let sgn = body.pos.get_y().signum();
                println!("Updating y pos of object {}", body.pos.str(Some(3)));
                body.pos.set(&XY::new(body.pos.get_x(), sgn * max));
                body.vel.set(&XY::new(body.vel.get_x(), -sgn/100.));
                println!("Updated y pos of object {}", body.pos.str(Some(3)));
            }
        }
    }
    com
}

fn correct_com(bodies: &mut Vec<Body>, com: &CoM) {
    for body in bodies
    {
        body.pos.add_self(&com.pos().multiply(-1.0))
    }
}

#[allow(dead_code)]
enum SpeedType
{
    None,
    Const(f64),
    Rand,
}

fn physics_body(mass: f64, dist_pos: &impl Distribution<f64>, dis_vel: &impl Distribution<f64>, speed_type: SpeedType) -> Body {
    let pos = XY::random_radial(&dist_pos);
    let base_v: f64 = match speed_type {
        SpeedType::Const(mass) => mass.sqrt() * (G / pos.rad()).sqrt(),
        SpeedType::None => 0.0,
        SpeedType::Rand => rng().sample(dis_vel) * (G / pos.rad()).sqrt(),
    };

    let vel = pos.rotate_90().unit().multiply(base_v);
    if mass > 0.0 {
        let b = Body{pos, vel, accel: XY::zero(), mass};
        return b;
    }

    let b = Body{pos, vel, accel: XY::zero(), mass: 0.0};
    b
}
