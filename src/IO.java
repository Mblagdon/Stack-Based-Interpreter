import java.util.Scanner;

public class IO extends Word {

    public IO(String string) {
        super(string);
    }

    public String in() {
        System.out.println("Please enter a string to echo");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public void out(String s) {
        System.out.println(s);

    }
}
