package fr.pokemonteam.pokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.model.ElementSac;
import fr.pokemonteam.pokemon.model.Pokemon;

/**
 * Created by melvin on 1/4/16.
 */
public class AdapterPokedex extends ArrayAdapter<Pokemon> {

    int layoutId;

    public AdapterPokedex(Context context, int resource, List<Pokemon> Objects) {
        super(context, resource, Objects);
        layoutId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Pokemon item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_pokemon);
        TextView numero = (TextView) convertView.findViewById(R.id.num_pokemon);
        TextView nom = (TextView) convertView.findViewById(R.id.nom_pokemon);
        numero.setText(Integer.toString(item.getNumero()));
        nom.setText(item.getNom());
        imageView.setImageResource(getContext().getResources().getIdentifier(item.getNomImage(), "drawable", getContext().getPackageName()));

        return convertView;
    }

}
