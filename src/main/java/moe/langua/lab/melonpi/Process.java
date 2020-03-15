package moe.langua.lab.melonpi;

public class Process implements Runnable {
    long start;
    long end;

    double result = 0.0D;

    public Process(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (long l = start; l < end; l++) {
            result = result + LeibnizFormula.d(l);
        }
        BootStrap.submit(result);
    }
}
