package org.fuzzybot.player.FuzzySet;

public class L {
	private float alpha, beta;
	
	public L(float a, float b) {
		alpha = a;
		beta = b;
	}
	
	public float getValue(float x) {
		float mi;
		
		if(x < alpha) {
			return 1;
		}
		else if(x >= alpha && x <= beta) {
			mi = (beta - x) / (beta - alpha);
			return mi;
		}
		else return 0;
	}
}
