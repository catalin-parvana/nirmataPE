package pages.policies;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class EnvironmentTypesPage extends LibraryUtils {
    private WebDriver driver;
    private SelenideElement addEnvironmentTypeButton=$x("//button[contains(.,'Add Environment Type')]");
    private SelenideElement nameInputField=$x("//input[@id='name']");
    private SelenideElement resourceLimitsKeyInputField=$x("(//input[@placeholder='key'])[1]");
    private SelenideElement resourceLimitsValueInputField=$x("(//input[@placeholder='value'])[1]");
    private SelenideElement addItemButton=$x("//div[@class='btn btn-info']");
    private SelenideElement isDefaultCheckbox=$x("//ins[@class='iCheck-helper']");
    private SelenideElement addButton=$x("//button[text()='Add']");
    private SelenideElement deleteButton=$x("//button[text()='Delete']");


    public EnvironmentTypesPage(WebDriver driver){
        this.driver=driver;
        addEnvironmentTypeButton.shouldBe(visible);
    }

    public EnvironmentTypesPage clickAddEnvironmentTypeButton(){
        click("Add Environment Type Button",addEnvironmentTypeButton);
        return this;
    }

    public EnvironmentTypesPage clickAddItemButton(){
        click("Add Item Button",addItemButton);
        return this;
    }

    public EnvironmentTypesPage clickIsDefaultCheckbox(){
        click("Is Default Checkbox",isDefaultCheckbox);
        return this;
    }

    public EnvironmentTypesPage clickAddButton(){
        click("Add Button",addButton);
        addButton.should(disappear);
        return this;
    }

    public EnvironmentTypesPage setNameInputField(String environmentTypeName){
        type("Environment Type Input Field",nameInputField,environmentTypeName);
        return this;
    }

    public EnvironmentTypesPage setResourceLimitsKeyInputField(String resourceLimitsKey){
        type("Resource Limits Key Input Field",resourceLimitsKeyInputField,resourceLimitsKey);
        return this;
    }

    public EnvironmentTypesPage setResourceLimitsValueInputField(String resourceLimitsValue){
        type("Resource Limits Value Input Field",resourceLimitsValueInputField,resourceLimitsValue);
        return this;
    }

    public EnvironmentTypesPage isCreatedEnvironmentTypeDisplayed(String environmentTypeName){
        assertTrue(isEnvironmentTypeDisplayed(environmentTypeName),"Environment Type Was Not Created");
        return this;
    }

    public EnvironmentTypesPage isDeletedEnvironmentTypeDisplayed(String environmentTypeName){
        assertFalse(isEnvironmentTypeDisplayed(environmentTypeName),"Environment Type Was Not Deleted");
        return this;
    }

    private boolean isEnvironmentTypeDisplayed(String environmentTypeName) {
        addEnvironmentTypeButton.shouldBe(visible);
        boolean found = false;
        SelenideElement environmentType = $x("//td[text()='"+environmentTypeName+"']/..");

        if (environmentType.exists()) {
            found = true;
        }
        return found;
    }

    public EnvironmentTypesPage clickDeleteEnvironmentType(String environmentTypeName){
        SelenideElement deleteEnvironmentType=$x("//td[text()='"+environmentTypeName+"']/..//*[@id='deleteResourceType']");
        click("Delete Environment Type Button",deleteEnvironmentType);
        return this;
    }

    public EnvironmentTypesPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        deleteButton.should(disappear);
        return this;
    }
}
