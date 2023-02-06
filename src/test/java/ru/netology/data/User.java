package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;


public class User {
    public UserData getUserFirst() {
        Faker user = new Faker(new Locale("en"));
        String id = user.idNumber().valid();
        String name = user.name().firstName();
        String password = "qwerty123";
        String encryptedPassword = "$2a$10$NH6ljuqZpVyfg8dFoONYLuHUO8RbHiRl03Lv4mP5yJslMdrMNCyy.";
        return new UserData(id, name, password, encryptedPassword);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private String id;
        private String login;
        private String password;
        private String encryptedPassword;
    }
}
