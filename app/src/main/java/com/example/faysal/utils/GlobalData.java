package com.example.faysal.utils;

public class GlobalData {

    private static String base_url = "https://api.github.com/search/repositories";

    public static String getBase_url() {
        return base_url;
    }

    public static void setBase_url(String base_url) {
        GlobalData.base_url = base_url;
    }
}
