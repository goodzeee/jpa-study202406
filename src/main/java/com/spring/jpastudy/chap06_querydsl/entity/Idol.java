package com.spring.jpastudy.chap06_querydsl.entity;

import lombok.*;

import javax.persistence.*;


@Setter @Getter
@ToString(exclude = "group")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Idol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idol_id")
    private Long id;

    private String idolName;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 1 : N일 때 N쪽에 LAZY !
    @JoinColumn(name = "group_id")
    private Group group;


    public Idol(String idolName, int age, Group group) {
        this.idolName = idolName;
        this.age = age;
        if (group != null) {
            changeGroup(group);
        }
    }


    public void changeGroup(Group group) {
        this.group = group;
        group.getIdols().add(this);
    }
}

