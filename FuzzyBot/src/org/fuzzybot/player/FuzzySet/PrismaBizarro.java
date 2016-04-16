package org.fuzzybot.player.FuzzySet;

public class PrismaBizarro {

	private float alpha, beta, gamma, delta;
	private L inicio;
	private Gamma fim;
	
	public PrismaBizarro(float a, float b, float g, float d) {
		alpha = a;
		beta = b;
		gamma = g;
		delta = d;
		
		inicio = new L(alpha, beta);
		fim = new Gamma(gamma, delta);
		
	}
	
	public float getValue(float x) {
		if (x <= beta) {
			return inicio.getValue(x);
		}
		else return fim.getValue(x);
	}
}
