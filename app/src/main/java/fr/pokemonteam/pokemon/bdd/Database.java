package fr.pokemonteam.pokemon.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.common.api.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.pokemonteam.pokemon.R;
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
            db.execSQL("CREATE TABLE utilisateur (idUtilisateur INT, pseudo VARCHAR(20), nom VARCHAR(20), prenom VARCHAR(20));");
            db.execSQL("CREATE TABLE sacADos (idUtilisateur INT, idElement INT, nombre INT);");
            db.execSQL("CREATE TABLE element (idElement INT, libelle VARCHAR(50), effet VARCHAR(255));");
            db.execSQL("CREATE TABLE pokemonReel (idPokemonReel INT, idUtilisateur INT, idPokemon INT, pseudo VARCHAR(20), equipe BOOL, atk INT, def INT, niveau INT, exp INT, longitude REAL, latitude REAL, vieActuelle INT, maxVie INT );");
            db.execSQL("CREATE TABLE infosPokemon (idPokemon INT , type INT, numero INT, nom VARCHAR(255), attaque INT, defense INT, pv INT, nomImage VARCHAR(255), vue BOOL, capture BOOL);");
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
            db.insert("utilisateur", null, values);

            //// CREATION DE POKEMONS
            for(int i = 0; i < pokemons.size(); i++) {
                values = new ContentValues();
                values.put("idPokemon", i);
                values.put("type", pokemons.get(0).get("Type"));
                values.put("nom", pokemons.get(0).get("Nom"));
                values.put("attaque", pokemons.get(0).get("Défense"));
                values.put("defense", pokemons.get(0).get("Attaque"));
                values.put("pv", pokemons.get(0).get("PV"));
                values.put("nomImage", pokemons.get(0).get("Nom"));
                values.put("vue", 0);
                values.put("capture", 0);
                values.put("numero", pokemons.get(0).get("Numéro"));
                db.insert("infosPokemon", null, values);
            }

            //// CREATION DE POKEMONS REELS

            values = new ContentValues();
            values.put("idPokemonReel", 0);
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
            values.put("idPokemonReel", 1);
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
            values.put("idPokemonReel", 2);
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
            db.insert("element", null, values);

            values = new ContentValues();
            values.put("idElement", 1);
            values.put("libelle", "superball");
            values.put("effet", "Pour capturer des petits pokemons un peu plus grand");
            db.insert("element", null, values);

            values = new ContentValues();
            values.put("idElement", 2);
            values.put("libelle", "megaball");
            values.put("effet", "Pour capturer des pokemons très grand");
            db.insert("element", null, values);

            values = new ContentValues();
            values.put("idElement", 3);
            values.put("libelle", "potion");
            values.put("effet", "Pour guérir ton petit mignon tout doux pokemon adoré <3 <3 <3 XO XO XO ");
            db.insert("element", null, values);


            // CREATION DU SAC A DOS DU USER
            values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("idElement", 3);
            values.put("nombre", 3);
            db.insert("sacADos", null, values);

            values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("idElement", 1);
            values.put("nombre", 10);
            db.insert("sacADos", null, values);

            values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("idElement", 0);
            values.put("nombre", 25);
            db.insert("sacADos", null, values);

            values = new ContentValues();
            values.put("idUtilisateur", 0);
            values.put("idElement", 2);
            values.put("nombre", 3);
            db.insert("sacADos", null, values);

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

            Cursor c = db.query("utilisateur", new String[]{"pseudo", "nom", "prenom"}, "idUtilisateur=" + id, null, null, null, null);


            if (c.getCount() == 1) {
                c.moveToFirst();
                user.setPseudo(c.getString(c.getColumnIndex("pseudo")));
                user.setNom(c.getString(c.getColumnIndex("nom")));
                user.setPrenom(c.getString(c.getColumnIndex("prenom")));
            } else {
                System.out.println("Il y a eu une petite erreur qque part.... " + c.getCount());
            }

            user.setEquipe(this.getEquipeUtilisateur(id));
            //
            // user.setSacADos();
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

    private Pokemon getPokemon(int idPokemon) {

        Pokemon p = new Pokemon();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            Cursor c = db.query("infosPokemon", new String[]{"idPokemon", "nom", "description", "nomImage", "vue", "capture"}, "idPokemon=" + idPokemon, null, null, null, null);

            p.setId(idPokemon);
            if (c.getCount() == 1) {
                c.moveToFirst();
                p.setNom(c.getString(c.getColumnIndex("nom")));
                p.setDescription(c.getString(c.getColumnIndex("description")));
                p.setLienImage(c.getString(c.getColumnIndex("nomImage")));

                if (c.getInt(c.getColumnIndex("vue")) == 1) {
                    p.setVue(true);
                } else {
                    p.setVue(false);
                }
                if (c.getInt(c.getColumnIndex("capture")) == 1) {
                    p.setCapture(true);
                } else {
                    p.setCapture(false);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return p;
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
                String image = "pokemon" + (i+1);
                String nom = jo_inside.getString("Nom");
                String pv = jo_inside.getString("PV");
                String atk = jo_inside.getString("Attaque");
                String def = jo_inside.getString("Défense");
                String type = jo_inside.getString("Type");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("Numéro", num);
                m_li.put("Image", image);
                m_li.put("Nom", nom);
                m_li.put("PV", pv);
                m_li.put("Attaque", atk);
                m_li.put("Défense", def);
                m_li.put("Type", type);

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
