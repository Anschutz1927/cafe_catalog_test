package by.black_pearl.test_cafe.server_data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import by.black_pearl.test_cafe.server_data.shop.Shop;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "yml_catalog")
public class YmlCatalog {
    @Attribute(name = "date")
    private String date;

    public String getDate() {
        return date;
    }

    @Element(name = "shop")
    private Shop shop;

    public Shop getShop() {
        return shop;
    }
}
