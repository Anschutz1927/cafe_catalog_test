package by.black_pearl.test_cafe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.orm_framework.OfferOrm;
import by.black_pearl.test_cafe.orm_framework.ParamOrm;

/**
 * Created by BLACK_Pearl.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final OnRecycleViewItemClicked mListener;
    private List<OfferOrm> mListOfferOrm;

    public RecyclerViewAdapter(Context context, List<OfferOrm> listOfferOrm, OnRecycleViewItemClicked listener) {
        this.mContext = context;
        this.mListOfferOrm = listOfferOrm;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext).inflate(R.layout.view_catalog_list_of_dish, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mName.setText("Название: " + this.mListOfferOrm.get(position).getName());
        String weight = OfferOrm
                .getParamNameAndParamValueByParamName(mListOfferOrm.get(position).getParams(), ParamOrm.WEIGHT);
        holder.mWeight.setText(weight);
        holder.mPrice.setText(String.valueOf(this.mListOfferOrm.get(position).getPrice()));
        String image_url = this.mListOfferOrm.get(position).getPictureUrl();
        if(image_url == null || image_url.equals("")) {
            return;
        }
        Picasso.with(mContext)
                .load(image_url)
                .placeholder(R.drawable.no_img)
                .error(R.drawable.no_img)
                //             .centerInside()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return this.mListOfferOrm.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mWeight;
        private TextView mName;
        private TextView mPrice;
        private ImageView mImageView;

        ViewHolder(final View itemView) {
            super(itemView);
            this.mImageView = (ImageView) itemView.findViewById(R.id.catalog_list_dish_image);
            this.mName = (TextView) itemView.findViewById(R.id.catalog_list_dish_name);
            this.mWeight = (TextView) itemView.findViewById(R.id.catalog_list_dish_weight);
            this.mPrice = (TextView) itemView.findViewById(R.id.catalog_list_dish_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }

    public interface OnRecycleViewItemClicked {
        void onItemClick(View v, int position);
    }
}
