import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TicTacToeTomek {
    private static final List<int[]> winsolutions = new ArrayList<int[]>();
    
    public static void main(String[] args) throws Exception {
        winsolutions.add(new int[]{0,1,2,3});       // horizontal wins
        winsolutions.add(new int[]{4,5,6,7});
        winsolutions.add(new int[]{8,9,10,11});
        winsolutions.add(new int[]{12,13,14,15});   
        winsolutions.add(new int[]{0,4,8,12});      // vertical wins
        winsolutions.add(new int[]{1,5,9,13});
        winsolutions.add(new int[]{2,6,10,14});
        winsolutions.add(new int[]{3,7,11,15});
        winsolutions.add(new int[]{0,5,10,15});     // diagonal wins
        winsolutions.add(new int[]{3,6,9,12}); 
        
        FileInputStream fis = new FileInputStream("/home/jason/algorist/codejam/src/sample.txt");
        Scanner scanner = new Scanner(fis);
        String board = new String();
        
        int numOfGames = Integer.parseInt(scanner.nextLine());
        int curGame = 1;
        
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(!line.equals("")) {
                board = board + line;
            } else {
                checkGame(board, curGame);
                curGame++;
                board = "";
            }
        }
        checkGame(board, curGame);
    }
    
    public static void checkGame(String board, int gameNum) {
        boolean xWon=false, oWon=false, incomplete=false;
        for(int[] solution : winsolutions) {
            int status = checkWin(solution, board);
            if (status == 0) { xWon = true; }
            if (status == 1) { oWon = true; }
            if (status == -1) { incomplete = true; }
        }
        
        if (xWon) {
            System.out.println("Case #" + gameNum + ": X won");
        } else if (oWon) {
            System.out.println("Case #" + gameNum + ": O won");
        } else if (incomplete) {
            System.out.println("Case #" + gameNum + ": Game has not completed");
        } else {
            System.out.println("Case #" + gameNum + ": Draw");
        }
    }
    
    public static int checkWin(int[] solution, String board) {
        int xcount=0, ocount=0;
        boolean t=false, sawEmpty=false;
        for(int i=0; i<solution.length; i++) {
            char boardchar = board.charAt(solution[i]);
            if(boardchar == 'X') {
                xcount++;
            } else if(boardchar == 'O') {
                ocount++;
            } else if(boardchar == 'T') {
                t=true;
            } else {
                sawEmpty=true;
            }
        }
        //System.out.println(String.format("xc %s, oc %s, t? %s", xcount, ocount, t));
        if ((xcount == 4) || (xcount == 3 && t)) {
            return 0; // x wins
        } else if ((ocount == 4) || (ocount == 3 && t)) {
            return 1; // o wins
        } else if (sawEmpty) {
            return -1; // game not done
        } else {
            return -2; // no one wins
        }
    }
    
    
}
