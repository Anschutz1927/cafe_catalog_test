package by.black_pearl.test_cafe.server_data.shop.categories;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import by.black_pearl.test_cafe.server_data.shop.categories.category.Category;

/**
 * Created by BLACK_Pearl.
 */

@Root(name = "categories")
public class Categories {

    @ElementList(name = "category", inline = true)
    private List<Category> categories;

    public List<Category> getCategory() {
        return categories;
    }
}
