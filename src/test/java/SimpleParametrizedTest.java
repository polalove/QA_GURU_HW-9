import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@DisplayName("Checking the site's operation")
@Tag("WEB")
public class SimpleParametrizedTest extends TestBase {

    @ValueSource(strings = {
            "Fish", "Dogs", "Cats", "Reptiles", "Birds"
    })

    @ParameterizedTest(name = "Checking the transition from the main page {0}")
    void selectedAnimalPageTest(String setAnimal) {
        open("/actions/Catalog.action");
        $("area[alt=" + setAnimal + "]").click();
        $("#Catalog").shouldHave(text(setAnimal));
    }

    static Stream<String> categoryProvider() {
        return Stream.of("FISH", "DOGS", "CATS", "REPTILES");
    }

    @MethodSource("categoryProvider")
    @ParameterizedTest(name = "Checking for categories {0}")
    void categoryIdentifierTest(String category) {
        open("/actions/Catalog.action");
        $("div#QuickLinks a[href*='categoryId=" + category + "']").click();
        $("div#Catalog h2").shouldHave(Condition.text(category));
    }

    @CsvFileSource(resources = "/test_data/testCategoryIdentifierCsv.csv")
    @ParameterizedTest(name = "Checking for categories {0}")
    void categoryIdentifierCsvTest(String category) {
        open("/actions/Catalog.action");
        $("div#QuickLinks a[href*='categoryId=" + category + "']").click();
        $("div#Catalog h2").shouldHave(Condition.text(category));
    }

}