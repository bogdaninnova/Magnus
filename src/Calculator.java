public class Calculator {

	private static final double T = 1000000;
	private static final double M = 1000;
	private static double H = 100;
	private static final double ETA = 0.001;
	private static final double RO = 1;
	private static final double A = Math.pow(10, -5);
	
	private static double ALPHA = T * M * H / 6 / ETA;
	private static double BETTA = RO * M * H * A * A / 36 / ETA / ETA;	
	
	private static double PSI_0 = - ALPHA / 4;
		
	private final double dt = 0.0001;

	private double t = 0;
	public double ksi = 0;

	public double ux = 0;
	public double uy = 0;


	public void iteration() {
		double k1 = equation(t, ksi);
		double k2 = equation(t + dt / 2, ksi + k1 * dt / 2);
		double k3 = equation(t + dt / 2, ksi + k2 * dt / 2);
		double k4 = equation(t + dt, ksi + k3 * dt);
		ksi += dt / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
		t += dt;

		ux += getUx();
		uy += getUy();
	}
	
	private static double equation(double t, double ksi) {
		return - ALPHA * Math.sin(ksi) - getDerivatePsi(t);
	}
	
	private static double getDerivatePsi(double t) {
		double n = 0;
		while (true) {
			n++;
			if ((t >= n - 1) && (t < n - 0.5))
				return 4 * PSI_0;
			if ((t > n - 0.5) && (t < n))
				return -4 * PSI_0;
		}
	}
	
	public double getUx() {
		return Math.sin(2 * Math.PI * t) /
				(1 + BETTA * BETTA * Math.sin(ksi) * Math.sin(ksi));
	}
	
	public double getUy() {
		return - BETTA * Math.sin(ksi) * getUx();
	}
}
