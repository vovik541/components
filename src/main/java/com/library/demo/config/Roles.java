package com.library.demo.config;

import java.util.Set;

public enum Roles {
    READER("READER"),
    LIBRARIAN("LIBRARIAN");

    private final String roleName;
    Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getFullRoleName(){
        return "ROLE_"+roleName;
    }
}
