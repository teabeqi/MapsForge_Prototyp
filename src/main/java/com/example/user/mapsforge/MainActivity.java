package com.example.user.mapsforge;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;


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

        //Koords von Theth
        this.mapView.setCenter(new LatLong(42.39561, 19.77352));
        this.mapView.setZoomLevel((byte) 17);

    }
}

