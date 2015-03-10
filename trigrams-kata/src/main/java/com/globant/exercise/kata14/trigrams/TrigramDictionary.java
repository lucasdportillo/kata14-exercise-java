package com.globant.exercise.kata14.trigrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class holds the trigrams generated from input files
 * 
 * @author Lucas Portillo
 */
public class TrigramDictionary {

	public static Logger logger = LoggerFactory
			.getLogger(TrigramDictionary.class);

	static class TrigramValue {
		String text;
		boolean used;

		TrigramValue(String text) {
			this.text = text;
			used = false;
		}

		boolean isUsed() {
			return used;
		}

		void setUsed(boolean used) {
			this.used = used;
		}

		@Override
		public String toString() {
			return text + "(" + used + ")";
		}

		@Override
		public boolean equals(Object obj) {

			if (obj == null)
				return false;

			if (!(obj instanceof TrigramValue))
				return false;

			TrigramValue other = (TrigramValue) obj;

			if (!text.equals(other.text) || used != other.used) {
				return false;
			}

			return true;
		}
	}

	private Map<String, List<TrigramValue>> trigrams;

	public TrigramDictionary() {
		trigrams = new HashMap<String, List<TrigramValue>>();
	}

	/**
	 * Reads from the given input and parses the text to form trigrams
	 * 
	 * @param input
	 * @throws IOException
	 *             when closing the buffered reader
	 */
	public void generate(InputStreamReader input) {
		BufferedReader reader = new BufferedReader(input);

		try {
			String line = null;

			StringBuilder key;
			TrigramValue value;
			String[] words = null;

			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("[^a-zA-Z0-9 ]", "");
				line = line.trim();

				words = append(words, line.split(" "));

				for (int i = 0; i < words.length - 2; i++) {
					key = new StringBuilder(words[i].trim());
					key.append(" ");
					key.append(words[i + 1].trim());

					value = new TrigramValue(words[i + 2]);

					// skips problematic combination of words
					if (key.toString().split(" ").length != 2) {
						logger.trace("Skipping single word trigram key ({})",
								key.toString());
						continue;
					}

					List<TrigramValue> values = trigrams.get(key.toString());

					if (values == null) {
						values = new ArrayList<TrigramValue>();
					}

					// avoid repetition
					if (!values.contains(value)) {
						values.add(value);
					}

					trigrams.put(key.toString(), values);
				}

				// carry the last two words to process in the next iteration
				if (words.length > 4) {
					words = new String[] { words[words.length - 2],
							words[words.length - 1] };
				}
			}
		} catch (IOException ioe) {
			logger.error("IOException when generating trigrams from input", ioe);
		} finally {
			try {
				reader.close();
			} catch (IOException ioe) {
				logger.error("IOException when closing input reader", ioe);
			}
		}
	}

	protected String[] append(String[] a, String[] b) {
		if (a == null) {
			return b;
		}

		if (b == null) {
			return a;
		}

		String[] result = new String[a.length + b.length];

		int i;
		for (i = 0; i < a.length; i++) {
			result[i] = a[i];
		}

		for (int j = 0; j < b.length; j++, i++) {
			result[i] = b[j];
		}

		return result;
	}

	public Map<String, List<TrigramValue>> getTrigrams() {
		return trigrams;
	}

	// for testing
	protected void setTrigrams(Map<String, List<TrigramValue>> trigrams) {
		this.trigrams = trigrams;
	}
}
