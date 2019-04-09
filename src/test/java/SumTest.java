import java.util.Scanner;

public class SumTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();
        int first = Integer.parseInt(data.split(" ")[0]);
        int second = Integer.parseInt(data.split(" ")[1]);
        System.out.println(first + second);
    }
}
