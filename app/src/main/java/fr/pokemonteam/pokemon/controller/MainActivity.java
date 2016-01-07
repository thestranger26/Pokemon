package fr.pokemonteam.pokemon.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;


public class MainActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    final float ICONE_POKEMON = BitmapDescriptorFactory.HUE_YELLOW;
    final float ICONE_UTILISATEUR = BitmapDescriptorFactory.HUE_BLUE;
    final float ICONE_LIEU = BitmapDescriptorFactory.HUE_GREEN;

    HashMap<String, ArrayList<String>> listeMarkers = new HashMap<>();
    View view = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.content_main,
                    container, false);

//        SupportMapFragment mapFragment = (SupportMapFragment) this.getFragmentManager().findFragmentById(R.id.map_test_tristan);
//        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("Bla");

//        SupportMapFragment mapFragment = (SupportMapFragment) this.getFragmentManager()
//                .findFragmentById(R.id.map_test_tristan);
//        mapFragment.getMapAsync(this);
//        FragmentManager fm = getChildFragmentManager();
//        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_test_tristan);
//        if (fragment == null) {
//            fragment = SupportMapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.map_test_tristan, fragment)
//                    .commit();
//        }
        SupportMapFragment j = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_test_tristan));
        j.getMapAsync(this);
//        GoogleMap map = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_test_tristan))
//                .getMap();
//
//
//        System.out.println("Config de la map");
//        //setContentView(R.layout.activity_main);
//        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        map.setMyLocationEnabled(true);
//
//        Double longitude_villeurbanne = 45.782843;
//        Double latitude_villeurbanne = 4.874071;
//
//        LatLng campusVilleurbanne = new LatLng(longitude_villeurbanne, latitude_villeurbanne);
//
//        //  mMap.addMarker(new MarkerOptions().position(campusVilleurbanne).title("Campus").snippet("Coucou"));
//
//        map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(campusVilleurbanne, 15)));
//
//
//        LocationManager locationManager;
//        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//        ;
//        criteria.setAltitudeRequired(false);
//        criteria.setBearingRequired(false);
//        criteria.setSpeedRequired(false);
//        criteria.setCostAllowed(true);
//
//        String provider1 = locationManager.getBestProvider(criteria, true);
//        Location l = locationManager.getLastKnownLocation(provider1);
//        Marker m_user = null;
//
//        if (l != null) {
//            System.out.println("Long act = " + l.getLongitude() + " / lat : " + l.getLatitude());
//
//
//            m_user = map.addMarker(new MarkerOptions()
//                            .position(new LatLng(l.getLongitude(), l.getLatitude()))
//                            .title("Ma position")
//                            .icon(BitmapDescriptorFactory.defaultMarker(ICONE_UTILISATEUR))
//
//            );
//            ArrayList<String> b = new ArrayList<>();
//            b.add(0, "User");
//            b.add(1, "vueMkr");
//            listeMarkers.put(m_user.getId(), b);
//        } else {
//            System.out.println("Pas de gps");
//        }
//
//
//        View marker = ((LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_pokemon_layout, null);
//        TextView numTxt = (TextView) marker.findViewById(R.id.mkrPkm_nomPkm);
//        numTxt.setText("27");
//
//        Marker m_2 = map.addMarker(new MarkerOptions()
//                        .position(campusVilleurbanne)
//                        .icon(BitmapDescriptorFactory.defaultMarker(ICONE_POKEMON))
//        );
//
//        ArrayList<String> c = new ArrayList<>();
//        c.add(0, "Pokemon");
//        c.add(1, "vueMkr");
//        listeMarkers.put(m_2.getId(), c);
//
//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                MainActivity.this.changeMarker(marker);
//                return true;
//            }
//        });
//
//
//        map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(campusVilleurbanne, 15)));
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                System.out.println(location.getLongitude() + " lat : " + location.getLatitude());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        locationManager.requestLocationUpdates(provider1, 2000, 10, locationListener);
//
//        //Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        GoogleMap.OnInfoWindowClickListener markerClic = new GoogleMap.OnInfoWindowClickListener() {
//
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//
//            }
//        };
//
//        map.setOnInfoWindowClickListener(markerClic);

//        System.out.println("Apell à la BDD");
////        Database db = Database.getInstance(this);
////        Utilisateur u = db.getUser(0);
//        System.out.println(u.getNom());
//        System.out.println("BDD OK");

        //setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // MapFragment maps = this.getActivity().

        // mapFragment.getMapAsync(this.getActivity());


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        GoogleMap.OnInfoWindowClickListener markerClic = new GoogleMap.OnInfoWindowClickListener() {
//
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//
//            }
//        };
//
//        mMap.setOnInfoWindowClickListener(markerClic);

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
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("Config de la map");
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        Double longitude_villeurbanne = 45.782843;
        Double latitude_villeurbanne = 4.874071;

        LatLng campusVilleurbanne = new LatLng(longitude_villeurbanne, latitude_villeurbanne);

        //  mMap.addMarker(new MarkerOptions().position(campusVilleurbanne).title("Campus").snippet("Coucou"));

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(campusVilleurbanne, 15)));


        LocationManager locationManager;
        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        ;
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        String provider1 = locationManager.getBestProvider(criteria, true);
        Location l = locationManager.getLastKnownLocation(provider1);
        Marker m_user = null;

        if (l != null) {
            System.out.println("Long act = " + l.getLongitude() + " / lat : " + l.getLatitude());


            m_user = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(l.getLongitude(), l.getLatitude()))
                            .title("Ma position")
                            .icon(BitmapDescriptorFactory.defaultMarker(ICONE_UTILISATEUR))

            );
            ArrayList<String> b = new ArrayList<>();
            b.add(0, "User");
            b.add(1, "vueMkr");
            listeMarkers.put(m_user.getId(), b);
        } else {
            System.out.println("Pas de gps");
        }


        View marker = ((LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_pokemon_layout, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.mkrPkm_nomPkm);
        numTxt.setText("27");

        Marker m_2 = mMap.addMarker(new MarkerOptions()
                        .position(campusVilleurbanne)
                        .icon(BitmapDescriptorFactory.defaultMarker(ICONE_POKEMON))
        );

        ArrayList<String> c = new ArrayList<>();
        c.add(0, "Pokemon");
        c.add(1, "vueMkr");
        listeMarkers.put(m_2.getId(), c);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MainActivity.this.changeMarker(marker);
                return true;
            }
        });


        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(campusVilleurbanne, 15)));
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println(location.getLongitude() + " lat : " + location.getLatitude());
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
        };

        locationManager.requestLocationUpdates(provider1, 2000, 10, locationListener);

        //Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        GoogleMap.OnInfoWindowClickListener markerClic = new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        };

        mMap.setOnInfoWindowClickListener(markerClic);

    }

    public PokemonReel genererPokemon(double latitude, double longitude ){
        PokemonReel pkmn = new PokemonReel();
        Database db = Database.getInstance(this.getActivity());
        Random randomGenerator = new Random();
        Pokemon p = db.getPokemon(randomGenerator.nextInt(151));
        pkmn.setAtk(generateRandomValue(p.getAttaque()));
        pkmn.setDef(generateRandomValue(p.getDefense()));
        pkmn.setMaxVie(generateRandomValue(p.getPv()));
        pkmn.setExp(0);
        pkmn.setVieActuelle(pkmn.getMaxVie());
        pkmn.setNiveau(randomGenerator.nextInt(100));
        pkmn.setLatitude(latitude);
        pkmn.setLongitude(longitude);
        return pkmn;
    }

    public int generateRandomValue(int val){
        Random randomGenerator = new Random();
        double coeff = randomGenerator.nextDouble()*(1.5-0.5) + 0.5;
        double ret = val*coeff;
        return (int) ret;
    }

    private void changeMarker(Marker marker) {
        ArrayList<String> a = listeMarkers.get(marker.getId());
        if (a.get(1).equals("vueMkr")) {
            View v_marker = ((LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_pokemon_layout, null);
            TextView numTxt = (TextView) v_marker.findViewById(R.id.mkrPkm_nomPkm);
            numTxt.setText("27");
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this.getActivity(), v_marker)));
            a.set(1, "vueInfos");
            listeMarkers.put(marker.getId(), a);
        } else {
            a.set(1, "vueMkr");
            listeMarkers.put(marker.getId(), a);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(ICONE_POKEMON));
        }
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

}
