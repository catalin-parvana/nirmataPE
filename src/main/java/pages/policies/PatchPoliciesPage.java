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

public class PatchPoliciesPage extends LibraryUtils {
    private WebDriver driver;
    private SelenideElement addPatchPolicyButton=$x("//button[contains(.,'Add Patch Policy')]");
    private SelenideElement nameInputField=$x("//input[@id='name']");
    private SelenideElement resourceTypeDropdown=$x("//select[@id='resourceType']");
    private SelenideElement addButton=$x("//button[text()='Add']");
    private SelenideElement deleteButton=$x("//button[text()='Delete']");


    public PatchPoliciesPage(WebDriver driver){
        this.driver=driver;
        addPatchPolicyButton.shouldBe(visible);
    }

    public PatchPoliciesPage clickAddPatchPolicyButton(){
        click("Add Patch Policy Button", addPatchPolicyButton);
        return this;
    }

    public PatchPoliciesPage setNameInputField(String name){
        type("Name Input Field", nameInputField, name);
        return this;
    }

    public PatchPoliciesPage selectFromResourceTypeDropdown(String resourceTypeValue){
        selectOptionByValue("Resource Type Dropdown", resourceTypeDropdown,resourceTypeValue); //DaemonSet
        return this;
    }

    public PatchPoliciesPage clickAddButton(){
        click("Add Button", addButton);
        addButton.should(disappear);
        return this;
    }

    public PatchPoliciesPage clickDeletePatchPolicyButton(String patchPolicyName){
        SelenideElement deletePatchPolicyButton=$x("(//*[text()='"+patchPolicyName+"']/../..//button)[2]");
        click("Delete Patch Policy Button",deletePatchPolicyButton);
        return this;
    }

    public PatchPoliciesPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public PatchPoliciesPage isCreatedPatchPolicyDisplayed(String patchPolicyName){
        assertTrue(isPatchPolicyDisplayed(patchPolicyName),"Patch Policy Was Not Created");
        return this;
    }

    public PatchPoliciesPage isDeletedPatchPolicyDisplayed(String patchPolicyName){
        assertFalse(isPatchPolicyDisplayed(patchPolicyName),"Patch Policy Was Not Deleted");
        return this;
    }

    private boolean isPatchPolicyDisplayed(String patchPolicyName) {
        addPatchPolicyButton.shouldBe(visible);
        boolean found = false;
        SelenideElement patchPolicy = $x("//*[text()='"+patchPolicyName+"']/../..");

        if (patchPolicy.exists()) {
            found = true;
        }
        return found;
    }
}
