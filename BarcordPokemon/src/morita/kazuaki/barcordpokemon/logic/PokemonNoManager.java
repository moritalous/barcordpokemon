package morita.kazuaki.barcordpokemon.logic;

public class PokemonNoManager {
	
	public static int generatePokemonNo(String barcordNo){
		
		try {
			long no = Long.valueOf(barcordNo);
			int n = (int)(no % 718 + 1);
			return n;
		} catch (NumberFormatException e) {
			return 1;
		}
	}
}
