package edu.uns.galaxian.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.*;
import edu.uns.galaxian.juego.screen.nivel.DirectorNivel;
import edu.uns.galaxian.juego.screen.menuprincipal.MenuPrincipal;
import edu.uns.galaxian.util.EntidadBatch;
import edu.uns.galaxian.juego.config.GameData;
import edu.uns.galaxian.juego.config.SaveData;
import static edu.uns.galaxian.util.enums.Asset.*;

public class Juego extends Game {

	private AssetManager assetManager;
	private EntidadBatch batch;
	private GameData gameData;
	private SaveData saveData;
	private int nivelActual;

	@Override
	public void create () {
		batch = new EntidadBatch();
		gameData = new GameData();
		saveData = new SaveData();
		nivelActual = saveData.getNivelAlcanzado();
		cargarAssets();

		setScreen(new MenuPrincipal(this));
	}

	/**
	 * Finaliza el juego luego de haber completado
	 * todos los niveles
	 */
	public void finalizarJuego(){
		// TODO Mostrar alguna pantalla de finalizacion
	}

	/**
	 * Verifica si el nivel actual es el ultimo del
	 * juego, en cuyo caso retorna verdadero.
	 * @return Verdadero si el nivel actual es el ultimo
	 */
	public boolean seAlcanzoUltimoNivel(){
		return nivelActual == gameData.getCantidadNiveles();
	}

	/**
	 * Verifica si el jugador supero el primer nivel del juego
	 * @return Verdadero si el jugador supero el primer nivel
	 */
	public boolean seSuperoElPrimerNivel(){
		return nivelActual > 1;
	}

	/**
	 * Carga el nivel siguiente al actual.
	 * @throws IllegalStateException Si no hay mas nivel
	 */
	public void cargarSiguienteNivel() throws IllegalStateException{
		if(nivelActual == gameData.getCantidadNiveles()){
			throw new IllegalStateException("No hay mas niveles para cargar.");
		}
		nivelActual++;
		saveData.setNivelAlcanzado(nivelActual);
		new DirectorNivel(this, gameData.getNivel(nivelActual), saveData.getNaveJugador());
	}

	/**
	 * Inicia el nivel actual.
	 */
	public void iniciarNivelActual(){
		new DirectorNivel(this, gameData.getNivel(nivelActual), saveData.getNaveJugador());
	}

	/**
	 * Inicia el primer nivel del juego
	 */
	public void iniciarPrimerNivel(){
		nivelActual = 1;
		saveData.setNivelAlcanzado(nivelActual);
		new DirectorNivel(this, gameData.getNivel(nivelActual), saveData.getNaveJugador());
	}

	/**
	 * Vuelve al menu principal del juego
	 */
	public void volverAlMenuPrincipal(){
		setScreen(new MenuPrincipal(this));
	}

	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Devuelve el EntidadBatch del juego.
	 * @return EntidadBatch del juego
	 */
	public EntidadBatch getBatch(){
		return batch;
	}

	/**
	 * Devuelve el AssetManager del juego
	 * @return AssetManager del juego
	 */
	public AssetManager getAssetManager(){
		return assetManager;
	}

	private void cargarAssets(){
		assetManager = new AssetManager();

		// Carga de texturas
		assetManager.load(ATLAS_NAVES.valor(), TextureAtlas.class);
		assetManager.load(ATLAS_OBSTACULOS.valor(), TextureAtlas.class);
		assetManager.load(ATLAS_DISPAROS.valor(), TextureAtlas.class);
		assetManager.load(ATLAS_POWERUP.valor(), TextureAtlas.class);
		assetManager.load(ATLAS_UI.valor(), TextureAtlas.class);
		assetManager.load(LOGO_TEXTURE.valor(), Texture.class);

		// Carga de fuentes
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		FreeTypeFontLoaderParameter normalFont = new FreeTypeFontLoaderParameter();
		normalFont.fontFileName = FONT_16.valor();
		normalFont.fontParameters.size = 16;
		assetManager.load(FONT_16.valor(), BitmapFont.class, normalFont);
		FreeTypeFontLoaderParameter biggerFont = new FreeTypeFontLoaderParameter();
		biggerFont.fontFileName = FONT_24.valor();
		biggerFont.fontParameters.size = 24;
		assetManager.load(FONT_24.valor(), BitmapFont.class, biggerFont);

		// Carga de audio
		assetManager.load(AUDIO_START.valor(), Sound.class);
		assetManager.load(AUDIO_FOCUS.valor(), Sound.class);
		assetManager.load(MAIN_THEME.valor(), Music.class);
		assetManager.finishLoading();
	}
}
