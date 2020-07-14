// Aufgabe 1
import biz.source_code.dsp.filter.*;

// a wrapper class for IirFilter to implement Bessel bandpass filters
public class Bandpass implements SignalFilter{

    private final IirFilter filter;

    public Bandpass(int filterOrder, int fLow, int fHigh, int sampleRate){
        // filter Order gets divided by 2, because this library implements n-th order band pass as
        // high pass and low pass of order n each.
        // contrary to that the task asks for an implementation of n-th order band pass as
        // high pass and low pass of order n/2 each.
        IirFilterCoefficients coefficients = IirFilterDesignFisher.design(FilterPassType.bandpass, FilterCharacteristicsType.bessel,
                Math.floorDiv(filterOrder, 2), 0, calcFRelative(fLow,sampleRate), calcFRelative(fHigh, sampleRate));
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
