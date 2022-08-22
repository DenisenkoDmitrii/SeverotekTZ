package SeverotekTZ;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

/*
Denisenko Dmitriy (18.08.2022 - 22.08.2022)
 */

public class AppTest {

    static WebDriver driver;

    @BeforeAll
    static void setDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--incognito");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }


    @AfterAll
    public static void exit() {
        driver.quit();
    }


    //==================================== Найти ноутбук #laptopName #minPrice #maxPrice ==================================================
    @Test
    void findLaptop() {

        //Параметры поиска - РЕДАКТИРУЕМЫЕ (можно поменять имя ноутбука и диапазон цены)
        String laptopName = "Lenovo";
        String laptopLabel = "//label[.='Lenovo']";
        String minPrice = "25000";
        String maxPrice = "30000";
        //Конец параметров поиска

        //Проверки, что нашли ноутбук заданного производителя. Первые три ноутбука в выдаче должны соответствовать имени ноутбука "laptopName"
        String assert_h1 = "//h1[contains(text(),'" + laptopName + "')]";
        String assert_h2 = "//div[@data-index=1]/div/div/div[1]/article/div/h3/a/span[contains(text(),'" + laptopName + "')]";
        String assert_h3 = "//div[@data-index=1]/div/div/div[2]/article/div/h3/a/span[contains(text(),'" + laptopName + "')]";
        String assert_h4 = "//div[@data-index=1]/div/div/div[3]/article/div/h3/a/span[contains(text(),'" + laptopName + "')]";

        driver.get("https://market.yandex.ru");
        driver.findElement(By.id("catalogPopupButton")).click();
        driver.findElement(By.linkText("Компьютеры")).click();
        driver.findElement(By.linkText("Ноутбуки")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Показать всё')]")).click();
        driver.findElement(By.xpath("//label[.='Найти производителя']/../*[name()='input']")).sendKeys(laptopName);
        driver.findElement(By.xpath(laptopLabel)).click();
        driver.findElement(By.xpath("//label[contains(text(),'Цена, ₽ от')] /../div/div/*[name()='input']")).sendKeys(minPrice);
        driver.findElement(By.xpath("//label[contains(text(),'Цена, ₽ до')] /../div/div/*[name()='input']")).sendKeys(maxPrice);
        driver.findElement(By.xpath("//span[contains(text(),'Найдено')]")).click();

        //кликаем для отображание выдачи в виде сетки
        driver.findElement(By.xpath("//input[contains(@aria-label,'сетки')]")).click();

        //Проверяем, что наименование компании ноутбука в заголовке H1 соответствует "laptopName"
        WebElement assert_1 = driver.findElement(By.xpath(assert_h1));
        assertNotNull(assert_1);

        //Три проверки на то, что в выдаче первые три результата - это ноутбуки с "laptopName"
        WebElement assert_2 = driver.findElement(By.xpath(assert_h2));
        assertNotNull(assert_2);

        WebElement assert_3 = driver.findElement(By.xpath(assert_h3));
        assertNotNull(assert_3);

        WebElement assert_4 = driver.findElement(By.xpath(assert_h4));
        assertNotNull(assert_4);


    }
}
