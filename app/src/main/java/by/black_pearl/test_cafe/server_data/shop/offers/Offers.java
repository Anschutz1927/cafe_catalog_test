package by.black_pearl.test_cafe.server_data.shop.offers;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import by.black_pearl.test_cafe.server_data.shop.offers.offer.Offer;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "offers")
public class Offers {

    @ElementList(name = "offer", inline = true)
    List<Offer> offers;

    public List<Offer> getOffer() {
        return offers;
    }
}
