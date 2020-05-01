package pages.imageRegistries;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.*;

public class ImageRegistriesPage extends LibraryUtils {

    private final SelenideElement addImageRegistryButton=$x("//button[contains(.,'Add Image Registry')]");
    private final SelenideElement registryProviderDropdown=$x("//select[@id='provider']");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement locationInputField=$x("//input[@id='location']");
    private final SelenideElement usernameInputField=$x("//input[@id='username']");
    private final SelenideElement passwordInputField=$x("//input[@id='password']");
//  private final SelenideElement preferredRegistryCheckbox=$x("//ins[@class='iCheck-helper']");
    private final SelenideElement preferredRegistryCheckbox=$x("//div[@class='icheckbox_square-blue']");
    private final SelenideElement privateCloudDropdown=$x("//select[@id='privateCloud']");
    private final SelenideElement cancelButton=$x("//button[text()='Cancel']");
    private final SelenideElement addButton=$x("//button[text()='Add']");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final WebDriver driver;

    public ImageRegistriesPage(WebDriver driver){
        this.driver=driver;
        addImageRegistryButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "Image Registries", "Incorrect Panel Title");
//        assertEquals(title(), "Nirmata | Image Registries", "Incorrect Page Title");
    }

    public ImageRegistriesPage clickAddImageRegistryButton(){
        click("Add Image Registry Button",addImageRegistryButton);
        return this;
    }

    public ImageRegistriesPage selectFromRegistryProviderDropdown(String registryProvider){
        selectOptionByValue("Image Registry Provider Dropdown",registryProviderDropdown,registryProvider);
        return this;
    }

    public ImageRegistriesPage setNameInputField(String name){
        type("Name Input Field",nameInputField,name);
        return this;
    }

    public ImageRegistriesPage setLocationInputField(String location){
        type("Location Input Field",locationInputField,location);
        return this;
    }

    public ImageRegistriesPage setUsernameInputField(String username){
        type("Username Input Field",usernameInputField,username);
        return this;
    }

    public ImageRegistriesPage setPasswordInputField(String password){
        type("Password Input Field",passwordInputField,password);
        return this;
    }

    public ImageRegistriesPage checkPreferredRegistryCheckbox(){
        click("Preferred Registry Checkbox",preferredRegistryCheckbox);
        return this;
    }

    public ImageRegistriesPage selectFromPrivateCloudDropdown(String privateCloud){
        selectOptionByText("Private Cloud Dropdown",privateCloudDropdown,privateCloud);
        return this;
    }

    public ImageRegistriesPage clickCancelButton(){
        click("Cancel Button",cancelButton);
        return this;
    }

    public ImageRegistriesPage clickAddButton(){
        click("Add Button",addButton);
        addButton.should(disappear);
        return this;
    }

    public ImageRegistriesPage isCreatedRegistryDisplayed(String registryName){
        SelenideElement registry=$x("//*[contains(@class,'card-content card-select')]//*[text()='"+registryName+"']");
        waitFor("Image Registry "+registryName,registry);
        assertTrue(registry.exists(),"Image Registry was not created");
        return this;
    }

    public ImageRegistriesPage isDeletedRegistryDisplayed(String registryName){
        SelenideElement registry=$x("//*[contains(@class,'card-content card-select')]//*[text()='"+registryName+"']");
        assertFalse(registry.exists(),"Image Registry was not deleted");
        return this;
    }

    public InsideImageRegistryPage clickOnImageRegistry(String registryName){
        SelenideElement registry=$x("//*[contains(@class,'card-content card-select')]//*[text()='"+registryName+"']");
        click("Image Registry "+registryName,registry);
        return new InsideImageRegistryPage(driver);
    }
}
