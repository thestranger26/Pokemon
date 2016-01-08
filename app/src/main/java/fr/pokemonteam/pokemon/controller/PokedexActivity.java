package fr.pokemonteam.pokemon.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterPokedex;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.Pokemon;

public class PokedexActivity extends Fragment {

    private ArrayList<Pokemon> pokemons = null;
    private View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setContentView(R.layout.activity_pokedex);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if (view == null)
            view = inflater.inflate(R.layout.activity_pokedex,
                    container, false);

        Database db = Database.getInstance(this.getActivity());
        pokemons = db.getPokedex();

        if (pokemons != null) {

            AdapterPokedex adapter = new AdapterPokedex(this.getActivity(), R.layout.content_pokedex, pokemons);
            ListView listView = (ListView) view.findViewById(R.id.list_pokedex);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pokemon pokemon = (Pokemon) parent.getItemAtPosition(position);
                    Intent monIntent = new Intent(PokedexActivity.this.getActivity().getBaseContext(), PokedexDetailActivity.class);
                    monIntent.putExtra("pokemon", pokemon);
                    startActivity(monIntent);
                }
            });
        } else

        {
            System.out.println("C'etait null....");
        }
        return view;
    }

}
