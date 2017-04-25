import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    static {ZipUtils.saveCodeInHistory();}





    public static void main(String[] args) {
        ExcelWriter ew = new ExcelWriter();







        double kappa = 15;
        double alpha = 15;
        double phase = 0;






        double t_0 = 100;
        double t_m = 1;

        Calculator5 calc = new Calculator5(alpha, alpha * kappa / 4 /*psi_m*/, phase);
        CalculateSyTheor4 c3 = new CalculateSyTheor4(alpha, kappa, phase);
        ArrayList<Double> arrayList = new ArrayList<>();


        while (calc.t < t_0)
            calc.iteration(false);
        while (calc.t < t_0 + t_m) {
            calc.iteration(true);
            arrayList.add(c3.getKsi(calc.t));
        }

        ew.addColumn("theor", trim(arrayList, 200));
        ew.addColumn("theor", trim(calc.ksiList, 200));
        ew.write("cst4");

//
//        for (double alpha = 5; alpha <= 30; alpha = round(alpha + 5, 0)) {
//            for (double kappa = 1; kappa <= 5; kappa = round(kappa + 0.25, 2)) {
//                System.out.println("alpha="+alpha + ";kappa="+kappa);
//                Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, phi * Math.PI /*phase*/);
//                while (calc.t < t_0)
//                    calc.iteration(false);
//                while (calc.t < t_0 + t_m)
//                    calc.iteration(true);
//                ew.addColumn("alpha="+alpha, trim(calc.ksiList, 100));
//                //ew.addVectorList("kappa="+kappa,trim(calc.track, 50));
//                //System.out.println(calc.sGamma);
//            }
//        }
//
//        ew.write("kappa_more_then_one1");



//        for (double alpha : alphas) {
//            ArrayList<Double> list = new ArrayList<>();
//            for (double kappa = 2.0; kappa <= 3.5; kappa = round(kappa + 0.01, 2)) {
//                System.out.println(alpha + " \\ " + kappa);
//                Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, phi * Math.PI /*phase*/);
//                while (calc.t < t_0)
//                    calc.iteration(false);
//                while (calc.t < t_0 + t_m)
//                    calc.iteration(true);
//                list.add(calc.sGamma);
//                System.out.println(calc.sGamma);
//            }
//            ew.addColumn("sGamma", list);
//        }
//        ew.write("alpha=50t=5000");

System.exit(0);

//
//        ExcelWriter excelWriter = new ExcelWriter();
//
//        for (double alpha : alphas) {
//            ArrayList<Double> list = new ArrayList<>();
//            for (double phi = 0; phi <= 2; phi = round(phi + 0.01, 2)) {
//                System.out.println(alpha+" \\ "+phi);
//                Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, phi * Math.PI /*phase*/);
//                while (calc.t < t_0)
//                    calc.iteration(false);
//                while (calc.t < t_0 + t_m)
//                    calc.iteration(true);
//                list.add(calc.sGamma);
//            }
//            excelWriter.addColumn("sGamma", list);
//
//        }
//        excelWriter.write("Alpha=5-10-20-30");


//        for (double alpha : alphas) {
//            Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, phase /*phase*/);
//            while (calc.t < t_0)
//                calc.iteration(false);
//            while (calc.t < t_0 + t_m)
//                calc.iteration(true);
//            excelWriter.addColumn("ksi", trim(calc.ksiList, 10));
//            excelWriter.addVectorList("track", trim(calc.track, 10));
//        }
//        excelWriter.write("Ksi2");
    }



    private static void bla() {
        ExcelWriter ew = new ExcelWriter();
        for (double phase = 0; phase < 2; phase = round(phase + 0.1, 1)) {
            System.out.println(phase);
            for (double kappa = 0.1; kappa <= 1.0; kappa = round(kappa + 0.1, 1)) {
                ArrayList<Double> arrayList = new ArrayList<>(100);
                for (double alpha0 = 1; alpha0 <= 100; alpha0++)
                    arrayList.add(new CalculateSyTheor2(alpha0, kappa, phase * Math.PI).getS_gamma());
                ew.addColumn(phase + " PI", arrayList);
            }
        }
        ew.write("S_y");
    }

    private static double countAllSlist(double alpha, double kappa, double phase) {
        double t_0 = 10;
        double t_m = 0.5;
        Calculator5 calc = new Calculator5(alpha, kappa * alpha / 4 /*psi_m*/, phase /*phase*/);
        while (calc.t < t_0)
            calc.iteration(false);
        while (calc.t < t_0 + t_m)
            calc.iteration(true);
        return calc.sGamma;
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

    private static ArrayList trim(ArrayList list, int trim) {
        ArrayList newList = new ArrayList();
        int counter = trim;

        for (int i = 0; i < list.size(); i++)
            if (counter++ >= trim) {
                newList.add(list.get(i));
                counter = 0;
            }
        return newList;
    }


    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
