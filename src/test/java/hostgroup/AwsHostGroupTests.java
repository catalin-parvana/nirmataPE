package hostgroup;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.hostGroups.AmazonWebServicesPage;
import pages.hostGroups.InsideHostGroupPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;


public class AwsHostGroupTests extends NirmataSetup {
    private AmazonWebServicesPage amazonWebServicesPage;
    private InsideHostGroupPage insideHostGroupPage;

    @Test(description = "Test Add Host Group")
    @Parameters({"awsHostGroupName"})
    public void testAddHostGroup(String awsHostGroupName){
        login();
  //      amazonWebServicesPage=overviewPage.clickHostGroups();
        amazonWebServicesPage
                .clickAddHostGroupButton()
                .setNameInputField(awsHostGroupName)
                .selectCloudProviderFromDropdown("regression-aws-cloud-provider")
                .clickNextButton()
                .selectRegionFromDropdown("us-west-1")
                .selectHostInstanceFromDropdown("ami")
                .setAmazonMachineImageID("ami-063aa838bd7631e0b")
                .selectInstanceTypeFromDropdown("t2.large")
                .selectSshKeyPairFromDropdown("nirmata-west-1-062014")
                .scrollDown()
                .selectSecurityGroupFromDropdown("sg-1a38d97f")
                .selectNetworkFromDropdown("subnet-7681a130")
                .selectIamRoleFromDropdown("aws-k8s-role")
                .clickNextButton()
                .clickNextButton()
                .selectUserDataTemplateFromDropdown("ubuntu")
                .clickFinishButton()
                .isCreatedHostGroupDisplayed(awsHostGroupName);
        insideHostGroupPage=amazonWebServicesPage.clickOnHostGroupWithName(awsHostGroupName);
        insideHostGroupPage
                .waitForPendingCreateHostGroupStatus()
                .waitForConnectedHostGroupStatus();
        back();
        refresh();
        amazonWebServicesPage
                .isCreatedHostGroupDisplayed(awsHostGroupName);

    }


    @Test(description = "Test Delete Host Group")
    @Parameters({ "awsHostGroupName"})
    public void testDeleteHostGroup(String awsHostGroupName){
        login();
    //    amazonWebServicesPage=overviewPage.clickHostGroups();
        insideHostGroupPage=amazonWebServicesPage.clickOnHostGroupWithName(awsHostGroupName);
        insideHostGroupPage
                .clickActionButton()
                .clickDeleteHostGroupButton()
                .setNameInputField(awsHostGroupName)
                .clickDeleteButton();
        amazonWebServicesPage=insideHostGroupPage.waitForShuttingDownHostGroupStatus();
        refresh();
        amazonWebServicesPage
                .isDeletedHostGroupDisplayed(awsHostGroupName);
    }
}
