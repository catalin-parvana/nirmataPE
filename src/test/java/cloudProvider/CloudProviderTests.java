package cloudProvider;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cloudProviders.CloudProvidersPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.refresh;


public class CloudProviderTests extends NirmataSetup {

    private CloudProvidersPage cloudProvidersPage;

    @Test(description = "Test Add AWS Cloud Provider")
    @Parameters({"cloudProviderName"})
    public void testAddAwsCloudProvider(String cloudProviderName){
        login();
        cloudProvidersPage=overviewPage.clickCloudProviders();
        cloudProvidersPage
                .clickAddCloudProviderButton()
                .setNameInputField(cloudProviderName)
                .selectTypeFromDropdown("AWS")
                .clickNextButton()
                .selectRegionFromDropdown("us-west-2")
//                .setRoleArnInputField("arn:aws:iam::094919933512:role/NirmataV2")
                .setRoleArnInputField("arn:aws:iam::094919933512:role/eks-test-role")
                .clickNextButton()
                .waitForSuccessProviderStatus()
                .clickFinishButton()
                .isCreatedCloudProviderDisplayed(cloudProviderName);
    }

    @Test(description = "Test Add GCP Cloud Provider")
    @Parameters({"cloudProviderName"})
    public void testAddGcpCloudProvider(String cloudProviderName){
        login();
        cloudProvidersPage=overviewPage.clickCloudProviders();
        cloudProvidersPage
                .clickAddCloudProviderButton()
                .setNameInputField(cloudProviderName)
                .selectTypeFromDropdown("GoogleCloudPlatform")
                .clickNextButton()
                .uploadGcpServiceAccountKey("nirmata-gcp-key")
                .verifyApiKeyDropBoxMessage()
                .clickNextButton()
                .waitForSuccessProviderStatus()
                .clickFinishButton()
                .isCreatedCloudProviderDisplayed(cloudProviderName);
    }

    @Test(description = "Test Add AKS Cloud Provider")
    @Parameters({"cloudProviderName"})
    public void testAddAksCloudProvider(String cloudProviderName){
        login();
        cloudProvidersPage=overviewPage.clickCloudProviders();
        cloudProvidersPage
                .clickAddCloudProviderButton()
                .setNameInputField(cloudProviderName)
                .selectTypeFromDropdown("Azure")
                .clickNextButton()
                .setSubscriptionIdField("baf89069-e8f3-46f8-b74e-c146931ce7a4")
                .setTenantIdInputField("3d95acd6-b6ee-428e-a7a0-196120fc3c65")
                .setClientIdInputField("8612edd1-29cf-4ee3-9db7-7c215cf43b2f")
                .setClientSecretInputField("vaSgLEI783lZYMjyEaGBRi/dXlKPUk08rhpWIlCi/NCfazt/+VWSzyawQHW7jTTLLy9A/UWDoIeIcDwGpZ7I1g==")
                .clickNextButton()
                .waitForSuccessProviderStatus()
                .clickFinishButton()
                .isCreatedCloudProviderDisplayed(cloudProviderName);
    }

    @Test(description = "Test Delete Cloud Provider")
    @Parameters({"cloudProviderName"})
    public void testDeleteCloudProvider(String cloudProviderName){
        login();
        cloudProvidersPage=overviewPage.clickCloudProviders();
        cloudProvidersPage
                .hoverCloudProviderAndClickDeleteButton(cloudProviderName)
                .clickDeleteButton();
        refresh();
        cloudProvidersPage
                .isDeletedCloudProviderDisplayed(cloudProviderName);
    }
}
