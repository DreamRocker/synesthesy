package de.synesthesy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import themidibus.Note;

import de.synesthesy.csv.CSVTestSetInOutput;
import de.synesthesy.csv.CSVTestSetLoader;
import de.synesthesy.music.MusicKey;
import de.synesthesy.nn.*;

public class Synesthesy {
	private int nnInputs = 12;
	private int nnOutputs = 15;
	private int[] nnHidden = { 24 };
	private IMusicKeyNN keyDetermination;

	public Synesthesy() {
		keyDetermination = new PreTrainedMusicKeyNN();
	}
	
	public Synesthesy(String path) {
		keyDetermination = new MusicKeyNN(nnInputs, nnOutputs, nnHidden);
		CSVTestSetInOutput csv;
		try {
			csv = new CSVTestSetLoader(path).readCSV(
					nnInputs, nnOutputs);
			keyDetermination.train(csv);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MusicKey getMusicKey(Vector<Note> notes) {
		double[] inp = new double[12];
		double[] output = new double[nnOutputs];
		for (Note note : notes) {
			int value = note.getPitch() % 12;
			inp[value] = 1.0;
		}
		output = keyDetermination.compute(inp);
		double highestValue = -1;
		int key = -1;
		for (int i = 0; i < 12; i++) {
			if (highestValue < output[i]) {
				highestValue = output[i];
				key = i;
			}
		}
		MusicKey mKey = new MusicKey(key);
		if (output[12] > 0.5){
			mKey.setIs7(true);
		}
		if (output[13] > 0.5){
			mKey.setMaj7(true);
		}
		if (output[14] > 0.5){
			mKey.setMinor(true);
		}
		
		return mKey;
	}
}
