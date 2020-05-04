package pages.hostGroups;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;
import utils.NirmataApplicationProperties;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

public class DirectConnectHostGroupsPage extends LibraryUtils {

    private final WebDriver driver;
    private SelenideElement hostGroup;
    private final SelenideElement addHostGroupButton= $x("//*[@id='addHostGroup']");
    private final SelenideElement modelContentPanelTitle= $x("//h1[@id='model-content-panel-title']");
    private final SelenideElement nameInputField= $x("//input[@id='name']");
    private final SelenideElement addFinishButton= $x( "//button[text()='Finish']");
    private final SelenideElement toggleCardView=$x("//img[@id='toggleCardView'][contains(@style,'block')]");
    private final SelenideElement toggleTableView=$x("//img[@id='toggleTableView'][contains(@style,'block')]");
    private final SelenideElement hostAgentLink=$x("//div[contains(text(),'sudo curl')]");


    public DirectConnectHostGroupsPage(WebDriver driver) {
        this.driver = driver;
        addHostGroupButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
    }

    public DirectConnectHostGroupsPage clickAddHostGroupButton(){
        click("Add Host Group Button",addHostGroupButton);
        return this;
    }

    public DirectConnectHostGroupsPage setNameInputField(String hostGroupName){
        type("Name Input Field",nameInputField,hostGroupName);
        return this;
    }

    public DirectConnectHostGroupsPage clickFinishButton(){
        click("Click Finish Button",addFinishButton);
        return this;
    }

    public DirectConnectHostGroupsPage isCreatedHostGroupDisplayed(String hostGroupName) {
        addHostGroupButton.shouldBe(visible);
        if (toggleTableView.exists()) {
            hostGroup = $x("//div[@class='page-header'][contains(.,'" + hostGroupName + "')]");
        } else if (toggleCardView.exists()) {
            hostGroup = $x("//*[contains(@class,'card-content card-select')]//*[text()='" + hostGroupName + "']");
        }

        return this;
    }

    public DirectConnectHostGroupsPage isDownloadLinkVisible(){

        hostAgentLink.shouldBe(visible);

        return this;
    }
}
