package com.globant.exercise.kata14.trigrams.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TrigramDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner
				.run(new Configuration(), new TrigramDriver(), args);

		System.exit(res);
	}

	@Override
	public int run(String[] args) throws Exception {

		if (args.length != 2) {
			System.out
					.println("Usage: hadoop jar trigrams-kata-1.0-SNAPSHOT.jar [generic options] <input> <output>");

			return 1;
		}

		Job job = new Job(getConf(), "Trigram");

		job.setJarByClass(getClass());

		job.setMapperClass(TrigramMapper.class);
		job.setReducerClass(TrigramReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		boolean success = job.waitForCompletion(true);

		return success ? 0 : 1;
	}

}
