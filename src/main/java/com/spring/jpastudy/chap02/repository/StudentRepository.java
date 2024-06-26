package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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


    /*
        -⭐⭐ JPQL

        SELECT 엔터티별칭
        FROM 엔터티클래스명 AS 엔터티별칭
        WHERE 별칭.필드명

        ex) native - SELECT * FROM tbl_student WHERE stu_name = ?
            JPQL   - SELECT st FROM Student AS st WHERE st.name = ?

     */
    //도시명으로 학생 1명을 단일 조회
    @Query(value = "SELECT st FROM Student st WHERE st.city = ?1", nativeQuery = false)  // false 생략 가능 -> JPQL 사용시 !
    Optional<Student> getByCityWithJPQL(String city);

    // 특정 이름이 포함된 학생 리스트 조회하기
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE %?1%")
    List<Student> searchByNameWithJPQL(String name);

    // JPQL로 갱신 처리하기
    @Modifying  // 삽입, 삭제, 수정이런 SQL문은 필수로 달기
    @Query("DELETE FROM Student s WHERE s.name = ?1 AND s.city = ?2")
    void deleteByNameAndCity(String name, String city);
}
