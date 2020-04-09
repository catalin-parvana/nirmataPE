package cluster.addOns;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.cluster.ClustersPage;
import pages.cluster.InsideClusterPage;
import utils.NirmataSetup;

public class PrometheusTests extends NirmataSetup {

    private ClustersPage clustersPage;
    private InsideClusterPage insideClusterPage;

    @Test(description = "Test Install Prometheus")
    @Parameters({"clusterName"})
    public void testInstallPrometheus(String clusterName){
        login();
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickOnAddOnsButton()
                .clickAddPrometheus()
                .clickNextButton()
                .waitForDeployingLabel()
                .waitForDeployedLabel()
                .clickCloseButton()
                .waitForClusterReadyState()
                .isInstalledAddOnDisplayed("prometheus");
    }

    @Test(description = "Test Prometheus Delete")
    @Parameters({"clusterName"})
    public void testDeletePrometheus(String clusterName){
        login();
        clustersPage=overviewPage.clickClusters();
        insideClusterPage=clustersPage.clickOnClusterWithName(clusterName);
        insideClusterPage
                .clickOnAddOnsButton()
                .clickDeleteAddOn("prometheus")
                .clickDeleteAddOnButton()
                .isDeletedAddOnDisplayed("prometheus");
    }
}
