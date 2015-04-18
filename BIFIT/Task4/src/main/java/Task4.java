import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class Task4 {

    private static Logger log = Logger.getLogger(Task4.class);

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileInputChannel = null;
        FileChannel fileOutputChannel = null;

        try {
            Calendar time = Calendar.getInstance();
            if (args.length < 2) {
                log.info("ERROR: Need two parametrs");
                return;
            }

            fileInputStream = new FileInputStream(args[0]);
            fileOutputStream = new FileOutputStream(args[1]);
            fileInputChannel = fileInputStream.getChannel();
            fileOutputChannel = fileOutputStream.getChannel();

            fileInputChannel.transferTo(0, fileInputChannel.size(), fileOutputChannel);
            time.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - time.getTimeInMillis());

            String timeText = "Finished in " + time.getTimeInMillis() / 1000 + " seconds";
            log.info(timeText);

        } catch (IOException e) {
            log.error(e);
        } finally {
            try {
                if(fileOutputChannel != null) fileOutputChannel.close();
                if(fileInputChannel != null) fileInputChannel.close();
                if(fileInputStream != null) fileInputStream.close();
                if(fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                log.debug(e);
            }
        }

    }

}
