package by.black_pearl.test_cafe.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import by.black_pearl.test_cafe.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

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
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        setMapPointAndScrollingSupport(
                (ScrollView) view.findViewById(R.id.fragment_contacts_scrollView),
                (GMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_contacts_gmapFragment)
                );
        return view;
    }

    private void setMapPointAndScrollingSupport(final ScrollView scrollView, GMapFragment gMapFragment) {
        gMapFragment.setListener(new GMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
        gMapFragment.getMapAsync(gMapFragment.getMapAsyncCallback());
    }

    private boolean checkPermission() {
        return !(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
