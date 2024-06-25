package com.spring.jpastudy.chap01.repository;


// my batis : mapper 역할하는 레퍼지토리 클래스

import com.spring.jpastudy.chap01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// ⭐jpaRepository 에 CRUD가 기본적으로 만들어져있음 !
// jpaRepository를 상속한 후 첫번째 제너릭엔 엔터티 클래스 타입,
// 두번째 제너릭엔 PK 타입 넣기
public interface ProductRepository extends JpaRepository<Product, Long> {

}
