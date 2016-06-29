import java.util.ArrayList;

public class Calculator3 {

    public static  double PSI_MAX = 0.5 * Math.PI;
    private static final double DERRIVATE_dt = Math.pow(10, -8);
    private static final double dt = Math.pow(10, -4);
    public static double PHASE = 0.5 * Math.PI;


    private static final double R = 2 * Math.pow(10, -6);
    private static final double ABS_M = 3.89 * 1000 / (4 * Math.PI);
    private static final double ABS_H = 0.0001 * ABS_M;
    private static final double ETA = 8.9 * Math.pow(10, -3);
    private static final double b = 2.5 * Math.pow(10, -6);
    private static final double OMEGA = Math.pow(10, 3);
    private static final double RHO = 1;

    private static final double ALPHA = getAlpha();
    private static final double GAMMA = getGamma();


    private double ksi = 0;
    public double t = 0;
    private Vector L = new Vector();
    public ArrayList<Vector> locationList = new ArrayList<>();


    private static Vector getU(double ksi, double t) {
        return new Vector(1, GAMMA * Math.sin(ksi), 0).multiply(Math.sin(2 * Math.PI * t));
    }

    private Vector averageU = new Vector();
    private double counter_aver_u = 0;

    public Vector getAverageU() {
        return averageU.multiply(1d / counter_aver_u);
    }

    public void iteration(boolean isWrite) {
        ksi += getdKsi();
        t += dt;
        Vector U = getU(ksi, t);
        L = L.plus(U.multiply(dt));
        if (isWrite) {
            counter_aver_u++;
            averageU = averageU.plus(U);
            locationList.add(L);
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


    private static double getdPsi(double t) {
        return (getPsi(t + DERRIVATE_dt) - getPsi(t)) / DERRIVATE_dt;
    }

    private static double getPsi(double t) {
        return PSI_MAX * 2 / Math.PI * Math.asin(Math.sin(2 * Math.PI * t + PHASE));
    }

    private static double getAlpha() {
        return Math.PI * ABS_M * ABS_H * Math.pow(R, 3) /
                (3 * ETA * Math.pow(b, 3) * OMEGA);
    }

    private static double getGamma() {
        return RHO * ABS_M * ABS_H * Math.pow(R, 3) / (36 * ETA * ETA * b);
    }

}
