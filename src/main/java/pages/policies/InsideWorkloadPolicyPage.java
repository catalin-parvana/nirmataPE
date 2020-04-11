package pages.policies;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

public class InsideWorkloadPolicyPage extends LibraryUtils {

    private final WebDriver driver;
    private final SelenideElement actionButton=$x("//a[@class='btn btn-default dropdown-toggle']");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final SelenideElement editPolicyButton=$x("//a[@id='editResourcePolicy']");
    private final SelenideElement deletePolicyButton=$x("//a[@id='deleteResourcePolicy']");
    private final SelenideElement deleteButton=$x("//button[text()='Delete']");

    public InsideWorkloadPolicyPage(WebDriver driver){
        this.driver=driver;
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
    }

    public InsideWorkloadPolicyPage clickActionButton(){
        click("Action Button", actionButton);
        return this;
    }

    public InsideWorkloadPolicyPage verifyModelContentPanelTitle(String workloadPolicyName){
        assertTrue(modelContentPanelTitle.getText().contains(workloadPolicyName),"Incorrect Model Content Panel Title");
        return this;
    }

    public InsideWorkloadPolicyPage clickEditPolicyButton(){
        click("Edit Policy Button", editPolicyButton);
        return this;
    }

    public InsideWorkloadPolicyPage clickDeletePolicyButton(){
        click("Delete Policy Button", deletePolicyButton);
        return this;
    }

    public WorkloadPoliciesPage clickDeleteButton(){
        click("Delete Button", deleteButton);
        deleteButton.should(disappear);
        return new WorkloadPoliciesPage(driver);
    }



}
