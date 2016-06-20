public class Main {

    public static void main(String[] args) {

        int m = 3;
   //     m = 30;
        int n = 50;
        Calculator2 calc = new Calculator2();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);


        ExcelWriter ew = new ExcelWriter();
        ew.addVectorList("calc.locationList", calc.locationList);
        ew.addVectorList("calc.magnetizationList", calc.magnetizationList);
        ew.addVectorList("calc.fieldList", calc.fieldList);
        ew.write("Magnus");

        System.out.println(calc.getAverU().getX());
        System.out.println(calc.getAverU().getY());
        System.out.println(calc.getAverU().getZ());






    }



}
