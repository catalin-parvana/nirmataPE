package pages.policies;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SecretsPage extends LibraryUtils {

    private WebDriver driver;
    private SelenideElement addSecretPolicyButton=$x("//button[contains(.,'Add Secret Policy')]");
    private SelenideElement nameInputField=$x("//input[@id='name']");
    private SelenideElement secretsManagerDropdown=$x("//select[@id='secretsManager']");
    private SelenideElement nextButton=$x("//button[text()='Next']");
    private SelenideElement applicationSelectorDropdown=$x("//select[@id='applicationSelector-type']");
    private SelenideElement typeDropdown=$x("//select[@id='type']");
    private SelenideElement keyDataInputField=$x("//input[@placeholder='key']");
    private SelenideElement valueDataInputField=$x("//input[@placeholder='value']");
    private SelenideElement addItemButton=$x("//div[contains(text(),'Add item')]");
    private SelenideElement showValueCheckbox=$x("//input[@type='checkbox']");
    private SelenideElement finishButton=$x("//button[text()='Finish']");
    private SelenideElement deleteButton=$x("//button[text()='Delete']");

    public SecretsPage(WebDriver driver){
        this.driver=driver;
        addSecretPolicyButton.shouldBe(visible);
    }

    public SecretsPage clickAddSecretPolicyButton(){
        click("Add Secret Policy Button", addSecretPolicyButton);
        return this;
    }

    public SecretsPage setNameInputField(String secretName){
        type("Name Input Field", nameInputField, secretName);
        return this;
    }

    public SecretsPage selectFromSecretsManagerDropdown(String secretsManagerValue){
        selectOptionByValue("Secrets Manager Dropdown", secretsManagerDropdown, secretsManagerValue);
        return this;
    }

    public SecretsPage selectFromApplicationSelectorDropdown(String applicationSelectorValue){
        selectOptionByValue("Application Selector Dropdown", applicationSelectorDropdown, applicationSelectorValue);
        return this;
    }

    public SecretsPage selectFromTypeDropdown(String typeValue){
        selectOptionByValue("Type Dropdown", typeDropdown, typeValue);
        return this;
    }

    public SecretsPage clickNextButton(){
        click("Next Button", nextButton);
        return this;
    }

    public SecretsPage clickFinishButton(){
        click("Finish Button", finishButton);
        finishButton.should(disappear);
        return this;
    }

    public SecretsPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public SecretsPage isCreatedSecretPolicyDisplayed(String secretPolicyName){
        assertTrue(isSecretPolicyDisplayed(secretPolicyName),"Secret Policy Was Not Created");
        return this;
    }

    public SecretsPage isDeletedSecretPolicyDisplayed(String secretPolicyName){
        assertFalse(isSecretPolicyDisplayed(secretPolicyName),"Secret Policy Was Not Deleted");
        return this;
    }

    private boolean isSecretPolicyDisplayed(String secretPolicyName) {
        addSecretPolicyButton.shouldBe(visible);
        boolean found = false;
        SelenideElement secretPolicy = $x("//*[text()='"+secretPolicyName+"']/..");

        if (secretPolicy.exists()) {
            found = true;
        }
        return found;
    }

    public SecretsPage clickDeleteSecretPolicyButton(String secretPolicyName){
        SelenideElement deleteSecretPolicyButton=$x("(//*[text()='"+secretPolicyName+"']/..//button)[2]");
        click("Delete Secret Policy Button",deleteSecretPolicyButton);
        return this;
    }

    public SecretsPage clickEditSecretPolicyButton(String secretPolicyName){
        SelenideElement editSecretPolicyButton=$x("(//*[text()='"+secretPolicyName+"']/..//button)[1]");
        click("Edit Secret Policy Button",editSecretPolicyButton);
        return this;
    }


}
