package packClases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jga.algorithms.Filter;
import net.sf.jga.fn.UnaryFunctor;


public class Definicion {
	private String enunciado;
	private List<String> listaRespuestas;
	
	public Definicion(String pPreg) {
		enunciado = pPreg;
		listaRespuestas = new ArrayList<String>();
	}
	
	@Override
	public String toString() {
		String texto = String.format("Enunciado: %1$s\n", enunciado);
		for (String resp : listaRespuestas) {
			texto += String.format("\t\tRespuesta: %1$s\n", resp);
		}
		return texto;
	}

	public void addRespuesta(String pRes) {
		listaRespuestas.add(pRes);
	}
	
	public String obtenerEnunciado(){
		return this.enunciado;
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<String> comprobarRespuesta(String pRespuesta){ 
		 @SuppressWarnings("rawtypes")
		Iterator it = Filter.filter(listaRespuestas, new comprobar(pRespuesta)).iterator(); 	 
		return it; 
	}
	
	@SuppressWarnings("serial")
	class comprobar extends UnaryFunctor<String, Boolean>{ 
		 String respuesta; 
		 public comprobar(String pRespuesta){ 
			 respuesta=pRespuesta; 
		 } 
		 public Boolean fn(String pResp){  
			 return (pResp.equalsIgnoreCase(respuesta)); 
		 }
	}
}
