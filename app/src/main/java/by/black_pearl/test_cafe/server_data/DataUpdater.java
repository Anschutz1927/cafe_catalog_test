package by.black_pearl.test_cafe.server_data;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import java.util.List;

import by.black_pearl.test_cafe.R;
import by.black_pearl.test_cafe.orm_framework.CatalogDateOrm;
import by.black_pearl.test_cafe.orm_framework.CategoryOrm;
import by.black_pearl.test_cafe.orm_framework.OfferOrm;
import by.black_pearl.test_cafe.orm_framework.SugarManager;
import by.black_pearl.test_cafe.retrofit.RetrofitManager;
import by.black_pearl.test_cafe.server_data.shop.categories.category.Category;
import by.black_pearl.test_cafe.server_data.shop.offers.offer.Offer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by BLACK_Pearl.
 */

public class DataUpdater {
    public static String SERVER_ADDRESS = "http://ufa.farfor.ru/";
    private static String sKey = "ukAXxeJYZN";
    private static String sUpdaterName = "Database updater";

    private DataUpdaterInterface mCallback;
    private TextView mMessageTextView;
    private Context mContext;


    public DataUpdater(Context context, TextView messageTextView, DataUpdaterInterface callback) {
        this.mContext = context;
        this.mMessageTextView = messageTextView;
        this.mCallback = callback;
    }

    public void startDownload() {
        this.mMessageTextView.setText(R.string.data_updater_prepare_update);
        Retrofit retrofit = RetrofitManager.getRetrofitWithXmlConverter(SERVER_ADDRESS);
        ServerAPI api = retrofit.create(ServerAPI.class);
        Call<YmlCatalog> call = api.getServerData(sKey);
        this.mMessageTextView.setText(R.string.data_updater_call_server);
        enqueueCall(call);
    }

    private void enqueueCall(Call<YmlCatalog> call) {
        this.mMessageTextView.setText(R.string.data_updater_query_processing);
        call.enqueue(new Callback<YmlCatalog>() {
            @Override
            public void onResponse(Call<YmlCatalog> call, Response<YmlCatalog> response) {
                mMessageTextView.setText(R.string.data_updater_update_database);
                if(!SugarManager.isDbExist(mContext)) {
                    fillDatabase(response.body());
                }
            }

            @Override
            public void onFailure(Call<YmlCatalog> call, Throwable t) {
                try {
                    mMessageTextView.setText(R.string.data_updater_error_get_data);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    t.printStackTrace();
                }
            }
        });
    }

    private void onFinishUpdate() {
        mMessageTextView.setText(R.string.data_updater_database_updated);
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onFinishUpdate();
                    }
                },
                1000
        );
    }

    private void fillDatabase(final YmlCatalog body) {
        final Handler handler = new Handler();
        Runnable fillDataRunnable = new Runnable() {
            @Override
            public void run() {
                CatalogDateOrm dateOrm = new CatalogDateOrm(body.getDate());
                dateOrm.save();

                List<Category> listCategory = body.getShop().getCategories().getCategory();
                for (Category category : listCategory) {
                    CategoryOrm categoryOrm = new CategoryOrm(category.getId(), category.getCategory());
                    categoryOrm.save();
                }

                List<Offer> listOffer = body.getShop().getOffers().getOffer();
                for (Offer offer : listOffer) {
                    OfferOrm offerOrm = new OfferOrm(
                            offer.getId(),
                            offer.getUrl(),
                            offer.getName(),
                            offer.getPrice(),
                            offer.getDescription(),
                            offer.getPictureUrl(),
                            offer.getCategoryId(),
                            OfferOrm.valueOf(offer.getId(), offer.getParams())
                    );
                    offerOrm.save();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFinishUpdate();
                    }
                });
            }
        };
        Thread thread = new Thread(fillDataRunnable);
        thread.setName(sUpdaterName);
        thread.start();
    }

    public interface DataUpdaterInterface {
        void onFinishUpdate();
    }
}
