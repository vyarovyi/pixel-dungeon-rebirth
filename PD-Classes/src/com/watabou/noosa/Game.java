/*
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.noosa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.watabou.glscripts.Script;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.SystemTime;

public class Game extends ApplicationAdapter {

    public static Game instance;

    // Actual size of the screen
    public static int width;
    public static int height;

    // Density: mdpi=1, hdpi=1.5, xhdpi=2...
    public static float density = 1;

    public static String version;
    public static float timeScale = 1f;
    public static float elapsed = 0f;
    // Current scene
    protected Scene scene;
    // New scene we are going to switch to
    protected Scene requestedScene;
    // true if scene switch is requested
    protected boolean requestedReset = true;
    // New scene class
    protected Class<? extends Scene> sceneClass;
    // Current time in milliseconds
    protected long now;
    // Milliseconds passed since previous update
    protected long step;

    public Game(Class<? extends Scene> c) {
        super();
        sceneClass = c;
    }

    @Override
    public void create () {
        instance = this;

        density = Gdx.graphics.getDensity();

        // TODO: Is this right?
        onSurfaceCreated();
    }

    @Override
    public void resize (int width, int height) {
        Gdx.gl.glViewport(0, 0, width, height);

        if (width != Game.width || height != Game.height) {
            Game.width = width;
            Game.height = height;

            Scene sc = scene();
            if (sc != null) {
                TextureCache.reload();
                Camera.reset();
                switchScene(sc.getClass());
            }
        }
    }

    @Override
    public void render () {
        if (width == 0 || height == 0) {
            return;
        }

        SystemTime.tick();
        long rightNow = SystemTime.now;
        step = (now == 0 ? 0 : rightNow - now);
        now = rightNow;

        step();

        NoosaScript.get().resetCamera();
        Gdx.gl.glScissor( 0, 0, width, height );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        draw();
    }

    @Override
    public void pause () {
        if (scene != null) {
            scene.pause();
        }

        Script.reset();

        Music.INSTANCE.pause();
        Sample.INSTANCE.pause();
    }

    @Override
    public void resume () {
        now = 0;

        Music.INSTANCE.resume();
        Sample.INSTANCE.resume();
    }

    @Override
    public void dispose () {
        destroyGame();

        Music.INSTANCE.mute();
        Sample.INSTANCE.reset();
    }

    public void onSurfaceCreated() {
        Gdx.gl.glEnable( GL20.GL_BLEND );
        // For premultiplied alpha:
        // Gdx.gl.glBlendFunc( GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA );
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

        TextureCache.reload();
    }

    public static void resetScene() {
        switchScene(instance.sceneClass);
    }

    public static void switchScene(Class<? extends Scene> c) {
        instance.sceneClass = c;
        instance.requestedReset = true;
    }

    public static Scene scene() {
        return instance.scene;
    }

    public static void vibrate(int milliseconds) {
        Gdx.input.vibrate(milliseconds);
    }

    protected void destroyGame() {
        if (scene != null) {
            scene.destroy();
            scene = null;
        }

        instance = null;
    }

    protected void step() {

        if (requestedReset) {
            requestedReset = false;
            try {
                requestedScene = sceneClass.newInstance();
                switchScene();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        update();
    }

    protected void draw() {
        scene.draw();
    }

    protected void switchScene() {

        Camera.reset();

        if (scene != null) {
            scene.destroy();
        }
        scene = requestedScene;
        scene.create();

        Game.elapsed = 0f;
        Game.timeScale = 1f;
    }

    protected void update() {
        Game.elapsed = Game.timeScale * step * 0.001f;

        scene.update();
        Camera.updateAll();
    }

    public void finish() {
        Gdx.app.exit();
    }
}
