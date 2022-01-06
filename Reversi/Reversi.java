import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class Reversi{
	
    public static void play(Board b){  
	 
		
        Scanner scan = new Scanner(System.in);
               
        Board.Cell move = b.new Cell(-1, -1); 
		
        int result;
	    int mmdepth, ln=0, cn=0, score=0; //al=0, ac=0;
	    boolean skip;
        boolean youFirst=true, firstTime=true;
        String input;
		
		System.out.println("**** New game. Enter q to quit, n to start over when prompted for input ****\n");
		do {
          /*  get algorithm depth  */
		  do {
		    System.out.println("Please choose Minimax search depth : ");
            input = scan.next();
		    if (input.equals("q") || input.equals("Q"))  throw new NoSuchElementException();
		    else if (input.equals("n") || input.equals("N")) {System.out.println("Starting new game\n");continue;}
			else if (input.equals("0")) {System.out.println("Depth should be >0");continue;}
			else if (Integer.parseInt(input) > 10) {System.out.println("Depth should be less than 10");continue;}
		
            mmdepth = Integer.parseInt( input );
			
			break;
		  } while (true);
		
		  /*  who moves first  */
		  do {
		    System.out.println("Who moves first? You (o), Computer (x) :");
            input = scan.next();
            if 	    (input.equals("o") || input.equals("O") ) {youFirst = true;break;}
		    else if (input.equals("x") || input.equals("X") ) {youFirst = false;break;}
		    else if (input.equals("q") || input.equals("Q") || input.equals("n") || input.equals("N") ) break;
		  } while (true); // end of do
		
	      if (input.equals("q") || input.equals("Q"))  throw new NoSuchElementException();
		  else if (input.equals("n") || input.equals("N")) {System.out.println("Starting new game\n");continue;}
		  break;
		} while (true);
		
	    if (firstTime) {
				System.out.println("\nGame starts . . . \n");
				scan.nextLine();
				firstTime = !firstTime;
			}	 
			
		if (youFirst) System.out.println("You (o) Move first, please do a move");
		else    	  System.out.println("Computer (x) Moves first"); 
		
        while(true){ 
		  
          skip = false;
          if (!youFirst)
          {	
			 
            
		    ArrayList<Board.Cell> yourLegalMoves  = b.getLegalMoves('o', 'x');
			ArrayList<Board.Cell> compLegalMoves  = b.getLegalMoves('x', 'o');
		
            b.showBoardState(compLegalMoves, 'x', 'o'); 
			
            result = b.gameResult(yourLegalMoves, compLegalMoves);
            
            if(result == 0){System.out.println("It is a draw.");break;}
            else if(result==1){System.out.println("You (o) win with "+ b.YourDiscs +" against "+ b.CompDiscs + " discs. Congratulations!!!");break;}
            else if(result==-1){System.out.println("Computer (x) wins with "+ b.CompDiscs +" against "+ b.YourDiscs +" discs");break;}

            if(compLegalMoves.isEmpty()){ 
                    System.out.println("Computer (x) needs to skip... Passing to You (o)");
                    skip = true; 
            }
			

            if(!skip){
			 
                System.out.print("\nComputer move (x)-->"+ b.CompDiscs +", (o)-->"+ b.YourDiscs +" : ");
				wait(1000);
				System.out.print("\nComputer is thinking ");
				
				//Board.Cell a = b.new Cell(-1,-1);
				Object[] temp;
                 
				for (int i=0;i<2*mmdepth;i++){
		           System.out.print(".");wait(3000/mmdepth);
		        }
				
				  
				//Board.Cell newmove = a;
				  
				temp = Minimax.mmab(b,mmdepth,Integer.MIN_VALUE,Integer.MAX_VALUE,'x','o'); //,a);
				ln=((Board.Cell)temp[1]).x;cn=((Board.Cell)temp[1]).y;
				move.y = cn;move.x = ln;
				ln++;
				System.out.println("\n"+b.coordinateXletter(cn)+ln+"\n");
	
                
                while(!compLegalMoves.contains(move)){
                    System.out.println("Invalid move!\n");
					do {
					 System.out.println("Computer move (x): ");
                     input = scan.next();
					 if (input.length() != 2) {
					   System.out.print("Wrong coordinates\n");
					   continue;
				     }
				     if (!b.isValidInput(input.charAt(0),Integer.parseInt(input.charAt(1)+""))) continue;
				     break;
				    } while (true);
                    move.y = b.coordinateX(input.charAt(0)); 
                    move.x = Integer.parseInt((input.charAt(1)+""))-1;  
                }
				
                b.applyMove(move, 'x', 'o');
                score=b.getScore('x');
                
            }
            skip = false;

            yourLegalMoves = b.getLegalMoves('o', 'x');
            compLegalMoves = b.getLegalMoves('x', 'o');

           	b.showBoardState(yourLegalMoves, 'o', 'x');
            result = b.gameResult(yourLegalMoves, compLegalMoves);

            if(result==0){System.out.println("It is a draw.");break;} 
            else if(result==1){System.out.println("You (o) win with "+ b.YourDiscs +" against "+ b.CompDiscs +" discs. Congratulations!!!");break;}
            else if(result==-1){System.out.println("Computer (x) wins with "+ b.CompDiscs +" against "+ b.YourDiscs +" discs");break;}

            if(yourLegalMoves.isEmpty()){  
                    System.out.println("You (o) need to skip... Passing to Computer (x)");
                    skip = true; 
            }

            if(!skip){ 
			 do {
				  System.out.println("Your move (o)-->"+ b.YourDiscs +", (x)-->"+ b.CompDiscs + ": ");
				  input = scan.next();
				  if (input.equals("q") || input.equals("Q")) break;  
				  if (input.equals("n") || input.equals("N")) break;
				  if (input.length() != 2) {
					System.out.print("Wrong coordinates\n");
					continue;
				  }
				  if (!b.isValidInput(input.charAt(0),Integer.parseInt(input.charAt(1)+""))) continue;
				  break;
				} while (true);
				if (input.equals("q") || input.equals("Q"))  throw new NoSuchElementException();
				if (input.equals("n") || input.equals("N")) throw new NumberFormatException();
				
				move.y = b.coordinateX(input.charAt(0));
				move.x = (Integer.parseInt(input.charAt(1)+"")-1);

             while(!yourLegalMoves.contains(move)){
                System.out.println("Invalid move!\n");
				do {
					System.out.println("Your move (o)-->"+ b.YourDiscs +", (x)-->"+ b.CompDiscs + ": ");
					input = scan.next();
					if (input.equals("q") || input.equals("Q")) break; // || input.equals("n") || input.equals("N") ) break;
					if (input.length() != 2) {
					 System.out.print("Wrong coordinates\n");
					 continue;
				    }
				    if (!b.isValidInput(input.charAt(0),Integer.parseInt(input.charAt(1)+""))) continue;
				    break;
				  } while (true);
				  if (input.equals("q") || input.equals("Q"))  throw new NoSuchElementException();
				                  
                  move.y = b.coordinateX(input.charAt(0));
                  move.x = (Integer.parseInt(input.charAt(1)+"")-1);
             }  
             b.applyMove(move, 'o', 'x');   
             score=b.getScore('o');
			 
            }
		  }
		  else
		  {
            
			ArrayList<Board.Cell> yourLegalMoves = b.getLegalMoves('o', 'x');
			ArrayList<Board.Cell> compLegalMoves = b.getLegalMoves('x', 'o');
			
            b.showBoardState(yourLegalMoves, 'o', 'x');
            result = b.gameResult(yourLegalMoves, compLegalMoves);

            if(result==0){System.out.println("It is a draw.");break;} 
            else if(result==1){System.out.println("You (o) win with "+ b.YourDiscs +" against "+ b.CompDiscs +" discs. Congratulations!!!");break;}
            else if(result==-1){System.out.println("Computer (x) wins with "+ b.CompDiscs +" against "+ b.YourDiscs +" discs");break;}

            if(yourLegalMoves.isEmpty()){  
                    System.out.println("You (o) need to skip... Passing to Computer (x)");
                    skip = true; 
            }

            if(!skip){ 
			 do {
                  System.out.println("Your move (o)-->"+ b.YourDiscs +", (x)-->"+ b.CompDiscs + ": ");
				  input = scan.next();
				  if (input.equals("q") || input.equals("Q")) break; 
				  if (input.equals("n") || input.equals("N")) break;
				  if (input.length() != 2) {
					System.out.print("Wrong coordinates\n");
					continue;
				  }
				  if (!b.isValidInput(input.charAt(0),Integer.parseInt(input.charAt(1)+""))) continue;
				  break;
				} while (true);
				if (input.equals("q") || input.equals("Q"))  throw new NoSuchElementException();
				if (input.equals("n") || input.equals("N")) throw new NumberFormatException();
				
				move.y = b.coordinateX(input.charAt(0));
				move.x = (Integer.parseInt(input.charAt(1)+"")-1);

             while(!yourLegalMoves.contains(move)){
                System.out.println("Invalid move!\n");
				do {
					System.out.println("Your move (o)-->"+ b.YourDiscs +", (x)-->"+ b.CompDiscs + ": ");
					input = scan.next();
				    if (input.equals("q") || input.equals("Q")) break; // || input.equals("n") || input.equals("N") ) break;
					if (input.length() != 2) {
					 System.out.print("Wrong coordinates\n");
					 continue;
				    }
				    if (!b.isValidInput(input.charAt(0),Integer.parseInt(input.charAt(1)+""))) continue;
				    break;
				  } while (true);
				  if (input.equals("q") || input.equals("Q"))  throw new NoSuchElementException();
				                  
                  move.y = b.coordinateX(input.charAt(0));
                  move.x = (Integer.parseInt(input.charAt(1)+"")-1);
             }  
             b.applyMove(move, 'o', 'x');   
             score=b.getScore('o');
             
            }
            skip = false;

            yourLegalMoves = b.getLegalMoves('o', 'x');
            compLegalMoves = b.getLegalMoves('x', 'o');

           	b.showBoardState(compLegalMoves, 'x', 'o'); 
            result = b.gameResult(yourLegalMoves, compLegalMoves);
            
            if(result == 0){System.out.println("It is a draw.");break;}
            else if(result==1){System.out.println("You (o) win with "+ b.YourDiscs +" against "+ b.CompDiscs +" discs. Congratulations!!!");break;}
            else if(result==-1){System.out.println("Computer (x) wins with "+ b.CompDiscs +" against "+ b.YourDiscs +" discs");break;}

            if(compLegalMoves.isEmpty()){ 
                    System.out.println("Computer (x) needs to skip... Passing to You (o)");
                    skip = true; 
            }

            if(!skip){
				
				
			 
                System.out.print("\nComputer move (x)-->"+ b.CompDiscs+", (o)-->"+ b.YourDiscs +" : ");
				wait(1000);
				System.out.print("\nComputer is thinking ");
				 
				// Board.Cell a = b.new Cell(-1,-1);
				Object[] temp;
            
				for (int i=0;i<2*mmdepth;i++){
		           System.out.print(".");wait(3000/mmdepth);
		          }
								  
				//Board.Cell newmove = a;
				  
				temp = Minimax.mmab(b,mmdepth,Integer.MIN_VALUE,Integer.MAX_VALUE,'x','o'); //,a);
				ln=((Board.Cell)temp[1]).x;cn=((Board.Cell)temp[1]).y;
				move.y = cn;move.x = ln;
				ln++;
				System.out.print("\n"+b.coordinateXletter(cn)+ln+"\n");
				               
                while(!compLegalMoves.contains(move)){
                    System.out.println("Invalid move!\n");
					do {
					 System.out.println("Computer move (x): ");
                     input = scan.next();
					 if (input.length() != 2) {
					   System.out.print("Wrong coordinates\n");
					   continue;
				     }
				     if (!b.isValidInput(input.charAt(0),Integer.parseInt(input.charAt(1)+""))) continue;
				     break;
				    } while (true);
                    move.y = b.coordinateX(input.charAt(0)); 
                    move.x = Integer.parseInt((input.charAt(1)+""))-1;  
                }
                b.applyMove(move, 'x', 'o');
                score=b.getScore('x');
                 
            }
		  }
		  
        } // end while(true)
		
    }  // end play()

	public static void wait(int mlsecs) {
		try {
			Thread.sleep(mlsecs);
		} catch (Exception e) {}
	}
  
} // end class Reversi