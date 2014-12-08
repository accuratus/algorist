package com.itherael;

import java.util.Stack;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class Ch2_LinkedLists {

    @Test
    public void testRemoveDups() {
        Node a = new Node("A");
        removeDups(a);
        assertThat(a.val, equalTo("A"));
        assertThat(a.next, equalTo(null));
        
        Node n = llFromString("ABAC");
        
        removeDups(n);
        assertThat(n.val, equalTo("A"));
        assertThat(n.next.val, equalTo("B"));
        assertThat(n.next.next.val, equalTo("C"));
        assertThat(n.next.next.next, equalTo(null));
    }
    
    @Test
    public void testKthLast() {
        Node n = llFromString("ABCD");
        
        assertThat(kthLast(n, 1).val, equalTo("D"));
        assertThat(kthLast(n, 2).val, equalTo("C"));
        assertThat(kthLast(n, 3).val, equalTo("B"));
        assertThat(kthLast(n, 4).val, equalTo("A"));
        assertThat(kthLast(n, 5), equalTo(null));
    }
    
    @Test
    public void testDeleteMiddle() {
        Node n = llFromString("ABCD");
        
        deleteMiddle(n, n.next.next);
        assertThat(n.val, equalTo("A"));
        assertThat(n.next.val, equalTo("B"));
        assertThat(n.next.next.val, equalTo("D"));
        assertThat(n.next.next.next, equalTo(null));
    }
    
    @Test
    public void testPartition() {
        Node n = llFromString("LZCAB");
        
        Node h = partition(n, "C");
        
        assertThat(h.val, equalTo("A"));
        assertThat(h.next.val, equalTo("B"));
        assertThat(h.next.next.val, equalTo("L"));
        assertThat(h.next.next.next.val, equalTo("Z"));
        assertThat(h.next.next.next.next.val, equalTo("C"));
        assertThat(h.next.next.next.next.next, equalTo(null));
        
    }
    
    @Test
    public void testSum() {
        Node a = llFromString("716");
        Node b = llFromString("592");
        
        Node r = sum(a, b);
        assertThat(r.val, equalTo("2"));
        assertThat(r.next.val, equalTo("1"));
        assertThat(r.next.next.val, equalTo("9"));
        assertThat(r.next.next.next, equalTo(null));

        a = llFromString("069");
        b = llFromString("15");
        
        r = sum(a, b);
        assertThat(r.val, equalTo("1"));
        assertThat(r.next.val, equalTo("1"));
        assertThat(r.next.next.val, equalTo("0"));
        assertThat(r.next.next.next.val, equalTo("1"));
        assertThat(r.next.next.next.next, equalTo(null));        
    }
    
    @Test
    public void testIsPalindrome() {
        assertTrue(isPalindrome(llFromString("racecar")));
        assertTrue(isPalindrome(llFromString("abba")));
        assertFalse(isPalindrome(llFromString("hello")));
    }
    
    // 2.1
    private void removeDups(Node n) {
        if(n == null) return;
        
        Node current = n;
        while(current != null) {
            Node runner = current;
            while(runner.next != null) {
                if(runner.next.val.equals(current.val)) {
                    runner.next = runner.next.next;
                }
                runner = runner.next;
            }
            current = current.next;
        }
    }
    
    // 2.2
    private Node kthLast(Node n, int k) {
        if(n == null) return null;
        
        Node a = n;
        Node b = n;
        for(int i=0; i<k-1; i++) {
            if (b.next == null) return null; // in case k is greater than list
            b = b.next;
        }
        while(b.next != null) {
            a = a.next;
            b = b.next;
        }
        return a;
    }
    
    // 2.3
    private void deleteMiddle(Node head, Node target) {
        if (head == null || target == null) return;
        Node a = head;
        Node b = head.next;
        while(b != null) {
            if(b.equals(target)) {
                a.next = b.next;
            }
            a = a.next;
            b = b.next;
        }
    }
    
    // 2.4
    private Node partition(Node head, String s) {
        if (head == null) return null;
        Node lhead = null;
        Node lcur = null;
        Node ghead = null;
        Node gcur = null;
        while(head != null) {
            if(head.val.compareTo(s) < 0) {
                if(lhead == null) {
                    lhead = lcur = head;
                } else {
                    lcur.next = head;
                    lcur = lcur.next;
                }
            } else {
                if(ghead == null) {
                    ghead = gcur = head;
                } else {
                    gcur.next = head;
                    gcur = gcur.next;
                }
            }
            head = head.next;
        }
        
        gcur.next = null; // terminate g list
        
        // put less before greater/equal than
        lcur.next = ghead;
        return lhead;
    }
    
    // 2.5
    public Node sum(Node a, Node b) {
        Node resultHead = null;
        Node resultTail = null;
        int carry = 0;
        while(a != null || b != null) {
            int v = 0;
            if(a != null && b != null) {
                v = Integer.parseInt(a.val) + Integer.parseInt(b.val) + carry;
                a = a.next;
                b = b.next;
            } else if(a == null) {
                v = Integer.parseInt(b.val) + carry;
                b = b.next;
            } else {
                v = Integer.parseInt(a.val) + carry;
                a = a.next;
            }
            
            if(v >= 10) {
                carry = 1;
                v = v % 10;
            } else {
                carry = 0;
            }
            
            Node n = new Node(String.valueOf(v));
            if (resultHead == null) {
                resultHead = resultTail = n;
            } else {
                resultTail.next = n;
                resultTail = resultTail.next;
            }
            
        }
        
        // last carry
        if(carry != 0) {
            resultTail.next = new Node("1");
        }
        
        return resultHead;
    }
    
    // 2.7
    // racecar | abba
    private boolean isPalindrome(Node n) {
        if (n == null) return true;
        if (n.next == null) return true;
        
        // discover length
        int len = 0;
        Node runner = n;
        while(runner != null) {
            len++;
            runner = runner.next;
        }
        
        runner = n;
        Stack<String> s = new Stack<String>();
        for(int i=0; i<len; i++) {
            if (i < (len/2)) { // on left side of center
                s.push(runner.val);
            } else {
                if(! (i == len/2 && len % 2 == 1)) {
                    if (!s.pop().equals(runner.val)) return false;
                }
            }
            runner = runner.next;
        }
        return true;
    }
    
    private Node llFromString(String s) {
        Node head = null;
        Node tail = null;
        
        for(int i=0; i<s.length(); i++) {
            Node n = new Node(s.charAt(i) + "");
            if (head == null) {
                head = tail = n;
            } else {
                tail.next = n;
                tail = tail.next;
            }
        }
        
        return head;
    }
    
    private class Node {
        Node next;
        String val;
        
        public Node(String val) {
            this.val = val;
        }
    }
    
    
    
}
