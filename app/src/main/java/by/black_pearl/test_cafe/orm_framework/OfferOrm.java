package by.black_pearl.test_cafe.orm_framework;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import by.black_pearl.test_cafe.server_data.shop.offers.offer.param.Param;

/**
 * Created by BLACK_Pearl.
 */

public class OfferOrm extends SugarRecord {

    int offerId;
    String url;
    String name;
    double price;
    String description;
    String pictureUrl;
    int categoryId;
    ArrayList<ParamOrm> params;

    public OfferOrm() {}

    public OfferOrm(
            int offerId,
            String url,
            String name,
            double price,
            String description,
            String pictureUrl,
            int categoryId,
            ArrayList<ParamOrm> params
    ) {
        this.offerId = offerId;
        this.url = url;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.categoryId = categoryId;
        this.params = params;
    }

    public int getOfferId() {
        return this.offerId;
    }

    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public List<ParamOrm> getParams() {
        return ParamOrm.find(ParamOrm.class, "offer_id = ?", String.valueOf(offerId));
    }

    @Override
    public long save() {
        if(this.params != null && this.params.size() > 0)
        {
            for (ParamOrm paramOrm : this.params) {
                paramOrm.save();
            }
        }
        return super.save();
    }

    public static ArrayList<ParamOrm> valueOf(int offerId, List<Param> params) {
        if(params == null || params.size() == 0) {
            return null;
        }
        ArrayList<ParamOrm> arrayList = new ArrayList<>();
        for (by.black_pearl.test_cafe.server_data.shop.offers.offer.param.Param param : params) {
            arrayList.add(new ParamOrm(offerId, param.getName(), param.getParamValue()));
        }
        return arrayList;
    }

    public static String getParamNameAndParamValueByParamName(List<ParamOrm> params, String name) {
        String param_value = null;
        for (ParamOrm paramOrm : params) {
            if (paramOrm != null && paramOrm.getParamName().equals(name)) {
                param_value = name + ": " + paramOrm.getParamValue();
                break;
            }
        }
        return param_value;
    }
}
