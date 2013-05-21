import static org.junit.Assert.*;

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
    
    public class Node {
        Node left, right;
        int value;
        public Node(int v) { this.value = v; }
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
    
}
