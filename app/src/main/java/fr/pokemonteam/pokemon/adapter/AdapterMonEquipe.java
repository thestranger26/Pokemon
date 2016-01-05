package fr.pokemonteam.pokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.model.PokemonReel;

/**
 * Created by Tristan on 05/01/16.
 */
public class AdapterMonEquipe extends ArrayAdapter<PokemonReel> {

    int layoutId;

    public AdapterMonEquipe(Context context, int resource, List<PokemonReel> objects) {
        super(context, resource, objects);
        layoutId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PokemonReel pkm = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }
        TextView pseudo = (TextView) convertView.findViewById(R.id.monEquipe_pseudoPkm);
        TextView nom = (TextView) convertView.findViewById(R.id.monEquipe_nomPokemon);
        TextView niv = (TextView) convertView.findViewById(R.id.monEquipe_niveauPkm);
        TextView atk = (TextView) convertView.findViewById(R.id.monEquipe_atkPkm);
        TextView def = (TextView) convertView.findViewById(R.id.monEquipe_defPkm);
        TextView exp = (TextView) convertView.findViewById(R.id.monEquipe_expPkm);

        niv.setText(Integer.toString(pkm.getNiveau()));
        atk.setText(atk.getText() + Integer.toString(pkm.getAtk()));
        def.setText(def.getText() + Integer.toString(pkm.getDef()));
        exp.setText(exp.getText() + Integer.toString(pkm.getExp()));


        if(pkm.getPseudo() == null || pkm.getPseudo().isEmpty()) {
            nom.setText("");
            pseudo.setText(pkm.getPokemon().getNom());
        } else {
            pseudo.setText(pkm.getPseudo());
            nom.setText(pkm.getPokemon().getNom());
        }

        ProgressBar pg = (ProgressBar) convertView.findViewById(R.id.monEquipe_progressBar);
        pg.setMax(pkm.getMaxVie());
        pg.setProgress(pkm.getVieActuelle());


        return convertView;
    }
}
