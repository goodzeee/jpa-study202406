package com.spring.jpastudy.chap03_page;

import com.spring.jpastudy.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <= 147; i++) {
            Student s = Student.builder()
                    .name("김시골" + i)
                    .city("바닷가" + i)
                    .major("낚시하기" + i)
                    .build();
            studentPageRepository.save(s);
        }
    }

    @Test
    @DisplayName("기본적인 페이지 조회 테스트")
    void basicPageTest() {
        //given
        int pageNo = 6;
        int amount = 10;
        // 페이지 정보 객체를 생성 (Pageable) - PageRequest !
        // 여기서는 페이지번호가 zero-base임 : 1페이지는 0으로 취급
        Pageable pageInfo = PageRequest.of(pageNo - 1, amount);
        //when
        Page<Student> students = studentPageRepository.findAll(pageInfo);

        // 실질적인 페이징된 데이터 꺼내기
        List<Student> studentList = students.getContent();
        // 총 페이지 수
        int totalPages = students.getTotalPages();
        // 총 학생 수
        long count = students.getTotalElements();
        //then
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
        System.out.println("totalPages = " + totalPages);
        System.out.println("count = " + count);
    }

}