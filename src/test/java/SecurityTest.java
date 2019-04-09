import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityTest {
    public static void main(String[] args) throws IOException {
        String filePath = ".";
        String text = "Security not work XD";
        boolean create = true;

        File commandFile = new File(filePath);
        if(create){
            commandFile.setWritable(true, true);
            commandFile.setReadable(true, true);
            commandFile.setExecutable(true, true);
            boolean resultJavaFile = commandFile.createNewFile();
        }

        PrintWriter out = new PrintWriter(commandFile.getAbsolutePath(), "UTF-8");
        out.print(text);
        out.close();
    }
}
