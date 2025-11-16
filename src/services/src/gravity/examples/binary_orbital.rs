use rand::distr::Uniform;
use rand::{rng, Rng};
use flat_space_examples::Demo;
use flat_space_examples::grav_examples::{demo_loop, linear_distribution};
use gravity::model::physics::{AccelerationObject, Body, CentredBox, System};
use gravity::model::physics::SpaceDimension::Standard;
use gravity::model::physics::SpeedType::Rand;
use gravity::model::xy::XY;
use gravity::physics::gravity::physics_body;
use gravity::samples::sample_grav_loop;

// TODO binary
#[macroquad::main("Binary System Gravity Demo")]
async fn main() {

}