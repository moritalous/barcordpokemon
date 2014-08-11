package morita.kazuaki.barcordpokemon.util;

import java.io.File;

import android.content.Context;
import android.net.Uri;

public class FileUtils {

	public static String getDownloadFile(Context context, String uri){
		File baseDir = context.getExternalFilesDir("cache");
		String filename = Uri.parse(uri).getLastPathSegment();
		
		return baseDir.getAbsolutePath().toString() + "/" + filename;
	}
	
}
