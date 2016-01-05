package fr.pokemonteam.pokemon.model;

/**
 * Created by Tristan on 04/01/16.
 */
public class ElementSac {
    Element element;
    int nombre;

    public ElementSac(Element element, int nombre) {
        this.element = element;
        this.nombre = nombre;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
