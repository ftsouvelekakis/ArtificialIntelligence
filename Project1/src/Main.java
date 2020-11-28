import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner userInputReader = new Scanner(System.in);
        int maxDepth = 0;
        int turn = -1;

        System.out.println("-------------Othello Game-------------\n");
        Main app = new Main();
        
        maxDepth = app.readDepth(userInputReader, maxDepth);
        turn = app.readTurn(userInputReader, turn);
        
        userInputReader.close();    
    }

    public int readDepth(Scanner input, int maxDepth){
        while(true){
            System.out.print("Please select minimax algorithm depth (Valid input > 0) : ");
            try { 
                
                maxDepth = Integer.parseInt(input.nextLine());
                if(maxDepth>0) 
                {
                    System.out.println("\nSelected depth is : " + maxDepth);  
                    break;
                }
                else{
                    System.out.println("\n-----Selected depth should be positive integer-----\n");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("\n-----Wrong input please select a positive integer-----\n");
            }
            
        }
        return maxDepth;
    }

    public int readTurn(Scanner input,int turn){
        while(turn!=1 && turn!=0){
            System.out.print("\nDo you want play first? Select 1 (Yes) or 0 (No) : "); 
            try {
                turn = Integer.parseInt(input.nextLine());
                if (turn==1){
                    System.out.println("\nHuman starts");
                    break;
                }
                else if(turn==0){
                    System.out.println("\nAI starts");
                    break;
                }
                else{
                    System.out.println("\n-----Wrong input please select 1 or 0-----");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("\n-----Wrong input please select 1 or 0-----");
            }
        }
        return turn;
    }
}