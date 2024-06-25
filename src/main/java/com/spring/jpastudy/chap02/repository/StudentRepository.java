package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JPA 자체에서 만들어진걸로 CRUD할 엔터티
public interface StudentRepository extends JpaRepository<Student, String> {

    //⭐ 쿼리메서드 : 메서드에 이름에 특별한 규칙을 적용하면 SQL이 규칙에 맞게 생성됨.
    // findBy-- 처럼 코드 짜는게 규칙임 !!
    List<Student> findByName(String name);

    List<Student> findByCityAndMajor(String city, String major);

    // where major like %major%
    List<Student> findByMajorContaining(String major);
    // where major like major%
    List<Student> findByMajorStartingWith(String major);
    // where major like %major
    List<Student> findByMajorEndingWith(String major);

    // where age <= ?
//    List<Student> findByAgeLessThanEqual(int age);

    // 📌📌native sql 사용하기 !
    @Query(value = "SELECT * FROM tbl_student Where stu_name = :snm or city = :c", nativeQuery = true)
    List<Student> getStudentByNameOrCity(@Param("snm") String name, @Param("c") String city); // ? 들어갈 파라미터 이름

    // ? 자리에 순서 넣어줘서 파라미터 넣어도 된다 !
    @Query(value = "SELECT * FROM tbl_student Where stu_name = ?1 or city = ?2", nativeQuery = true)
    List<Student> getStudentByNameOrCity2(String name, String city);
}
