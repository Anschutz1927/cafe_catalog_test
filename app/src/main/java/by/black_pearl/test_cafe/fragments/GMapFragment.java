package by.black_pearl.test_cafe.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by BLACK_Pearl.
 */

public class GMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private static double LATITUDE = 53.847989;
    private static double LONGTITUDE = 27.552960;
    private OnTouchListener mListener;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        GMapFrame frame = new GMapFrame(getActivity());
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup) view).addView(frame, params);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng cafePoint = new LatLng(LATITUDE, LONGTITUDE);
        googleMap.addMarker(new
                MarkerOptions().position(cafePoint).title("Cafe is here."));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cafePoint));
        if(checkPermission()) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        mListener = null;
        super.onDestroy();
    }

    private boolean checkPermission() {
        return !(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);
    }

    public void setListener(OnTouchListener listener) {
        mListener = listener;
    }

    public OnMapReadyCallback getMapAsyncCallback() {
        return this;
    }

    public interface OnTouchListener {
        void onTouch();
    }

    public class GMapFrame extends FrameLayout {

        public GMapFrame(Context context) {
            super(context);
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
    }
}
