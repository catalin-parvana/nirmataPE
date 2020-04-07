package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class NirmataReportListener implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {
//        NirmataSetup.methodInfo = NirmataSetup.test.createNode(result.getName());
        NirmataSetup.test = NirmataSetup.extent.createTest(result.getMethod().getDescription()).assignCategory(result.getMethod().getGroups());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
//        NirmataSetup.methodInfo.pass("Test Case Name : " + result.getName() + " is passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
//        NirmataSetup.methodInfo.log(Status.FAIL, "Test Case Name : " + result.getName() + " is failed");
//        NirmataSetup.methodInfo.log(Status.FAIL, "Test fallure : " + result.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
//        NirmataSetup.methodInfo = NirmataSetup.test.createNode(result.getName());
//        NirmataSetup.methodInfo.log(Status.SKIP, "Test Case Name : " + result.getName() + " is skipped");


    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        NirmataSetup.methodInfo = NirmataSetup.extent.createTest(context.getName()).assignCategory(context.getName());



//        NirmataSetup.methodInfo = NirmataSetup.extent.createTest(context.getName()).assignCategory(context.getName());
//        test = extent.createTest(result.getMethod().getDescription()).assignCategory(result.getMethod().getGroups());

    }

    @Override
    public void onFinish(ITestContext context) {

    }

}