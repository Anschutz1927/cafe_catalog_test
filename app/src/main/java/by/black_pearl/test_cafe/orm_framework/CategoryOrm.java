package by.black_pearl.test_cafe.orm_framework;

import com.orm.SugarRecord;

/**
 * Created by BLACK_Pearl.
 */

public class CategoryOrm extends SugarRecord {

    int categoryId;
    String category;

    public CategoryOrm() {}

    public CategoryOrm(int id, String category) {
        this.categoryId = id;
        this.category = category;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public String getCategory() {
        return category;
    }
}
