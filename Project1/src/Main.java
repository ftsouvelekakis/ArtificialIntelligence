import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws Exception 
    {
        Scanner userInputReader = new Scanner(System.in);
        int turn = -1;
        int maxDepth = 0;
        int gameMode = 0;
        int choice = 0;

        System.out.println("-------------Othello-------------\n");
        Main app = new Main();
        
        gameMode =app.readGameMode(userInputReader, gameMode); //Game mode selection
        
        if(gameMode == 2 || gameMode == 3) //if human vs Ai or Ai vs Ai ask for  max depth
        {
            maxDepth = app.readDepth(userInputReader,maxDepth); // read max depth
        }
        if(gameMode == 2)
        {
            turn = app.readTurn(userInputReader, turn);  // if human vs Ai, ask human if wants to play first
        }
        
        GamePlayer XPlayer = new GamePlayer(maxDepth,Board.X); // Create players
        GamePlayer OPlayer = new GamePlayer(maxDepth,Board.O);
        
        Board board = new Board();
        board.findAllValidMoves(board.opponent(board.getLastLetterPlayed()));
        board.print();
		while(!board.isTerminal())
		{
            if(gameMode == 3)
            {
                Thread.sleep(2000);  // if Ai vs Ai pause the program for better readability
            }
            System.out.println();
            switch (board.getLastLetterPlayed())
            {
                case Board.X:
                    System.out.println("O moves");
                    if(!board.noValidMoves(Board.O))
                    {
                        if( (turn == 1 && gameMode == 2) || gameMode == 3 ) // if player X is AI
                        {
                            Move OMove = OPlayer.MiniMax(board); //call minimax algorithm
                            board.makeMove(OMove.getRow(), OMove.getCol(), Board.O);
                            System.out.println("O moved at: row: " + OMove.getRow() + " , col: " + OMove.getCol() );
                        }
                        else if((turn==0 && gameMode==2) || gameMode==1) // else if human
                        {
                            System.out.println("\nValid moves:"); // print available moves
                            for(int i =0; i<board.getValidRowsHelper().size(); i++)
                            {
                                System.out.println((i+1) + ") " + board.getValidRowsHelper().get(i) + " " + board.getValidColsHelper().get(i));
                            }
                            choice = app.readChoice(userInputReader,choice,board);
                            board.makeMove(board.getValidRowsHelper().get(choice-1), board.getValidColsHelper().get(choice-1), Board.O);
                        }   
                    }
                    else
                    {
                        System.out.println("Player O has no valid moves");
                        Thread.sleep(2000);

                    }
                    break;
                case Board.O:  //Same as O
                    System.out.println("X moves");
                    if(!board.noValidMoves(Board.X))
                    {
                        if((turn == 0 && gameMode == 2) || gameMode == 3)
                        {
                            Move XMove = XPlayer.MiniMax(board);
                            board.makeMove(XMove.getRow(), XMove.getCol(), Board.X);
                            System.out.println("X moved at: row: " + XMove.getRow() + " , col: " + XMove.getCol() );
                        }
                        else if((turn == 1 && gameMode == 2) || gameMode == 1)
                        {
                            System.out.println("\nValid moves:");
                            for(int i =0; i<board.getValidRowsHelper().size(); i++)
                            {
                                System.out.println((i+1) + ") " + board.getValidRowsHelper().get(i) + " " + board.getValidColsHelper().get(i));
                            }
                            choice = app.readChoice(userInputReader,choice,board);
                            board.makeMove(board.getValidRowsHelper().get(choice-1), board.getValidColsHelper().get(choice-1), Board.X);
                        }
                    }
                    else
                    {
                        System.out.println("Player X has no valid moves" );
                        Thread.sleep(2000);
                    }
                    break;
                default:
                    break;
            }
            if(board.getCountNoValid() == 2)  // if both players have no valid moves print message and finish the program
            {
                System.out.println("\nBoth players have no valid moves the game has ended");
                System.out.println("Final score = X:" + board.getCountX() + " O:"+ board.getCountO());
            }
            else // else find valid moves for the next player
            {
                board.findAllValidMoves(board.opponent(board.getLastLetterPlayed()));
                board.print();
            }
        }
        if(board.getCountNoValid() !=2 )    // if board is full
        {
            System.out.println("\nNo empty cells found");
            System.out.println("Final score = X:" + board.getCountX() + " O:"+ board.getCountO());
        }
        userInputReader.close();    
    }

    public int readDepth(Scanner input,int maxDepth)
    {
        while(true)
        {
            System.out.print("Please select minimax algorithm depth (Valid input > 0) : ");
            try
            {
                maxDepth = Integer.parseInt(input.nextLine());
                if(maxDepth > 0) 
                {
                    System.out.println("\nSelected depth is : " + maxDepth);
                    break;
                }
                else
                {
                    System.out.println("\n-----Selected depth should be positive integer-----\n");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("\n-----Wrong input please select a positive integer-----\n");
            }
        }
        return maxDepth;
    }

    public int readTurn(Scanner input,int turn)
    {
        while(turn != 1 && turn != 0)
        {
            System.out.print("\nDo you want play first? Select 1 (Yes) or 0 (No) : ");
            try
            {
                turn = Integer.parseInt(input.nextLine());
                if (turn  ==1)
                {
                    System.out.println("\nHuman starts\n");
                    break;
                }
                else if(turn == 0)
                {
                    System.out.println("\nAI starts\n");
                    break;
                }
                else
                {
                    System.out.println("\n-----Wrong input please select 1 or 0-----");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("\n-----Wrong input please select 1 or 0-----");
            }
        }
        return turn;
    }

    public int readGameMode(Scanner input,int gameMode)
    {
        while(gameMode != 1 && gameMode!=2 && gameMode != 3)
        {
            System.out.println("Game modes:" + "\n1)Human vs Human" + "\n2)Human Vs AI" + "\n3)AI vs AI");
            System.out.print("Please select game mode: ");
            try
            {
                gameMode = Integer.parseInt(input.nextLine());
                if (gameMode == 1)
                {
                    System.out.println("\nHuman vs Human mode selected\n");
                    break;
                }
                else if(gameMode == 2)
                {
                    System.out.println("\nHuman vs AI mode selected\n");
                    break;
                }
                else if(gameMode == 3)
                {
                    System.out.println("\nAI vs AI mode selected\n");
                    break;
                }
                else
                {
                    System.out.println("\n-----Wrong input please select between 1-3-----\n");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("\n-----Wrong input please select between 1-3-----\n");
            }
        }
        return gameMode;
    }

    public int readChoice(Scanner input,int choice,Board board){
        boolean flag = true;
        while(flag)
        {
            System.out.print("Please select your move: ");
            try
            {
                choice = Integer.parseInt(input.nextLine());
                if(choice < 1 || choice > board.getValidRowsHelper().size()){
                    System.out.println("\nError: Wrong input, please select an available move\n");
                }
                else
                {
                    break;
                }

            }
            catch(NumberFormatException e)
            {
                System.out.println("\nError: Wrong input, please select an available move\n");
            }
        }
        return choice;
    }
}