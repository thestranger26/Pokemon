package fr.pokemonteam.pokemon.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Lieu;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Context context;
    private LatLng latLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        Location loc = mMap.getMyLocation();
        if (loc != null) {
            LatLng myLatLng = new LatLng(loc.getLatitude(),
                    loc.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(45.782649, 4.865827)));
        //generationPokemon();
        //generationDesLieux();
        //generationObjets();
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {
                Location loc = mMap.getMyLocation();
                if (loc != null) {
                    latLon = new LatLng(loc.getLatitude(),
                            loc.getLongitude());
                    mMap.clear();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLon));
                    generationPokemon();
                    generationDesLieux();
                    generationObjets();
                }
                return true;
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent newIntent = new Intent(MapsActivity.this, CombatActivity.class);
                newIntent.putExtra("idPokemon", marker.getTitle());
                newIntent.putExtra("latitude", marker.getPosition().latitude);
                newIntent.putExtra("longitude", marker.getPosition().longitude);
                startActivity(newIntent);

                return true;
            }
        });


    }

    private void generationObjets() {
        Location loc = mMap.getMyLocation();
        if (loc != null) {
            Random randomGenerator = new Random();
            int u = randomGenerator.nextInt(15);

            if (u>=8 && u<=11){
                LatLng objCoord = ramdomCoordonnes();
                creerMarker(objCoord.latitude,objCoord.longitude,"pokeball");
            }
            if (u>=10 && u<=13 ){
                LatLng objCoord = ramdomCoordonnes();
                creerMarker(objCoord.latitude,objCoord.longitude,"potion");
            }
        }

    }


    private void generationDesLieux() {
        Database db = Database.getInstance(this);
        ArrayList<Lieu> u = db.envoieLieu();
        for (Lieu l : u ) {
            creerMarker(l.getLatitude(),l.getLongitude(),l.getType());
        }
    }

    private void generationPokemon() {
        for (int i=0;i<5;i++){
            System.out.println("pokemon num :"+ i);
            Database db = Database.getInstance(this);
            Random randomGenerator = new Random();
            int u = randomGenerator.nextInt(151);
            LatLng pokemCoord = ramdomCoordonnes();
            creerMarker(pokemCoord.latitude,pokemCoord.longitude,"pokemon"+u);
        }


    }
    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public void creerMarker(double lat,double lon,String libelle){
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        ImageView imageView = (ImageView) marker.findViewById(R.id.imageDisplay);
        imageView.setImageResource(this.getResources().getIdentifier(libelle, "drawable", this.getPackageName()));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))).position(new LatLng(lat, lon)).title(libelle));

    }
    public LatLng ramdomCoordonnes()
    {
        LatLng coord = new LatLng(0,0);
        Random randomGenerator = new Random();
        int y = randomGenerator.nextInt(50);
        Double r= Double.parseDouble("-1");
        if (y>25) {r = Double.parseDouble("1");
        }
        Double d_1 = r*   ( Double.parseDouble( Integer.toString( randomGenerator.nextInt(8)))/ (Double.parseDouble("1000")));
        y = randomGenerator.nextInt(50);
        r= Double.parseDouble("-1");
        if (y>25) {r = Double.parseDouble("1");
        }
        Double d_2 = r* ( Double.parseDouble( Integer.toString( randomGenerator.nextInt(8)))/ (Double.parseDouble("1000")));
        coord = new LatLng(latLon.latitude+d_1,latLon.longitude+d_2);
        return  coord;
    }

}


