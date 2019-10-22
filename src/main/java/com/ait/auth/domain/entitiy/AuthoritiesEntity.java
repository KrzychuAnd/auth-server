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
@Builder
@Table(name="authorities")
public class AuthoritiesEntity implements GrantedAuthority {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "authority")
    private String authority;
}
