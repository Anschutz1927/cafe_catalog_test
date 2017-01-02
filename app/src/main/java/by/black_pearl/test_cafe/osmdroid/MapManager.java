package by.black_pearl.test_cafe.osmdroid;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by BLACK_Pearl.
 */

public class MapManager implements LocationListener {

    private boolean isNewPosition = false;

    @Override
    public void onLocationChanged(Location location) {
        if(location == null) {
            return;
        }
        if(!isNewPosition) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
