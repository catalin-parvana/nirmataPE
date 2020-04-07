package NirmataPE;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.NirmataSetup;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class EC2Instance  extends NirmataSetup {

    private String awsAccount = appProperties.properties.getProperty("awsAccount");
    private String awsUsername = appProperties.properties.getProperty("awsUsername");
    private String awsPassword = appProperties.properties.getProperty("awsPassword");
    private String instanceIP = appProperties.properties.getProperty("instanceIP");
    private String ip;
    private PropertiesConfiguration config;

    @Test(description = "Test Create EC2 Instance From Template")
    @Parameters({"templateName"})
    public void createEC2InstanceFromTemplate(String templateName) {
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#LaunchInstanceFromTemplate:");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        switchTo().frame("instance-lx-react-frame");
        $x("//span[@id='awsui-select-0-textbox']").shouldBe(visible).click();
        $x("//span[contains(text(),'" + templateName + "')]").shouldBe(visible).click();
        $x("//button[contains(.,'Launch instance from template')]").shouldBe(visible).scrollIntoView(true).click();
        $x("//*[@data-id='summaryContainer'][contains(.,'Successfully initiated launch of instance')]").shouldBe(visible);
        $x("//*[@data-id='summaryContainer']/a").shouldBe(visible).click();
        try {
            Thread.sleep(180000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Get Instance Ip")
    @Parameters({"templateName"})
    public void getInstanceIP(String templateName) {
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + templateName + ";sort=desc:launchTime");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        $x("//div[text()='" + templateName + "']/../../../..//td[contains(.,'running')]").waitUntil(visible, 120000).click();
        $x("//span[@id='detailsPublicIp']").shouldBe(visible);
        ip = $x("//span[@id='detailsPublicIp']").shouldBe(visible).getText();

//        String file="resources/data/Application.properties";
//        Path path = Paths.get(file);
//        Charset charset = StandardCharsets.UTF_8;
//
//        String content = null;
//        try {
//            content = new String(Files.readAllBytes(path), charset);
//            content = content.replaceAll("instanceIP="+instanceIP, "instanceIP="+ip);
//            Files.write(path, content.getBytes(charset));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("instanceIP",ip);
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("instanceIP= "+ip);

    }


    @Test(description = "Test Delete EC2 Instance")
    @Parameters({ "templateName"})
    public void deleteEC2Instance(String templateName){
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search="+templateName+";sort=desc:launchTime");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        $x("//div[text()='"+templateName+"']/../../../..//td[contains(.,'running')]").shouldBe(visible).click();
        $x("//button[contains(.,'Actions')]").shouldBe(visible).click();
        $x("//div[@id='gwt-debug-menu-instance-state']").shouldBe(visible).click();
        $x("//div[@id='gwt-debug-action-terminate-instances']").shouldBe(visible).click();
        $x("//li[contains(.,'"+templateName+"')]").shouldBe(visible);
        $x("//span[contains(text(),'Yes, Terminate')]").shouldBe(visible).click();
        $x("//span[contains(text(),'Yes, Terminate')]").should(disappear);
        $x("//div[text()='"+templateName+"']/../../../..//td[contains(.,'shutting-down')]").waitUntil(disappear,120000);
    }

}
