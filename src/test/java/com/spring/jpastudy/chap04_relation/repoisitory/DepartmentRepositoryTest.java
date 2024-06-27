package com.spring.jpastudy.chap04_relation.repoisitory;

import com.spring.jpastudy.chap04_relation.entity.Department;
import com.spring.jpastudy.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;

//    @BeforeEach
    void bulkInsert() {

        for (int j = 1; j <= 10; j++) {
            Department dept = Department.builder()
                    .name("부서" + j)
                    .build();
            departmentRepository.save(dept);

            for (int i = 1; i <= 100 ; i++) {
                Employee employee = Employee.builder()
                        .name("사원" + i)
                        .department(dept)
                        .build();
                employeeRepository.save(employee);
            }
        }
    }

    @Test
    @DisplayName("특정 부서를 조회하면 해당 소속 부서원들이 함께 조회된다.")
    void findDeptTest() {
        //given
        Long id = 1L; // 1번 부서 조회
        //when
        Department department = departmentRepository.findById(id).orElseThrow();
        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("\n\n");

        // LAZY 로 당연히 조인되는걸 막아둬서 사원 조회하고 싶을 시 아래처럼 호출해오기 !!
        List<Employee> employees = department.getEmployees();
        System.out.println("employees = " + employees);
    }

    // 양방향 연관관계에서 리스트에 데이터 갱신시 주의사항
    @Test
    @DisplayName("양반향 연관관계에서 연관 데이터 수정")
    void changeTest() {
        //given
        // 3번 사원의 부서를 2번 부서에서 1번 부서로 수정

        // 3번 사원 정보 조회
        Employee employee = employeeRepository.findById(3L).orElseThrow();
        // 1번 부서 정보 조회
        Department department = departmentRepository.findById(2L).orElseThrow();

        //when
        /*
            ❌사원정보가 Employee 엔터티에서 수정되어도
            반대편 엔터티인 Department 에서는 리스트에 바로 반영되지 않는다.

            📌해결방안 : 데이터 수정시에 반대편 엔터티에도 같이 수정 해줘라 !
         */
        // 사원정보 수정 -> save 저장
//        employee.setDepartment(department);
//        //⭐⭐ 핵심 : 양방향에선 반대편 부서에도 수정된 사원정보 추가해주기
//        department.getEmployees().add(employee);

        employee.changeDepartment(department);

        employeeRepository.save(employee);

        //then
        // 수정하여 바뀐부서의 사원목록 조회
        List<Employee> employees = department.getEmployees();
        System.out.println("\n\n\n");
        employees.forEach(System.out::println);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("고아 객체 삭제하기")
    void orphanRemovalTest() {
        //given
        // 1번 부서 조회
        Department department = departmentRepository.findById(1L).orElseThrow();

        // 2번 사원 조회
        Employee employee = employeeRepository.findById(2L).orElseThrow();

        //when
        // 1번 부서 사원 목록 가져오기
        List<Employee> employeeList = department.getEmployees();

        // 2번 사원 조회
//        Employee employee = employeeList.get(1);

        // 부서목록에서 사원 삭제
//        employeeList.remove(employee);
//        employee.setDepartment(null);  // 반대편 사원에 부서도 set !

        department.removeEmployee(employee);

        // 갱신 반영
//        departmentRepository.save(department);

        //then
    }

    @Test
    @DisplayName("양방향 관계에서 리스트에 데이터를 추가하면 DB에도 INSERT 된다.")
    void cascadePersistTest() {
        //given
        // 2번 부서 조회
        Department department = departmentRepository.findById(2L).orElseThrow();

        // 새로운 사원 생성
        Employee employee = Employee.builder()
                .name("뽀로로")
                .build();
        //when
        department.addEmployee(employee);

        //then
    }

    @Test
    @DisplayName("부서가 사라지면 해당 사원들도 함께 사라진다.")
    void cascadeRemoveTest() {
        //given
        Department department = departmentRepository.findById(2L).orElseThrow();
        //when
        departmentRepository.delete(department);
        //then
    }

    @Test
    @DisplayName("N + 1 문제")
    void nPlusOneTest() {
        //given
        // 1개의 쿼리
        // 모든 부서 조회 - 10개
        List<Department> department = departmentRepository.findAll();
        //when
        // 부서 조회 1번 / 사원 조회 10번 (부서 갯수대로)
        for (Department dept : department) {
            List<Employee> employees = dept.getEmployees();
            System.out.println("사원목록 가져옴 : " + employees.get(0).getName());
        }
        //then
    }

    @Test
    @DisplayName("fetch join으로 n+1문제 해결하기")
    void fetchJoinTest() {
        //given

        //when
        List<Department> departments = departmentRepository.getFetchEmployee();

        for (Department dept : departments) {
            List<Employee> employees = dept.getEmployees();
            System.out.println("사원목록 가져옴: " + employees.get(0).getName());
        }
        //then
    }


}