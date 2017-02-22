import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {




    public static void main(String[] args) {
        ZipUtils.saveCodeInHistory();


        System.out.println(new CalculateSyTheor2(2, 0.1, 0 * Math.PI).getS_gamma());

        countAllSlist();


        System.exit(0);





        double t_0 = 5;
        double t_m = Calculator5.dt;




        double alpha = 10;
        double ksi = 0;

        for (double kappa = 1.1; kappa < 25; kappa+=0.1) {
//            Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, 0 /*phase*/);
//            calc.setKsi_0(ksi * Math.PI);
//            while (calc.t < t_0)
//                calc.iteration(false);
//            while (calc.t < t_0 + t_m)
//                calc.iteration(true);
//            System.out.println(calc.ksiList.get(0));
System.out.println(getKsiStationar(alpha, kappa));
        }


    }




    private static void countAll() {
        double t_0 = 100;
        double t_m = 1;


        //double[] kappaList = {1.1, 1.3, 1.5, 2.0, 5.0, 10, 25, 50, 100};
        //double[] alphaList = {1, 5, 10, 25, 50, 100};

        ExcelWriter ew = new ExcelWriter();
        for (double alpha = 5; alpha <= 50; alpha = round(alpha + 5, 0)) {
            for (double kappa = 0.1; kappa <= 1; kappa = round(kappa + 0.1, 1)) {
                System.out.println("alpha = " + alpha + "; kappa = " + kappa);
                    Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, 0 /*phase*/);
                    while (calc.t < t_0)
                        calc.iteration(false);
                    while (calc.t < t_0 + t_m)
                        calc.iteration(true);
                ew.addColumn("alpha="+alpha, calc.ksiList);
            }
        }
        ew.write("ksi_dzeta");
    }



    private static void countAllSlist() {
        double t_0 = 100;
        double t_m = 0.5;


        double[] kappaList = {0.5};
        double[] alphaList = {2};

        ExcelWriter ew = new ExcelWriter();
        for (double alpha : alphaList) {
            for (double kappa : kappaList) {
                System.out.println("alpha = " + alpha + "; kappa = " + kappa);
                Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, 0 /*phase*/);
                while (calc.t < t_0)
                    calc.iteration(false);
                while (calc.t < t_0 + t_m) {
                    calc.iteration(true);
                }
                System.out.println(calc.sGamma);
            }
        }
        ew.write("ksi_dzeta");
    }



    private static void ksi_0_alpha() {
        double t_0 = 100;
        double t_m = 1;


        //double[] kappaList = {1.1, 1.3, 1.5, 2.0, 5.0, 10, 25, 50, 100};
        //double[] alphaList = {1, 5, 10, 25, 50, 100};

        ExcelWriter ew = new ExcelWriter();
        for (double kappa = 0.1; kappa <= 1; kappa = round(kappa + 0.1, 1)) {
            ArrayList<Double> arrayList = new ArrayList<>();
            for (double alpha = 1; alpha <= 100; alpha = round(alpha + 1, 0)) {
                System.out.println("alpha = " + alpha + "; kappa = " + kappa);
                //for (double ksi = -3; ksi <= 3; ksi = round(ksi + 0.1, 1)) {
                Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, 0 /*phase*/);
                //   calc.setKsi_0(ksi * Math.PI);
                while (calc.t < t_0)
                    calc.iteration(false);
                while (calc.t < t_0 + t_m)
                    calc.iteration(true);
                arrayList.add(calc.ksiList.get(0));
                //}
            }
            ew.addColumn("ksi(0)_alpha", arrayList);
        }
        ew.write("ksi(0)_alpha");
    }



    private static double getKsiStationar(double alpha, double kappa) {
        double check = alpha/4/Math.PI*Math.sqrt(kappa*kappa-1);
        int k = (int) Math.round(check);

        if(check-(int)check > 0.5)
            return getKsiStationar1(alpha, kappa, k);
        else
            return getKsiStationar2(alpha, kappa, k);
    }


    private static double getKsiStationar1(double alpha, double kappa, int k) {
        return Math.PI * k -
                Math.atan(kappa/Math.sqrt(kappa*kappa-1)*
                Math.tan(Math.PI*k-alpha/4*Math.sqrt(kappa*kappa-1)));
    }

    private static double getKsiStationar2(double alpha, double kappa, int k) {
        return Math.PI * k +
                Math.atan(kappa/Math.sqrt(kappa*kappa-1)*
                Math.tan(-Math.PI*k+alpha/4*Math.sqrt(kappa*kappa-1)));
    }



    private static void getS() {
        ExcelWriter ew = new ExcelWriter();

        for (double alpha0 = 25; alpha0 <= 50; alpha0 = round(alpha0 + 25, 0)){
            ArrayList<Double> arrayList = new ArrayList<>();
            for (double phase = 0; phase < 2.001; phase = round(phase + 0.05, 2)) {
                Calculator5 calculatorNUM = new Calculator5(alpha0, 0.25 * Math.PI, phase * Math.PI);
                double t0 = 5;
                while (calculatorNUM.t < t0)
                    calculatorNUM.iteration(false);
                while (calculatorNUM.t < t0 + 0.5)
                    calculatorNUM.iteration(true);

                arrayList.add(calculatorNUM.sGamma);


//                    ew.addColumn("other", calculatorNUM.ksiList);
//                    ew.addVectorList("other", calculatorNUM.track);
//                TextWriter.writeDoubleList(calculatorNUM.ksiList, "ksiList_psi=" + psi + "_phase=" + phase + "alpha=" + alpha0);
//                TextWriter.writeTraectorysCoordinates(calculatorNUM.track, "track_psi=" + psi + "_phase=" + phase + "alpha=" + alpha0);
            }
            ew.addColumn("s_phase che", arrayList);
            System.out.println(new Date());
            System.out.println(alpha0);
            System.out.println();
        }
        ew.write("s_phase 25 50");
    }




    private static void getS_alpha() {
        ExcelWriter ew = new ExcelWriter();
        for (double phase = 0; phase <= 3.1; phase = round(phase + 0.1, 1))
        {
            ArrayList<Double> arrayList = new ArrayList<>();
            for (double alpha0 = 1; alpha0 <= 51; alpha0 = round(alpha0 + 1, 0)) {
                Calculator5 calculatorNUM = new Calculator5(alpha0, 0.25 * Math.PI, phase);
                double t0 = 5;
                while (calculatorNUM.t < t0)
                    calculatorNUM.iteration(false);
                while (calculatorNUM.t < t0 + 0.5)
                    calculatorNUM.iteration(true);

                arrayList.add(calculatorNUM.sGamma);

            }
            ew.addColumn("s_alpha", arrayList);
            System.out.println(new Date());
            System.out.println(phase);
            System.out.println();
        }
        ew.write("s_alpha many no phase");
    }



    private static ArrayList<Double> minus(ArrayList<Double> list2, ArrayList<Double> list1) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++)
            list.add(list2.get(i) - list1.get(i));
        return list;
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
