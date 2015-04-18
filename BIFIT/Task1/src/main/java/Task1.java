import org.apache.log4j.Logger;
import java.io.*;

public class Task1 {

    private static String inputFileName = "Input.txt";
    private static String outputFileName = "Output.txt";
    private static Logger log = Logger.getLogger(Task1.class);

    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            String data = reader.readLine();
            int num = Integer.parseInt(data);
            long result = 1;
            for(int i = 2; i <= num; i++)
                result *= i;
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
            writer.println(result);
            writer.flush();
        } catch (IOException e) {
            log.error(e);
        } catch (NumberFormatException e) {
            log.error(e);
        } finally {
            try {
                if(reader != null) reader.close();
                if(writer != null) writer.close();
            } catch (IOException e) {
                log.info(e);
            }
        }

    }

}