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
				gameBoard[i][j] = EMPTY;
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
						System.out.print("- ");
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
