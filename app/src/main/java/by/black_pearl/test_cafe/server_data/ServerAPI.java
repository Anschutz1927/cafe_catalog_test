package by.black_pearl.test_cafe.server_data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by BLACK_Pearl.
 */

public interface ServerAPI {
    @GET("/getyml/")
    Call<ResponseBody> getFarforXml(@Query("key") String key);

    @GET("/getyml/")
    Call<YmlCatalog> getServerData(@Query("key") String key);

    @GET
    Call<ResponseBody> getImage(@Url String url);

}
