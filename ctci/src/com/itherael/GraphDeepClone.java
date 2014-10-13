package com.itherael;

import java.util.LinkedList;

public class GraphDeepClone {

    private static class Node {
        int value;
        Node[] children;
        public Node(int value) { this.value = value; }
    }

    public static void main(String[] args) {
        Node parent = new Node(1);
        Node p1 = new Node(2);
        Node p2 = new Node(3);
        Node[] pc = new Node[2];
        pc[0] = p1;
        pc[1] = p2;
        parent.children = pc;
        Node q1 = new Node(4);
        Node q2 = new Node(5);
        Node[] qc = new Node[2];
        qc[0] = q1;
        qc[1] = q2;
        pc[0].children = qc;

        printGraph(parent);
        Node newr = deepClone(parent);
        System.out.println();
        printGraph(newr);

    }

    private static void printGraph(Node root) {
        LinkedList<Node> q = new LinkedList<Node>();
        q.add(root);
        while(!q.isEmpty()) {
            Node n = q.remove();
            System.out.println(n + " has value " + n.value);
            if(n.children != null) {
                for(int i=0; i<n.children.length; i++) {
                    q.add(n.children[i]);
                }
            }
        }
    }

    private static Node deepClone(Node root) {
        if (root == null) return null;
        Node newRoot = new Node(root.value);
        LinkedList<Node> origq = new LinkedList<Node>(); // for traversing
        LinkedList<Node> newq = new LinkedList<Node>(); // for building the new graph
        origq.add(root);
        newq.add(newRoot);
        while(!origq.isEmpty()) {
            Node cur = origq.remove();
            Node newcur = newq.remove();

            if(cur.children != null) {
                // allocate children array for new graph node
                newcur.children = new Node[cur.children.length];

                // run through it's children
                for(int i=0; i<cur.children.length; i++) {
                    // create child and link it in the new graph
                    Node c = new Node(cur.children[i].value);
                    newcur.children[i] = c;

                    // add to traversal queue & new graph ptr queue
                    origq.add(cur.children[i]);
                    newq.add(newcur.children[i]);
                }
            }
        }
        return newRoot;
    }
}
