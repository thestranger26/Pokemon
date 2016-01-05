package fr.pokemonteam.pokemon.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import fr.pokemonteam.pokemon.model.Pokemon;
import fr.pokemonteam.pokemon.model.PokemonReel;
import fr.pokemonteam.pokemon.model.Utilisateur;

public class Database extends SQLiteOpenHelper {
    private static Database instance = null;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static Database getInstance(Context context) {
        if (instance == null)
            instance = new Database(context, "pokemon.db", null, 1);
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
            db.execSQL("CREATE TABLE pokemonReel (idPokemonReel INT, idUtilisateur INT, idPokemon INT, pseudo VARCHAR(20), equipe BOOL, atk INT, def INT, niveau INT, exp INT, longitude REAL, latitude REAL );");
            db.execSQL("CREATE TABLE infosPokemon (idPokemon INT , idTypePokemon INT, nom VARCHAR(255), description TEXT, nomImage VARCHAR(255), vue BOOL, capture BOOL);");
            db.execSQL("CREATE TABLE typePokemon (idTypePokemon INT, libelle VARCHAR(50));");
            db.execSQL("CREATE TABLE lieu (idLieu INT, libelle VARCHAR(50), typeLieu VARCHAR(50), longitude REAL, latitude REAL );");
            db.execSQL("CREATE TABLE lieuFavoris (idLieu INT, idUtilisateur INT );");
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            System.out.println("Fin de la creation de la BDD");
        }
        this.remplirDB(db);
    }

    private void remplirDB(SQLiteDatabase db ) {
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


            //// CREATION DES TYPES DE POKEMON
            values = new ContentValues();
            values.put("idTypePokemon", 0);
            values.put("Libelle", "Feuille");
            db.insert("typePokemon", null, values);

            values = new ContentValues();
            values.put("idTypePokemon", 1);
            values.put("Libelle", "Eau");
            db.insert("typePokemon", null, values);


            //// CREATION DE POKEMONS
            values = new ContentValues();
            values.put("idPokemon", 0);
            values.put("idTypePokemon", 1);
            values.put("nom", "Bulbizarre");
            values.put("description", "Bulbizarre est un petit quadrupède vert avec une tête large. Il porte un bulbe sur son dos.");
            values.put("nomImage", " ");
            values.put("vue", 1);
            values.put("capture", 1);
            db.insert("infosPokemon", null, values);

            values = new ContentValues();
            values.put("idPokemon", 1);
            values.put("idTypePokemon", 1);
            values.put("nom", "Carapuce");
            values.put("description", "Carapuce est une petite tortue bipède de couleur bleue. Il possède une carapace majoritairement brune, jaune pâle au niveau du ventre. Ses yeux sont grands et rouges. Il a une queue avec un motif de spirale à son extrémité. Il possède quatre pattes avec chacune trois doigts.");
            values.put("nomImage", " ");
            values.put("vue", 1);
            values.put("capture", 0);
            db.insert("infosPokemon", null, values);

            values = new ContentValues();
            values.put("idPokemon", 2);
            values.put("idTypePokemon", 1);
            values.put("nom", "Draco");
            values.put("description", "Draco est un long dragon sans membre, pouvant faire penser à un serpent de mer.");
            values.put("nomImage", " ");
            values.put("vue", 0);
            values.put("capture", 0);
            db.insert("infosPokemon", null, values);

            values = new ContentValues();
            values.put("idPokemon", 3);
            values.put("idTypePokemon", 1);
            values.put("nom", "Ramoloss");
            values.put("description", "Ramoloss ressemble à un hippopotame rose. Ses yeux sont vides et ses oreilles sont arrondies. Chacune de ses jambes se termine par une unique griffe blanche et sa queue ressemble à une pointe.");
            values.put("nomImage", " ");
            values.put("vue", 0);
            values.put("capture", 0);
            db.insert("infosPokemon", null, values);


            //// CREATION DE POKEMONS REELS
            values = new ContentValues();
            values.put("idPokemonReel", 0);
            values.put("idPokemon", 1);
            values.put("pseudo", "Bulbi");
            values.put("equipe", true);
            values.put("atk", 10);
            values.put("def",40);
            values.put("niveau",23);
            values.put("exp",1034);
            values.put("longitude","43.2");
            values.put("latitude","151");
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
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private void remplirDB(SQLiteDatabase db ) {

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
            Cursor c = db.query("pokemonReel", new String[]{"idPokemonReel", "idPokemon", "pseudo", "equipe", "atk", "def", "niveau", "exp", "longitude", "latitude"}, "idUtilisateur=" + idUtilisateur + " AND equipe=TRUE", null, null, null, null);


            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    PokemonReel p = new PokemonReel();
                    p.setPseudo(c.getColumnName(c.getColumnIndex("pseudo")));
                    p.setAtk(Integer.parseInt(c.getColumnName(c.getColumnIndex("atk"))));
                    p.setDef(Integer.parseInt(c.getColumnName(c.getColumnIndex("def"))));
                    p.setNiveau(Integer.parseInt(c.getColumnName(c.getColumnIndex("niveau"))));
                    p.setExp(Integer.parseInt(c.getColumnName(c.getColumnIndex("exp"))));
                    p.setLongitude(Long.parseLong(c.getColumnName(c.getColumnIndex("longitude"))));
                    p.setLatitude(Long.parseLong(c.getColumnName(c.getColumnIndex("latitude"))));

                    p.setPokemon(this.getPokemon(Integer.parseInt(c.getColumnName(c.getColumnIndex("idPokemon")))));

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
                p.setNom(c.getColumnName(c.getColumnIndex("nom")));
                p.setDescription(c.getColumnName(c.getColumnIndex("description")));
                p.setLienImage(c.getColumnName(c.getColumnIndex("nomImage")));
                p.setVue(Boolean.parseBoolean(c.getColumnName(c.getColumnIndex("vue"))));
                p.setCapture(Boolean.parseBoolean(c.getColumnName(c.getColumnIndex("capture"))));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return p;
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
