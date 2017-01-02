package by.black_pearl.test_cafe.osmdroid;

import android.view.View;

/**
 * Created by BLACK_Pearl.
 */

public class MapViewScrolled {

    private View.OnTouchListener mListener;

    public void setListener(View.OnTouchListener listener) {
        mListener = listener;
    }
/*
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(mListener != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mListener.onTouch();
                    break;
                case MotionEvent.ACTION_UP:
                    mListener.onTouch();
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public interface OnTouchListener {
        public abstract void onTouch();
    }*/
}
