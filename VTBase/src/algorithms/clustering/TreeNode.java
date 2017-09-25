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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TreeNode {
    
    public int nodeID;
    public int leftChildID;
    public int rightChildID;
    public TreeNode leftChild;
    public TreeNode rightChild;
    public TreeNode parent;
    public TreeNode sister;
    public ArrayList <Integer> descendants;
    public boolean isRealLeaf;
    public boolean isSubTreeLeaf;
    public boolean isRoot;
    public double height;
    public double x, y;
    public double p0_x, p0_y, p1_x, p1_y, p2_x, p2_y, p3_x, p3_y;
    public int start, end;
    
    public TreeNode (int nodeID) {
        this.nodeID = nodeID;
        this.height = 0;
        isSubTreeLeaf = true;
        isRealLeaf = false;
        isRoot = true;
        descendants = new ArrayList <Integer> ();
    }
    
    public void addChildren (TreeNode leftChild, TreeNode rightChild, double height) {
        
        this.isSubTreeLeaf = false;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.leftChildID = leftChild.nodeID;
        this.rightChildID = rightChild.nodeID;
        this.height = height;
        
        leftChild.isRoot = false;
        rightChild.isRoot = false;
        leftChild.parent = this;
        rightChild.parent = this;
        leftChild.sister = rightChild;
        rightChild.sister = leftChild;
        
    }
    
    public void mergeDecendants () {
        
        descendants.clear();
        if (leftChild.isSubTreeLeaf || leftChild.isRealLeaf) {
            descendants.add(leftChild.nodeID);
        } else {
            descendants.addAll(leftChild.descendants);
            descendants.add(leftChild.nodeID);
        }
        if (rightChild.isSubTreeLeaf || leftChild.isRealLeaf) {
            descendants.add(rightChild.nodeID);
        } else {
            descendants.addAll(rightChild.descendants);
            descendants.add(rightChild.nodeID);
        }
        
    }
    
    public void markRealLeafNode () {
        this.isRealLeaf = true;
    }
    
}
