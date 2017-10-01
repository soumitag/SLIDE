/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.clustering;

/**
 *
 * @author Soumita
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class BinaryTree implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private class Node implements Comparable{
        
        int id;
        double height;
        
        Node(int id, double height) {
            this.id = id;
            this.height = height;
        }
        
        @Override
        public int compareTo(Object that) {
            return this.compare(this, (Node)that);
        }
        
        public int compare(Node n1, Node n2) {
            if (n1.height < n2.height) {
                return 1;           // Neither val is NaN, thisVal is smaller
            }
            if (n1.height > n2.height) {
                return -1;            // Neither val is NaN, thisVal is larger
            }
            long thisBits = Double.doubleToLongBits(n1.height);
            long anotherBits = Double.doubleToLongBits(n2.height);

            return (thisBits == anotherBits ? 0
                    : // Values are equal
                    (thisBits < anotherBits ? 1
                            : // (-0.0, 0.0) or (!NaN, NaN)
                            -1));                          // (0.0, -0.0) or (NaN, !NaN)
        }
    }
    
    
    public static final int LARGEST_CHILD_FIRST_LEAF_ORDER = 0;
    public static final int SMALLEST_CHILD_FIRST_LEAF_ORDER = 1;
    public static final int MOST_DIVERSE_CHILD_FIRST_LEAF_ORDER = 2;
    public static final int LEAST_DIVERSE_CHILD_FIRST_LEAF_ORDER = 3;
    
    public int num_internal_nodes;
    public int num_leaf_nodes;
    public int num_nodes;
    public double[][] linkage_tree;
    
    double[] node_split_heights;
    ArrayList <int[]> labels;
    
    int leaf_ordering_strategy;
    public ArrayList <Integer> leaf_ordering;
    public int[][] node_start_end;
    public HashMap <Integer, Integer> leafPositionMap;
    
    public int root_node_id;
    
    public BinaryTree (int N) {
        leaf_ordering = new ArrayList <Integer> ();
        for (int i=0; i<N; i++) {
            leaf_ordering.add(i);
        }
    }
    
    public BinaryTree (double[][] linkage_tree, int leaf_ordering_strategy) {
        
        this.num_leaf_nodes = linkage_tree.length + 1;
        this.num_nodes = 2*num_leaf_nodes - 1;
        this.num_internal_nodes = this.num_nodes - this.num_leaf_nodes;
        this.linkage_tree = linkage_tree;
        
        this.root_node_id = (num_leaf_nodes-1)*2;
        
        this.leaf_ordering_strategy = leaf_ordering_strategy;
        
        // re-order children in linkage tree as per leaf_ordering_strategy
        // always left child is first
        reorderChildNodes(leaf_ordering_strategy);
        
        // extrat leaf order using depth-first-search
        this.leaf_ordering = new ArrayList <Integer> ();
        extractLeafOrdering(root_node_id);
        
        // compute the start and end indices of each inner node
        // using post order traversal
        this.node_start_end = new int[num_internal_nodes][2];
        computeNodeStartEnds();
    }
    
    public int getRow(int node_id) {
        int row = node_id - this.num_leaf_nodes;
                
        if(row < 0) {
            int debug_stop = 1;
        }
        
        return row;
    }
    
    public boolean isLeaf (int node_id) {
        return node_id < num_leaf_nodes;
    }
    
    public int[] getLabels (double threshold) {
        return labels.get(0);
    }
    
    public final void reorderChildNodes(int leaf_ordering_strategy) {
        
        int first_child_id, second_child_id;
        double first_child_size, second_child_size, first_child_height, second_child_height;
        
        int left_child_id = -1;
        int right_child_id = -1;
        
        for (int row = 0; row < linkage_tree.length; row++) {
            
            first_child_id = (int)linkage_tree[row][0];
            second_child_id = (int)linkage_tree[row][1];
            
            if (isLeaf(first_child_id) && isLeaf(second_child_id)) {
                
                left_child_id = first_child_id;
                right_child_id = second_child_id;
                
            } else if (!isLeaf(first_child_id) && !isLeaf(second_child_id)) {
                
                switch (leaf_ordering_strategy) {
                
                    case BinaryTree.LARGEST_CHILD_FIRST_LEAF_ORDER:
                        first_child_size = linkage_tree[getRow(first_child_id)][3];
                        second_child_size = linkage_tree[getRow(second_child_id)][3];
                        if (first_child_size >= second_child_size) {
                            left_child_id = first_child_id;
                            right_child_id = second_child_id;
                        } else {
                            left_child_id = second_child_id;
                            right_child_id = first_child_id;
                        }
                        break;

                    case BinaryTree.SMALLEST_CHILD_FIRST_LEAF_ORDER:
                        first_child_size = linkage_tree[getRow(first_child_id)][3];
                        second_child_size = linkage_tree[getRow(second_child_id)][3];
                        if (first_child_size <= second_child_size) {
                            left_child_id = first_child_id;
                            right_child_id = second_child_id;
                        } else {
                            left_child_id = second_child_id;
                            right_child_id = first_child_id;
                        }
                        break;

                    case BinaryTree.MOST_DIVERSE_CHILD_FIRST_LEAF_ORDER:
                        first_child_height = linkage_tree[getRow(first_child_id)][2];
                        second_child_height = linkage_tree[getRow(second_child_id)][2];
                        if (first_child_height >= second_child_height) {
                            left_child_id = first_child_id;
                            right_child_id = second_child_id;
                        } else {
                            left_child_id = second_child_id;
                            right_child_id = first_child_id;
                        }
                        break;

                    case BinaryTree.LEAST_DIVERSE_CHILD_FIRST_LEAF_ORDER:
                        first_child_height = linkage_tree[getRow(first_child_id)][2];
                        second_child_height = linkage_tree[getRow(second_child_id)][2];
                        if (first_child_height <= second_child_height) {
                            left_child_id = first_child_id;
                            right_child_id = second_child_id;
                        } else {
                            left_child_id = second_child_id;
                            right_child_id = first_child_id;
                        }
                        break;
                        
                    default:
                        break;
                }
                
            } else {
                
                // one child is leaf and one is not
                
                int leaf_child_id;
                int non_leaf_child_id;
                if (isLeaf(first_child_id)) {
                    leaf_child_id = first_child_id;
                    non_leaf_child_id = second_child_id;
                } else {
                    leaf_child_id = second_child_id;
                    non_leaf_child_id = first_child_id;
                }
                
                switch (leaf_ordering_strategy) {
                
                    case BinaryTree.LARGEST_CHILD_FIRST_LEAF_ORDER:
                        left_child_id = non_leaf_child_id;
                        right_child_id = leaf_child_id;
                        break;

                    case BinaryTree.SMALLEST_CHILD_FIRST_LEAF_ORDER:
                        left_child_id = leaf_child_id;
                        right_child_id = non_leaf_child_id;
                        break;
                            
                    case BinaryTree.MOST_DIVERSE_CHILD_FIRST_LEAF_ORDER:
                        left_child_id = non_leaf_child_id;
                        right_child_id = leaf_child_id;
                        break;

                    case BinaryTree.LEAST_DIVERSE_CHILD_FIRST_LEAF_ORDER:
                        left_child_id = leaf_child_id;
                        right_child_id = non_leaf_child_id;
                        break;
                            
                    default:
                        break;
                            
                }
            }
            
            linkage_tree[row][0] = left_child_id;
            linkage_tree[row][1] = right_child_id;
            
        }
        
    }
    
    public final void extractLeafOrdering (int node_id) {
        // extrat leaf order using depth-first-search
        
        int left_child_id = (int)linkage_tree[getRow(node_id)][0];
        int right_child_id = (int)linkage_tree[getRow(node_id)][1];
        
        if(isLeaf(left_child_id)) {
            leaf_ordering.add(left_child_id);
        } else {
            extractLeafOrdering(left_child_id);
        }
        
        if(isLeaf(right_child_id)) {
            leaf_ordering.add(right_child_id);
        } else {
            extractLeafOrdering(right_child_id);
        }
        
    }
    
    public final void computeNodeStartEnds () {
        
        leafPositionMap = new HashMap <> ();
        for (int i=0; i<leaf_ordering.size(); i++) {
            leafPositionMap.put(leaf_ordering.get(i),i);
        }
        
        // Post order traversal
        processSubTree(linkage_tree, (num_leaf_nodes-1)*2);
        
    }
    
    public void processSubTree(double[][] linkage_tree, int node_id) {
        
        if (node_id == 3355) {
            int debugStop = 1;
        }
        
        if (isLeaf(node_id)) {
            return;
        }

        int left_child_id = (int)linkage_tree[getRow(node_id)][0];
        int right_child_id = (int)linkage_tree[getRow(node_id)][1];

        if (left_child_id == 3355) {
            int debugStop = 1;
        }
        
        if (right_child_id == 3355) {
            int debugStop = 1;
        }
        
        processSubTree(linkage_tree, left_child_id);
        processSubTree(linkage_tree, right_child_id);
        
        ArrayList <Integer> start_ends = new ArrayList <Integer> (4);
        
        if (isLeaf(left_child_id)) {
            start_ends.add(leafPositionMap.get(left_child_id));
            //this.node_start_ends[getRow(node_id)][1] = leafPositionMap.get(left_child_id);
        } else {
            start_ends.add(node_start_end[getRow(left_child_id)][0]);
            start_ends.add(node_start_end[getRow(left_child_id)][1]);
            //node_start_ends[getRow(node_id)][1] = node_start_ends[getRow(left_child_id)][0];
        }
        
        if (isLeaf(right_child_id)) {
            start_ends.add(leafPositionMap.get(right_child_id));
            //this.node_start_ends[getRow(node_id)][0] = leafPositionMap.get(right_child_id);
        } else {
            start_ends.add(node_start_end[getRow(right_child_id)][0]);
            start_ends.add(node_start_end[getRow(right_child_id)][1]);
            //node_start_ends[getRow(node_id)][0] = node_start_ends[getRow(right_child_id)][1];
        }
        
        node_start_end[getRow(node_id)][0] = Collections.min(start_ends);
        node_start_end[getRow(node_id)][1] = Collections.max(start_ends);
        
    }
    
    public ArrayList <Integer> breadthFirstSearch (int start_node, int K) {
        
        ArrayList <Integer> sub_tree = new ArrayList <Integer> ();
        
        HashSet <Integer> S = new HashSet <Integer> (K);
        //ArrayDeque <Integer> Q = new ArrayDeque <Integer> ();
        PriorityQueue <Node> Q = new PriorityQueue <Node> (K);
        
        double start_node_height = linkage_tree[getRow(start_node)][2];
        
        S.add(start_node);
        Q.add(new Node(start_node, start_node_height));
        
        while(!Q.isEmpty()) {
            
            Node curr_node = Q.remove();
            if (Q.size() >= K) {
                break;
            }
            
            if (isLeaf(curr_node.id)) {
                continue;
            }
            
            int left_child_id = (int)linkage_tree[getRow(curr_node.id)][0];
            int right_child_id = (int)linkage_tree[getRow(curr_node.id)][1];
            
            double left_child_height = 0.0;
            if (!isLeaf(left_child_id)) {
                left_child_height = linkage_tree[getRow(left_child_id)][2];
            }
            
            double right_child_height = 0.0;
            if (!isLeaf(right_child_id)) {
                right_child_height = linkage_tree[getRow(right_child_id)][2];
            }
            
            S.add(left_child_id);
            S.add(right_child_id);
            
            Q.add(new Node(left_child_id, left_child_height));
            Q.add(new Node(right_child_id, right_child_height));
            
        }
        
        Iterator iterator = S.iterator(); 
        while (iterator.hasNext()){
            sub_tree.add((Integer)iterator.next());
        }
        
        return sub_tree;
    }
    
    public HashMap <Integer, TreeNode> buildSubTree(ArrayList <Integer> sub_tree) {
        
        HashMap <Integer, TreeNode> treeMap = new HashMap <Integer, TreeNode> ();
        
        for (int i=0; i<sub_tree.size(); i++) {

            int node_id = sub_tree.get(i);
            
            if (node_id == 5809 || node_id == 5309 || node_id == 5081 || node_id == 5022 || node_id == 4744) {
                int debuStop = 1;
            }
            
            if (!isLeaf(node_id)) {

                // check if node_id's children are leaf nodes
                int left_child_id = (int)linkage_tree[getRow(node_id)][0];
                int right_child_id = (int)linkage_tree[getRow(node_id)][1];

                if (sub_tree.contains(left_child_id)) {

                    TreeNode P, L, R;
                    
                    if (treeMap.containsKey(left_child_id)) {
                        L = treeMap.get(left_child_id);
                    } else {
                        L = new TreeNode(left_child_id);
                        if (isLeaf(left_child_id)) {
                            L.markRealLeafNode();
                        }
                    }
                    
                    if (treeMap.containsKey(right_child_id)) {
                        R = treeMap.get(right_child_id);
                    } else {
                        R = new TreeNode(right_child_id);
                        if (isLeaf(right_child_id)) {
                            R.markRealLeafNode();
                        }
                    }

                    if (treeMap.containsKey(node_id)) {
                        P = treeMap.get(node_id);
                        P.addChildren(L, R, linkage_tree[getRow(node_id)][2]);
                    } else {
                        P = new TreeNode (node_id);
                        P.addChildren(L, R, linkage_tree[getRow(node_id)][2]);
                        if (isLeaf(node_id)) {
                            P.markRealLeafNode();
                        }
                    }

                    treeMap.put(node_id, P);
                    treeMap.put(left_child_id, L);
                    treeMap.put(right_child_id, R);

                } else {

                    if (!treeMap.containsKey(node_id)) {
                        TreeNode P = new TreeNode (node_id);
                        if (isLeaf(node_id)) {
                            P.markRealLeafNode();
                        }
                        treeMap.put(node_id, P);
                    }

                }
            }
        }
        
        // for all nodes that are subTreeLeaves but not realTreeLeaves add left and right children
        Iterator iterator = treeMap.keySet().iterator(); 
        while (iterator.hasNext()) {
            
            int node_id = (Integer)iterator.next();
            TreeNode N = treeMap.get(node_id);
            
            if (N.isSubTreeLeaf && !N.isRealLeaf) {
                
                // check if node_id's children are leaf nodes
                int left_child_id = (int)linkage_tree[getRow(node_id)][0];
                int right_child_id = (int)linkage_tree[getRow(node_id)][1];
                
                TreeNode L = new TreeNode(left_child_id);
                if (isLeaf(left_child_id)) {
                    L.markRealLeafNode();
                }
                
                TreeNode R = new TreeNode(right_child_id);
                if (isLeaf(right_child_id)) {
                    L.markRealLeafNode();
                }
                
                N.addChildren(L, R, linkage_tree[getRow(node_id)][2]);
                
                // reset isSubTreeLeaf
                N.isSubTreeLeaf = true;
            }
        }
        
        return treeMap;
    }
    
    public void computeEdgeCoordinatesPostOrder (HashMap <Integer, TreeNode> treeMap, TreeNode N) {
        
        TreeNode left_child = N.leftChild;
        TreeNode right_child = N.rightChild;
       
        if (N.isSubTreeLeaf) {

            N.x = 0;
            if (isLeaf(N.nodeID)) {
                N.y = leafPositionMap.get(N.nodeID);
                N.start = leafPositionMap.get(N.nodeID);
                N.end = N.start;
            } else {
                N.start = node_start_end[getRow(N.nodeID)][0];
                N.end = node_start_end[getRow(N.nodeID)][1];
                N.y = (N.start + N.end) / 2.0;
            }

            treeMap.put(N.nodeID, N);
            return;
        }

        computeEdgeCoordinatesPostOrder(treeMap, left_child);
        computeEdgeCoordinatesPostOrder(treeMap, right_child);
        N.mergeDecendants();
        
        N.p0_x = left_child.x;
        N.p0_y = left_child.y;
        
        N.p3_x = right_child.x;
        N.p3_y = right_child.y;
        
        N.p1_x = N.height;
        N.p1_y = N.p0_y;
        
        N.p2_x = N.height;
        N.p2_y = N.p3_y;
        
        N.x = N.p2_x;
        N.y = (N.p2_y + N.p1_y)/2.0;
        
        N.start = node_start_end[getRow(N.nodeID)][0];
        N.end = node_start_end[getRow(N.nodeID)][1];
        
        treeMap.put(N.nodeID, N);
    }
    
    public void rescaleEdgeCoordinates (HashMap <Integer, TreeNode> treeMap, double image_height, double image_width) {
        
        double min_x = Double.POSITIVE_INFINITY;
        double min_y = Double.POSITIVE_INFINITY;
        double max_x = Double.NEGATIVE_INFINITY;
        double max_y = Double.NEGATIVE_INFINITY;
        int start = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;
        
        TreeNode leftmostNode = null;
        TreeNode rightmostNode = null;
        
        Iterator iterator = treeMap.keySet().iterator(); 
        while (iterator.hasNext()){
            
            int node_id = (Integer)iterator.next();
            TreeNode N = treeMap.get(node_id);
            
            if (N.isSubTreeLeaf) {
            
                if (N.y < min_y) {
                    min_y = N.y;
                    leftmostNode = N;
                }
                if (N.y > max_y) {
                    max_y = N.y;
                    rightmostNode = N;
                }
                
                if (N.start < start) {
                    start = N.start;
                }
                if (N.end > end) {
                    end = N.end;
                }
                
            }   else    {
                
                if (N.p0_x < min_x) {
                    min_x = N.p0_x;
                }
                if (N.p1_x < min_x) {
                    min_x = N.p1_x;
                }
                if (N.p3_x < min_x) {
                    min_x = N.p3_x;
                }

                if (N.p0_x > max_x) {
                    max_x = N.p0_x;
                }
                if (N.p1_x > max_x) {
                    max_x = N.p1_x;
                }
                if (N.p3_x > max_x) {
                    max_x = N.p3_x;
                }

                if (N.p2_y < min_y) {
                    min_y = N.p2_y;
                    leftmostNode = N;
                }
                if (N.p1_y < min_y) {
                    min_y = N.p1_y;
                    leftmostNode = N;
                }

                if (N.p2_y > max_y) {
                    max_y = N.p2_y;
                }
                if (N.p1_y > max_y) {
                    max_y = N.p1_y;
                }
            }
        }
    
        double top_buffer, bottom_buffer;
        if (leftmostNode.isRealLeaf) {
            top_buffer = 0.5;
        } else {
            top_buffer = 0;
        }
        
        /*
        if (rightmostNode.isRealLeaf) {
            bottom_buffer = 0.5;
        } else {
            bottom_buffer = (rightmostNode.end - rightmostNode.start)/2;
        }
        */
        
        //double rowHeight = image_height/((end-start)+1);
        double x_scale_factor = image_width/(max_x-min_x);
        //double y_scale_factor = (image_height - (top_buffer + bottom_buffer)*rowHeight)/(end-start);
        double y_scale_factor = image_height/((end-start)+1);
        
        iterator = treeMap.keySet().iterator(); 
        while (iterator.hasNext()){
            
            int node_id = (Integer)iterator.next();
            TreeNode N = treeMap.get(node_id);
            
            N.p0_x = image_width - (N.p0_x - min_x)*x_scale_factor;
            N.p0_y = (N.p0_y - start + top_buffer)*y_scale_factor;
            
            N.p1_x = image_width - (N.p1_x - min_x)*x_scale_factor;
            N.p1_y = (N.p1_y - start + top_buffer)*y_scale_factor;

            N.p2_x = image_width - (N.p2_x - min_x)*x_scale_factor;
            N.p2_y = (N.p2_y - start + top_buffer)*y_scale_factor;
            
            N.p3_x = image_width - (N.p3_x - min_x)*x_scale_factor;
            N.p3_y = (N.p3_y - start + top_buffer)*y_scale_factor;

        }
        
    }
    
}

