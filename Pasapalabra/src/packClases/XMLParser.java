package packClases;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLParser extends DefaultHandler {
	private static XMLParser mXMLParser = new XMLParser();
	private String texto = null;
	private Character letra = null;
	private Definicion definicionActual = null;
	
    //Atributo que contiene la factoría de tags
	private TagOperatorFactory operatorFactory=new TagOperatorFactory();
	
	private XMLParser() {
		//Constructora de la clase XMLParser. Implementación por defecto		
	}

	public static XMLParser getPDF2XMLParser() {
		//Método de acceso a la única instancia de XMLParser
		return mXMLParser;
	}

	public void parseXmlFile(String pFile)
			throws XmlParsingException {
		// Método que procesa el fichero XML
		// Utiliza el patrón Factory para obtener la instancia de parser
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		//saxParserFactori es una instancia de la factoría SAXParserFactory
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			//Crea una nueva instancia de la clase SAXParser utilizando los parámetros de la factoría
			//previamente construida
			/*
			 * Procesa el fichero XML. Le pasamos la instancia
			 * que procesa las etiquetas, documento, etc.
			 * Tiene que proporcionar los métodos que se implementan
			 * debajo
			 */
			saxParser.parse(new FileInputStream(pFile), this);
		} catch (Exception e) {
			throw new XmlParsingException(e);
		}
	}

	public void characters(char[] pCh, int pStart, int pLength)
			throws SAXException {
		//Este método de la clase SAXParser se invoca cuando SAX encuentra texto dentro de un elemento.
		//Es frecuente que el texto esté formado por varios caracteres, por lo que NO se lee todo de una pasada
        //y cada caracter leido debe ir añadiédose para construir el texto final.
 
		//Para construir el String correspondiente al texto contenido entre un tag de inicio y su correspondiente
		//tag de finalización:
		texto = new String(pCh, pStart, pLength).trim();		
	}

	public void endDocument() throws SAXException {
		//Notifica el final de un documento XML. Aquí podeis incluir lo que quereis hacer al finalizar la revisión del
        //documento XML. En el caso que nos ocupa podeis imprimir en la salida estándar un mensaje informativo,
        //diciendo que finaliza el proceso de parseo
		System.out.println("Finished processing the file");
	}

	public void endElement(String pUri, String pLocalName, String pName)
			throws SAXException {
	    //Notifica el final de un elemento. Se ejecuta cuando SAX lee un tag de finalización: </nombreTag>
	    //El parámetro pName contiene el nombre del tag
	    //En nuestro caso, el fichero XML contiene varios tags distintos, por lo que el proceso a ejecutar cuando se
	    //detecte un tag de finalización será diferente para cada uno de ellos.
	    //Para simplificar y generalizar el código de este método utilizaremos el patrón Symple Factory:
	    //a) Definiremos una clase privada interna que implemente la factoría de tags
	    //b) Definiremos una clase privada interna para cada tag que aparece en el fichero XML.
	    //c) Estas clases privadas correspondientes a los diferentes tag descienden de una interfaz común en la que
		//   se especifica el método invoqueEnd(). Este método se implementará en todas las clases, y contendrá las
		//   instrucciones a ejecutar cada vez que se detecte dicho tag de finalización.
		TagOperator op =operatorFactory.getTagOperator(pName); //Recoge la instancia de tag que ha finalizado
		if (op == null) {
			// TODO: Add Log information
			return;
		}
		op.invokeEnd(); //Invoca al metodo de finalización adecuado a la clase de tag que ha finalizado
	}

	public void startDocument() throws SAXException {
		//Notifica el comienzo de un documento XML. Aquí podeis incluir lo que quereis hacer al comenzar a tratar
        //el fichero XML. En el caso que nos ocupa podeis imprimir en la salida estándar un mensaje informativo,
        //diciendo que comienza el proceso de parseo
		System.out.println("Comenzado lectura...");
	}

	public void startElement(String pUri, String pLocalName, String pName,
			Attributes pAttributes) throws SAXException {
        //Notifica el comienzo de un elemento (cuando detecta un tag de comienzo).
        //Aquí se incluirá lo que se debe hacer al encontrar un tag de inicio de elemento. En el caso que nos ocupa,
        //no es necesario hacer nada al encontrar un tag de comienzo, por lo que podeis dejar vacía la implementación
        //de este método
	}

	private class TagOperatorFactory { //Es un TAD. Se ha incluido en XMLParser un atributo que contenga su instanciación 
		private Map<String, TagOperator> operators = null;
		
		public TagOperatorFactory() {
			operators = new HashMap<String, TagOperator>();
			operators.put("Definicion", new DefinicionTagOperator());
			operators.put("Letra", new LetraTagOperator());
			operators.put("Enunciado", new EnunciadoTagOperator());
			operators.put("Respuesta", new RespuestaTagOperator());
		}
		
		public TagOperator getTagOperator(String pName) {
			return operators.get(pName);
		}
	}
			
	// Tag operators
		
	private class DefinicionTagOperator implements TagOperator {
	        //Hereda la constructora de Object
	 		@Override
	 		
	        //Implementación del tratamiento del tag </Definicion>
			public void invokeEnd() {
				if (definicionActual != null && letra != null) {
					CatalogoDefiniciones.getCatalogoDefiniciones().addDefinicion(letra,definicionActual);
					definicionActual = null;
					letra = null;
				}
			}	
	}
	
	private class LetraTagOperator implements TagOperator {
        @Override
        public void invokeEnd() {
            letra = new Character(texto.charAt(0));
            texto = null;
        }
    }
     
    private class EnunciadoTagOperator implements TagOperator {
        @Override
        public void invokeEnd() {
            definicionActual = new Definicion(texto);
            texto = null;
        }
    }
     
    private class RespuestaTagOperator implements TagOperator {
        @Override
        public void invokeEnd() {
            if (definicionActual != null && texto != null) {
                definicionActual.addRespuesta(texto);
                texto = null;
            }
        }
    }
	
	//TENEIS QUE IMPLEMENTAR UNA CLASE PRIVADA SIMILAR A ESTA PARA CADA TAG QUE DEFINAIS EN VUESTRO FICHERO XML
			
}
