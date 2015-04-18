import org.apache.log4j.Logger;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {

    private static String inputFileName = "Input.txt";
    private static String outputFileName = "Output.txt";
    private static Logger log = Logger.getLogger(Task2.class);

    private static long getCount(String filename, String word) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            long count = 0;
            Pattern pattern = Pattern.compile(word);
            String line = reader.readLine();
            while(line != null) {
                Matcher matcher = pattern.matcher(line);
                while(matcher.find())
                    count++;
                line = reader.readLine();
            }
            return count;
        } catch (IOException e) {
            log.error(e);
            return -1;
        } finally {
            try {
                if(reader != null) reader.close();
            } catch (IOException e) {
                log.info(e);
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
            String data = reader.readLine();
            String tmp[] = data.split(" ");
            if(tmp.length < 2) {
                log.error("ERROR: Need file name and word");
                System.exit(0);
            }
            String fileName = tmp[0];
            String word = tmp[1];
            long result = getCount(fileName, word);
            if(result != -1) {
                writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
                writer.println(result);
                writer.flush();
            }
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
