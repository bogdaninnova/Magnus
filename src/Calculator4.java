import java.util.ArrayList;

public class Calculator4 {


    public double dt = Math.pow(10, -5);


    public double PHASE;
    public double PSI_MAX;
    public double ALPHA;//5772.8204089548935 * h 10-6 -- 1


    public Calculator4(double alpha, double psi, double phase) {
        this.PSI_MAX = psi;
        this.PHASE = phase;
        this.ALPHA = alpha;
    }

    private double ksi = 0;
    public double t = 0;

    public double Is = 0;
    public double Ic = 0;


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
            track.add(L);

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


}
