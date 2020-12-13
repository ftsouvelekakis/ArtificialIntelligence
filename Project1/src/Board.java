import java.util.ArrayList;
public class Board{
	
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
	
	private int tempRow;
	private int tempCol;
	private int countX;
	private int countO;
	private int countNoValid;
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
			for(int j=0; j<8; j++) // initialise board
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

	//check if given player has valid moves 

	public boolean noValidMoves(int moveLetter)
	{
		if (validRowsHelper.size() == 0)
		{
			lastLetterPlayed = moveLetter;
			countNoValid++;
			return true;
		}
		else
		{
			countNoValid=0;
			return false;
		}
	}

	// find all the moves whick are available at current board

	public void findAllValidMoves(int player)
	{
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

	// check if move is legal for given row,col and player

	public boolean isValidMove(int row, int col,int player)
	{	
		if(gameBoard[row][col]==EMPTY)
		{
			return (scan8Dim(row,col,player));
		}
		return false;
	}

	//chech if board is funal stage

	public boolean isTerminal()
	{
		if(countNoValid == 2)
		{
			return true;
		}
		for(int row = 0; row < 8; row++)
		{
			for(int col = 0; col < 8; col++)
			{
				if(gameBoard[row][col] == EMPTY)
				{
					return false;
				}
			}
		}
		return true;
	}

	// prints board

	public void print()
	{
		countX = 0; 	//count the disks for each player
		countO = 0;
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
						if(isValidMove(row, col, opponent(lastLetterPlayed)))
						{
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

	public int evaluate(int player)
	{
		if (getDisksOnBoard() <= 20) {
			// Opening game
			return 5*mobility() + 20*squareWeight(player) + 10000*corners(player);
		}
		else if (getDisksOnBoard() <= 58) {
			// Midgame
			return 10*diskDifference() + 2*mobility() + 10*squareWeight(player) + 10000*corners(player);
		}
		else {
			// Endgame
			return 500*diskDifference() + 10000*corners(player);
		}
	}

	public int squareWeight(int player){
		
		int weights[][]={{120,-20,20,5,5,20,-20,120},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
					  	{20,-5,15,3,3,15,-5,20},
					  	{5,-5,3,3,3,3,-5,5},
					  	{5,-5,3,3,3,3,-5,5},
					  	{20,-5,15,3,3,15,-5,20},
					  	{-20,-40,-5,-5,-5,-5,-40,-20},
						  {120,-20,20,5,5,20,-20,120}};
		if(gameBoard[0][0] !=0 ){
			for(int i = 0; i<4; i++){
				for(int j = 0; j<4; j++){
					weights[i][j]=0;
				}
			}
		}
		if(gameBoard[0][7] !=0 ){
			for(int i = 0; i<4; i++){
				for(int j = 7; j>=4; j--){
					weights[i][j]=0;
				}
			}
		}
		if(gameBoard[7][0] !=0 ){
			for(int i = 7; i>=4; i--){
				for(int j = 0; j<4; j++){
					weights[i][j]=0;
				}
			}
		}
		if(gameBoard[7][7] !=0 ){
			for(int i = 7; i>=4; i--){
				for(int j = 7; j>=4; j--){
					weights[i][j]=0;
				}
			}
		}
		
		int sumX = 0;
		int sumO = 0;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++) {
				if(gameBoard[i][j] == X){
					sumX += weights[i][j];
				}
				else if (gameBoard[i][j] == O)
				{
					sumO +=  weights[i][j];
				}
			}
		}
		if (player == 1) {
			return sumX;
		}else{
			return -1*sumO;
		}
	}

	public int diskDifference(){  //calculates whick player have more disks
		countX = 0;
		countO = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if (gameBoard[i][j]==1){
					countX++;
				}else if(gameBoard[i][j]==-1){
					countO++;
				}
			}
		}
        return 100 * (countX - countO) / (countX + countO+1);
    	
	}

	public int mobility(){  // less available moves for opponent is better for player
		int xMoves = 0;
		int oMoves = 0;
		findAllValidMoves(1);
		xMoves = validRowsHelper.size();

		findAllValidMoves(-1);
		oMoves = validRowsHelper.size();

		
		return 100 * (xMoves - oMoves) / (xMoves + oMoves + 1);
		
	}

	public int corners(int player) {  // give better a player with more corners
		int xCorners = 0;
		int oCorners = 0;
		if (gameBoard[0][0] == 1) {
			xCorners++;
		}
		else if (gameBoard[0][0] == -1) {
			oCorners++;
		}
		if (gameBoard[0][7] == 1) {
			xCorners++;
		}
		else if (gameBoard[0][7] == -1) {
			oCorners++;
		}
		if (gameBoard[7][0] == 1) {
			xCorners++;
		}
		else if (gameBoard[7][0] == -1) {
			oCorners++;
		}
		if (gameBoard[7][7] == 1) {
			xCorners++;
		}
		else if (gameBoard[7][7] == -1) {
			oCorners++;
		}
		
		return 100 * (xCorners - oCorners) / (xCorners + oCorners + 1);
		
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

	//check which paths from given position are valid

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

	//check valid paths and flip the diskss for the given player

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

	//----------getters and setters---------
	public int getDisksOnBoard(){
		int disksOnBoard = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(gameBoard[i][j] != EMPTY){
					disksOnBoard++;
				}
			}
		}
		return disksOnBoard;
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

	public int getCountX(){
		return countX;
	}

	public int getCountO(){
		return countO;
	}

	public int getCountNoValid(){
		return countNoValid;
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
