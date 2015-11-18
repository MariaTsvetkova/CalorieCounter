package com.example.weightcontroller.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.weightcontroller.MainActivity;
import com.example.weightcontroller.R;

public class SplashScreen extends Activity {
	private boolean mIsBackButtonPressed;
	private static final int SPLASH_DURATION = 1000;
	private Handler handler;
	private ProgressBar progressBr;
	private int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		progressBr = (ProgressBar) findViewById(R.id.progressBar1);

		handler = new Handler();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					progress += 20;
					handler.post(new Runnable() {

						@Override
						public void run() {

							progressBr.setProgress(progress);
							if (progress == progressBr.getMax()) {
								finish();

								if (!mIsBackButtonPressed) {
									Intent intent = new Intent(
											SplashScreen.this,
											MainActivity.class);
									startActivity(intent);
								}
							}
						}
					});
					try {
						Thread.sleep(SPLASH_DURATION);
					} catch (InterruptedException e) {

					}

				}
			}
		}).start();

	}

	@Override
	public void onBackPressed() {

		mIsBackButtonPressed = true;
		super.onBackPressed();
	}

}
