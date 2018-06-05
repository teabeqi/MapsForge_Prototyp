package com.example.user.mapsforge;

import android.app.Activity;
import android.os.Bundle;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.view.MapView;


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

    }
}