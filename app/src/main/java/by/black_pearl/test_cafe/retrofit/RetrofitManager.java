package by.black_pearl.test_cafe.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by BLACK_Pearl.
 */

public class RetrofitManager {
    /**
     * @param address Address of server.
     * @param createXmlConverter True to add Retrofit SimpleXmlConverter.
     * @return Retrofit object.
     */
    private static Retrofit newInstance(String address, boolean createXmlConverter) {
        if(createXmlConverter) {
            return new Retrofit.Builder()
                    .baseUrl(address)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return new Retrofit.Builder()
                .baseUrl(address)
                .build();
    }

    /**
     * New Retrofit object without converter.
//     * @param address Address of server.
     * @return Retrofit object.
     */
    public static Retrofit getRetrofit(String address) {
        return newInstance(address, false);
    }

    /**
     * New Retrofit object with converter.
     * @param address Address of server.
     * @return Retrofit object.
     */
    public static Retrofit getRetrofitWithXmlConverter(String address) {
        return newInstance(address, true);
    }
}
