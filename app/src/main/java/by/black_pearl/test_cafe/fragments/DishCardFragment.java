package by.black_pearl.test_cafe.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.retrofit.RetrofitManager;
import by.black_pearl.test_cafe.server_data.DataUpdater;
import by.black_pearl.test_cafe.server_data.ServerAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DishCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DishCardFragment extends Fragment {
    private static final String IMAGE_URL = "image";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String WEIGHT = "weight";
    private static final String DESCRIPTION = "desc";

    public DishCardFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param image_url URL of image.
     * @param name name of dish.
     * @param price price of dish.
     * @param weight weight of dish.
     * @param description description of dish.
     * @return A new instance of fragment DishCardFragment.
     */
    public static DishCardFragment newInstance(
            String image_url,
            String name,
            String price,
            @Nullable String weight,
            @Nullable String description
    ) {
        DishCardFragment fragment = new DishCardFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, image_url);
        args.putString(NAME, name);
        args.putString(PRICE, price);
        args.putString(WEIGHT, weight);
        args.putString(DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String image_url = null;
        String name = "Название: ";
        String price = "Цена: ";
        String weight = "";
        String description = "Описание: \n\n";
        if(getArguments() != null) {
            image_url = getArguments().getString(IMAGE_URL, null);
            name += getArguments().getString(NAME, "");
            price += getArguments().getString(PRICE, "");
            weight += getArguments().getString(WEIGHT, "");
            description += getArguments().getString(DESCRIPTION, "");
        }
        View view = inflater.inflate(R.layout.fragment_dish_card, container, false);
        loadDishcardImage(((ImageView) view.findViewById(R.id.fragment_dish_card_image)), image_url);
        ((TextView) view.findViewById(R.id.fragment_dish_card_name)).setText(name);
        ((TextView) view.findViewById(R.id.fragment_dish_card_price)).setText(price);
        ((TextView) view.findViewById(R.id.fragment_dish_card_weight)).setText(weight);
        ((TextView) view.findViewById(R.id.fragment_dish_card_description)).setText(description);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadDishcardImage(final ImageView dishcardImage, String image_url) {
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
                dishcardImage.setImageBitmap(image);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
