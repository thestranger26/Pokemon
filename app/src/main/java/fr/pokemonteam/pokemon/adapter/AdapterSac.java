package fr.pokemonteam.pokemon.Adapter;

import android.content.Context;
import android.renderscript.Element;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.pokemonteam.pokemon.R;
import fr.pokemonteam.pokemon.model.ElementSac;

/**
 * Created by melvin on 1/4/16.
 */
public class AdapterSac extends ArrayAdapter<ElementSac> {

    int layoutId;

    public AdapterSac(Context context, int resource, List<ElementSac> Objects) {
        super(context, resource);
        layoutId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ElementSac item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        TextView libelle = (TextView) convertView.findViewById(R.id.element_libelle);
        TextView nombre = (TextView) convertView.findViewById(R.id.element_nombre);
        libelle.setText(item.getElement().getLibelle());
        nombre.setText(item.getNombre());

        return convertView;
    }

}
