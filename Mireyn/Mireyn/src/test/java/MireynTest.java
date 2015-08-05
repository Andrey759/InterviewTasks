import org.junit.Test;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class MireynTest {

    @Test
    public void evaluateTest() throws ExecutionException, InterruptedException {
        BigInteger[] in = Mireyn.evaluate(new long[]{-1L, 0L, 1L, 2L, 3L}, 3);
        assertEquals(in[0].intValue(), -1);
        assertEquals(in[1].intValue(), 0);
        assertEquals(in[2].intValue(), 1);
        assertEquals(in[3].intValue(), 8);
        assertEquals(in[4].intValue(), 27);
    }

}
