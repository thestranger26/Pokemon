package fr.pokemonteam.pokemon.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.adapter.AdapterMonEquipe;
import fr.pokemonteam.pokemon.bdd.Database;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class MonEquipeActivity extends Fragment {

    ArrayList<PokemonReel> monEquipe;
View view = null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.activity_mon_equipe,container,false);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mon_equipe);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Database db = Database.getInstance(this.getActivity());
        Utilisateur u = db.getUser(0);

        System.out.println(u.getNom());

        monEquipe = u.getEquipe();

        if (monEquipe != null) {


AdapterMonEquipe adapter = new AdapterMonEquipe(this.getActivity(), R.layout.content_mon_equipe, monEquipe);
                    ListView listView = (ListView) view.findViewById(R.id.monEquipe_listView);

                    listView.setAdapter(adapter);


        } else {
            System.out.println("C'etait null....");
        }

        return view;
    }

}
