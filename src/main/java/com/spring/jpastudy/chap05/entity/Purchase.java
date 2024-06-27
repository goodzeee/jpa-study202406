package com.spring.jpastudy.chap05.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = {"User", "Goods"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder

@Entity
@Table(name = "tbl_mtm_purchase")
// 상품과 회원의 다대다를 풀어주는 중간 클래스 !
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    // 다인 구매에서 1인 회원을 가져옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;
}
