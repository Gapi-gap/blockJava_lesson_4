package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

    @Test
    void shouldRegisterByAccountNumberDOMModification() {
        Selenide.open("http://localhost:9999");
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = date.format(formatter);
        $(byCssSelector("[data-test-id='city'] input")).setValue("Москва");
        $(byCssSelector("[data-test-id='date'] input")).press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE).setValue(formattedDate);
        $(byName("name")).setValue("Иванов Иван");

        $(byName("phone")).setValue("+79309554618");
        $(byName("agreement")).parent().click();
        $$("button").find(exactText("Забронировать")).click();
        String text = "Встреча успешно забронирована на "+formattedDate;

        $(".notification__content").should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text(text));
    }


}
