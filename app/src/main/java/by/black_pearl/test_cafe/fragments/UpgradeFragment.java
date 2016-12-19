package by.black_pearl.test_cafe.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.black_pearl.test_cafe.MainActivity;
import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.server_data.DataUpdater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpgradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpgradeFragment extends Fragment {

    public UpgradeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment UpgradeFragment.
     */
    public static UpgradeFragment newInstance() {
        UpgradeFragment fragment = new UpgradeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upgrade, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView messageTextView =
                (TextView) getView().findViewById(R.id.upgrade_message_textview);
        DataUpdater dataUpdater = new DataUpdater(getContext(), messageTextView, getDataUpdaterCallback());
        dataUpdater.startDownload();
    }

    private DataUpdater.DataUpdaterInterface getDataUpdaterCallback() {
        return new DataUpdater.DataUpdaterInterface() {
            @Override
            public void onFinishUpdate() {
                ((MainActivity) getActivity()).setIsUpgrading(false);
                ((MainActivity) getActivity()).fragmentChanger(MainActivity.SetFragment.CATALOG);
            }
        };
    }
}
