package fr.pokemonteam.pokemon.controller;

import android.content.Intent;
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
        implements NavigationView.OnNavigationItemSelectedListener {

    public Utilisateur u;

    Intent fragment_map;
    Fragment fragment_equipe;
    Fragment fragment_pokedex;

    Fragment fragment_sacADos;

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


        Intent i = getIntent();
        if (i.hasExtra("fragmentToLoad")) {
            String fragmentToLoad = i.getStringExtra("fragmentToLoad");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (fragmentToLoad.equals("monEquipe")) {
                fragment_equipe = new MonEquipeActivity();
                toolbar.setTitle("@string/title_activity_home");
                ft.replace(R.id.fragmentForChange, fragment_equipe);
            } else if (fragmentToLoad.equals("pokedex")) {
                fragment_pokedex = new PokedexActivity();
                toolbar.setTitle("@string/title_activity_pokedex");
                ft.replace(R.id.fragmentForChange, fragment_pokedex);
            } else if (fragmentToLoad.equals("sacADos")) {
                fragment_sacADos = new SacActivity();
                toolbar.setTitle("@string/title_sac_activity");
                ft.replace(R.id.fragmentForChange, fragment_sacADos);
            }
            ft.commit();
        } else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment_equipe = new MonEquipeActivity();
            ft.replace(R.id.fragmentForChange, fragment_equipe);
            ft.commit();
        }
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_carte) {
            if (fragment_map == null) {
                fragment_map = new Intent(this, MapsActivity.class);
                startActivity(fragment_map);
            } else {
                startActivity(fragment_map);
            }

        } else if (id == R.id.nav_monEquipe) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (this.fragment_equipe == null) {
                this.fragment_equipe = new MonEquipeActivity();
            }
            ft.replace(R.id.fragmentForChange, fragment_equipe);

            ft.commit();
        } else if (id == R.id.nav_pokedex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (this.fragment_pokedex == null) {
                this.fragment_pokedex = new PokedexActivity();
            }
            ft.replace(R.id.fragmentForChange, fragment_pokedex);

            ft.commit();
        } else if (id == R.id.nav_sacADos) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (this.fragment_sacADos == null) {
                this.fragment_sacADos = new SacActivity();
            }
            ft.replace(R.id.fragmentForChange, fragment_sacADos);

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
