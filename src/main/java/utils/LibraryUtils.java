package utils;

import com.aventstack.extentreports.Status;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.FileDownloadMode.HTTPGET;
import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.$x;

public class LibraryUtils extends NirmataSetup{
    protected String absolutePathOfFile = System.getProperty("user.dir");

    public static void waitFor(String element, SelenideElement selector,int seconds) {
        seconds=seconds*1000;
        try {
            selector.waitUntil(visible,seconds);
            test.log(Status.PASS, "<b>"+element+"</b>" + " is visible.");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Element : " + "<b>"+element+"</b>"+" is not visible.");
            throw(e);
        }
    }

    public static void waitFor(String element, SelenideElement selector) {
        try {
            selector.shouldBe(visible);
            test.log(Status.PASS, "<b>"+element+"</b>" + " is visible.");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Element : " + "<b>"+element+"</b>"+" is not visible.");
            throw(e);
        }
    }

    public static void click(String element, SelenideElement selector) {
        try {
            selector.shouldBe(visible).click();
            test.log(Status.PASS, "<b>"+element+"</b>"  + " clicked.");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Could not click: " + "<b>"+element+"</b>" );
            throw(e);
        }
    }

    public static void type(String element, SelenideElement selector, String content) {
        try {
            selector.shouldBe(visible).clear();
            selector.shouldBe(visible).setValue(content);
            if(element.contains("Password")){
                content="**********";
            }
            test.log(Status.PASS, "<b>"+element+"</b>" + " filled with: "+"<b>"+content+"</b>");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Could not fill: " +"<b>"+element+"</b>"+" with: "+"<b>"+content+"</b>");
            throw(e);
        }
    }

    public static void selectOptionByValue(String element,SelenideElement selector, String value) {
        try {
            selector.selectOptionByValue(value);
            test.log(Status.PASS, "Selection made on : "+"<b>"+element+"</b>" +", option: "+"<b>"+value+"</b>");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Could not make selection on: "+"<b>"+element+"</b>" +", option: "+"<b>"+value+"</b>");
            throw(e);
        }
    }

    public static void selectOptionByText(String element, SelenideElement selector, String content) {

        try {
            selector.selectOptionContainingText(content);
            test.log(Status.PASS, "Selection made on : "+"<b>"+element+"</b>" +", option: "+"<b>"+content+"</b>");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Could not make selection on: "+"<b>"+element+"</b>" +", option: "+"<b>"+content+"</b>");
            throw(e);
        }
    }

    public static void select2OptionByText(String element, SelenideElement selector, String content) {

        try {
            SelenideElement dropdownElement=$x("//li[text()='"+content+"']");
            selector.shouldBe(visible).click();
            dropdownElement.shouldBe(visible).scrollIntoView(true).click();

            test.log(Status.PASS, "Selection made on : "+"<b>"+element+"</b>" +", option: "+"<b>"+content+"</b>");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Could not make selection on: "+"<b>"+element+"</b>" +", option: "+"<b>"+content+"</b>");
            throw(e);
        }
    }

    public static void upload(String element, SelenideElement selector,String filePath){
        try {
            selector.uploadFile(new File(filePath));
            test.log(Status.PASS,  "<b>"+element+"</b>"+" Uploaded.");
        } catch (com.codeborne.selenide.ex.ElementNotFound e) {
            test.log(Status.FAIL, "Could not Upload: " + "<b>"+element+"</b>");
            throw(e);
        }
    }

    public static void download(String element, SelenideElement selector) {
        try {
            useProxy(true);
            File downloadedFile = selector.download(4000);
            test.log(Status.PASS,   "<b>"+element+"</b>"+" Downloaded");
        } catch (Exception e) {
            test.log(Status.FAIL, "Could not Download: " + "<b>"+element+"</b>");
            e.printStackTrace();
        }

    }


    public static boolean exists(SelenideElement selector){
        boolean found;
            try {
                selector.waitUntil(visible, 3000);
                found=true;
            } catch (NoSuchElementException e) {
                found=false;
            }
            return found;

    }

    protected static void useProxy(boolean proxyEnabled) {
        if (Configuration.proxyEnabled != proxyEnabled) {
            Selenide.closeWebDriver();
        }
        Configuration.proxyEnabled = proxyEnabled;
        Configuration.fileDownload = proxyEnabled ? PROXY : HTTPGET;
    }



}
