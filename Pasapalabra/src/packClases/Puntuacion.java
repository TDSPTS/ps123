package packClases;

public class Puntuacion {
	
	private String nombre;
	private int aciertos;
	private int fallos;
	private int tiempoRestante;
	
	public Puntuacion(String pNombre){
		this.nombre=pNombre;
		this.aciertos=0;
		this.fallos=0;
		this.tiempoRestante=120;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void actualizarPuntuación(int pAciertos,int pFallidos, int pTiempoRestante){
		this.aciertos=pAciertos;
		this.fallos=pFallidos;
		this.tiempoRestante=pTiempoRestante;
	}

}
