
public class CalculateSyTheor {

    private double phase;
    public double t;
    public double s_Gamma;
    private static final double dt = Math.pow(10, -5);
    private final double ALPHA;

    public CalculateSyTheor(double alpha, double phase) {
        this.ALPHA = alpha;
        this.phase = phase;
        this.s_Gamma = 0;
    }

    public void iteration() {
        s_Gamma += function(t);
        t += dt;
    }

    private double function(double t) {
        return -2 / Math.PI * Math.cos(phase)
                + 4 * Math.sin(2*Math.PI*t - phase) / (1 + Math.pow(getQ(t),2));
    }

    private double getQ(double t) {
        return ALPHA * t + Math.sqrt(1 + Math.pow(ALPHA/4, 2)) - ALPHA / 4;
    }



}
