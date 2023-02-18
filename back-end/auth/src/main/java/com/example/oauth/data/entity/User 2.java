package com.example.oauth.data.entity;

import com.example.oauth.data.entity.type.AuthorityType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
//@Table(name = "VIEW_USER")
@Table(name = "PET_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User  {
    //private static final long serialVersionUID = -1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String name;

    private String phone;

    private String provider;

    @ElementCollection(targetClass = AuthorityType.class)
    @CollectionTable(name = "PET_USER_AUTHORITY", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    private List<AuthorityType> authorities = new ArrayList<>();


    @Builder
    public User(String username, String password, String name, String provider, String phone) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.provider = provider;
        this.phone = phone;
        this.authorities.add(AuthorityType.ROLE_USER);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
    }
}