import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mireyn {

    private static final int cores = 8;
    private static final ExecutorService EXECUTE_SERVICE = Executors.newFixedThreadPool(cores);
    private static ExtLib extLib = new ExtLib();

    public static BigInteger[] evaluate(long[] data, int p) throws ExecutionException, InterruptedException {
        Future<BigInteger>[] future = new Future[data.length];
        for(int i = 0; i < data.length; i++) {
            future[i] = EXECUTE_SERVICE.submit(new EvalTask(extLib, data[i], p));
        }
        BigInteger[] result = new BigInteger[data.length];
        for(int i = 0; i < data.length; i++) {
            result[i] = future[i].get();
        }
        return result;
    }

}
