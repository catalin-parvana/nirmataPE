package cluster.addOns;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

public class KubeappsTests extends NirmataSetup {
    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;

    @Test(description = "Test Install Kubeapps")
    @Parameters({"clusterName"})
    public void testInstallKubeapps(String clusterName){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickOnAddOnsButton()
                .clickAddKubeapps()
                .clickNextButton()
                .waitForDeployingLabel()
                .waitForDeployedLabel()
                .clickCloseButton()
                .waitForClusterReadyState()
                .isInstalledAddOnDisplayed("kubeapps");
    }

    @Test(description="Test Delete Kubeapps")
    @Parameters({"clusterName"})
    public void testDeleteKubeapps(String clusterName){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickOnAddOnsButton()
                .clickDeleteAddOn("kubeapps")
                .clickDeleteAddOnButton()
                .isDeletedAddOnDisplayed("kubeapps");
    }
}
