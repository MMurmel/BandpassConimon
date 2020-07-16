import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class SignalGeneratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument(){
        // sample rate to small
        SignalGenerator.generateSingleSineWave(10, 1, 1, 10);

        List<Integer> frequencies = Arrays.asList(1,2,7);
        double[] actual = SignalGenerator.generateSuperimposingSineWaves(frequencies,10);
    }

    @Test
    public void testSingleSineWave(){
        double[] expected = {0.0, 0.5878, 0.9511, 0.9511, 0.5878, 0, -0.5878, -0.9511, -0.9511, -0.5878};
        double[] actual = new double[expected.length];
        List<Double> signal = SignalGenerator.generateSingleSineWave(1, 1, 1, 10);
        for (int i = 0; i < actual.length; i++) {
            actual[i] = signal.get(i);
        }
        Assert.assertArrayEquals(expected, actual, 0.0001);
    }

    @Test
    public void testSuperimposingWave(){
        double[] expected = {0.0, 2.4899, 0.9511, -0.2245, 0.5878, 0, -0.5878, 0.2245, -0.9511, -2.4899};
        List<Integer> frequencies = Arrays.asList(1,2,3);
        double[] actual = SignalGenerator.generateSuperimposingSineWaves(frequencies,10);
        Assert.assertArrayEquals(expected, actual, 0.0001);

    }
}
