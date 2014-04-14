package packClases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CatalogoDefiniciones {
	private static CatalogoDefiniciones mCatalogoDefiniciones = new CatalogoDefiniciones();
	private Map<Character, List<Definicion>> listaDefiniciones;

	private CatalogoDefiniciones() {
		listaDefiniciones = new HashMap<Character, List<Definicion>>();
	}

	public static CatalogoDefiniciones getCatalogoDefiniciones() {
		return mCatalogoDefiniciones;
	}
	
	public void loadData() {
		try {	
			XMLParser.getPDF2XMLParser().parseXmlFile("Preguntas.xml");
		} catch (XmlParsingException e) {
			e.printStackTrace();
		}
	}

	public void addDefinicion(Character pLetra, Definicion pDefinicion) {
		if (!listaDefiniciones.containsKey(pLetra)) {
			listaDefiniciones.put(pLetra, new ArrayList<Definicion>());
		}
		listaDefiniciones.get(pLetra).add(pDefinicion);
	}

	@Override
	public String toString() {
		return listaDefiniciones.toString();
	}
	
	public Map<Character, List<Definicion>> buscarPorLetra(char pLetra){
		Map<Character, List<Definicion>> lista=new HashMap<Character, List<Definicion>>();
		lista.put(pLetra, new ArrayList<Definicion>());
		
		for (Entry<Character, List<Definicion>> e: listaDefiniciones.entrySet()) {
				if(e.getKey()==pLetra)
				{
					for(int i=0;i<e.getValue().size();i++)
						lista.get(pLetra).add(e.getValue().get(i));
				}
	    }	
		return lista;
	}
}
