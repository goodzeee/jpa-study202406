package com.spring.jpastudy.chap04_relation.repoisitory;

import com.spring.jpastudy.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {


}