package by.black_pearl.test_cafe.osmdroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.IconOverlay;
import org.osmdroid.views.overlay.Overlay;

import by.black_pearl.test_cafe.BuildConfig;

/**
 * Created by BLACK_Pearl.
 */

public class MapManager implements LocationListener {

    private final Context mContext;
    private final MapView mMapView;
    private boolean isNewPosition = false;

    public MapManager(Context context, MapView mapView) {
        this.mContext = context;
        this.mMapView = mapView;
        addMapToMapView();
    }

    private void addMapToMapView() {
        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        this.mMapView.setTileSource(TileSourceFactory.MAPNIK);
        enableZoomControls();
    }

    public void enableZoomControls() {
        this.mMapView.setBuiltInZoomControls(true);
        this.mMapView.setMultiTouchControls(true);
    }

    public void setCenterAt(double lat, double lon) {
        IMapController controller = this.mMapView.getController();
        IGeoPoint point = new GeoPoint(lat, lon);
        controller.setCenter(point);
        controller.setZoom(17);
        setOverlayToMap(lat, lon);
    }

    private void setOverlayToMap(double lat, double lon) {
        Drawable drawable_finish =
                ContextCompat.getDrawable(mContext, org.osmdroid.library.R.drawable.marker_default);
        Overlay item_finish = new IconOverlay(new GeoPoint(lat, lon), drawable_finish);
        this.mMapView.getOverlays().add(item_finish);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location == null) {
            return;
        }
        if(!isNewPosition) {
            IMapController controller = mMapView.getController();
            controller.setZoom(8);
        }
        Drawable drawable_start =
                ContextCompat.getDrawable(mContext, org.osmdroid.library.R.drawable.person);
        Overlay item_start = new IconOverlay(
                new GeoPoint(location.getLatitude(), location.getLongitude()), drawable_start
        );
        mMapView.getOverlays().add(item_start);
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
