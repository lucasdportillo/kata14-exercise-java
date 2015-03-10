package com.globant.exercise.kata14.trigrams.mapreduce;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class TrigramMapperReducerTest {
	MapDriver<LongWritable, Text, Text, Text> mapDriver;
	ReduceDriver<Text, Text, Text, Text> reduceDriver;
	MapReduceDriver<LongWritable, Text, Text, Text, Text, Text> mapReduceDriver;

	@Before
	public void setUp() {
		TrigramMapper mapper = new TrigramMapper();
		TrigramReducer reducer = new TrigramReducer();
		mapDriver = MapDriver.newMapDriver(mapper);
		reduceDriver = ReduceDriver.newReduceDriver(reducer);
		mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
	}

	@Test
	public void testMapper() throws Exception {
		mapDriver.withInput(new LongWritable(), new Text(
				"This is a line to generate trigrams-test"));

		mapDriver.withOutput(new Text("This is"), new Text("a"));
		mapDriver.withOutput(new Text("is a"), new Text("line"));
		mapDriver.withOutput(new Text("a line"), new Text("to"));
		mapDriver.withOutput(new Text("line to"), new Text("generate"));
		mapDriver.withOutput(new Text("to generate"), new Text("trigramstest"));

		mapDriver.runTest();
	}

	@Test
	public void testReducer() throws Exception {
		List<Text> values = new ArrayList<Text>();
		values.add(new Text("a"));
		values.add(new Text("b"));
		values.add(new Text("C"));
		values.add(new Text("d"));
		reduceDriver.withInput(new Text("This is"), values);
		reduceDriver.withOutput(new Text("This is"), new Text("a\tb\tC\td"));

		values = new ArrayList<Text>();
		values.add(new Text("line"));
		reduceDriver.withInput(new Text("is a"), values);
		reduceDriver.withOutput(new Text("is a"), new Text("line"));

		values = new ArrayList<Text>();
		values.add(new Text("to"));
		reduceDriver.withInput(new Text("a line"), values);
		reduceDriver.withOutput(new Text("a line"), new Text("to"));

		values = new ArrayList<Text>();
		values.add(new Text("generate"));
		reduceDriver.withInput(new Text("line to"), values);
		reduceDriver.withOutput(new Text("line to"), new Text("generate"));

		values = new ArrayList<Text>();
		values.add(new Text("trigramstest"));
		reduceDriver.withInput(new Text("to generate"), values);
		reduceDriver.withOutput(new Text("to generate"), new Text(
				"trigramstest"));

		reduceDriver.runTest();
	}

}