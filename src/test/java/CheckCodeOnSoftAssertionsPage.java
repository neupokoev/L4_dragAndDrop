import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CheckCodeOnSoftAssertionsPage {
    @Test
    void checkCodeOnSoftAssertionsPage() {
        String testDataJunit5Text = "@ExtendWith({SoftAssertsExtension.class})\n" +
                "class Tests {\n" +
                "  @Test\n" +
                "  void test() {\n" +
                "    Configuration.assertionMode = SOFT;\n" +
                "    open(\"page.html\");\n" +
                "\n" +
                "    $(\"#first\").should(visible).click();\n" +
                "    $(\"#second\").should(visible).click();\n" +
                "  }\n" +
                "}";

        // Откройте страницу Selenide в Github
        open("https://github.com/selenide/selenide");
        $("h1").shouldHave(text("selenide / selenide"));

        // Перейдите в раздел Wiki проекта
        $("ul li [data-content=Wiki]").click();
        $("#wiki-content h1").shouldHave(text("Welcome to the selenide wiki!"));

        // Убедитесь, что в списке страниц (Pages) есть страница SoftAssertions, Откройте страницу SoftAssertions
        $$(".markdown-body ul li a").findBy(text("Soft assertions")).click();
        $("#wiki-wrapper h1").shouldHave(text("SoftAssertions"));

        //проверьте, что внутри есть пример кода для JUnit5
        $$(".markdown-body ol li").shouldHave(itemWithText("Using JUnit5 extend test class:"));
        $x("//li[text()='Using JUnit5 extend test class:']/ancestor::ol/following-sibling::div[1]")
                .shouldHave(text(testDataJunit5Text));
    }
}
