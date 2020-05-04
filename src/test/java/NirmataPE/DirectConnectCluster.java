package NirmataPE;

import com.aventstack.extentreports.Status;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.hostGroups.DirectConnectHostGroupsPage;
import pages.cluster.ClustersPage;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DirectConnectCluster extends NirmataSetup {

    private DirectConnectHostGroupsPage directConnectHostGroupsPage;
    private ClustersPage clustersPage;
    private String hostAgentKey;
    private PropertiesConfiguration config;
  //  private final SelenideElement hostAgentLink=$x("//div[contains(text(),'sudo curl')]");
  private final SelenideElement hostAgentLink=$x("//p[contains(text(),'Install the Nirmata Host Agent using the key')]//strong");

    @Test(description = "Add Host Group ")
    @Parameters({"dcHostGroupName"})
    public void addHostGroup(String dcHostGroupName) {

        login();
        directConnectHostGroupsPage=overviewPage.clickHostGroups()
        .clickAddHostGroupButton()
        .setNameInputField(dcHostGroupName)
        .clickFinishButton()
        .isCreatedHostGroupDisplayed(dcHostGroupName);
        hostAgentKey=hostAgentLink.shouldBe(visible).getText();


        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("hostAgentKey",hostAgentKey);

            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("hostAgentKey= "+hostAgentKey);


    }

    @Test(description = "Create New Cluster ")
    @Parameters({"clusterName","dcHostGroupName","clusterPolicyName"})
    public void addDirectConnectCluster(String clusterName, String dcHostGroupName, String clusterPolicyName) {
        login();
        clustersPage=overviewPage.clickClusters();
        clustersPage.clickCreateClusterButton()
                .clickInstallNewKubernetesClusterOption()
                .setInputNameForInstallAndManageCluster(clusterName)
                .selectHostGroupFromDropdown(dcHostGroupName)
                .selectClusterPolicyFromDropdown(clusterPolicyName)
                .clickInstallationButton()
        ;


    }
}
