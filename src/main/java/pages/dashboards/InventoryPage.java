package pages.dashboards;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import utils.LibraryUtils;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class InventoryPage extends LibraryUtils {

    private final SelenideElement exportButton=$x("//button[@id='addButton']");
    private final SelenideElement asPdfButton=$x("//a[@id='exportPdf']");
    private final SelenideElement asCsvButton=$x("//a[@id='exportCSV']");
    private final SelenideElement modelContentPanelTitle = $("//h1[contains(text(),'Inventory Report')]");


    private final WebDriver driver;



    public InventoryPage(WebDriver driver){
        this.driver=driver;
        exportButton.shouldBe(visible);
        modelContentPanelTitle.shouldBe(visible);
        assertEquals(modelContentPanelTitle.getText(), "Inventory Report", "Incorrect Panel Title");
        assertEquals(title(), "Nirmata | Inventory", "Incorrect Page Title");
    }

    public InventoryPage clickExportButton(){
        click("Export Button",exportButton);
        return this;
    }

    public InventoryPage clickAsPdfButton() {
        download("as PDF Button",asPdfButton);
        return this;
    }

    public InventoryPage clickAsCsvButton(){
        download("as CSV Button",asCsvButton);
        return this;
    }


    public PDF getPDF(String fileName) {
        PDF pdf = null;
        try {
            pdf = new PDF(new File(absolutePathOfFile+ "/resources/download/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdf;
    }

    //        PDF pdf=inventoryPage.getPDF("inventory.pdf");
//        assertThat(pdf, containsText("Totals Clusters Environments Applications Namespaces"));



}
