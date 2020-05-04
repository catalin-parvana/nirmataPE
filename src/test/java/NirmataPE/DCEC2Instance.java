package NirmataPE;

import com.aventstack.extentreports.Status;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.NirmataApplicationProperties;
import utils.NirmataSetup;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DCEC2Instance extends NirmataSetup {

    private final String awsAccount = appProperties.properties.getProperty("awsAccount");
    private final String awsUsername = appProperties.properties.getProperty("awsUsername");
    private final String awsPassword = appProperties.properties.getProperty("awsPassword");
    private final String nadmVersion = appProperties.properties.getProperty("nadmVersion");
    private String dcInstanceID,dcInstanceIP;
    private PropertiesConfiguration config;


    @Test(description = "Create Direct Connect EC2 Instance From Template")
    @Parameters({"dcTemplateName"})
    public void createDCInstanceFromTemplate(String dcTemplateName) {
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
        $x("//span[contains(text(),'" + dcTemplateName + "')]").shouldBe(visible).click();
        $x("//button[contains(.,'Launch instance from template')]").shouldBe(visible).scrollIntoView(true).click();
        $x("//*[@data-id='summaryContainer'][contains(.,'Successfully initiated launch of instance')]").shouldBe(visible);
        dcInstanceID=$x("//*[@data-id='summaryContainer']/a").shouldBe(visible).getText();
        $x("//*[@data-id='summaryContainer']/a").shouldBe(visible).click();
        System.out.println("dcInstanceID= "+dcInstanceID);
        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("dcInstanceID",dcInstanceID);
            config.save();
            Thread.sleep(120000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        		test.log(Status.PASS, "dcInstanceID= "+"<b>"+dcInstanceID+"/b");

    }

    @Test(description = "Get DC EC2 Instance Ip")
    public void getDCInstanceIP() {
        dcInstanceID = (new NirmataApplicationProperties()).properties.getProperty("dcInstanceID");
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + dcInstanceID + ";sort=desc:launchTime");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        $x("//td/div[text()='"+dcInstanceID+"']/../..//*[text()='running']").waitUntil(visible, 120000).click();
        $x("//span[@id='detailsPublicIp']").shouldBe(visible);
        dcInstanceIP = $x("//span[@id='detailsPublicIp']").shouldBe(visible).getText();
        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("dcInstanceIP",dcInstanceIP);
      //      config.setProperty("url","https://"+dcInstanceIP+":443");
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("dcInstanceIP= "+dcInstanceIP);
     //   System.out.println("url= "+"https://"+dcInstanceIP+":443");
//        test.log(Status.PASS, "ec2InstanceID= "+"<b>"+ec2InstanceID+"</b>");
//        test.log(Status.PASS, "ec2InstanceIP= "+"<b>"+ec2InstanceIP+"</b>");
        test.log(Status.PASS, "dcInstanceID= "+"<a href=https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + dcInstanceID + ";sort=desc:launchTime>"+dcInstanceID+"</a>");
        test.log(Status.PASS, "dcInstanceIP= "+"<a href=https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search=" + dcInstanceID + ";sort=desc:launchTime>"+dcInstanceIP+"</a>");
//        test.log(Status.PASS, "nadmVersion= "+"<b>"+nadmVersion+"</b>");
 //       test.log(Status.PASS, "NirmataPE url= "+ "<a href=https://"+dcInstanceIP+":443>"+"https://"+dcInstanceIP+":443</a>");
    }


    @Test(description = "Delete EC2 Instance")
    public void deleteDCInstance(){
        dcInstanceID = (new NirmataApplicationProperties()).properties.getProperty("dcInstanceID");
        System.out.println("dcInstanceID= "+dcInstanceID);
        open("https://us-west-1.console.aws.amazon.com/ec2/v2/home?region=us-west-1#Instances:search="+dcInstanceID+";sort=securityGroupNames");
        $x("//*[@id='iam_user_radio_button']").shouldBe(visible).click();
        $x("//input[@id='resolving_input']").setValue(awsUsername);
        $x("//*[@id='next_button_text']").shouldBe(visible).click();
        $x("//input[@id='account']").setValue(awsAccount);
        $x("//input[@id='username']").setValue(awsUsername);
        $x("//input[@id='password']").setValue(awsPassword);
        $x("//a[@id='signin_button']").shouldBe(visible).click();
        $x("//td/div[text()='"+dcInstanceID+"']/../..//*[text()='running']").shouldBe(visible).click();
        $x("//button[contains(.,'Actions')]").shouldBe(visible).click();
        $x("//div[@id='gwt-debug-menu-instance-state']").shouldBe(visible).click();
        $x("//div[@id='gwt-debug-action-terminate-instances']").shouldBe(visible).click();
        $x("//li[contains(.,'"+dcInstanceID+"')]").shouldBe(visible);
        $x("//span[contains(text(),'Yes, Terminate')]").shouldBe(visible).click();
        $x("//span[contains(text(),'Yes, Terminate')]").should(disappear);
        $x("//td/div[text()='"+dcInstanceID+"']/../..//*[text()='shutting-down']").waitUntil(disappear,120000);
        $x("//td/div[text()='"+dcInstanceID+"']/../..//*[text()='terminated']").waitUntil(appears,120000);
        try {
            config = new PropertiesConfiguration("resources/data/Application.properties");
            config.setProperty("url","https://nirmata.io");
            config.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        test.log(Status.PASS, "dcInstanceID= "+"<b>"+dcInstanceID+"/b");
    }

}
