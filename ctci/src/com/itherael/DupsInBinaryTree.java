package com.itherael;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import com.itherael.types.TreeNode;

public class DupsInBinaryTree {
    
    @Test
    public void testDups() {
        TreeNode n = new TreeNode("A");
        n.left = new TreeNode("B");
        n.right = new TreeNode("C");
        n.left.left = new TreeNode("X");
        n.left.right = new TreeNode("Y");
        n.right.left = new TreeNode("D");
        n.right.right = new TreeNode("B");
        n.right.right.left = new TreeNode("X");
        n.right.right.right = new TreeNode("Y");
        
        assertThat(dups(n), equalTo(3)); // 3, since the larger tree matches, but so do the single dup nodes too
    }
    
    private int dups(TreeNode root) {
        if (root == null) return 0;
        
        // calculate signatures for all subtrees
        HashMap<TreeNode, Double> sigMap = new HashMap<TreeNode, Double>();
        calcSignatures(root, sigMap);
        return checkDups(sigMap);
    }
    
    private Double calcSignatures(TreeNode root, Map<TreeNode, Double> sigMap) {
        if(root == null) return 0D;
        Double leftSig = calcSignatures(root.left, sigMap);
        Double rightSig = calcSignatures(root.right, sigMap);
        Double sig = leftSig * Math.pow(31, 2) + rightSig * Math.pow(31, 1) + root.val.charAt(0) * Math.pow(31, 0);
        sigMap.put(root, sig);
        return sig;
    }
    
    private int checkDups(Map<TreeNode, Double> sigMap) {
        HashMap<Double, List<TreeNode>> seen = new HashMap<Double, List<TreeNode>>();
        int dups = 0;
        for(Map.Entry<TreeNode, Double> e : sigMap.entrySet()) {
            if(seen.containsKey(e.getValue())) { // sigs match, potential dup - check to be sure (even though hash collision really unlikely)
                System.out.println("saw same sig, " + e.getValue() + ", list len " + seen.get(e.getValue()).size());
                for(TreeNode n : seen.get(e.getValue())) {
                    if(sameSubtree(e.getKey(), n)) dups++;
                }
            } else {
                ArrayList<TreeNode> l = new ArrayList<TreeNode>();
                l.add(e.getKey());
                seen.put(e.getValue(), l);
            }
        }
        return dups;
    }
    
    private boolean sameSubtree(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) return true;
        if ((n1 != null && n2 == null) || (n1 == null && n2 != null)) return false;
        return n1.val.equals(n2.val) && sameSubtree(n1.left, n2.left) && sameSubtree(n1.right, n2.right);
    }
}
