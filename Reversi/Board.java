//  o (player)  x (computer)


import java.util.ArrayList;

/**
* Board class that contains all board manipulation and look around methods
*  black == 'x'
*  white == 'o'
* empty space == '_'
* Moves are formatted as "row column" (without quotes) where row and column are integers
*/


class Board{ 
    
    public class Cell{
        int x, y;
        Cell(int x, int y){
            this.x = x;
            this.y = y;
        }
		/*  need to properly override the equals() and hashCode() methods 
		 *  so as ArrayList<Cell>.contains() to work properly  
		 */
		@Override
        public boolean equals(Object o){
            return o.hashCode()==this.hashCode();
        }
			
		@Override
        public int hashCode() {
            return Integer.parseInt(x+""+y);
        }
		public String toString(){
			 return "("+this.y + ","+this.x+")";
		}
			
        
    }
    
    private final char[][] board;
    int YourDiscs, CompDiscs, remaining;
    private final char boardX[] = new char[]{'a','b','c','d','e','f','g','h'};
    private final char boardY[] = new char[]{'1','2','3','4','5','6','7','8'};
	
	/**
	* Board constructor
	*/
    public Board(){ 
        board = new char[][]{
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','o','x','_','_','_',},
            {'_','_','_','x','o','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
            {'_','_','_','_','_','_','_','_',},
        };
    }
	/**
	* Copy constructor for Board
	* @param b board to copy
	*/	
		
    public Board(Board b){
		board = new char[8][];
		for (int i = 0; i < 8; i++) {
	        board[i] = b.board[i].clone();
		}
		YourDiscs = b.YourDiscs;
		CompDiscs = b.CompDiscs;
		remaining = b.remaining;
	}
	public Board clone(Board state){
		Board newState = new Board(state);
		return newState;
	}
	public Board initialize() {
		Board initial = new Board();
		System.out.println("Board initialized\n");
		return initial;
	}
		
	public boolean isValidInput(char a, int b){   // a in board[], b between 1 and 8
	 boolean validX = false;
	 for(int i=0;i<8;++i)
		 if (a == boardX[i]) validX = true;
	 if (b >= 1 && b <= 8 && validX) return true;
     else {
           System.out.print("Wrong coordinates\n");
           return false;
	 }		   
	}
	public int coordinateX(char x){
        for(int i=0;i<8;++i)if(boardX[i]==Character.toLowerCase(x)||boardX[i]==Character.toUpperCase(x))return i;
        return -1; // Illegal move received
    }
	public String coordinateXletter (int x) { 
		return String.valueOf(boardX[x]);
	}
	
	
	public void displayBoard(Board b){  
        System.out.print("\n                   ");
        for(int i=0;i<8;++i)System.out.print(boardX[i]+" ");
        System.out.println();

        for(int i=0;i<8;++i){
			System.out.print("                 ");
            System.out.print(boardY[i]+" ");
            for(int j=0;j<8;++j)
                System.out.print(b.board[i][j]+" ");
			System.out.print(boardY[i]);
            System.out.println();
        }
	
		System.out.print("                   ");
		for(int i=0;i<8;++i)System.out.print(boardX[i]+" ");
        System.out.println("\n"); 
    }

	public void showBoardState(ArrayList<Cell> locations, char player, char opponent){
		int v = 46;
        char p = (char)v;
        for(Cell c:locations){
            board[c.x][c.y]=p;
		}
        displayBoard(this);
        for(Cell c:locations)
            board[c.x][c.y]='_';
    }
	/**
	* Find legal moves on the board according to rules
	* @param player char representation
	* @param legalCells to fill in
	* @return move
	*/
    
    private  void findLegalMoves(char player, char opponent, ArrayList<Cell> legalCells){ 
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if( board[i][j] == opponent){
                    int I = i, J = j;  
                    if(i-1>=0 && j-1>=0 &&  board[i-1][j-1] == '_'){ 
                        i = i+1; j = j+1;
                        while(i<7 && j<7 &&  board[i][j] == opponent){i++;j++;}
                        if(i<=7 && j<=7 &&  board[i][j] == player) legalCells.add(new Cell(I-1, J-1));
                    } 
                    i=I;j=J;
                    if(i-1>=0 &&  board[i-1][j] == '_'){
                        i = i+1;
                        while(i<7 &&  board[i][j] == opponent) i++;
                        if(i<=7 &&  board[i][j] == player) legalCells.add(new Cell(I-1, J));
                    } 
                    i=I;
                    if(i-1>=0 && j+1<=7 &&  board[i-1][j+1] == '_'){
                        i = i+1; j = j-1;
                        while(i<7 && j>0 &&  board[i][j] == opponent){i++;j--;}
                        if(i<=7 && j>=0 &&  board[i][j] == player) legalCells.add(new Cell(I-1, J+1));
                    }  
                    i=I;j=J;
                    if(j-1>=0 &&  board[i][j-1] == '_'){
                        j = j+1;
                        while(j<7 &&  board[i][j] == opponent)j++;
                        if(j<=7 &&  board[i][j] == player) legalCells.add(new Cell(I, J-1));
                    }
                    j=J;
                    if(j+1<=7 &&  board[i][j+1] == '_'){
                        j=j-1;
                        while(j>0 &&  board[i][j] == opponent)j--;
                        if(j>=0 &&  board[i][j] == player) legalCells.add(new Cell(I, J+1));
                    }
                    j=J;
                    if(i+1<=7 && j-1>=0 &&  board[i+1][j-1] == '_'){
                        i=i-1;j=j+1;
                        while(i>0 && j<7 &&  board[i][j] == opponent){i--;j++;}
                        if(i>=0 && j<=7 &&  board[i][j] == player) legalCells.add(new Cell(I+1, J-1));
                    }
                    i=I;j=J;
                    if(i+1 <= 7 &&  board[i+1][j] == '_'){
                        i=i-1;
                        while(i>0 &&  board[i][j] == opponent) i--;
                        if(i>=0 &&  board[i][j] == player) legalCells.add(new Cell(I+1, J));
                    }
                    i=I;
                    if(i+1 <= 7 && j+1 <=7 &&  board[i+1][j+1] == '_'){
                        i=i-1;j=j-1;
                        while(i>0 && j>0 &&  board[i][j] == opponent){i--;j--;}
                        if(i>=0 && j>=0 &&  board[i][j] == player)legalCells.add(new Cell(I+1, J+1));
                    }
                    i=I;j=J;
                    }
                } 
        } 
    } 
    
     public  ArrayList<Cell> getLegalMoves(char player, char opponent){ 
        ArrayList<Cell> legalCells = new ArrayList<>();
        findLegalMoves(player, opponent, legalCells);
        return legalCells;
    }
	/**
	* Place a move on the board
	* @param  player char representation
	* @param c location = Cell c
	*/
    
    public void applyMove(Cell c, char player, char opponent){
        int i = c.x, j = c.y;
         board[i][j] = player; 
        int I = i, J = j;  
        
		/* OK, now flip discs i.e change from opponent to player where appropriate */
        if(i-1>=0 && j-1>=0 &&  board[i-1][j-1] == opponent){ 
            i = i-1; j = j-1;
            while(i>0 && j>0 &&  board[i][j] == opponent){i--;j--;}
            if(i>=0 && j>=0 &&  board[i][j] == player) {while(i!=I-1 && j!=J-1) board[++i][++j]=player;}
        } 
        i=I;j=J; 
        if(i-1>=0 &&  board[i-1][j] == opponent){
            i = i-1;
            while(i>0 &&  board[i][j] == opponent) i--;
            if(i>=0 &&  board[i][j] == player) {while(i!=I-1) board[++i][j]=player;}
        } 
        i=I; 
        if(i-1>=0 && j+1<=7 &&  board[i-1][j+1] == opponent){
            i = i-1; j = j+1;
            while(i>0 && j<7 &&  board[i][j] == opponent){i--;j++;}
            if(i>=0 && j<=7 &&  board[i][j] == player) {while(i!=I-1 && j!=J+1) board[++i][--j] = player;}
        }   
        i=I;j=J;
        if(j-1>=0 &&  board[i][j-1] == opponent){
            j = j-1;
            while(j>0 &&  board[i][j] == opponent)j--;
            if(j>=0 &&  board[i][j] == player) {while(j!=J-1) board[i][++j] = player;}
        }
        j=J; 
        if(j+1<=7 &&  board[i][j+1] == opponent){
            j=j+1;
            while(j<7 &&  board[i][j] == opponent)j++;
            if(j<=7 &&  board[i][j] == player) {while(j!=J+1) board[i][--j] = player;}
        }
        j=J; 
        if(i+1<=7 && j-1>=0 &&  board[i+1][j-1] == opponent){ 
            i=i+1;j=j-1;
            while(i<7 && j>0 &&  board[i][j] == opponent){i++;j--;}
            if(i<=7 && j>=0 &&  board[i][j] == player) {while(i!=I+1 && j!=J-1) board[--i][++j] = player;}
        }
        i=I;j=J; 
        if(i+1 <= 7 &&  board[i+1][j] == opponent){ 
            i=i+1;
            while(i<7 &&  board[i][j] == opponent) i++;
            if(i<=7 &&  board[i][j] == player) {while(i!=I+1) board[--i][j] = player;}
        }
        i=I;

        if(i+1 <= 7 && j+1 <=7 &&  board[i+1][j+1] == opponent){
            i=i+1;j=j+1;
            while(i<7 && j<7 &&  board[i][j] == opponent){i++;j++;}
            if(i<=7 && j<=7 &&  board[i][j] == player)while(i!=I+1 && j!=J+1) board[--i][--j] = player;}
    } 
	   
    public int getScore(char player){
        YourDiscs = 0; CompDiscs = 0; remaining = 0;
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if(board[i][j]=='o')YourDiscs++;
                else if(board[i][j]=='x')CompDiscs++;
                else remaining++;
            }
        }
		
		if(player=='x') return CompDiscs-YourDiscs ;
		return YourDiscs - CompDiscs; 
    }
	public int gameResult(ArrayList<Cell> yourLegalMoves, ArrayList<Cell> compLegalMoves){ 
        int score = getScore('x');
        if(remaining == 0){
            if(YourDiscs > CompDiscs) return 1;        // You win
            else if(CompDiscs > YourDiscs) return -1; // Computer wins
            else return 0; // we have draw
        }
        if(YourDiscs==0 || CompDiscs == 0){
            if(YourDiscs > 0) return 1;
            else if(CompDiscs > 0) return -1;
        }  
        if(yourLegalMoves.isEmpty() && compLegalMoves.isEmpty()){
            if(YourDiscs > CompDiscs) return 1;
            else if(CompDiscs > YourDiscs) return -1;
            else return 0;
        } 
        return -2;
    } 	

}  // end of class Board 