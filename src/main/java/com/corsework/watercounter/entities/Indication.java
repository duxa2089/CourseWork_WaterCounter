package com.corsework.watercounter.entities;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.PostgresUUIDType;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Indication", schema = "public")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@TypeDef(name="postgres-uuid",
        defaultForType = UUID.class,
        typeClass = PostgresUUIDType.class)
public class Indication {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "indication_id", length = 36, updatable = false, nullable = false)
    private UUID indication_id;

    @Column(name = "value", length = 1000, nullable = false)
    private String value;
}
