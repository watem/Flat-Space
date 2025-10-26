use rand::distr::{Distribution, Uniform};
use rand::Rng;
use crate::model::real_space::physics::Quadrant;

#[derive(Copy, Clone)]
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
        Self::radial(r, angle)
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

    pub fn radial(r: f64, angle: f64) -> XY {
        let x = r * f64::cos(angle);
        let y = r * f64::sin(angle);
        Self{x, y}
    }

    pub fn radius(&self) -> f64 {
        (self.x * self.x + self.y * self.y).sqrt()
    }

    pub fn rotate_90(&self) -> Self {
        Self {x: -self.y, y: self.x}
    }

    pub fn multiply(&self, scaler: f64) -> XY {
        Self {x: self.x * scaler, y: self.y * scaler}
    }

    pub fn unit(&self) -> XY {
        self.multiply(1.0/self.radius())
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
        self.subtract(other).radius()
    }

    pub fn x(&self) -> f64 {
        self.x
    }
    pub fn y(&self) -> f64 {
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
            None => {format!("({}, {})", self.x(), self.y())}
            Some(n) => {format!("({:.n$}, {:.n$})", self.x(), self.y())}
        }

    }
}

impl std::ops::Add for XY {
    type Output = XY;

    fn add(self, rhs: Self) -> Self::Output {
        self.plus(&rhs)
    }
}

impl std::ops::Mul<f64> for XY {
    type Output = XY;

    fn mul(self, rhs: f64) -> Self::Output {
        self.multiply(rhs)
    }
}

impl std::ops::Div<f64> for XY {
    type Output = XY;
    fn div(self, rhs: f64) -> Self::Output {
        self * (1.0 / rhs)
    }
}

impl std::ops::AddAssign for XY {
    fn add_assign(&mut self, rhs: Self) {
        self.add_self(&rhs);
    }
}

impl std::ops::Sub for XY {
    type Output = XY;
    fn sub(self, rhs: Self) -> Self::Output {
        XY{x: self.x - rhs.x, y: self.y - rhs.y}
    }
}

impl std::ops::SubAssign for XY {
    fn sub_assign(&mut self, rhs: Self) {
        self.x -= rhs.x;
        self.y -= rhs.y;
    }
}