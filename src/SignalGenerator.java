import java.util.*;

public class SignalGenerator {

    // this class will use mostly java.util.List instead of arrays, since it is easier to handle and can be converted
    // later when needed

    /**
     * @param frequency in Hz (integer)
     * @param amplitude in 1
     * @param duration in seconds (double)
     * @param sampleRate in Hz (integer)
     * @return a discrete sine signal sample of the given specs
     * @throws IllegalArgumentException

     * code influenced by ganeshtiwaridotcomdotnp.blogspot.com/2011/12/java-sound-generate-play-sine-wave.html
     */
    public static List<Double> generateSingleSineWave(int frequency, int amplitude, double duration, int sampleRate) throws IllegalArgumentException{
        if (2 * frequency >= sampleRate) {
            throw new IllegalArgumentException("Sampling rate must be bigger than double the frequency (Nyquist-Shannon)!");
        }

        double samplingInterval = (double) sampleRate / (double) frequency;
        int sampleSize = (int) Math.ceil(duration * sampleRate);

        // this will make wave a fixed size list, which is desirable for this application
        List<Double> wave = Arrays.asList(new Double[sampleSize]);

        for (int i = 0; i < wave.size(); i++){
            double angle = 2.0 * Math.PI * i / samplingInterval;
            wave.set(i, Math.sin(angle) * amplitude);
        }

        return wave;
    }


    public static double[] generateSuperimposingSineWaves(List<Integer> frequencies, int sampleRate) throws IllegalArgumentException{
        if (frequencies == null || frequencies.isEmpty() || Collections.min(frequencies) <= 0) throw new IllegalArgumentException();
        if (sampleRate <= 2 * Collections.max(frequencies)) throw new IllegalArgumentException();

        List<List<Double>> waves = new ArrayList<>();

        // since frequencies are only integers, the longest possibly needed duration is 1 second
        // TODO implement shorter minimum duration (1/gcd(frequencies))
        double minDuration = 1;

        for (int i: frequencies){
            waves.add(generateSingleSineWave(i, 1, minDuration, sampleRate));
        }

        // initializing final result wave
        int sampleSize = (int) Math.ceil(minDuration * sampleRate);

        double[] addedWave = new double[sampleSize];
        Arrays.fill(addedWave, 0);

        for (List<Double> singleWave:waves) {
            for (int i = 0; i < sampleSize; i++){
                addedWave[i] += singleWave.get(i);
            }
        }

        return addedWave;
    }

    // Aufgabe 6
    public static double[] generateNoise(double duration, int sampleRate, double mean, double standardDeviation){
        Random r = new Random();
        int sampleSize = (int) Math.ceil(duration * sampleRate);

        double[] noise = new double[sampleSize];

        for (int i = 0; i < noise.length; i++) {
            noise[i] = r.nextGaussian() * standardDeviation + mean;
        }

        return noise;
    }

    public static double[] generateNoise(double duration, int sampleRate){
        return generateNoise(duration, sampleRate, 0, 1);
    }
}
