package packClases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import packClases.Estados.Estado;
import packClases.Ranking.comprobar;
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
		
		for(char c='A';c<='J';c++)
		{
			for (Entry<Character, List<Definicion>> e: CatalogoDefiniciones.getCatalogoDefiniciones().buscarPorLetra(c).entrySet()) 
			{
				num=(int)(Math.random()*e.getValue().size());
				listaCasillas.add(new Casilla(e.getKey(),e.getValue().get(num)));
			}
		}
		
		for(char c='L';c<='V';c++)
		{
			for (Entry<Character, List<Definicion>> e: CatalogoDefiniciones.getCatalogoDefiniciones().buscarPorLetra(c).entrySet()) 
			{
				num=(int)(Math.random()*e.getValue().size());
				listaCasillas.add(new Casilla(e.getKey(),e.getValue().get(num)));
			}
		}
		
		for(char c='X';c<='Z';c++)
		{
			for (Entry<Character, List<Definicion>> e: CatalogoDefiniciones.getCatalogoDefiniciones().buscarPorLetra(c).entrySet()) 
			{
				num=(int)(Math.random()*e.getValue().size());
				listaCasillas.add(new Casilla(e.getKey(),e.getValue().get(num)));
			}
		}
		Iterator<Casilla> it = getIterador();
		it.next().Activar(true);
	}
	
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

	public boolean comprobarRespuesta(String pRespuesta) {

		return (obtenerCasillaActiva().next().comprobarRespuesta(pRespuesta));	
	}
	
	public void pasapalabra() {

		Iterator<Casilla> it = getIterador();
		boolean encontrado=false;	
		Casilla unaCasilla=obtenerCasillaActiva().next();
		obtenerCasillaActiva().next().Activar(false);
		
		while(!encontrado && finJuego().hasNext())
		{
			if(!it.hasNext())
			{
				it=getIterador();
				System.out.println("Entro");
			}
			
			unaCasilla=it.next();
			
			if(unaCasilla.comprobarEstado()==Estado.PENDIENTE)
			{
				encontrado=true;
				unaCasilla.Activar(true);
				System.out.println(unaCasilla.getLetra());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Iterator<Casilla> finJuego(){ 
		@SuppressWarnings("rawtypes")
		Iterator it = Filter.filter(listaCasillas, new comprobar()).iterator(); 	 
		return it; 
	}
	
	@SuppressWarnings("serial")
	class comprobar extends UnaryFunctor<Casilla, Boolean>{ 
		 public Boolean fn(Casilla pCas){  
			 Estado unEstado=pCas.comprobarEstado();
			 return (unEstado.equals(Estado.PENDIENTE)); 
		 }
	}
}
