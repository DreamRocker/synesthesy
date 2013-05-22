package de.synesthesy.music;

import java.util.Vector;

import de.synesthesy.music.rhythm.RhythmAnalyzer;

import themidibus.Note;

import junit.framework.TestCase;

public class RhythmAnalyzerTest extends TestCase {
	public void testSampleDataFlat() {
		Vector<SampledNote> nt = new Vector<SampledNote>();
		nt.add( new SampledNote(0,127,24206958743L));
		nt.add( new SampledNote(4,60, 24207158798L));
		nt.add( new SampledNote(6,60, 24207347712L));
		nt.add( new SampledNote(0,127,24207658743L));
		nt.add( new SampledNote(4,60, 2420798798L));
		nt.add( new SampledNote(6,60, 24208247712L));
		
		RhythmAnalyzer rh = new RhythmAnalyzer();
		rh.sampleDataFlat(nt);
	}

	public void testFindInterval() {
		//double[] testA = { 1.0, 5.0, 5.0, 0.0, 5,0};
		//double[] testA = { 1.0, 2.0, 1.0, 2.0,1.0, 2.0, 1.0, 2.0,1.0, 2.0, 1.0, 2.0,1.0, 2.0, 1.0, 2.0};
		double[] testA = { 1.0, 2.0, 0.0, 0.0, 1.0, 2.0, 0.0, 1.0, 2.0, 0.0, 1.0, 2.0, 0.0, 1.0, 0.0 ,2.0, 1.0, 0.0,0.0,  2.0,1.0, 0.0, 2.0, 1.0, 2.0};
		
		RhythmAnalyzer rh = new RhythmAnalyzer();
		System.out.println(rh.findInterval(testA));
	}
	
	public void testAnalyze(){
		Vector<SampledNote> nt = new Vector<SampledNote>();
		nt.add( new SampledNote(4,60, 24207158798L));
		nt.add( new SampledNote(5,60, 24207158798L+100000));
		nt.add( new SampledNote(6,60, 24207158798L+400000));
		nt.add( new SampledNote(7,60, 24207158798L+500000));
		nt.add( new SampledNote(7,60, 24207158798L+900000));
		
		RhythmAnalyzer rh = new RhythmAnalyzer();
		rh.setPlayedNotes(nt);
		System.out.println(rh.analyze());
	}

}
