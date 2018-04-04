package net.lht.redis;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.lht.redis.service.Tester;

@SpringBootApplication
public class RedistestApplication implements CommandLineRunner {

	@Autowired
	Tester tester;

	public static void main(String[] args) {
		SpringApplication.run(RedistestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		long sum = 0L;

		LocalDateTime begin = LocalDateTime.now();

		tester.reset();

		ExecutorService pool = Executors.newFixedThreadPool(4);
		List<Future<Long>> threadResult = new ArrayList<>();

		IntStream.range(0, 1000000).forEach(n -> threadResult.add(pool.submit(() -> tester.test())));

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

		System.out.println(
				"after " + time + " seconds, multy thread finish at: " + tester.getResult() + ", and sum = " + sum);
	}
}
