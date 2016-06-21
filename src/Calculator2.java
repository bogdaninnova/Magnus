import java.util.ArrayList;

public class Calculator2 {
//
    public double t = 0;
    public static final double dt = Math.pow(10, -6);

    private static final Vector Ex = new Vector(1, 0, 0);
    private static final double initTheta = Math.PI / 4;
    private Vector M = new Vector(Math.sin(initTheta), 0, Math.cos(initTheta));//Magnetic moment
    public Vector L = new Vector(0, 0, 0);//LOCATION

    public ArrayList<Vector> locationList = new ArrayList<>();
    public ArrayList<Vector> magnetizationList = new ArrayList<>();
    public ArrayList<Vector> fieldList = new ArrayList<>();

    private static final double RADIUS = 2 * Math.pow(10, -6);
    private static final double ABS_M = 3.89 * 1000 / (4 * Math.PI);
    private static final double ABS_H = 1 * ABS_M;
    private static final double ETA = 8.9 * Math.pow(10, -3);
    private static final double b = 2.5 * Math.pow(10, -6);
    public static  double PSI0 = 1 * Math.PI;
    private static final double RHO = 1;

    private static final double OMEGA = Math.pow(10, 3);

    private static final double ALPHA = getAlpha();
    private static final double BETA = getBeta();

    int counter = 99;
    private Vector averU = new Vector();
    private double averCounter = 0;

    public Vector getAverU() {
        return averU.multiply(1.0 / averCounter);
    }



    private int count2 = 9;
    public void iteration(boolean isWrite) {
        counter++;
        Vector dM = getdM();
        M = M.plus(dM);

        Vector U = getU(M, t);
        L = L.plus(U.multiply(dt));
        t += dt;

        if (counter == 100) {
            counter = 0;
            //System.out.println(t);
            if (isWrite) {
                averCounter++;
                averU = averU.plus(U);
                count2++;
                if (count2 == 10) {
                    count2 = 0;
                    locationList.add(L);
                    magnetizationList.add(M);
                    fieldList.add(getH(t));
                }

            }
        }
    }


    private static Vector getH(double t) {
        double psi = getPsi(t);
        return new Vector(Math.cos(psi), Math.sin(psi), 0);
    }

    private static double getPsi(double t) {
        return PSI0 * Math.sin(2 * Math.PI * t);
    }

    private static Vector getU(Vector m, double t) {
        Vector kappa = getKappa(getOmega(m, getH(t)));

        return new Vector(Ex, kappa.crossProduct(Ex), kappa.multiply(Ex.dotProduct(kappa)))
                .multiply(Math.sin(2* Math.PI * t)
                        / (1 + Math.pow(kappa.modul(), 2)));
    }


    private static Vector getKappa(Vector omega) {
        return omega.multiply(BETA);
    }

    private static Vector getOmega(Vector m, Vector h) {
        return m.crossProduct(h).multiply(ALPHA * OMEGA / (2 * Math.PI));
    }

    private Vector getdM() {
        Vector d1, d2, d3, d4;
        d1 = func(new Vector(M.getX(), M.getY(), M.getZ()), t);
        d2 = func(new Vector(M.getX() + dt / 2 * d1.getX(), M.getY() + dt / 2 * d1.getY(), M.getZ() + dt / 2 * d1.getZ()), t + dt / 2);
        d3 = func(new Vector(M.getX() + dt / 2 * d2.getX(), M.getY() + dt / 2 * d2.getY(), M.getZ() + dt / 2 * d2.getZ()), t + dt / 2);
        d4 = func(new Vector(M.getX() + dt / 1 * d3.getX(), M.getY() + dt / 1 * d3.getY(), M.getZ() + dt / 1 * d3.getZ()), t + dt / 1);

        return new Vector(
                dt/6 * (d1.getX() + 2 * d2.getX() + 2 * d3.getX() + d4.getX()),
                dt/6 * (d1.getY() + 2 * d2.getY() + 2 * d3.getY() + d4.getY()),
                dt/6 * (d1.getZ() + 2 * d2.getZ() + 2 * d3.getZ() + d4.getZ()));
    }

    private static Vector func(Vector M, double t) {
        return new Vector(M.crossProduct(M.crossProduct(getH(t)))).multiply(-ALPHA);
    }


    public static double getAlpha() {
        return Math.PI * ABS_M * ABS_H * Math.pow(RADIUS, 3) /
                (3 * ETA * Math.pow(b, 3) * OMEGA);
    }

    public static double getBeta() {
        return RHO * b * b / (6 * ETA);
    }

}
