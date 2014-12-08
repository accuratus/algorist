package com.itherael;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.itherael.types.GraphNode;
import com.itherael.types.TreeNode;

public class Ch4_TreesAndGraphs {
    
    @Test
    public void testIsBalanced() {
        TreeNode n = new TreeNode("");
        n.left = new TreeNode("");
        n.right = new TreeNode("");
        n.left.left = new TreeNode("");
        n.left.left.left = new TreeNode("");        
        assertThat(isBalanced(n), equalTo(-1));
        
        n = new TreeNode("");
        n.left = new TreeNode("");
        n.right = new TreeNode("");
        n.left.left = new TreeNode("");
        assertThat(isBalanced(n), equalTo(3));
    }
    
    @Test
    public void testHasRoute() {
        GraphNode n = new GraphNode("A");
        n.adjacent = new ArrayList<GraphNode>();
        GraphNode na1 = new GraphNode("B");
        GraphNode na2 = new GraphNode("C");
        n.adjacent.add(na1);
        n.adjacent.add(na2);

        na1.adjacent = new ArrayList<GraphNode>();
        GraphNode ba1 = new GraphNode("D");
        na1.adjacent.add(ba1);
        
        na2.adjacent = new ArrayList<GraphNode>();
        na2.adjacent.add(ba1);
        
        ba1.adjacent = new ArrayList<GraphNode>();
        GraphNode da1 = new GraphNode("E");
        ba1.adjacent.add(da1);
        
        assertTrue(hasRoute(n, ba1));
        assertFalse(hasRoute(n, new GraphNode("F")));
    }
    
    @Test
    public void testMinimalHeightBST() {
        int[] ar = new int[] { 1, 2, 3, 4, 5 };
        TreeNode n = minimalHeightBST(ar, 0, ar.length-1);
        assertThat(n.val, equalTo("3"));
        assertThat(n.left.val, equalTo("1"));
        assertThat(n.left.right.val, equalTo("2"));
        assertThat(n.left.right.left, equalTo(null));
        assertThat(n.left.right.right, equalTo(null));
        assertThat(n.right.val, equalTo("4"));
        assertThat(n.right.right.val, equalTo("5"));
        assertThat(n.right.right.left, equalTo(null));
        assertThat(n.right.right.right, equalTo(null));
    }
    
    @Test
    public void testLlEveryLevel() {
        TreeNode n = new TreeNode("A");
        n.left = new TreeNode("B");
        n.right = new TreeNode("C");
        n.left.left = new TreeNode("D");
        n.left.right = new TreeNode("E");
        n.right.right = new TreeNode("F");
        
        List<LinkedList<TreeNode>> r = llEveryLevel(n);
        assertThat(r.size(), equalTo(3));
        LinkedList<TreeNode> ll = r.get(0);
        assertThat(ll.poll().val, equalTo("A"));
        ll = r.get(1);
        assertThat(ll.poll().val, equalTo("B"));
        assertThat(ll.poll().val, equalTo("C"));
        ll = r.get(2);
        assertThat(ll.poll().val, equalTo("D"));
        assertThat(ll.poll().val, equalTo("E"));
        assertThat(ll.poll().val, equalTo("F"));
    }
    
    @Test
    public void testIsBST() {
        TreeNode n = new TreeNode("5");
        n.left = new TreeNode("3");
        n.right = new TreeNode("8");
        n.left.left = new TreeNode("1");
        n.left.right = new TreeNode("6");
        n.right.left = new TreeNode("7");
        n.right.right = new TreeNode("10");
        
        int[] res = isBST(n);
        assertThat(res[0], equalTo(0));
        assertThat(res[1], equalTo(1));
        assertThat(res[2], equalTo(10));
        
        assertFalse(isBSTInorder(n, new int[] { Integer.MIN_VALUE }));
        
        n.left.right = new TreeNode("4");
        res = isBST(n);
        assertThat(res[0], equalTo(1));
        assertThat(res[1], equalTo(1));
        assertThat(res[2], equalTo(10));
        
        assertTrue(isBSTInorder(n, new int[] { Integer.MIN_VALUE }));
    }
    
    private int isBalanced(TreeNode n) {
        if (n == null) return 0;
        int l = isBalanced(n.left);
        int r = isBalanced(n.right);
        if(l < 0 || r < 0) return -1;
        int mh = Math.max(l, r) + 1;
        return (Math.abs(l - r) < 2) ? mh : -1;
    }
    
    private boolean hasRoute(GraphNode n, GraphNode target) {
        if(n == null || target == null || n.visited) return false;
        if(n.equals(target)) return true;
        n.visited = true;
        // explore adjacent nodes
        boolean found = false;
        for(GraphNode adj : n.adjacent) {
            if(hasRoute(adj, target)) found = true;
        }
        return found;
    }
    
    private TreeNode minimalHeightBST(int[] ar, int l, int r) {
        if(l > r) return null;
        int idx = (l + r) / 2;
        TreeNode n = new TreeNode(ar[idx]+"");
        n.left = minimalHeightBST(ar, l, idx-1);
        n.right = minimalHeightBST(ar, idx+1, r);
        return n;
    }
    
    private List<LinkedList<TreeNode>> llEveryLevel(TreeNode root) {
        List<LinkedList<TreeNode>> l = new ArrayList<LinkedList<TreeNode>>();
        if(root == null) return l;
        LinkedList<TreeNode> curLevel = new LinkedList<TreeNode>();
        LinkedList<TreeNode> nextLevel;
        curLevel.add(root); // start with root
        
        // keep going until no more nodes in current level
        while(!curLevel.isEmpty()) {
            nextLevel = new LinkedList<TreeNode>();
            for(TreeNode n : curLevel) {
                if (n.left != null) nextLevel.add(n.left);
                if (n.right != null) nextLevel.add(n.right);
            }
            l.add(curLevel);
            curLevel = nextLevel;
            System.out.println();
        }
        
        return l;
    }
    
    // bottom-up approach. each node returns what it knows about min, max, and ordered up to this point.
    // ar: 0th is ordered, 1st is min, 2nd is max 
    private int[] isBST(TreeNode n) {
        if (n == null) return new int[] {1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] l = isBST(n.left);
        int[] r = isBST(n.right);
        int ordered = 1;
        if(l[0] == 0 || r[0] == 0) ordered = 0;
        // check this node is ordered
        int v = Integer.valueOf(n.val);
        if(v < l[2] || v > r[1]) ordered = 0;
        return new int[] { ordered, Math.min(v, l[1]), Math.max(v, r[2]) };
    }
    
    private boolean isBSTInorder(TreeNode n, int[] last) {
        if(n == null) return true;
        if( !isBSTInorder(n.left, last) ) return false;
        int v = Integer.valueOf(n.val);
        if(v < last[0]) return false;
        last[0] = v;
        if( !isBSTInorder(n.right, last) ) return false;
        return true;
    }
    
    /*
    private TreeNode inorderSucc(TreeNode n) {
        if (n == null) return null;
        // if theres a right, return leftmost child of right subtree.
        if (n.right != null) {
            TreeNode lm = n.right;
            while(lm.left != null) {
                lm = lm.left;
            }
            return lm;
        }
        // if there isn't, go up, until you find a parent that hasn't "already been part of the traversal" - aka, parent's right is me.
        String v = n.val;
        while(n.parent != null) {
            v = n.val;
            n = n.parent;
            if (n.left.val.equals(v)) return n;
        }
        
        return null;
    }
    */
    
    private void firstCommonAncestor(TreeNode n, TreeNode p, TreeNode q, TreeNode ancestor) {
        if(n == null) return;
        int[] v = reachable(n, p, q);
        if (v[0] == 1 && v[1] == 1) {
            ancestor = n; // both nodes were reachable from this point
            firstCommonAncestor(n.left, p, q, ancestor);
            firstCommonAncestor(n.right, p, q, ancestor);
        }
        
    }
    
    private int[] reachable(TreeNode n, TreeNode p, TreeNode q) {
        if(n == null) return new int[] {0,0};
        int[] l = reachable(n.left, p, q);
        int[] r = reachable(n.right, p, q);
        int[] v = new int[2];
        if(l[0] == 1 || r[0] == 1) v[0] = 1;
        if(l[1] == 1 || r[1] == 1) v[1] = 1;
        if(n.equals(p)) v[0] = 1;
        if(n.equals(q)) v[1] = 1;
        return v;
    }
}
