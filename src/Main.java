import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

		int count = 10000;

		Calculator calc = new Calculator();
		ArrayList<Double> listKsi = new ArrayList<Double>(count);
		ArrayList<Double> listX = new ArrayList<Double>(count);
		ArrayList<Double> listY = new ArrayList<Double>(count);

		for (double phase = 0; phase < 1.001; phase += 0.1) {
			double[] u;
			for (int i = 0; i < count; i++) {
				calc.RungeKuttIteration();
				u = calc.getU();
				calc.ux += u[0];
				calc.uy += u[1];
				listX.add(calc.ux);
				listY.add(calc.uy);
				listKsi.add(calc.ksi);
			}

			//DrawComponents.wright(listX, "res/listX.phase = " + phase);
			//DrawComponents.wright(listY, "res/listY.phase = " + phase);
			//DrawComponents.wright(listKsi, "res/listKsi.phase = " + phase);

			//writeDoubleList(listX, "res/listX.phase = " + phase);
			writeDoubleList(listY, "res/listY.phase = " + round(phase, 2));
			//writeDoubleList(listKsi, "res/listKsi.phase = " + phase);
		}

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
		writeDoubleList(listUx, "res/listUx");
		writeDoubleList(listUy, "res/listUy");
		//writeDoubleList(listKsi, "res/listKsi");
		//writeDoubleList(listX, "res/listX");
		//writeDoubleList(listY, "res/listY");
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

	private static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}