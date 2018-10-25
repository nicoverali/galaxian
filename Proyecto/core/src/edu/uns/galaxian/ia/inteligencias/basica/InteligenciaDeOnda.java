package edu.uns.galaxian.ia.inteligencias.basica;

import com.badlogic.gdx.math.Vector2;

import edu.uns.galaxian.ia.autonomo.Autonomo;
import edu.uns.galaxian.ia.InteligenciaArtificial;

public class InteligenciaDeOnda<T extends Autonomo> implements InteligenciaArtificial<T> {

	private T autonomo;

	public InteligenciaDeOnda(T autonomo){
		this.autonomo = autonomo;
	}

	public void pensar(float delta) {
		float posX = autonomo.getPosicion().x + 1 ;
		float posY = (float) Math.abs((autonomo.getPosicion().y + 2*Math.sin(posX/40)));
		Vector2 nuevaPos = new Vector2(posX,posY);
		autonomo.setPosicion(nuevaPos);
	}

	public void transicionar(InteligenciaArtificial<T> nuevaInteligencia) {
		autonomo.setInteligencia(nuevaInteligencia);
	}
}
