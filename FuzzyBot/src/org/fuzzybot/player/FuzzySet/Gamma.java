package org.fuzzybot.player.FuzzySet;

public class Gamma {
	private float alpha, beta;
	
	public Gamma(float a, float b) {
		alpha = a;
		beta = b;
	}
	
	public float getValue(float x) {
		float mi;
		
		if(x < alpha) {
			return 0;
		}
		else if(x >= alpha && x <= beta) {
			mi = (x - alpha) / (beta - alpha);
			return mi;
		}
		else return 1;
	}
}
