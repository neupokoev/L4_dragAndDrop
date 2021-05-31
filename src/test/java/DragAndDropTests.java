import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.DragAndDropOptions.usingActions;
import static com.codeborne.selenide.DragAndDropOptions.usingJavaScript;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Log4j
public class DragAndDropTests {

    @BeforeEach
    void setup() {
        log.info("Start test " + LocalDateTime.now());
    }

    @AfterEach
    void tears() {
        log.info("End of test " + LocalDateTime.now());
    }

    @Test
    void dragAndDropSelenideTest() {
        // open page
        open("https://the-internet.herokuapp.com/drag_and_drop");
        $$(".column").get(0).shouldHave(text("A"));

        // drag and drop
        //$("#column-a").dragAndDropTo("#column-b", usingActions()); // does not work here
        $("#column-a").dragAndDropTo("#column-b", usingJavaScript()); //works

        // check result
        $$(".column").get(0).shouldHave(text("B"));
    }

    @Test
    void dragAndDropUsingActionsSelenideTest() {
        // open page
        open("http://www.pbclibrary.org/mousing/click4.htm");

        // prepare test data
        WebDriver driver = WebDriverRunner.driver().getWebDriver();
        WebElement squareA = driver.findElement(By.cssSelector("div img"));
        int xCoordinateBeforeMoving = squareA.getLocation().getX();
        WebElement squareB = driver.findElement(By.cssSelector("div a"));

        // drag and drop
        //$("#drag1div").dragAndDropTo(squareB); //does not work
        //$("div img").dragAndDropTo("div a", usingJavaScript()); //does not work
        $("div img").dragAndDropTo("div a", usingActions()); //works

        // check result
        int xCoordinateAfterMoving = squareA.getLocation().getX();
        assertThat(xCoordinateAfterMoving).isNotEqualTo(xCoordinateBeforeMoving);
    }

    @Test
    void dragAndDropWebElementsBuilderTest() {
        open("http://www.pbclibrary.org/mousing/click4.htm");

        WebDriver driver = WebDriverRunner.driver().getWebDriver();
        WebElement squareA = driver.findElement(By.cssSelector("div img"));
        int xCoordinateBeforeMoving = squareA.getLocation().getX();
        WebElement squareB = driver.findElement(By.cssSelector("div a"));

        // drag and drop
        Actions builder = new Actions(driver);
        builder.dragAndDrop(squareA, squareB).perform();//works

        // check result
        int xCoordinateAfterMoving = squareA.getLocation().getX();
        assertThat(xCoordinateAfterMoving).isNotEqualTo(xCoordinateBeforeMoving);
    }

    @Test
    void dragAndDropMoveByOffsetTest() {
        open("http://www.pbclibrary.org/mousing/click4.htm");

        WebDriver driver = WebDriverRunner.driver().getWebDriver();
        WebElement squareA = driver.findElement(By.id("drag1div"));
        int xCoordinateBeforeMoving = squareA.getLocation().getX();

        // drag and drop
        actions().moveToElement(squareA)
                .clickAndHold()
                .moveByOffset(200, 200)
                .release()
                .perform();

        // check result
        int xCoordinateAfterMoving = squareA.getLocation().getX();
        assertThat(xCoordinateAfterMoving).isNotEqualTo(xCoordinateBeforeMoving);
    }
}
