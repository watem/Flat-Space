use macroquad::color::{BLUE, DARKGRAY, GREEN, RED, YELLOW};
use macroquad::prelude::{clear_background, draw_circle, draw_line, draw_rectangle, draw_text, next_frame, screen_height, screen_width};

#[macroquad::main("Flat-Space")]
#[example]
async fn main() {
    let mut theta: f32 = 0.0;
    let radius: f32 = 50.0;

    loop {
        clear_background(RED);

        let mid_x: f32 = screen_width() / 2.0;
        let mid_y: f32 = screen_height() / 2.0;
        draw_line(mid_x + radius * theta.cos(), mid_y + radius * theta.sin(), mid_x-radius * theta.cos(), mid_y-radius * theta.sin(), 15.0, BLUE);
        draw_rectangle(screen_width() / 2.0 - 60.0, 100.0, 120.0, 60.0, GREEN);
        draw_circle(screen_width() - 30.0, screen_height() - 30.0, 15.0, YELLOW);

        draw_text("IT WORKS!", 20.0, 20.0, 30.0, DARKGRAY);

        theta += std::f32::consts::TAU / 30.0;

        next_frame().await
    }
}