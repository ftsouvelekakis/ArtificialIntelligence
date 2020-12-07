import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner userInputReader = new Scanner(System.in);
        int turn = -1;
        int maxDepth=0;

        System.out.println("-------------Othello Game-------------\n");
        Main app = new Main();
        
        maxDepth =app.readDepth(userInputReader,maxDepth);
        turn = app.readTurn(userInputReader, turn);
        
        GamePlayer XPlayer = new GamePlayer(maxDepth,Board.X);
        GamePlayer OPlayer = new GamePlayer(maxDepth,Board.O);
        
        Board board = new Board();
        board.print();
		while(!board.isTerminal())
		{
            System.out.println();
            switch (board.getLastLetterPlayed())
            {
                case Board.X:
                    System.out.println("O moves");
                    if(board.noValidMoves(Board.O)){
                        if(turn==1){
                            board.resetValidMoves();
                            Move OMove = OPlayer.MiniMax(board);
                            board.setValidHelper(OMove.getRow(),OMove.getCol());
                            board.makeMove(OMove.getRow(), OMove.getCol(), Board.O);
                        }
                        else
                        {
                            System.out.println("\nValid moves:");
                            for(int i =0; i<board.getValidRowsHelper().size(); i++){
                                System.out.println((i+1) + ") " + board.getValidRowsHelper().get(i) + " " + board.getValidColsHelper().get(i));
                            }
                            int choice = Integer.parseInt(userInputReader.nextLine());
                            board.makeMove(board.getValidRowsHelper().get(choice-1), board.getValidColsHelper().get(choice-1), Board.O);
                        }   
                    }
                    break;
                case Board.O:  
                    System.out.println("X moves");
                    if(board.noValidMoves(Board.X)){
                        if(turn==0){
                            board.resetValidMoves();
                            Move XMove = XPlayer.MiniMax(board);
                            board.setValidHelper(XMove.getRow(),XMove.getCol());
                            board.makeMove(XMove.getRow(), XMove.getCol(), Board.X);
                        }
                        else
                        {
                            System.out.println("\nValid moves:");
                            for(int i =0; i<board.getValidRowsHelper().size(); i++){
                                System.out.println((i+1) + ") " + board.getValidRowsHelper().get(i) + " " + board.getValidColsHelper().get(i));
                            }
                            int choice = Integer.parseInt(userInputReader.nextLine());
                            board.makeMove(board.getValidRowsHelper().get(choice-1), board.getValidColsHelper().get(choice-1), Board.X);
                        }
                    }
                    break;
                default:
                    break;
            }
            if(board.countNoValid==2){
                System.out.println("\nBoth players have no valid moves the game has ended");
                System.out.println("Final score = X:" + board.countX + " O:"+ board.countO);
            }else{
                board.print();
            }
        }
        if(board.countNoValid!=2){
            System.out.println("\nNo empty cells found");
            System.out.println("Final score = X:" + board.countX + " O:"+ board.countO);
        }
        userInputReader.close();    
    }

    public int readDepth(Scanner input,int maxDepth){
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