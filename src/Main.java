import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

		Calculator calc = new Calculator();
		ArrayList<Double> listKsi = new ArrayList<Double>(10000);
		ArrayList<Double> listX = new ArrayList<Double>(10000);
		ArrayList<Double> listY = new ArrayList<Double>(10000);


		ArrayList<Double> listUx = new ArrayList<Double>(10000);
		ArrayList<Double> listUy = new ArrayList<Double>(10000);

		double k = 1;
		double ux;
		double uy;
			calc.BETTA = k;
			for (int i = 0; i < 4000; i++)
				calc.RungeKuttIteration();

			ux = 0;
			uy = 0;
			int p = 10000;
			for (int i = 0; i < p; i++) {
				calc.RungeKuttIteration();
				calc.ux += calc.getUx();
				calc.uy += calc.getUy();
				listX.add(calc.ux);
				listY.add(calc.uy);
				ux += calc.ux;
				uy += calc.uy;
			}
			listUx.add(ux / p);
			listUy.add(uy / p);

			calc.reset();

		writeDoubleList(listUx, "listUx");
		writeDoubleList(listUy, "listUy");
		writeDoubleList(listX, "listX");
		writeDoubleList(listY, "listY");


    }


	public static void circle() {
		Calculator calc = new Calculator();
		//ArrayList<Double> listKsi = new ArrayList<Double>(10000);
		//ArrayList<Double> listX = new ArrayList<Double>(10000);
		//ArrayList<Double> listY = new ArrayList<Double>(10000);


		ArrayList<Double> listUx = new ArrayList<Double>(10000);
		ArrayList<Double> listUy = new ArrayList<Double>(10000);

		double ux;
		double uy;
		for (double k = 0; k < 5; k += 0.01) {
			calc.BETTA = k;
			System.out.println(k);
			for (int i = 0; i < 4000; i++)
				calc.RungeKuttIteration();

			ux = 0;
			uy = 0;
			for (int i = 0; i < 1000; i++) {
				calc.RungeKuttIteration();
				calc.ux += calc.getUx();
				calc.uy += calc.getUy();

				ux += calc.ux;
				uy += calc.uy;
			}
			System.out.println(ux / 1000);
			System.out.println(uy / 1000);
			System.out.println();
			listUx.add(ux / 1000);
			listUy.add(uy / 1000);

			calc.reset();
		}
		writeDoubleList(listUx, "listUx");
		writeDoubleList(listUy, "listUy");
		//writeDoubleList(listKsi, "listKsi");
		//writeDoubleList(listX, "listX");
		//writeDoubleList(listY, "listY");
	}
    
	@SuppressWarnings("resource")
	public static void writeDoubleList(List<Double> averrageList, String name) {
		ListIterator<Double> iter = averrageList.listIterator();
		File file = new File(name + ".txt");
		try {
			FileWriter writer = new FileWriter(file);
			while (iter.hasNext())
				writer.append(iter.next() + "\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}