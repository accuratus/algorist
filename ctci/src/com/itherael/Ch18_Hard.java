package com.itherael;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

public class Ch18_Hard {

    @Test
    public void testRandomM() {
        int[] n = new int[] { 1, 2, 3, 4, 5, 6 };
        int[] res = randomM(n, 4);
        for(int i=0; i<res.length; i++) {
            System.out.print(res[i] + " ");
        }
    }
    
    // 18.3
    private int[] randomM(int[] n, int m) {
        int[] res = new int[m];
        for(int i=0; i<m; i++) {
            Random r = new Random();
            int rnd = r.nextInt(n.length - i) + i;
            res[i] = n[rnd];
            n[rnd] = n[i];
        }
        return res;
    }
    
    // 18.4
    private int countNumberOf2s(int n) {
        // find number of 2s digit-by-digit
        int count = 0;
        int len = String.valueOf(n).length();
        for(int digit=0; digit<len; digit++) {
            count += numberOf2sAtDigit(n, digit);
        }
        
        return count;
    }
    
    private int numberOf2sAtDigit(int number, int digit) {
        
        int powerOfTen = (int) Math.pow(10, digit);
        int nextPowerOfTen = powerOfTen * 10;
        int digitval = (number / powerOfTen) % 10;
        
        int roundDown = number - (number % nextPowerOfTen);
        int roundUp = roundDown + nextPowerOfTen;
        
        int right = number % powerOfTen;
        
        if(digitval < 2) {
            return roundDown / 10;
        } else if(digitval == 2) {
            return (roundDown / 10) + right + 1;
        } else {
            return roundUp / 10;
        }
    }
}
