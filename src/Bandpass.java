// Aufgabe 1
import biz.source_code.dsp.filter.*;

// a wrapper class for IirFilter to implement Bessel bandpass filters
public class Bandpass implements SignalFilter{

    private final IirFilter filter;

    public Bandpass(int filterOrder, int fLow, int fHigh, int sampleRate){
        IirFilterCoefficients coefficients = IirFilterDesignFisher.design(FilterPassType.bandpass, FilterCharacteristicsType.bessel,
                filterOrder, 0, calcFRelative(fLow,sampleRate), calcFRelative(fHigh, sampleRate));
        this.filter = new IirFilter(coefficients);

    }

    @Override
    public double step(double v) {
        return this.filter.step(v);
    }

    public static double calcFRelative(int f, int sampleRate){
        if(f <= 0 || sampleRate <= 0 || 2 * f >= sampleRate) throw new IllegalArgumentException();

        return ((double) f / (double) sampleRate);
    }
}
