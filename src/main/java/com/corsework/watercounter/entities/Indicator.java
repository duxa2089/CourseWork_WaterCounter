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
@Table(name = "Indicator", schema = "public")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@TypeDef(name="postgres-uuid",
        defaultForType = UUID.class,
        typeClass = PostgresUUIDType.class)
public class Indicator {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "indicator_id", length = 36, updatable = false, nullable = false)
    private UUID indicator_id;

    @Column(name = "firm", length = 1000, nullable = false)
    private String Firm;

    @Column(name = "model", length = 1000, nullable = false)
    private String Model;

    @Column(name = "techParam", length = 1000)
    private String TechParam;

    @Column(name = "guarantee", length = 1000)
    private String Guarantee;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "indication_id")
    @Fetch(FetchMode.SELECT)
    private List<Indication> indications;
}
