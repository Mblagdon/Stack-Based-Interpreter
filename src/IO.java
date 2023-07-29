import java.util.Scanner;
/**
 * IO class extends Word
 * This class is used for input/output operations
 *
 * @author MBlagdon
 */
public class IO extends Word {
    /**
     * IO constructor
     * @param string to be use for IO operation
     */
    public IO(String string) {
        super(string);
    }
    /**
     * Method is used for input operation
     * Prompts the user to enter a string and returns it
     *
     * @return string input by the user
     */
    public String in() {
        System.out.println("Please enter a string to echo");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
    /**
     * Method is used for output operation
     * Prints the given string to the standard output
     *
     * @param string to be output
     */
    public void out(String string) {
        System.out.println(string);

    }
}
