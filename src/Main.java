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
        dataToFile(Arrays.stream(signal).boxed().toArray(Double[]::new), "signal", true);

        // Aufgabe 3
        Bandpass filter = new Bandpass(4, 10, 1000, sampleRate);
        double[] filteredSignal = signal.clone();
        for (int i = 0; i < filteredSignal.length; i++) {
            filteredSignal[i] = filter.step(filteredSignal[i]);
        }
        dataToFile(Arrays.stream(filteredSignal).boxed().toArray(Double[]::new), "filtered", true);

        // Aufgabe 4
        //find next power of 2
        int power = 1;
        while (power < filteredSignal.length) power *= 2;
        // initialize arrays for FFT
        Complex[] cSignal = new Complex[power];
        Complex[] cFilteredSignal = new Complex[power];
        for (int i = 0; i < cFilteredSignal.length; i++) {
            cSignal[i] = (i < signal.length) ? new Complex(signal[i],0) : new Complex(0,0);
            cFilteredSignal[i] = (i < filteredSignal.length) ? new Complex(filteredSignal[i],0) : new Complex(0,0);
        }

        // FFT
        Complex[] spectrum = FFT.fft(cSignal);
        Complex[] filteredSpectrum = FFT.fft(cFilteredSignal);

        int limitSpektrum = 20000;
        dataToFile(Arrays.stream(spectrum).limit(limitSpektrum).map(Complex::abs).toArray(Double[]::new), "spectrum",false);
        dataToFile(Arrays.stream(filteredSpectrum).limit(limitSpektrum).map(Complex::abs).toArray(Double[]::new), "filtered_spectrum", false);

        // Aufgabe 5
        // finding maximal values in the spectrum around the expected maxima
        // for original signal and the filtered one
        Double[] amplitudes = Arrays.stream(spectrum).map(Complex::abs).toArray(Double[]::new);
        Double[] fAmplitudes = Arrays.stream(filteredSpectrum).map(Complex::abs).toArray(Double[]::new);

        // using this complicated way instead of Collections.max(…), because the highest value in the unfiltered spectrum isn't necessarily the highest in the
        int low = 0,mid = 100,high = 10000;
        for (int i = 0; i < 10; i++) {
            if (amplitudes[i] > amplitudes[low]) low = i;
        }

        for (int i = 100; i < 120; i++) {
            if (amplitudes[i] > amplitudes[mid]) mid = i;
        }

        for (int i = 10000; i < 14000; i++) {
            if (amplitudes[i] > amplitudes[high]) high = i;
        }

        // calculating dampening
        double dampLow = 10 * Math.log10(fAmplitudes[low] / amplitudes[low]);
        double dampMid = 10 * Math.log10(fAmplitudes[mid] / amplitudes[mid]);
        double dampHigh = 10 * Math.log10(fAmplitudes[high] / amplitudes[high]);

        System.out.println("Low frequency: index:" + low + "\tvalue original:" + amplitudes[low] + " \tvalue filtered" + fAmplitudes[low] + "\tdampening" + dampLow);
        System.out.println("Mid frequency: index:" + mid + "\tvalue original:" + amplitudes[mid] + " \tvalue filtered" + fAmplitudes[mid] + "\tdampening" + dampMid);
        System.out.println("High frequency:index:" + high + "\t2value original:" + amplitudes[high] + " \tvalue filtered" + fAmplitudes[high] + "\tdampening" + dampHigh);


        //--------------------------
        // Aufgabe 6 und 7
        // generating noise
        double[] noise = SignalGenerator.generateNoise(1, sampleRate);
        dataToFile(Arrays.stream(noise).boxed().toArray(Double[]::new), "noise", true);

        // filtering noise (analogous to signal filtering)
        double[] filteredNoise = noise.clone();
        for (int i = 0; i < filteredNoise.length; i++) {
            filteredNoise[i] = filter.step(filteredNoise[i]);
        }
        dataToFile(Arrays.stream(filteredNoise).boxed().toArray(Double[]::new), "filtered_noise",true);

        // FFT for noise and filtered noise
        Complex[] cNoise = new Complex[power];
        Complex[] cFilteredNoise = new Complex[power];
        for (int i = 0; i < cFilteredSignal.length; i++) {
            cNoise[i] = (i < noise.length) ? new Complex(noise[i],0) : new Complex(0,0);
            cFilteredNoise[i] = (i < filteredNoise.length) ? new Complex(filteredNoise[i],0) : new Complex(0,0);
        }

        Complex[] noiseSpectrum = FFT.fft(cNoise);
        Complex[] filteredNoiseSpectrum = FFT.fft(cFilteredNoise);

        dataToFile(Arrays.stream(noiseSpectrum).limit(limitSpektrum).map(Complex::abs).toArray(Double[]::new), "noise_spectrum", false);
        dataToFile(Arrays.stream(filteredNoiseSpectrum).limit(limitSpektrum).map(Complex::abs).toArray(Double[]::new), "filtered_noise_spectrum", false);

    }

    // helper function to write data to a file (used for visualization)
    protected static void dataToFile(Double[] data, String name, boolean scale) throws IOException {
        String fileName = "/home/murmel/IdeaProjects/BandpassConimon/graphics/" + name + ".data";

        new FileWriter(fileName, false).close();
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < data.length; i++) {
            String s = data[i] + "\n";
            bw.write((scale ? (double)i/data.length : i) + "    " + s);
        }

        bw.close();
        fw.close();
    }
}
