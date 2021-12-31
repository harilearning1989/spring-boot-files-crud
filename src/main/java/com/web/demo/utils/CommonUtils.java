package com.web.demo.utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.Charset;

public interface CommonUtils {

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isLinux() {
        return getOsName().startsWith("Linux");
    }

    public static boolean isMac() {
        return getOsName().startsWith("Mac");
    }

    static String fileLocation(){
        if (CommonUtils.isWindows()) {
            return AppicationConstants.DOWNLOAD_LOCATION;
        } else if (CommonUtils.isLinux()) {
            return AppicationConstants.DOWNLOAD_LOCATION;
        } else if(CommonUtils.isMac()){
            return AppicationConstants.DOWNLOAD_MAC;
        }
        return null;
    }

    public static String readResource(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }

}
