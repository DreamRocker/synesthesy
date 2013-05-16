package de.synesthesy.nn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;


public class MusicKeyNN {
	BasicNetwork network = new BasicNetwork();
	int ins, outs;
	int[] neuron_numbers;

	public MusicKeyNN(int ins, int outs, int[] neuron_numbers) {
		this.ins = ins;
		this.outs = outs;
		this.neuron_numbers = neuron_numbers;

		network.addLayer(new BasicLayer(null, true, ins));
		for (int i = 0; i < neuron_numbers.length; i++) {
			network.addLayer(new BasicLayer(new ActivationSigmoid(), true,
					neuron_numbers[i]));
		}
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, outs));
		network.getStructure().finalizeStructure();
		network.reset();
	}

	public void train(String training_file) throws FileNotFoundException,
			IOException {
		CSVTestSetInOutput testSet = new CSVTestSetLoader(training_file)
				.readCSV(ins, outs);

		List <MLDataPair> ml = new Vector<MLDataPair>();
		
		for (int x =0 ; x < testSet.getInput().size(); x++){
			double[] inpArray = new double[testSet.getInput().get(x).size()];
			double[] outpArray = new double[testSet.getOutput().get(x).size()];
			for (int y =0 ; y < testSet.getInput().get(x).size(); y++){
				inpArray[y] = testSet.getInput().get(x).get(y);
			}
			for (int y =0 ; y < testSet.getOutput().get(x).size(); y++){
				outpArray[y] = testSet.getOutput().get(x).get(y);
			}
			BasicMLDataPair mlPair = new BasicMLDataPair(new BasicMLData(inpArray),new BasicMLData(outpArray));
			ml.add(mlPair);
			}
		
		MLDataSet trainingSet = new BasicMLDataSet(ml);

		// train the neural network
		final ResilientPropagation train = new ResilientPropagation(network,
				trainingSet);

		int epoch = 1;

		do {
			train.iteration();
			epoch++;
		} while (train.getError() > 0.001);

		// test the neural network
		System.out.println("Neural Network Results:");
		for (MLDataPair pair : trainingSet) {
			final MLData output = network.compute(pair.getInput());
			for (int in = 0; in < pair.getInput().getData().length; in++) {
				System.out.print(pair.getInput().getData(in) + " ");
			}
			System.out.print("|Output: ");
			for (int in = 0; in < output.getData().length; in++) {
				System.out.printf("%.2f ", output.getData(in));
			}
			System.out.println("");
		}

		Encog.getInstance().shutdown();

	}

}
