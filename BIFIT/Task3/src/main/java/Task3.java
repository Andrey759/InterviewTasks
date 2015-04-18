import org.apache.log4j.Logger;
import java.io.*;
import java.util.regex.Pattern;

public class Task3 {

    private static String inputFileName = "Input.txt";
    private static String outputFileName = "Output.txt";
    private static Logger log = Logger.getLogger(Task3.class);

    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            String data = reader.readLine().trim();
            if(!Pattern.compile("^[0-9]{1,9}[+-/*//][0-9]{1,9}$").matcher(data).matches()) {
                writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
                writer.println("ERROR: Incorrect data");
                writer.flush();
                log.info("ERROR: Incorrect data");
                return;
            }
            String stringValue[] = Pattern.compile("[+-/*//]").split(data);
            long value[] = new long[2];
            value[0] = Long.valueOf(stringValue[0]);
            value[1] = Long.valueOf(stringValue[1]);
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
            switch (data.split("[0-9]{1,9}")[1]) {
                case "+":
                    writer.println(value[0] + value[1]);
                    break;
                case "-":
                    writer.println(value[0] - value[1]);
                    break;
                case "*":
                    writer.println(value[0] * value[1]);
                    break;
                case "/":
                    if(value[0] % value[1] == 0)
                        writer.println(value[0] / value[1]);
                    else
                        writer.println((double)value[0] / value[1]);
                    break;
            }
            writer.flush();
        } catch (IOException e) {
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
