import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Osmos {

    public static void main(String args[]) throws Exception {
        FileInputStream fis = new FileInputStream("resources/OsmosInputLarge.txt");
        Scanner scanner = new Scanner(fis);
        int testcase = 1;
        
        scanner.nextLine(); //ignore num input
        while(scanner.hasNextLine()) {
            int mote = Integer.valueOf(scanner.nextLine().split(" ")[0]);
            List<Integer> motelist = new ArrayList<Integer>();
            Map<Integer, Map<Integer, Integer>> memos = new HashMap<Integer, Map<Integer, Integer>>();
            for(String s : scanner.nextLine().split(" ")) motelist.add(Integer.valueOf(s));
            Collections.sort(motelist);
            System.out.println(String.format("Case #%d: %d", testcase++, consume(mote, 0, motelist, memos)));
        }
    }

    private static int consume(int mote, int lpos, List<Integer> motelist, Map<Integer, Map<Integer, Integer>> memos) {

        // consume as much as possible
        while (lpos <= motelist.size()-1 && mote > motelist.get(lpos)) {
            mote += motelist.get(lpos);
            lpos++;
        }
        
        if (mote == 1) return motelist.size();
        if (lpos > motelist.size()-1) return 0; //no more motes
        
        int addresult;
        if (memos.get(mote+mote-1) == null || memos.get(mote+mote-1).get(lpos) == null) {
            addresult = consume(mote+mote-1, lpos, motelist, memos);
            // need to create map first time through
            if (memos.get(mote+mote-1) == null) memos.put(mote+mote-1, new HashMap<Integer, Integer>());
            memos.get(mote+mote-1).put(lpos, addresult);
        } else {
            addresult = memos.get(mote+mote-1).get(lpos);
        }
        
        int removeresult;
        if (memos.get(mote) == null || memos.get(mote).get(lpos+1) == null) {
            removeresult = consume(mote, lpos+1, motelist, memos);
            // need to create map first time through
            if (memos.get(mote) == null) memos.put(mote, new HashMap<Integer, Integer>());
            memos.get(mote).put(lpos+1, removeresult);
        } else {
            removeresult = memos.get(mote).get(lpos+1);
        }
        
        //System.out.println("addres " + addres + ", removeres " + removeres);
        return 1 + Math.min(addresult, removeresult);

    }
}