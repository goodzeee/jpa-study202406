package com.spring.jpastudy.chap05.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@ToString(exclude = "purchaseList")  // employees만 제거 !
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder

@Entity
@Table(name = "tbl_mtm_goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "goods_name")
    private String name;

    // mappedBy 다쪽에서 뭐라고 부르는지 "" !
    @OneToMany(mappedBy = "goods", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Purchase> purchaseList;

}
