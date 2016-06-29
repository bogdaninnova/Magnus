import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ExcelWriter ew3 = new ExcelWriter();
        for (double h = 0.000001; h < 1.01; h *= 10)
            for (double psi = 0; psi <= 10 * Math.PI; psi += Math.PI / 8) {
                ArrayList<Double> list3 = new ArrayList<>();
                for (double phase = 0; phase <= 2 * Math.PI; phase += Math.PI / 100) {
                    list3.add(calculate3(phase, psi).getY());
                    System.out.println("h = " + h);
                    System.out.println("psi = " + psi);
                    System.out.println("phase = " + phase);
                    System.out.println("-------------");
                }
                ew3.addColumn("h = " + h, list3);
            }


        ew3.write("Magnus3");
        System.exit(0);







        double nk = 1.5;
        int mc = 0;
        Calculator2 calcC = new Calculator2();
        Calculator2.PSI0 = Math.PI * nk;

        while (++mc < 0) {
            while (calcC.t < mc)
                calcC.iteration(false);
            System.out.println(mc);
        }

        while (++mc < 50) {
            while (calcC.t < mc)
                calcC.iteration(true);
            System.out.println(mc);
        }
        ExcelWriter eww = new ExcelWriter();
        eww.addVectorList("magnetizationList", calcC.magnetizationList);
        eww.addVectorList("locationList", calcC.locationList);
        eww.addVectorList("fieldList", calcC.fieldList);
        eww.write("psi="+nk+"pi");

        System.exit(0);







        double theta = 0.25 * Math.PI;
        double phi = 1 * Math.PI ;

        Calculator2 c = calculate(theta, phi);
        ExcelWriter ew = new ExcelWriter();
        ew.addVectorList("magnetizationList", c.magnetizationList);
        ew.addVectorList("locationList", c.locationList);
        ew.addVectorList("fieldList", c.fieldList);
        ew.write("Magnus");


        System.exit(0);

        ArrayList<Vector> list = new ArrayList<>();
        for (double i = 0; i <= 10 * Math.PI; i += Math.PI / 20) {
            list.add(calculate(i));
            System.out.println(i / Math.PI);
        }
        ew.rest();
        ew.addVectorList("list", list);
        ew.write("Magnus");

        System.exit(0);

        int m = 3;
   //     m = 30;
        int n = 1;
        Calculator2 calc = new Calculator2();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);

        ew.rest();
        ew.addVectorList("calc.locationList", calc.locationList);
        ew.addVectorList("calc.magnetizationList", calc.magnetizationList);
        ew.addVectorList("calc.fieldList", calc.fieldList);
        ew.write("Magnus");

        System.out.println(calc.getAverU().getX());
        System.out.println(calc.getAverU().getY());
        System.out.println(calc.getAverU().getZ());


    }


    private static Vector calculate(double PSI0) {
        Calculator2.PSI0 = PSI0;
        int m = 3;
        int n = 1;
        Calculator2 calc = new Calculator2();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);
        return calc.getAverU();
    }

    private static Calculator2 calculate(double THETA0, double PHI0) {
        Calculator2.initTheta = THETA0;
        Calculator2.initPhi = PHI0;
        int m = 10;
        int n = 10;
        Calculator2 calc = new Calculator2();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);
        return calc;
    }


    private static Vector calculate3 (double phase, double psi_max) {
        Calculator3 c3 = new Calculator3();
        Calculator3.PHASE = phase;
        Calculator3.PSI_MAX = psi_max;

        double t_wait = 3;
        double t_run = 1;

        while (c3.t < t_wait)
            c3.iteration(false);
        while (c3.t < t_wait + t_run)
            c3.iteration(true);

        return c3.getAverageU();
    }


}
