package com.itherael;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TreesGraphs {
    
    // (#4.1) O(N) time, O(log N) space 
    public int isBalanced(Node n) {
        if (n == null) { return 0; }
        
        int leftHeight = isBalanced(n.left);
        int rightHeight = isBalanced(n.right);
        if (leftHeight < 0 || rightHeight < 0) { return -1; }
        int maxHeight = 1+Math.max(leftHeight, rightHeight); 
        
        // return -1 if imbalanced, otherwise just return max height
        return (Math.abs(leftHeight-rightHeight) > 1) ? -1 : maxHeight;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Node buildBST(int[] ar, int r, int l) {
        if ((ar.length < 1) || (r > l)) { return null; }
        int mid = (r+l)/2;
        Node n = new Node(ar[mid]);
        n.left = buildBST(ar, r, mid-1);
        n.right = buildBST(ar, mid+1, l);
        return n;
    }
    
    public class Node<T extends Comparable<?>> {
        Node<T> left, right;
        T data;
        public Node(T data) { this.data = data; }
    }
    
    @Test
    public void testIsBalanced() {
        Node root = new Node(0);
        assertEquals(1, isBalanced(root)); // balanced
        
        root.left = new Node(0);
        root.right = new Node(0);
        assertEquals(2, isBalanced(root)); // balanced
        
        root.left.left = new Node(0);
        assertEquals(3, isBalanced(root)); // left side larger by 1, balanced
        
        root.left.left.left = new Node(0); // left side larger by 2, unbalanced
        assertEquals(-1, isBalanced(root));
        
        root.left.right = new Node(0);
        root.right.right = new Node(0);
        assertEquals(4, isBalanced(root)); // left side larger by 1, (right side added 1), balanced
        
        root.right.right.right = new Node(0);
        assertEquals(-1, isBalanced(root)); // right side too long, unbalanced
    }
    
    @Test
    public void testBuildBST() {
        int[] ar = new int[] {1};
        Node root = buildBST(ar, 0, ar.length-1);
        assertEquals(1, root.data);
        
        ar = new int[] {1,2};
        root = buildBST(ar, 0, ar.length-1);
        assertEquals(1, root.data);
        assertEquals(2, root.right.data);
        //  1   
        //   \ 
        //    2 
        
        ar = new int[] {1, 3, 4, 5, 7, 10};
        root = buildBST(ar, 0, ar.length-1);
        assertEquals(4, root.data);
        assertEquals(3, root.left.right.data);
        assertEquals(10, root.right.right.data);
        //    4       
        //   / \   
        //  /   \  
        // 1     7   
        //  \   / \ 
        //   3 5  10
        
    }
    
}
