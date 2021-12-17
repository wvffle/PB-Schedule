package net.wvffle.android.pb.schedule.util;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {

    private final static Serializer instance = new Serializer();
    private Serializer() {}

    /**
     * A Serializer singleton instance getter
     * @return Serializer instance
     */
    public static Serializer getInstance() {
        return instance;
    }

    /**
     * Serializes a Serializable to a string
     * @param serializable Serializable object
     * @return Serialized string
     * @throws IOException an exception
     */
    public String toString (Serializable serializable) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.close();
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    /**
     * Deserializes a string into an object
     * @param serialized Serialized string
     * @return Deserialized object
     * @throws IOException an exception
     * @throws ClassNotFoundException an exception
     */
    public Object fromString (String serialized) throws IOException, ClassNotFoundException {
        byte [] data = Base64.decode(serialized, Base64.DEFAULT);
        ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        Object object  = inputStream.readObject();
        inputStream.close();
        return object;
    }
}
