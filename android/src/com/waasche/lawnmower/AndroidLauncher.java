package com.waasche.lawnmower;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.waasche.lawnmower.main.MainClass;

public class AndroidLauncher extends AndroidApplication {
	public static final String TAG = "AndroidLauncher";
	protected AdView adView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new MainClass(), config);
		layout.addView(gameView);
		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				Log.i(TAG, "Ad Loaded");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-8290380788307601/429516140111");

		AdRequest adRequest = new AdRequest.Builder().build();
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout.addView(adView, adParams);
		adView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		adView.loadAd(adRequest);

		setContentView(layout);
	}
}
