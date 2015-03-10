package com.globant.exercise.kata14.trigrams.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TrigramMapper extends Mapper<LongWritable, Text, Text, Text> {
	Text trigramKey = new Text();
	Text trigramValue = new Text();
	StringBuilder keyBuilder = new StringBuilder();
	StringBuilder valueBuilder = new StringBuilder();

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String line = value.toString();
		line = line.replaceAll("[^a-zA-Z0-9 ]", "").trim();

		String[] words = line.split(" ");

		for (int i = 0; i < words.length - 2; i++) {
			keyBuilder.append(words[i]);
			keyBuilder.append(" ");
			keyBuilder.append(words[i + 1]);

			trigramKey.set(keyBuilder.toString());
			keyBuilder.delete(0, keyBuilder.length());

			valueBuilder.append(words[i + 2]);
			trigramValue.set(valueBuilder.toString());
			context.write(trigramKey, trigramValue);

			valueBuilder.delete(0, valueBuilder.length());
		}
	}
}
