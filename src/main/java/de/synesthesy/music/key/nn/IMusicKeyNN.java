package de.synesthesy.music.key.nn;

import org.encog.neural.networks.BasicNetwork;

import de.synesthesy.csv.CSVTestSetInOutput;

public interface IMusicKeyNN {
	public BasicNetwork getNetwork();
	public int getIns();
	public int getOuts();
	public int[] getHiddenNeurons();
	public double[] compute(double[] input);
	public void train(CSVTestSetInOutput testSet);		
}