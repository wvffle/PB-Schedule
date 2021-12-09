package net.wvffle.android.pb.schedule.api.db;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Room extends ScheduleEntry {
    public static final String TAG_NAME = "tabela_sale";
    private final int id;
    private final String name;
    private final int updatedAt;

    public Room(int id, String name, int updatedAt) {
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public static Room fromParser (XmlPullParser parser) throws IOException, XmlPullParserException {
        int id = -1;
        String name = "";
        int updatedAt = -1;

        parser.require(XmlPullParser.START_TAG, null, TAG_NAME);
        updatedAt = Integer.parseInt(parser.getAttributeValue(null, "data-aktualizacji")) * 1000;

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


        return new Room(id, name, updatedAt);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }
}
