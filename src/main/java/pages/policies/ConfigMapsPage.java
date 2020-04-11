package pages.policies;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ConfigMapsPage extends LibraryUtils {
    private final WebDriver driver;
    private final SelenideElement addConfigMapPolicyButton=$x("//button[contains(.,'Add Config Map Policy')]");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement applicationSelectorDropdown=$x("//select[@id='applicationSelector-type']");
    private final SelenideElement nextButton=$x("//button[text()='Next']");
    private final SelenideElement finishButton=$x("//button[text()='Finish']");
    private final SelenideElement deleteButton=$x("//button[text()='Delete']");

    public ConfigMapsPage(WebDriver driver){
        this.driver=driver;
        addConfigMapPolicyButton.shouldBe(visible);
    }

    public ConfigMapsPage clickAddConfigMapPolicyButton(){
        click("Add Config Map Policy Button",addConfigMapPolicyButton);
        return this;
    }

    public ConfigMapsPage setNameInputField(String name){
        type("Name Input Field", nameInputField, name);
        return this;
    }

    public ConfigMapsPage selectFromApplicationSelectorDropdown(String applicationSelectorValue){
        selectOptionByValue("Application Selector Dropdown", applicationSelectorDropdown,applicationSelectorValue);
        return this;
    }

    public ConfigMapsPage clickDeleteConfigMapButton(String configMapName){
        SelenideElement deleteConfigMapButton=$x("(//*[text()='"+configMapName+"']/..//button)[2]");
        click("Delete Config Map Button",deleteConfigMapButton);
        return this;
    }

    public ConfigMapsPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public ConfigMapsPage clickNextButton(){
        click("Next Button", nextButton);
        return this;
    }

    public ConfigMapsPage clickFinishButton(){
        click("Finish Button", finishButton);
        finishButton.should(disappear);
        return this;
    }

    public ConfigMapsPage isCreatedConfigMapDisplayed(String configMapName){
        assertTrue(isConfigMapDisplayed(configMapName),"Config Map Was Not Created");
        return this;
    }

    public ConfigMapsPage isDeletedConfigMapDisplayed(String configMapName){
        assertFalse(isConfigMapDisplayed(configMapName),"Config Map Was Not Deleted");
        return this;
    }

    private boolean isConfigMapDisplayed(String configMapName) {
        addConfigMapPolicyButton.shouldBe(visible);
        boolean found = false;
        SelenideElement configMap = $x("//td[text()='"+configMapName+"']/..");

        if (configMap.exists()) {
            found = true;
        }
        return found;
    }
}
