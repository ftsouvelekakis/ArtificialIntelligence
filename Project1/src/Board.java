public class Board {
    
    public static final int X = 1;
    public static final int O = -1;
    public static final int EMPTY = 0;

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
		gameBoard[row][col] = moveLetter;
		lastMove = new Move(row, col);
		lastLetterPlayed = moveLetter;
	}

    public boolean isValidMove(int row, int col)
	{
        if(row >= 0 && row < 8 && col >= 0 && col < 8 ){
            if(gameBoard[row][col] == EMPTY){
                if(lastLetterPlayed == X){
                    return (scanRow(row, col,O,X)||scanCol(row, col,O,X)||scanDiag0(row,col,O,X)||scanDiag1(row, col,O,X));
                }
                else if(lastLetterPlayed == O){
					return (scanRow(row, col,X,O)||scanCol(row, col,X,O)||scanDiag0(row,col,X,O)||scanDiag1(row, col,X,O));
                }
            }
        }
        return false;
	}

	public boolean scanRow(int row,int col,int player,int opponent){
		if(col<7){
			if(gameBoard[row][col+1]==opponent){	
				while((col+2)<=7){
					if(gameBoard[row][col]==player){
						return true;
					}
					col=col+1;
				}
			}
		}
		if(col>0){
			if(gameBoard[row][col-1]==opponent){	
				while((col-2)>=0){
					if(gameBoard[row][col]==player){
						return true;
					}
					col=col-1;
				}
			}
		}
		return false;
	}

	public boolean scanCol(int row,int col,int player,int opponent){
		if(row<7){
			if(gameBoard[row+1][col]==opponent){	
				while((row+2)<=7){
					if(gameBoard[row][col]==player){
						return true;
					}
					row=row+1;
				}
			}
		}
		if(row>0){
			if(gameBoard[row-1][col]==opponent){	
				while((row-2)>=0){
					if(gameBoard[row][col]==player){
						return true;
					}
					row=row-1;
				}
			}
		}
		return false;
	}

	public boolean scanDiag0(int row,int col,int player,int opponent){
		if(row<7 && col<7){
			if(gameBoard[row+1][col+1]==opponent){	
				while((row+2)<=7 && (col+2)<=7){
					if(gameBoard[row][col]==player){
						return true;
					}
					row=row+1;
					col=col+1;
				}
			}
		}
		if(row>0 && col>0){
			if(gameBoard[row-1][col-1]==opponent){	
				while((row-2)>=0 && (col-2)>=0){
					if(gameBoard[row][col]==player){
						return true;
					}
					row=row-1;
					col=col-1;
				}
			}
		}
		return false;
	}

	public boolean scanDiag1(int row,int col,int player,int opponent){
		if(row<7 && col>0){
			if(gameBoard[row+1][col-1]==opponent){	
				while((row+2)<=7 && (col-2)>=0){
					if(gameBoard[row][col]==player){
						return true;
					}
					row=row+1;
					col=col-1;
				}
			}
		}
		if(row>0 && col<7){
			if(gameBoard[row-1][col+1]==opponent){	
				while((row-2)>=0 && (col+2)<=7){
					if(gameBoard[row][col]==player){
						return true;
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
		System.out.println("  A B C D E F G H  ");
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
                            System.out.print("_ ");
                        }else
                        {
                            System.out.print("- ");
                        }
						break;
					default:
						break;
                }
			}
			System.out.println(row);
		}
		System.out.println("  A B C D E F G H  ");
	}
}
