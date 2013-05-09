import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Osmos {

    public static void main(String args[]) throws Exception {
        FileInputStream fis = new FileInputStream("src/OsmosInput.txt");
        Scanner scanner = new Scanner(fis);
        int testcase = 1;
        
        scanner.nextLine(); //ignore num input
        while(scanner.hasNextLine()) {
            int mote = Integer.valueOf(scanner.nextLine().split(" ")[0]);
            List<Integer> motelist = new ArrayList<Integer>();
            for(String s : scanner.nextLine().split(" ")) motelist.add(Integer.valueOf(s));
            Collections.sort(motelist);
            System.out.println(String.format("Case #%d: %d", testcase++, consume(mote, 0, motelist)));
        }
    }

    private static int consume(int mote, int lpos, List<Integer> motelist) {

        // consume as much as possible
        while (lpos <= motelist.size()-1 && mote > motelist.get(lpos)) {
            mote += motelist.get(lpos);
            lpos++;
        }

        if (lpos > motelist.size()-1) return 0; //no more motes

        int addres = (mote == 1) ? Integer.MAX_VALUE : consume(mote+mote-1, lpos, motelist);
        int removeres = consume(mote, lpos+1, motelist);
        //System.out.println("addres " + addres + ", removeres " + removeres);
        return 1 + Math.min(addres, removeres);

    }

}
