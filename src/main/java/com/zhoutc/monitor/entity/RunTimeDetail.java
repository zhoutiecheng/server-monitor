package com.zhoutc.monitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:47
 */
public class RunTimeDetail implements Serializable{
    private String vmName;
    private String libraryPath;
    private String calssPath;
    private String vmVersion;
    private List<String> inputArguments;

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    public String getCalssPath() {
        return calssPath;
    }

    public void setCalssPath(String calssPath) {
        this.calssPath = calssPath;
    }

    public String getVmVersion() {
        return vmVersion;
    }

    public void setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }

    public List<String> getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(List<String> inputArguments) {
        this.inputArguments = inputArguments;
    }
}
