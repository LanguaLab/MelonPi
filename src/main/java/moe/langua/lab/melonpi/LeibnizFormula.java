package moe.langua.lab.melonpi;

public class LeibnizFormula {
    static double d(long n) {
        int flag = -1;
        for (int i = 0; i < n + 1; i++) {
            flag = flag * -1;
        }
        return (4.0D / (2.0D * n + 1.0D)) * flag;
    }
}
