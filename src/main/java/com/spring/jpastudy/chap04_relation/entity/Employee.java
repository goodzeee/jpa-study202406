package com.spring.jpastudy.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;

@Getter @Setter
@ToString(exclude = "department") //⭐⭐LAZY 사용시 연관된 엔터티 제외해주기 !
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder

@Entity
@Table(name = "tbl_emp")
public class Employee {  // employee 가 N다 !

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;  // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name;

    // ⭐⭐단방향 매핑 - 데이터베이스처럼 한쪽에 상대방의 PK를 FK로 갖는 형태
    // 1 : N(FK)
    //📌EAGER 로딩 : 연관된 데이터를 항상 JOIN을 통해 같이 가져옴
    // LAZY 로딩 : 해당 엔터티 데이터만 가져오고 필요한 경우 연관엔터티를 가져옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")   // FK 컬럼명
    private Department department;   // department 는 1 !

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                '}';
    }
}
