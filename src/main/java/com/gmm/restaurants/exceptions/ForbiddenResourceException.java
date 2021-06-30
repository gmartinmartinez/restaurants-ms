package com.gmm.restaurants.exceptions;

import lombok.Data;

@Data
public class ForbiddenResourceException extends RuntimeException {
    private String property;
    private String user;

    public ForbiddenResourceException(String property, String user) {
        this.property = property;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Forbidden resource " + property + " for user " + user;
    }
}
