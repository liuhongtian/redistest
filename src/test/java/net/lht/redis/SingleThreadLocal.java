package net.lht.redis;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.LongStream;

public class SingleThreadLocal {

	public static void main(String[] args) {
		long sum = 0L;

		LocalTester tester = new LocalTester();

		LocalDateTime begin = LocalDateTime.now();

		tester.reset();

		sum = LongStream.range(0L, 1000000L).map(n -> tester.test()).reduce((a, b) -> a + b).orElse(-1);

		LocalDateTime finish = LocalDateTime.now();

		long time = finish.toEpochSecond(ZoneOffset.UTC) - begin.toEpochSecond(ZoneOffset.UTC);

		System.out.println(
				"after " + time + " seconds, single thread local finish at: " + tester.getResult() + ", and sum = " + sum);
	}

}
