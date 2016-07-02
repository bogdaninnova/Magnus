import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        double H3 = 0.001;
        double PSI3 = 10 * Math.PI / 8;
        double PHASE3 = 0.9 * Math.PI;

        Calculator3 c33 = new Calculator3(H3, PSI3, PHASE3);

        while (c33.t < 3)
            c33.iteration(false);
        while (c33.t < 4)
            c33.iteration(true);

        Draw2DGraphic.draw(c33.locationList, "test");



        System.exit(0);
        double H = 0.001;
        double PSI = 10 * Math.PI / 8;

        ExcelWriter ew33 = new ExcelWriter();
        int counter = 0;
        int c = 1;
        double PHASE = 0;
        double step = Math.PI / 100.0;
        while (true) {
            if (PHASE > 2 * Math.PI + 0.001)
                break;
            PHASE += step;
            System.out.println(++counter);
            if (counter == 50) {
                c++;
                counter = 0;
            }
            Calculator3 c3 = new Calculator3(H, PSI, PHASE);
            double t_wait = 3;
            double t_run = 1;

            while (c3.t < t_wait)
                c3.iteration(false);
            while (c3.t < t_wait + t_run)
                c3.iteration(true);

            ew33.addVectorList("H" + c, c3.locationList, "x", "y");
            ew33.addColumn("H" + c, new ArrayList<Double>());
        }
        ew33.write("psi = " + PSI);







        System.exit(0);
        double psi_step = Math.PI / 8;
        double phase_step = Math.PI / 100;
        ExcelWriter ew3 = new ExcelWriter();
        for (int h = 0; h > -7; h--) {
            for (double psi = psi_step; psi <= 10 * Math.PI; psi += psi_step) {
                ArrayList<Double> list3 = new ArrayList<>();
                for (double phase = 0; phase <= 2 * Math.PI; phase += phase_step)
                    list3.add(calculate(Math.pow(10, h), psi, phase).getY());
                System.out.println("h = " + Math.pow(10, h));
                System.out.println("psi = " + psi);
                System.out.println("-------------");
                ew3.addColumn("h = " + Math.pow(10, h), list3);
            }
            ew3.write("Magnus3");
        }
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


    private static Vector calculate (double h, double psi, double phase) {
        Calculator3 c3 = new Calculator3(h, psi, phase);

        double t_wait = 3;
        double t_run = 1;

        while (c3.t < t_wait)
            c3.iteration(false);
        while (c3.t < t_wait + t_run)
            c3.iteration(true);

        return c3.getAverageU();
    }


}
