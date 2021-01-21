package net.lht.redis;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

/**
 * TODO sum计算结果有误！
 * 
 * @author liuhongtian
 *
 */
public class MultiThreadLocal {

	public static void main(String[] args) {
		long sum = 0L;

		LocalTester tester = new LocalTester();

		LocalDateTime begin = LocalDateTime.now();

		ExecutorService pool = Executors.newFixedThreadPool(4);
		List<Future<Long>> threadResult = new ArrayList<>();

		LongStream.range(0L, 1000000L).forEach(n -> threadResult.add(pool.submit(() -> tester.test())));

		sum = threadResult.stream().map(n -> {
			try {
				return n.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				return 0L;
			}
		}).reduce((a, b) -> a + b).orElse(-1L);

		pool.shutdown();

		LocalDateTime finish = LocalDateTime.now();

		long time = finish.toEpochSecond(ZoneOffset.UTC) - begin.toEpochSecond(ZoneOffset.UTC);

		System.out.println("after " + time + " seconds, multy thread local finish at: " + tester.getResult()
				+ ", and sum = " + sum);
	}

}
