package com.jcs.BateriaStore.dtos;

public class UserInsertDto extends UserDto {

    private String password;

    UserInsertDto() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
