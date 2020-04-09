package settings;

import org.testng.annotations.Test;
import pages.settings.ProfilePage;
import utils.NirmataSetup;

public class ProfilePageTests extends NirmataSetup {


    private ProfilePage profilePage;
    private String password=appProperties.properties.getProperty("password");
    private String email=appProperties.properties.getProperty("email");



    @Test(description = "Test Copy API Key")
    public void testCopyApiKey(){
        login();
        profilePage=overviewPage.clickSettings();
        profilePage
                .clickCopyApiKeyButton()
                .acceptAlert("API key has been copied to clipboard")
                .verifyCopiedApiKey();
    }

    @Test(description = "Test Generate API Key")
    public void testGenerateApiKey(){
        login();
        profilePage=overviewPage.clickSettings();
        profilePage
                .clickGenerateNewApiKeyButton()
                .setPasswordInputField(password)
                .clickGenerateButton()
                .clickCopyApiKeyButton()
                .acceptAlert("API key has been copied to clipboard")
                .verifyCopiedApiKey();

    }

    @Test(description = "Test Change Password")
    public void testChangePassword(){
        login();
        profilePage=overviewPage.clickSettings();
        profilePage
                .clickChangePasswordButton()
                .setCurrentPassword(password)
                .setNewPassword(password)
                .setConfirmNewPassword(password)
                .clickChangePasswordModalWindowButton()
                .verifyPasswordChangedMessage();
        loginPage=profilePage.clickOkButton();
        loginPage
                .setEmailInputField(email)
                .setPasswordInputField(password);
        overviewPage=loginPage.clickLoginButton();
    }


    @Test(description = "Test Download Kubeconfig File")
    public void testDownloadKubeconfigFile()  {
        login();
        profilePage=overviewPage.clickSettings();
        profilePage
                .clickDownloadKubeconfigFileButton()
                .clickDownloadButton();
    }
}
