import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class PlanetaRuGen {

    private static final String fileName = "Dates100,000,000";
    private static final String beginDate = "01/01/1970 00:00:00";
    private static final String endDate = "24/01/2015 22:03:00";
    private static final long minCount = 8;
    private static final double maxCoeff = 1.1;

    public static void main(String[] args) throws ParseException, IOException {
        long begin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(beginDate).getTime() / 1000;
        long end = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(endDate).getTime() / 1000;
        Random random = new Random();
        long count = (long)Math.pow(10, minCount) + Math.round((long)Math.pow(10, minCount) * (maxCoeff - 1) * random.nextDouble());

        DataOutputStream file = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));
        long date;
        for(long i = 0; i < count; i++) {
            date = random.nextLong();
            file.writeLong(date);
        }
        file.flush();
        file.close();
    }

}
