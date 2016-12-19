package by.black_pearl.test_cafe.osmdroid;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.views.MapView;

/**
 * Created by BLACK_Pearl.
 */

public class MapViewScrolled extends MapView {

    private OnTouchListener mListener;

    public MapViewScrolled(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
    }

    public MapViewScrolled(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs, boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
    }

    public MapViewScrolled(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapViewScrolled(Context context) {
        super(context);
    }

    public MapViewScrolled(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
    }

    public MapViewScrolled(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
    }

    public void setListener(OnTouchListener listener) {
        mListener = listener;
    }

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
    }
}
