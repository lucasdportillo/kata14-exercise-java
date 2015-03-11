package com.globant.exercise.kata14.trigrams.app;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {

	@Test
	public void testApp() {
		String[] args = new String[] { "20",
				"/Users/lucas/Documents/work/input/",
				"/Users/lucas/Documents/work/app-output.txt" };

		try {
			App.main(args);
		} catch (Throwable t) {
			fail();
		}
	}
}
