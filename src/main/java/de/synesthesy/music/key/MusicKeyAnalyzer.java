package de.synesthesy.music.key;

import java.util.Vector;

import de.synesthesy.music.Note.Note;
import de.synesthesy.music.key.nn.IMusicKeyNN;
import de.synesthesy.music.key.nn.PreTrainedMusicKeyNN;

public class MusicKeyAnalyzer {
	private int nnInputs = 12;
	private int nnOutputs = 15;
	private int[] nnHidden = { 24 };
	private IMusicKeyNN keyDetermination; 
	
	public MusicKeyAnalyzer(){
		keyDetermination = new PreTrainedMusicKeyNN();
	}
	
	public MusicKeyAnalyzer(IMusicKeyNN nn){
		keyDetermination = nn;
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
