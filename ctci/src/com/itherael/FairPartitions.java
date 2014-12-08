package com.itherael;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FairPartitions {
    public static void main(String[] args) {
        int[] ar = new int[] { 1, 2, 3, 4, 2, 1 };
        Partition p = fairP(ar, 0, 1);
        System.out.println(p.starts);
        p = fairP(ar, 0, 2);
        System.out.println(p.starts);
        p = fairP(ar, 0, 3);
        System.out.println(p.starts);
    }
    
    private static Partition fairP(int[] ar, int start, int k) {
        if (k == 0) return new Partition(0, new ArrayList<Integer>());
        if (k == 1) {
            List<Integer> l = Arrays.asList(start);
            return new Partition(sum(ar, start, ar.length-1), l);
        }
        Partition best = null;
        for(int s=start; s<ar.length-1; s++) { // could be better - since we know how many k partitions we still need to generate at this point
            int pval = sum(ar, start, s);
            Partition bestp = fairP(ar, s+1, k-1); // best partitions, having chosen this partition p
            if(best == null || Math.max(pval, bestp.value) < best.value) { // minimize max partition
                ArrayList<Integer> l = new ArrayList<Integer>();
                l.addAll(bestp.starts);
                l.add(0, s);
                best = new Partition(pval + bestp.value, l);
            }
        }
        return best;
    }
        
    private static class Partition {
        int value;
        List<Integer> starts;
        
        public Partition(int value, List<Integer> starts) {
            this.value = value;
            this.starts = starts;
        }
    }
    
    // inclusive
    private static int sum(int[] ar, int start, int end) {
        int v = 0;
        for(int i=start; i<=end; i++) {
            v += ar[i];
        }
        return v;
    }
}
