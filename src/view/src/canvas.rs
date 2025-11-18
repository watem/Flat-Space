use macroquad::color::{Color, BLACK, ORANGE, WHITE};
use macroquad::prelude::{clear_background, draw_circle, draw_text, next_frame, screen_height, screen_width};
use gravity::model::xy::XY;
use crate::view::{SystemView, View};

impl View
{
    pub fn draw_point_demo(&self, pos: &XY, size: f32, color: Option<Color>, system_id: usize)
    {
        self.systems[system_id].draw_point_demo(pos, size, color);
    }

    pub fn write_speed(&self)
    {
        draw_text(&format!("Speed: {}", self.speed), 0., 32., 32., WHITE);
    }

    pub fn write_paused(&self)
    {
        let mid_x: f32 = screen_width() / 2f32;
        let mid_y: f32 = screen_height() / 2f32;
        draw_text("PAUSED", mid_x - 48., mid_y + 8., 32., ORANGE);
    }
}

impl SystemView
{
    pub fn draw_point_demo(&self, pos: &XY, size: f32, color: Option<Color>)
    {
        let color = color.unwrap_or(WHITE);
        let screen = self.coords2screen(pos);
        draw_circle(screen.x() as f32, screen.y() as f32, size, color);
    }
}
pub fn reset() {
    clear_background(BLACK);
}

// pub fn draw(body: &Body) {
//     let mid_x: f32 = screen_width() / 2.0;
//     let mid_y: f32 = screen_height() / 2.0;
//
//     let x = mid_x + body.pos.x() as f32;
//     let y = mid_y + body.pos.y() as f32;
//     draw_circle(x, y, f64::powf(body.mass / 10.0, 1.0 / 3.0) as f32, WHITE);
// }

pub fn draw_point(xy: &XY, size: f32, color: Color, scale: f64) {
    let mid_x: f32 = screen_width() / 2.0;
    let mid_y: f32 = screen_height() / 2.0;

    let x = mid_x + (xy.x() * scale) as f32;
    let y = mid_y + (xy.y() * scale) as f32;
    draw_circle(x, y, size, color);
}

pub async fn render() {
    next_frame().await;
}

pub async fn demo_render() {
    next_frame().await;
}

