package com.ohgiraffers.goonthatbackend.metamate.domain.user;

import com.ohgiraffers.goonthatbackend.metamate.domain.AuditingFields;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class MetaUser extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String name;
    @Column(nullable = false, unique = true)
    private String nickname;
    private String number;
    private String major;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "metaUser")
    private List<FreeBoardPost> posts = new ArrayList<>();

    @Builder
    public MetaUser(String email, String password, String name,
                    String nickname, String number, String major,
                    Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.number = number;
        this.major = major;
        this.role = role;

    }

    public void update(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public void adminUpdate(String email, Role role, String name, String nickname) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.nickname = nickname;
    }
}