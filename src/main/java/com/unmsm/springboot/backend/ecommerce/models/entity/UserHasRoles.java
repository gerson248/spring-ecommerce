package com.unmsm.springboot.backend.ecommerce.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "user_has_roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHasRoles implements Serializable {

    @Id
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_user", nullable = false)
    private Users users;

    @Id
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_rol", nullable = false)
    private Roles roles;

    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updated_at;
}
