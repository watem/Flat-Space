use macroquad::color::{GOLD, RED, WHITE};
use macroquad::prelude::draw_fps;
use rand::distr::{Distribution, Uniform};
use rand::{rng, Rng};
use crate::model::real_space::physics::{Body, CentredBox, XY};
use crate::services::gravity::barnes_hut::BarnesHutQuadTree;
use crate::services::gravity::{apply_grav, correct_com, physics_body, TIME_STEP, TMP_BOUNDS};
use crate::services::gravity::SpeedType::{Const, Rand};
use crate::view::canvas::canvas;
use crate::view::View;
/*
 * Creates a linear distribution where p(start) = 0 and linearly increases to its max value at x=end
 * This distributes points evenly in a circle when placed radially.
 */
fn linear_distribution(start: &f64, end: &f64) -> impl Distribution<f64> {
    let u = Uniform::new(0.0, 1.0);
    u.unwrap().map(|num| f64::sqrt(num) * (end.clone() - start.clone()) + start.clone())
}

pub async fn demo_loop(mut view: View, bounds: CentredBox, mut bodies: Vec<Body>, colour_n: u32, log: bool) {
    let mut i:u32 = 0;
    let mut node_count = bodies.len() * 4;
    unsafe { TMP_BOUNDS = bounds.r; }
    loop {
        let mut c = 0;
        for body in &mut bodies {
            if c < colour_n {
                view.draw_point_demo(&body.pos, 8.0, Some(RED));
                c += 1;
            }
            view.draw_point_demo(&body.pos, 3.0, Some(WHITE));
        }
        draw_fps();
        view.demo().await;
        canvas::reset();

        if view.is_paused()
        {
            continue;
        }
        let tree: BarnesHutQuadTree = loop {
            i += 1;
            let mut tree = BarnesHutQuadTree::new(&bounds, node_count  + 50);
            tree.add_all(&bodies);
            node_count = tree.size();
            tree.apply_all(&mut bodies);

            let d_com = apply_grav(&mut bodies, TIME_STEP);
            correct_com(&mut bodies, &d_com);
            // println!("tree created: {}::{}::COM: {}::d_com: {}", i, node_count, tree.com().str(Some(6)), d_com.pos().str(Some(6)));
            if i%view.speed() == 0
            {
                break tree;
            }
        };

        if log
        {
            println!("tree created: {}::{}::COM: {}", i, node_count, tree.com().str(Some(6)));
        }

        view.draw_point_demo(&tree.com(), 1.0, Some(GOLD));
    }
}

pub async fn bench_loop(bounds: CentredBox, mut bodies: Vec<Body>, scale: f64, speed_factor: u32, colour_n: u32, loops: u32) {
    let mut i:u32 = 0;
    let mut node_count = bodies.len() * 4;
    unsafe { TMP_BOUNDS = bounds.r; }
    loop {
        let tree: BarnesHutQuadTree = loop {
            i += 1;
            let mut tree = BarnesHutQuadTree::new(&bounds, node_count  + 50);
            tree.add_all(&bodies);
            node_count = tree.size();
            tree.apply_all(&mut bodies);

            let d_com = apply_grav(&mut bodies, TIME_STEP);
            correct_com(&mut bodies, &d_com);
            println!("tree created: {}::{}::COM: {}::d_com: {}", i, node_count, tree.com().str(Some(6)), d_com.pos().str(Some(6)));
            if i%speed_factor == 0
            {
                break tree;
            }
        };

        canvas::reset();
        draw_fps();

        let mut c = 0;
        for body in &mut bodies {
            if c < colour_n {
                canvas::draw_point(&body.pos, 8.0, RED, scale);
                c += 1;
            }
            canvas::draw_point(&body.pos, 3.0, WHITE, scale);
        }
        canvas::draw_point(&tree.com(), 1.0, RED, scale);
        canvas::render().await;
        if i>loops {
            break;
        }
    }
}

pub async fn grav_demo() {
    let r_low = 800.0;
    let r_high = 1000.0;
    let scale = 100.0 / r_high;
    let bounds = CentredBox {centre: XY::zero(), r: r_high * 2.0};
    let dist_pos = Uniform::new(r_low, r_high).unwrap();
    let dis_vel = Uniform::new(-0.1, 1.0).unwrap();
    let masses = Uniform::new(10.0, 1000.0).unwrap();
    let n_bodies: usize = 100;
    let mut bodies: Vec<Body> = Vec::with_capacity(n_bodies);
    for _ in 0..n_bodies {
        bodies.push(physics_body(rng().sample(&masses), &dist_pos, &dis_vel, Rand));
    }
    let view = View::new(1, false, 1, scale);
    demo_loop(view, bounds, bodies, 0, true).await;
}

pub async fn grav_demo_2(log: bool) { // orbital
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
    let pos = XY::zero();
    let vel = XY::zero();
    let accel = XY::zero();
    bodies.push(Body{pos, vel, accel, mass: centre_mass });
    for _ in 0..n_bodies {
        bodies.push(physics_body(rng().sample(&masses), &dist_pos, &dis_vel, Rand));
    }
    let view = View::new(1, false, 1, scale);
    demo_loop(view, bounds, bodies, 1, log).await;
}

pub async fn grav_demo_2_5(log: bool) { // orbital
    let r_low = 500.0;
    let r_high = 5000.0;
    let scale = 500.0 / r_high;
    let bounds = CentredBox {centre: XY::zero(), r: r_high * 200.0};
    let centre_mass: f64 = 1e8;
    // let dist_pos = Uniform::new(r_low, r_high).unwrap();
    let dist_pos = linear_distribution(&r_low, &r_high);
    let dis_vel = Uniform::new(centre_mass.sqrt()*0.999, centre_mass.sqrt()*1.0).unwrap();
    let masses = Uniform::new(1.0, 1000.0).unwrap();
    let masses_large = Uniform::new(1e5, 5e5).unwrap();
    let n_bodies: usize = 1000;
    let mut bodies: Vec<Body> = Vec::with_capacity(n_bodies + 5);
    let pos = XY::zero();
    let vel = XY::zero();
    let accel = XY::zero();
    bodies.push(Body{pos, vel, accel, mass: centre_mass });
    for _ in 0..4 {
        bodies.push(physics_body(rng().sample(&masses_large), &dist_pos, &dis_vel, Const(centre_mass)));
    }
    for _ in 0..n_bodies {
        bodies.push(physics_body(rng().sample(&masses), &dist_pos, &dis_vel, Const(centre_mass)));
    }

    let view = View::new(10, false, 1, scale);
    demo_loop(view, bounds, bodies, 5, log).await;
}

pub async fn grav_demo_2_75(log: bool) { // orbital
    let r_low = 500.0;
    let r_high = 5000.0;
    let scale = 500.0 / r_high;
    let bounds = CentredBox {centre: XY::zero(), r: r_high * 200.0};
    let centre_mass: f64 = 1e8;
    let dist_pos = Uniform::new(r_low, r_high).unwrap();
    let dis_vel = Uniform::new(centre_mass.sqrt()*0.98, centre_mass.sqrt()*1.02).unwrap();
    let masses = Uniform::new(1.0, 1000.0).unwrap();
    let masses_large = Uniform::new(1e5, 5e5).unwrap();
    let n_bodies: usize = 1000;
    let mut bodies: Vec<Body> = Vec::with_capacity(n_bodies + 5);
    let pos = XY::zero();
    let vel = XY::zero();
    let accel = XY::zero();
    bodies.push(Body{pos, vel, accel, mass: centre_mass });
    for _ in 0..4 {
        bodies.push(physics_body(rng().sample(&masses_large), &dist_pos, &dis_vel, Const(centre_mass)));
    }
    for _ in 0..n_bodies {
        bodies.push(physics_body(rng().sample(&masses), &dist_pos, &dis_vel, Const(centre_mass)));
    }

    let view = View::new(10, false, 1, scale);
    demo_loop(view, bounds, bodies, 5, log).await;
}

pub async fn grav_demo_2_5_bench() { // orbital
    let r_low = 500.0;
    let r_high = 5000.0;
    let scale = 500.0 / r_high;
    let bounds = CentredBox {centre: XY::zero(), r: r_high * 200.0};
    let centre_mass: f64 = 1e8;
    // let dist_pos = Uniform::new(r_low, r_high).unwrap();
    let dist_pos = linear_distribution(&r_low, &r_high);
    let dis_vel = Uniform::new(centre_mass.sqrt()*0.999, centre_mass.sqrt()*1.0).unwrap();
    let masses = Uniform::new(1.0, 1000.0).unwrap();
    let masses_large = Uniform::new(1e5, 5e5).unwrap();
    let n_bodies: usize = 1000;
    let mut bodies: Vec<Body> = Vec::with_capacity(n_bodies + 5);
    let pos = XY::zero();
    let vel = XY::zero();
    let accel = XY::zero();
    bodies.push(Body{pos, vel, accel, mass: centre_mass });
    for _ in 0..4 {
        bodies.push(physics_body(rng().sample(&masses_large), &dist_pos, &dis_vel, Const(centre_mass)));
    }
    for _ in 0..n_bodies {
        bodies.push(physics_body(rng().sample(&masses), &dist_pos, &dis_vel, Const(centre_mass)));
    }

    bench_loop(bounds, bodies, scale, 10, 5, 6_000).await;
}

#[allow(unused_variables)]
pub async fn grav_demo_3(log: bool) { // binary orbital systems

}