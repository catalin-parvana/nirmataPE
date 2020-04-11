package pages.catalog.helmRepo;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.title;
import static org.testng.Assert.*;


public class HelmChartsPage extends LibraryUtils {

    private final SelenideElement addHelmRepositoryButton= $x("//button[@id='addRepository']");
    private final SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement locationInputField=$x("//input[@id='location']");
    private final SelenideElement addButton=$x("//button[contains(text(),'Add')]");
    private SelenideElement helmRepo;
    private final WebDriver driver;

    public HelmChartsPage(WebDriver driver){
        this.driver=driver;
        addHelmRepositoryButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(),"Helm Charts");
        assertEquals(title(),"Nirmata | Catalog | Helm Charts","Incorrect Page Title");
    }

    public HelmChartsPage verifyPanelTitle(){
        assertEquals(modelContentPanelTitle.getText(), "Helm Charts", "Incorrect Panel Title");
        return this;
    }

    public String getPageTitle(){
        return title();
    }


    public HelmChartsPage clickAddHelmRepository(){
        click("Add Helm repository Button",addHelmRepositoryButton);
        return this;
    }

    public HelmChartsPage setNameInputField(String helmChartName){
        type("Helm Chart Name",nameInputField,helmChartName);
        return this;
    }

    public HelmChartsPage setLocationInputField(String helmChartLocation){
        type("Helm Chart Location",locationInputField,helmChartLocation);
        return this;
    }

    public HelmChartsPage clickAddButton(){
        click("Add Button",addButton);
        addButton.should(disappear);
        return this;
    }

    public HelmChartsPage isCreatedHelmChartDisplayed(String helmRepoName){
        helmRepo=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+helmRepoName+"']");
        waitFor("Helm Repo "+helmRepoName,helmRepo);
        assertTrue(helmRepo.exists(),"Helm Chart Was Not Created");
        return this;
    }

    public HelmChartsPage isDeletedHelmChartDisplayed(String helmRepoName){
        helmRepo=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+helmRepoName+"']");
//        waitFor("Helm Repo "+helmRepoName,helmRepo);
        assertFalse(helmRepo.exists(),"Helm Chart Was Not Deleted");
        return this;
    }

    public InsideHelmChartPage clickOnHelmChartRepo(String helmRepoName){
        helmRepo=$x("//span[@for='name'][text()='"+helmRepoName+"']");
        click("Helm Chart Repo "+ helmRepoName,helmRepo);
        return new InsideHelmChartPage(driver);
    }

}
