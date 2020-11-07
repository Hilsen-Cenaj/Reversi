import java.util.HashSet;

public class Board {

    private final char[][] board;
    private int XScore, OScore, remaining;
    private final char[] boardX = new char[]{'A','B','C','D','E','F','G','H'};

/**
 * "Board" Constructor
 * Making the board display
 * */
    public Board(){
        board = new char[][]{
                {'_','_','_','_','_','_','_','_',},
                {'_','_','_','_','_','_','_','_',},
                {'_','_','_','_','_','_','_','_',},
                {'_','_','_','O','X','_','_','_',},
                {'_','_','_','X','O','_','_','_',},
                {'_','_','_','_','_','_','_','_',},
                {'_','_','_','_','_','_','_','_',},
                {'_','_','_','_','_','_','_','_',},
        };
    }

    public class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }

        @Override
        public boolean equals(Object o) {
            return o.hashCode() == this.hashCode();
        }

        @Override
        public int hashCode() {
            return Integer.parseInt(x + "" + y);
        }
    }

    private void findPlaceableLocations(char player, char opponent, HashSet<Point> placeablePositions){
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if(board[i][j] == opponent){
                    int I = i, J = j;
                    if(i-1>=0 && j-1>=0 && board[i-1][j-1] == '_'){
                        i = i+1; j = j+1;
                        while(i<7 && j<7 && board[i][j] == opponent){i++;j++;}
                        if(i<=7 && j<=7 && board[i][j] == player) placeablePositions.add(new Point(I-1, J-1));
                    }
                    i=I;j=J;
                    if(i-1>=0 && board[i-1][j] == '_'){
                        i = i+1;
                        while(i<7 && board[i][j] == opponent) i++;
                        if(i<=7 && board[i][j] == player) placeablePositions.add(new Point(I-1, J));
                    }
                    i=I;
                    if(i-1>=0 && j+1<=7 && board[i-1][j+1] == '_'){
                        i = i+1; j = j-1;
                        while(i<7 && j>0 && board[i][j] == opponent){i++;j--;}
                        if(i<=7 && j>=0 && board[i][j] == player) placeablePositions.add(new Point(I-1, J+1));
                    }
                    i=I;j=J;
                    if(j-1>=0 && board[i][j-1] == '_'){
                        j = j+1;
                        while(j<7 && board[i][j] == opponent)j++;
                        if(j<=7 && board[i][j] == player) placeablePositions.add(new Point(I, J-1));
                    }
                    j=J;
                    if(j+1<=7 && board[i][j+1] == '_'){
                        j=j-1;
                        while(j>0 && board[i][j] == opponent)j--;
                        if(j>=0 && board[i][j] == player) placeablePositions.add(new Point(I, J+1));
                    }
                    j=J;
                    if(i+1<=7 && j-1>=0 && board[i+1][j-1] == '_'){
                        i=i-1;j=j+1;
                        while(i>0 && j<7 && board[i][j] == opponent){i--;j++;}
                        if(i>=0 && j<=7 && board[i][j] == player) placeablePositions.add(new Point(I+1, J-1));
                    }
                    i=I;j=J;
                    if(i+1 <= 7 && board[i+1][j] == '_'){
                        i=i-1;
                        while(i>0 && board[i][j] == opponent) i--;
                        if(i>=0 && board[i][j] == player) placeablePositions.add(new Point(I+1, J));
                    }
                    i=I;
                    if(i+1 <= 7 && j+1 <=7 && board[i+1][j+1] == '_'){
                        i=i-1;j=j-1;
                        while(i>0 && j>0 && board[i][j] == opponent){i--;j--;}
                        if(i>=0 && j>=0 && board[i][j] == player)placeablePositions.add(new Point(I+1, J+1));
                    }
                    i=I;j=J;
                }
            }
        }
    }

/**
 * Print Board
 * 1st for -> print A-H
 * 2nd for -> print 1-8
 * 3rd for -> print board[][]
 * */
    public void displayBoard(Board b){
        System.out.print("\n  ");
        for(int i=0;i<8;++i)
            System.out.print(boardX[i]+" ");
        System.out.println();
        for(int i=0;i<8;++i){
            System.out.print((i+1)+" ");
            for(int j=0;j<8;++j)
                System.out.print(b.board[i][j]+" ");
            System.out.println();
        }
        System.out.println();
    }

}
