import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Aufgabe 2
        Integer[] frequencies = new Integer[]{1, 100, 10000};
        int sampleRate = 96000;
        //int sampleRate = Collections.max(Arrays.asList(frequencies)) * 2 + 1;
        double[] signal = SignalGenerator.generateSuperimposingSineWaves(Arrays.asList(frequencies), sampleRate);

        // Aufgabe 3
        Bandpass filter = new Bandpass(4, 10, 1000, sampleRate);
        double[] filteredSignal = signal.clone();
        Arrays.stream(filteredSignal).forEachOrdered(filter::step);
    }
}
