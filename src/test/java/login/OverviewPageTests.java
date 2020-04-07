package login;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.annotations.Test;
import utils.NirmataSetup;

import static com.codeborne.selenide.Condition.visible;

public class OverviewPageTests  extends NirmataSetup {

    @Test(description = "Login Nirmata Test")
    public void testLogo(){
        test.log(Status.PASS, MarkupHelper.createLabel("Logo Test Started", ExtentColor.GREEN));
		overviewPage.nirmataLogo.shouldBe(visible);
		test.log(Status.PASS, "Test Logo");

    }
}
