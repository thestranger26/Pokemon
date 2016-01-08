package fr.pokemonteam.pokemon.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fr.pokemonteam.pokemon.model.Element;
import fr.pokemonteam.pokemon.model.ElementSac;
import fr.pokemonteam.pokemon.model.Lieu;
import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class Database extends SQLiteOpenHelper {
    private static Database instance = null;
    private static ArrayList<HashMap<String, String>> pokemons = null;


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context, "pokemon.db", null, 1);
            pokemons = getAllPokemonFromJson(context);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creation de la BDD");
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE utilisateur (idUtilisateur INT, pseudo VARCHAR(20), mail VARCHAR(50), motDePasse VARCHAR(255), nom VARCHAR(20), prenom VARCHAR(20));");
            db.execSQL("CREATE TABLE sacADos (idUtilisateur INT, idElement INT, nombre INT);");
            db.execSQL("CREATE TABLE element (idElement INT, libelle VARCHAR(50), effet VARCHAR(255), image VARCHAR(255));");
            db.execSQL("CREATE TABLE pokemonReel (idPokemonReel INTEGER primary key autoincrement not null , idUtilisateur INT, idPokemon INT, pseudo VARCHAR(20), equipe BOOL, atk INT, def INT, niveau INT, exp INT, longitude REAL, latitude REAL, vieActuelle INT, maxVie INT );");
            db.execSQL("CREATE TABLE infosPokemon (idPokemon INT , type VARCHAR(255), numero INT, nom VARCHAR(255), attaque INT, defense INT, pv INT, nomImage VARCHAR(255), vue BOOL, capture BOOL, tauxCapture INT);");
            db.execSQL("CREATE TABLE lieu (idLieu INT, libelle VARCHAR(50), typeLieu VARCHAR(50), longitude REAL, latitude REAL );");
            db.execSQL("CREATE TABLE lieuFavoris (idLieu INT, idUtilisateur INT );");
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            System.out.println("Fin de la creation de la BDD");
        }
        try {
            db.beginTransaction();

            //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

            //// CREATION DE L'UTILISATEUR
            ContentValues values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("pseudo", "Regis");
            values.put("nom", "Professeur Chen");
            values.put("prenom", "Fils du");
            values.put("mail", "a@a");
            values.put("motDePasse", "aaaaa");
            db.insert("utilisateur", null, values);

            //// CREATION DE POKEMONS

            for (int i = 0; i < pokemons.size(); i++) {
                values = new ContentValues();
                values.put("idPokemon", i);
                values.put("type", pokemons.get(i).get("Type"));
                values.put("nom", pokemons.get(i).get("Nom"));
                values.put("attaque", pokemons.get(i).get("Défense"));
                values.put("defense", pokemons.get(i).get("Attaque"));
                values.put("pv", pokemons.get(i).get("PV"));
                values.put("nomImage", pokemons.get(i).get("Image"));
                values.put("vue", 0);
                values.put("capture", 0);
                values.put("numero", pokemons.get(i).get("Numéro"));
                values.put("tauxCapture", pokemons.get(i).get("Taux Capture"));
                db.insert("infosPokemon", null, values);
            }

            //// CREATION DE POKEMONS REELS

            values = new ContentValues();
            values.put("idPokemon", 1);
            values.put("idUtilisateur", 0);
            values.put("pseudo", "Pupuce");
            values.put("equipe", true);
            values.put("atk", 50);
            values.put("def", 40);
            values.put("niveau", 60);
            values.put("exp", 18657);
            values.put("longitude", "43.2");
            values.put("latitude", "151");
            values.put("vieActuelle", 130);
            values.put("maxVie", 130);
            db.insert("pokemonReel", null, values);

            values = new ContentValues();
            values.put("idPokemon", 2);
            values.put("idUtilisateur", 0);
            values.put("pseudo", "Dracouille");
            values.put("equipe", true);
            values.put("atk", 45);
            values.put("def", 23);
            values.put("niveau", 50);
            values.put("exp", 13400);
            values.put("longitude", "43.2");
            values.put("latitude", "151");
            values.put("vieActuelle", 30);
            values.put("maxVie", 130);
            db.insert("pokemonReel", null, values);

            values = new ContentValues();
            values.put("idPokemon", 3);
            values.put("idUtilisateur", 0);
            values.put("equipe", true);
            values.put("atk", 10);
            values.put("def", 50);
            values.put("niveau", 25);
            values.put("exp", 5400);
            values.put("longitude", "43.2");
            values.put("latitude", "151");
            values.put("vieActuelle", 90);
            values.put("maxVie", 130);
            db.insert("pokemonReel", null, values);

            //// CREATION D'ELEMENTS POUR LE SAC A DOS
            values = new ContentValues();
            values.put("idElement", 0);
            values.put("libelle", "pokeball");
            values.put("effet", "Pour capturer des petits pokemons");
            values.put("image", "pokeball");
            db.insert("element", null, values);

            values = new ContentValues();
            values.put("idElement", 1);
            values.put("libelle", "potion");
            values.put("effet", "Pour guérir ton petit mignon tout doux pokemon adoré <3 <3 <3 XO XO XO ");
            values.put("image", "potion");
            db.insert("element", null, values);


            // CREATION DU SAC A DOS DU USER
            values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("idElement", 0);
            values.put("nombre", 3);
            db.insert("sacADos", null, values);

            values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("idElement", 1);
            values.put("nombre", 5);
            db.insert("sacADos", null, values);

            // CREATION DE LIEUX
            values = new ContentValues();
            values.put("idLieu", 0);
            values.put("libelle", "CPE");
            values.put("typeLieu", "maison");
            values.put("latitude", 45.784807);
            values.put("longitude", 4.869069);
            db.insert("lieu", null, values);

            values = new ContentValues();
            values.put("idLieu", 1);
            values.put("libelle", "Home");
            values.put("typeLieu", "hopital");
            values.put("latitude", 45.770255);
            values.put("longitude", 4.869100);
            db.insert("lieu", null, values);


            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db.getVersion() == oldVersion) db.setVersion(newVersion);
    }

    public Utilisateur getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Utilisateur user = new Utilisateur(id);
        try {
            db.beginTransaction();

            Cursor c = db.query("utilisateur", new String[]{"pseudo", "nom", "prenom", "mail"}, "idUtilisateur=" + id, null, null, null, null);


            if (c.getCount() == 1) {
                c.moveToFirst();
                user.setPseudo(c.getString(c.getColumnIndex("pseudo")));
                user.setNom(c.getString(c.getColumnIndex("nom")));
                user.setPrenom(c.getString(c.getColumnIndex("prenom")));
                user.setMail(c.getString(c.getColumnIndex("mail")));
            } else {
                System.out.println("Il y a eu une petite erreur qque part.... " + c.getCount());
            }

            user.setEquipe(this.getEquipeUtilisateur(id));

            user.setSacADos(this.getSacADos(id));
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return user;
    }

    private ArrayList<PokemonReel> getEquipeUtilisateur(int idUtilisateur) {
        ArrayList<PokemonReel> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = db.query("pokemonReel", new String[]{"idPokemonReel", "idPokemon", "pseudo", "equipe", "atk", "def", "niveau", "exp", "longitude", "latitude", "maxVie", "vieActuelle"}, "idUtilisateur=" + idUtilisateur + " AND equipe=1", null, null, null, null);


            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    PokemonReel p = new PokemonReel();

                    p.setId(c.getInt(c.getColumnIndex("idPokemonReel")));
                    p.setPseudo(c.getString(c.getColumnIndex("pseudo")));
                    p.setAtk(c.getInt(c.getColumnIndex("atk")));
                    p.setDef(c.getInt(c.getColumnIndex("def")));
                    p.setNiveau(c.getInt(c.getColumnIndex("niveau")));
                    p.setExp(c.getInt(c.getColumnIndex("exp")));
                    p.setLongitude(c.getLong(c.getColumnIndex("longitude")));
                    p.setLatitude(c.getLong(c.getColumnIndex("latitude")));
                    p.setVieActuelle(c.getInt(c.getColumnIndex("vieActuelle")));
                    p.setMaxVie(c.getInt(c.getColumnIndex("maxVie")));

                    p.setPokemon(this.getPokemon(c.getInt(c.getColumnIndex("idPokemon"))));

                    liste.add(p);

                } while (c.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return liste;
    }

    public ArrayList<Pokemon> getPokedex() {
        ArrayList<Pokemon> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = db.query("infosPokemon", new String[]{"idPokemon", "type", "numero", "nom", "attaque", "defense", "pv", "nomImage", "vue", "capture", "tauxCapture"}, null, null, null, null, null);


            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Pokemon p = new Pokemon();

                    p.setId(c.getInt(c.getColumnIndex("idPokemon")));
                    p.setType(c.getString(c.getColumnIndex("type")));
                    p.setNumero(c.getInt(c.getColumnIndex("numero")));
                    p.setNom(c.getString(c.getColumnIndex("nom")));
                    p.setAttaque(c.getInt(c.getColumnIndex("attaque")));
                    p.setDefense(c.getInt(c.getColumnIndex("defense")));
                    p.setPv(c.getInt(c.getColumnIndex("pv")));
                    p.setNomImage(c.getString(c.getColumnIndex("nomImage")));
                    p.setVue(c.getInt(c.getColumnIndex("vue")) == 1);
                    p.setCapture(c.getInt(c.getColumnIndex("capture")) == 1);
                    p.setTauxCapture(c.getInt(c.getColumnIndex("tauxCapture")));

                    liste.add(p);

                } while (c.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return liste;
    }

    public void updatePokemont(Pokemon pokemon) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("type", pokemon.getType());
        values.put("nom", pokemon.getNom());
        values.put("attaque", pokemon.getAttaque());
        values.put("defense", pokemon.getDefense());
        values.put("pv", pokemon.getPv());
        values.put("nomImage", pokemon.getNomImage());
        values.put("vue", pokemon.getVue());
        values.put("capture", pokemon.getCapture());
        values.put("numero", pokemon.getNumero());
        values.put("tauxCapture", pokemon.getTauxCapture());
        db.update("infosPokemon", values, "idPokemon=" + pokemon.getId(), null);
    }

    public void setPokemonReel(PokemonReel pokemon, int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("idPokemon", pokemon.getPokemon().getId());
        values.put("idUtilisateur", idUser);
        values.put("equipe", true);
        values.put("atk", pokemon.getAtk());
        values.put("def", pokemon.getDef());
        values.put("niveau", pokemon.getNiveau());
        values.put("exp", pokemon.getExp());
        values.put("longitude", pokemon.getLongitude());
        values.put("latitude", pokemon.getLatitude());
        values.put("vieActuelle", pokemon.getVieActuelle());
        values.put("maxVie", pokemon.getMaxVie());
        db.insert("pokemonReel", null, values);
    }

    public void updatePokemonReel(PokemonReel pokemon, int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("idPokemonReel", pokemon.getId());
        values.put("idPokemon", pokemon.getPokemon().getId());
        values.put("idUtilisateur", idUser);
        values.put("equipe", true);
        values.put("atk", pokemon.getAtk());
        values.put("def", pokemon.getDef());
        values.put("niveau", pokemon.getNiveau());
        values.put("exp", pokemon.getExp());
        values.put("longitude", pokemon.getLongitude());
        values.put("latitude", pokemon.getLatitude());
        values.put("vieActuelle", pokemon.getVieActuelle());
        values.put("maxVie", pokemon.getMaxVie());
        db.update("pokemonReel", values, "idPokemonReel=" + pokemon.getId(), null);
    }


    public Pokemon getPokemon(int idPokemon) {

        Pokemon p = new Pokemon();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = db.query("infosPokemon", new String[]{"idPokemon", "type", "numero", "nom", "attaque", "defense", "pv", "nomImage", "vue", "capture", "tauxCapture"}, "idPokemon=" + idPokemon, null, null, null, null);

            p.setId(idPokemon);
            if (c.getCount() == 1) {
                c.moveToFirst();
                p.setType(c.getString(c.getColumnIndex("type")));
                p.setNumero(c.getInt(c.getColumnIndex("numero")));
                p.setNom(c.getString(c.getColumnIndex("nom")));
                p.setAttaque(c.getInt(c.getColumnIndex("attaque")));
                p.setDefense(c.getInt(c.getColumnIndex("defense")));
                p.setPv(c.getInt(c.getColumnIndex("pv")));
                p.setNomImage(c.getString(c.getColumnIndex("nomImage")));
                p.setVue(c.getInt(c.getColumnIndex("vue")) == 1);
                p.setCapture(c.getInt(c.getColumnIndex("capture")) == 1);
                p.setTauxCapture(c.getInt(c.getColumnIndex("tauxCapture")));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return p;
    }

    public ArrayList<ElementSac> getSacADos(int idUtilisateur) {

        ArrayList<ElementSac> liste = new ArrayList<ElementSac>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = db.query("sacADos", new String[]{"idUtilisateur", "idElement", "nombre"}, "idUtilisateur=" + idUtilisateur, null, null, null, null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Element e = getElementById(c.getInt(c.getColumnIndex("idElement")));
                    ElementSac es = new ElementSac();

                    es.setElement(e);
                    es.setNombre(c.getInt(c.getColumnIndex("nombre")));

                    liste.add(es);

                } while (c.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return liste;
    }

    public void updateSacADos(int idUser, ArrayList<ElementSac> list) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            values.put("idUtilisateur", idUser);
            values.put("idElement", list.get(i).getElement().getId());
            values.put("nombre", list.get(i).getNombre());
            db.update("sacADos", values, "idUtilisateur=" + idUser, null);
            values.clear();
        }
    }

    private Element getElementById(int idElement) {
        Element el = new Element();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = db.query("element", new String[]{"idElement", "libelle", "effet", "image"}, "idElement=" + idElement, null, null, null, null);

            el.setId(idElement);
            if (c.getCount() == 1) {
                c.moveToFirst();
                el.setLibelle(c.getString(c.getColumnIndex("libelle")));
                el.setEffet(c.getString(c.getColumnIndex("effet")));
                el.setImage(c.getString(c.getColumnIndex("image")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return el;
    }

    private static ArrayList<HashMap<String, String>> getAllPokemonFromJson(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(fr.pokemonteam.pokemon.R.raw.pokemons);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("Pokémons");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String num = jo_inside.getString("Numéro");
                String image = "pokemon" + (i + 1);
                String nom = jo_inside.getString("Nom");
                String pv = jo_inside.getString("PV");
                String atk = jo_inside.getString("Attaque");
                String def = jo_inside.getString("Défense");
                String type = jo_inside.getString("Type");
                String tauxCapture = jo_inside.getString("Catch rate");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("Numéro", num);
                m_li.put("Image", image);
                m_li.put("Nom", nom);
                m_li.put("PV", pv);
                m_li.put("Attaque", atk);
                m_li.put("Défense", def);
                m_li.put("Type", type);
                m_li.put("Taux Capture", tauxCapture);

                formList.add(m_li);
            }

            return formList;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean verifieDonneesUsers(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean ret = false;
        try {
            db.beginTransaction();
            Cursor c = db.query("utilisateur", new String[]{"idUtilisateur", "nom"}, "mail=\"" + email + "\" AND motDePasse=\"" + password + "\"", null, null, null, null);
            if (c.getCount() == 1) ret = true;
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return ret;
    }

    public ArrayList<Lieu> envoieLieu() {
        ArrayList<Lieu> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean ret = false;
        try {
            db.beginTransaction();
            Cursor c = db.query("lieu", new String[]{"idLieu", "libelle", "typeLieu", "longitude", "latitude"}, null, null, null, null, null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    Lieu l = new Lieu();

                    l.setId(c.getInt(c.getColumnIndex("idLieu")));
                    l.setLibelle(c.getString(c.getColumnIndex("libelle")));
                    l.setType(c.getString(c.getColumnIndex("typeLieu")));
                    l.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
                    l.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
                    liste.add(l);

                } while (c.moveToNext());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return liste;
    }


    public void suppPokemonEquipe(int id_pokemon) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            db.beginTransaction();
            ContentValues c = new ContentValues();
            c.put("equipe", 0);
            db.update("pokemonReel",c,"idPokemonReel="+id_pokemon,null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }
//

//    public void insertTweets(Tweet t) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            db.beginTransaction();
//
//            ContentValues values = new ContentValues();
//            //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
//            values.put("date", t.getDate());
//            values.put("user", t.getUser());
//            values.put("text", t.getText());
//            values.put("id", t.getId());
//            values.put("fav", t.getJaime());
//            values.put("retweet", t.getRetweet());
//
//            if (t.isGeoloc()) {
//                values.put("geoloc", 1);
//            } else {
//                values.put("geoloc", 0);
//            }
//
//            db.insert("tweets", null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//        }
//    }
//
//    public List<Tweet> getAllTweets() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        List<Tweet> ret = new ArrayList<Tweet>();
//
//        try {
//            db.beginTransaction();
//            Cursor c = db.query("tweets", new String[]{"date", "user", "text","retweet","id","fav","geoloc"}, null, null, null, null, null);
//            System.out.println(c.getCount());
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//
//                do {
//                    Tweet t = new Tweet();
//                    t.setDate(c.getString(c.getColumnIndex("date")));
//                    t.setUser(c.getString(c.getColumnIndex("user")));
//                    t.setText(c.getString(c.getColumnIndex("text")));
//                    t.setRetweet(c.getInt(c.getColumnIndex("retweet")));
//                    t.setId(c.getLong(c.getColumnIndex("id")));
//                    t.setJaime(c.getInt(c.getColumnIndex("fav")));
//                    if (c.getInt(c.getColumnIndex("geoloc")) == 1) {
//                        t.setGeoloc(true);
//                    } else {
//                        t.setGeoloc(false);
//                    }
//
//
//                    ret.add(t);
//
//                } while (c.moveToNext());
//            }
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//            return ret;
//        }
//    }
//
//    public void deleteAllTweets() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        try {
//            db.beginTransaction();
//            db.delete("tweets", null, null);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//        }
//    }
}
