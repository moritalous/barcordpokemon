package morita.kazuaki.barcordpokemon.net;

import java.io.IOException;
import java.util.Calendar;

import morita.kazuaki.barcordpokemon.db.DbAccess;
import morita.kazuaki.barcordpokemon.model.PokemonModel;
import morita.kazuaki.barcordpokemon.ui.ResultActivity;
import morita.kazuaki.barcordpokemon.util.FileUtils;
import morita.kazuaki.barcordpokemon.util.FileUtils.DL_FILE_TYPE;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class HttpGetAsyncTask extends AsyncTask<String, Integer, Long> {

	private ResultActivity activity;
	
	private final String serverDomain = "http://www.pokemon.co.jp";
	private final String htmlUrlFormat = serverDomain + "/zukan/pokemon/%03d.html";

	public HttpGetAsyncTask(ResultActivity activity) {
		this.activity = activity;
	}

	@SuppressLint("DefaultLocale")
	@Override
	protected Long doInBackground(String... params) {
		
		int pokemon_no = Integer.valueOf(params[0]);
		String htmlUrl = String.format(htmlUrlFormat, pokemon_no);
		
		try {
			Document doc = Jsoup.connect(htmlUrl).get();
			
			String largeImageUrl = getLargeImageUrl(doc);
			String pokemonname = getPokemonName(doc);
			String nameImageUrl = serverDomain + getNameImageUrl(doc);
			
			String filename = generateFilename();
			
			PokemonModel model = new PokemonModel();
			model.setLargeImagePath(FileUtils.getDownloadFile(activity, filename, DL_FILE_TYPE.LARGEIMAGE));
			model.setNameImagePath(FileUtils.getDownloadFile(activity, filename, DL_FILE_TYPE.NAME));
			model.setPokemonName(pokemonname);
			model.setPokemonNo(params[0]);
			
			//SQLite Insert
			DbAccess dbAccess = new DbAccess(activity, true);
			dbAccess.insertPokemon(model);
			
			DownloadManager downloadManager = new DownloadManager();
			downloadManager.get(nameImageUrl, model.getNameImagePath());
			downloadManager.get(largeImageUrl, model.getLargeImagePath());
			
			activity.updateImageWithHandler(model);
			
		} catch (IOException e) {
		}
		
		return null;
	}

	private String getNameImageUrl(Document doc) {
		Elements elements = doc.getElementsByAttributeValue("class",
				"name_s");
		for(Element e:elements){
			if(e.child(0).tagName().equals("img")){
				return e.child(0).attributes().get("src");
			}
		}
		return "";
	}

	private String getPokemonName(Document doc) {
		Elements elements = doc.getElementsByAttributeValue("class",
				"name_s");
		for(Element e:elements){
			if(e.child(0).tagName().equals("img")){
				return e.child(0).attributes().get("alt");
			}
		}
		return "";
	}

	private String getLargeImageUrl(Document doc) {
		Elements elements = doc.getElementsByAttributeValue("property",
				"og:image");
		return elements.attr("content");
	}
	
	private String generateFilename(){
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}

}
