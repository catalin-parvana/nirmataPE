package pages.catalog.catalog;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.environment.InsideDeploymentPage;
import pages.environment.InsideRunningApplicationPage;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class InsideApplicationPage extends LibraryUtils {

    private final SelenideElement modalDialog=$("div.modal-body");
    private final SelenideElement modelContentPanelTitle = $x("//h1[@id='model-content-panel-title']");
    private final SelenideElement runApplicationButton = $x("//button[@action='runApplication']");
    private final SelenideElement actionButton = $x("//*[@class='btn btn-default dropdown-toggle']");
    private final SelenideElement editApplicationButton = $x("//a[@id='editApplication']");
    private final SelenideElement nameInputField = $x("//input[@id='name']");
    private final SelenideElement saveButton = $x("//button[contains(text(),'Save')]");
    private final SelenideElement deleteApplicationButton=$x("//a[@id='deleteApplication']");
    private final SelenideElement deleteButton=$x("//div[@class='modal-footer']//*[text()='Delete']");
    private final SelenideElement importYamlButton=$x("//a[@id='importYaml']");
    private final SelenideElement yamlInputField=$x("(//input[@class='dz-hidden-input'])[1]");
    private final SelenideElement importButton=$x("//button[contains(.,'Import')]");
    private final SelenideElement exportYamlButton=$x("//a[@id='exportApplication']");
    private final SelenideElement downloadYamlFileButton=$x("//button[contains(text(),'Download YAML file')]");
    private final SelenideElement yaml=$x("//div[@class='dz-details']");
    private final SelenideElement runNameInputField=$x("//input[@id='run']");
    private final SelenideElement environmentDropdown=$x("//select[@id='environment']");
    private final SelenideElement runApplicationOnEnvironmentButton=$x("//button[contains(.,'Run Application')]");
    private final SelenideElement runningApplicationLink=$x("//*[@action='showRunningApplications']");
    private final SelenideElement editYamlButton=$x("//a[@id='editYaml']");
    private final SelenideElement cloneApplicationButton=$x("//a[@id='cloneApplication']");
    private final SelenideElement moveApplicationButton=$x("//a[@id='moveApplication']");
    private final SelenideElement targetCatalogDropdown=$x("//select[@id='catalog']");
    private final SelenideElement cloneApplicationModalWindowButton=$x("//button[contains(.,'Clone Application')]");
    private final SelenideElement moveApplicationModalWindowButton=$x("//button[contains(.,'Move Application')]");
    private final SelenideElement catalogNameOnModelCrumb=$x("//span[@class='status-badge status-light-gray']/../a");


    private SelenideElement application;
    private final WebDriver driver;


    public InsideApplicationPage(WebDriver driver){
    this.driver=driver;
    runApplicationButton.shouldBe(visible);
    actionButton.shouldBe(visible);
    }

    public InsideApplicationPage verifyPanelTitle(String appName){
        runApplicationButton.shouldBe(visible);
        waitFor("Panel Title",modelContentPanelTitle);
        assertEquals(modelContentPanelTitle.getText(),appName,"Incorrect Application Name");
        return this;
    }


    public InsideApplicationPage verifyPageTitle(String appName){
        assertEquals(title(), "Nirmata | Catalog | Applications | "+appName+" | Workloads", "Incorrect Page Title");
        return this;
    }

    public InsideApplicationPage clickActionButton(){
        click("Action Button", actionButton);
        return this;
    }

    public InsideApplicationPage clickEditApplicationButton(){
        click("Edit Application Button",editApplicationButton);
        return this;
    }

    public InsideApplicationPage clickSaveButton(){
        click("Save Button",saveButton);
        refresh();
        return this;
    }

    public InsideApplicationPage clickDeleteApplicationButton(){
        click("Delete Application Button",deleteApplicationButton);
        return this;
    }

    public InsideApplicationPage setApplicationNameIfNecessary(String applicationName){
        modalDialog.shouldBe(visible);
        if(nameInputField.exists()){
            type("Name Input Field",nameInputField,applicationName);
        }
        return this;
    }

    public InsideCatalogPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return new InsideCatalogPage(driver);
    }

    public InsideApplicationPage setNewApplicationName(String name){
        type("New Application Name",nameInputField,name);
        return this;
    }

    public InsideApplicationPage clickImportYamlButton(){
        click("Import Yaml Button",importYamlButton);
        return this;
    }

    public InsideApplicationPage setYamlFile(String yamlName){
        String filePath=absolutePathOfFile+"/resources/yaml/"+ yamlName+".yaml";
        upload("Yaml File "+yamlName+".yaml",yamlInputField,filePath);
        yaml.shouldBe(visible);
        return this;
    }

    public InsideApplicationPage verifyYamlFileText(String yamlName){
        yaml.shouldBe(visible);
        assertTrue(yaml.getText().contains(yamlName),"Incorrect yaml name");
        return this;
    }

    public InsideApplicationPage clickImportButton(){
        click("Import Button", importButton);
        importButton.should(disappear);
        return this;
    }

    public InsideApplicationPage clickExportYamlButton(){
        click("Export Yaml Button", exportYamlButton);
        return this;
    }

    public InsideApplicationPage clickEditYamlButton(){
        click("Edit Yaml Button",editYamlButton);
        return this;
    }

    public InsideApplicationPage clickCloneApplicationButton(){
        click("Clone Application Button",cloneApplicationButton);
        return this;
    }

    public InsideApplicationPage renameCloneApplication(String newCloneAppName){
        type("Clone Application Name",nameInputField,newCloneAppName);
        return this;
    }

    public InsideApplicationPage setTargetCatalogDropdown(String cloneOrMoveToCatalog){
        selectOptionByText("Target Catalog Dropdown",targetCatalogDropdown,cloneOrMoveToCatalog);
        return this;
    }

    public InsideCatalogPage clickCloneApplicationModalWindowButton(){
        click("Clone Application Modal Window Button", cloneApplicationModalWindowButton);
        cloneApplicationModalWindowButton.waitUntil(disappear,20000);
        return new InsideCatalogPage(driver);
    }

    public InsideApplicationPage clickMoveApplicationButton(){
        click("Move Application Button",moveApplicationButton);
        return this;
    }

    public InsideCatalogPage clickMoveApplicationModalWindowButton(){
        click("Move Application Modal Window Button",moveApplicationModalWindowButton);
        moveApplicationModalWindowButton.waitUntil(disappear,20000);
        return new InsideCatalogPage(driver);
    }

    public InsideApplicationPage clickDownloadYamlFileButton(){
        click("Download Yaml File Button",downloadYamlFileButton);
        return this;
    }

    public String getImportedApplicationName(String importedApplicationName){
        application=$x("//td[@class='string-url-name sortable renderable']//*[text()='"+importedApplicationName+"']");
        return application.text();
    }

    public InsideApplicationPage isImportedApplicationDisplayed(String appName){
        application=$x("//td[@class='string-url-name sortable renderable']//*[text()='"+appName+"']");
        boolean found=false;
        try{
            if (application.exists()){
                found=true;
            }
        }catch(Exception e){
            found=false;
        }
        assertTrue(found,"Application was not imported");
        return this;
    }

    public InsideApplicationPage clickDeleteDeploymentButton(String applicationName){
        SelenideElement deleteButton=$x("//*[text()='"+applicationName+"']/../..//button");
        click("Delete Deployment Button",deleteButton);
        return this;
    }

    public InsideApplicationPage clickModalDialogDeleteButton(){
        click("Modal Dialog delete Button",deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public InsideApplicationPage isRecentDeletedDeploymentDisplayed(String appName){
        SelenideElement recentDeletedDeployment=$x("//td[@class='string-url-name sortable renderable']/../../tr[contains(.,'"+appName+"')]");
        assertFalse(recentDeletedDeployment.exists(),"Deployment Was Not Deleted");
        return this;
    }

    public InsideApplicationPage clickRunApplication(){
        click("Run Application Button",runApplicationButton);
        return this;
    }

    public InsideApplicationPage setRunName(String runName){
        type("Runing Application Name",runNameInputField,runName);
        return this;
    }

    public InsideApplicationPage selectEnvironmentFromDropdown(String environmentName){
        selectOptionByText("Environment Dropdown",environmentDropdown,environmentName);
        return this;
    }

    public InsideRunningApplicationPage clickRunApplicationOnEnvironment(){
        click("Run Application On Environment Button",runApplicationOnEnvironmentButton);
        runApplicationOnEnvironmentButton.waitUntil(disappear,20000);
        return new InsideRunningApplicationPage(driver);
    }

    public InsideApplicationPage clickToViewLaunchedApplication(){
        click("Running Application Link",runningApplicationLink);
        return this;
    }

    public InsideRunningApplicationPage clickRunningApplicationLink(String deploymentName){
        SelenideElement runningApplicationLink=$x("//tbody//*[contains(text(),'"+deploymentName+"')]");
        click("Running Application"+deploymentName,runningApplicationLink);
        return new InsideRunningApplicationPage(driver);
    }

    public InsideDeploymentPage clickDeploymentLink(String deploymentName){
        SelenideElement deploymentLink=$x("//tbody//*[contains(text(),'"+deploymentName+"')]");
        click("Catalog Deployment"+deploymentName,deploymentLink);
        return new InsideDeploymentPage(driver);
    }

}
