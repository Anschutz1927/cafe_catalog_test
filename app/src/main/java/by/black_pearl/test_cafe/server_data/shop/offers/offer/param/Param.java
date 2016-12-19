package by.black_pearl.test_cafe.server_data.shop.offers.offer.param;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "param")
public class Param {

    @Attribute(name = "name")
    String name;

    public String getName() {
        return name;
    }

    @Text
    String param;

    public String getParamValue() {
        return param;
    }
}
