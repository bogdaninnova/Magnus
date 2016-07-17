import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ExcelWriter ew = new ExcelWriter();
        for (double h = 0.000001; h < 1.0001; h *= 1000000000) {
            for (double psi = 0; psi < 10 * Math.PI; psi += Math.PI / 8) {
                System.out.println(h);
                System.out.println(psi);
                System.out.println();
                ew.addColumn("H = " + h, calculate42(h, psi));
            }
        }
        ew.write("Phase!=0");
    }


    private static ArrayList<Double> calculate4(double H, double PSI) {

        Calculator4 c = new Calculator4(H, PSI, 0);
        while (c.t < 10)
            c.iteration(false);
        while (c.t < 11)
            c.iteration(true);
        ArrayList<Double> list = new ArrayList<>();
        for (double phase = 0; phase <= 2 * Math.PI; phase += Math.PI / 100) {
            list.add((Math.cos(phase) * c.Is - Math.sin(phase) * c.Ic) * c.GAMMA);
        }
        return list;
    }

    private static ArrayList<Double> calculate42(double H, double PSI) {

        ArrayList<Double> list = new ArrayList<>();
        for (double phase = 0; phase <= 2 * Math.PI; phase += Math.PI / 100) {
            Calculator4 c = new Calculator4(H, PSI, phase);
            while (c.t < 10)
                c.iteration(false);
            while (c.t < 11)
                c.iteration(true);
            list.add(c.Is * c.GAMMA);
        }
        return list;
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


    private void archive() {
        double H = 1;
        double PSI = 9*Math.PI / 8;
        double PHASE = 0;


        Calculator3 c34 = new Calculator3(H, PSI, PHASE);
        while (c34.t < 5)
            c34.iteration(false);
        while (c34.t < 6)
            c34.iteration(true);
        Draw2DGraphic.draw2dDouble(c34.ksiList, "3H = " + H);
        System.exit(0);

        int counter = 0;


        //ArrayList<Double> list = new ArrayList<>();
        //  double step = Math.PI / 20.0;
        while (true) {
            if (H > 1.0001)
                break;
            H *= 10;
            //PHASE += step;
            System.out.println(++counter);
            Calculator3 c3 = new Calculator3(H, PSI, PHASE);
            double t_wait = 10;
            double t_run = 10;

            while (c3.t < t_wait)
                c3.iteration(false);
            while (c3.t < t_wait + t_run)
                c3.iteration(true);

            // list.add(c3.getAverageU().getY());
            Draw2DGraphic.draw2dDouble(c3.ksiList, "H = " + H);
        }
        //Draw2DGraphic.draw2dDouble(list, "list2");






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


}
