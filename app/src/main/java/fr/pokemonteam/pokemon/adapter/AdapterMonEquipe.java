package fr.pokemonteam.pokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        ImageView image = (ImageView) convertView.findViewById(R.id.monEquipe_imagePokemon);
        TextView niv = (TextView) convertView.findViewById(R.id.monEquipe_nivPkm);
        TextView atk = (TextView) convertView.findViewById(R.id.monEquipe_atkPkm);
        TextView def = (TextView) convertView.findViewById(R.id.monEquipe_defPkm);
        TextView exp = (TextView) convertView.findViewById(R.id.monEquipe_expPkm);

        String label_niv = this.getContext().getString(R.string.label_niv) + " : " + pkm.getNiveau();
        String label_atk = this.getContext().getString(R.string.label_atk) + " : " + pkm.getAtk();
        String label_def = this.getContext().getString(R.string.label_def) + " : " + pkm.getDef();
        String label_exp = this.getContext().getString(R.string.label_exp) + " : " + pkm.getExp();


        niv.setText(label_niv );
        atk.setText(label_atk);
        def.setText(label_def);
        exp.setText(label_exp);
        image.setImageResource(getContext().getResources().getIdentifier(pkm.getPokemon().getNomImage(), "drawable", getContext().getPackageName()));

        if (pkm.getPseudo() == null || pkm.getPseudo().isEmpty()) {
            pseudo.setText(pkm.getPokemon().getNom());
        } else {
            pseudo.setText(pkm.getPseudo());
        }

        ProgressBar pg = (ProgressBar) convertView.findViewById(R.id.monEquipe_progressBar);
        pg.setMax(pkm.getMaxVie());
        pg.setProgress(pkm.getVieActuelle());


        return convertView;
    }
}
