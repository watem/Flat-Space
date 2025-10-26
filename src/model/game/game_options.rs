#[derive(Default)]
pub struct PermanentOptions {

}

#[derive(Default)]
pub struct TemporaryOptions {

}

#[derive(Default)]
pub struct Options {
    temp: TemporaryOptions,
    permanent: PermanentOptions,
}

impl Options {
    pub fn empty() -> Self {
        Self{temp:TemporaryOptions{}, permanent:PermanentOptions{}}
    }
}