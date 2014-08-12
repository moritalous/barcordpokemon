
package morita.kazuaki.barcordpokemon.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import morita.kazuaki.barcordpokemon.R;
import morita.kazuaki.barcordpokemon.common.Constants;
import morita.kazuaki.barcordpokemon.model.PokemonModel;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class GottenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gotten);

		File imageDir = this.getExternalFilesDir(Constants.DIR_IMAGE_CACHE);
		File nameDir = this.getExternalFilesDir(Constants.DIR_NAME_CACHE);
		File[] imageFiles = imageDir.listFiles();

		List<PokemonModel> modelList = new ArrayList<PokemonModel>();

		for (File f : imageFiles) {
			PokemonModel model = new PokemonModel();
			model.setLargeImagePath(f.getAbsolutePath());
			model.setNameImagePath(nameDir.getAbsolutePath() + "/"
					+ f.getName());
			modelList.add(model);
		}

		GottenArrayAdapter adapter = new GottenArrayAdapter(this, 0, 0,
				modelList);

		ListView listView = (ListView) findViewById(R.id.gotten_list);
		listView.setAdapter(adapter);

	}

}
