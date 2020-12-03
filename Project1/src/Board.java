import java.util.ArrayList;

public class Board {
	
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
	public static final int VALID = -2;
	
	int tempRow;
	int tempCol;
	boolean scanRowLeft=false;
	boolean scanRowRight=false;
	boolean scanColDown=false;
	boolean scanColUp=false;
	boolean scanDiag0Down=false;
	boolean scanDiag0Up=false;
	boolean scanDiag1Down=false;
	boolean scanDiag1Up=false;
	private Move lastMove;
	private int lastLetterPlayed;
	private int [][] gameBoard;
	private ArrayList<Integer> validRowsHelper;
	private ArrayList<Integer> validColsHelper;
	
	public Board()
	{
		lastMove = new Move();
		lastLetterPlayed = O;
		gameBoard = new int[8][8];
		validRowsHelper = new ArrayList<>();
		validColsHelper = new ArrayList<>();
		tempRow=0;
		tempCol=0;
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
		this.validRowsHelper = board.validRowsHelper;
		this.validColsHelper = board.validColsHelper;
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
		if(gameBoard[row][col]==VALID){
			gameBoard[row][col] = moveLetter;
			lastMove = new Move(row, col);
			lastLetterPlayed = moveLetter;
			resetValidMoves();
			flip8Dim(row,col,moveLetter);
		}
		else
		{
			System.out.println("Your move is not valid please try again\n");
		}
	}

	public void flip8Dim(int row,int col,int moveLetter){
		tempRow=row;
		tempCol=col;
		resetScanFlags();
		scan8Dim(row, col, moveLetter);
		if(scanRowRight){
			while(gameBoard[row][col+1]!=moveLetter){
				gameBoard[row][col+1]=moveLetter;
				col++;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanRowLeft){
			while(gameBoard[row][col-1]!=moveLetter){
				gameBoard[row][col-1]=moveLetter;
				col--;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanColDown){
			while(gameBoard[row+1][col]!=moveLetter){
				gameBoard[row+1][col]=moveLetter;
				row++;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanColUp){
			while(gameBoard[row-1][col]!=moveLetter){
				gameBoard[row-1][col]=moveLetter;
				row--;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanDiag0Down){
			while(gameBoard[row+1][col+1]!=moveLetter){	
				gameBoard[row+1][col+1]=moveLetter;
				col++;
				row++;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanDiag0Up){
			while(gameBoard[row-1][col-1]!=moveLetter){	
				gameBoard[row-1][col-1]=moveLetter;
				col--;
				row--;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanDiag1Down){
			while(gameBoard[row+1][col-1]!=moveLetter){	
				gameBoard[row+1][col-1]=moveLetter;
				col--;
				row++;
			}
		}
		row=tempRow;
		col=tempCol;
		if(scanDiag1Up){
			while(gameBoard[row-1][col+1]!=moveLetter){	
				gameBoard[row-1][col+1]=moveLetter;
				col++;
				row--;
			}
		}
	}

 	public boolean scan8Dim(int row,int col,int player){
		tempRow=row;
		tempCol=col;
		
		if(col<7){
			if(gameBoard[row][col+1]==opponent(player)){	
				while((col+2)<=7){
					if(gameBoard[row][col+2]==player){
						scanRowRight=true;
						break;
					}else if(gameBoard[row][col+2]==opponent(player)){
						col=col+1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(col>0){
			if(gameBoard[row][col-1]==opponent(player)){	
				while((col-2)>=0){
					if(gameBoard[row][col-2]==player){
						scanRowLeft=true;
						break;
					}else if(gameBoard[row][col-2]==opponent(player)){
						col=col-1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(row<7){
			if(gameBoard[row+1][col]==opponent(player)){	
				while((row+2)<=7){
					if(gameBoard[row+2][col]==player){
						scanColDown=true;
						break;
					}else if(gameBoard[row+2][col]==opponent(player)){
						row=row+1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(row>0){
			if(gameBoard[row-1][col]==opponent(player)){	
				while((row-2)>=0){
					if(gameBoard[row-2][col]==player){
						scanColUp=true;
						break;
					}else if(gameBoard[row-2][col]==opponent(player)){
						row=row-1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(row<7 && col<7){
			if(gameBoard[row+1][col+1]==opponent(player)){	
				while((row+2)<=7 && (col+2)<=7){
					if(gameBoard[row+2][col+2]==player){
						scanDiag0Down=true;
						break;
					}else if(gameBoard[row+2][col+2]==opponent(player)){
						row=row+1;
						col=col+1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(row>0 && col>0){
			if(gameBoard[row-1][col-1]==opponent(player)){	
				while((row-2)>=0 && (col-2)>=0){
					if(gameBoard[row-2][col-2]==player){
						scanDiag0Up=true;
						break;
					}else if(gameBoard[row-2][col-2]==opponent(player)){
						row=row-1;
						col=col-1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(row<7 && col>0){
			if(gameBoard[row+1][col-1]==opponent(player)){	
				while((row+2)<=7 && (col-2)>=0){
					if(gameBoard[row+2][col-2]==player){
						scanDiag1Down=true;
						break;
					}else if(gameBoard[row+2][col-2]==opponent(player)){
						row=row+1;
						col=col-1;
					}else{
						break;
					}
				}
			}
		}

		row=tempRow;
		col=tempCol;

		if(row>0 && col<7){
			if(gameBoard[row-1][col+1]==opponent(player)){	
				while((row-2)>=0 && (col+2)<=7){
					if(gameBoard[row-2][col+2]==player){
						scanDiag1Up=true;
						break;
					}else if(gameBoard[row-2][col+2]==opponent(player)){
						row=row-1;
						col=col+1;
					}else{
						break;
					}
				}
			}
		}

		return (scanRowRight||scanRowLeft||scanColDown||scanColUp||scanDiag0Down||scanDiag0Up||scanDiag1Down||scanDiag1Up);
	}

	public int opponent(int player)
	{
		if (player == 1){
			return -1;
		}
		else{
			return 1;
		}
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
						resetScanFlags();
                        if(isValidMove(row, col))
                        {
							gameBoard[row][col]=VALID;
							validRowsHelper.add(row);
							validColsHelper.add(col);
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

	public void resetScanFlags(){
		scanRowLeft=false;
		scanRowRight=false;
		scanColDown=false;
		scanColUp=false;
		scanDiag0Down=false;
		scanDiag0Up=false;
		scanDiag1Down=false;
		scanDiag1Up=false;
	}

	public boolean isValidMove(int row, int col)
	{
		return (scan8Dim(row,col,opponent(lastLetterPlayed)));
	}

	public void resetValidMoves()
	{
		for (int i = 0; i < validColsHelper.size(); i++)
		{
			if(getGameBoard()[validRowsHelper.get(i)][validColsHelper.get(i)]==VALID){
				getGameBoard()[validRowsHelper.get(i)][validColsHelper.get(i)] = EMPTY;
			}
		}
		validRowsHelper.clear();
		validColsHelper.clear();
	}
}
