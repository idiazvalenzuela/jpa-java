package com.codely.jpajava.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Table(name = "owner")
public class Owner {

    @Id
    private Integer id;

    @NaturalId
    @Column(unique = true)
    private String email;

    @NotNull
    private String name;

    @Column(name = "preferred_name")
    private String preferredName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt;
}
