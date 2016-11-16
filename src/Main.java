import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {




    public static void main(String[] args) {
        ZipUtils.saveCodeInHistory();

        double psi = 0.8;


        ExcelWriter ew = new ExcelWriter();


        for (double phase = 0; phase < 2.001; phase += 0.1) {
            ArrayList<Double> sList = new ArrayList<>();
            for (double alpha0 = 10; alpha0 <= 500; alpha0 += 10) {
                Calculator5 calculatorNUM = new Calculator5(alpha0, psi * Math.PI, phase * Math.PI);
                double t0 = 5;
                while (calculatorNUM.t < t0)
                    calculatorNUM.iteration(false);
                while (calculatorNUM.t < t0 + 0.5)
                    calculatorNUM.iteration(true);

                System.out.println(phase);
                System.out.println(alpha0);
                System.out.println();
                sList.add(calculatorNUM.sGamma);

//                TextWriter.writeDoubleList(calculatorNUM.ksiList, "ksiList_psi=" + psi + "_phase=" + phase + "alpha=" + alpha0);
//                TextWriter.writeTraectorysCoordinates(calculatorNUM.track, "track_psi=" + psi + "_phase=" + phase + "alpha=" + alpha0);
            }
            ew.addColumn("s_alpha", sList);
        }
        ew.write("s_alpha");
    }



//    {
//        double psi = 1.5;
//
//
//        for (double phase = 0; phase <= 1; phase = round(phase + 0.1, 1)) {
//            ArrayList<Double> alCST_num = new ArrayList<>();
//
//            for (double nu = 0; nu <= 1; nu = round(nu + 0.01, 2)) {
//                Calculator5 calculatorNUM =
//                        new Calculator5(nu, psi * Math.PI, phase * Math.PI);
//                System.out.println("NU = " + nu + "\n");
//                double t0 = 5;
//                while (calculatorNUM.t < t0)
//                    calculatorNUM.iteration(false);
//                while (calculatorNUM.t < t0 + 0.5)
//                    calculatorNUM.iteration(true);
//                alCST_num.add(calculatorNUM.sGamma);
//            }
//            TextWriter.writeDoubleList(alCST_num, "psi=" + psi + "_phase=" + phase + "alpha=10000");
//        }
//    }





    private void old () {

        ExcelWriter ewCS = new ExcelWriter();
        for (double phaseCS = 1.5; phaseCS <= 1.5; phaseCS = round(phaseCS + 0.1, 1)) {
            ArrayList<Double> alCST = new ArrayList<>();
            for (double alpha = 0; alpha <= 500; alpha += 1) {
                CalculateSyTheor cst = new CalculateSyTheor(alpha, phaseCS * Math.PI);
                while (cst.t < 0.5)
                    cst.iteration();
                alCST.add(cst.s_Gamma);
            }
            ewCS.addColumn("phase+=0.1pi", alCST);
            System.out.println(phaseCS);
            TextWriter.writeDoubleList(alCST, "phaseCST = " + phaseCS);
        }
        ewCS.write("phase+=0.1pi");


        System.exit(0);
        double phaseCST = 0.6;
        ArrayList<Double> alCST_num = new ArrayList<>();
        for (double alpha = 0; alpha <= 100; alpha += 1) {
            System.out.println(alpha);
            CalculatorLimited calculatorNUM =
                    new CalculatorLimited(alpha, 0.25 * alpha, phaseCST * Math.PI);
            double t0 = 5;
            while (calculatorNUM.t < t0)
                calculatorNUM.iteration(false);
            while (calculatorNUM.t < t0 + 0.5)
                calculatorNUM.iteration(true);
            alCST_num.add(calculatorNUM.sGamma);
        }
        TextWriter.writeDoubleList(alCST_num, "phaseNUM = " + phaseCST);







        System.exit(0);
        ExcelWriter ew = new ExcelWriter();
        for (double phase = 0.75; phase <= 1.25; phase = round(phase + 0.25, 2)) {


            for (double alpha = 1; alpha <= 100.001; alpha++) {

                ArrayList<Double> arrayList = new ArrayList<>();
                for (double psi = 0; psi <= 100.001; psi++) {
                    System.out.println("phase = " + phase);
                    System.out.println("psi = " + psi);
                    System.out.println("alpha = " + alpha);
                    System.out.println("Date = " + new Date() + "\n");
                    CalculatorLimited calculator0 =
                            new CalculatorLimited(alpha, psi, phase * Math.PI);
                    double t0 = 5;
                    while (calculator0.t < t0)
                        calculator0.iteration(false);
                    while (calculator0.t < t0 + 0.5)
                        calculator0.iteration(true);
                    arrayList.add(calculator0.sGamma);
                }
                ew.addColumn("phase = " + phase, arrayList);
            }


        }
        ew.write("FIG2 date"+ new Date().getTime());
    }

    private static ArrayList<Double> minus(ArrayList<Double> list2, ArrayList<Double> list1) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++)
            list.add(list2.get(i) - list1.get(i));
        return list;
    }


    private static void calc(double phase) {
        ExcelWriter ew = new ExcelWriter();

        for (int alpha = 100; alpha <= 1000; alpha += 100) {
            ArrayList<Double> listS = new ArrayList<>();
            for (int psi = 0; psi <= 1000; psi += 10) {
                System.out.println("alpha = " + alpha + "; psi = " + psi + "; " + new Date());
                Calculator4 c = new Calculator4(alpha, psi, phase);
                double time = 10;
                while (c.t < time)
                    c.iteration(false);
                while (c.t < time+0.5)
                    c.iteration(true);
                listS.add(c.sGamma);
            }
            ew.addColumn("phase=" + phase, listS);
        }

        ew.write("phase = " + phase);

    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
