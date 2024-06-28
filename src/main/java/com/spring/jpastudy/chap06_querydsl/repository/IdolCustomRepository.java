package com.spring.jpastudy.chap06_querydsl.repository;


import com.spring.jpastudy.chap06_querydsl.entity.Idol;

import java.util.List;

// 나만의 레포지토리 만들기 -> IdolRepository 에 추가로 상속 받기
public interface IdolCustomRepository {

    // 이름으로 오름차해서 전체 조회
    List<Idol> findAllSortedByName();

    // 그룹명으로 아이돌을 조회
    List<Idol> findByGroupName();
}
