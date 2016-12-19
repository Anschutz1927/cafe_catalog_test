package by.black_pearl.test_cafe.orm_framework;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by BLACK_Pearl.
 */

public class ParamOrm extends SugarRecord {

    @Ignore
    public static final String WEIGHT = "Вес";

    int offerId;
    String paramName;
    String paramValue;

    public ParamOrm() {}

    public ParamOrm(int offerId, String paramName, String paramValue) {
        this.offerId = offerId;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return this.paramName;
    }

    public String getParamValue() {
        return this.paramValue;
    }
}
