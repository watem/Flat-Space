use rand::distr::{Distribution, Uniform};
use rand::Rng;

pub struct XY {
    x: f64,
    y: f64,
}

impl XY {
    pub fn clone(&self) -> XY {
        XY {
            x: self.x,
            y: self.y
        }
    }
    pub fn random(distribution: &impl Distribution<f64>) -> Self {
        let mut rng = rand::rng();
        Self{x: rng.sample(distribution), y: rng.sample(distribution)}
    }

    pub fn random_radial(r_distribution: &impl Distribution<f64>) -> Self {
        let mut rng = rand::rng();
        let r = rng.sample(r_distribution);
        let angle = rng.sample(Uniform::new(-std::f64::consts::PI, std::f64::consts::PI).unwrap());
        let x = r * f64::cos(angle);
        let y = r * f64::sin(angle);
        Self{x, y}
    }

    pub fn set(&mut self, xy: &XY) {
        self.x = xy.x;
        self.y = xy.y;
    }

    pub fn new(x: f64, y: f64) -> XY {
        XY{x, y}
    }
    pub fn zero() -> XY {
        XY{x: 0.0, y: 0.0}
    }

    pub fn rad(&self) -> f64 {
        (self.x * self.x + self.y * self.y).sqrt()
    }

    pub fn rotate_90(&self) -> Self {
        Self {x: -self.y, y: self.x}
    }

    pub fn multiply(&self, scaler: f64) -> XY {
        Self {x: self.x * scaler, y: self.y * scaler}
    }

    pub fn unit(&self) -> XY {
        self.multiply(1.0/self.rad())
    }

    pub fn subtract(&self, other: &XY) -> XY {
        Self {x: self.x - other.x, y: self.y - other.y}
    }

    pub fn add_self(&mut self, other: &XY)
    {
        self.x += other.x;
        self.y += other.y;
    }
    pub fn plus(&self, other: &XY) -> XY {
        Self {x: self.x + other.x, y: self.y + other.y}
    }

    pub fn quadrant_of(&self, p0: &XY) -> Quadrant {
        if self.x < p0.x && self.y < p0.y {return Quadrant::SouthWest;}
        if self.x < p0.x {return Quadrant::NorthWest;}
        if self.y < p0.y {return Quadrant::SouthEast;}
        Quadrant::NorthEast
    }

    pub fn weighted_average(&self, self_weight: f64, other: &XY, other_weight: f64) -> XY {
        self.multiply(self_weight).plus(&other.multiply(other_weight)).multiply(1.0/(self_weight + other_weight))
    }

    pub fn distance(&self, other: &XY) -> f64 {
        self.subtract(other).rad()
    }

    pub fn get_x(&self) -> f64 {
        self.x
    }
    pub fn get_y(&self) -> f64 {
        self.y
    }

    pub fn set_x(&mut self, x:f64) {
        self.x = x;
    }
    pub fn set_y(&mut self, y:f64) {
        self.y = y;
    }

    pub fn str(&self, precision: Option<usize>) -> String {
        match precision {
            None => {format!("({}, {})", self.get_x(), self.get_y())}
            Some(n) => {format!("({:.n$}, {:.n$})", self.get_x(), self.get_y())}
        }

    }
}

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
        diff.x.abs() <= self.r && diff.y.abs() <= self.r
    }

    pub fn distance_max_axis(&self, point: &XY) -> f64 {
        let diff = self.centre.subtract(point);
        diff.x.max(diff.y)
    }
}

pub struct System {
    pub bodies: Vec<Body>,
    pub nonphysics_bodies: Vec<NonPhysicsBody>
}

pub struct NonPhysicsBody {
    pub parent: Option<usize>,
    pub theta: f64,
    pub mass: f64,
    pub rad: f64,
    pub angular_velocity: f64,
}

pub struct Body {
    pub pos: XY,
    pub vel: XY,
    pub accel: XY,
    pub mass: f64
}

impl Body {
    pub fn com(&self) -> CoM {
        CoM{pos: self.pos.clone(), mass: self.mass}
    }
    pub fn step(&mut self, dt: f64) -> CoM {
        let d_pos = self.vel.multiply(dt).plus(&self.accel.multiply(dt*dt / 2.0));
        self.vel.set(&self.vel.plus(&self.accel.multiply(dt)));
        self.pos.set(&self.pos.plus(&d_pos));
        CoM::new(&d_pos, self.mass)
    }
}
#[derive(PartialEq)]
pub enum Quadrant {
    NorthEast,
    NorthWest,
    SouthEast,
    SouthWest,
}