import java.util.Scanner;

public class Summ {
    public static void main(String[] args) {
        String data = new Scanner(System.in).nextLine();
        String[] splitData = data.split(" ");
        long first = Integer.parseInt(splitData[0]);
        long second = Integer.parseInt(splitData[1]);
        System.out.println(first + second);
    }
}
