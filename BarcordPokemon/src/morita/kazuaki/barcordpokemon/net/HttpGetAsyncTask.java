package morita.kazuaki.barcordpokemon.net;

import java.io.IOException;

import morita.kazuaki.barcordpokemon.model.PokemonModel;
import morita.kazuaki.barcordpokemon.ui.ResultActivity;
import morita.kazuaki.barcordpokemon.util.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class HttpGetAsyncTask extends AsyncTask<String, Integer, Long> {

	private ResultActivity activity;
	
	private final String htmlUrlFormat = "http://www.pokemon.co.jp/zukan/pokemon/%03d.html";
	private final String nameImageUrlFormat = "http://www.pokemon.co.jp/zukan/common/img/name/%03d.png";

	public HttpGetAsyncTask(ResultActivity activity) {
		this.activity = activity;
	}

	@Override
	protected Long doInBackground(String... params) {
		
		int pokemon_no = Integer.valueOf(params[0]);
		String htmlUrl = String.format(htmlUrlFormat, pokemon_no);
		String nameImageUrl = String.format(nameImageUrlFormat, pokemon_no);
		
		try {
			Document doc = Jsoup.connect(htmlUrl).get();
			Elements elements = doc.getElementsByAttributeValue("property", "og:image");
			String largeImageUrl = elements.attr("content");
			
			PokemonModel model = new PokemonModel();
			model.setLargeImagePath(FileUtils.getDownloadFile(activity, nameImageUrl));
			model.setNameImagePath(FileUtils.getDownloadFile(activity, largeImageUrl));
			
			DownloadManager downloadManager = new DownloadManager();
			downloadManager.get(nameImageUrl, model.getNameImagePath());
			downloadManager.get(largeImageUrl, model.getLargeImagePath());
			
			activity.updateImage(model);
			
		} catch (IOException e) {
		}
		
		return null;
	}

}
