package com.cheoloh.app;

import com.cheoloh.app.domain.Lesson;
import com.cheoloh.app.domain.Student;
import com.cheoloh.app.repository.LessonRepository;
import com.cheoloh.app.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(StudentRepository studentRepository,
									LessonRepository lessonRepository){
		return (args) -> {
			studentRepository.save(Student.builder().name("박철오").email("1@1.com").build());
			studentRepository.save(Student.builder().name("홍길동").email("2@2.com").build());
			lessonRepository.save(Lesson.builder().name("웹프로그래밍").quota(15).build());
			lessonRepository.save(Lesson.builder().name("객체지향").quota(20).build());

		};
	}

}
