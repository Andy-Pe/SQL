package ru.netology.test;


import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class BankLoginTest {

    @AfterAll
    static void teardown() {
        SQLHelper.cleanDataBase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfullyLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationLoginWithRandomUserWithoutAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authinfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authinfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error notification if login with random in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithRandomActiveUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authinfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authinfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisibility();
    }

//    @Test
//    void shouldBlockUserAfterThreeRepsInputInvalidPassword() {
//        new LoginPage().
//                .clearFields()
//                .invalidLogin(user.getLogin())
//                .clearFields()
//                .invalidLogin(user.getLogin())
//                .clearFields();
//
//        String status = SQLHelper.getUserStatus(user.getId());
//
//        Assertions.assertEquals("blocked", status);
//    }
}
//    DataHelper.UserData user = new DataHelper().getUserFirst();
//
//    @BeforeEach
//    void setUp() {
//        open("http://localhost:9999");
//    }
//
//    @AfterAll
//    static void clean() {
//        SQLHelper.cleanDefaultData();
//    }
//
//    @Test
//    void shouldBeSuccessfulLogin() {
//        SQLHelper.createUser(user);
//
//        new LoginPage().validLogin(user.getLogin(), user.getPassword())
//                .validVerify(SQLHelper.getVerificationCode(user.getId()));
//    }
//
//    @Test
//    void shouldBlockUserAfterThreeRepsInputInvalidPassword() {
//        SQLHelper.createUser(user);
//
//        new LoginPage()
//                .invalidLogin(user.getLogin())
//                .clearFields()
//                .invalidLogin(user.getLogin())
//                .clearFields()
//                .invalidLogin(user.getLogin())
//                .clearFields();
//
//        String status = SQLHelper.getUserStatus(user.getId());
//
//        Assertions.assertEquals("blocked", status);
//    }
