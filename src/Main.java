import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {




    public static void main(String[] args) {
        ZipUtils.saveCodeInHistory();
        getS_alpha();
        System.exit(0);
        double alpha0 = 100;
        ExcelWriter ew = new ExcelWriter();
        for (double phase = 4.0/3.0; phase <= 2; phase = round(phase + 2.25, 2)){
            Calculator5 calculatorNUM = new Calculator5(alpha0, 0.25 * Math.PI, phase * Math.PI);
            double t0 = 0;
            while (calculatorNUM.t < t0)
                calculatorNUM.iteration(false);
            while (calculatorNUM.t < t0 + 2)
                calculatorNUM.iteration(true);
            ew.addVectorList("phase=" + phase, calculatorNUM.track, "X", "Y");
            System.out.println(phase);
            //ew.addVectorList("Main", calculatorNUM.track);
        }



        ew.write("Main5");
    }



    private static void getS() {
        ExcelWriter ew = new ExcelWriter();

        for (double alpha0 = 50; alpha0 <= 500; alpha0 = round(alpha0 + 50, 0)){
            ArrayList<Double> arrayList = new ArrayList<>();
            for (double phase = 0; phase < 2.001; phase = round(phase + 0.05, 2)) {
                Calculator5 calculatorNUM = new Calculator5(alpha0, 0.25 * Math.PI, phase * Math.PI);
                double t0 = 0;
                while (calculatorNUM.t < t0)
                    calculatorNUM.iteration(false);
                while (calculatorNUM.t < t0 + 2)
                    calculatorNUM.iteration(true);

                arrayList.add(calculatorNUM.sGamma);


//                    ew.addColumn("other", calculatorNUM.ksiList);
//                    ew.addVectorList("other", calculatorNUM.track);
//                TextWriter.writeDoubleList(calculatorNUM.ksiList, "ksiList_psi=" + psi + "_phase=" + phase + "alpha=" + alpha0);
//                TextWriter.writeTraectorysCoordinates(calculatorNUM.track, "track_psi=" + psi + "_phase=" + phase + "alpha=" + alpha0);
            }
            ew.addColumn("s_phase", arrayList);
            System.out.println(new Date());
            System.out.println(alpha0);
            System.out.println();
        }
        ew.write("s_phase");
    }




    private static void getS_alpha() {
        ExcelWriter ew = new ExcelWriter();
        for (double phase = 0; phase < 2.001; phase = round(phase + 0.1, 1))
        {
            ArrayList<Double> arrayList = new ArrayList<>();
            for (double alpha0 = 1; alpha0 <= 100; alpha0 = round(alpha0 + 1, 0)) {
                Calculator5 calculatorNUM = new Calculator5(alpha0, 0.25 * Math.PI, phase * Math.PI);
                double t0 = 0;
                while (calculatorNUM.t < t0)
                    calculatorNUM.iteration(false);
                while (calculatorNUM.t < t0 + 2)
                    calculatorNUM.iteration(true);

                arrayList.add(calculatorNUM.sGamma);

            }
            ew.addColumn("s_alpha", arrayList);
            System.out.println(new Date());
            System.out.println(phase);
            System.out.println();
        }
        ew.write("s_alpha");
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
