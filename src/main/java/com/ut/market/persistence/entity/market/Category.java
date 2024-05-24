package com.ut.market.persistence.entity.market;

import lombok.Getter;

@Getter
public enum Category {

    FOOD("comestibles", 1L),
    PAPER("papeleria", 2L),
    CLEANING("aseo", 1L);


    private final String name;
    private final Long id;

    Category(String name, Long id) {
        this.name = name;
        this.id = id;
    }

}
