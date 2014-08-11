package morita.kazuaki.barcordpokemon.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DownloadManager {
	
	public boolean get(String uri, String downloadFile){
		
		try {
			HttpGet httpGet = new HttpGet(uri);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpGet);
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File(downloadFile));
			fileOutputStream.write(EntityUtils.toByteArray(response.getEntity()));
			fileOutputStream.close();
			return true;
			
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
		
	}

}
