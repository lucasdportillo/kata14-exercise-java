package com.globant.exercise.kata14.trigrams;

import java.util.List;
import java.util.Random;

/**
 * This is a simple implementation for text generation which defaults up to 200
 * words (depending on the size of trigrams available). <br/>
 * Generates a consecutive sequence of words disregarding punctuation.<br/>
 * This implementation does not reuse trigrams
 * 
 * @author Lucas Portillo
 *
 */
public class SimpleTextGenerator {

	private TrigramDictionary trigramDictionary;
	private Random rand = new Random();
	private int wordLimit = 200;
	private int wordCount = 0;

	public SimpleTextGenerator(TrigramDictionary trigramDictionary) {
		this.trigramDictionary = trigramDictionary;
	}

	// for testing
	protected void setTrigramDictionary(TrigramDictionary newTrigramdDictionary) {
		this.trigramDictionary = newTrigramdDictionary;
	}

	public void setWordLimit(int wordLimit) {
		this.wordLimit = wordLimit;
	}

	public int randInt(int min, int max) {
		int randomNum = rand.nextInt(max - min) + min;
		return randomNum;
	}

	public String generate(boolean randomStart) {

		if (trigramDictionary == null
				|| trigramDictionary.getTrigrams().isEmpty()) {
			return null;
		}

		int randomSeedIndex = randomStart ? randInt(0, trigramDictionary
				.getTrigrams().size()) : 0;

		String[] keys = trigramDictionary.getTrigrams().keySet()
				.toArray(new String[] {});

		String key = keys[randomSeedIndex];
		String value = "";
		StringBuilder text = new StringBuilder(key);

		wordCount = 3;
		boolean keepGenerating = true;
		while (keepGenerating && wordCount <= wordLimit) {
			if (trigramDictionary.getTrigrams().containsKey(key)) {
				value = nextValueForKey(key);
				buildNextTextSegment(text, value);
				key = nextKey(key, value);
			} else {
				keepGenerating = false;
			}
			wordCount++;
		}

		return text.toString();
	}

	private String nextKey(String key, String value) {
		StringBuilder builder = new StringBuilder();
		builder.append(key.split(" ")[1]);
		builder.append(" ");
		builder.append(value);
		return builder.toString();
	}

	protected String nextValueForKey(String key) {
		String word = null;
		List<TrigramDictionary.TrigramValue> values = trigramDictionary
				.getTrigrams().get(key);

		// if there are more than one possible values for the current key, we
		// seek the first one unused
		if (values.size() > 1) {
			for (TrigramDictionary.TrigramValue val : values) {
				if (val.isUsed())
					continue;
				word = val.text;
				val.setUsed(true);
				break;
			}
		}

		// if all values are already used, return the first one available
		if (null == word) {
			word = values.get(0).text;
		}

		return word;
	}

	private void buildNextTextSegment(StringBuilder builder, String value) {
		builder.append(" ");
		builder.append(value);
	}
}
