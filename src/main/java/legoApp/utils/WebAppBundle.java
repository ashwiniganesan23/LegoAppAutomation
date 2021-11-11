package legoApp.utils;


import java.io.File;

public enum WebAppBundle {

    HOME_PAGE("HomePage"),
    ITEM_PAGE("ItemPage");

    private final String path;

    WebAppBundle(String name) {
        String contentResourceFilePath =  "content" + File.separator;
        this.path = contentResourceFilePath + name;
    }

    public String getPath() {
        return path;
    }

}
