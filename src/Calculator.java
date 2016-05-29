import java.util.ArrayList;

public class Calculator {

    private double t = 0;
    private static final double dt = Math.pow(10, -5);

    private static final Vector Ex = new Vector(1, 0, 0);
    private static final double initTheta = Math.PI / 4;
    private Vector M = new Vector(Math.sin(initTheta), 0, Math.cos(initTheta));//Magnetic moment
    private Vector L = new Vector(1.5, 0, 0);//LOCATION

    public ArrayList<Vector> locationList = new ArrayList<>();
    public ArrayList<Vector> magnetizationList = new ArrayList<>();

    private static final double RADIUS = 2 * Math.pow(10, -6);
    private static final double ABS_M = 3.89 * 1000 / (4 * Math.PI);
    private static final double ETA = 8.9 * Math.pow(10, -3);
    private static final double b = 2.5 * Math.pow(10, -6);
    private static final double RHO = 1;
    private static final double G = 100;

    private static final double H0 = ABS_M;
    private static final double PHI = Math.PI / 2;
    private static final double OMEGA = Math.pow(10, 3) / (2 * Math.PI);




    private static final double v0T = getV0() / OMEGA;
    private static final double G_M = G / ABS_M;
    private static final double H0_M = H0 / ABS_M;
    private static final double BETA = getBeta();
    private static final double ALPHA = getAlpha();


//    private static final double v0T = 2 * Math.pow(10, -7);
//    private static final double G_M = 0.33333333;
//    private static final double H0_M = 1;
//    private static final double BETA = Math.pow(10, -4);
//    private static final double ALPHA = 0.5;


//    {
//        System.out.println("v0T = " + v0T);
//        System.out.println("G_M = " + G_M);
//        System.out.println("H0_M = " + H0_M);
//        System.out.println("BETA = " + BETA);
//        System.out.println("ALPHA = " + ALPHA);
//    }

    public void iteration(boolean isWrite) {
        Vector dM = getdM();
        M = M.plus(dM);
        t += dt;
        Vector U = getU(M, L, t);
        L = L.plus(U.multiply(v0T));
        if (isWrite) {
            locationList.add(L);
            magnetizationList.add(M);
        }

    }

    private Vector getdM() {
        Vector d1, d2, d3, d4;
        d1 = func(new Vector(M.getX(), M.getY(), M.getZ()), L, t);
        d2 = func(new Vector(M.getX() + dt / 2 * d1.getX(), M.getY() + dt / 2 * d1.getY(), M.getZ() + dt / 2 * d1.getZ()), L, t + dt / 2);
        d3 = func(new Vector(M.getX() + dt / 2 * d2.getX(), M.getY() + dt / 2 * d2.getY(), M.getZ() + dt / 2 * d2.getZ()), L, t + dt / 2);
        d4 = func(new Vector(M.getX() + dt / 1 * d3.getX(), M.getY() + dt / 1 * d3.getY(), M.getZ() + dt / 1 * d3.getZ()), L, t + dt / 1);

        return new Vector(
                dt/6 * (d1.getX() + 2 * d2.getX() + 2 * d3.getX() + d4.getX()),
                dt/6 * (d1.getY() + 2 * d2.getY() + 2 * d3.getY() + d4.getY()),
                dt/6 * (d1.getZ() + 2 * d2.getZ() + 2 * d3.getZ() + d4.getZ()));
    }

    private static Vector func(Vector M, Vector L, double t) {
        return new Vector(M.crossProduct(M.crossProduct(getH(L, t)))).multiply(-ALPHA);
    }

    private static Vector getH(Vector L, double t) {
        return new Vector(G_M * L.getX() * Math.sin(2 * Math.PI * t),
                H0_M * Math.cos(4 * Math.PI * t + PHI),
                0);
    }

    private static Vector getU(Vector M, Vector L, double t) {
        Vector C = getC(M, getH(L, t));

        return new Vector(Ex, C.crossProduct(Ex), C.multiply(C.dotProduct(Ex))).multiply(
                M.dotProduct(Ex) * Math.sin(2 * Math.PI * t) / (1 + Math.pow(C.modul(), 2)));
    }

    private static Vector getC(Vector M, Vector H) {
        return M.crossProduct(H).multiply(BETA);
    }







    public static double getAlpha() {
        return Math.PI * Math.pow(RADIUS, 3) * Math.pow(ABS_M, 2) /
                (3 * ETA * Math.pow(b, 3) * OMEGA);
    }

    public static double getBeta() {
        return RHO * Math.pow(RADIUS, 3) * Math.pow(ABS_M, 2) / (Math.pow(6 * ETA, 2) * b);
    }

    public static double getV0() {
        return 2 * Math.pow(RADIUS, 3) * G * ABS_M / (9 * b * ETA);
    }

}
