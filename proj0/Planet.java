/** Class defines a planet's members (attributes and methods).
 *  @author LANCE WONG
 */

public class Planet {
	static final double G = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	/**
	 * Constructor given the state of a planet.
	 */
	public Planet(double xP, double yP, double xV,
				  double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/**
	 * Constructor that initializes identical planet.
	 */
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	/**
	 * Calculates distance between two planets.
	 */
	public double calcDistance(Planet p) {
		double r = (p.xxPos - this.xxPos) * (p.xxPos - this.xxPos) +
		   			(p.yyPos - this.yyPos) * (p.yyPos - this.yyPos);
		return Math.sqrt(r);
	}

	/**
	 * Force exerted by p on the object calling this method.
	 */
	public double calcForceExertedBy(Planet p) {
		return (G * this.mass * p.mass) / (this.calcDistance(p) * this.calcDistance(p));
	}

	/**
	 * X-direction force exerted by x on the object calling this method.
	 */
	public double calcForceExertedByX(Planet x) {
		return ((this.calcForceExertedBy(x) * (x.xxPos - this.xxPos)) / this.calcDistance(x));
	}
	
	/**
	 * Y-direction force exerted by y on the object calling this method.
	 */
	public double calcForceExertedByY(Planet y) {
		return ((this.calcForceExertedBy(y) * (y.yyPos - this.yyPos)) / this.calcDistance(y));
	}

	/**
	 * X-direction force exerted by all planets on the object calling this method.
	 */
	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double result = 0;

		for (Planet p : allPlanets) {
			if (this.equals(p))
				continue;
			else
				result += this.calcForceExertedByX(p);
		}
		return result;
	}
	
	/**
	 * Y-direction force exerted by all planets on the object calling this method.
	 */
	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double result = 0;

		for (Planet p : allPlanets) {
			if (this.equals(p))
				continue;
			else
				result += this.calcForceExertedByY(p);
		}
		return result;
	}

	/**
	 * Determines how much the forces exerted on a planet will cause the planet to
	 * accelerate, and the resulting change in the planet's velocity and position
	 * in period of time dt.
	 */
	public void update(double dt, double fX, double fY) {
		double aX = fX / this.mass;
		double aY = fY / this.mass;
		this.xxVel = this.xxVel + dt * aX;
		this.yyVel = this.yyVel + dt * aY;
		this.xxPos += dt * this.xxVel;		
		this.yyPos += dt * this.yyVel;
	}

	/**
	 * Draws a planet in position.
	 */
	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}
