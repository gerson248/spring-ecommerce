package com.unmsm.springboot.backend.ecommerce.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique=true, length=20, name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "route")
    private String route;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_rol")
    private List<UserHasRoles> userHasRoles;
}
