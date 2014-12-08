package com.itherael;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateByteStream {

    @Test
    public void testIsStreamValid() {
        // 0 0 0 0  0 1 1 1
        // valid (stream just has one byte)
        int[] ar = new int[] { 7 };
        assertTrue(isStreamValid(ar));
        
        // 1 0 0 0  0 0 0 1 
        // invalid (only payload packet)
        ar = new int[] { 129 };
        assertFalse(isStreamValid(ar));
        
        // 1 1 0 0  0 0 0 0
        // 1 0 0 0  1 1 1 1
        // valid 2 total bytes (1 good header, 1 good payload)
        ar = new int[] { 192, 143 };
        assertTrue(isStreamValid(ar));

        // 1 1 0 0  0 0 0 0
        // 1 1 0 0  1 1 1 1
        // invalid 2 total bytes (1 good header, 1 bad payload)
        ar = new int[] { 192, 207 };
        assertFalse(isStreamValid(ar));
        
        // 1 1 0 0  1 0 0 0
        // 1 0 0 0  1 1 1 1
        // invalid 2 total bytes (1 bad header, 1 good payload)
        ar = new int[] { 200, 143 };
        assertFalse(isStreamValid(ar));
        
        // 1 1 1 1  1 1 1 0
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // invalid 3 total bytes (1 good header, 2 good payloads, 4 missing payloads)
        ar = new int[] { 254, 143, 143 };
        assertFalse(isStreamValid(ar));
        
        // 1 1 1 1  1 1 1 0
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // valid 7 total bytes (1 good header, 6 good payloads)
        ar = new int[] { 254, 143, 143, 143, 143, 143, 143 };
        assertTrue(isStreamValid(ar));
        
        // 1 1 1 1  1 1 1 0
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // 0 1 0 0  1 1 1 1
        // 1 0 0 0  1 1 1 1
        // invalid 7 total bytes (1 good header, 5 good payloads, 1 bad payload)
        ar = new int[] { 254, 143, 143, 143, 143, 79, 143 };
        assertFalse(isStreamValid(ar));
    }
    
    private boolean isStreamValid(int[] ar) {
        if (ar == null) return false;
        
        // first byte is header. 
        // the number of 1's preceding a 0 determines how many payload bytes follow
        // note: if 0 payload bytes follow, then the stream is complete (and valid)
        // payload packets must start with [ 1 0 ... ]
        
        // 0 x x x  x x x x = entire stream
        // 1 0 x x  x x x x = payload packet
        
        // 1 1 0 x  x x x x = 2 total bytes (1 header + 1 payload)
        // 1 1 1 0  x x x x = 3 total bytes (1 header + 2 payload)
        // ...
        // 1 1 1 1  1 1 1 0 = 7 total bytes (1 header + 6 payload)
        
        // header begins with 0, stream is complete and valid
        if (((ar[0] >> 7) & 1) == 0) return true;
        
        // examine header, determine how many payload bytes to expect
        int totalBytes = 0;
        boolean seenZeroPad = false;
        for(int i=128; i>=1; i/=2) {
            boolean bit = ((ar[0] & i) == 0) ? false : true;
            if(bit && !seenZeroPad) totalBytes++;
            if(!bit && !seenZeroPad) seenZeroPad = true;
            if(bit && seenZeroPad) return false; // saw a 1 after zero, invalid.
        }

        // if stream is just payload packet (1 0 x x...), consider it invalid
        if (totalBytes == 1) return false;
        
        // ensure that input has correct number of bytes
        if (ar.length != totalBytes) return false;
        
        int payloadBytes = totalBytes - 1; // expect n-1 payload bytes (header takes up one byte)
        
        for(int i=1; i<=payloadBytes; i++) {
            // check that payload packet is valid (1 0 x x ...)
            if (! (((ar[i] & 128) != 0) && ((ar[i] & 64) == 0))) return false;
        }
        
        return true;
    }
    
}
