package edu.uns.galaxian.entidades.inanimadas.obstaculos;

import edu.uns.galaxian.colision.colisionadores.Visitante;
import edu.uns.galaxian.colision.colisionadores.ColisionadorBarricada;
import edu.uns.galaxian.controlador.Controlador;

public class Barricada extends Obstaculo {
	
	private static final String TEXTURA_DIR = "obstaculo/meteoro4";
	
	public Barricada(float xPos, float yPos, Controlador controlador) {
		super(xPos, yPos, TEXTURA_DIR, controlador);
		colisionador = new ColisionadorBarricada(this);
	}
	
	public void aceptarColision(Visitante colisionador) {
		colisionador.visitBarricada(this);
	}

}