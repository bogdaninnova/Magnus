i
public class Calculator {

    public static final double OMEGA = 1;
    public static final double OMEGA_1 = 1;
    public static final double FIELDS_MODULE = 1;

    private double temp_t = 0; //realize start counting from last time


    public double h = 0.00001;
    public double t = 0;
    public double psi = 0; //initial psi

    public void iteration() {
        double k1 = main_equance(t, psi);
        double k2 = main_equance(t + h / 2, psi + h / 2 * k1);
        double k3 = main_equance(t + h / 2, psi + h / 2 * k2);
        double k4 = main_equance(t + h, psi + h * k3);

        psi += h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }

    public static double main_equance(double t, double psi) {
        return getDerPsi(t) - OMEGA_1 * Math.sin(psi);  //check sign
    }

    public static double getDerPsi(double t) {
        double var = Math.PI / (2 * OMEGA);
        if (t < var)
            return + FIELDS_MODULE;
        int n = 0;
        while (true) {
            if ((t >= var * (4 * n - 1)) && (t < var * (4 * n + 1)))
                return + FIELDS_MODULE;
            if ((t >= var * (4 * n - 3)) && (t < var * (4 * n - 1)))
                return - FIELDS_MODULE;
        }
    }

    public static double getVy() {
        return 0;
    }

    public static double getVx() {
        return 0;
    }

}
