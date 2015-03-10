package com.globant.exercise.kata14.trigrams.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TrigramReducer extends Reducer<Text, Text, Text, Text> {

	Text valueOutput = new Text();
	StringBuilder builder = new StringBuilder();

	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		for (Text val : values) {
			builder.append(val.toString());
			builder.append("\t");
		}

		valueOutput.set(builder.toString().trim());
		builder.delete(0, builder.length());

		context.write(key, valueOutput);
	}
}
