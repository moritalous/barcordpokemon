package morita.kazuaki.barcordpokemon.ui;

import morita.kazuaki.barcordpokemon.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button startQR = null;
	private Button gotten = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startQR = (Button) findViewById(R.id.btn_startQR);
		startQR.setOnClickListener(this);

		gotten = (Button) findViewById(R.id.btn_gotten);
		gotten.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.btn_startQR: {
			Intent intent = new Intent(this, CameraPreviewActivity.class);
			startActivity(intent);
		}

			break;
		case R.id.btn_gotten: {
			Intent intent = new Intent(this, GottenActivity.class);
			startActivity(intent);
		}

			break;
		default:
			break;
		}

	}
}
