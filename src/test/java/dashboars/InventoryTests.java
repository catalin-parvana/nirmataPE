package dashboars;

import org.testng.annotations.Test;
import pages.dashboards.InventoryPage;
import utils.NirmataSetup;

public class InventoryTests extends NirmataSetup {

    private InventoryPage inventoryPage;

    @Test(description = "Test Download PDF File")
    public void testDownloadPdfFile() {
        inventoryPage=overviewPage.clickInventory();
        inventoryPage
                .clickExportButton()
                .clickAsPdfButton();
    }

}
