import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {




    public static void main(String[] args) {
        ZipUtils.saveCodeInHistory();

        double t_0 = 5;
        double t_m = Calculator5.dt;

        double kappa = 5;
        double ksi = 0;

        for (double alpha = 0; alpha < 100; alpha++) {
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
        double t_0 = 0;
        double t_m = 3;

        double[] kappaList = {1.1, 1.3, 1.5, 2.0, 5.0, 10, 25, 50, 100};
        double[] alphaList = {1, 5, 10, 25, 50, 100};


        for (double alpha : alphaList) {
            ExcelWriter ew = new ExcelWriter();
            for (double kappa : kappaList) {
                System.out.println("alpha = " + alpha + "; kappa = " + kappa);
                for (double ksi = 0; ksi <= 2; ksi = round(ksi + 0.1, 1)) {
                    Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, 0 /*phase*/);
                    calc.setKsi_0(ksi * Math.PI);
                    while (calc.t < t_0)
                        calc.iteration(false);
                    while (calc.t < t_0 + t_m)
                        calc.iteration(true);
                    ew.addColumn("kappa="+kappa, calc.ksiList);
                }
            }
            ew.write("0_2pi_kappa_more_than_1_alpha = " + alpha);
        }
    }



    private static double getKsiStationar(double alpha, double kappa) {
        double check = alpha/4/Math.PI*Math.sqrt(kappa*kappa-1);
        int k = (int) Math.round(check);

        if(check-(int)check > 0.5)
            return getKsiStationar2(alpha, kappa, k) + Math.PI;
        else
            return getKsiStationar2(alpha, kappa, k);
    }


    private static double getKsiStationar1(double alpha, double kappa, int k) {
        return //Math.PI * k -
                Math.atan(kappa/Math.sqrt(kappa*kappa-1)*
                Math.tan(Math.PI*k-alpha/4*Math.sqrt(kappa*kappa-1)));
    }

    private static double getKsiStationar2(double alpha, double kappa, int k) {
        return //Math.PI * k +
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
