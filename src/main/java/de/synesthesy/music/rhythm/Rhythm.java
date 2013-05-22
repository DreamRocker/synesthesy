package de.synesthesy.music.rhythm;

import java.util.Vector;

import de.synesthesy.music.SampledNote;

public class Rhythm {
	Vector<SampledNote> rhythmicNotes;

	public Rhythm(Vector<SampledNote> rhythm) {
		this.rhythmicNotes = rhythm;
	}

	@Override
	public String toString() {
		String ret = "";
		int last_sample = 0;
		for (SampledNote nt : rhythmicNotes) {
			if (nt.getSampleInterval() - last_sample > 0) {
				for (int i = 0; i < nt.getSampleInterval() - last_sample; i++)
					ret += ".";
			}
			last_sample = nt.getSampleInterval();
			ret += "|" + nt.toString() + "|";
		}
		return "Rhythm: " + ret;
	}

}
