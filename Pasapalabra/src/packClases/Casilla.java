package packClases;

import packClases.Estados.Estado;

public class Casilla {
	
	private char letra;
	private Definicion definicion;
	private Estado estado;
	private boolean activa;
	
	public Casilla(char pLetra,Definicion pDefinicion){
		this.letra=pLetra;
		this.definicion=pDefinicion;
		this.estado=Estado.PENDIENTE;
		this.activa=false;
	}
	
	public char getLetra(){
		return this.letra;
	}
	
	public void setEstado(boolean pEstado)
	{
		if (pEstado)
			this.estado=Estado.ACERTADA;
		else
			this.estado=Estado.FALLIDA;
	}
	
	public Estado comprobarEstado()
	{
		return this.estado;
	}
	
	public boolean comprobarRespuesta(String pRespuesta){
		if(definicion.comprobarRespuesta(pRespuesta).hasNext())
		{
			setEstado(false);
			return false;
		}
		else
		{
			setEstado(true);
			return true;
		}
	}
	
	public boolean estaActiva(){
		return activa;
	}
	
	public void Activar(boolean pActiva){
		this.activa=pActiva;
	}
	
	public String obtenerEnunciado(){
		return this.definicion.obtenerEnunciado();	
	}
	

}
