package packClases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import packClases.Estados.Estado;
import net.sf.jga.algorithms.Filter;
import net.sf.jga.fn.UnaryFunctor;

public class Rosco {
	
	private static Rosco mRosco=new Rosco();
	private ArrayList<Casilla> listaCasillas;
	
	private Rosco()	{
		listaCasillas=new ArrayList<Casilla>();
	}
	
	public static Rosco getRosco() {
		return mRosco;
	}
	
	private Iterator<Casilla> getIterador(){
		return this.listaCasillas.iterator();
	}
	
	public void cargarCasillas()
	{	
		int num;
		
		for(char c='A';c<='Z';c++)
		{
			for (Entry<Character, List<Definicion>> e: CatalogoDefiniciones.getCatalogoDefiniciones().buscarPorLetra(c).entrySet()) 
			{
				num=(int)(Math.random()*e.getValue().size());
				listaCasillas.add(new Casilla(e.getKey(),e.getValue().get(num)));
			}
		}
	}
	
	//BUSCAR CASILLA ACTIVA
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator<Casilla> obtenerCasillaActiva(){ 
		Iterator it = Filter.filter(listaCasillas, new obtener()).iterator(); 	 
		return it; 
	}
	
	@SuppressWarnings("serial")
	class obtener extends UnaryFunctor<Casilla, Boolean>{ 
		public Boolean fn(Casilla pCas){ 
			 return (pCas.estaActiva()); 
	    } 
	}
	//----------------------------------------------
	
	
	private boolean comprobarRespuesta(String pRespuesta) {
		boolean acertada;
		
		acertada=obtenerCasillaActiva().next().comprobarRespuesta(pRespuesta);
		obtenerCasillaActiva().next().Activar(false);
	
		return acertada;		
	}
	
	private void pasapalabra() {

		Iterator<Casilla> it = getIterador();
		boolean encontrado=false;
		Casilla unaCasilla=obtenerCasillaActiva().next();
	
		while(!encontrado && it.hasNext())
		{
			unaCasilla=it.next();
			if(unaCasilla.comprobarEstado()==Estado.PENDIENTE)
			{
				encontrado=true;
				unaCasilla.Activar(true);
			}
			
		}
	}
	
	
	
}
