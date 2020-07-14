import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        // Aufgabe 2
        Integer[] frequencies = new Integer[]{1, 100, 10000};
        int sampleRate = 96000;
        //int sampleRate = Collections.max(Arrays.asList(frequencies)) * 2 + 1;
        double[] signal = SignalGenerator.generateSuperimposingSineWaves(Arrays.asList(frequencies), sampleRate);

        dataToFile(Arrays.stream(signal).boxed().toArray(Double[]::new), "signal");

        // Aufgabe 3
        Bandpass filter = new Bandpass(4, 10, 1000, sampleRate);
        double[] filteredSignal = signal.clone();
        for (int i = 0; i < filteredSignal.length; i++) {
            filteredSignal[i] = filter.step(filteredSignal[i]);
        }

        dataToFile(Arrays.stream(filteredSignal).boxed().toArray(Double[]::new), "filtered");

        // Aufgabe 4
        //find next power of 2
        int power = 1;
        while (power < filteredSignal.length) power *= 2;
        // initialize arrays for FFT
        Complex[] cSignal = new Complex[power];
        Complex[] cFilteredSignal = new Complex[power];
        for (int i = 0; i < cFilteredSignal.length; i++) {
            cFilteredSignal[i] = (i < filteredSignal.length) ? new Complex(filteredSignal[i],0) : new Complex(0,0);
            cSignal[i] = (i < signal.length) ? new Complex(signal[i],0) : new Complex(0,0);

        }
        // FFT
        Complex[] spectrum = FFT.fft(cSignal);
        Complex[] filteredSpectrum = FFT.fft(cFilteredSignal);


        dataToFile(Arrays.stream(spectrum).map(Complex::abs).toArray(Double[]::new), "spectrum");
        dataToFile(Arrays.stream(filteredSpectrum).map(Complex::abs).toArray(Double[]::new), "filtered_spectrum");

    }

    // helper function to write data to a file (used for visualization)
    protected static void dataToFile(Double[] data, String name) throws IOException {
        String fileName = "/home/murmel/IdeaProjects/BandpassConimon/graphics/" + name + ".data";

        new FileWriter(fileName, false).close();
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < data.length; i++) {
            String s = data[i] + "\n";
            bw.write(i + "    " + s);
        }

        bw.close();
        fw.close();
    }
}
