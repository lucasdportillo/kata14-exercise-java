package com.globant.exercise.kata14.trigrams;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.globant.exercise.kata14.trigrams.TrigramDictionary.TrigramValue;

import junit.framework.TestCase;

public class TextGeneratorTestCase extends TestCase {

	private SimpleTextGenerator textGenerator;
	private TrigramDictionary trigramDictionary;

	/**
	 * Trigrams used for testing: <br/>
	 * <q>This is a simple trigram for testing</q> <br/>
	 * <q>This is a short piece of text for testing</q>
	 */
	protected void setUp() {
		trigramDictionary = new TrigramDictionary();
		textGenerator = new SimpleTextGenerator(trigramDictionary);

		Map<String, List<TrigramValue>> trigrams;

		trigrams = new HashMap<String, List<TrigramValue>>();
		trigramDictionary.setTrigrams(trigrams);

		List<TrigramValue> values = new ArrayList<TrigramValue>();

		values.add(new TrigramValue("a"));
		trigrams.put("This is", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("simple"));
		values.add(new TrigramValue("short"));
		trigrams.put("is a", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("trigram"));
		trigrams.put("a simple", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("piece"));
		trigrams.put("a short", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("of"));
		trigrams.put("short piece", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("text"));
		trigrams.put("piece of", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("for"));
		trigrams.put("of text", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("testing"));
		trigrams.put("text for", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("for"));
		trigrams.put("simple trigram", values);

		values = new ArrayList<TrigramValue>();
		values.add(new TrigramValue("testing"));
		trigrams.put("trigram for", values);
	}

	public void testShouldGenerateNoTextWithEmptyTrigram() throws Exception {
		trigramDictionary.getTrigrams().clear();
		String text = textGenerator.generate(true);
		assertNull(
				"Text generated should be null when using empty trigram dictionary",
				text);
	}

	public void testShouldGenerateText() throws Exception {
		String text = textGenerator.generate(false);

		assertNotNull("Generated text should not be null", text);
		assertFalse("Generated text should not be empty", "".equals(text));

		System.out.println("Generated text: " + text);
	}

	public void testShouldGenerateRandomText() throws Exception {
		String text = textGenerator.generate(true);

		assertNotNull("Generated text should not be null", text);
		assertFalse("Generated text should not be empty", "".equals(text));

		System.out.println("Generated text: " + text);
	}

	public void testShouldGenerateAtLeastXAmountOfWords() throws Exception {
		assertNotNull("missing file: sample2",
				this.getClass().getResource("/sample2.txt"));

		File file = new File(this.getClass().getResource("/sample2.txt")
				.toURI());

		InputStreamReader inputStreamReader = new InputStreamReader(
				new FileInputStream(file));

		trigramDictionary.generate(inputStreamReader);

		inputStreamReader.close();

		textGenerator.setTrigramDictionary(trigramDictionary);
		textGenerator.setWordLimit(200);

		String text = textGenerator.generate(true);

		assertNotNull(text);
		assertTrue("Generated text is greaten than specified amount of words",
				text.split(" ").length <= 200);

		System.out.println("Generated text (from file): " + text);
	}
}
