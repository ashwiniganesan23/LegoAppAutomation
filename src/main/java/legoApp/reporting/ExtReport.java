package legoApp.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;
import legoApp.utils.Paths;


public class ExtReport {

    public synchronized static ExtentReports getReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter( Paths.EXTENT_REPORT_HTML.getPath ( ) );
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        JsonFormatter jsonReporter = new JsonFormatter( Paths.EXTENT_REPORT_JSON.getPath ());
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.attachReporter(jsonReporter);
        return extent;

    }

}
