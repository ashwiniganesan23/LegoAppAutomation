package legoApp.reporting;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import legoApp.utils.BaseTest;
import legoApp.utils.LegoApp;

import java.util.Objects;

public class ReportingTestListener extends LegoApp implements ITestListener {

    private static  ExtentReports extent = ExtReport.getReport ( );
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<> ( );
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<> ( );


    private final Logger logger = Logger.getLogger ( ReportingTestListener.class );



    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod ( ).getMethodName ( );
        if ( parentTest.get ( ) == null || !parentTest.get ( ).getModel ( ).getName ( ).equals ( methodName ) ) {
            testMethodStart ( methodName );
        }
        ExtentTest extentTest = parentTest.get ( )
                .createNode ( result.getMethod ( ).getMethodName ( ), result.getMethod ( ).getDescription ( ) );
        test.set ( extentTest );
        addTestDescription ( result );
    }

    protected void addTestDescription(ITestResult result) {
        String testCaseDescription = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation( Test.class).description ();
        test.get().info("Testcase Description = " +  testCaseDescription);
    }



    public static void testMethodStart(String methodName) {
        if ( null != parentTest.get ( ) ) {
            if ( methodName.equals ( parentTest.get ( ).getModel ( ).getName ( ) ) ) {
                return;
            }
            if ( null == parentTest.get ( ).getStatus ( ) ) {
                parentTest.get ( ).skip ( "Next test started." );
            }
        }
        ExtentTest extentTest = extent.createTest ( methodName );
        parentTest.set ( extentTest );
    }


    public void onTestSuccess(ITestResult result) {

        Object testClass = result.getInstance ( );
        WebDriver driver = ((BaseTest) testClass).getDriver ( );
        String base64Screenshot =
                "data:image/png;base64," +
                        ((TakesScreenshot) Objects.requireNonNull ( driver )).getScreenshotAs ( OutputType.BASE64 );

        test.get ( ).pass ( "Test Passed",
                test.get ( ).addScreenCaptureFromBase64String ( base64Screenshot ).getModel ( ).getMedia ( ).get ( 0 ) );
        test.set(null);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error ( (result.getMethod ( ).getMethodName ( ) + " failed!") );
        Object testClass = result.getInstance ( );
        WebDriver driver = ((BaseTest) testClass).getDriver ( );
        String base64Screenshot =
                "data:image/png;base64," +
                        ((TakesScreenshot) Objects.requireNonNull ( driver )).getScreenshotAs ( OutputType.BASE64 );

        test.get ( ).fail ( result.getThrowable ( ),
                test.get ( ).addScreenCaptureFromBase64String ( base64Screenshot ).getModel ( ).getMedia ( ).get ( 0 ) );
        test.set ( null );
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if ( result.getAttribute ( "goingToBeRetried" ) != null
                && (Boolean) result.getAttribute ( "goingToBeRetried" ) ) {
            extent.removeTest ( parentTest.get ( ) );
        } else {

            if ( test.get ( ) == null ) {
                logger.debug ( "Test did not execute, because of an exception thrown in data provider." );
                parentTest.get ( ).fail ( result.getThrowable ( ) );
                result.setStatus ( ITestResult.FAILURE );
                test.get ( ).fail ( "Test has been marked as failed, because of an exception thrown in data provider." );
            } else if ( result.getThrowable ( ) != null && result.getThrowable ( ).getClass ( ) != SkipException.class ) {
                test.get ( ).fail ( result.getThrowable ( ) );
                result.setStatus ( ITestResult.FAILURE );
            } else {
                test.get ( ).skip ( result.getThrowable ( ) );
            }
        }
        test.set ( null );

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }


    @Override
    public void onStart(ITestContext iTestContext) {
        logger.debug ( "Extent Reports - Test Suite started!" );
    }

    @AfterSuite
    public void onFinish(ITestContext iTestContext) {
        logger.debug ( "Test Suite is ending!" );
        extent.flush ( );
    }


    public static void error(String message) {
        ExtentTest extentTest = getTest ( );
        if ( extentTest != null ) {
            extentTest.info ( formatMessage ( message ) );
        }
    }


    public static void info(String message) {
        ExtentTest extentTest = getTest ( );
        if ( extentTest != null ) {
            extentTest.info ( formatMessage ( message ) );
        }
    }

    public static void debug(String message) {
        ExtentTest extentTest = getTest ( );
        if ( extentTest != null ) {
            extentTest.info ( formatMessage ( message ) );
        }
    }


    private static ExtentTest getTest() {
        if ( null != test.get ( ) ) {
            return test.get ( );
        }
        if ( null != parentTest ) {
            return parentTest.get ( );
        }
        return null;
    }

    private static String formatMessage(String message) {
        return message.replaceAll ( "\n", "<br>" );
    }


}
