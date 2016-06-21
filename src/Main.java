import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        ArrayList<Vector> list = new ArrayList<>();
        for (double i = 0; i <= 10 * Math.PI; i += Math.PI / 20) {
            list.add(calculate(i));
            System.out.println(i / Math.PI);
        }
        ExcelWriter ew = new ExcelWriter();
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


}
