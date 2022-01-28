package net.wvffle.android.pb.schedule.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.wvffle.android.pb.schedule.api.deserializers.DegreeDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.RoomDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.ScheduleDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.SpecialityDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.SubjectDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.TeacherDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.TitleDeserializer;
import net.wvffle.android.pb.schedule.api.deserializers.UpdateDeserializer;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;
import net.wvffle.android.pb.schedule.models.Update;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendApi {
    private static final String HOST = "https://schedule.wvffle.net";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Teacher.class, new TeacherDeserializer())
            .registerTypeAdapter(Degree.class, new DegreeDeserializer())
            .registerTypeAdapter(Subject.class, new SubjectDeserializer())
            .registerTypeAdapter(Room.class, new RoomDeserializer())
            .registerTypeAdapter(Schedule.class, new ScheduleDeserializer())
            .registerTypeAdapter(Speciality.class, new SpecialityDeserializer())
            .registerTypeAdapter(Title.class, new TitleDeserializer())
            .registerTypeAdapter(Update.class, new UpdateDeserializer())
            .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BackendApi.HOST)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                    new OkHttpClient.Builder()
                            // TODO [#65]: Fix too long timeout when no internet
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .callTimeout(5, TimeUnit.SECONDS)
                            .build()
            )
            .build();

    private static final BackendService service = retrofit.create(BackendService.class);

    /**
     * Return a {@link BackendService}
     * @return {@link BackendService} instance
     */
    public static BackendService getService() {
        return service;
    }
}
