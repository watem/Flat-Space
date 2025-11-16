// quadtree
// theta  // accuracy
// s/d    //s := region width, d:= distance between source and region COM
//
/*
 * Minimum # nodes with a full tree of size n is 4n/3.
 * More common looks to be ~1.9*n. 2n should be a good estimate to not need to grow too often.
 */
use crate::model::physics::{Body, CentredBox, CoM, Positional, Quadrant, SpaceDimension, System};
use crate::model::xy::XY;
use crate::physics::gravity::grav;
// use crate::services_::gravity::grav;

static THETA_ACC: f64 = 0.25;

struct LeafPushInfo {
    com1: CoM,
    com2: CoM,
    combined_com: CoM,
    id1: usize,
    id2: usize,
}

impl LeafPushInfo {
    fn new(com1: &CoM, com2: &CoM, id1: usize, id2: usize) -> Self {
        let combined_com = com1.clone() + com2.clone();
        LeafPushInfo{com1: com1.clone(), com2: com2.clone(), combined_com, id1, id2}
    }
}

pub struct BarnesHutQuadTree {
    nodes: Vec<BarnesHutQuadTreeNode>,
    root: Option<usize>,
}

impl BarnesHutQuadTree {
    fn _insert(&mut self, boundary: CentredBox, target: &CoM) {
        let node = BarnesHutQuadTreeNode::new(boundary, target, None);
        self.nodes.push(node)
    }
    pub fn new(boundary: &CentredBox, tree_capacity: usize) -> Self {
        let nodes = Vec::with_capacity(tree_capacity);
        let mut tree = Self{nodes, root: None};
        tree._insert(boundary.clone(), &CoM::zero());
        tree
    }

    pub fn size(&self) -> usize {
        self.nodes.len()
    }
    pub fn add_all(&mut self, system: &System) {
        for (id, body) in system.bodies.iter().enumerate() {
            match body {
                Body::MassBody(mass, p) => {self.insert(system, p, *mass, id);}
                _ => {}
            }
        }
    }

    fn apply_branch(&self, system: &mut System, branch: Option<usize>, body_pos: XY, id: usize)
    {
        match branch {
            Some(node_id) => {self.apply_r(system, node_id, body_pos, id)}
            None => {}
        }
    }
    fn apply_r(&self, system: &mut System, node_id: usize, body_pos: XY, id: usize)
    {
        let node = &self.nodes[node_id];
        if node.id.is_some_and(|n| {n == id})  // FIXME: ids are not being set properly in
        {
            return;
        }

        let d = body_pos.distance(&node.com.pos());
        let sd = node.boundary.r / d;

        if sd <= THETA_ACC || node.is_leaf() {
            grav(system, id, body_pos, d, &node.com);
        }
        else {
            self.apply_branch(system, node.south_west, body_pos, id);
            self.apply_branch(system, node.south_east, body_pos, id);
            self.apply_branch(system, node.north_west, body_pos, id);
            self.apply_branch(system, node.north_east, body_pos, id);
        }
    }
    pub fn apply(&self, system: &mut System, id: usize)
    {
        let body_pos = system.bodies[id].pos(system);
        match self.root {
            None => {
                return;
            }
            Some(root) => {
                self.apply_r(system, root, body_pos, id);
            }
        }
    }
    pub fn apply_all(&self, system: &mut System, n_bodies: usize) {
        for i in 0..n_bodies {
            self.apply(system, i);
        }
    }

    fn grow_new_node(&mut self, mut node_id: usize, com: CoM, bounds: &CentredBox, quadrant: Quadrant) -> usize {
        let new_node = {
            let new_node_id = {self.nodes.len()};
            let mut new_node = BarnesHutQuadTreeNode::new(bounds.clone(), &com, None);

            match quadrant {
                Quadrant::SouthWest => {new_node.north_east = Some(node_id)}
                Quadrant::NorthWest => {new_node.south_east = Some(node_id)}
                Quadrant::SouthEast => {new_node.north_west = Some(node_id)}
                Quadrant::NorthEast => {new_node.south_west = Some(node_id)}
            }

            node_id = new_node_id;
            new_node
        };

        self.nodes.push(new_node);
        node_id
    }

    fn grow(&mut self, mut root_id: usize, system: &System, space: &SpaceDimension, target: &CoM, id: usize) {
        // panic!("Grow not implemented correctly yet!");
        let com = self.nodes[root_id].com.clone();

        let mut bounds = self.nodes[root_id].boundary.clone();
        loop {
            let (new_bounds, quad) = BarnesHutQuadTree::_node_quadrant(&bounds, &target.pos(), true);
            bounds = new_bounds;
            root_id = self.grow_new_node(root_id, com.clone(), &bounds, quad);

            if bounds.contains(&target.pos()) {
               break;
            }
        }
        self.root = Some(root_id);
        self.insert(system, space, target.mass(), id);
    }

    fn _node_quadrant(original_bounds: &CentredBox, point: &XY, grow: bool) -> (CentredBox, Quadrant) {
        let mut r = {if grow{original_bounds.r} else {original_bounds.r / 2.0}};
        let quadrant = point.quadrant_of(&original_bounds.centre);
        let centre: XY = match quadrant {
            Quadrant::SouthWest => {
                let diff = XY::new(-r, -r);
                original_bounds.centre + diff
            }
            Quadrant::NorthWest => {
                let diff = XY::new(-r, r);
                original_bounds.centre + diff
            }
            Quadrant::SouthEast => {
                let diff = XY::new(r, -r);
                original_bounds.centre + diff
            }
            Quadrant::NorthEast => {
                let diff = XY::new(r, r);
                original_bounds.centre + diff
            }
        };
        if grow {r *= 2.0;}
        (CentredBox{centre, r}, quadrant)
    }
    fn insert_r(&mut self, node_id: usize, target: &CoM, id: usize) {
        let node = &self.nodes[node_id];

        if node.is_leaf() {
            self.push_leaf(node_id, target, node.id.unwrap(), id);
        }
        else {
            let (new_bounds, quadrant_loc) =  BarnesHutQuadTree::_node_quadrant(&node.boundary, &target.pos(), false);
            let quadrant: Option<usize> = match quadrant_loc {
                Quadrant::SouthWest => node.south_west,
                Quadrant::NorthWest => node.north_west,
                Quadrant::SouthEast => node.south_east,
                Quadrant::NorthEast => node.north_east,
            };

            match quadrant {
                Some(target_node_id) => {
                    self.insert_r(target_node_id, target, id);
                }
                None => {
                    let new_id = self._new_node(node_id, target, Some(id));
                }
            }
        }
        {
            let node = &mut self.nodes[node_id];
            node.com += target.clone();
            // node.com = node.com.weighted_average(node.mass, xy, mass);
            // node.mass += mass;
        };
    }

    fn _new_node(&mut self, mut node_id: usize, target: &CoM, id: Option<usize>) -> usize
    {
        let new_node = {
            let new_node_id = {self.nodes.len()};
            let node: &mut BarnesHutQuadTreeNode = &mut self.nodes[node_id];
            let (new_bounds, quadrant) =  BarnesHutQuadTree::_node_quadrant(&node.boundary, &target.pos(), false);

            node_id = new_node_id;
            match quadrant {
                Quadrant::SouthWest => {node.south_west = Some(node_id)}
                Quadrant::NorthWest => {node.north_west = Some(node_id)}
                Quadrant::SouthEast => {node.south_east = Some(node_id)}
                Quadrant::NorthEast => {node.north_east = Some(node_id)}
            }
            BarnesHutQuadTreeNode::new(new_bounds, target, id)
        };
        self.nodes.push(new_node);
        node_id
    }

    fn push_leaf(&mut self, mut node_id: usize, target: &CoM, id1: usize, id2: usize) {
        {
            let node = &mut self.nodes[node_id];
            node.id = None;
        }

        let leaf_info = {
            let node = &self.nodes[node_id];
            LeafPushInfo::new(&node.com, target, id1, id2)
        };
        loop
        {
            let matching_quadrant = {
                let node = &self.nodes[node_id];
                node.boundary.centre.quadrant_of(&target.pos()) == node.boundary.centre.quadrant_of(&node.com.pos())
            };
            if !matching_quadrant
            {
                break;
            }

            node_id = self._new_node(node_id, &leaf_info.combined_com, None);
        }

        self._new_node(node_id, &leaf_info.com1, Some(leaf_info.id1));
        self._new_node(node_id, &leaf_info.com2, Some(leaf_info.id2));
    }

    pub fn insert(&mut self, system: &System, space: &SpaceDimension, mass: f64, id: usize) {
        if mass <= 0.0 {
            return;  // No impact from these sources
        }
        let pos = &space.pos(system);
        let com = CoM::new(pos, mass);
        match self.root {
            None => {
                self.root = Some(0);
                let node = &mut self.nodes[0];
                let bounds = &mut node.boundary;

                if !bounds.contains(pos) {
                    bounds.r = bounds.distance_max_axis(pos) * 2.0;
                }
                node.id = Some(id);
                node.com = com;
            }
            Some(root) => {
                let root_node = &self.nodes[root];
                if root_node.boundary.contains(pos) {
                    self.insert_r(root, &com, id);
                }
                else {
                    self.grow(root, system, space, &com, id)
                }

            }
        }
    }
    
    pub fn com(&self) -> CoM {
        match self.root {
            None => { CoM::zero() }
            Some(root) => {
                self.nodes[root].com.clone()
            }
        }
    }

    pub fn bounds(&self, default: CentredBox) -> CentredBox {
        match self.root {
            None => { default }
            Some(root) => {
                self.nodes[root].boundary.clone()
            }
        }
    }
}

struct BarnesHutQuadTreeNode {
    boundary: CentredBox,
    id: Option<usize>,
    com: CoM,
    north_east: Option<usize>,
    north_west: Option<usize>,
    south_east: Option<usize>,
    south_west: Option<usize>,
}

impl BarnesHutQuadTreeNode {
    fn new(boundary: CentredBox, target: &CoM, id: Option<usize>) -> BarnesHutQuadTreeNode {
        let b = boundary;
        let com = target.clone();
        Self{ boundary: b, com, id, north_east: None, north_west: None, south_east: None, south_west: None }
    }

    fn is_leaf(&self) -> bool {
        self.north_east.is_none() && self.north_west.is_none() && self.south_west.is_none() && self.south_east.is_none()
    }
}


