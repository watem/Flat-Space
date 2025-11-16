use gravity::model::xy::XY;


enum Focus
{
    Position(XY),
    Body(usize)
}

static mut SYSTEM_ID: usize = 0;

trait SystemViewModel {

}

trait FocusModel {

}

pub trait ViewModel {

}
