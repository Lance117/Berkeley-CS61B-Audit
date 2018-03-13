/** Class that runs n-body simulation.
 *  @author LANCE WONG
 */

public class NBody {

	/**
	 * Main method to define command line args and run animation.
	 * @T: total time of simulation
	 * @dt: change in time after an iteration
	 * @filename: file with universe details
	 */
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double rUniverse = readRadius(filename);

		/* Draws initial universe state */
		StdDraw.setScale(-rUniverse, rUniverse);	
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Planet p : planets)
			p.draw();
		StdDraw.enableDoubleBuffering();

		/* Creates animation */
		double time;
		int numPlanets = planets.length;
		for (time = 0; time < T; time += dt) {
			double[] xForces = new double[numPlanets];	
			double[] yForces = new double[numPlanets];	
			int index = 0;
			for (Planet p : planets) {
				xForces[index] = p.calcNetForceExertedByX(planets);	
				yForces[index] = p.calcNetForceExertedByY(planets);	
				index++;
			}
			index = 0;
			for (Planet p : planets) {
				p.update(dt, xForces[index], yForces[index]);
				index++;
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet p : planets)
				p.draw();
			StdDraw.show();
			StdDraw.pause(10);
		}

		/* Prints the universe */
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", rUniverse);
		for (int i = 0; i < planets.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  	  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  	  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
		}
	}

	/**
	 * Reads radius from a file.
	 */
	public static double readRadius(String fileName) {
		In fp = new In(fileName);
		int numPlanets = fp.readInt();
		double rUniverse = fp.readDouble();
		return rUniverse;
	}	

	/**
	 * Instantiates planets defined in file.
	 * Return: array of Planet objects
	 */
	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int numPlanets = in.readInt();
		double rUniverse = in.readDouble();
		Planet[] planets = new Planet[numPlanets];
		for (int i = 0; i < numPlanets; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			planets[i] = new Planet(xP, yP, xV, yV, m, img);
		}	
		return planets;
	}
}
