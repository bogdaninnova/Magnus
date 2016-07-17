import java.util.ArrayList;

public class Calculator4 {

    public double PSI_MAX;
    public double dt = Math.pow(10, -5);
    public double PHASE;


    public double R = 2 * Math.pow(10, -6);
    public double ABS_M = 3.89 * 1000 / (4 * Math.PI);
    public double ABS_H;
    public double ETA = 8.9 * Math.pow(10, -3);
    public double b = 2.5 * Math.pow(10, -6);
    public double OMEGA = Math.pow(10, 3);
    public double RHO = 1;

    public double ALPHA;
    public double GAMMA;


    public Calculator4(double h, double psi, double phase) {
        this.ABS_H = h * ABS_M;
        this.PSI_MAX = psi;
        this.PHASE = phase;
        this.ALPHA = getAlpha();
        this.GAMMA = getGamma();
    }


    private double ksi = 0;
    public double t = 0;
    private Vector L = new Vector();


    private Vector getU(double ksi, double t) {
        return new Vector(1, GAMMA * Math.sin(ksi), 0).multiply(Math.sin(2 * Math.PI * t));
    }

    public double Is = 0;
    public double Ic = 0;

    public void iteration(boolean isWrite) {
        ksi += getdKsi();
        t += dt;
        Vector U = getU(ksi, t);
        L = L.plus(U.multiply(dt));
        if (isWrite) {
            Is += Math.sin(ksi) * Math.sin(2 * Math.PI * t);
            Ic += Math.sin(ksi) * Math.cos(2 * Math.PI * t);
        }
    }

    private double getdKsi() {
        double d1, d2, d3, d4;
        d1 = function(ksi, t);
        d2 = function(ksi + d1 * dt/2, t + dt/2);
        d3 = function(ksi + d2 * dt/2, t + dt/2);
        d4 = function(ksi + d3 * dt, t + dt);
        return dt/6 * (d1 + 2 * d2 + 2 * d3 + d4);
    }

    private double function(double ksi, double t) {
        return getdPsi(t) - ALPHA * Math.sin(ksi);
    }


    private double getdPsi(double t) {
        return 2 * Math.PI * PSI_MAX * Math.cos(2 * Math.PI * t + PHASE);
    }


    private double getAlpha() {
        return Math.PI * ABS_M * ABS_H * Math.pow(R, 3) /
                (3 * ETA * Math.pow(b, 3) * OMEGA);
    }

    private double getGamma() {
        return RHO * ABS_M * ABS_H * Math.pow(R, 3) / (36 * ETA * ETA * b);
    }

}
