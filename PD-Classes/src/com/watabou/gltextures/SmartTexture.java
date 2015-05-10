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

package com.watabou.gltextures;

import com.watabou.gdx.Bitmap;
import com.watabou.glwrap.Texture;
import com.watabou.utils.RectF;

import com.badlogic.gdx.graphics.Texture.*;

public class SmartTexture extends Texture {

    public int width;
    public int height;

    public TextureFilter fModeMin;
    public TextureFilter fModeMax;

    public TextureWrap wModeH;
    public TextureWrap wModeV;

    public Bitmap bitmap;

    public Atlas atlas;

    public SmartTexture(Bitmap bitmap) {
        this(bitmap, TextureFilter.Nearest, TextureWrap.ClampToEdge );
    }

    public SmartTexture(Bitmap bitmap, TextureFilter filtering, TextureWrap wrapping ) {

        super(bitmap);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        filter( filtering, filtering );
        wrap( wrapping, wrapping );
    }

    @Override
    public void filter(TextureFilter minMode, TextureFilter maxMode) {
        super.filter( fModeMin = minMode, fModeMax = maxMode);
    }

    @Override
    public void wrap( TextureWrap u, TextureWrap v ) {
        super.wrap( wModeH = u, wModeV = v );
    }

    @Override
    public void bitmap(Bitmap bitmap) {
        bitmap(bitmap, false);
    }

    public void bitmap(Bitmap bitmap, boolean premultiplied) {
        if (premultiplied) {
            super.bitmap(bitmap);
        } else {
            handMade(bitmap, true);
        }

        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    public void reload() {
//        id = new SmartTexture(bitmap).id;
        filter(fModeMin, fModeMax);
        wrap(wModeH, wModeV);
    }

    public RectF uvRect(int left, int top, int right, int bottom) {
        return new RectF(
                (float) left / width,
                (float) top / height,
                (float) right / width,
                (float) bottom / height);
    }
}
