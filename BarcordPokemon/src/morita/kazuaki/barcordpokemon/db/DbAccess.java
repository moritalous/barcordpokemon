package morita.kazuaki.barcordpokemon.db;

import morita.kazuaki.barcordpokemon.model.PokemonModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbAccess {

	private Context context;

	private DbOpenHelper dbHelper;

	private SQLiteDatabase db;

	public DbAccess(Context context, boolean writable) {
		this.context = context;

		dbHelper = new DbOpenHelper(context.getApplicationContext());
		if (writable) {
			db = dbHelper.getWritableDatabase();
		} else {
			db = dbHelper.getReadableDatabase();
		}
	}

	public boolean insertPokemon(PokemonModel model) {
		ContentValues values = new ContentValues();
		values.put(PokemonEntity.pokemonNo, model.getPokemonNo());
		values.put(PokemonEntity.pokemonName, model.getPokemonName());
		values.put(PokemonEntity.largeImagePath, model.getLargeImagePath());
		values.put(PokemonEntity.nameImagePath, model.getNameImagePath());

		db.insert(PokemonEntity.tableName, null, values);

		return true;
	}

	public PokemonModel getpokemon(String pokemonNo) {

		Cursor result = db.query(PokemonEntity.tableName, new String[] {
				PokemonEntity.pokemonNo, PokemonEntity.pokemonName, PokemonEntity.largeImagePath, PokemonEntity.nameImagePath }, 
				PokemonEntity.pokemonNo + " = ?", 
				new String[]{pokemonNo}, null, null,
				PokemonEntity.id + " DESC");

		if (result.getCount() == 0) {
			result.close();
			return null;
		}

		result.moveToFirst();

		PokemonModel model = new PokemonModel();

		model.setPokemonNo(result.getString(result
				.getColumnIndex(PokemonEntity.pokemonNo)));
		model.setPokemonName(result.getString(result
				.getColumnIndex(PokemonEntity.pokemonName)));
		model.setLargeImagePath(result.getString(result
				.getColumnIndex(PokemonEntity.largeImagePath)));
		model.setNameImagePath(result.getString(result
				.getColumnIndex(PokemonEntity.nameImagePath)));

		result.close();

		return model;
	}
	
}
