package de.synesthesy.nn;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.synesthesy.csv.CSVTestSetInOutput;
import de.synesthesy.csv.CSVTestSetLoader;

import junit.framework.TestCase;

public class MusicKeyNNTest extends TestCase {
	MusicKeyNN nn;
	final static int inputs = 12;
	final static int outputs = 15;

	public void testMusicKeyNN() {
		int []hidden_neurons = { 5,9,6};
		MusicKeyNN nn = new MusicKeyNN(15,2,hidden_neurons);
	}

	public void testTrain() throws FileNotFoundException, IOException {
		nn = new MusicKeyNN(inputs,outputs, new int[]{inputs*4});
		CSVTestSetInOutput testSet = new CSVTestSetLoader("resource/chords_training.csv")
				.readCSV(inputs, outputs);
		nn.train(testSet);
		double [] encoded = new double [nn.getNetwork().encodedArrayLength()];
		nn.getNetwork().encodeToArray(encoded);
		System.out.print(nn.getNetwork().encodedArrayLength()+"\n{");
		for (int i = 0; i<encoded.length; i++){
			System.out.print(encoded[i]+",");
		}
		System.out.println("}");
	}
	
	public void testCompute(){
		nn = new MusicKeyNN(inputs,outputs, new int[]{inputs*2});
		nn.compute(new double[]{1.0,0.0,0.0,0.0,1.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0});
	}

}
