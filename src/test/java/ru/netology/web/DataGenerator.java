package ru.netology.web;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {}

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void setUp(RegistrationDto user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new RegistrationDto(user.getLogin(), user.getPassword(), user.getStatus())) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String generateLogin() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String generatePassword() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto generateUser(String status) {
            return new RegistrationDto(generateLogin(), generatePassword(), status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            RegistrationDto getRegisteredUser = generateUser(status);
            setUp(getRegisteredUser);
            return getRegisteredUser;
        }

    }

    @Data
    @RequiredArgsConstructor
    @Value
    public static class RegistrationDto {
        private final String login;
        private final String password;
        private final String status;
    }

}
