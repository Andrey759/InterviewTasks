import java.math.BigInteger;
import java.util.concurrent.Callable;

public class EvalTask implements Callable<BigInteger> {

    private ExtLib extLib = new ExtLib();
    private long data;
    private int p;

    public EvalTask(ExtLib extLib, long data, int p) {
        this.extLib = extLib;
        this.data = data;
        this.p = p;
    }

    public BigInteger call() throws Exception {
        return extLib.eval(BigInteger.valueOf(data), p);
    }

}
