import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args){
        do{
            try {
                Board b = new Board();
                Reversi.play(b);

            } catch (NoSuchElementException ex) {
                System.out.println("Exiting Game ");
                System.exit(0);
            } catch (NumberFormatException ex1) {
                continue;
            }
        } while(true);
    }
}
