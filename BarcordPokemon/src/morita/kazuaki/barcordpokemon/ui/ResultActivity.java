package morita.kazuaki.barcordpokemon.ui;

import morita.kazuaki.barcordpokemon.common.Constants;
import morita.kazuaki.barcordpokemon.logic.PokemonNoManager;
import morita.kazuaki.barcordpokemon.model.PokemonModel;
import morita.kazuaki.barcordpokemon.net.HttpGetAsyncTask;
import morita.kazuaki.barcordpokemon.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class ResultActivity extends Activity {

	private ImageView nameImage = null;
	private ImageView largeImage = null;

	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		nameImage = (ImageView) findViewById(R.id.result_name_image);
		largeImage = (ImageView) findViewById(R.id.result_large_image);

		String barcordNo = getIntent().getExtras().getString(
				Constants.BARCODRD_NO);

		int pokemon_no = PokemonNoManager.generatePokemonNo(barcordNo);

		new HttpGetAsyncTask(this).execute(String.valueOf(pokemon_no));
	}

	public void updateImage(final PokemonModel model) {
		final String sNameImage = model.getNameImagePath();
		final String sLargeImage = model.getLargeImagePath();

		mHandler.post(new Runnable() {
			@Override
			public void run() {
				nameImage.setImageURI(Uri.parse(sNameImage));
				largeImage.setImageURI(Uri.parse(sLargeImage));
			}
		});
	}

}
