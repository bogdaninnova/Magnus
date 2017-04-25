
public class CalculateSyTheor4 {

    private double PHASE;
    public double t;
    private double ALPHA;
    private double KAPPA;
    private double NU;
    private double R0;
    private double P;

    public CalculateSyTheor4(double alpha, double kappa, double phase) {
        this.ALPHA = alpha;
        this.KAPPA = kappa;
        this.PHASE = phase;

        this.NU = getNu();
        this.R0 = getR0();
        this.P = getP();


        System.out.println("ALPHA = "+ALPHA);
        System.out.println("KAPPA = "+KAPPA);
        System.out.println("PHASE = "+PHASE);
        System.out.println("NU = "+NU);
        System.out.println("R0 = "+R0);
        System.out.println("P = "+P);
    }





    private double getNu() {
        return ALPHA / 4 * Math.sqrt(KAPPA*KAPPA-1);
    }

    private double getR0() {
        double tanNu = Math.tan(NU);
        return 1
                - Math.sqrt(KAPPA*KAPPA-1) / tanNu
                + Math.sqrt((KAPPA*KAPPA-1)/tanNu/tanNu + KAPPA*KAPPA);
    }


    private double getR(double t) {
        return Math.sqrt(KAPPA*KAPPA-1)
                * (R0 - Math.sqrt(KAPPA*KAPPA-1) * Math.tan(2*NU*t))
                / (R0*Math.tan(2*NU*t) + Math.sqrt(KAPPA*KAPPA-1));
    }



    public double getKsi_k(double k) {
        return (k * Math.PI - Math.atan(Math.sqrt(KAPPA*KAPPA-1)/R0)) / 2 / NU;
    }

    public double getP() {
        double nuAll = NU;
        int counter = 0;
        while (nuAll > Math.PI) {
            nuAll -= Math.PI;
            counter++;
        }
        return counter;
    }



    public double getP_k(double t) {
        double k = 0;
        while (true) {
            if ((t < getKsi_k(k+1)) && (t > getKsi_k(k)))
                return k;
            else
                k++;
        }

    }

    public double getKsi(double t) {
        double time = t-((int) t);
        if (time < 0.5)
            return -2 * Math.PI * getP_k(time) + 2 * Math.atan((getR(time)-1) / KAPPA);
        else
            return -2*Math.PI * P -(-2 * Math.PI * getP_k(time-0.5) + 2 * Math.atan((getR(time-0.5)-1) / KAPPA));


    }





}
