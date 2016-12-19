package by.black_pearl.test_cafe.server_data.shop.categories.category;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "category")
public class Category {

    @Attribute(name = "id")
    int id;

    public int getId() {
        return id;
    }

    @Text
    private String category;

    public String getCategory() {
        return category;
    }

    public Category() {}
}
