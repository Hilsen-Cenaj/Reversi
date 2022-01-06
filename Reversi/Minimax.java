import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Minimax class has evaluation function and minimax algorithm with alpha-beta pruning to determine best moves.
 */
public class Minimax extends Board{

    
	/**
	 * Evaluation function
	 * @param  board to evaluate
	 * @return score of board
	 */
	static int evaluate(Board board, char player) {
	    if (player=='x')
		 return board.CompDiscs - board.YourDiscs;
         return board.YourDiscs - board.CompDiscs ;
	 }

	/**
	 * Minimax algorithm with alpha-beta pruning.
	 * @param  d -> Depth
	 * @param  myBest -> Player best score
	 * @param  hisBest -> PC best score
	 * @param  player -> Player moves
	 * @param  opponent -> PC moves
	 * @return Object[0] contains (int) score and Object[1] contains (Cell) move
	 */
	 static Object[]  mmab(Board board, int d, int myBest, int hisBest, char player, char opponent){ //, Cell c) {
		
		ArrayList<Cell> moveList;
		moveList = board.getLegalMoves(player, opponent);
		
		int bestScore = 0;
		Object[] temp;
		int tempScore;
		
		Board.Cell bestMove = board.new Cell(-1, -1);   // = c;
       
		// evaluate at leaf
		if (d == 0) {
			
			Object[] x = { evaluate(board,player), moveList.get(0) };
			
			return x;
		}

		bestScore = myBest;
       try {
		while (moveList.size() > 0) {
			Board newBoard = new Board(board);
		
			Cell newMove = moveList.get(0);
			
			
			newBoard.applyMove(newMove, player, opponent);
			int score=newBoard.getScore(player);
			
			temp = mmab(newBoard, d - 1, -hisBest, -bestScore, opponent, player); //,c);
			tempScore = -(Integer) temp[0];
			if (tempScore > bestScore) {
				bestScore = tempScore;
				bestMove = newMove;
			}
			if (bestScore > hisBest) {
				Object[] x = { bestScore, bestMove };
				
				return x;
			}
			moveList.remove(0);
		}
		} catch (IndexOutOfBoundsException e) {
                 e.printStackTrace();
                 System.out.println("Invalid indexes");
				 
                }
		Object[] x = { bestScore, bestMove };

		return x;
	   
	}
}