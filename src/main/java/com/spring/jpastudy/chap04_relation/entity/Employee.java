package com.spring.jpastudy.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;

@Getter @Setter @ToString
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

    // 단방향 매핑 - 데이터베이스처럼 한쪽에 상대방의 PK를 FK로 갖는 형태
    // 1 : N(FK)
    @ManyToOne
    @JoinColumn(name = "dept_id")   // FK 컬럼명
    private Department department;   // department 는 1 !
}
