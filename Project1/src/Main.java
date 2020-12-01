import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner userInputReader = new Scanner(System.in);
        int maxDepth = 0;
        int turn = -1;

        System.out.println("-------------Othello Game-------------\n");
        Main app = new Main();
        
        maxDepth = app.readDepth(userInputReader, maxDepth);
        turn = app.readTurn(userInputReader, turn);

        GamePlayer Χplayer = new GamePlayer(Board.X);
        GamePlayer Οplayer = new GamePlayer(Board.O);

        Board board = new Board();
        board.print();
		while(!board.isTerminal())
		{
			System.out.println();
			switch (board.getLastLetterPlayed())
			{
				case Board.X:
                    System.out.println("O moves");
					//Move OMove = OPlayer.MiniMax(board);
					//board.makeMove(OMove.getRow(), OMove.getCol(), Board.O);
					int rowO = userInputReader.nextInt();
					int colO = userInputReader.nextInt();
					board.makeMove(rowO, colO, Board.O);
					break;
				case Board.O:
                    System.out.println("X moves");
					//Move XMove = XPlayer.MiniMax(board);
                    //board.makeMove(XMove.getRow(), XMove.getCol(), Board.X);
                    int rowX = userInputReader.nextInt();
					int colX = userInputReader.nextInt();
					board.makeMove(rowX, colX, Board.X);
					break;
				default:
					break;
			}
			board.print();
		} 
        userInputReader.close();    
    }

    public int readDepth(Scanner input, int maxDepth){
        while(true){
            System.out.print("Please select minimax algorithm depth (Valid input > 0) : ");
            try { 
                
                maxDepth = Integer.parseInt(input.nextLine());
                if(maxDepth>0) 
                {
                    System.out.println("\nSelected depth is : " + maxDepth);  
                    break;
                }
                else{
                    System.out.println("\n-----Selected depth should be positive integer-----\n");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("\n-----Wrong input please select a positive integer-----\n");
            }
            
        }
        return maxDepth;
    }

    public int readTurn(Scanner input,int turn){
        while(turn!=1 && turn!=0){
            System.out.print("\nDo you want play first? Select 1 (Yes) or 0 (No) : "); 
            try {
                turn = Integer.parseInt(input.nextLine());
                if (turn==1){
                    System.out.println("\nHuman starts\n");
                    break;
                }
                else if(turn==0){
                    System.out.println("\nAI starts\n");
                    break;
                }
                else{
                    System.out.println("\n-----Wrong input please select 1 or 0-----");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("\n-----Wrong input please select 1 or 0-----");
            }
        }
        return turn;
    }
}