package org.fuzzybot.player;

import java.io.IOException;
import java.net.UnknownHostException;

import org.robosoccer.RoboSoccer;

import gnu.getopt.Getopt;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Main {

	private static String hostName = "localhost";
	private static int port = 1024;
	private static String fileName = "fcl/robotBrain.fcl";
	private static final boolean debug = false;

	private static void getArgs(String[] argv) {

		Getopt g = new Getopt("robot", argv, "h:p:f:");
		//
		int c;
		String arg;
		while ((c = g.getopt()) != -1) {
			switch (c) {
			case 'h':
				arg = g.getOptarg();
				System.out.print(
						"You picked " + (char) c + " with an argument of " + ((arg != null) ? arg : "null") + "\n");
				
				hostName = arg;
				break;
			//
			case 'p':
				arg = g.getOptarg();
				System.out.print(
						"You picked " + (char) c + " with an argument of " + ((arg != null) ? arg : "null") + "\n");
				
				port = Integer.parseInt(arg);
				break;
			//
				
			case 'f':
				arg = g.getOptarg();
				System.out.print(
						"You picked " + (char) c + " with an argument of " + ((arg != null) ? arg : "null") + "\n");
				
				fileName = arg;
				break;
			//
			case '?':
				break; // getopt() already printed an error
			//
			default:
				System.out.print("getopt() returned " + c + "\n");
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		getArgs(args);
		
		// Load from 'FCL' file
		FIS fis = FIS.load(fileName, true);

		// Error while loading?
		if (fis == null) {
			System.err.println("Can't load file: '" + fileName + "'");
			return;
		}

		FunctionBlock functionBlock = fis.getFunctionBlock("robotBrain");

		// Show
		// JFuzzyChart.get().chart(functionBlock);

		// Show output variable's chart

		// JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

		// Print ruleSet
		// System.out.println(fis);

		MyRoboSoccer soccer = new MyRoboSoccer(hostName, port, debug);

		while (true) {

			// pega angulos
			int myID = soccer.getId();
			double anguloAlvo = soccer.getEnvironment().getTargetAngle(myID);
			double anguloBola = soccer.getEnvironment().getBallAngle(myID);
			double anguloObstaculo = soccer.getEnvironment().getObstacleAngle(myID);
			double distanciaObstaculo = soccer.getEnvironment().getObstacleDistance(myID);
			double distanciaBola = soccer.getEnvironment().getBallDistance(myID);
			double angularMovement = soccer.getEnvironment().getRobot(myID).getAngle()
					- soccer.getEnvironment().getRobot(myID).getOldAngle();

			double mySize = soccer.getEnvironment().getRobotSize();

			distanciaObstaculo -= mySize;
			distanciaBola -= mySize;

			if (distanciaObstaculo < 0)
				distanciaObstaculo = 0;

			if (distanciaBola < 0)
				distanciaBola = 0;

			float anguloAlvoGraus = -(float) Math.toDegrees(anguloAlvo);
			float anguloBolaGraus = -(float) Math.toDegrees(anguloBola);
			float anguloObstaculoGraus = -(float) Math.toDegrees(anguloObstaculo);
			float angularDifferenceGraus = -(float) Math.toDegrees(angularMovement);

			String outStr = String.format(
					"angulo alvo, bola: %f %f\n angulo obstaculo %f, distancia %f - DistanciaBola: %f - AngularDifference: %f",
					anguloAlvoGraus, anguloBolaGraus, anguloObstaculoGraus, distanciaObstaculo, distanciaBola,
					angularDifferenceGraus);

			// Set inputs
			fis.setVariable("ball_angle", anguloBolaGraus);
			fis.setVariable("target_angle", anguloAlvoGraus);
			fis.setVariable("obstacle_angle", anguloObstaculoGraus);
			fis.setVariable("obstacle_distance", distanciaObstaculo);
			fis.setVariable("ball_distance", distanciaBola);

			// Evaluate
			fis.evaluate();

			Variable leftWheelTurn = functionBlock.getVariable("leftWheelTurn");
			Variable rightWheelTurn = functionBlock.getVariable("rightWheelTurn");

			// accessa matriz para pegar valores
			float forcaEsq = (float) leftWheelTurn.getValue();
			float forcaDir = (float) rightWheelTurn.getValue();

			System.out.println(outStr);

			soccer.doAction(forcaEsq, forcaDir);
		}

	}

}
