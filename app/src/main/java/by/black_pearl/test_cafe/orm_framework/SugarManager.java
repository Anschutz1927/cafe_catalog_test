package by.black_pearl.test_cafe.orm_framework;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.File;

/**
 * Created by BLACK_Pearl.
 */

public class SugarManager {

    public static boolean isDbExist(Context context) {
        String dbName = "app.db";
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA
            );
            dbName = info.metaData.getString("DATABASE");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
