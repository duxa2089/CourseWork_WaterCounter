package com.corsework.watercounter.entities;

import com.corsework.watercounter.utils.UserRole;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.PostgresUUIDType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Users", schema = "public")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@TypeDef(name="postgres-uuid",
        defaultForType = UUID.class,
        typeClass = PostgresUUIDType.class)
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "user_id", length = 36, updatable = false, nullable = false)
    private UUID user_id;

    @Column(name = "firstname", length = 1000, nullable = false)
    private String firstName;

    @Column(name = "middlename", length = 1000)
    private String middleName;

    @Column(name = "lastname", length = 1000, nullable = false)
    private String lastName;

    @Column(name = "email", length = 1000, nullable = false)
    private String email;

    @Column(name = "username", length = 1000, nullable = false)
    private String username;

    @Column(name = "password", length = 1000, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    @Column(name = "role", length = 15, nullable = false)
    private UserRole role;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "history_id")
//    @Fetch(FetchMode.SELECT)
//    private List<History> history;
}
