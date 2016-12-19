package by.black_pearl.test_cafe.server_data.shop.offers.offer;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import by.black_pearl.test_cafe.server_data.shop.offers.offer.param.Param;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "offer")
public class Offer {

    @Attribute(name = "id")
    int id;

    public int getId() {
        return id;
    }

    @Element(name = "url")
    String url;

    public String getUrl() {
        return url;
    }

    @Element(name = "name")
    String name;

    public String getName() {
        return name;
    }

    @Element(name = "price")
    double price;

    public double getPrice() {
        return price;
    }

    @Element(name = "description", required = false)
    String description;

    public String getDescription() {
        return description;
    }

    @Element(name = "picture", required = false, type = String.class)
    String pictureUrl = "";

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Element(name = "categoryId")
    int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    @ElementList(name = "param", inline = true, required = false)
    List<Param> params;

    public List<Param> getParams() {
        return params;
    }
}
