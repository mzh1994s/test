package cn.mzhong.test.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTest {

    @Test
    public void atomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (int i = 0; i < 1000; i++) {
            new Thread(atomicInteger::getAndIncrement).start();
        }
        System.out.println(atomicInteger.get());
    }

    @Test
    public void lock() {
        class RunService {
            Lock lock = new ReentrantLock();

            public void run() {
                lock.lock();
                System.out.print(Thread.currentThread().getId() + ":");
                for (int i = 0; i < 10; i++) {
                    System.out.print("lock:" + i + ",");
                }
                System.out.println();
                lock.unlock();
            }
        }
        long start = System.nanoTime();
        class RunService2 {
            void run() {
                synchronized (this) {
                    System.out.print(Thread.currentThread().getId() + ":");
                    for (int i = 0; i < 10; i++) {
                        System.out.print("sync:" + i + ",");
                    }
                    System.out.println();
                }
            }
        }
        System.out.println("花费：" + (System.nanoTime() - start));
        start = System.nanoTime();
        RunService runService = new RunService();
        for (int i = 0; i < 10; i++) {
            new Thread(runService::run).start();
        }
        RunService2 runService2 = new RunService2();
        for (int i = 0; i < 10; i++) {
            new Thread(runService2::run).start();
        }
        System.out.println("花费：" + (System.nanoTime() - start));
        System.out.println();
    }
}
