

public class Board {
    
    public static final int X = 1;
    public static final int O = -1;
	public static final int EMPTY = 0;
	public static final int VALID = -2;
	

	private Move lastMove;
	private int lastLetterPlayed;
    private int [][] gameBoard;

    public Board()
    {
        lastMove = new Move();
		lastLetterPlayed = O;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
            for(int j=0; j<8; j++)
            {
                if((i==4 && j==4) || (i==3 && j==3))
                {
                    gameBoard[i][j] = 1;
                }
                else if((i==3 && j==4) || (i==4 && j==3))
                {
                    gameBoard[i][j] = -1;
                }
                else
                {
                    gameBoard[i][j] = EMPTY;
                }
            }
        }
    }

    public Board(Board board)
	{
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}

    public Move getLastMove()
	{
		return lastMove;
	}
	
	public int getLastLetterPlayed()
	{
		return lastLetterPlayed;
	}
	
	public int[][] getGameBoard()
	{
		return gameBoard;
	}

    public void setLastMove(Move lastMove)
	{
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastLetterPlayed(int lastLetterPlayed)
	{
		this.lastLetterPlayed = lastLetterPlayed;
	}
	
    public void setGameBoard(int[][] gameBoard)
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}
    
    public void makeMove(int row, int col, int moveLetter)
	{
		if(gameBoard[row][col]==-2){
			gameBoard[row][col] = moveLetter;
			lastMove = new Move(row, col);
			lastLetterPlayed = moveLetter;
			flipRowRight(row,col,moveLetter);
			flipRowLeft(row, col, moveLetter);
			flipColDown(row, col, moveLetter);
			flipColUp(row, col, moveLetter);
			flipDiag0Up(row, col, moveLetter);
			flipDiag0Down(row, col, moveLetter);
			flipDiag1Down(row, col, moveLetter);
			flipDiag1Up(row, col, moveLetter);
		}else{
			System.out.println("Your move is not valid please try again\n");
		}
	}

	public void flipRowRight(int row,int col,int moveLetter){
		if(col<=5){
			if(gameBoard[row][col+1]!=EMPTY){
				if(scanRow(row, col, moveLetter)){
					while(gameBoard[row][col+1]!=moveLetter){
						gameBoard[row][col+1]=moveLetter;
						col++;
					}
				}
			}
		}
	}

	public void flipRowLeft(int row,int col,int moveLetter){
		if(col>=2){
			if(gameBoard[row][col-1]!=EMPTY){
				if(scanRow(row, col, moveLetter)){
					while(gameBoard[row][col-1]!=moveLetter){
						gameBoard[row][col-1]=moveLetter;
						col--;
					}
				}
			}
		}
	}

	public void flipColDown(int row,int col, int moveLetter){
		if(row<=5){	
			if(gameBoard[row+1][col]!=EMPTY){	
				if(scanCol(row, col, moveLetter)){
					while(gameBoard[row+1][col]!=moveLetter){
						gameBoard[row+1][col]=moveLetter;
						row++;
					}
				}
			}
		}
	}

	public void flipColUp(int row,int col, int moveLetter){	
		if(row>=2){	
			if(gameBoard[row-1][col]!=EMPTY){	
				if(scanCol(row, col, moveLetter)){
					while(gameBoard[row-1][col]!=moveLetter){
						gameBoard[row-1][col]=moveLetter;
						row--;
					}
				}
			}
		}
	}
		
	public void flipDiag0Down(int row,int col,int moveLetter){
		if(row<=5 && col<=5){
			if(gameBoard[row+1][col+1]!=EMPTY){
				if(scanDiag0(row, col, moveLetter)){
					while(gameBoard[row+1][col+1]!=moveLetter){	
						gameBoard[row+1][col+1]=moveLetter;
						col++;
						row++;
					}
				}
			}
		}
	}

	public void flipDiag0Up(int row,int col,int moveLetter){
		if(row>=2&&col>=2){	
			if(gameBoard[row-1][col-1]!=EMPTY){	
				if(scanDiag0(row, col, moveLetter)){
					while(gameBoard[row-1][col-1]!=moveLetter){	
						gameBoard[row-1][col-1]=moveLetter;
						col--;
						row--;
					}
				}
			}
		}
	}

	public void flipDiag1Down(int row,int col,int moveLetter){
		if(row<=5&&col>=2){
			if(gameBoard[row+1][col-1]!=EMPTY){
				if(scanDiag1(row, col, moveLetter)){
					while(gameBoard[row+1][col-1]!=moveLetter){	
						gameBoard[row+1][col-1]=moveLetter;
						col--;
						row++;
					}
				}
			}
		}
	}

	public void flipDiag1Up(int row,int col,int moveLetter){
		if(row>=2&&col<=5){
			if(gameBoard[row-1][col+1]!=EMPTY){
				if(scanDiag1(row, col, moveLetter)){
					while(gameBoard[row-1][col+1]!=moveLetter){	
						gameBoard[row-1][col+1]=moveLetter;
						col++;
						row--;
					}
				}
			}
		}
	}

	public int opponent(int player){
		if (player == 1){
			return -1;
		}
		else{
			return 1;
		}
	}

    public boolean isValidMove(int row, int col)
	{
        if(row >= 0 && row < 8 && col >= 0 && col < 8 ){
            if(gameBoard[row][col] == EMPTY){
                if(lastLetterPlayed == X){
                    return (scanRow(row, col,O)||scanCol(row, col,O)||scanDiag0(row,col,O)||scanDiag1(row, col,O));
                }
                else if(lastLetterPlayed == O){
					return (scanRow(row, col,X)||scanCol(row, col,X)||scanDiag0(row,col,X)||scanDiag1(row, col,X));
                }
            }
        }
        return false;
	}
	public boolean scanRow(int row,int col,int player){
		return (scanRowRight(row, col, player)||scanRowLeft(row, col, player));
	}
	public boolean scanCol(int row,int col,int player){
		return (scanColDown(row, col, player)||scanColUp(row, col, player));
	}
	public boolean scanDiag0(int row,int col,int player){
		return (scanDiag0Down(row, col, player)||scanDiag0Up(row, col, player));
	}
	public boolean scanDiag1(int row,int col,int player){
		return (scanDiag1Down(row, col, player)||scanDiag1Up(row, col, player));
	}

	public boolean scanRowRight(int row,int col,int player){
		if(col<7){
			if(gameBoard[row][col+1]==opponent(player)){	
				while((col+2)<=7){
					if(gameBoard[row][col+2]==player){
						return true;
					}else if(gameBoard[row][col+2]==EMPTY||gameBoard[row][col+2]==VALID){
						break;
					}
					col=col+1;
				}
			}
		}
		return false;
	}
	public boolean scanRowLeft(int row,int col,int player){
		if(col>0){
			if(gameBoard[row][col-1]==opponent(player)){	
				while((col-2)>=0){
					if(gameBoard[row][col-2]==player){
						return true;
					}else if(gameBoard[row][col-2]==EMPTY||gameBoard[row][col-2]==VALID){
						break;
					}
					col=col-1;
				}
			}
		}
		return false;
	}

	public boolean scanColDown(int row,int col,int player){
		if(row<7){
			if(gameBoard[row+1][col]==opponent(player)){	
				while((row+2)<=7){
					if(gameBoard[row+2][col]==player){
						return true;
					}else if(gameBoard[row+2][col]==EMPTY){
						break;
					}
					row=row+1;
				}
			}
		}
		return false;
	}
	public boolean scanColUp(int row,int col,int player){
		if(row>0){
			if(gameBoard[row-1][col]==opponent(player)){	
				while((row-2)>=0){
					if(gameBoard[row-2][col]==player){
						return true;
					}else if(gameBoard[row-2][col]==EMPTY){
						break;
					}
					row=row-1;
				}
			}
		}
		return false;
	}

	public boolean scanDiag0Down(int row,int col,int player){
		if(row<7 && col<7){
			if(gameBoard[row+1][col+1]==opponent(player)){	
				while((row+2)<=7 && (col+2)<=7){
					if(gameBoard[row+2][col+2]==player){
						return true;
					}else if(gameBoard[row+2][col+2]==EMPTY){
						break;
					}
					row=row+1;
					col=col+1;
				}
			}
		}
		return false;
	}
	public boolean scanDiag0Up(int row,int col,int player){
		if(row>0 && col>0){
			if(gameBoard[row-1][col-1]==opponent(player)){	
				while((row-2)>=0 && (col-2)>=0){
					if(gameBoard[row-2][col-2]==player){
						return true;
					}else if(gameBoard[row-2][col-2]==EMPTY){
						break;
					}
					row=row-1;
					col=col-1;
				}
			}
		}
		return false;
	}

	public boolean scanDiag1Down(int row,int col,int player){
		if(row<7 && col>0){
			if(gameBoard[row+1][col-1]==opponent(player)){	
				while((row+2)<=7 && (col-2)>=0){
					if(gameBoard[row+2][col-2]==player){
						return true;
					}else if(gameBoard[row+2][col-2]==EMPTY){
						break;
					}
					row=row+1;
					col=col-1;
				}
			}
		}
		return false;
	}
	public boolean scanDiag1Up(int row,int col,int player){
		if(row>0 && col<7){
			if(gameBoard[row-1][col+1]==opponent(player)){	
				while((row-2)>=0 && (col+2)<=7){
					if(gameBoard[row-2][col+2]==player){
						return true;
					}else if(gameBoard[row-2][col+2]==EMPTY){
						break;
					}
					row=row-1;
					col=col+1;
				}
			}
		}
		return false;
	}

    public void print()
	{
		System.out.println("  0 1 2 3 4 5 6 7  ");
		for(int row=0; row<8; row++)
		{
			System.out.print(row + " ");
			for(int col=0; col<8; col++)
			{
				switch (gameBoard[row][col])
				{
					case X:
						System.out.print("X ");
						break;
					case O:
						System.out.print("O ");
						break;
                    case EMPTY:
                        if(isValidMove(row, col))
                        {
							gameBoard[row][col]=VALID;
                            System.out.print("+ ");
                        }else
                        {
                            System.out.print("- ");
                        }
						break;
					case VALID:
						System.out.print("+ ");
						break;
					default:
						break;
                }
			}
			System.out.println(row);
		}
		System.out.println("  0 1 2 3 4 5 6 7  ");
	}

	public boolean isTerminal()
    {
        for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(gameBoard[row][col] == EMPTY)
				{
                    return false;
                }
            }
        }
        return true;
    }
}
