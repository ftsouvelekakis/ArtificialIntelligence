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
	boolean scanRowLeft;
	boolean scanRowRight;
	boolean scanColDown;
	boolean scanColUp;
	boolean scanDiag0Down;
	boolean scanDiag0Up;
	boolean scanDiag1Down;
	boolean scanDiag1Up;
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
	
	public void makeMove(int row, int col, int moveLetter)
	{
		if(isValidMove(row, col, moveLetter)){
			gameBoard[row][col] = moveLetter;
			lastMove = new Move(row, col);
			lastLetterPlayed = moveLetter;
			flip8Dim(row,col,moveLetter);
		}
		else
		{
			System.out.println("Your move is not valid please try again\n");
		}
	}

	public boolean noValidMoves(int moveLetter){
		if (validRowsHelper.size()==0){
			lastLetterPlayed=moveLetter;
			countNoValid++;
			return true;
		}
		else{
			countNoValid=0;
			return false;
		}
	}

	public void findAllValidMoves(int player){
		validRowsHelper.clear();
		validColsHelper.clear();
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(isValidMove(i,j,player))
				{
					validRowsHelper.add(i);
					validColsHelper.add(j);
				}
			}
		}
	}

	public boolean isValidMove(int row, int col,int player)
	{	
		if(gameBoard[row][col]==EMPTY){
			return (scan8Dim(row,col,player));
		}
		return false;
	}

	

	public boolean isTerminal()
	{
		if(countNoValid==2)
		{
			return true;
		}
		for(int row = 0; row < 8; row++)
		{
			for(int col = 0; col < 8; col++)
			{
				if(gameBoard[row][col] == EMPTY || gameBoard[row][col] == VALID)
				{
					return false;
				}
			}
		}
		return true;
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
						if(isValidMove(row, col, opponent(lastLetterPlayed))){
							System.out.print("+ ");
						}
						else{
							System.out.print("- ");
						}
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

	public int evaluate()
	{
		//System.out.println("i am evaluation");
		int weights[][]={{120,-20,20,5,5,20,-20,120},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{20,-5,15,3,3,15,-5,20},
					  	{5,-5,3,3,3,3,-5,5},
					  	{5,-5,3,3,3,3,-5,5},
					  	{20,-5,15,3,3,15,-5,20},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{120,-20,20,5,5,20,-20,120}};
		int sum = 0;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++) {
				if(gameBoard[i][j] == X){
					sum += weights[i][j];
				}
				else if (gameBoard[i][j] == O)
				{
					sum -=  weights[i][j];
				}
			}
		}
		return sum;
	}

	public int evaluateM()
	{
		System.out.println("I am mobility evaluation");
		int weights[][]={{120,-20,20,5,5,20,-20,120},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{20,-5,15,3,3,15,-5,20},
					  	{5,-5,3,3,3,3,-5,5},
					  	{5,-5,3,3,3,3,-5,5},
					  	{20,-5,15,3,3,15,-5,20},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{120,-20,20,5,5,20,-20,120}};
		int sum = 0;
		int remainMoves =0;
		int myCoins = 0;
		int oppCoins = 0;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++) {
				if(gameBoard[i][j] == X){
					sum += weights[i][j];
					myCoins++;
				}
				else if (gameBoard[i][j] == O)
				{
					sum -=  weights[i][j];
					oppCoins++;
				}else{
					remainMoves++;
				}
			}
		}
		/*double m= 0;
		int myValidMoves = 0; 
		int oppValidMoves = 0;
		for(int i=0; i<8; i++){
			for (int j=0; j<8; j++){
				resetScanFlags();
				if(isValidMove(i, j, X)){
					myValidMoves++;
				}else if(isValidMove(i, j, O)){
					oppValidMoves++;
				}
			}
		}
		if(myValidMoves > oppValidMoves){
			m = (100.0 * myValidMoves)/(myValidMoves + oppValidMoves);
		}else if(myValidMoves < oppValidMoves){
			m = -(100.0 * oppValidMoves)/(myValidMoves + oppValidMoves);
		}else{
			m = 0;
		}
		double c = 0;
		c = 100* (myCoins - oppCoins)/(myCoins + oppCoins);

		if(remainMoves>2){
			return ((int) m * 5 + sum * 10 );
		}else{
			return ((int) m * 1 + sum * 5 + (int) c * 10);
		}*/
		return sum;
		
		
	}

	public int opponent(int player)
	{
		if (player == 1)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	public ArrayList<Board> getChildren(int letter)
	{
		ArrayList<Board> children = new ArrayList<Board>();	
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(this.isValidMove(row, col,letter))
				{
					Board child = new Board(this);
					child.makeMove(row, col, letter);
					children.add(child);
				}
			}
		}
		return children;
	}

	public boolean scan8Dim(int row,int col,int player)
	{
		tempRow = row; // save row and col in order to restore afterwards
		tempCol = col;
		scanRowLeft = false;  // reset scan flags
		scanRowRight = false;
		scanColDown = false;
		scanColUp = false;
		scanDiag0Down = false;
		scanDiag0Up = false;
		scanDiag1Down = false;
		scanDiag1Up = false;

		// Scan all dimensions, if a dimension is valid path then return true

		if(col < 7)
		{
			if(gameBoard[row][col+1] == opponent(player))
			{	
				while( (col+2) <= 7)
				{
					if(gameBoard[row][col+2] == player)
					{
						scanRowRight=true;
						break;
					}
					else if(gameBoard[row][col+2] == opponent(player))
					{
						col++;
					}
					else
					{
						break;
					}
				}
			}
		}

		row=tempRow; // restore row and col
		col=tempCol;

		if(col > 0)
		{
			if(gameBoard[row][col-1] == opponent(player))
			{	
				while((col-2) >= 0)
				{
					if(gameBoard[row][col-2] == player)
					{
						scanRowLeft = true;
						break;
					}
					else if(gameBoard[row][col-2] == opponent(player))
					{
						col--;
					}
					else
					{
						break;
					}
				}
			}
		}

		row = tempRow;
		col = tempCol;

		if(row < 7)
		{
			if(gameBoard[row+1][col] == opponent(player))
			{	
				while((row+2) <= 7)
				{
					if(gameBoard[row+2][col] == player)
					{
						scanColDown = true;
						break;
					}
					else if(gameBoard[row+2][col] == opponent(player))
					{
						row++;
					}
					else
					{
						break;
					}
				}
			}
		}

		row = tempRow;
		col = tempCol;

		if(row > 0)
		{
			if(gameBoard[row-1][col] == opponent(player))
			{	
				while((row-2) >= 0)
				{
					if(gameBoard[row-2][col] == player)
					{
						scanColUp = true;
						break;
					}
					else if(gameBoard[row-2][col] == opponent(player))
					{
						row--;
					}
					else
					{
						break;
					}
				}
			}
		}

		row = tempRow;
		col = tempCol;

		if(row<7 && col<7)
		{
			if(gameBoard[row+1][col+1] == opponent(player))
			{	
				while((row+2) <= 7 && (col+2) <= 7)
				{
					if(gameBoard[row+2][col+2] == player)
					{
						scanDiag0Down = true;
						break;
					}
					else if(gameBoard[row+2][col+2]==opponent(player))
					{
						row++;
						col++;
					}
					else
					{
						break;
					}
				}
			}
		}

		row = tempRow;
		col = tempCol;

		if(row > 0 && col > 0)
		{
			if(gameBoard[row-1][col-1] == opponent(player))
			{	
				while((row-2) >= 0 && (col-2) >= 0)
				{
					if(gameBoard[row-2][col-2] == player)
					{
						scanDiag0Up = true;
						break;
					}
					else if(gameBoard[row-2][col-2] == opponent(player))
					{
						row--;
						col--;
					}
					else
					{
						break;
					}
				}
			}
		}

		row = tempRow;
		col = tempCol;

		if(row < 7 && col > 0)
		{
			if(gameBoard[row+1][col-1] == opponent(player))
			{	
				while((row+2) <= 7 && (col-2) >= 0)
				{
					if(gameBoard[row+2][col-2] == player)
					{
						scanDiag1Down = true;
						break;
					}
					else if(gameBoard[row+2][col-2] == opponent(player))
					{
						row++;
						col--;
					}
					else
					{
						break;
					}
				}
			}
		}

		row = tempRow;
		col = tempCol;

		if(row > 0 && col < 7){
			if(gameBoard[row-1][col+1] == opponent(player))
			{	
				while((row-2) >= 0 && (col+2) <= 7)
				{
					if(gameBoard[row-2][col+2] == player)
					{
						scanDiag1Up = true;
						break;
					}
					else if(gameBoard[row-2][col+2] == opponent(player))
					{
						row--;
						col++;
					}
					else
					{
						break;
					}
				}
			}
		}
		return (scanRowRight||scanRowLeft||scanColDown||scanColUp||scanDiag0Down||scanDiag0Up||scanDiag1Down||scanDiag1Up);
	}

	public void flip8Dim(int row,int col,int moveLetter)
	{
		tempRow  =row; //save row and col
		tempCol = col;

		scan8Dim(row, col, moveLetter); // Check which dimension is valid path in order to flip the disks

		// Flip all true dimensions

		if(scanRowRight)
		{
			while(gameBoard[row][col+1] != moveLetter)
			{
				gameBoard[row][col+1] = moveLetter;
				col++;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanRowLeft)
		{
			while(gameBoard[row][col-1] != moveLetter)
			{
				gameBoard[row][col-1] = moveLetter;
				col--;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanColDown)
		{
			while(gameBoard[row+1][col] != moveLetter)
			{
				gameBoard[row+1][col] = moveLetter;
				row++;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanColUp)
		{
			while(gameBoard[row-1][col] != moveLetter)
			{
				gameBoard[row-1][col] = moveLetter;
				row--;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanDiag0Down)
		{
			while(gameBoard[row+1][col+1] != moveLetter)
			{	
				gameBoard[row+1][col+1] = moveLetter;
				col++;
				row++;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanDiag0Up)
		{
			while(gameBoard[row-1][col-1] != moveLetter)
			{	
				gameBoard[row-1][col-1] = moveLetter;
				col--;
				row--;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanDiag1Down)
		{
			while(gameBoard[row+1][col-1] != moveLetter)
			{	
				gameBoard[row+1][col-1] = moveLetter;
				col--;
				row++;
			}
		}

		row = tempRow;
		col = tempCol;

		if(scanDiag1Up)
		{
			while(gameBoard[row-1][col+1] != moveLetter)
			{	
				gameBoard[row-1][col+1] = moveLetter;
				col++;
				row--;
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

	public void setValidHelper(int row,int col){
		validColsHelper.add(col);
		validRowsHelper.add(row);
	}

	public ArrayList<Integer> getValidRowsHelper(){
		return this.validRowsHelper;
	}

	public ArrayList<Integer> getValidColsHelper(){
		return this.validColsHelper;
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
}
