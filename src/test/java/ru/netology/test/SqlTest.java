package ru.netology.test;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.SQL.DataSQL;
import ru.netology.data.User;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class SqlTest {
    User.UserData user = new User().getUserFirst();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clean() {
        DataSQL.cleanDefaultData();
    }

    @Test
    void shouldBeSuccessfulLogin() {
        DataSQL.createUser(user);

        new LoginPage().validLogin(user.getLogin(), user.getPassword()).validVerify(DataSQL.getVerificationCode(user.getId()));
    }

    @Test
    void shouldBlockUserAfterThreeRepsInputInvalidPassword() {
        DataSQL.createUser(user);

        new LoginPage()
                .invalidLogin(user.getLogin())
                .clearFields()
                .invalidLogin(user.getLogin())
                .clearFields()
                .invalidLogin(user.getLogin())
                .clearFields();

        String status = DataSQL.getUserStatus(user.getId());

        Assertions.assertEquals("blocked", status);
    }
}
