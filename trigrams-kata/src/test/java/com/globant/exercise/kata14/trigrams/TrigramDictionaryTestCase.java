package com.globant.exercise.kata14.trigrams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.globant.exercise.kata14.trigrams.TrigramDictionary.TrigramValue;

public class TrigramDictionaryTestCase extends TestCase {

	// SUT
	private TrigramDictionary trigramDictionary;

	private InputStreamReader stringInput;
	private InputStreamReader fileInput1;
	private InputStreamReader fileInput2;
	private ByteArrayInputStream byteArrayInputStream;

	protected void setUp() throws Exception {
		trigramDictionary = new TrigramDictionary();

		String line = "This is a line to generate trigrams-test";
		byteArrayInputStream = new ByteArrayInputStream(line.getBytes());
		stringInput = new InputStreamReader(byteArrayInputStream);

		assertNotNull("missing file: sample",
				this.getClass().getResource("/sample.txt"));

		assertNotNull("missing file: sample2.txt",
				this.getClass().getResource("/sample2.txt"));

		File file1 = new File(this.getClass().getResource("/sample.txt").toURI());
		fileInput1 = new InputStreamReader(new FileInputStream(file1));

		File file2 = new File(this.getClass().getResource("/sample2.txt")
				.toURI());
		fileInput2 = new InputStreamReader(new FileInputStream(file2));
	}

	protected void tearDown() throws Exception {
		byteArrayInputStream.close();
		stringInput.close();
		fileInput1.close();
		fileInput2.close();
	}

	public void testShouldGenerateTrigrams() throws Exception {
		trigramDictionary.generate(stringInput);

		Map<String, List<TrigramValue>> trigrams;

		trigrams = trigramDictionary.getTrigrams();

		assertNotNull(trigrams);
		assertFalse(trigrams.isEmpty());
		List<TrigramValue> values;

		assertTrue(trigrams.containsKey("This is"));
		values = trigrams.get("This is");
		assertNotNull(values);
		assertEquals(1, values.size());
		assertEquals("a", values.get(0).text);

		assertTrue(trigrams.containsKey("is a"));
		values = trigrams.get("is a");
		assertNotNull(values);
		assertEquals(1, values.size());
		assertEquals("line", values.get(0).text);

		assertTrue(trigrams.containsKey("a line"));
		values = trigrams.get("a line");
		assertNotNull(values);
		assertEquals(1, values.size());
		assertEquals("to", values.get(0).text);

		assertTrue(trigrams.containsKey("line to"));
		values = trigrams.get("line to");
		assertNotNull(values);
		assertEquals(1, values.size());
		assertEquals("generate", values.get(0).text);

		assertTrue(trigrams.containsKey("to generate"));
		values = trigrams.get("to generate");
		assertNotNull(values);
		assertEquals(1, values.size());
		assertEquals("trigramstest", values.get(0).text);
	}

	/**
	 * Locates a file from file system, and verifies the generation of the
	 * trigrams
	 * 
	 * @throws Exception
	 */
	public void testShouldGenerateTrigramsFromInputFile() throws Exception {
		trigramDictionary.generate(fileInput1);

		Map<String, List<TrigramValue>> trigrams;

		trigrams = trigramDictionary.getTrigrams();

		assertNotNull(trigrams);
		assertFalse(trigrams.isEmpty());
	}

	/**
	 * Locates two files from file system, and verifies the generation of the
	 * trigrams
	 * 
	 * @throws Exception
	 */
	public void testShouldGenerateTrigramsFromTwoInputFiles() throws Exception {
		Map<String, List<TrigramValue>> trigrams;

		trigramDictionary.generate(fileInput1);

		trigrams = trigramDictionary.getTrigrams();
		assertNotNull(trigrams);
		assertFalse(trigrams.isEmpty());
		int trigramsSizeWithOneFileLoaded = trigrams.size();

		trigramDictionary.generate(fileInput2);

		trigrams = trigramDictionary.getTrigrams();
		assertNotNull(trigrams);
		assertFalse(trigrams.isEmpty());
		int trigramsSizeWithTwoFilesLoaded = trigrams.size();

		assertTrue(trigramsSizeWithOneFileLoaded < trigramsSizeWithTwoFilesLoaded);
	}

	/**
	 * Verifies all keys in the dictionary are made of two words
	 * 
	 * @throws Exception
	 */
	public void testShouldTwoWordsTrigramKey() throws Exception {
		trigramDictionary.generate(fileInput2);

		Map<String, List<TrigramValue>> trigrams;

		trigrams = trigramDictionary.getTrigrams();

		assertNotNull(trigrams);
		assertFalse(trigrams.isEmpty());

		Set<String> keys = trigrams.keySet();
		for (String key : keys) {
			assertNotNull(key);
			assertNotNull(key.split(" "));
			assertEquals("key: " + key + ", not a 2 element key", 2,
					key.split(" ").length);
		}
	}

	/**
	 * Locates a file from file system, and verifies the generation of the
	 * trigrams
	 * 
	 * @throws Exception
	 */
	public void testShouldGenerateTrigramsFromInputFile2() throws Exception {
		trigramDictionary.generate(fileInput2);

		Map<String, List<TrigramValue>> trigrams;

		trigrams = trigramDictionary.getTrigrams();

		assertNotNull(trigrams);
		assertFalse(trigrams.isEmpty());

		// This is a known key, that is present several times in the file
		assertTrue("Should contain this trigram key",
				trigrams.containsKey("he went"));
		List<TrigramValue> values = trigrams.get("he went");
		assertNotNull(values);
		assertTrue(values.size() > 1);
		System.out.println(values);
	}

	public void testShouldAppendTwoStrings1() throws Exception {
		String[] a = new String[] { "1", "2" };
		String[] b = new String[] { "Three", "Four" };

		String[] appended = trigramDictionary.append(a, b);

		assertNotNull(appended);
		assertTrue(appended.length == 4);

		assertEquals(a[0], appended[0]);
		assertEquals(a[1], appended[1]);
		assertEquals(b[0], appended[2]);
		assertEquals(b[1], appended[3]);
	}

	public void testShouldAppendTwoStrings2() throws Exception {
		String[] a = null;
		String[] b = new String[] { "Three", "Four" };

		String[] appended = trigramDictionary.append(a, b);

		assertNotNull(appended);
		assertTrue(appended.length == 2);

		assertEquals(b[0], appended[0]);
		assertEquals(b[1], appended[1]);
	}

	public void testShouldAppendTwoStrings3() throws Exception {
		String[] a = new String[] { "One", "2" };
		String[] b = null;

		String[] appended = trigramDictionary.append(a, b);

		assertNotNull(appended);
		assertTrue(appended.length == 2);

		assertEquals(a[0], appended[0]);
		assertEquals(a[1], appended[1]);
	}

}
