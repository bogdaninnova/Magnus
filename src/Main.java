import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class Main {




    public static void main(String[] args) {
        ZipUtils.saveCodeInHistory();

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


    private static void calc() {
        ExcelWriter ew = new ExcelWriter();

        double phase = 0;
        String name = "10 - 1000";
        for (double alpha = 10; alpha <= 1000; alpha += 10) {
            ArrayList<Double> listS = new ArrayList<>();
            for (double psi = 10; psi <= 1000; psi += 10) {
                System.out.println("alpha = " + alpha + "; psi = " + psi + "; " + new Date());
                Calculator4 c = new Calculator4(alpha, psi, phase);
                double time = 5;
                while (c.t < time)
                    c.iteration(false);
                while (c.t < time+0.5)
                    c.iteration(true);
                listS.add(c.sGamma);
            }
            ew.addColumn(name, listS);
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
