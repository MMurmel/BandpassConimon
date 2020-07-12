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
        for (int i = 0; i < filteredSignal.length; i++) {
            filteredSignal[i] = filter.step(filteredSignal[i]);
        }

        // Aufgabe 4
        //find next power of 2
        int power = 1;
        while (power < filteredSignal.length) power *= 2;
        Complex[] cFilteredSignal = new Complex[power];
        Complex[] cSignal = new Complex[power];
        for (int i = 0; i < cFilteredSignal.length; i++) {
            cFilteredSignal[i] = (i < filteredSignal.length) ? new Complex(filteredSignal[i],0) : new Complex(0,0);
            cSignal[i] = (i < signal.length) ? new Complex(signal[i],0) : new Complex(0,0);

        }

        Complex[] filteredSpectrum = FFT.fft(cFilteredSignal);
        Complex[] Spectrum = FFT.fft(cSignal);

        for (int i = 0; i < filteredSpectrum.length; i++) {
            System.out.println("Index:" + i + "\tOriginal:" + Spectrum[i].re() + "\tFiltered:" + filteredSpectrum[i].re());
        }
    }
}
