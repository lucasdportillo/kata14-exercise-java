package com.globant.exercise.kata14.trigrams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class receives a path where input files are located and creates the
 * input stream for every file
 * 
 * @author Lucas Portillo
 *
 */
public class FileLoader {

	private List<String> dirs;
	private List<InputStreamReader> inputStreamList;

	public FileLoader() {
		dirs = new ArrayList<String>();
	}

	public void addInputDirectory(String path) {
		if (!dirs.contains(path)) {
			dirs.add(path);
		}
	}

	public String[] list() {
		List<String> allFiles = new ArrayList<String>();

		for (String dir : dirs) {
			File fileDir = new File(dir);

			String[] fileNamesInThisDirectory = fileDir
					.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".txt");
						}
					});

			for (String fileName : fileNamesInThisDirectory) {
				allFiles.add(fileName);
			}
		}

		return allFiles.toArray(new String[] {});
	}

	public InputStreamReader[] getInputStreams() throws FileNotFoundException {
		inputStreamList = new ArrayList<InputStreamReader>();

		for (String dir : dirs) {
			File fileDir = new File(dir);

			File[] fileNamesInThisDirectory = fileDir
					.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".txt");
						}
					});

			for (File fileName : fileNamesInThisDirectory) {
				inputStreamList.add(new InputStreamReader(new FileInputStream(
						fileName)));
			}
		}

		return inputStreamList.toArray(new InputStreamReader[] {});
	}

	public TrigramDictionary getTrigramDictionary() throws IOException {

		TrigramDictionary trigramDictionary = new TrigramDictionary();

		for (InputStreamReader in : getInputStreams()) {
			trigramDictionary.generate(in);
		}

		return trigramDictionary;
	}

	public void close() throws IOException {
		if (inputStreamList != null && !inputStreamList.isEmpty()) {
			for (InputStreamReader in : inputStreamList) {
				in.close();
			}
		}

	}

}
