package com.spring.jpastudy.chap04_relation.repoisitory;

import com.spring.jpastudy.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // JPQL에만 있는 JOIN FETCH
    @Query("SELECT d FROM Department d JOIN FETCH d.employees")
    List<Department> getFetchEmployee();


}
