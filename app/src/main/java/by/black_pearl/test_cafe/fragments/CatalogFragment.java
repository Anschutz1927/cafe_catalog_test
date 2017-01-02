package by.black_pearl.test_cafe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import by.black_pearl.test_cafe.MainActivity;
import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.adapters.GridViewAdapter;
import by.black_pearl.test_cafe.orm_framework.CategoryOrm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CatalogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogFragment extends Fragment {

    private ArrayList<CategoryOrm> mListCategoryOrm;
    private MainActivity.MainActivityFragmentsCallback mCallback;

    public CatalogFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CatalogFragment.
     */
    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mListCategoryOrm = (ArrayList<CategoryOrm>) CategoryOrm.listAll(CategoryOrm.class);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ((GridView) view.findViewById(R.id.fragment_catalog_gridView))
                .setAdapter(new GridViewAdapter(getContext(), mListCategoryOrm));
        ((GridView) view.findViewById(R.id.fragment_catalog_gridView))
                .setOnItemClickListener(getOnItemClickListener());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try  {
            this.mCallback = ((MainActivity) getActivity()).getFragmentsCallback();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mCallback = null;
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.changeFragmentToListDish((int) id);
            }
        };
    }
}
