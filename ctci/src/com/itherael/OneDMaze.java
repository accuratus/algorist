package com.itherael;

public class OneDMaze {

    public static void main(String[] args) {
        int[] ar = {3, 1, 0, 3, 2, 0, 8};
        System.out.println(maze(0, ar));
    }
    
    private static boolean maze(int idx, int[] ar) {
        if(idx >= ar.length-1) return true;
        int spaces = ar[idx];
        boolean foundEnd = false;
        for(int i=1; i<=spaces; i++) {
            if(maze(idx+i, ar)) {
                System.out.println(ar[idx+i]);
                foundEnd = true;
                break;
            }
        }
        return foundEnd;
    }
}
