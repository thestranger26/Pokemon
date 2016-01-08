package fr.pokemonteam.pokemon.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Lieu;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private Context context;

    final float ICONE_POKEMON = BitmapDescriptorFactory.HUE_YELLOW;
    final float ICONE_UTILISATEUR = BitmapDescriptorFactory.HUE_BLUE;
    final float ICONE_LIEU = BitmapDescriptorFactory.HUE_GREEN;

    HashMap<String, ArrayList<String>> listeMarkers = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        System.out.println("Apell à la BDD");
        Database db = Database.getInstance(this);
        Utilisateur u = db.getUser(0);
        System.out.println(u.getNom());
        System.out.println("BDD OK");


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_tristan);
        mapFragment.getMapAsync(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Carte");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_tristan);
        navigationView.setNavigationItemSelectedListener(this);

        TextView t_nom = (TextView) findViewById(R.id.menu_nomUser);
        TextView t_mail = (TextView) findViewById(R.id.menu_mailUser);
        TextView t_pseudo = (TextView) findViewById(R.id.menu_pseudo);
        ImageView avatar = (ImageView) findViewById(R.id.menu_imageView);

        t_nom.setText(u.getPrenom() + " " + u.getNom());
        t_pseudo.setText(u.getPseudo());
        t_mail.setText(u.getMail());
        avatar.setImageResource(R.mipmap.pika);

    }

    @Override
    protected void onResume() {
        super.onResume();
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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(45.782649, 4.865827)));
        generationPokemon();
        generationDesLieux();
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {
                Location loc = mMap.getMyLocation();
                if (loc != null) {
                    LatLng myLatLng = new LatLng(loc.getLatitude(),
                            loc.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
                }
                return true;
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent newIntent = new Intent(MapsActivity.this, CombatActivity.class);
                newIntent.putExtra("idPokemon", Integer.parseInt(marker.getTitle()));
                newIntent.putExtra("latitude", marker.getPosition().latitude);
                newIntent.putExtra("longitude", marker.getPosition().longitude);
                startActivity(newIntent);

                return true;
            }
        });


    }

    public PokemonReel genererPokemon(double latitude, double longitude) {
        PokemonReel pkmn = new PokemonReel();
        Database db = Database.getInstance(this);
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

    public int generateRandomValue(int val) {
        Random randomGenerator = new Random();
        double coeff = randomGenerator.nextDouble() * (1.5 - 0.5) + 0.5;
        double ret = val * coeff;
        return (int) ret;
    }

    private void changeMarker(Marker marker) {
        ArrayList<String> a = listeMarkers.get(marker.getId());
        if (a.get(1).equals("vueMkr")) {
            View v_marker = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_pokemon_layout, null);
            TextView numTxt = (TextView) v_marker.findViewById(R.id.mkrPkm_nomPkm);
            numTxt.setText("27");
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, v_marker)));
            a.set(1, "vueInfos");
            listeMarkers.put(marker.getId(), a);
        } else {
            a.set(1, "vueMkr");
            listeMarkers.put(marker.getId(), a);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(ICONE_POKEMON));
        }
    }

    private void generationDesLieux() {
        Database db = Database.getInstance(this);
        ArrayList<Lieu> u = db.envoieLieu();
        for (Lieu l : u) {
            System.out.println("*********************");
            System.out.println(l.toString());
            System.out.println("*********************");
            creerMarker(l.getLatitude(),l.getLongitude(),l.getType(), 0);
        }
    }

    private void generationPokemon() {
        LatLng myLatLng = new LatLng(45.782649, 4.865827);

        for (int i = 0; i < 5; i++) {
            System.out.println("pokemon num :" + i);

            Database db = Database.getInstance(this);
            Random randomGenerator = new Random();
            int u = randomGenerator.nextInt(151);
            int y = randomGenerator.nextInt(50);

            Double r = Double.parseDouble("-1");
            if (y > 25) {
                r = Double.parseDouble("1");
            }
            Double d_1 = r * (Double.parseDouble(Integer.toString(randomGenerator.nextInt(8))) / (Double.parseDouble("1000")));
            y = randomGenerator.nextInt(50);
            r = Double.parseDouble("-1");
            if (y > 25) {
                r = Double.parseDouble("1");
            }
            Double d_2 = r* ( Double.parseDouble( Integer.toString( randomGenerator.nextInt(8)))/ (Double.parseDouble("1000")));
            creerMarker(myLatLng.latitude+d_1,myLatLng.longitude+d_2, "pokemon"+(u+1), u);
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

    public void creerMarker(double lat,double lon,String libelle, int id){
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        ImageView imageView = (ImageView) marker.findViewById(R.id.imageDisplay);
        imageView.setImageResource(this.getResources().getIdentifier(libelle, "drawable", this.getPackageName()));
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))).position(new LatLng(lat, lon)).title(Integer.toString(id)));

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.se_deconnecter) {

            Intent newIntent = new Intent(this, LoginActivity.class);
            startActivity(newIntent);

            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        System.out.println("coucou");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_carte) {

        } else if (id == R.id.nav_monEquipe) {

            Intent monIntent = new Intent(this, HomeActivity.class);
            monIntent.putExtra("fragmentToLoad", "monEquipe");
            startActivity(monIntent);
        } else if (id == R.id.nav_pokedex) {
            Intent monIntent = new Intent(this, HomeActivity.class);
            monIntent.putExtra("fragmentToLoad", "pokedex");
            startActivity(monIntent);
        } else if (id == R.id.nav_sacADos) {

            Intent monIntent = new Intent(this, HomeActivity.class);
            monIntent.putExtra("fragmentToLoad", "sacADos");
            startActivity(monIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
