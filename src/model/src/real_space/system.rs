struct System {

}

enum SystemBody
{
    Star,
    Planet(Planetoid),
    Moon(Planetoid),
    Asteroid,
    Comet,
}


enum Planetoid
{
    Rocky,
    Ice,
    Metallic,
    GasGiant,
    BrownDwarf
}