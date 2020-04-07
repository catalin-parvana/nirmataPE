package pages.catalog.helmRepo;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.catalog.catalog.InsideApplicationPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;
import static org.testng.Assert.assertEquals;


public class InsideHelmChartPage extends LibraryUtils {

    private SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private SelenideElement actionButton=$x("//a[@class='btn btn-default dropdown-toggle']");
    private SelenideElement deleteRepositoryButton=$x("//a[@id='deleteChartRepository']");
    private SelenideElement editRepositoryButton=$x("//a[@id='editChartRepository']");
    private SelenideElement inputNameField=$x("//input[@id='name']");
    private SelenideElement inputLocationField=$x("//input[@id='location']");
    private SelenideElement saveButton=$x("//button[contains(text(),'Save')]");
    private SelenideElement deleteButton=$x("//button[contains(text(),'Delete')]");
    private SelenideElement catalogDropdown=$x("//select[@id='catalog']");
    private SelenideElement updateCheckbox=$x("//ins[@class='iCheck-helper']");
    private SelenideElement importChartButton=$x("//button[text()='Import Chart']");

    private SelenideElement application;
    private WebDriver driver;

    public InsideHelmChartPage(WebDriver driver){
        this.driver=driver;
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
    }

    public InsideHelmChartPage verifyPanelTitle(String helmChartName){
        waitFor("Panel Title",modelContentPanelTitle);
        assertEquals(modelContentPanelTitle.getText(), helmChartName, "Incorrect Panel Title");
        return this;
    }

    public InsideHelmChartPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideHelmChartPage clickEditRepositoryButton(){
        click("Edit Repository Button",editRepositoryButton);
        return this;
    }

    public InsideHelmChartPage setRepositoryName(String newRepositoryName){
        type("Repository Name",inputNameField,newRepositoryName);
        return this;
    }
    public InsideHelmChartPage setRepositoryLocation(String newRepositoryLocation){
        type("Repository Location",inputLocationField,newRepositoryLocation);
        return this;
    }

    public InsideHelmChartPage clickSaveButton(){
        click("Save Button",saveButton);
        refresh();
        return this;
    }

    public InsideHelmChartPage clickDeleteRepositoryButton(){
        click("Delete Repository Button",deleteRepositoryButton);
        return this;
    }

    public HelmChartsPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        return new HelmChartsPage(driver);
    }

    public InsideHelmChartPage clickOnApplicationToImport(String applicationName){
        application=$x("//*[@class='card-title text-truncate']/*[contains(text(),'"+applicationName+"')]");
        actionButton.shouldBe(visible);
        click("Application To Import",application);
        return this;
    }

    public InsideHelmChartPage selectCatalogToImportApplicationFromDropdown(String catalogName){
        selectOptionByText("Catalog To Import Application From",catalogDropdown,catalogName);
        return this;

    }

    public InsideHelmChartPage setUpdateCheckbox(){
        click("Update Checkbox",updateCheckbox);
        return this;
    }

    public InsideApplicationPage clickImportChart(){
        click("Import Chart",importChartButton);
        return new InsideApplicationPage(driver);
    }

}
