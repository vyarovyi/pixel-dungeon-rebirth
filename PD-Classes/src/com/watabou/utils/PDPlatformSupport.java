package com.watabou.utils;

import com.watabou.input.NoosaInputProcessor;

public class PDPlatformSupport{
    private final String version;
    private final String basePath;
    private final NoosaInputProcessor inputProcessor;

    public PDPlatformSupport(String version, String basePath, NoosaInputProcessor inputProcessor) {
        this.version = version;
        this.basePath = basePath;
        this.inputProcessor = inputProcessor;
    }

    public String getVersion() {
        return version;
    }

    public String getBasePath() {
        return basePath;
    }

    public NoosaInputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public boolean isFullscreenEnabled() {
        return false;
    }

    public boolean isImmersiveModeEnabled(){
        return false;
    }

    public void updateImmersiveMode(){

    }

    public void onOrientationChanged(boolean isLandscape){

    }
}
