package packClases;

public class MainClass {

	public static void main(String[] args) {
		CatalogoDefiniciones catalogo = CatalogoDefiniciones.getCatalogoDefiniciones();
		Rosco rosco=Rosco.getRosco();
		catalogo.loadData();
		rosco.cargarCasillas();
	//	System.out.println(catalogo.toString());
		//System.out.println(catalogo.buscarPorLetra('A').toString());
		
	}

}
