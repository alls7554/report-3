package com.cheoloh.app.controller;

import com.cheoloh.app.domain.Course;
import com.cheoloh.app.domain.Lesson;
import com.cheoloh.app.domain.Student;
import com.cheoloh.app.repository.CourseRepository;
import com.cheoloh.app.repository.LessonRepository;
import com.cheoloh.app.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class CourseController {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public CourseController(StudentRepository studentRepository, CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping("/course")
    public String showCourseForm(Model model) {
        List<Student> students = studentRepository.findAll();
        List<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);
        return "courses/courseForm";
    }

    @PostMapping("/course")
    public String createCourse(@RequestParam("studentId") Long studentId,
                               @RequestParam("lessonId") Long lessonId) {
        Student student = studentRepository.findById(studentId).get();
        Lesson lesson = lessonRepository.findById(lessonId).get();
        Course course = Course.createCourse(student,lesson);
        Course savedCourse = courseRepository.save(course);

        return "redirect:/courses";
    }

    @GetMapping("/courses")
    public String courseList(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses/courseList";
    }

    @GetMapping("/courses/update/{id}")
    public String courseUpdateForm(@PathVariable("id") Long id, Model model){
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:"+id));
        model.addAttribute("courses", course);
        List<Student> students = studentRepository.findAll();
        List<Lesson> lessons = lessonRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("lessons", lessons);

        return "courses/courseUpdate";
    }

    @PostMapping("/courses/update/{id}")
    public String courseUpdate(@PathVariable("id") Long id,
                               @RequestParam("studentId") Long studentId,
                               @RequestParam("lessonId") Long lessonId){
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:"+id));

        Student student = studentRepository.findById(studentId).get();
        Lesson lesson = lessonRepository.findById(lessonId).get();
        course.setStudent(student);
        course.setLesson(lesson);

        courseRepository.save(course);

        return "redirect:/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, Model model){
        Course course = courseRepository.findById(id).get();

        course.setStudent(null);
        course.setLesson(null);

        courseRepository.delete(course);

        model.addAttribute("course", courseRepository.findAll());
        return "redirect:/courses";
    }
}
