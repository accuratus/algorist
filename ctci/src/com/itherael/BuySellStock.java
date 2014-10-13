package com.itherael;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuySellStock {

    public static void main(String[] args) {
        int[] prices = new int[] {1, 3, 5, 2, 10, 5, 20};
        
        HashMap<Integer, HashMap<Integer, Integer>> memo = new HashMap<Integer, HashMap<Integer, Integer>>();
        System.out.println(maxProfit(prices, 0, 1, memo)); // should be 1->20, max $19
        System.out.println();
        memo.clear();
        System.out.println(maxProfit(prices, 0, 2, memo)); // should be 1->10, 5->20, max $24
        System.out.println();
        memo.clear();
        System.out.println(maxProfit(prices, 0, 3, memo)); // should be 1->5, 2->10, 5->20, max $27
        System.out.println();
    }
    
    private static int maxProfit(int[] ar, int start, int k, HashMap<Integer, HashMap<Integer, Integer>> memo) {
        if (k == 0) return 0;
        List<Integer> l = new ArrayList<Integer>();
        for(int b=start; b<ar.length-1; b++) {
            for(int s=b+1; s<ar.length; s++) {
                // if we made this particular transaction (b, s), find max profit 'downstream' having made this decision
                if(!memo.containsKey(s+1)) memo.put(s+1, new HashMap<Integer, Integer>()); // init nested map
                int maxw = memo.get(s+1).containsKey(k-1) ? memo.get(s+1).get(k-1) : maxProfit(ar, s+1, k-1, memo);
                if(!memo.get(s+1).containsKey(k-1)) memo.get(s+1).put(k-1, maxw);
                l.add((ar[s]-ar[b]) + memo.get(s+1).get(k-1));
            }
        }
        
        // maximize profit $
        return l.isEmpty() ? 0 : Collections.max(l);
    }
    
}
