package by.black_pearl.test_cafe.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import org.osmdroid.views.MapView;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.osmdroid.MapManager;
import by.black_pearl.test_cafe.osmdroid.MapViewScrolled;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

    private static double LATITUDE = 53.847989;
    private static double LONGTITUDE = 27.552960;
    private MapManager mMapManager;
    private LocationManager mLocationManager;

    public ContactsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContactsFragment.
     */
    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        this.mMapManager = new MapManager(
                getContext(),
                (MapView) view.findViewById(R.id.fragment_contacts_map)
        );
        this.mMapManager.setCenterAt(LATITUDE, LONGTITUDE);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(checkPermission()) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this.mMapManager);
        }
        setScrollingSupport(
                (ScrollView) view.findViewById(R.id.fragment_contacts_scrollView),
                (MapViewScrolled) view.findViewById(R.id.fragment_contacts_map)
        );
        return view;
    }

    private void setScrollingSupport(final ScrollView scrollView, MapViewScrolled mapViewScrolled) {
        mapViewScrolled.setListener(new MapViewScrolled.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mLocationManager != null && mMapManager != null) {
            try {
                if(checkPermission()) {
                    mLocationManager.removeUpdates(mMapManager);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
