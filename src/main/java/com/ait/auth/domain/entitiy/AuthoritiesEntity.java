package com.ait.auth.domain.entitiy;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="authorities")
public class AuthoritiesEntity implements GrantedAuthority {

    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "authority")
    private String authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private UsersEntity user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
