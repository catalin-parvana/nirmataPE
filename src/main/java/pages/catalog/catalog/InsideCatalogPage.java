package pages.catalog.catalog;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;


public class InsideCatalogPage extends LibraryUtils {

    private final SelenideElement toggleCardView=$("img#toggleCardView");
    private final SelenideElement toggleTableView=$("img#toggleTableView");
    private final SelenideElement addApplicationButton=$x("//button[contains(.,'Add Application')]");
    private final SelenideElement modelContentPanelTitle=$("h1#model-content-panel-title");
    private final SelenideElement nameInputField=$("input#name");
    private final SelenideElement yamlInputField=$(".dz-hidden-input");
    private final SelenideElement createApplicationButton=$x("//button[contains(text(),'Create')]");
    private final SelenideElement actionButton=$("div.btn-group");
    private final SelenideElement editCatalogButton=$("a#editCatalog");
    private final SelenideElement deleteCatalogButton=$("a#deleteCatalog");
    private final SelenideElement deleteCatalogModalDialogTitle=$("div.dialog-title");
    private final SelenideElement deleteButton=$x("//button[contains(text(),'Delete')]");
    private final SelenideElement deleteCatalogControlLabel=$("span.control-label");
    private final SelenideElement deleteCatalogModalDialog=$("div.nirmata-dialog");
    private final SelenideElement saveButton=$x("//button[contains(text(),'Save')]");
    private final SelenideElement runApplicationButton=$x("//button[contains(text(),'Run application')]");
    private final SelenideElement modalDialog=$("div.modal-body");
    private final SelenideElement nextButton = $x("//button[contains(text(),'Next')]");
    private final SelenideElement nextButtonEnabled=$x("//button[contains(text(),'Next')][not(@disabled)]");

    private SelenideElement application;
    private final WebDriver driver;
    private boolean found=false;


    public InsideCatalogPage(WebDriver driver){
        this.driver=driver;
        addApplicationButton.shouldBe(visible);
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
    }

    public InsideCatalogPage verifyPanelTitle(String catalogName){
        assertEquals(modelContentPanelTitle.getText(),catalogName,"Incorrect Page Panel Title");
        return this;
    }

    public InsideCatalogPage clickAddApplicationButton(){
        click("Add Application Button",addApplicationButton);
        return this;
    }

    public InsideCatalogPage setApplicationName(String name){
        type("Application Name",nameInputField,name);
        return this;
    }

    public InsideCatalogPage setYamlFile(String yamlName){
        String filePath=absolutePathOfFile +"/resources/yaml/"+ yamlName+".yaml";
        upload("Yaml File "+yamlName+".yaml",yamlInputField,filePath);
        return this;
    }

    public InsideApplicationPage clickCreateApplicationButton(){
        click("Create Application Button",createApplicationButton);
        createApplicationButton.should(disappear);
        return new InsideApplicationPage(driver);
    }

    public InsideCatalogPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideCatalogPage clickEditCatalogButton(){
        click("Edit Catalog Button",editCatalogButton);
        return this;
    }

    public InsideCatalogPage clickDeleteCatalogButton(){
        click("Delete catalog Button",deleteCatalogButton);
        return this;
    }

    public InsideCatalogPage setCatalogNameIfNecessary(String catalogName){
        modalDialog.shouldBe(visible);
        if(nameInputField.exists()){
            type("Name Input Field",nameInputField,catalogName);
        }
        return this;
    }

    public CatalogsPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.should(disappear);
        return new CatalogsPage(driver);
    }

    public InsideCatalogPage verifyPageTitle(String catalogName){
        assertEquals(title(), "Nirmata | Catalog | "+catalogName, "Incorrect Page Title");
        return this;
    }

    public InsideApplicationPage clickOnApplicationWithName(String applicationName){
        addApplicationButton.shouldBe(visible);
        if(toggleCardView.exists()){
            application=$x("//div[@class='application-name']//*[text()='"+applicationName+"']");
        }
        if(toggleTableView.exists()){
            application=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+applicationName+"']");
        }

        if (application!=null && application.exists()){
            click("Application "+applicationName,application);
        }else
            if(nextButton.exists()){
                nextButton.scrollIntoView(true);
                if(nextButtonEnabled.exists()) {
                    do {
                        click("Next Button", nextButton);
                        addApplicationButton.shouldBe(visible).scrollIntoView(true);
                        if (application.exists()) {
                            click("Application " + applicationName, application);
                            break;
                        }
                    }
                    while (nextButtonEnabled.exists());
                }
            }
            return new InsideApplicationPage(driver);
    }

    public InsideCatalogPage setNewCatalogName(String name){
        type("New Catalog Name",nameInputField,name);
        return this;
    }

    public InsideCatalogPage clickSaveButton(){
        click("Save Button",saveButton);
        refresh();
        return this;
    }

    public InsideCatalogPage isCreatedApplicationDisplayed(String appName){
        assertTrue(isApplicationDisplayed(appName),"Application was not created");
        return this;
    }

    public InsideCatalogPage isDeletedApplicationDisplayed(String appName){
        assertFalse(isApplicationDisplayed(appName),"Application was not deleted");
        return this;
    }

    public InsideCatalogPage isClonedApplicationDisplayed(String appName){
        assertTrue(isApplicationDisplayed(appName),"Application Was Not Cloned");
        return this;
    }

    public InsideCatalogPage isMovedApplicationDisplayed(String appName){
        assertTrue(isApplicationDisplayed(appName),"Application Was Not Moved");
        return this;
    }

    private boolean isApplicationDisplayed(String applicationName){
        addApplicationButton.shouldBe(visible);

        if(toggleCardView.exists()){
            application=$x("//div[@class='application-name']//*[text()='"+applicationName+"']");
        }else if(toggleTableView.exists()){
            application=$x("//div[contains(@class,'card-content card-select')]//*[text()='"+applicationName+"']");
        }

        if(application!=null && application.exists()){
            found=true;
        }else
        if(nextButton.exists()){
            if(nextButtonEnabled.exists()){
                do {
                    nextButton.scrollIntoView(true);
                    click("Next Button",nextButton);
                    modelContentPanelTitle.scrollIntoView(true);
                    if (application.exists()) {
                        found = true;
                        break;
                    }
                } while (nextButtonEnabled.exists());
            }
        }
        return found;
    }

}
