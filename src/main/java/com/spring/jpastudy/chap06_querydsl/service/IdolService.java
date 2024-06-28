package com.spring.jpastudy.chap06_querydsl.service;

import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import com.spring.jpastudy.chap06_querydsl.repository.IdolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.idol;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional  // JPA, QueryDSL 쓸 때 잊지말 것 !!
public class IdolService {

    private final IdolRepository idolRepository;

    // 아이돌을 나이 순으로 내림차 정렬해서 조회
    public List<Idol> getIdols() {
        // ⭐1번째 방법
//        List<Idol> idolList = idolRepository.findAll();

//        return idolList.stream()
//                .sorted(Comparator.comparing(Idol::getAge).reversed())
//                .collect(Collectors.toList());

        // ⭐2번째 방법 -> IdolRepository 에 쿼리문 작성 !
//        List<Idol> idolList1 = idolRepository.findAllBySorted();
//        return idolList1;

        // ⭐3번째 방법 -> IdolRepositoryCustomImpl 에 내가 사용할 메소드 커스텀 해서 사용하기 !
        List<Idol> byGroupName = idolRepository.findByGroupName();
        return byGroupName;
    }
}
