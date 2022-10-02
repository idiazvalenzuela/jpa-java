package com.codely.jpajava.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "vegetable")
@IdClass(VegetableId.class)
public class Vegetable {

    @Id
    private String name;

    @Id
    private String family;

    @Column(name = "vegetable_type", nullable = false)
    private String type;

    @Column(name = "average_weight")
    private Double averageWeight;

}
