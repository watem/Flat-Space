use rand::distr::{Distribution, Uniform};
use rand::{rng, Rng};
use crate::model::physics::{AccelerationObject, Body, CentredBox, CoM, System};
use crate::model::physics::SpaceDimension::Standard;
use crate::model::xy::XY;
use crate::physics::barnes_hut::BarnesHutQuadTree;
use crate::physics::gravity::apply_grav;

pub fn sample_grav_loop(mut i: u32, log: bool, mut node_count: usize, mut bounds: CentredBox, system: &mut System, time_step: f64, speed: u32) -> (u32, usize, CentredBox)
{
    let tree: BarnesHutQuadTree = loop {
        i += 1;
        let mut tree = BarnesHutQuadTree::new(&bounds, node_count  + 50);
        tree.add_all(system);
        node_count = tree.size();
        bounds = tree.bounds(bounds);
        let n_bodies = system.bodies.len();
        tree.apply_all(system, n_bodies);

        apply_grav(&mut system.bodies, time_step);
        // let d_com =
        // correct_com(demo.bodies_m(), &d_com);  // TODO: still needed?
        // println!("tree created: {}::{}::COM: {}::d_com: {}", i, node_count, tree.com().str(Some(6)), d_com.pos().str(Some(6)));

        if i%speed == 0
        {
            break tree;
        }
    };
    if log
    {
        println!("tree created: {}::{}::COM: {}", i, node_count, tree.com().pos().str(Some(6)));
    }
    (i, node_count, bounds)
}

#[allow(unused_variables)]
pub async fn grav_demo_3(log: bool) { // binary orbital systems

}