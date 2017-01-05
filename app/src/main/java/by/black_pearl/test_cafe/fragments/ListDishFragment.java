package by.black_pearl.test_cafe.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import by.black_pearl.test_cafe.MainActivity;
import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.adapters.RecyclerViewAdapter;
import by.black_pearl.test_cafe.orm_framework.OfferOrm;
import by.black_pearl.test_cafe.orm_framework.ParamOrm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDishFragment extends Fragment {

    private static String CATEGORY_ID = "categoryid";

    private List<OfferOrm> mListOfferOrm;
    private MainActivity.MainActivityFragmentsCallback mCallback;

    public ListDishFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param categoryId
     *
     * @return A new instance of fragment ListDishFragment.
     */
    public static ListDishFragment newInstance(int categoryId) {
        ListDishFragment fragment = new ListDishFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() == null) {
            return;
        }
        int id = getArguments().getInt(CATEGORY_ID);
        this.mListOfferOrm = OfferOrm.find(OfferOrm.class, "category_id = ?", String.valueOf(id));
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dish, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_dish_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(getContext(), this.mListOfferOrm, getItemClickListener()));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCallback = ((MainActivity) getActivity()).getFragmentsCallback();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mCallback = null;
    }

    private RecyclerViewAdapter.OnRecycleViewItemClicked getItemClickListener() {
        return new RecyclerViewAdapter.OnRecycleViewItemClicked() {
            @Override
            public void onItemClick(View v, int position) {
                String weight = OfferOrm
                        .getParamNameAndParamValueByParamName(mListOfferOrm.get(position)
                                .getParams(), ParamOrm.WEIGHT);
                String description = null;
                if(mListOfferOrm.get(position).getDescription() != null) {
                    description = mListOfferOrm.get(position).getDescription();
                }
                mCallback.changeFragmentToDishCard(
                        mListOfferOrm.get(position).getPictureUrl(),
                        mListOfferOrm.get(position).getName(),
                        String.valueOf(mListOfferOrm.get(position).getPrice()),
                        weight,
                        description
                );
            }
        };
    }
}
