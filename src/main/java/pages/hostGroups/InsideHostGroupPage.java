package pages.hostGroups;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.title;

public class InsideHostGroupPage extends LibraryUtils {

    private final SelenideElement pendingCreateHostGroupState=$x("//span[@id='state-popover'][contains(.,'Pending Create')]");
    private final SelenideElement shuttingDownHostGroupState=$x("//span[@id='state-popover'][contains(.,'Shutting Down')]");
    private final SelenideElement deletingHostGroupState=$x("//span[@id='state-popover'][contains(.,'Deleting')]");
    private final SelenideElement connectedHostGroupState=$x("//span[@id='state-popover'][contains(.,'Connected')]");
    private final SelenideElement modelContentPanelTitle=$x("//h1[@id='model-content-panel-title']");
    private final SelenideElement actionButton=$x("//a[@class='btn btn-default dropdown-toggle']");
    private final SelenideElement editHostGroupButton=$x("//a[@id='editHostGroup']");
    private final SelenideElement deleteHostGroupButton=$x("//a[@id='deleteHostGroup']");
    private final SelenideElement nameInputField=$x("//input[@id='name']");
    private final SelenideElement deleteButton=$x("//button[contains(.,'Delete')]");

    private final WebDriver driver;


    public InsideHostGroupPage(WebDriver driver){
        this.driver=driver;
        actionButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
    }

    public String getPanelTitle(){
//        waitFor("Content Panel Title",modelContentPanelTitle);
        return modelContentPanelTitle.text();
    }

    public String getPageTitle(){
//        actionButton.shouldBe(visible);
        return title();
    }

    public InsideHostGroupPage clickActionButton(){
        click("Action Button",actionButton);
        return this;
    }

    public InsideHostGroupPage clickEditHostGroupButton(){
        click("Edit Host Group Button",editHostGroupButton);
        return this;
    }

    public InsideHostGroupPage clickDeleteHostGroupButton(){
        click("Delete Host Group Button",deleteHostGroupButton);
        return this;
    }

    public InsideHostGroupPage setNameInputField(String hostGroupName){
        type("Name Input Field",nameInputField,hostGroupName);
        return this;
    }

    public InsideHostGroupPage clickDeleteButton(){
        click("Delete Button",deleteButton);
        return this;
    }

    public InsideHostGroupPage waitForPendingCreateHostGroupStatus(){
        actionButton.shouldBe(visible);
        waitFor("Pending Create Host Group Status",pendingCreateHostGroupState,200000);
//        waitForElementToBeInvisible(driver, pendingCreateHostGroupState,200);
        return this;
    }

    public InsideHostGroupPage waitForConnectedHostGroupStatus(){
        waitFor("Connected Host Group Status",connectedHostGroupState,200000);
        return this;
    }

    public AmazonWebServicesPage waitForShuttingDownHostGroupStatus(){
        waitFor("Shutting Down Host Group Status",shuttingDownHostGroupState,200000);
//        waitForElementToBeInvisible(driver, shuttingDownHostGroupState,200);
        return new AmazonWebServicesPage(driver);
    }

    public AmazonWebServicesPage waitForDeletingHostGroupStatus(){
        waitFor("Deleting Host Group Status",deletingHostGroupState,200000);
        return new AmazonWebServicesPage(driver);
    }

}
