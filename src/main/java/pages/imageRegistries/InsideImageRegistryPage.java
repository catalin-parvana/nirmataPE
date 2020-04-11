package pages.imageRegistries;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class InsideImageRegistryPage extends LibraryUtils {

    private final SelenideElement actionButton=$x("//*[contains(@class,'btn btn-default dropdown-toggle')]");
    private final SelenideElement deleteRegistryButton=$x("//a[@id='deleteRegistry']");
    private final SelenideElement deleteButton=$x("//button[text()='Delete']");
    private final WebDriver driver;

    public InsideImageRegistryPage(WebDriver driver){
        this.driver=driver;
        actionButton.shouldBe(visible);
    }

    public InsideImageRegistryPage clickActionButton(){
        click("Action Button", actionButton);
        return this;
    }

    public InsideImageRegistryPage clickDeleteRegistryButton(){
        click("Delete Registry Button", deleteRegistryButton);
        return this;
    }

    public ImageRegistriesPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return new ImageRegistriesPage(driver);
    }
}
