package com.example.user.mapsforge;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import org.mapsforge.core.graphics.Color;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Polygon;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    // name of the map file in the external storage
    private static final String MAP_FILE = "download/albania.map";
    // mapView variable deklarieren
    private MapView mapView;

    // onCreate Methode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // neue Instance erstellen
        AndroidGraphicFactory.createInstance(this.getApplication());
        // mapView initialisieren
        this.mapView = new MapView(this);
        // Karte auf den Activity anzeigen
        setContentView(this.mapView);
        // was man mit der Karte machen kann
        this.mapView.setClickable(true);
        this.mapView.getMapScaleBar().setVisible(true);
        this.mapView.setBuiltInZoomControls(true);
        this.mapView.setZoomLevelMin((byte) 10);
        this.mapView.setZoomLevelMax((byte) 20);

        // ein tile cache von anpassendes Größe erstellen
        TileCache tileCache = AndroidUtil.createTileCache(this, "mapcache",
                mapView.getModel().displayModel.getTileSize(), 1f,
                this.mapView.getModel().frameBufferModel.getOverdrawFactor());

        // tile renderer layer vewwendet internal render theme
        // data pfad sehen und kontrollieren
        MapDataStore mapDataStore = new MapFile(new File(Environment.getExternalStorageDirectory(), MAP_FILE));

        TileRendererLayer tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                this.mapView.getModel().mapViewPosition, AndroidGraphicFactory.INSTANCE);
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.DEFAULT);

        // only once a layer is associated with a mapView the rendering starts
        this.mapView.getLayerManager().getLayers().add(tileRendererLayer);

        //Koords of Theth
        this.mapView.setCenter(new LatLong(42.39561, 19.77352));
        this.mapView.setZoomLevel((byte) 17);

        drawPath();

    }
    // Route erstellen
    private void drawPath() {
        LatLong latLong1 = new LatLong(42.39561, 19.77352);
        LatLong latLong2 = new LatLong(42.39632, 19.77311);
        LatLong latLong3 = new LatLong(42.39762, 19.77208);
        LatLong latLong4 = new LatLong(42.39795, 19.77187);
        LatLong latLong5 = new LatLong(42.39816, 19.77319);
        // Route zeichnen
        Paint paint = Utils.createPaint(
                AndroidGraphicFactory.INSTANCE.createColor(Color.BLUE),
                (int) (8 * mapView.getModel().displayModel.getScaleFactor()),
                Style.STROKE);

        Polyline polyline = new Polyline(paint, AndroidGraphicFactory.INSTANCE);
        // eine List mitt Longitudes erstellen
        List<LatLong> latLongs = polyline.getLatLongs();
        latLongs.add(latLong1);
        latLongs.add(latLong2);
        latLongs.add(latLong3);
        latLongs.add(latLong4);
        latLongs.add(latLong5);


        Paint paintFill2 = Utils.createPaint(
                AndroidGraphicFactory.INSTANCE.createColor(Color.GREEN), 2,
                Style.FILL);
        paintFill2.setBitmapShader(AndroidGraphicFactory.convertToBitmap(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? getDrawable(R.drawable.green_marker) : getResources().getDrawable(R.drawable.green_marker)));

        Paint paintStroke2 = Utils.createPaint(
                AndroidGraphicFactory.INSTANCE.createColor(Color.BLACK), 2,
                Style.STROKE);
        Polygon polygonWithShaderNonAligned = new Polygon(paintFill2, paintStroke2,
                AndroidGraphicFactory.INSTANCE);
        List<LatLong> latLongs4 = polygonWithShaderNonAligned.getLatLongs();
        latLongs4.add(latLong1);
        latLongs4.add(latLong2);
        latLongs4.add(latLong5);


    }

    public void getLoc() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }
}

