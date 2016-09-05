import java.util.ArrayList;

public class Calculator4 {


    public double dt = Math.pow(10, -5);

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

    public double sGamma = 0;
    public double sTheorGamma = 0;

    public ArrayList<Double> sList = new ArrayList<>();
    public ArrayList<Double> sTheorList = new ArrayList<>();

    public ArrayList<Double> ksiList = new ArrayList<>();
    public ArrayList<Double> ksiListTheor = new ArrayList<>();



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

                if (currentTime < 0.5) {
                    double q = getQ(currentTime);
                    sTheorGamma += 2 * (1 - q * q) / (1 + q * q)
                            * Math.sin(2 * Math.PI * currentTime - PHASE);
                } else {
                    double q = getQ(currentTime - 0.5);
                    sTheorGamma += 2 * (1 - q * q) / (1 + q * q)
                            * Math.sin(2 * Math.PI * (currentTime - 0.5) - PHASE);
                }
                sTheorList.add(sTheorGamma);

                sGamma += 2 * Math.sin(ksi) * Math.sin(2 * Math.PI * currentTime);
                sList.add(sGamma);

                ksiList.add(ksi);
                if (currentTime < 0.5) {
                    ksiListTheor.add(getKsiTheor(currentTime));
                } else {
                    ksiListTheor.add(-getKsiTheor(currentTime - 0.5));
                }
                counter = 0;
            }
        }
    }

    private double getQ(double t) {
        return ALPHA * t + Math.sqrt(1 + Math.pow(ALPHA/4, 2)) - ALPHA / 4;
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
        return (getPsi(t + dt) - getPsi(t)) / dt;
    }

    private double getPsi(double t) {
        return PSI_MAX * 2 / Math.PI * Math.asin(Math.cos(2 * Math.PI * t + PHASE));
    }

    private double getdPsi2(double t) {
        return 2 * Math.PI * PSI_MAX * Math.cos(2 * Math.PI * t + PHASE);
    }


}
