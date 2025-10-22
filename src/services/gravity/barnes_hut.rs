// quadtree
// theta  // accuracy
// s/d    //s := region width, d:= distance between source and region COM
//
/*
 * Minimum # nodes with a full tree of size n is 4n/3.
 * More common looks to be ~1.9*n. 2n should be a good estimate to not need to grow too often.
 */

use crate::model::real_space::physics::{Body, CentredBox, Quadrant, XY};
use crate::services::gravity::grav;

static THETA_ACC: f64 = 0.25;

struct LeafPushInfo {
    com1: XY,
    com2: XY,
    combined_com: XY,
    mass1: f64,
    mass2: f64,
    combined_mass: f64,
    id1: usize,
    id2: usize,
}

impl LeafPushInfo {
    fn new(com1: &XY, com2: &XY, mass1: f64, mass2: f64, id1: usize, id2: usize) -> Self {
        let combined_mass = mass1 + mass2;
        let combined_com = com1.weighted_average(mass1, com2, mass2);
        LeafPushInfo{com1: com1.clone(), com2: com2.clone(), combined_com, mass1, mass2, combined_mass, id1, id2}
    }
}

pub struct BarnesHutQuadTree {
    nodes: Vec<BarnesHutQuadTreeNode>,
    root: Option<usize>,
}

impl BarnesHutQuadTree {
    fn _insert(&mut self, boundary: CentredBox, mass: f64, com: XY) {
        let node = BarnesHutQuadTreeNode::new(boundary, mass, com, None);
        self.nodes.push(node)
    }
    pub fn new(boundary: &CentredBox, tree_capacity: usize) -> Self {
        let nodes = Vec::with_capacity(tree_capacity);
        let mut tree = Self{nodes, root: None};
        tree._insert(boundary.clone(), 0.0, XY::zero());
        tree
    }

    pub fn size(&self) -> usize {
        self.nodes.len()
    }
    pub fn add_all(&mut self, bodies: &[Body]) {
        for (id, body) in bodies.iter().enumerate() {
            self.insert(&body.pos, body.mass, id);
        }
    }

    fn apply_branch(&self, branch: Option<usize>, body: &mut Body, id: usize)
    {
        match branch {
            Some(node_id) => {self.apply_r(node_id, body, id)}
            None => {}
        }
    }
    fn apply_r(&self, node_id: usize, body: &mut Body, id: usize)
    {
        let node = &self.nodes[node_id];
        if node.id.is_some_and(|n| {n == id})  // FIXME: ids are not being set properly in
        {
            return;
        }

        let d = body.pos.distance(&node.com);
        let sd = node.boundary.r / d;

        if sd <= THETA_ACC || node.is_leaf() {
            grav(body, &node.com, node.mass);
        }
        else {
            self.apply_branch(node.south_west, body, id);
            self.apply_branch(node.south_east, body, id);
            self.apply_branch(node.north_west, body, id);
            self.apply_branch(node.north_east, body, id);
        }
    }
    pub fn apply(&self, body: &mut Body, id: usize)
    {
        match self.root {
            None => {
                return;
            }
            Some(root) => {
                self.apply_r(root, body, id);
            }
        }
    }
    pub fn apply_all(&self, bodies: &mut [Body]) {
        let mut i = 0;
        for body in bodies {
            self.apply(body, i);
            i += 1;
        }
    }

    fn grow(&mut self, target: &XY) {
        println!("{}", target.str(None));
        panic!("Grow not implemented correctly yet!");
        /*
        let i = self.nodes.len();
        let root_node = &mut self.nodes[self.root.unwrap()];
        let com = &root_node.com;
        let bounds = &root_node.boundary;
        let mass = root_node.mass;
        if bounds.contains(target) {
            return;
        }

        // TODO: get quadrant to expand
        let direction = bounds.centre.quadrant_of(target);
        let mut boundary = CentredBox{centre: XY::zero(), r: 2.0 * bounds.r};

        match direction {
            Quadrant::NorthEast => {}
            Quadrant::NorthWest => {}
            Quadrant::SouthEast => {}
            Quadrant::SouthWest => {}
        }
        // let node = BarnesHutQuadTreeNode::new(&boundary, mass, com.clone());
        */
    }

    fn _node_quadrant(original_bounds: &CentredBox, point: &XY) -> (CentredBox, Quadrant) {
        let r = original_bounds.r / 2.0;
        let quadrant = point.quadrant_of(&original_bounds.centre);
        let centre: XY = match quadrant {
            Quadrant::SouthWest => {
                let diff = XY::new(-r, -r);
                original_bounds.centre.plus(&diff)
            }
            Quadrant::NorthWest => {
                let diff = XY::new(-r, r);
                original_bounds.centre.plus(&diff)
            }
            Quadrant::SouthEast => {
                let diff = XY::new(r, -r);
                original_bounds.centre.plus(&diff)
            }
            Quadrant::NorthEast => {
                let diff = XY::new(r, r);
                original_bounds.centre.plus(&diff)
            }
        };
        (CentredBox{centre, r}, quadrant)
    }
    fn insert_r(&mut self, node_id: usize, mass: f64, xy: &XY, id: usize) {
        let node = &self.nodes[node_id];

        if node.is_leaf() {
            self.push_leaf(node_id, mass, xy, node.id.unwrap(), id);
        }
        else {
            let (new_bounds, quadrant_loc) =  BarnesHutQuadTree::_node_quadrant(&node.boundary, xy);
            let quadrant: Option<usize> = match quadrant_loc {
                Quadrant::SouthWest => node.south_west,
                Quadrant::NorthWest => node.north_west,
                Quadrant::SouthEast => node.south_east,
                Quadrant::NorthEast => node.north_east,
            };

            match quadrant {
                Some(target_node_id) => {
                    self.insert_r(target_node_id, mass, xy, id);
                }
                None => {
                    let new_id = self._new_node(node_id, mass, xy, Some(id));
                }
            }
        }
        {
            let node = &mut self.nodes[node_id];
            node.com = node.com.weighted_average(node.mass, xy, mass);
            node.mass += mass;
        };
    }

    fn _new_node(&mut self, mut node_id: usize, mass: f64, xy: &XY, id: Option<usize>) -> usize
    {
        let new_node = {
            let new_node_id = {self.nodes.len()};
            let node: &mut BarnesHutQuadTreeNode = &mut self.nodes[node_id];
            let (new_bounds, quadrant) =  BarnesHutQuadTree::_node_quadrant(&node.boundary, xy);

            node_id = new_node_id;
            match quadrant {
                Quadrant::SouthWest => {node.south_west = Some(node_id)}
                Quadrant::NorthWest => {node.north_west = Some(node_id)}
                Quadrant::SouthEast => {node.south_east = Some(node_id)}
                Quadrant::NorthEast => {node.north_east = Some(node_id)}
            }
            BarnesHutQuadTreeNode::new(new_bounds, mass, xy.clone(), id)
        };
        self.nodes.push(new_node);
        node_id
    }

    fn push_leaf(&mut self, mut node_id: usize, mass: f64, xy: &XY, id1: usize, id2: usize) {
        {
            let node = &mut self.nodes[node_id];
            node.id = None;
        }

        let leaf_info = {
            let node = &self.nodes[node_id];
            LeafPushInfo::new(&node.com, xy, node.mass, mass, id1, id2)
        };
        loop
        {
            let matching_quadrant = {
                let node = &self.nodes[node_id];
                node.boundary.centre.quadrant_of(xy) == node.boundary.centre.quadrant_of(&node.com)
            };
            if !matching_quadrant
            {
                break;
            }

            node_id = self._new_node(node_id, leaf_info.combined_mass, &leaf_info.combined_com, None);
        }

        self._new_node(node_id, leaf_info.mass1, &leaf_info.com1, Some(leaf_info.id1));
        self._new_node(node_id, leaf_info.mass2, &leaf_info.com2, Some(leaf_info.id2));
    }

    pub fn insert(&mut self, xy: &XY, mass: f64, id: usize) {
        if mass <= 0.0 {
            return;  // No impact from these sources
        }
        match self.root {
            None => {
                self.root = Some(0);
                let node = &mut self.nodes[0];
                let bounds = &mut node.boundary;
                if !bounds.contains(xy) {
                    bounds.r = bounds.distance_max_axis(xy) * 2.0;
                }
                node.id = Some(id);
                node.mass = mass;
                node.com = xy.clone();
            }
            Some(root) => {
                let root_node = &self.nodes[root];
                if root_node.boundary.contains(xy) {
                    self.insert_r(root, mass, xy, id);
                }
                else {
                    self.grow(xy)
                }

            }
        }
    }
    
    pub fn com(&self) -> XY {
        match self.root {
            None => { XY::zero() }
            Some(root) => {
                self.nodes[root].com.clone()
            }
        }
    }
}

struct BarnesHutQuadTreeNode {
    boundary: CentredBox,
    com: XY,
    id: Option<usize>,
    mass: f64,
    north_east: Option<usize>,
    north_west: Option<usize>,
    south_east: Option<usize>,
    south_west: Option<usize>,
}

impl BarnesHutQuadTreeNode {
    fn new(boundary: CentredBox, mass:f64, com: XY, id: Option<usize>) -> BarnesHutQuadTreeNode {
        let b = boundary;
        Self{ boundary: b, mass, com, id, north_east: None, north_west: None, south_east: None, south_west: None }
    }

    fn is_leaf(&self) -> bool {
        self.north_east.is_none() && self.north_west.is_none() && self.south_west.is_none() && self.south_east.is_none()
    }
}


