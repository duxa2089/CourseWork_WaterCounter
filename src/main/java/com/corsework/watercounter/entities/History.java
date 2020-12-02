package com.corsework.watercounter.entities;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.hibernate.type.PostgresUUIDType;

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
@Table(name = "history", schema = "public")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@TypeDef(name="postgres-uuid",
        defaultForType = UUID.class,
        typeClass = PostgresUUIDType.class)
public class History {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "history_id", length = 36, updatable = false, nullable = false)
    private UUID history_id;

    @Column(name = "isConfirmed")
    private boolean isConfirmed;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activity_id")
    @Fetch(FetchMode.SELECT)
    private List<Activity> activities;
}
