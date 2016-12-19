package by.black_pearl.test_cafe.orm_framework;

import com.orm.SugarRecord;

/**
 * Created by BLACK_Pearl.
 */

public class CatalogDateOrm extends SugarRecord {

    String catalogDate;

    public CatalogDateOrm() {}

    public CatalogDateOrm(String date) {
        this.catalogDate = date;
    }
}
