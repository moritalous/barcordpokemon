package morita.kazuaki.barcordpokemon.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import morita.kazuaki.barcordpokemon.common.Constants;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.MultiFormatOneDReader;

public class CameraPreviewActivity extends Activity {

	private SurfaceView mSurfaceView;
	private Camera mCamera;
	
	private final CameraPreviewActivity activity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSurfaceView = new SurfaceView(this);
		mSurfaceView.setOnClickListener(onClickListener);
		setContentView(mSurfaceView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.addCallback(callback);
	}

	private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// 生成されたとき
			mCamera = Camera.open();
			try {
				// プレビューをセットする
				mCamera.setPreviewDisplay(holder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// 変更されたとき
			Camera.Parameters parameters = mCamera.getParameters();
			List<Camera.Size> previewSizes = parameters
					.getSupportedPreviewSizes();
			Camera.Size previewSize = previewSizes.get(0);
			parameters.setPreviewSize(previewSize.width, previewSize.height);
			// width, heightを変更する
			mCamera.setParameters(parameters);
			mCamera.startPreview();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// 破棄されたとき
			mCamera.release();
			mCamera = null;
		}
	};

	private OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// オートフォーカス
			if (mCamera != null) {
				mCamera.autoFocus(autoFocusCallback);
			}
		}
	};

	private AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				// 現在のプレビューをデータに変換
				camera.setOneShotPreviewCallback(previewCallback);
			}
		}
	};

	private PreviewCallback previewCallback = new PreviewCallback() {
		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			// 読み込む範囲
			int previewWidth = camera.getParameters().getPreviewSize().width;
			int previewHeight = camera.getParameters().getPreviewSize().height;

			// プレビューデータから BinaryBitmap を生成
			PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
					data, previewWidth, previewHeight, 50, 50, previewWidth - 100,
					previewHeight-100, false);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			// バーコードを読み込む
			
			Reader reader = new MultiFormatOneDReader(getOneDFormat());
			Result result = null;
			try {
				result = reader.decode(bitmap);
				
				switch (result.getBarcodeFormat()) {
				case UPC_A:
				case UPC_E:
				case EAN_8:
				case EAN_13:
					String text = result.getText();
					
					Intent intent = new Intent(activity, ResultActivity.class);
					intent.putExtra(Constants.BARCODRD_NO, text);
					startActivity(intent);
					
					finish();
					break;
				default:
					break;
				}
			} catch (Exception e) {
			}
		}

		private Map<DecodeHintType, List<BarcodeFormat>> getOneDFormat() {
			Map<DecodeHintType, List<BarcodeFormat>> map = new HashMap<DecodeHintType, List<BarcodeFormat>>();
			List<BarcodeFormat> list = new ArrayList<BarcodeFormat>();
			
			list.add(BarcodeFormat.EAN_13);
			list.add(BarcodeFormat.EAN_8);
			list.add(BarcodeFormat.UPC_A);
			list.add(BarcodeFormat.UPC_E);
			
			map.put(DecodeHintType.POSSIBLE_FORMATS, list);
			
			return map;
		}
	};
}
