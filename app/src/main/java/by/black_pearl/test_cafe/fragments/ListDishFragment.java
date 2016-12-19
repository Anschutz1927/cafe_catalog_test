package by.black_pearl.test_cafe.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.adapters.ListViewAdapter;
import by.black_pearl.test_cafe.orm_framework.OfferOrm;
import by.black_pearl.test_cafe.orm_framework.ParamOrm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDishFragment extends Fragment {

    private static String CATEGORY_ID = "categoryid";

    private List<OfferOrm> mListOfferOrm;

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_dish, container, false);
        ((ListView) view.findViewById(R.id.fragment_list_dish_listView))
                .setAdapter(new ListViewAdapter(getContext(), this.mListOfferOrm));
        ((ListView) view.findViewById(R.id.fragment_list_dish_listView))
                .setOnItemClickListener(getOnItemClickListener());
        return view;
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String weight = OfferOrm
                        .getParamNameAndParamValueByParamName(mListOfferOrm.get(position)
                                .getParams(), ParamOrm.WEIGHT);
                String description = null;
                if(mListOfferOrm.get(position).getDescription() != null) {
                    description = mListOfferOrm.get(position).getDescription();
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(
                        R.id.content_main,
                        DishCardFragment.newInstance(
                                mListOfferOrm.get(position).getPictureUrl(),
                                mListOfferOrm.get(position).getName(),
                                String.valueOf(mListOfferOrm.get(position).getPrice()),
                                weight,
                                description
                        )
                );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
    }

}
