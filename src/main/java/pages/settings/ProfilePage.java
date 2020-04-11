package pages.settings;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.LibraryUtils;
import utils.NirmataApplicationProperties;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProfilePage extends LibraryUtils {

    private final SelenideElement modelContentPanelTitle = $x("//*[@id='model-content-panel-title']");
    private final SelenideElement copyApiKeyButton = $x("//button[contains(.,'Copy API Key')]");
    private final SelenideElement generateNewApiKeyButton = $x("//button[contains(.,'Generate a new API Key')]");
    private final SelenideElement changePasswordButton = $x("//button[contains(.,'Change Password')]");
    private final SelenideElement downloadKubeconfigFileButton = $x("//button[contains(.,'Download Kubeconfig File')]");
    private final SelenideElement passwordInputField = $x("//input[@id='password']");
    private final SelenideElement generateButton = $x("//*[@class='bootstrap-dialog-footer']//button[contains(.,'Generate')]");
    private final SelenideElement currentPassword = $x("//input[@id='currentPassword']");
    private final SelenideElement newPassword = $x("//input[@id='newPassword']");
    private final SelenideElement confirmNewPassword = $x("//input[@id='newPassword2']");
    private final SelenideElement changePasswordModalWindowButton = $x("//*[@class='modal-footer']//button[contains(.,'Change Password')]");
    private final SelenideElement modalWindowMessage = $x("//div[@class='bootstrap-dialog-message']");
    private final SelenideElement okButton = $x("//*[@class='modal-footer']//button[contains(.,'Ok')]");
    private final SelenideElement downloadButton = $x("//*[@class='modal-footer']//button[contains(.,'Download')]");
//    private NirmataApplicationProperties appProperties = new NirmataApplicationProperties();
private final WebDriver driver;
    private String copiedText;




    public ProfilePage(WebDriver driver){
        this.driver=driver;
        copyApiKeyButton.shouldBe(visible);
        generateNewApiKeyButton.shouldBe(visible);
        changePasswordButton.shouldBe(visible);
        downloadKubeconfigFileButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
//        assertEquals(modelContentPanelTitle.getText(), appProperties.properties.getProperty("email"), "Incorrect Panel Title");
        assertEquals(title(), "Nirmata | Profile", "Incorrect Page Title");
    }

    public String getPanelTitle(){
        waitFor("Panel Title",modelContentPanelTitle);
        return modelContentPanelTitle.getText();
    }

    public String getPageTitle(){
        return title();
    }

    public ProfilePage clickCopyApiKeyButton(){
        click("Copy API Key Button",copyApiKeyButton);
        return this;
    }

    public ProfilePage acceptAlert(String expectedAlertText){
        confirm(expectedAlertText);
        return this;
    }

    public ProfilePage verifyCopiedApiKey(){
        try {
            copiedText=(String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("String from Clipboard: " + copiedText);
        assertTrue(!copiedText.equals("return")&&(copiedText!=null),"API Key was not copied");
        return this;
    }

    public ProfilePage clickGenerateNewApiKeyButton(){
        click("Generate New API Key Button",generateNewApiKeyButton);
        return this;
    }

    public ProfilePage setPasswordInputField(String password){
        type("Password Input Field",passwordInputField,password);
        return this;
    }

    public ProfilePage clickGenerateButton(){
        click("Generate Button",generateButton);
        generateButton.should(disappear);
        return this;
    }

    public ProfilePage clickChangePasswordButton(){
        click("Change Password Button",changePasswordButton);
        return this;
    }

    public ProfilePage setCurrentPassword(String password){
        type("Current Password Input Field",currentPassword,password);
        return this;
    }

    public ProfilePage setNewPassword(String password){
        type("New Password Input Field",newPassword,password);
        return this;
    }

    public ProfilePage setConfirmNewPassword(String password){
        type("Confirm New Password Input Field",confirmNewPassword,password);
        return this;
    }

    public ProfilePage clickChangePasswordModalWindowButton(){
        click("Change Password Modal Window Button",changePasswordModalWindowButton);
        return this;
    }

    public ProfilePage verifyPasswordChangedMessage(){
        okButton.shouldBe(visible);
        modalWindowMessage.shouldBe(visible);
        assertEquals(modalWindowMessage.text(), "Your password has been changed. Click Ok to log in.",
                "Incorrect Change Password Message");
        return this;
    }

    public LoginPage clickOkButton(){
        click("Ok Button",okButton);
        return new LoginPage(driver);
    }

    public ProfilePage clickDownloadKubeconfigFileButton(){
        click("Download Kubeconfig File Button",downloadKubeconfigFileButton);
        return this;
    }

    public ProfilePage clickDownloadButton() {
        download("Download Button",downloadButton);
        downloadButton.should(disappear);
        return this;
    }


}
