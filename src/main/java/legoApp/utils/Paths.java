package legoApp.utils;

public enum Paths {

    GLOBAL_VARIABLES("src/resources/Config/config.properties"),
    EXTENT_REPORT_HTML("src/reports/index.html"),
    EXTENT_REPORT_JSON("src/reports/test_result.json");


    private final String path;

    Paths(String name) {
        this.path = name;
    }


    public String getPath() {
        return path;
    }

}
