package txtreader;

public class MainReader {
	public static void main(String[] args) {
		Leitor vailer =new Leitor();
		vailer.ler_Regras();
		vailer.ler_emails("C:\\Users\\Sergio-PC\\Desktop\\Universidade\\Engenharia de Software\\Projecto\\Inputs\\ham.log");
		vailer.ler_emails("C:\\Users\\Sergio-PC\\Desktop\\Universidade\\Engenharia de Software\\Projecto\\Inputs\\spam.log");
		vailer.avaliar();
	}
}
