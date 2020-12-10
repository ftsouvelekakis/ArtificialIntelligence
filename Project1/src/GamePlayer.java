import java.util.ArrayList;
import java.util.Random;

public class GamePlayer{
    int maxDepth = 0;
    private int playerLetter;
    
    public GamePlayer()
	{
		maxDepth = 2;
		playerLetter = Board.X;
	}
	
	public GamePlayer(int maxDepth, int playerLetter)
	{
		this.maxDepth = maxDepth;
		this.playerLetter = playerLetter;
    }
    
	public Move MiniMax(Board board)
	{
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if (playerLetter == Board.X)
        {
            return max(new Board(board), 0,alpha,beta);
        }
        else
        {
            return min(new Board(board), 0,alpha,beta);
        }
    }
    
	public Move max(Board board, int depth,int alpha,int beta)
	{
        Random r = new Random();

		if((board.isTerminal()) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
			return lastMove;
        }
        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.X));
        if(children.size()==0){
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            System.out.println("I am children of X and i have not valid moves");
			return lastMove;
        }
		Move maxMove = new Move(Integer.MIN_VALUE);
		for (Board child : children)
		{
            
            Move move = min(child, depth + 1, alpha ,beta);
            
			if(move.getValue() >= maxMove.getValue())
			{
                if ((move.getValue() == maxMove.getValue()))
                {
                    if (r.nextInt(2) == 0)
                    {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else
                {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                }
            }
            
            alpha = maxMove.getValue();
            if(alpha >= beta){
                System.out.println("---------Inside Max - Pruning--------------");
                break;
            }
            if(depth+1==2){
                System.out.println("player"+ board.opponent(board.getLastLetterPlayed() )+"  Max Children at depth " + (depth + 1) + " : ");
                System.out.println("Evalution of max " + child.evaluate());
                child.printWithout();
            }else{
                System.out.println("player"+ board.opponent(board.getLastLetterPlayed() )+"  Max Children at depth " + (depth + 1) );
                System.out.println("Returned eval of max " + maxMove.getValue()+  "alpha : " + alpha + " beta : " + beta);
                child.printWithout();
            }
        }
        System.out.println("player"+ board.opponent(board.getLastLetterPlayed() )+"  Max Parent at depth " + depth + " :" + " and i choose this eval" + maxMove.getValue()+ "alpha : " + alpha + " beta : " + beta);
        board.printWithout();
		return maxMove;
	}

	public Move min(Board board, int depth,int alpha,int beta)
	{
        Random r = new Random();

		if((board.isTerminal()) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
			return lastMove;
        }

        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.O));
        if(children.size()==0){
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            System.out.println("I am children of O and i have not valid moves");
			return lastMove;
        }
		Move minMove = new Move(Integer.MAX_VALUE);
		for (Board child : children)
		{
            
            Move move = max(child, depth + 1,alpha,beta);
			if(move.getValue() <= minMove.getValue())
			{
                if ((move.getValue() == minMove.getValue()))
                {
                    if (r.nextInt(2) == 0)
                    {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else
                {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                }
            }
            
            beta = minMove.getValue();
            if(alpha >= beta){
                System.out.println("---------Inside Min - Pruning--------------");
                break;
            }
            if(depth+1==2){
                System.out.println("player: "+ board.opponent(board.getLastLetterPlayed() )+"  min Children at depth " + (depth + 1) + " : ");
                System.out.println("Evalution of min " + child.evaluate());
                child.printWithout();
            }else{
                System.out.println("player: "+ board.opponent(board.getLastLetterPlayed() )+"  min Children at depth " + (depth + 1) + " : ");
                System.out.println("eval returned " + minMove.getValue()+ "alpha : " + alpha + " beta : " + beta);
                child.printWithout();
            }
        }
        System.out.println("player : "+ board.opponent(board.getLastLetterPlayed() ) + "Min Parent at depth " + depth + " :" + " and i choose this eval" + minMove.getValue() + "alpha : " + alpha + " beta : " + beta);
        board.printWithout();
        return minMove;
    }

}