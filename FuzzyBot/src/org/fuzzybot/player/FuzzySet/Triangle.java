package org.fuzzybot.player.FuzzySet;

public class Triangle {

	private float alpha, beta, gamma;
	
	public Triangle(float a, float b, float g) {
		
		alpha = a;
		beta = b;
		gamma = g;
	}
	
	public float getValue(float x) {
		float mi;
		
		if(x <= alpha || x >= gamma) {
			return 0;
		}
		else if(x >= alpha && x <= beta) {
			mi = (x - alpha) / (beta - alpha);
			return mi;
		}
		else if (x > beta && x < gamma) {
			mi = (gamma - x) / (gamma - beta);
			return mi;
		}
		else return 0;
	}
}
