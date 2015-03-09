package com.globant.exercise.kata14.trigrams;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class App {
	public static void main(String[] args) throws IOException {

		if (args.length != 3) {
			System.out.println("Usage:");
			System.out.println("	arg[0]: word count limit");
			System.out.println("	arg[1]: input directory path");
			System.out.println("	arg[2]: output directory path and filename");
			System.exit(0);
		}

		System.out.println("Args:");
		for (int i = 0; i < args.length; i++) {
			System.out.print("arg[" + i + "]: ");
			System.out.println(args[i]);
		}

		FileLoader fileLoader = new FileLoader();
		fileLoader.addInputDirectory(args[1]);
		TrigramDictionary trigramDictionary = fileLoader.getTrigramDictionary();
		SimpleTextGenerator simpleTextGenerator = new SimpleTextGenerator(
				trigramDictionary);

		simpleTextGenerator.setWordLimit(Integer.valueOf(args[0]));

		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				new FileOutputStream(new File(args[2])));
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		bufferedWriter.write(simpleTextGenerator.generate(true));

		fileLoader.close();
		bufferedWriter.close();
	}
}
