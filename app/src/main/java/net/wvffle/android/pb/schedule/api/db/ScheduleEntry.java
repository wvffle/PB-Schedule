package net.wvffle.android.pb.schedule.api.db;

import net.wvffle.android.pb.schedule.api.ScheduleParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public abstract class ScheduleEntry {
    static void skip (XmlPullParser parser) throws XmlPullParserException, IOException {
        ScheduleParser.skip(parser);
    }

    static String readText (XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        return result;
    }

}
