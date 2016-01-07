package fr.pokemonteam.pokemon.controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapFragment.OnFragmentInteractionListener {

    public Utilisateur u;

    Fragment fragment_map;
    Fragment fragment_equipe;
    Fragment fragment_pokedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_tristan);
        navigationView.setNavigationItemSelectedListener(this);

        Database db = Database.getInstance(this);
        u = db.getUser(0);

        TextView t_nom = (TextView) findViewById(R.id.menu_nomUser);
        TextView t_mail = (TextView) findViewById(R.id.menu_mailUser);
        TextView t_pseudo = (TextView) findViewById(R.id.menu_pseudo);
        ImageView avatar = (ImageView) findViewById(R.id.menu_imageView);

        t_nom.setText(u.getPrenom() + " " + u.getNom());
        t_pseudo.setText(u.getPseudo());
        t_mail.setText(u.getMail());
        avatar.setImageResource(R.mipmap.pika);

//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.replace(R.id.fragmentForChange, new MainActivity());
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        ft.commit();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        System.out.println("coucou");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (this.fragment_map == null) {
                this.fragment_map = new MainActivity();
            }

            ft.replace(R.id.fragmentForChange, fragment_map );
            ft.commit();
        } else if (id == R.id.nav_gallery) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (this.fragment_equipe == null) {
                this.fragment_equipe = new MonEquipeActivity();
            }
            ft.replace(R.id.fragmentForChange, fragment_equipe);

            ft.commit();
        } else if (id == R.id.nav_slideshow) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (this.fragment_pokedex == null) {
                this.fragment_pokedex = new PokedexActivity();
            }
            ft.replace(R.id.fragmentForChange, fragment_pokedex);

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
