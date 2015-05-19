package com.watabou.gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

/**
 * Created by Vlad on 10.05.2015.
 */
public class Bitmap extends Texture {
    public Bitmap(String internalPath) {
        super(internalPath);
    }

    public Bitmap(FileHandle file) {
        super(file);
    }

    public Bitmap(FileHandle file, boolean useMipMaps) {
        super(file, useMipMaps);
    }

    public Bitmap(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
        super(file, format, useMipMaps);
    }

    public Bitmap(Pixmap pixmap) {
        super(pixmap);
    }

    public Bitmap(Pixmap pixmap, boolean useMipMaps) {
        super(pixmap, useMipMaps);
    }

    public Bitmap(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
        super(pixmap, format, useMipMaps);
    }

    public Bitmap(int width, int height, Pixmap.Format format) {
        super(width, height, format);
    }

    public Bitmap(TextureData data) {
        super(data);
    }

    public int getId() {
        return glHandle;
    }
}
