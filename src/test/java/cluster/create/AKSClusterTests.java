package cluster.create;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

public class AKSClusterTests extends NirmataSetup {
    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;


    @Test(description = "Test Create AKS Cluster")
    @Parameters({"aksClusterName","cloudProviderName"})
    public void testCreateAKSCluster(String aksClusterName, String cloudProviderName){
        login();
        clustersPage=overviewPage.clickClusters();
        clustersPage
                .clickAddClusterButton()
                .scrollToAKSClusterOption()
                .clickCreateAKSClusterOption()
                .setInputNameForAKSCluster(aksClusterName)
                .selectAKSCloudProviderFromDropdown(cloudProviderName)
                .selectAKSRegionFromDropdown("centralus")
                .selectAKSKubernetesVersionFromDropdown("1.17.3")
                .selectAKSResourceGroupFromDropdown("nirmata-test")
                .clickAKSNextButton()
                .selectAKSNetworkConfigurationFromDropdown("basic")
                .selectAKSClusterSubnetFromDropdown("azure-test | default (192.168.0.0/16)")
                .clickAKSNextButton()
                .clickAKSNextButton()
                .selectAKSVMSetTypeFromDropdown("AvailabilitySet")
                .selectAKSNodeTypeFromDropdown("Standard_DS2")
                .setAKSDiskSize("30")
                .setAKSNodeCount("1")
                .clickCreateClusterButton()
                .waitForDeployingClusterLabel()
                .waitForDeployingNirmataControllerLabel();
        insideClusterPage=clustersPage.clickViewDetailsOfMyClusterButton();
        insideClusterPage
                .waitForClusterReadyState()
                .verifyPanelTitle(aksClusterName);
        back();
        refresh();
        clustersPage
                .isCreatedClusterDisplayed(aksClusterName);
    }

    @Test(description = "Test Delete AKS Cluster")
    @Parameters({"aksClusterName"})
    public void testDeleteAKSCluster(String aksClusterName){
        login();
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(aksClusterName);
        insideClusterPage
                .clickActionButton()
                .clickDeleteClusterButton()
                .setNameInputField(aksClusterName);
        clustersPage=insideClusterPage.clickDeleteButton();
        clustersPage
//                .waitForUnknownClusterStatus(aksClusterName)
                .waitForDeletedClusterStatus(aksClusterName);
        refresh();
        clustersPage
                .isDeletedClusterDisplayed(aksClusterName);
    }
}
