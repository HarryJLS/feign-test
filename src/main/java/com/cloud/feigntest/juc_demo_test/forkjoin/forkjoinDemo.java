package com.cloud.feigntest.juc_demo_test.forkjoin;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {

    private static final  Integer VALUE = 10;
    private int begin;
    private int end;
    private int result;

    public MyTask(int begin,int end) {
        this.begin = begin;
        this.end = end;
    }

    // 拆分合并的过程
    @Override
    protected Integer compute() {
        // 判断相加的两个数是否大于10
        if((end - begin) <= VALUE) {
            // 相加操作
            for (int i = begin; i <= end ; i++) {
                result = result + i;
            }
        } else {
            // 继续进行拆分
            int middle = (begin + end)/2;
            // 拆分左边
            MyTask task01 = new MyTask(begin, middle);
            // 拆分右边
            MyTask task02 = new MyTask(middle+1, end);
            // 拆分
            task01.fork();
            task02.fork();
            // 合并
            result = task01.join() + task02.join();
        }


        return result;
    }
}
public class forkjoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0, 100);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);

        System.out.println(submit.get());

        forkJoinPool.shutdown();
    }

}
