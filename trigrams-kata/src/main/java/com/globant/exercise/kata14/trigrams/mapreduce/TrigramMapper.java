package com.globant.exercise.kata14.trigrams.mapreduce;

import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TrigramMapper extends Mapper<LongWritable, Text, Text, Text> {
	Text trigramKey = new Text();
	Text trigramValue = new Text();
	StringBuilder builder = new StringBuilder();
	String[] words;

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String line = value.toString();
		line = line.replaceAll("[^a-zA-Z0-9 ]", "").trim();

		words = (String[]) ArrayUtils.addAll(words, line.split(" "));

		for (int i = 0; i < words.length - 2; i++) {

			builder.append(words[i]);
			builder.append(" ");
			builder.append(words[i + 1]);

			trigramKey.set(builder.toString());
			trigramValue.set(words[i + 2]);
			context.write(trigramKey, trigramValue);

			builder.delete(0, builder.length());
		}

		words = new String[] { words[words.length - 2], words[words.length - 1] };
	}
}
