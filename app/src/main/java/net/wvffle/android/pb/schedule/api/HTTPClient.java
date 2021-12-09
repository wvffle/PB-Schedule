package net.wvffle.android.pb.schedule.api;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HTTPClient {
    private static OkHttpClient client;
    private static OkHttpClient buildClient () {
        if (client != null) {
            return client;
        }

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS);

        return (client = clientBuilder.build());
    }

    public static String get (String url) {
        try {
            Request request = new Request.Builder()
                    .url(new URL(url))
                    .build();

            return Objects.requireNonNull(buildClient().newCall(request).execute().body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
