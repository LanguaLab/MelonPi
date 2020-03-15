package moe.langua.lab.melonpi;

public class PiThread extends Thread {
    @Override
    public void run() {
        while (BootStrap.taskNumber.get() < BootStrap.loopTime) {
            int start = BootStrap.taskNumber.getAndAdd(1)*BootStrap.operationPerLoop;
            int end = start+BootStrap.operationPerLoop;
            Process process = new Process(start, end);
            process.run();
        }
    }
}
