package cluster.create;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;
import static org.testng.Assert.assertEquals;

public class ManagedClusterTests extends NirmataSetup {
    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;

    @Test(description = "Test Cluster Page Title")
    public void testClustersPageTitle(){
        clustersPage=overviewPage.clickClusters();
        String pageTitle=clustersPage.getPageTitle();
        assertEquals(pageTitle, "Nirmata | Clusters", "Incorrect Page Title");
    }

    @Test(description = "Test Cluster Page Panel Title")
    public void testClustersPagePanelTitle(){
        clustersPage=overviewPage.clickClusters();
        String panelTitle=clustersPage.getPanelTitle();
        assertEquals(panelTitle, "Clusters", "Incorrect Panel Title");
    }

    @Test(description = "Test Install And Manage New Kubernetes Cluster")
    @Parameters({ "awsHostGroupName", "clusterName","clusterPolicyName"})
    public void testInstallAndManageNewKubernetesCluster(String awsHostGroupName,String clusterName,String clusterPolicyName){
        clustersPage=overviewPage.clickClusters();
        clustersPage
                .clickAddClusterButton()
                .clickInstallNewKubernetesClusterOption()
                .setInputNameForInstallAndManageCluster(clusterName)
                .selectHostGroupFromDropdown(awsHostGroupName)
                .selectClusterPolicyFromDropdown(clusterPolicyName)
                .clickCreateClusterAndStartInstallationButton()
                .waitForRunningPreInstallChecksLabel()
                .waitForPreparingInstallLabel()
                .waitForDeployingClusterLabel()
                .waitForWaitingForControllerConnectLabel();
        insideClusterPage=clustersPage.clickViewDetailsOfMyClusterButton();
        insideClusterPage
                .waitForClusterReadyState()
                .verifyPanelTitle(clusterName);
        back();
        refresh();
        clustersPage
                .isCreatedClusterDisplayed(clusterName);
    }

    @Test(description = "Test Delete Managed Cluster")
    @Parameters({"clusterName"})
    public void testDeleteManagedCluster(String clusterName){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickActionButton()
                .clickDeleteClusterButton()
                .setNameInputField(clusterName);
        clustersPage=insideClusterPage.clickDeleteButton();
        clustersPage
                .waitForReadyClusterStatus(clusterName)
                .waitForShuttingDownClusterStatus(clusterName)
                .waitForDeletedClusterStatus(clusterName)
                .isDeletedClusterDisplayed(clusterName);
    }

}
