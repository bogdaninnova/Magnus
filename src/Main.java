import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

    	phaseTracks(0);
    }
    
    public static void yAverByPhase() {
		ArrayList<Double> list = new ArrayList<Double>(2100);


		
		for (double phase = 0; phase < 2; phase += 0.001) {
			Calculator calc = new Calculator();
			calc.phase = phase * Math.PI;
			
			for (int i = 0; i < 10000; i++)
				calc.RungeKuttIteration();
			
			for (int i = 0; i < 1000; i++) {
				calc.RungeKuttIteration();
				calc.uy += calc.getU()[1];
			}

		}
		
		writeDoubleList(list, "res/list");
    }

    
    public static void phaseTracks(double phase) {
		int count = (int) (10 / Calculator.dt);

		Calculator calc = new Calculator();
		ArrayList<Double> listX = new ArrayList<Double>(count);
		ArrayList<Double> listY = new ArrayList<Double>(count);
		
		int max = 100;
		int counter = 0;
		
		System.out.println(phase);
		calc.phase = phase * Math.PI;
		double[] u;
		for (int i = 0; i < count; i++) {
			counter++;
			calc.RungeKuttIteration();
			u = calc.getU();
			calc.ux += u[0];
			calc.uy += u[1];
			
			if (counter == max) {
				counter = 0;
				listX.add(calc.ux);
				listY.add(calc.uy);
			}
		}

		//DrawComponents.wright(listX, "res/listX_phase = " + phase);
		//DrawComponents.wright(listY, "res/listY_phase = " + phase);
		//DrawComponents.wright(listKsi, "res/listKsi_phase = " + phase);

		

		writeDoubleList(cutTo32(listX), "res/listX.phase = " + round(phase, 2));
		writeDoubleList(cutTo32(listY), "res/listY.phase = " + round(phase, 2));
		//writeDoubleList(listKsi, "res/listKsi.phase = " + phase);
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
	
	public static List<Double> cutTo32(List<Double> list) {
		double size = (double) list.size();
		if (size < 32000)
			return list;
		ArrayList<Double> newList = new ArrayList<Double>(32000);
		double max = size / 32000.0;
		
		double count = 0;
		for (double e : list) {
			count++;
			if (count > max) {
				count = 0;
				newList.add(e);
			}
		}
		return newList;
	}
}