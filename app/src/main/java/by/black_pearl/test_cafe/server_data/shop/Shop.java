package by.black_pearl.test_cafe.server_data.shop;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import by.black_pearl.test_cafe.server_data.shop.categories.Categories;
import by.black_pearl.test_cafe.server_data.shop.offers.Offers;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "shop")
public class Shop {

    @Element(name = "categories")
    private Categories categories;

    public Categories getCategories() {
        return categories;
    }

    @Element(name = "offers")
    private Offers offers;

    public Offers getOffers() {
        return offers;
    }
}
