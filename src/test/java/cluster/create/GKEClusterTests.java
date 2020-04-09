package cluster.create;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.refresh;

public class GKEClusterTests extends NirmataSetup {

    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;


    @Test(description = "Test Create GKE Cluster")
    @Parameters({"gkeClusterName","cloudProviderName"})
    public void testCreateGKECluster(String gkeClusterName, String cloudProviderName){
        login();
        clustersPage=overviewPage.clickClusters();
        clustersPage
                .clickAddClusterButton()
                .clickCreateGKEClusterOption()
                .setInputNameForGKECluster(gkeClusterName)
                .selectGKECloudProviderFromDropdown(cloudProviderName)
                .selectGKERegionFromDropdown("us-west1-a")
                .selectGKEKubernetesVersionFromDropdown("1.15.11-gke.1")
                .setGKEDiskSpace("40")
                .clickGKENextButton()
                .selectGKEMachineTypeFromDropdown("e2-medium")
                .setGKENodeCount("1")
                .clickCreateClusterButton()
                .waitForDeployingClusterLabel()
                .waitForDeployingNirmataControllerLabel();
        insideClusterPage=clustersPage.clickViewDetailsOfMyClusterButton();
        insideClusterPage
                .waitForClusterReadyState()
                .verifyPanelTitle(gkeClusterName);
        back();
        refresh();
        clustersPage
                .isCreatedClusterDisplayed(gkeClusterName);
    }

    @Test(description = "Test Delete GKE Cluster")
    @Parameters({"gkeClusterName"})
    public void testDeleteGKECluster(String gkeClusterName){
        login();
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(gkeClusterName);
        insideClusterPage
                .clickActionButton()
                .clickDeleteClusterButton()
                .setNameInputField(gkeClusterName);
        clustersPage=insideClusterPage.clickDeleteButton()
//                .waitForReadyClusterStatus(gkeClusterName)
//                .waitForShuttingDownClusterStatus(gkeClusterName)
                .waitForDeletedClusterStatus(gkeClusterName);
        refresh();
        clustersPage
                .isDeletedClusterDisplayed(gkeClusterName);
    }

}
