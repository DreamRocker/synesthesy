package de.synesthesy.nn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import au.com.bytecode.opencsv.CSVReader;

public class CSVTestSetLoader{
	private String path = "";
	
	public CSVTestSetLoader(String path){
		this.path= path;
	}
	
	public CSVTestSetInOutput readCSV(int inputs, int outputs) throws FileNotFoundException, IOException{
		String [] nextLine;
		CSVReader reader = new CSVReader(new FileReader(this.path),';','\'',1);
		CSVTestSetInOutput io = new CSVTestSetInOutput();
		
		while ((nextLine = reader.readNext()) != null) {
			Vector <Double> inLine = new Vector <Double>(inputs);
			Vector <Double> outLine = new Vector <Double>(outputs);
	        for (int i=0; i < inputs; i++){
	        	inLine.add(Double.parseDouble(nextLine[i]));
	        }
	        for (int i=inputs; i < inputs+outputs; i++){
	        	outLine.add(Double.parseDouble(nextLine[i]));
	        }
	        io.getInput().add((inLine));
	        io.getOutput().add((outLine));
	    }
		reader.close();
	    return io;
	}
		
}
