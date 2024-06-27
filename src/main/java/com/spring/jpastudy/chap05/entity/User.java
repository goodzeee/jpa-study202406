package com.spring.jpastudy.chap05.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "purchaseList")  // employees만 제거 !
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder

@Entity
@Table(name = "tbl_mtm_user")
public class User {

    // 상품N - 회원N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    // 회원은 구매를 여러 번 할 수 있어 1회원 : N구매
    @OneToMany(mappedBy = "user", orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Purchase> purchaseList;

}
