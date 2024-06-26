package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData() {
        Student s1 = Student.builder()
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();

        Student s2 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();

        Student s3 = Student.builder()
                .name("폼푸")
                .city("제주도")
                .major("화학공학")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 모든 정보를 조호한다.")
    void dummyTest() {
        //given
        String name = "춘식이";
        //when
        List<Student> byName = studentRepository.findByName(name);
        //then
        assertEquals(1, byName.size());
        System.out.println();
        System.out.println("students.get(0) : " + byName.get(0));
    }
    
    @Test
    @DisplayName("도시이름과 전공으로 학생을 조회한다.")
    void findByCityAndMajorTest() {
        //given
        String  city = "청양군";
        String major = "경제학";
        //when
        List<Student> byCityAndMajor = studentRepository.findByCityAndMajor(city, major);
        //then
        System.out.println();
        System.out.println("students.get(0) : " + byCityAndMajor.get(0));
    }

    @Test
    @DisplayName("전공이 공학으로 끝나는 학생들 조회")
    void findByMajorContainingTest() {
        //given
        String major = "공학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(major);
        //then
        System.out.println();
        students.forEach(System.out::println);
    }

    @Test
    @DisplayName("도시 또는 이름으로 학생을 조회")
    void getStudentByNameOrCityTest() {
        //given
        String name = "폼푸";
        String city = "청양군";
        //when
        List<Student> students = studentRepository.getStudentByNameOrCity2(name, city);
        //then
        System.out.println();
        students.forEach(System.out::println);
    }
    
    @Test
    @DisplayName("JPQL로 학생 조회하기")
    void jpqlTest() {
        //given
        String city = "제주도";
        //when
        Student student = studentRepository.getByCityWithJPQL(city)
                .orElseThrow(() -> new RuntimeException("학생이 없음 !"));  // 학생이 조회가 안되면 예외를 발생시켜라 
        //then
        assertNotNull(student);
        System.out.println("student + \"\\n\\n\" = " + student + "\n\n");
//        assertThrows(RuntimeException.class, () -> new RuntimeException()); // 에러가 발생시키는 테스트할 때 throws
    }

    @Test
    @DisplayName("JPQL로 이름이 포함된 학생목록 조회하기")
    void jpqlTest2() {
        //given
        String containingName = "폼";
        //when
        List<Student> students = studentRepository.searchByNameWithJPQL(containingName);
        //then
        System.out.println("\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("JPQL로 삭제하기")
    void deleteJPQLTest() {
        //given
        String name = "쿠로미";
        String city = "청양군";
        //when
        studentRepository.deleteByNameAndCity(name, city);
        //then
        assertEquals(0, studentRepository.findByName(name).size());
    }


}