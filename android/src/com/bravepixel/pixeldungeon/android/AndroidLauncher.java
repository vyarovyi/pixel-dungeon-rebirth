package com.bravepixel.pixeldungeon.android;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bravepixel.pixeldungeon.android.AndroidInputProcessor;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.utils.PDPlatformSupport;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		String version;
		try {
			version = getPackageManager().getPackageInfo( getPackageName(), 0 ).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			version = "???";
		}
		initialize(new PixelDungeon(new AndroidPlatformSupport(version, "BravePixel/PixelDungeon", new AndroidInputProcessor())), config);
	}

	private class AndroidPlatformSupport extends PDPlatformSupport {
		public AndroidPlatformSupport(String version, String basePath, NoosaInputProcessor inputProcessor) {
			super(version, basePath, inputProcessor);
		}

		@SuppressLint("NewApi")
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
	}
}
