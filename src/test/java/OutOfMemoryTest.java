import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryTest {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(9);
        while (true){
            integers.addAll(integers);
        }
    }
}
