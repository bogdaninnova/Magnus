import java.util.ArrayList;

public class Calculator4 {


    public double dt = Math.pow(10, -5);
    public double DERRIVATE_dt = Math.pow(10, -11);

    public double PHASE;
    public double PSI_MAX;
    public double KSI_ST;
    public double ALPHA;//5772.8204089548935 * h 10-6 -- 1


    public Calculator4(double alpha, double psi, double phase) {
        this.PSI_MAX = psi;
        this.PHASE = phase;
        this.ALPHA = alpha;
        this.KSI_ST = Math.atan(alpha / 4);
    }

    private double ksi = 0;
    public double t = 0;

    public double Is = 0;
    public double Ic = 0;

    public ArrayList<Double> IsList = new ArrayList<>();
    public ArrayList<Double> ksiList = new ArrayList<>();
    public ArrayList<Double> ksiListTheor = new ArrayList<>();

    int counter = 0;


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
        Is += Math.sin(ksi) * Math.sin(2 * Math.PI * t);
        Ic += Math.sin(ksi) * Math.cos(2 * Math.PI * t);
        if (isWrite) {
            counter++;

            if (counter == 10) {
                track.add(L);
                IsList.add(Is);
                ksiList.add(ksi);
                double currentTime = t - ((int) t);
                if (currentTime < 0.5)
                    ksiListTheor.add(getKsiTheor(currentTime));
                else
                    ksiListTheor.add(-getKsiTheor(currentTime - 0.5));
                counter = 0;
            }
        }
    }

    private double getKsiTheor(double t) {
        return Math.PI / 2 - 2 * Math.atan(
                ALPHA * t - Math.tan(KSI_ST / 2 - Math.PI / 4));
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
        return (getPsi2(t + DERRIVATE_dt) - getPsi2(t)) / DERRIVATE_dt;
    }

    private double getPsi2(double t) {
        return PSI_MAX * 2 / Math.PI * Math.asin(Math.cos(2 * Math.PI * t + PHASE));
    }



    private double getdPsi2(double t) {
        return 2 * Math.PI * PSI_MAX * Math.cos(2 * Math.PI * t + PHASE);
    }


}