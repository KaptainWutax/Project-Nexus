package kaptainwutax.nexus.utility;

public class Time {

    private static long LAST = 0;
    public static long DELTA = 0;

    public static void updateTime() {
        final long CURRENT = System.currentTimeMillis();

        if(LAST == 0) {
            LAST = CURRENT;
            return;
        }

        DELTA = CURRENT - LAST;
        LAST = CURRENT;
    }

}
