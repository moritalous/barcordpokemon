package morita.kazuaki.barcordpokemon.ui;

import java.util.List;

import morita.kazuaki.barcordpokemon.R;
import morita.kazuaki.barcordpokemon.model.PokemonModel;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GottenArrayAdapter extends ArrayAdapter<PokemonModel> {

	private LayoutInflater layoutInflater_;

	public GottenArrayAdapter(Context context, int resource,
			int textViewResourceId, List<PokemonModel> objects) {
		super(context, resource, textViewResourceId, objects);

		layoutInflater_ = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PokemonModel model = (PokemonModel) getItem(position);

		if (convertView == null) {
			convertView = layoutInflater_.inflate(R.layout.list_gotten_row,
					null);
		}

		TextView noText = (TextView) convertView
				.findViewById(R.id.gotten_no_text);
		ImageView nameImage = (ImageView) convertView
				.findViewById(R.id.gotten_name_image);
		ImageView largeImage = (ImageView) convertView
				.findViewById(R.id.gotten_large_image);

		noText.setText(String.valueOf(position));
		nameImage.setImageURI(Uri.parse(model.getNameImagePath()));
		largeImage.setImageURI(Uri.parse(model.getLargeImagePath()));

		return convertView;

	}

}
