use crate::model::xy::XY;

pub struct CoM
{
    pos: XY,
    mass: f64,
}

impl CoM {
    pub fn zero() -> CoM {
        let pos = XY::zero();
        CoM{ pos, mass: 0.0 }
    }

    pub fn new(pos: &XY, mass: f64) -> CoM {
        CoM{pos: pos.clone(), mass}
    }

    pub fn add_self(&mut self, other: CoM) {
        let new_com = self.pos.weighted_average(self.mass, &other.pos, other.mass);
        self.pos.set(&new_com);
        self.mass += other.mass;
    }

    pub fn clone(&self) -> CoM {
        CoM{pos: self.pos.clone(), mass: self.mass}
    }

    pub fn pos(&self) -> XY {
        self.pos.clone()
    }
    pub fn mass(&self) -> f64 { self.mass }
}

impl std::ops::AddAssign for CoM {
    fn add_assign(&mut self, rhs: Self) {
        let new_com = self.pos.weighted_average(self.mass, &rhs.pos, rhs.mass);
        self.pos.set(&new_com);
        self.mass += rhs.mass;
    }
}

impl std::ops::Add for CoM {
    type Output = CoM;
    fn add(self, rhs: Self) -> Self::Output {
        CoM{pos: self.pos.weighted_average(self.mass, &rhs.pos, rhs.mass), mass: self.mass + rhs.mass}
    }
}

pub struct CentredBox {
    pub centre: XY,
    pub r: f64,
}

impl CentredBox {
    pub fn new(centre: XY, r: f64) -> CentredBox {
        CentredBox{centre, r}
    }
    pub fn clone(&self) -> CentredBox {
        CentredBox {
            centre: self.centre.clone(),
            r: self.r
        }
    }
    pub fn contains(&self, point: &XY) -> bool {
        let diff = self.centre.subtract(point);
        diff.x().abs() <= self.r && diff.y().abs() <= self.r
    }

    pub fn distance_max_axis(&self, point: &XY) -> f64 {
        let diff = self.centre.subtract(point);
        diff.x().max(diff.y())
    }
}

pub struct System {
    pub name: String,
    pub bodies: Vec<Body>,
    id: usize,
}

static mut SYSTEM_ID: usize = 0;
impl System {
    pub fn new(name: String, bodies: Vec<Body>) -> System {
        let id = unsafe {
            let id = SYSTEM_ID;
            SYSTEM_ID += 1;
            id
        };
        System{name, bodies, id}
    }

    pub fn id(&self) -> usize {
        self.id
    }
}

pub enum Focus
{
    Position(XY),
    Body(usize)
}

pub trait Positional {
    fn pos(&self, system: &System) -> XY;
    fn vel(&self, system: &System) -> XY;

    fn time_step(&mut self, dt: f64);

    // fn system<'game>(&self, game: &'game Game) -> &'game System;
}

pub trait Accel {
    fn accel(&mut self, accel: &XY);
    fn reset_accel(&mut self);
}

pub struct AccelerationObject {
    pub pos: XY,
    pub vel: XY,
    pub accel: XY,
    pub system: usize
}

impl AccelerationObject {
    pub fn new(pos: XY, vel: XY, accel: XY, system: usize) -> AccelerationObject {
        AccelerationObject{ pos, vel, accel, system }
    }

    pub fn zero(system: usize) -> AccelerationObject {
        AccelerationObject::new(XY::zero(), XY::zero(), XY::zero(), system)
    }
}

impl Positional for AccelerationObject {
    fn pos(&self, _system: &System) -> XY {
        self.pos.clone()
    }

    fn vel(&self, _system: &System) -> XY {
        self.vel.clone()
    }

    fn time_step(&mut self, dt: f64) {
        let d_pos = self.vel * dt  + self.accel * (dt*dt / 2.0);
        self.vel += self.accel * dt;
        self.pos += d_pos;
    }

    // fn system<'game>(&self, game: &'game Game) -> &'game System {
    //     game.system(self.system)
    // }
}

impl Accel for AccelerationObject {
    fn accel(&mut self, accel: &XY) {
        self.accel += accel.clone();
    }

    fn reset_accel(&mut self) {
        self.accel = XY::zero();
    }
}

pub struct RotationalObject {
    pub focus: Focus,
    pub rad: f64,
    pub angular_velocity: f64,
    pub angle: f64,
    pub system: usize
}

impl RotationalObject {
    pub fn new(focus: Focus, rad: f64, angular_velocity: f64, angle: f64, system: usize) -> RotationalObject {
        RotationalObject{focus, rad, angular_velocity, angle, system}
    }
}

impl Positional for RotationalObject {
    fn pos(&self, system: &System) -> XY {
        match &self.focus {
            Focus::Position(p) => {
                p.clone() + XY::radial(self.rad, self.angle)
            }
            Focus::Body(b) => {
                system.bodies.get(*b).unwrap().pos(system) + XY::radial(self.rad, self.angle)
            }
        }
    }

    fn vel(&self, _system: &System) -> XY {
        let v = XY::radial(self.rad, self.angle).rotate_90() * self.angular_velocity;
        match &self.focus {
            Focus::Position(_) => v,
            Focus::Body(_) => v
        }
    }

    fn time_step(&mut self, dt: f64) {
        self.angle += self.angular_velocity * dt;
    }

    // fn system<'game>(&self, game: &'game Game) -> &'game System {
    //     game.system(self.system)
    // }
}

pub enum SpaceDimension {
    Standard(AccelerationObject),
    Rotating(RotationalObject)
}

impl Positional for SpaceDimension {
    fn pos(&self, system: &System) -> XY {
        match self {
            SpaceDimension::Standard(p) => {p.pos(system)}
            SpaceDimension::Rotating(p) => {p.pos(system)}
        }
    }

    fn vel(&self, system: &System) -> XY {
        match self {
            SpaceDimension::Standard(p) => {p.vel(system)}
            SpaceDimension::Rotating(p) => {p.vel(system)}
        }
    }

    fn time_step(&mut self, dt: f64) {
        match self {
            SpaceDimension::Standard(p) => {p.time_step(dt)}
            SpaceDimension::Rotating(p) => {p.time_step(dt)}
        }
    }

    // fn system<'game>(&self, system: &System) -> &'game System {
    //     match self {
    //         SpaceDimension::Standard(p) => {p.system(game)}
    //         SpaceDimension::Rotating(p) => {p.system(game)}
    //     }
    // }
}

impl Accel for SpaceDimension {
    fn accel(&mut self, accel: &XY) {
        match self {
            SpaceDimension::Standard(p) => {p.accel(accel);}
            SpaceDimension::Rotating(_) => {}  // Cannot accelerate, do nothing
        }
    }

    fn reset_accel(&mut self) {
        match self {
            SpaceDimension::Standard(p) => p.reset_accel(),
            SpaceDimension::Rotating(_) => {}  // Cannot accelerate, do nothing
        }
    }
}

pub enum Body {
    MassBody(f64, SpaceDimension),
    MasslessBody(SpaceDimension),
}

impl Body {
    pub fn com(&self, system: &System) -> CoM {
        let mass = match self {
            Body::MassBody(m, _) => *m,
            Body::MasslessBody(_) => 0.0
        };

        CoM{pos: self.pos(system), mass}
    }

    pub fn new(mass: f64, dimension: SpaceDimension) -> Body {
        if mass > 0.0 {
            return Body::MassBody(mass, dimension);
        }
        Body::MasslessBody(dimension)

    }
}

impl Positional for Body {
    fn pos(&self, system: &System) -> XY {
        match self {
            Body::MassBody(_, p) => {p.pos(system)}
            Body::MasslessBody(p) => {p.pos(system)}
        }
    }

    fn vel(&self, system: &System) -> XY {
        match self {
            Body::MassBody(_, p) => {p.vel(system)}
            Body::MasslessBody(p) => {p.vel(system)}
        }
    }

    fn time_step(&mut self, dt: f64) {
        match self {
            Body::MassBody(_, p) => {p.time_step(dt)}
            Body::MasslessBody(p) => {p.time_step(dt); }
        }
    }

    // fn system<'game>(&self, game: &'game Game) -> &'game System {
    //     match self {
    //         Body::MassBody(_, p) => {p.system(game)}
    //         Body::MasslessBody(p) => {p.system(game)}
    //     }
    // }
}

impl Accel for Body {
    fn accel(&mut self, accel: &XY) {
        match self {
            Body::MassBody(_, p) => p.accel(accel),
            Body::MasslessBody(p) => p.accel(accel)
        }
    }

    fn reset_accel(&mut self) {
        match self {
            Body::MassBody(_, p) => p.reset_accel(),
            Body::MasslessBody(p) => p.reset_accel()
        }
    }
}

#[derive(PartialEq)]
pub enum Quadrant {
    NorthEast,
    NorthWest,
    SouthEast,
    SouthWest,
}

#[allow(dead_code)]
pub enum SpeedType
{
    None,
    Const(f64),
    Rand,
}