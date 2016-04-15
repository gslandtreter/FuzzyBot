package org.fuzzybot.player;

import java.io.IOException;
import java.net.UnknownHostException;

import org.fuzzybot.player.FuzzySet.Gamma;
import org.fuzzybot.player.FuzzySet.L;
import org.fuzzybot.player.FuzzySet.PrismaBizarro;
import org.fuzzybot.player.FuzzySet.Triangle;
import org.robosoccer.Geom;
import org.robosoccer.RoboSoccer;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Testesss");
		
		
		
		float frente [] = new float[361];
		float esquerda [] = new float[361];
		float direita [] = new float[361];
		float tras [] = new float[361];
		
		float[][][] alfa = new float[16][361][361];
		
		float[][] esqFR = new float[361][361];
		float[][] esqR = new float[361][361];
		float[][] esqS = new float[361][361];
		float[][] esqF = new float[361][361];
		float[][] esqFF = new float[361][361];
		
		float[][] dirFR = new float[361][361];
		float[][] dirR = new float[361][361];
		float[][] dirS = new float[361][361];
		float[][] dirF = new float[361][361];
		float[][] dirFF = new float[361][361];
		
		float[][] zesq = new float[361][361];
		float[][] zdir = new float[361][361];
		
		//Define conjuntos fuzzy
		
		Triangle triaFrente = new Triangle(-90f, 0f, 90f);
		L lEsquerda = new L(-135f, 45f);
		Gamma gammaDireita = new Gamma(45f, 135f);
		PrismaBizarro primaTras = new PrismaBizarro(-180f, -90f, 90f, 180f);
		
		for(int i = 0; i < frente.length; i++) {
			int x = i - 180;
			frente[i] = triaFrente.getValue(x);
			esquerda[i] = lEsquerda.getValue(x);
			direita[i] = gammaDireita.getValue(x);
			tras[i] = primaTras.getValue(x);
		}
		
		//Regras de ativacao
		for(int i = 0; i < frente.length; i++) {
			for(int j = 0; j < frente.length; j++) {
				
				//Matriz de ativacao
				alfa[0][i][j] = Float.min(esquerda[i], esquerda[j]);
				alfa[1][i][j] = Float.min(esquerda[i], frente[j]);
				alfa[2][i][j] = Float.min(esquerda[i], direita[j]);
				alfa[3][i][j] = Float.min(esquerda[i], tras[j]);
				
				alfa[4][i][j] = Float.min(frente[i], esquerda[j]);
				alfa[5][i][j] = Float.min(frente[i], frente[j]);
				alfa[6][i][j] = Float.min(frente[i], direita[j]);
				alfa[7][i][j] = Float.min(frente[i], tras[j]);
				
				alfa[8][i][j] = Float.min(direita[i], esquerda[j]);
				alfa[9][i][j] = Float.min(direita[i], frente[j]);
				alfa[10][i][j] = Float.min(direita[i], direita[j]);
				alfa[11][i][j] = Float.min(direita[i], tras[j]);
				
				alfa[12][i][j] = Float.min(tras[i], esquerda[j]);
				alfa[13][i][j] = Float.min(tras[i], frente[j]);
				alfa[14][i][j] = Float.min(tras[i], direita[j]);
				alfa[15][i][j] = Float.min(tras[i], tras[j]);
				
				//////////////////////////////////
				
				//esquerda Full Reverse
				esqFR[i][j] = Float.max(alfa[3][i][j], alfa[7][i][j]);
				esqFR[i][j] = Float.max(esqFR[i][j], alfa[11][i][j]);
				esqFR[i][j] = Float.max(esqFR[i][j], alfa[15][i][j]);
				
				//esquerda Reverse
				esqR[i][j] = 0f;
				
				//esquerda Stationary
				esqS[i][j] = Float.max(alfa[1][i][j], alfa[6][i][j]);
				esqS[i][j] = Float.max(esqS[i][j], alfa[12][i][j]);
				
				//esquerda Forward
				esqF[i][j] = Float.max(alfa[0][i][j], alfa[4][i][j]);
				esqF[i][j] = Float.max(esqF[i][j], alfa[9][i][j]);
				esqF[i][j] = Float.max(esqF[i][j], alfa[10][i][j]);
				esqF[i][j] = Float.max(esqF[i][j], alfa[13][i][j]);
				esqF[i][j] = Float.max(esqF[i][j], alfa[14][i][j]);
				
				//esquerda FastForward
				esqFF[i][j] = Float.max(alfa[5][i][j], alfa[8][i][j]);
				
				/////////////////////////////////
				
				
				//////////////////////////////////
							
				//direita Full Reverse
				dirFR[i][j] = 0f;
				
				//direita Reverse
				dirR[i][j] = 0f;
				
				//direita Stationary
				dirS[i][j] = Float.max(alfa[4][i][j], alfa[8][i][j]);
				dirS[i][j] = Float.max(dirS[i][j], alfa[9][i][j]);
				dirS[i][j] = Float.max(dirS[i][j], alfa[14][i][j]);
				
				//direita Forward
				dirF[i][j] = Float.max(alfa[0][i][j], alfa[1][i][j]);
				dirF[i][j] = Float.max(dirF[i][j], alfa[6][i][j]);
				dirF[i][j] = Float.max(dirF[i][j], alfa[10][i][j]);
				dirF[i][j] = Float.max(dirF[i][j], alfa[12][i][j]);
				dirF[i][j] = Float.max(dirF[i][j], alfa[13][i][j]);
				
				//direita FastForward
				dirFF[i][j] = Float.max(alfa[2][i][j], alfa[3][i][j]);
				dirFF[i][j] = Float.max(dirFF[i][j], alfa[5][i][j]);
				dirFF[i][j] = Float.max(dirFF[i][j], alfa[7][i][j]);
				dirFF[i][j] = Float.max(dirFF[i][j], alfa[11][i][j]);
				dirFF[i][j] = Float.max(dirFF[i][j], alfa[15][i][j]);
				
				/////////////////////////////////
				
				zesq[i][j] =  (-1f * esqFR[i][j] + -0.5f * esqR[i][j] + 0f * esqS[i][j] + 0.5f * esqF[i][j] + 1f * esqFF[i][j])
						/ (esqFR[i][j] + esqR[i][j] + esqS[i][j] + esqF[i][j] + esqFF[i][j]);
				
				zdir[i][j] =  (-1f * dirFR[i][j] + -0.5f * dirR[i][j] + 0f * dirS[i][j] + 0.5f * dirF[i][j] + 1f * dirFF[i][j])
						/ (dirFR[i][j] + dirR[i][j] + dirS[i][j] + dirF[i][j] + dirFF[i][j]);
						 
			}
		}
		
		RoboSoccer soccer = new RoboSoccer("localhost", 1024);
		
		while(true) {
			
			//pega angulos
			int myID = soccer.getId();
			double anguloAlvo =  soccer.getEnvironment().getTargetAngle(myID);
			double anguloBola =  soccer.getEnvironment().getBallAngle(myID);
			
			/*double anguloBolaAlvo = Geom.angle(soccer.getEnvironment().getBall(), soccer.getEnvironment().getTarget(myID));
			anguloBolaAlvo = anguloAlvo - anguloBola;
			anguloBolaAlvo = Geom.fixAngle(anguloBolaAlvo);*/
			
			float anguloAlvoGraus = -(float)Math.toDegrees(anguloAlvo);
			float anguloBolaGraus = -(float)Math.toDegrees(anguloBola);
			//float anguloBolaAlvoGraus =  (float)Math.toDegrees(anguloBolaAlvo);
	        
			String outStr = String.format("angulo alvo, bola: %f %f", anguloAlvoGraus, anguloBolaGraus);
			
			//pega indice na matriz baseado no angulo
			int indexAlvo = Math.round(anguloAlvoGraus) + 180;
			int indexBola = Math.round(anguloBolaGraus) + 180;
			
			//accessa matriz para pegar valores
			float forcaEsq = zesq[indexBola][indexAlvo];
			float forcaDir = zdir[indexBola][indexAlvo];
			
			
			System.out.println(outStr);
                                         
			soccer.doAction(forcaEsq, forcaDir);
		}
		
	}

}
