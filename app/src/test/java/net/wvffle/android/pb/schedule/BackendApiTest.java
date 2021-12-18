package net.wvffle.android.pb.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.BackendService;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Room;
import net.wvffle.android.pb.schedule.models.Schedule;
import net.wvffle.android.pb.schedule.models.Speciality;
import net.wvffle.android.pb.schedule.models.Subject;
import net.wvffle.android.pb.schedule.models.Teacher;
import net.wvffle.android.pb.schedule.models.Title;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class BackendApiTest {
    BackendService service = BackendApi.getService();

    @Test
    public void testTeachersRetrofit() throws IOException {
        Call<List<Teacher>> teachersCall = service.getTeachers();
        List<Teacher> teachers = teachersCall.execute().body();

        assertNotNull(teachers);
        assertTrue(teachers.size() > 0);

        for (Teacher teacher : teachers) {
            assertNotNull(teacher);
        }
    }

    @Test
    public void testTeacherRetrofit() throws IOException {
        Call<List<Teacher>> teachersCall = service.getTeachers();
        List<Teacher> teachers = teachersCall.execute().body();
        assertNotNull(teachers);
        assertTrue(teachers.size() > 0);

        Call<Teacher> teacherCall = service.getTeacher(teachers.get(0).getHash());
        Teacher teacher = teacherCall.execute().body();

        assertNotNull(teacher);
        assertEquals(teachers.get(0).id, teacher.id);
        assertEquals(teachers.get(0).getHash(), teacher.getHash());
        assertEquals(teachers.get(0).getInitials(), teacher.getInitials());
        assertEquals(teachers.get(0).getFirstName(), teacher.getFirstName());
        assertEquals(teachers.get(0).getSurname(), teacher.getSurname());

        // TODO: Add ObjectBox relations the tests
        // assertEquals(teachers.get(0).getTitle(), teacher.getTitle());
    }

    @Test
    public void testSubjectsRetrofit() throws IOException {
        Call<List<Subject>> subjectsCall = service.getSubjects();
        List<Subject> subjects = subjectsCall.execute().body();

        assertNotNull(subjects);
        assertTrue(subjects.size() > 0);

        for (Subject subject : subjects) {
            assertNotNull(subject);
        }
    }

    @Test
    public void testSubjectRetrofit() throws IOException {
        Call<List<Subject>> subjectsCall = service.getSubjects();
        List<Subject> subjects = subjectsCall.execute().body();
        assertNotNull(subjects);
        assertTrue(subjects.size() > 0);

        Call<Subject> subjectCall = service.getSubject(subjects.get(0).getHash());
        Subject subject = subjectCall.execute().body();

        assertNotNull(subject);
        assertEquals(subjects.get(0).id, subject.id);
        assertEquals(subjects.get(0).getHash(), subject.getHash());
        assertEquals(subjects.get(0).getName(), subject.getName());
        assertEquals(subjects.get(0).getShortName(), subject.getShortName());

    }

    @Test
    public void testSpecialitiesRetrofit() throws IOException {
        Call<List<Speciality>> specialitiesCall = service.getSpecialities();
        List<Speciality> specialities = specialitiesCall.execute().body();

        assertNotNull(specialities);
        assertTrue(specialities.size() > 0);

        for (Speciality speciality : specialities) {
            assertNotNull(speciality);
        }
    }

    @Test
    public void testSpecialityRetrofit() throws IOException {
        Call<List<Speciality>> specialitiesCall = service.getSpecialities();
        List<Speciality> specialities = specialitiesCall.execute().body();
        assertNotNull(specialities);
        assertTrue(specialities.size() > 0);

        Call<Speciality> subjectCall = service.getSpeciality(specialities.get(0).getHash());
        Speciality speciality = subjectCall.execute().body();

        assertNotNull(speciality);
        assertEquals(specialities.get(0).id, speciality.id);
        assertEquals(specialities.get(0).getHash(), speciality.getHash());
        assertEquals(specialities.get(0).getName(), speciality.getName());

    }

    @Test
    public void testDegreesRetrofit() throws IOException {
        Call<List<Degree>> degreesCall = service.getDegrees();
        List<Degree> degrees = degreesCall.execute().body();

        assertNotNull(degrees);
        assertTrue(degrees.size() > 0);

        for (Degree degree : degrees) {
            assertNotNull(degree);
        }
    }

    @Test
    public void testDegreeRetrofit() throws IOException {
        Call<List<Degree>> degreesCall = service.getDegrees();
        List<Degree> degrees = degreesCall.execute().body();
        assertNotNull(degrees);
        assertTrue(degrees.size() > 0);

        Call<Degree> degreeCall = service.getDegree(degrees.get(0).getHash());
        Degree degree = degreeCall.execute().body();

        assertNotNull(degree);
        assertEquals(degrees.get(0).id, degree.id);
        assertEquals(degrees.get(0).getHash(), degree.getHash());
        assertEquals(degrees.get(0).getName(), degree.getName());

    }

    @Test
    public void testRoomsRetrofit() throws IOException {
        Call<List<Room>> roomsCall = service.getRooms();
        List<Room> rooms = roomsCall.execute().body();

        assertNotNull(rooms);
        assertTrue(rooms.size() > 0);

        for (Room room : rooms) {
            assertNotNull(room);
        }
    }

    @Test
    public void testRoomRetrofit() throws IOException {
        Call<List<Room>> roomsCall = service.getRooms();
        List<Room> rooms = roomsCall.execute().body();
        assertNotNull(rooms);
        assertTrue(rooms.size() > 0);

        Call<Room> roomCall = service.getRoom(rooms.get(0).getHash());
        Room room = roomCall.execute().body();

        assertNotNull(room);
        assertEquals(rooms.get(0).id, room.id);
        assertEquals(rooms.get(0).getHash(), room.getHash());
        assertEquals(rooms.get(0).getName(), room.getName());

    }

    @Test
    public void testTitlesRetrofit() throws IOException {
        Call<List<Title>> titlesCall = service.getTitles();
        List<Title> titles = titlesCall.execute().body();

        assertNotNull(titles);
        assertTrue(titles.size() > 0);

        for (Title title : titles) {
            assertNotNull(title);
        }
    }

    @Test
    public void testTitleRetrofit() throws IOException {
        Call<List<Title>> titlesCall = service.getTitles();
        List<Title> titles = titlesCall.execute().body();
        assertNotNull(titles);
        assertTrue(titles.size() > 0);

        Call<Title> roomCall = service.getTitle(titles.get(0).getHash());
        Title title = roomCall.execute().body();

        assertNotNull(title);
        assertEquals(titles.get(0).id, title.id);
        assertEquals(titles.get(0).getHash(), title.getHash());
        assertEquals(titles.get(0).getName(), title.getName());

    }

    @Test
    public void testSchedulesRetrofit() throws IOException {
        Call<List<Schedule>> schedulesCall = service.getSchedules();
        List<Schedule> schedules = schedulesCall.execute().body();

        assertNotNull(schedules);
        assertTrue(schedules.size() > 0);

        for (Schedule schedule : schedules) {
            assertNotNull(schedule);
        }
    }

    @Test
    public void testScheduleRetrofit() throws IOException {
        Call<List<Schedule>> roomsCall = service.getSchedules();
        List<Schedule> schedules = roomsCall.execute().body();
        assertNotNull(schedules);
        assertTrue(schedules.size() > 0);

        Call<Schedule> scheduleCall = service.getSchedule(schedules.get(0).getHash());
        Schedule schedule = scheduleCall.execute().body();

        assertNotNull(schedule);
        assertEquals(schedules.get(0).getDay(), schedule.getDay());
        assertEquals(schedules.get(0).getHour(), schedule.getHour());
        assertEquals(schedules.get(0).getGroup(), schedule.getGroup());
        assertEquals(schedules.get(0).getSemester(), schedule.getSemester());
        assertEquals(schedules.get(0).getIntervals(), schedule.getIntervals());
        assertEquals(schedules.get(0).getWeekFlags(), schedule.getWeekFlags());

    }
}