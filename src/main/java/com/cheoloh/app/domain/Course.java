package com.cheoloh.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    // 신청자 = 학생
    // 대상 = 과목
    // [학생(Student)] -- [수강(Course)] 1:N
    @ManyToOne
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    public void setStudent(Student student) {
        this.student = student;
        this.student.getCourses().add(this);
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        this.lesson.getCourses().add(this);
        this.lesson.setQuota(this.lesson.getQuota()-1);
    }

    public static Course createCourse(Student student, Lesson... lessons) {
        Course course = new Course();
        course.setStudent(student);
        Arrays.stream(lessons).forEach(course::setLesson);
        return course;
    }

}
