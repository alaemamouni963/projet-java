package model;

import model.enums.Role;

public class Administrator extends User {

    public Administrator(int id, String username, String email, String password) {
        super(id, username, email, password, Role.ADMIN);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}