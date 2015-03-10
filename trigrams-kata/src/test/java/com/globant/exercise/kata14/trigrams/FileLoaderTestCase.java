package com.globant.exercise.kata14.trigrams;

import junit.framework.TestCase;

public class FileLoaderTestCase extends TestCase {

	// SUT
	public FileLoader fileLoader;

	protected void setUp() {
		String path = this.getClass().getResource("/").getPath();

		fileLoader = new FileLoader();
		fileLoader.addInputDirectory(path);
	}

	protected void tearDown() throws Exception {
		if (fileLoader != null) {
			fileLoader.close();
		}
	}

	public void testShouldLoadAllTextFilesFromDirectory() throws Exception {
		assertNotNull(fileLoader.list());
		assertTrue(fileLoader.list().length > 0);

		for (String fileName : fileLoader.list()) {
			assertTrue(fileName.endsWith(".txt"));
		}
	}

	public void testShouldCreateProperInputStreamFromDirectory()
			throws Exception {

		assertNotNull(fileLoader.getInputStreams());
	}

	public void testShouldGenerateTrigramFromInputStream() throws Exception {
		TrigramDictionary trigramDictionary = null;

		try {
			trigramDictionary = fileLoader.getTrigramDictionary();
		} catch (Throwable t) {
			fail();
		}
		assertNotNull(trigramDictionary);
	}
}
