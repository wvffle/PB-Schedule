package net.wvffle.android.pb.schedule.util;

import static java.util.stream.Collectors.joining;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.stream.IntStream;

import io.sentry.Sentry;

public class StringsUtil {
    private static final RuleBasedCollator localRules = (RuleBasedCollator) Collator.getInstance();
    private static final String extraRules = IntStream.range(0, 100).mapToObj(String::valueOf).collect(joining(" < "));
    private static RuleBasedCollator collator;

    static {
        try {
            collator = new RuleBasedCollator(localRules.getRules() + " & " + extraRules);
        } catch (ParseException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }
    }

    public static int naturalCompareTo(String s1, String s2) {
        if (collator == null) return 0;
        return collator.compare(s1, s2);
    }
}
