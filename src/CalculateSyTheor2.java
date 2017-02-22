
public class CalculateSyTheor2 {

    private double PHASE;
    public double t;
    public double s_Gamma;
    private static final double dt = Math.pow(10, -5);
    private final double ALPHA;
    private final double KAPPA;
    private final double Q0;

    public CalculateSyTheor2(double alpha, double kappa, double phase) {
        this.ALPHA = alpha;
        this.KAPPA = kappa;
        this.PHASE = phase;
        this.s_Gamma = 0;
        this.Q0 = getQ0();
    }


    public double getS_gamma() {
        while (t <= 0.5) {
            t += dt;
            s_Gamma += iterateS_gamma();
        }
        return s_Gamma;
    }


    private double iterateS_gamma() {
        double Q_t = getQ_t();
        return 4 * KAPPA * (Q_t-1) / (KAPPA*KAPPA + Math.pow(Q_t-1, 2)) * Math.sin(2 * Math.PI * t - PHASE) * dt;
    }

    private double getQ_t() {
        return Math.sqrt(1-KAPPA*KAPPA)
                * (Q0 + Math.sqrt(1-KAPPA*KAPPA) * Math.tanh(ALPHA*Math.sqrt(1-KAPPA*KAPPA) * t / 2))
                / (Q0 * Math.tanh(ALPHA*Math.sqrt(1-KAPPA*KAPPA) * t / 2) + Math.sqrt(1-KAPPA*KAPPA));
    }


    private double getQ0() {
        return KAPPA*KAPPA*Math.tanh(ALPHA*Math.sqrt(1-KAPPA*KAPPA)/4) /
                (Math.sqrt(1- KAPPA*KAPPA/Math.pow(Math.cosh(ALPHA*Math.sqrt(1-KAPPA*KAPPA)/4), 2))
                        + Math.sqrt(1-KAPPA*KAPPA))
                + 1;
    }




}
