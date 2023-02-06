package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    User.UserData user = new User().getUserFirst();


    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private SelenideElement closeButton = $("[data-test-id=error-notification] button");

    public VerificationPage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }

    public LoginPage invalidLogin(String login) {
        loginField.setValue(login);
        passwordField.setValue(login);
        loginButton.click();
        errorNotification.shouldBe(Condition.appear);
        return new LoginPage();
    }

    public LoginPage clearFields() {
        closeButton.click();
        loginField.doubleClick().sendKeys(Keys.DELETE);
        passwordField.doubleClick().sendKeys(Keys.DELETE);
        return new LoginPage();
    }
}
