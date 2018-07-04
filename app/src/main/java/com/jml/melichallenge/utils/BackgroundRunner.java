package com.jml.melichallenge.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

/*
 * Simple singleton excecutor to run tasks in background
 * */

@Singleton
public class BackgroundRunner
{
	private ExecutorService executorService = Executors.newFixedThreadPool(4);

	/**
	 * Utility method to run blocks on a dedicated background thread, used for io/database work.
	 */
	public void run(Runnable runnable)
	{
		executorService.execute(runnable);
	}
}
