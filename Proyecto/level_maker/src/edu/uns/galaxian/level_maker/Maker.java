package edu.uns.galaxian.level_maker;

import com.badlogic.gdx.Game;
import com.google.gson.*;
import edu.uns.galaxian.entidades.enemigo.fabrica.FabricaEnemigos;
import edu.uns.galaxian.entidades.enemigo.fabrica.FabricaEstandar;
import edu.uns.galaxian.juego.config.GameData;
import edu.uns.galaxian.servicios.FormacionEnemigo;
import edu.uns.galaxian.servicios.GeneracionObstaculo;
import edu.uns.galaxian.servicios.Oleada;
import edu.uns.galaxian.util.io.gson.GSONClassSerializer;
import edu.uns.galaxian.util.io.IOManager;

import java.io.IOException;


public class Maker extends Game {

    private FabricaEnemigos FABRICA_ENEMIGOS;

    public void create(){
        FABRICA_ENEMIGOS = new FabricaEstandar();

        IOManager io = IOManager.getInstance();
        Gson gson = getGson();
        JsonArray gameData = getGameData();

        // Creacion de FABRICA JSON
        JsonElement fabricaEnemigo = gson.toJsonTree(FABRICA_ENEMIGOS);

        // A partir de aca las cosas se tienen que hacer para cada nivel
        JsonArray nivelJson = new JsonArray();

        // Oleada 1
        JsonObject oleadaJson = new JsonObject();
            // Oleada principal
            oleadaJson.add(GameData.OLEADA, gson.toJsonTree(FormacionEnemigo.class, Oleada.class));
            // Decorators
            JsonArray oleadaDecorators = new JsonArray();
            oleadaDecorators.add(gson.toJsonTree(GeneracionObstaculo.class, Oleada.class));
            oleadaJson.add(GameData.DECORATORS, oleadaDecorators);
            // Configuracion
            JsonObject oleadaConfig = new JsonObject();
            oleadaConfig.add(GameData.FABRICA, gson.toJsonTree(FabricaEstandar.class, FabricaEnemigos.class));
            oleadaConfig.addProperty(GameData.FORMACION, "A partir de aca inserte un arreglo de arreglos con los tipos de enemigos que desea");
            oleadaJson.add(GameData.CONFIG_OLEADA, oleadaConfig);
        nivelJson.add(oleadaJson);

        try {
            gameData.add(nivelJson);
            io.escribirArchivoComoString(GameData.DIR, gameData.toString(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Oleada.class, new GSONClassSerializer<>());
        builder.registerTypeAdapter(FabricaEnemigos.class, new GSONClassSerializer<>());
        return builder.create();
    }

    private JsonArray getGameData(){
        IOManager io = IOManager.getInstance();
        JsonParser parser = new JsonParser();
        JsonArray gameData;
        try{
            String contenido = io.leerArchivo(GameData.DIR, true);
            gameData = parser.parse(contenido).getAsJsonArray();
        }catch (IOException e){
            gameData = new JsonArray();
        }
        return gameData;
    }
}
