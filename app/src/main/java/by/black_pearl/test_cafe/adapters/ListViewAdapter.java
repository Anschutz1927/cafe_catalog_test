package by.black_pearl.test_cafe.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.orm_framework.OfferOrm;
import by.black_pearl.test_cafe.orm_framework.ParamOrm;
import by.black_pearl.test_cafe.retrofit.RetrofitManager;
import by.black_pearl.test_cafe.server_data.DataUpdater;
import by.black_pearl.test_cafe.server_data.ServerAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BLACK_Pearl.
 */

public class ListViewAdapter extends BaseAdapter {

    private final Context mContext;
    private List<OfferOrm> mListOfferOrm;

    public ListViewAdapter(Context context, List<OfferOrm> listOfferOrm) {
        this.mContext = context;
        this.mListOfferOrm = listOfferOrm;
    }

    @Override
    public int getCount() {
        return this.mListOfferOrm.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mListOfferOrm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mListOfferOrm.get(position).getOfferId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(this.mContext)
                    .inflate(R.layout.view_catalog_list_of_dish, parent, false);
            ((TextView) view.findViewById(R.id.catalog_list_dish_name))
                    .setText("Название: " + this.mListOfferOrm.get(position).getName());
            String weight = OfferOrm
                    .getParamNameAndParamValueByParamName(mListOfferOrm.get(position).getParams(), ParamOrm.WEIGHT);
            ((TextView) view.findViewById(R.id.catalog_list_dish_weight))
                    .setText(weight);
            ((TextView) view.findViewById(R.id.catalog_list_dish_price))
                    .setText("Цена: " + String.valueOf(this.mListOfferOrm.get(position).getPrice()));
            setImageOfDish(
                    (ImageView) view.findViewById(R.id.catalog_list_dish_image),
                    this.mListOfferOrm.get(position).getPictureUrl()
            );
        }
        else {
            view = convertView;
        }
        return view;
    }

    private void setImageOfDish(final ImageView dishImage, String image_url) {
        if(image_url == null || image_url.equals("")) {
            return;
        }
        Call<ResponseBody> call = RetrofitManager.getRetrofit(DataUpdater.SERVER_ADDRESS)
                .create(ServerAPI.class).getImage(image_url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream inputStream = response.body().byteStream();
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                dishImage.setImageBitmap(image);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
