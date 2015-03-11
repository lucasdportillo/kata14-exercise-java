package com.globant.exercise.kata14.trigrams;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileLoaderTestCase {

	// SUT
	public FileLoader fileLoader;

	@Before
	public void setUp() {
		String path = this.getClass().getResource("/").getPath();

		fileLoader = new FileLoader();
		fileLoader.addInputDirectory(path);
	}

	@After
	public void tearDown() throws Exception {
		if (fileLoader != null) {
			fileLoader.close();
		}
	}

	@Test
	public void testShouldLoadAllTextFilesFromDirectory() throws Exception {
		assertNotNull(fileLoader.list());
		assertTrue(fileLoader.list().length > 0);

		for (String fileName : fileLoader.list()) {
			assertTrue(fileName.endsWith(".txt"));
		}
	}

	@Test
	public void testShouldCreateProperInputStreamFromDirectory()
			throws Exception {

		assertNotNull(fileLoader.getInputStreams());
	}

	@Test
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
