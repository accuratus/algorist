package com.itherael;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class Ch1_ArraysAndStrings {
    
    public static void main(String[] args) {
        //String[][] ar = { {"A", "B", "C", "D"},
        //                  {"E", "F", "G", "H"},
        //                  {"I", "J", "K", "L"},
         //                 {"M", "N", "O", "P"} };

        String[][] ar = { {"A", "B", "C"},
                           {"E", "F", "G"},
                           {"I", "J", "K"} };        
        
        rotateArray(ar);
        for(int i=0; i<ar.length; i++) {
            for(int j=0; j<ar[i].length; j++) {
                System.out.print(ar[i][j] + " ");
            }
            System.out.println();
        }
    }
    

    @Test
    public void testUniqueChars() {
        assertTrue(uniqueChars("abcdef"));
        assertFalse(uniqueChars("abcddef"));
    }
    
    @Test
    public void testReverse() {
        assertThat(reverse("hello".toCharArray()), equalTo("olleh"));
        assertThat(reverse("hello!".toCharArray()), equalTo("!olleh"));
    }
    
    @Test
    public void testPerm() {
        assertTrue(isPerm("", ""));
        assertTrue(isPerm("hello", "lolhe"));
        assertFalse(isPerm("hello", "world"));
    }
    
    @Test
    public void testReplaceSpaces() {
        assertThat(replaceSpaces("Hello World  ".toCharArray(), 11), equalTo("Hello%20World"));
        assertThat(replaceSpaces("      ".toCharArray(), 2), equalTo("%20%20"));
    }
    
    // 1.1
    private static boolean uniqueChars(String s) {
        boolean[] chars = new boolean[256]; // assume ascii
        for(int i=0; i<s.length(); i++) {
            if(chars[s.charAt(i) - 'a']) {
                return false;
            }
            chars[s.charAt(i) - 'a'] = true;
        }
        return true;
    }
    
    // 1.2
    private static String reverse(char[] ar) {
        if(ar == null) return null;
        int l = 0;
        int r = ar.length -1;
        while (l < r) {
            char tmp = ar[r];
            ar[r] = ar[l];
            ar[l] = tmp;
            l++;
            r--;
        }
        return new String(ar);
    }
    
    // 1.3
    private static boolean isPerm(String s1, String s2) {
        int[] record = new int[256]; // assume ascii
        if (s1.length() != s2.length()) return false;
        
        for(int i=0; i<s1.length(); i++) {
            record[s1.charAt(i)]++;
        }
        
        for(int i=0; i<s2.length(); i++) {
            if(--record[s2.charAt(i)] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    // 1.4
    // 'Hello World  ' (extra 2 chars for every space)
    private static String replaceSpaces(char[] ar, int trueLength) {
        if(ar == null) return null;
        // find actual word end idx
        int dest = ar.length-1;
        for(int i = trueLength-1; i>=0; i--) {
            if(ar[i] == ' ') {
                ar[dest--] = '0';
                ar[dest--] = '2';
                ar[dest--] = '%';
            } else {
                ar[dest--] = ar[i];
            }
        }
        
        return new String(ar);
    }
    
    // 1.6
    private static void rotateArray(String[][] ar) {
        if(ar == null) return;
        if(ar.length != ar[0].length) return; // not NxN
        for(int layer=0; layer<ar.length/2; layer++) {
            for(int i=layer; i<ar.length-layer-1; i++) {
                int edge = ar.length-layer-1;
                String tmp = ar[i][edge];
                // top -> right
                ar[i][edge] = ar[layer][i];
                // left -> top
                ar[layer][i] = ar[edge-i][layer];
                // bottom -> left
                ar[edge-i][layer] = ar[edge][edge-i];
                // right -> bottom
                ar[edge][edge-i] = tmp;
            }
        }
    }
}
