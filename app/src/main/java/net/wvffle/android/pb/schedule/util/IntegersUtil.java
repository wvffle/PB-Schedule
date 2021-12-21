package net.wvffle.android.pb.schedule.util;

public class IntegersUtil {
    public static int getIntFromEnd(String string) {
        for (int len = string.length() - 1; len >= 0; len--) {
            try {
                int result = java.lang.Integer.parseInt(string.substring(len));
                if (len == 0) return result;
            } catch (Exception e) {
                if (len == string.length() - 1) break;
                return java.lang.Integer.parseInt(string.substring(len + 1));
            }
        }

        return -1;
    }
}
