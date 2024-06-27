package com.spring.jpastudy.chap06_querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.chap06_querydsl.entity.Group;
import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import com.spring.jpastudy.chap06_querydsl.entity.QGroup;
import com.spring.jpastudy.chap06_querydsl.entity.QIdol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Table;

import java.util.List;
import java.util.Optional;

import static com.spring.jpastudy.chap06_querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class QueryDslBasicTest {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    IdolRepository idolRepository;

    // JPA의 CRUD를 제어하는 객체 !
    @Autowired
    EntityManager em;

    //⭐QueryDslConfig 클래스에 만들어둔거 가져오기 !
    @Autowired
    JPAQueryFactory factory;

    @BeforeEach
    void setUp() {
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
    }

    @Test
    @DisplayName("JPQL로 특정 아이돌 조회하기")
    void jpqlTest() {
        //given
        String jpqlQuery = "SELECT i FROM Idol i WHERE i.idolName = ?1";
        //when
        Idol foundIdol = em.createQuery(jpqlQuery, Idol.class)
                .setParameter(1, "리즈")
                .getSingleResult();

        //then
        assertEquals("아이브", foundIdol.getGroup().getGroupName());

        System.out.println("\n\n\n\n");
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
        System.out.println("\n\n\n\n");
    }

    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
        //QueryDsl로 JPQL을 만드는 빌더 ! -> ⭐가져와서 씀
//        JPAQueryFactory factory = new JPAQueryFactory(em);
        //when
        Idol foundIdol = factory
                .select(idol)  // alt + enter로 간략화 할 수 있음
                .from(idol)
                .where(idol.idolName.eq("리즈"))
                .fetchOne();

        //then
        assertEquals("아이브", foundIdol.getGroup().getGroupName());

        System.out.println("\n\n\n\n");
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
        System.out.println("\n\n\n\n");
    }

    @Test
    @DisplayName("이름과 나이로 아이돌 조회하기")
    void searchTest() {
        //given
        String name = "가을";
        int age = 22;
        //when
        Idol foundIdol = factory
                .select(idol)
                .from(idol)
                .where(
                        idol.idolName.eq(name)
                                .and(idol.age.eq(age))
                )
                .fetchOne();
        //then
        assertNotNull(foundIdol);
        assertEquals("아이브", foundIdol.getGroup().getGroupName());

        System.out.println("\n\n\n\n");
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
        System.out.println("\n\n\n\n");

//        idol.idolName.eq("리즈") // idolName = '리즈'
//        idol.idolName.ne("리즈") // username != '리즈'
//        idol.idolName.eq("리즈").not() // username != '리즈'
//        idol.idolName.isNotNull() //이름이 is not null
//        idol.age.in(10, 20) // age in (10,20)
//        idol.age.notIn(10, 20) // age not in (10, 20)
//        idol.age.between(10,30) //between 10, 30
//        idol.age.goe(30) // age >= 30
//        idol.age.gt(30) // age > 30
//        idol.age.loe(30) // age <= 30
//        idol.age.lt(30) // age < 30
//        idol.idolName.like("_김%")  // like _김%
//        idol.idolName.contains("김") // like %김%
//        idol.idolName.startsWith("김") // like 김%
//        idol.idolName.endsWith("김") // like %김

    }

    @Test
    @DisplayName("조회 결과 반환하기")
    void fetchTest() {
        //given
        // 리스트 전체 조회
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .fetch();   // 다중행 조회
        System.out.println("\n\n==================fetch=====================");
        idolList.forEach(System.out::println);

        // 단일행 조회 (fetchOne)
        Idol foundIdol = factory
                .select(idol)
                .from(idol)
                .where(idol.age.lt(21))
                .fetchOne();
        System.out.println("\n\n==================fetchOne=====================");

        System.out.println("foundIdol = " + foundIdol);

        Optional<Idol> foundIdolOptional = Optional.ofNullable(factory
                .select(idol)
                .from(idol)
                .where(idol.age.lt(21))
                .fetchOne());

        Idol foundIdol2 = foundIdolOptional.orElseThrow();

        System.out.println("\n\n=========== fetchOne (Optional) =============");
        System.out.println("foundIdol2 = " + foundIdol2);
    }

    @Test
    @DisplayName("나이가 24세 이상인 아이돌을 조회하세요")
    void QuizTest() {
        //given
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .where(idol.age.goe(24))
                .fetch();
        System.out.println("\n\n==================AGE 24 FETCH=====================");
        idolList.forEach(System.out::println);
    }

    @Test
    @DisplayName("이름에 김 이라는 문자열이 포함된 아이돌을 조회하세요")
    void KimNameContaining() {
        //given
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .where(idol.idolName.contains("김"))
                .fetch();
        System.out.println("\n\n==================KIM FETCH=====================");
        idolList.forEach(System.out::println);
    }

    @Test
    @DisplayName("나이가 20세에서 25세 사이인 아이돌을 조회하세요")
    void ageTest() {
        //given
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .where(idol.age.between(20, 25))
                .fetch();
        System.out.println("\n\n==================AGE FETCH=====================");
        idolList.forEach(System.out::println);
    }

    @Test
    @DisplayName("그룹이름이 문자열 르세라핌인 아이돌을 조회하세요")
    void GroupNameTest() {
        //given
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .where(idol.group.groupName.eq("르세라핌"))
                .fetch();
        System.out.println("\n\n==================르세라핌 GROUP FETCH=====================");
        idolList.forEach(System.out::println);
    }

}