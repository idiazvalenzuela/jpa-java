package com.codely.jpajava.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class VegetableId implements Serializable {

    private String name;

    private String family;
}
