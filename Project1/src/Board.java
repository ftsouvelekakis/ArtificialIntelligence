import java.util.ArrayList;
public class Board{
	
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
	public static final int VALID = -2;
	
	int tempRow;
	int tempCol;
	int countX;
	int countO;
	int countNoValid;
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
		validRowsHelper = board.validRowsHelper;
		validColsHelper = board.validColsHelper;
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
		countX=0;
		countO=0;
		System.out.println("\n  0 1 2 3 4 5 6 7  ");
		for(int row=0; row<8; row++)
		{
			System.out.print(row + " ");
			for(int col=0; col<8; col++)
			{
				switch (gameBoard[row][col])
				{
					case X:
						System.out.print("X ");
						countX++;
						break;
					case O:
						System.out.print("O ");
						countO++;
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
		System.out.println("\nX:" +countX + " O:"+countO);
	}

	public boolean isTerminal()
	{
		if(countNoValid==2){
			return true;
		}
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(gameBoard[row][col] == EMPTY || gameBoard[row][col] == VALID)
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

	public boolean noValidMoves(int moveLetter){
		if (validRowsHelper.size()==0){
			if(moveLetter==O)
				System.out.println("Player O has no valid moves" );
			else
			{
				System.out.println("Player X has no valid moves" );
			}
			lastLetterPlayed=moveLetter;
			countNoValid++;
			return false;
		}
		else{
			countNoValid=0;
			return true;
		}
	}

	public void setValidHelper(int row,int col){
		validColsHelper.add(col);
		validRowsHelper.add(row);
		gameBoard[row][col]=VALID;
	}

	public boolean isValidMove(int row, int col)
	{	
		if(gameBoard[row][col]==EMPTY){
			return (scan8Dim(row,col,opponent(lastLetterPlayed)));
		}
		return false;
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

	public int evaluate()
	{
		int weights[][]={{120,-20,20,5,5,20,-20,120},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{20,-5,15,3,3,15,-5,20},
					  	{5,-5,3,3,3,3,-5,5},
					  	{5,-5,3,3,3,3,-5,5},
					  	{20,-5,15,3,3,15,-5,20},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{120,-20,20,5,5,20,-20,120}};
		int sum = 0;
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++) {
				if(gameBoard[i][j] == opponent(lastLetterPlayed))
					sum += weights[i][j];
			}
		return sum;
	}

	public ArrayList<Board> getChildren(int letter)
	{
		ArrayList<Board> children = new ArrayList<Board>();
		
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				this.resetScanFlags();
				if(this.isValidMove(row, col))
				{
					Board child = new Board(this);
					child.gameBoard[row][col]=VALID;
					child.validRowsHelper.add(row);
					child.validColsHelper.add(col);
					child.makeMove(row, col, letter);
					children.add(child);
				}
			}
		}
		return children;
	}


}
