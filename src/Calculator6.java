import java.util.ArrayList;

public class Calculator6 {


    public static final double dt = Math.pow(10, -5);

    public double PHASE;
    public double PSI_MAX;
    public double ALPHA;//5772.8204089548935 * h 10-6 -- 1

    public Calculator6(double alpha, double psi, double phase) {
        this.PSI_MAX = psi;
        this.PHASE = phase;
        this.ALPHA = alpha;
    }

    private double ksi = 0;
    public double t = 0;
    public double sGamma = 0;

    public void iteration(boolean isWrite) {
        ksi += getdKsi();
        t += dt;

        if (isWrite) {
            double currentTime = t - ((int) t);
            sGamma += 2 * Math.sin(ksi) * Math.sin(2 * Math.PI * currentTime - PHASE) * dt;
        }
    }

    private double getdKsi() {
        double d1, d2, d3, d4;
        d1 = function(ksi, t);
        d2 = function(ksi + d1 * dt / 2, t + dt / 2);
        d3 = function(ksi + d2 * dt/2, t + dt/2);
        d4 = function(ksi + d3 * dt, t + dt);
        return dt/6 * (d1 + 2 * d2 + 2 * d3 + d4);
    }


    private double function(double ksi, double t) {
        return getdPsi2(t) - ALPHA * Math.sin(ksi);
    }

    private double getdPsi2(double t) {
        if (t-(int)t < 0.5)
            return -4*PSI_MAX;
        else
            return 4*PSI_MAX;
    }

}
