package com.gmm.restaurants.exceptions;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private String property;
    private String id;

    public NotFoundException(String property, String id) {
        this.property = property;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Not found " + property + " with id " + id;
    }
}
