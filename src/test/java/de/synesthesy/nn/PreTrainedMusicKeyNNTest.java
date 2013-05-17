package de.synesthesy.nn;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.synesthesy.csv.CSVTestSetInOutput;
import de.synesthesy.csv.CSVTestSetLoader;

import junit.framework.TestCase;

public class PreTrainedMusicKeyNNTest extends TestCase {

	public void testMusicKeyNN() {
		IMusicKeyNN nn = new PreTrainedMusicKeyNN();
	}

	public void testCompute(){
		PreTrainedMusicKeyNN nn = new PreTrainedMusicKeyNN();
		nn.compute(new double[]{1.0,0.0,0.0,0.0,1.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0});
	}

}
