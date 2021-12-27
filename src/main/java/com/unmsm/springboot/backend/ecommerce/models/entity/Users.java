package com.unmsm.springboot.backend.ecommerce.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "no puede estar vacio")
    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @NotEmpty(message = "no puede estar vacio")
    @Column(unique = true, length = 20, name = "username", nullable = false)
    private String username;

    @NotEmpty(message = "no puede estar vacio")
    @Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12")
    @Column(nullable = false, name = "name")
    private String name;

    @NotEmpty(message = "no puede estar vacio")
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @NotEmpty(message = "no puede estar vacio")
    @Column(name = "phone", length = 80, nullable = false)
    private String phone;

    @Column(name = "image")
    private String image;

    @NotEmpty(message = "no puede estar vacio")
    @Column(length = 60, name = "password", nullable = false)
    private String password;

    @Column(name = "is_available")
    private Boolean is_available;

    @Column(name = "session_token")
    private String session_token;

    @Column(name = "notification_token")
    private String notification_token;

    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_has_roles", joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns=@JoinColumn(name="id_rol"),
    uniqueConstraints= {@UniqueConstraint(columnNames= {"id_user", "id_rol"})})*/
    @OneToMany
    @JoinColumn(name="id_user")
    @JsonIgnore
    private List<UserHasRoles> userHasRoles;

}
