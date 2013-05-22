package de.synesthesy.music.util;

public class ErrorFunction {
	private static ErrorFunction errorFunction;

	private ErrorFunction() {
	};

	public static ErrorFunction getInstance() {
		if (errorFunction == null) {
			return new ErrorFunction();
		}
		return errorFunction;
	}

	public double calculateError(double[] x, double[] y) {
		double err = 0;
		for (int i = 0; i < x.length; i++) {
			err += Math.abs(y[i] - x[i]);
		}
		return err;
	}

	/**
	 * Calculates error for a vector on a given repetition factor k
	 * 
	 * @param x
	 * @param k
	 * @return
	 */
	public double calculateError(double[] x, int k) {
		double err = 0;
		for (int i = 0; i < x.length; i++) {
			err += Math.abs(x[i] - x[i % k]);
		}
		return err;
	}

	/**
	 * Calculates error for a vector on a given repetition factor k and a
	 * looseness of m %
	 * 
	 * @param x
	 * @param k
	 * @param looseness
	 * @return
	 */
	public double calculateError(double[] x, int k, float looseness) {
		double err = 0;
		int absLooseness = Math.round(x.length * looseness);
		return calculateError(x, k, absLooseness);
		}
	
	/**
	 * Calculates error for a vector on a given repetition factor k and a
	 * absolute looseness of m
	 * 
	 * @param x
	 * @param k
	 * @param looseness
	 * @return
	 */
	public double calculateError(double[] x, int k, int looseness) {
		double err = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] != x[i % k]) {
				boolean foundNeighbour = false;
				for (int m = 1; m <= looseness; m++) {
					if ((k-m > 0) && (x[i] == x[i % (k-m)])){
						foundNeighbour = true;
						break;
					}
					if (x[i] == x[i % (k + m)]){
						foundNeighbour = true;
						if (x.length > i+m){
							i += m;
						}
						break;
					}
				}
				if (!foundNeighbour){
					err += Math.abs(x[i] - x[i % k]);
				}
			}
		}
		return err;
	}

}
