package packClases;
import java.util.ArrayList;
import java.util.Iterator;

import net.sf.jga.algorithms.Filter;
import net.sf.jga.fn.UnaryFunctor;
import packClases.Definicion.comprobar;

public class Ranking {
	
	private static Ranking mRanking=new Ranking();
	private ArrayList<Puntuacion> listaPuntuaciones;
	
	private Ranking(){
		listaPuntuaciones=new ArrayList<Puntuacion>();
	}
	
	public static Ranking getRanking(){
		return mRanking;
	}
	
	public void loadData() {
		try {	
			XMLParser.getPDF2XMLParser().parseXmlFile("Puntuaciones.xml");
		} catch (XmlParsingException e) {
			e.printStackTrace();
		}
	}
	//---------BUSCAR SI HA JUGADO YA
	@SuppressWarnings("unchecked")
	public Iterator<Puntuacion> buscarJugador(String pNombre){ 
		@SuppressWarnings("rawtypes")
		Iterator it = Filter.filter(listaPuntuaciones, new comprobar(pNombre)).iterator(); 	 
		return it; 
	}
	
	@SuppressWarnings("serial")
	class comprobar extends UnaryFunctor<Puntuacion, Boolean>{ 
		 String nombre; 
		 public comprobar(String pNombre){ 
			 nombre=pNombre; 
		 } 
		 public Boolean fn(Puntuacion pPunt){  
			 String unNombre=pPunt.getNombre();
			 return (unNombre.equalsIgnoreCase(nombre)); 
		 }
	}
	//--------------------------------------

}
