package com.bravepixel.pixeldungeon.android;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.utils.PDPlatformSupport;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        String version;
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "???";
        }
        initialize(new PixelDungeon(new AndroidPlatformSupport(version, "BravePixel/PixelDungeon", new AndroidInputProcessor())), config);
    }

    @Override
    public void onWindowFocusChanged( boolean hasFocus ) {

        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            try{
                PixelDungeon.instance.getPlatformSupport().updateImmersiveMode();
            } catch (Exception e){
                PixelDungeon.reportException(e);
            }
        }
    }

    private class AndroidPlatformSupport extends PDPlatformSupport {
        public AndroidPlatformSupport(String version, String basePath, NoosaInputProcessor inputProcessor) {
            super(version, basePath, inputProcessor);
        }

        @Override
        public boolean isImmersiveModeEnabled() {
            return android.os.Build.VERSION.SDK_INT >= 19;
        }

        @Override
        public void onOrientationChanged(boolean value) {
            setRequestedOrientation(value ?
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        @SuppressLint("NewApi")
        @Override
        public void updateImmersiveMode() {
            if (isImmersiveModeEnabled()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            // Sometime NullPointerException happens here
                            getWindow().getDecorView().setSystemUiVisibility(
                                    PixelDungeon.immersed() ?
                                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                            :
                                            0);
                        } catch (Exception e) {
                            PixelDungeon.reportException(e);
                        }
                    }
                });
            }
        }
    }
}
