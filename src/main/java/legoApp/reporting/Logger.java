package legoApp.reporting;

import org.slf4j.LoggerFactory;


public class Logger {

    private Logger(org.slf4j.Logger slf4jLogger, Class clazz) {
        this.slf4jLogger = slf4jLogger;
        this.clazz = clazz;
    }

    private final Class clazz;
    private final org.slf4j.Logger slf4jLogger;

    public static Logger getLogger(Class clazz) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        return new Logger(logger, clazz);
    }



    public void info(String message) {
        slf4jLogger.info(message);
        ReportingTestListener.info(clazz.getSimpleName() + " : " + message);
    }

    public void error(String message) {
        slf4jLogger.error(message);
        ReportingTestListener.error(clazz.getSimpleName() + " : " + message);
    }

    public void debug(String message) {
        slf4jLogger.debug(message);
        ReportingTestListener.debug(clazz.getSimpleName() + " : " + message);
    }

}
