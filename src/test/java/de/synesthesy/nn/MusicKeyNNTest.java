package de.synesthesy.nn;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

public class MusicKeyNNTest extends TestCase {

	public void testMusicKeyNN() {
		int []hidden_neurons = { 5,9,6};
		MusicKeyNN nn = new MusicKeyNN(15,2,hidden_neurons);
		
	}

	public void testTrain() throws FileNotFoundException, IOException {
		MusicKeyNN nn = new MusicKeyNN(12,15, new int[]{24});
		nn.train("resource/chords_training.csv");
	}

}
