public class driver {
    public static void main(String args[]){
        //takes filename from command line and iterates it.
        Board testy = new Board(args[0]);

        testy.printBoard();

        int count = 55;

        while(count > 0){
            testy.updateBoard();
            testy.printBoard();
            count--;
        }
    }
}
