package com.gmm.restaurants.exceptions;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private String property;
    private Integer id;

    public NotFoundException(String property, Integer id) {
        this.property = property;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Not found " + property + " with id " + id;
    }
}
