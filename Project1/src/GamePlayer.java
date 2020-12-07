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
        if (playerLetter == Board.X)
        {
            return max(new Board(board), 0);
        }
        else
        {
            return min(new Board(board), 0);
        }
    }
    
	public Move max(Board board, int depth)
	{
        Random r = new Random();

		if((board.isTerminal()) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
			return lastMove;
        }
        
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.X));
		Move maxMove = new Move(Integer.MIN_VALUE);
		for (Board child : children)
		{
            Move move = min(child, depth + 1);
            
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
        }
        
		return maxMove;
	}

	public Move min(Board board, int depth)
	{
        Random r = new Random();

		if((board.isTerminal()) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
			return lastMove;
		}
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.O));
		Move minMove = new Move(Integer.MAX_VALUE);
		for (Board child : children)
		{
			Move move = max(child, depth + 1);
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
        }
        return minMove;
    }

}