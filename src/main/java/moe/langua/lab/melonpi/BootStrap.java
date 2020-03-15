package moe.langua.lab.melonpi;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class BootStrap {
    static double result = 0D;
    static AtomicInteger taskNumber = new AtomicInteger(0);
    static final int CORES = Runtime.getRuntime().availableProcessors();
    static final int ONE_HALF_CORES = (int) (CORES * 1.5);
    static final int DOUBLE_CORES = CORES * 2;
    static final int operationPerLoop = 0x800;
    static final int loopTime = 0x111;

    public static void main(String[] inputs) throws InterruptedException {
        String name = "";
        if (inputs.length != 0) {
            for(String x:inputs){
                name = name.concat(x+" ");
            }
        }
        System.out.println("Yay, I am going to calculate pi. It may takes a few minutes...");

        long timestamp = System.currentTimeMillis();
        startPiThreads(1);
        long singleResult = System.currentTimeMillis() - timestamp;
        resetResult(false);

        timestamp = System.currentTimeMillis();
        startPiThreads(CORES);
        long multiThreadResult = System.currentTimeMillis() - timestamp;
        resetResult(false);


        timestamp = System.currentTimeMillis();
        startPiThreads(ONE_HALF_CORES);
        long onePointFiveTimesThreadsResult = System.currentTimeMillis() - timestamp;
        resetResult(false);

        timestamp = System.currentTimeMillis();
        startPiThreads(DOUBLE_CORES);
        long twoTimesThreadsResult = System.currentTimeMillis() - timestamp;
        resetResult(true);

        Properties properties = System.getProperties();


        if(name.length()!=0) System.out.println("Device: "+ name);
        System.out.println("System: ");
        System.out.println("  Name: "+properties.getProperty("os.name"));
        System.out.println("  Version: "+properties.getProperty("os.version"));
        System.out.println("  Architecture: "+properties.getProperty("os.arch"));
        System.out.println("Java Runtime: ");
        System.out.println("  Java Version: "+properties.getProperty("java.version"));
        System.out.println("  JVM Version: "+properties.getProperty("java.vm.version"));
        System.out.println("  JVM Vendor: "+properties.getProperty("java.vm.vendor"));
        System.out.println("Performance: ");
        System.out.println("  Single thread: " + (double) singleResult / 1000 + "s");
        System.out.println("  Multi threads("+CORES+"): " + (double) multiThreadResult / 1000 + "s");
        System.out.println("  Multi threads("+ONE_HALF_CORES+"): " + (double) onePointFiveTimesThreadsResult / 1000 + "s");
        System.out.println("  Multi threads("+DOUBLE_CORES+"): " + (double) twoTimesThreadsResult / 1000 + "s");
    }

    private static void startPiThreads(int threadsNumber) throws InterruptedException {
        PiThread[] piThreads = new PiThread[threadsNumber];
        for (int i = 0; i < piThreads.length; i++) {
            piThreads[i] = new PiThread();
            piThreads[i].start();
        }
        for (PiThread x : piThreads) {
            x.join();
        }
    }

    private static void resetResult(boolean print){
        if(print) System.out.println("The pi you calculated is... "+result+"!");
        result = 0;
        taskNumber.set(0);
    }

    static synchronized void submit(double result) {
        BootStrap.result += result;
    }
}
