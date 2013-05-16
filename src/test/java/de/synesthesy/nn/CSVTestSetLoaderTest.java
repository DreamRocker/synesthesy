package de.synesthesy.nn;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import de.synesthesy.nn.CSVTestSetInOutput;
import de.synesthesy.nn.CSVTestSetLoader;

public class CSVTestSetLoaderTest extends TestCase {
	final static int ins = 12;
	final static int outs = 15;

	CSVTestSetLoader reader;
	public void testCSVTestSetLoader() throws FileNotFoundException {
		reader = new CSVTestSetLoader("resource/chords_training.csv");
	}

	public void testReadCSV() throws IOException {
		reader = new CSVTestSetLoader("resource/chords_training.csv");
		CSVTestSetInOutput testVec = reader.readCSV(ins, outs);
		assertEquals(ins, testVec.getInput().get(0).size());
		assertEquals(outs, testVec.getOutput().get(0).size());
	}
}
