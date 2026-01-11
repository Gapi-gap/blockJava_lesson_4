package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.beans.PropertyEditor;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {

    @BeforeEach
    void setUp()
    {
        Selenide.open("http://localhost:9999");
    }

    private String GenerateDate()
    {
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
    @Test
    void shouldRegisterByAccountNumberDOMModification() {

        String firstMeetingDate =  GenerateDate();
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue("Поликарпова Светлана");
        $("[data-test-id='phone'] input").setValue("+79309554618");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $(Selectors.withText("Успешно!")).shouldBe(visible,Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + firstMeetingDate))
                .shouldBe(visible);
    }


}
