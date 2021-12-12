package net.wvffle.android.pb.schedule.api;

import android.util.Xml;


import net.wvffle.android.pb.schedule.api.db.Room;
import net.wvffle.android.pb.schedule.api.db.ScheduleEntry;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ScheduleParser {
    private final XmlPullParser parser;

    public ScheduleParser(String xml) {
        XmlPullParser parser = Xml.newPullParser();
        this.parser = parser;

        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(IOUtils.toInputStream(xml, Charset.defaultCharset()), null);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "conversations");
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

    }

    public List<ScheduleEntry> parse () {
        List<ScheduleEntry> entries = new ArrayList<>();

        try {
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }


                String name = parser.getName();
                switch (name) {
                    default: skip(parser);
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

    public static void skip (XmlPullParser parser) throws XmlPullParserException, IOException {
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;

                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }

    }
}
