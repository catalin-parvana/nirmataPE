package pages.adminDashboard;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class InsideTenantPage  extends LibraryUtils {

    private final SelenideElement addUserButton=$x("//a[@class='add-button'] | //div[@id='addUser']");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement emailInputField=$x("//input[@id='email']");
    private final SelenideElement roleDropdown=$x("//select[@id='role']");
    private final SelenideElement addButton=$x("//button[text()='Add']");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final SelenideElement deleteButton=$x("//button[text()='Delete']");
    private final SelenideElement saveButton=$x("//button[text()='Save']");
    private SelenideElement user;
    private boolean found;
    private final WebDriver driver;

    public InsideTenantPage(WebDriver driver){
        this.driver=driver;
        modelContentPanelTitle.shouldBe(visible);
    }

    public InsideTenantPage setNameInputField(String userName){
        type("Name Input Field", nameInputField,userName);
        return this;
    }

    public InsideTenantPage setEmailInputField(String userEmail){
        type("Email Input Field", emailInputField,userEmail);
        return this;
    }

    public InsideTenantPage selectRoleFromDropdown(String roleValue){
        selectOptionByValue("Role Dropdown", roleDropdown,roleValue);
        return this;
    }

    public InsideTenantPage clickAddButton(){
        click("Add Button", addButton);
        addButton.should(disappear);
        return this;
    }

    public InsideTenantPage clickSaveButton(){
        click("Add Button", saveButton);
        saveButton.should(disappear);
        return this;
    }

    public InsideTenantPage clickAddUserButton(){
        click("Add User Button", addUserButton);
        return this;
    }

    public InsideTenantPage isUserAdmin(String userEmail){
        user=$x("//td[text()='"+userEmail+"']/..//*[text()='Administrator']");
        assertTrue(user.exists(),"User "+userEmail+" Is Not Administrator");
        return this;
    }

    public InsideTenantPage clickDeleteUserButton(String userEmail){
        SelenideElement deleteUserButton=$x("//td[text()='"+userEmail+"']/..//*[@id='deleteUser']");
        click("Delete User Button", deleteUserButton);
        return this;
    }

    public InsideTenantPage clickEditUserButton(String userEmail){
        SelenideElement deleteUserButton=$x("//td[text()='"+userEmail+"']/..//*[@id='editUser']");
        click("Delete User Button", deleteUserButton);
        return this;
    }

    public InsideTenantPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return this;
    }

    public InsideTenantPage isUserReadOnly(String userEmail){
        user=$x("//td[text()='"+userEmail+"']/..//*[text()='Read Only']");
        assertTrue(user.exists(),"User "+userEmail+" Is Not Read Only");
        return this;
    }

    public InsideTenantPage isUserDevops(String userEmail){
        user=$x("//td[text()='"+userEmail+"']/..//*[text()='Devops']");
        assertTrue(user.exists(),"User "+userEmail+" Is Not Devops");
        return this;
    }

    public InsideTenantPage isUserPlatform(String userEmail){
        user=$x("//td[text()='"+userEmail+"']/..//*[text()='Platform']");
        assertTrue(user.exists(),"User "+userEmail+" Is Not Platform");
        return this;
    }

    public InsideTenantPage isDeletedUserDisplayed(String userEmail){
        assertFalse(isUserDisplayed(userEmail),"User Was Not Deleted");
        return this;
    }

    public InsideTenantPage isCreatedUserDisplayed(String userEmail){
        assertTrue(isUserDisplayed(userEmail),"User Was Not Created");
        return this;
    }

    private boolean isUserDisplayed(String userEmail){
        addUserButton.shouldBe(visible);
        user=$x("//td[text()='"+userEmail+"']/..");

        if(user.exists()){
            found=true;
        }else
        {
            found=false;
        }
        return found;
    }
}
