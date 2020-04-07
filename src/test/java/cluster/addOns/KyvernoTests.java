package cluster.addOns;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

public class KyvernoTests extends NirmataSetup {
    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;

    @Test(description = "Test Install Kyverno")
    @Parameters({"clusterName"})
    public void testInstallKyverno(String clusterName){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickOnAddOnsButton()
                .clickAddKyverno()
                .clickNextButton()
                .waitForDeployingLabel()
                .waitForDeployedLabel()
                .clickCloseButton()
                .waitForClusterReadyState()
                .isInstalledAddOnDisplayed("kyverno");
    }

    @Test(description = "Test Delete Kyverno")
    @Parameters({"clusterName"})
    public void testDeleteKyverno(String clusterName){
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickOnAddOnsButton()
                .clickDeleteAddOn("kyverno")
                .clickDeleteAddOnButton()
                .isDeletedAddOnDisplayed("kyverno");
    }
}
