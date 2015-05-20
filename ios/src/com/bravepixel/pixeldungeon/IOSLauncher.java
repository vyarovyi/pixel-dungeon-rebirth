package com.bravepixel.pixeldungeon;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.utils.PDPlatformSupport;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        final String version = NSBundle.getMainBundle().getInfoDictionaryObject("CFBundleShortVersionString").toString();
        return new IOSApplication(new PixelDungeon(new iOSPDPlatformSupport(version, null, new IOSInputProcessor())), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    private class iOSPDPlatformSupport extends PDPlatformSupport{

        public iOSPDPlatformSupport(String version, String basePath, NoosaInputProcessor inputProcessor) {
            super(version, basePath, inputProcessor);
        }
    }
}