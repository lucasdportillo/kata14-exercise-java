package com.globant.exercise.kata14.trigrams.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globant.exercise.kata14.trigrams.FileLoader;
import com.globant.exercise.kata14.trigrams.SimpleTextGenerator;
import com.globant.exercise.kata14.trigrams.TrigramDictionary;

public class App {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.debug("kata14 app running");

		if (args.length != 3) {
			logger.debug("Usage:");
			logger.debug("	arg[0]: word count limit");
			logger.debug("	arg[1]: input directory path");
			logger.debug("	arg[2]: output directory path and filename");

			System.exit(0);
		}

		logger.debug("Args:");
		for (int i = 0; i < args.length; i++) {
			logger.debug("arg[{}]: {}", new Object[] { i, args[i] });
		}

		try {

			FileLoader fileLoader = new FileLoader();
			fileLoader.addInputDirectory(args[1]);
			TrigramDictionary trigramDictionary = fileLoader
					.getTrigramDictionary();
			SimpleTextGenerator simpleTextGenerator = new SimpleTextGenerator(
					trigramDictionary);

			simpleTextGenerator.setWordLimit(Integer.valueOf(args[0]));

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					new FileOutputStream(new File(args[2])));
			BufferedWriter bufferedWriter = new BufferedWriter(
					outputStreamWriter);
			bufferedWriter.write(simpleTextGenerator.generate(true));

			fileLoader.close();
			bufferedWriter.close();
		} catch (FileNotFoundException fnfe) {
			logger.error(
					"FileNotFoundException when processing trigrams. Message: {}",
					fnfe);
		} catch (IOException ioe) {
			logger.error("IOException when processing trigrams. Message: {}",
					ioe);
		} catch (Throwable t) {
			logger.error("Something wrong happend...", t);
		}
	}
}
