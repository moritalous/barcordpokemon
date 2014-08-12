package morita.kazuaki.barcordpokemon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	private static final String DB = "sqlite.db";
	private static final int DB_VERSION = 1;
	private static final String CREATE_TABLE = "CREATE TABLE POKEMON ( "
			+ "_id integer primary key autoincrement , "
			+ "pokemon_no text not null, " + "pokemon_name text not null, "
			+ "large_image_path text not null, "
			+ "name_image_path text not null)";
	private static final String DROP_TABLE = "";

	public DbOpenHelper(Context context) {
		super(context, DB, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
