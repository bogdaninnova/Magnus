import java.util.ArrayList;

public class Calculator5 {


    public double dt = Math.pow(10, -5);

    public double PHASE;
    public double PSI_MAX;
    public double ALPHA;//5772.8204089548935 * h 10-6 -- 1



 //   private static double RHO = 1;
    private static double OMEGA = Math.pow(10, 3);
    private static double M = 3.89 * 1000 / (4 * Math.PI);
    private static double H = 0.2 * M;
    private static double ETA = 8.9 * Math.pow(10, -3);
//    private static double B = 2.5 * Math.pow(10, -6);


    private double nu;

    public Calculator5(double nu, double psi, double phase) {
        this.PSI_MAX = psi;
        this.PHASE = phase;
        this.nu = nu;
        setAlpha(nu);
    }

    public void setAlpha(double nu) {
        this.ALPHA = nu*nu*nu * getAlpha0();
    }

    private static double getAlpha0() {
        //return Math.PI * M * H / (3 * ETA * OMEGA);
        return 10000;
    }

//    private static double getGamma0() {
//        return RHO * M * H * B*B / (36 * ETA*ETA);
//    }

    private double ksi = 0;
    public double t = 0;

    public double sGamma = 0;


    int init_counter = 10;
    int counter = init_counter-1;


    private Vector L = new Vector();
    public ArrayList<Vector> track = new ArrayList<>();
    private Vector getU(double ksi, double t) {
        return new Vector(1, Math.sin(ksi), 0).multiply(Math.sin(2 * Math.PI * t));
    }


    public void iteration(boolean isWrite) {
        ksi += getdKsi();
        t += dt;
        Vector U = getU(ksi, t);
        L = L.plus(U.multiply(dt));

        if (isWrite) {
            counter++;

            if (counter == init_counter) {
                double currentTime = t - ((int) t);

                track.add(L);

                sGamma += 2 * Math.sin(ksi) * Math.sin(2 * Math.PI * currentTime) * nu*nu*nu * dt;

                counter = 0;
            }
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
        return (getPsi(t + dt) - getPsi(t)) / dt;
    }

    private double getPsi(double t) {
        return PSI_MAX * 2 / Math.PI * Math.asin(Math.cos(2 * Math.PI * t + PHASE));
    }


}
