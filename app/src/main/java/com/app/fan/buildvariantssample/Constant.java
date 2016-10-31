package com.app.fan.buildvariantssample;

/**
 * Created by Administrator on 2016/10/31.
 */

public class Constant {

    public static String APP_HOST;
    public static String APP_PORT;


    static {
        switch (BuildConfig.API_EVN) {
            case "APITEST":
                APP_HOST = "192.168.152.1";
                APP_PORT = ":8080";
                break;

            case "APIRELEASE":
                APP_HOST = "www.release.com";
                APP_PORT = ":80";
                break;
        }
    }

}
