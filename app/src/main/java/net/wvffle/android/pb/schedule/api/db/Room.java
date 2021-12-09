package net.wvffle.android.pb.schedule.api.db;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Room extends ScheduleEntry {
    public static final String TAG_NAME = "tabela_sale";
    private final int id;
    private final String name;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Room fromParser (XmlPullParser parser) throws IOException, XmlPullParserException {
        int id = -1;
        String name = "";

        parser.require(XmlPullParser.START_TAG, null, TAG_NAME);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "ID":
                    parser.require(XmlPullParser.START_TAG, null, "ID");
                    id = Integer.parseInt(readText(parser));
                    parser.require(XmlPullParser.END_TAG, null, "ID");
                    break;

                case "NAZWA":
                    parser.require(XmlPullParser.START_TAG, null, "NAZWA");
                    name = readText(parser);
                    parser.require(XmlPullParser.END_TAG, null, "NAZWA");
                    break;

                default: skip(parser);
            }
        }


        return new Room(id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
