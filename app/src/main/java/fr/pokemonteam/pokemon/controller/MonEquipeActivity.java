package fr.pokemonteam.pokemon.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterMonEquipe;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class MonEquipeActivity extends Fragment {

    private ArrayList<PokemonReel> monEquipe;
    private View view = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.activity_mon_equipe, container, false);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mon_equipe);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Toolbar t = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        t.setTitle(R.string.title_activity_my_team);


        Database db = Database.getInstance(this.getActivity());
        Utilisateur u = db.getUser(0);

        System.out.println(u.getNom());

        monEquipe = u.getEquipe();

        if (monEquipe != null) {


            AdapterMonEquipe adapter = new AdapterMonEquipe(this.getActivity(), R.layout.content_mon_equipe, monEquipe);
            final ListView listView = (ListView) view.findViewById(R.id.monEquipe_listView);

            listView.setAdapter(adapter);

            ((ListView) view.findViewById(R.id.monEquipe_listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MonEquipeActivity.this.afficheInfosPokemon(position);
                }
            });

        } else {
            System.out.println("C'etait null....");
        }

        return view;
    }

    private void afficheInfosPokemon(int position) {


        Intent monIntent = new Intent(MonEquipeActivity.this.getActivity().getBaseContext(), DetailPokemonMonEquipeActivity.class);
        monIntent.putExtra("idPkmnReel", position);
        startActivity(monIntent);

    }

    public void suppPokemon(int position) {
        int id_pokemon = MonEquipeActivity.this.monEquipe.get(position).getId();

        Database db = Database.getInstance(this.getActivity());
        // db.suppPokemonEquipe(id_pokemon);
        System.out.println(this.monEquipe.size());
        this.monEquipe.remove(position);
        System.out.println(this.monEquipe.size());

        AdapterMonEquipe adapter = new AdapterMonEquipe(this.getActivity(), R.layout.content_mon_equipe, monEquipe);
        final ListView listView = (ListView) view.findViewById(R.id.monEquipe_listView);

        listView.setAdapter(adapter);

    }
}
