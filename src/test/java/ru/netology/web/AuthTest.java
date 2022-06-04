package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.web.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.web.DataGenerator.generateLogin;

public class AuthTest {

    @Test
    @DisplayName("Пользователь active, валидные логин и пароль")
    void shouldSuccessLoginIfUserAreActive() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $x("//*[text()=\"Продолжить\"]").click();
        $(".heading.heading_size_l.heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    @DisplayName("Пользователь blocked, валидные логин и пароль")
    void shouldUnSuccessLoginIfUserAreBlocked() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $x("//*[text()=\"Продолжить\"]").click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка" +
                " Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Пользователь active, ввод невалидного логина")
    void shouldUnSuccessLoginIfUseInvalidLogin() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(generateLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $x("//*[text()=\"Продолжить\"]").click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка" +
                " Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Пользователь active, ввод невалидного пароля")
    void shouldUnSuccessLoginIfUseInvalidPassword() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(generateLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $x("//*[text()=\"Продолжить\"]").click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка" +
                " Ошибка! Неверно указан логин или пароль"));
    }

}