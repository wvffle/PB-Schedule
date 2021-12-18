package net.wvffle.android.pb.schedule.api;

import net.wvffle.android.pb.schedule.api.update.UpdateEntry;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;
import net.wvffle.android.pb.schedule.models.Update;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendService {
    @GET("updates")
    Call<List<UpdateEntry>> getUpdates();

    @GET("updates/{hash}")
    Call<Update> getUpdate(@Path("hash") String hash);

//    @GET("diff/{hash}")
//    Call<Update> getDiff(@Path("hash") String hash);

    @GET("rooms")
    Call<List<Room>> getRooms();

    @GET("rooms/{hash}")
    Call<Room> getRoom(@Path("hash") String hash);

    @GET("titles")
    Call<List<Title>> getTitles();

    @GET("titles/{hash}")
    Call<Title> getTitle(@Path("hash") String hash);

    @GET("degrees")
    Call<List<Degree>> getDegrees();

    @GET("degrees/{hash}")
    Call<Degree> getDegree(@Path("hash") String hash);

    @GET("subjects")
    Call<List<Subject>> getSubjects();

    @GET("subjects/{hash}")
    Call<Subject> getSubject(@Path("hash") String hash);

    @GET("specialities")
    Call<List<Speciality>> getSpecialities();

    @GET("specialities/{hash}")
    Call<Speciality> getSpeciality(@Path("hash") String hash);

    @GET("teachers")
    Call<List<Teacher>> getTeachers();

    @GET("teachers/{hash}")
    Call<Teacher> getTeacher(@Path("hash") String hash);

    @GET("schedules")
    Call<List<Schedule>> getSchedules();

    @GET("schedules/{hash}")
    Call<Schedule> getSchedule(@Path("hash") String hash);


}
