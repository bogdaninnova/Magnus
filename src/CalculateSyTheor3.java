
public class CalculateSyTheor3 {

    private double PHASE;
    public double t;
    public double s_Gamma;
    private double dt = Calculator5.dt;
    private double ALPHA;
    private double KAPPA;
    private double Q0;

    public CalculateSyTheor3(double alpha, double kappa, double phase) {
        this.ALPHA = alpha;
        this.KAPPA = kappa;
        this.PHASE = phase;
        this.s_Gamma = 0;
        this.Q0 = getQ0();
    }


    public double getS_gamma() {
        while (t <= 0.5) {
            s_Gamma += iterateS_gamma(t);
            t += dt;
        }
        return s_Gamma;
    }

    public double getKsi(double t) {
        double time = t-((int) t);
        if (time < 0.5)
            return 2 * Math.atan((getQ_t(time) - 1) / KAPPA);
        return - 2 * Math.atan((getQ_t(time-0.5) - 1) / KAPPA);
    }


    private double iterateS_gamma(double t) {
        double Q_t = getQ_t(t);
        return 4 * KAPPA * (Q_t-1) / (KAPPA*KAPPA + Math.pow(Q_t-1, 2)) * Math.sin(2 * Math.PI * t - PHASE) * dt;
    }


    private double iterateS_gamma2(double t) {
        return 2 * getSinKsi(t) * Math.sin(2 * Math.PI * t - PHASE) * dt;
    }

    private double getSinKsi(double t) {
        double Q_t = getQ_t(t);
        return 2 * KAPPA * (Q_t-1) / (KAPPA*KAPPA+Math.pow(Q_t - 1, 2));
    }


    private double getQ_t(double t) {
        return Math.sqrt(KAPPA*KAPPA-1)
                * (Q0 - Math.sqrt(KAPPA*KAPPA-1) * Math.tan(ALPHA * Math.sqrt(KAPPA * KAPPA-1) * t / 2))
                / (Q0 * Math.tan(ALPHA * Math.sqrt(KAPPA * KAPPA-1) * t / 2) + Math.sqrt(KAPPA*KAPPA-1));
    }


    private double getQ0() {
        return 1
                - Math.sqrt(KAPPA*KAPPA-1)
                        / Math.tan(ALPHA * Math.sqrt(KAPPA*KAPPA-1) / 4)
                + Math.sqrt(
                        (KAPPA*KAPPA-1)
                        / Math.pow(Math.tan(ALPHA * Math.sqrt(KAPPA*KAPPA-1) / 4), 2)
                        + KAPPA*KAPPA
                );


    }




}
