package de.synesthesy.midi;

import junit.framework.TestCase;

public class MidiHandlerTest extends TestCase {
	public void testInit() throws InterruptedException {
		MidiHandler mh = MidiHandler.getInstance();
		mh.init();
		Thread.sleep(10000);
	}
}
