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

package com.watabou.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.watabou.utils.PointF;
import com.watabou.utils.Signal;

import java.util.HashMap;

public class Touchscreen implements InputProcessor{

    public static Signal<Touch> event = new Signal<Touch>(true);

    public static HashMap<Integer, Touch> pointers = new HashMap<Integer, Touch>();

    public static float x;
    public static float y;
    public static boolean touched;

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

//    public static void processTouchEvents(ArrayList<MotionEvent> events) {
//
//        int size = events.size();
//        for (int i = 0; i < size; i++) {
//
//            MotionEvent e = events.get(i);
//            Touch touch;
//
//            switch (e.getAction() & MotionEvent.ACTION_MASK) {
//
//                case MotionEvent.ACTION_DOWN:
//                    touched = true;
//                    touch = new Touch(e, 0);
//                    pointers.put(e.getPointerId(0), touch);
//                    event.dispatch(touch);
//                    break;
//
//                case MotionEvent.ACTION_POINTER_DOWN:
//                    int index = e.getActionIndex();
//                    touch = new Touch(e, index);
//                    pointers.put(e.getPointerId(index), touch);
//                    event.dispatch(touch);
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//                    int count = e.getPointerCount();
//                    for (int j = 0; j < count; j++) {
//                        pointers.get(e.getPointerId(j)).update(e, j);
//                    }
//                    event.dispatch(null);
//                    break;
//
//                case MotionEvent.ACTION_POINTER_UP:
//                    event.dispatch(pointers.remove(e.getPointerId(e.getActionIndex())).up());
//                    break;
//
//                case MotionEvent.ACTION_UP:
//                    touched = false;
//                    event.dispatch(pointers.remove(e.getPointerId(0)).up());
//                    break;
//
//            }
//
//            e.recycle();
//        }
//    }

    public static class Touch {

        public PointF start;
        public PointF current;
        public boolean down;

        public Touch(int x, int y) {
            start = new PointF(x, y);
            current = new PointF(x, y);

            down = true;
        }

        public void update(int x, int y) {
            current.set(x, y);
        }

        public Touch up() {
            down = false;
            return this;
        }
    }

}
