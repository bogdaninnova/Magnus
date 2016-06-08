public class Main {

    public static void main(String[] args) {

        int m = 3;
   //     m = 30;
        int n = 3;
        Calculator2 calc = new Calculator2();

        while (calc.t < m)
            calc.iteration(false);

        while (calc.t < m + n)
            calc.iteration(true);

        System.out.println(calc.L.getX());

        System.out.println(calc.getAverU().getX());
        System.out.println(calc.getAverU().getY());
        System.out.println(calc.getAverU().getZ());

        TextWriter.writeTraectorysCoordinates(calc.locationList, "location");
        TextWriter.writeTraectorysCoordinates(calc.magnetizationList, "magnetization");
        TextWriter.writeTraectorysCoordinates(calc.fieldList, "fieldList");





    }



}
