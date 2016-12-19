package by.black_pearl.test_cafe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.orm_framework.CategoryOrm;

/**
 * Created by BLACK_Pearl.
 */

public class GridViewAdapter extends BaseAdapter {


    private final Context mContext;
    private ArrayList<CategoryOrm> mListCategory;

    public GridViewAdapter(Context context, ArrayList<CategoryOrm> listCategoryOrm) {
        this.mContext = context;
        this.mListCategory = listCategoryOrm;
    }

    @Override
    public int getCount() {
        return this.mListCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mListCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mListCategory.get(position).getCategoryId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.view_catalog_category, parent, false);
            String name = "id" + String.valueOf(this.mListCategory.get(position).getCategoryId());
            try {
                int resId = this.mContext.getResources().getIdentifier(name, "drawable", this.mContext.getPackageName());
                ((ImageView) view.findViewById(R.id.catalog_content_image)).setImageResource(resId);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            ((TextView) view.findViewById(R.id.catalog_content_category_name))
                    .setText(this.mListCategory.get(position).getCategory());
        }
        else {
            view = convertView;
        }

        return view;
    }
}
