package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");

        driver.findElement(By.partialLinkText("Create Product")).click();

        String currentUrl = driver.getCurrentUrl();

        assertEquals(baseUrl + "/product/create", currentUrl);

        String fixedName = "Samber";
        String fixedQuantity = "100";

        driver.findElement(By.id("nameInput")).clear();
        driver.findElement(By.id("nameInput")).sendKeys(fixedName);

        driver.findElement(By.id("quantityInput")).clear();
        driver.findElement(By.id("quantityInput")).sendKeys(fixedQuantity);

        driver.findElement(By.xpath("//button[text()='Submit']")).click();

        assertEquals(baseUrl + "/product/list", driver.getCurrentUrl());

        WebElement productRow = driver.findElement(By.xpath("//tr[descendant::td[@data-field='productName' and text()='" + fixedName + "']]"));

        WebElement nameCell = productRow.findElement(By.xpath(".//td[@data-field='productName']"));
        WebElement quantityCell = productRow.findElement(By.xpath(".//td[@data-field='productQuantity']"));

        assertEquals(fixedName, nameCell.getText());
        assertEquals(fixedQuantity, quantityCell.getText());
    }
}