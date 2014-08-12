package morita.kazuaki.barcordpokemon.util;

import java.io.File;

import morita.kazuaki.barcordpokemon.common.Constants;

import android.content.Context;

public class FileUtils {

	public enum DL_FILE_TYPE {
		NAME, LARGEIMAGE,
	}

	public static String getDownloadFile(Context context, String filename,
			DL_FILE_TYPE type) {
		String dlDirName = "";
		switch (type) {
		case NAME:
			dlDirName = Constants.DIR_NAME_CACHE;
			break;
		case LARGEIMAGE:
			dlDirName = Constants.DIR_IMAGE_CACHE;
			break;

		default:
			break;
		}
		File baseDir = context.getExternalFilesDir(dlDirName);

		return baseDir.getAbsolutePath().toString() + "/" + filename;
	}

}
