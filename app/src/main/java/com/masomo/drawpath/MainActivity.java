package com.masomo.drawpath;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import admost.sdk.AdMostInterstitial;
import admost.sdk.base.AdMost;
import admost.sdk.base.AdMostConfiguration;
import admost.sdk.listener.AdMostFullScreenCallBack;
import admost.sdk.listener.AdMostInitListener;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "APPHARBR_TEST";
    private static final String APP_ID = "c9ab73ca-ecb0-2e3a-3d00-56adc9ab3dca";
    private static final String INTERSTITIAL_ID = "20a1faaf-71bf-4f75-976f-3b9c7c9a6d4c";
    private static final String REWARDED_ID = "20a1faaf-71bf-4f75-976f-3b9c7c9a6d4d";

    AdMostInterstitial interstitial;
    AdMostInterstitial rewarded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initADMOST();
        setClicks();
    }

    private void initADMOST() {
        AdMostConfiguration.Builder configuration = new AdMostConfiguration.Builder(MainActivity.this, APP_ID);

        AdMost.getInstance().init(configuration.build(), new AdMostInitListener() {
            @Override
            public void onInitCompleted() {

            }

            @Override
            public void onInitFailed(int i) {

            }
        });
    }

    private void loadInterstitial() {
        if (interstitial == null) {
            interstitial = new AdMostInterstitial(null, INTERSTITIAL_ID, new AdMostFullScreenCallBack() {
                @Override
                public void onReady(String network, int ecpm) {
                    Log.i(LOG_TAG, "onReady - " + network);
                }

                @Override
                public void onFail(int errorCode) {
                    Log.i(LOG_TAG, "onFail - NOT_FILLED - " + errorCode);
                }

                @Override
                public void onDismiss(String message) {
                    Log.i(LOG_TAG, "onDismiss - " + message);
                }
            });
        }
        interstitial.refreshAd(false);
    }

    private void showInterstitial() {
        if (interstitial != null && interstitial.isLoaded()) {
            interstitial.show();
        } else {
            Log.i(LOG_TAG, "Interstitial ad is not ready, load ad first ..!");
        }
    }

    private void loadRewarded() {
        if (rewarded == null) {
            rewarded = new AdMostInterstitial(null, REWARDED_ID, new AdMostFullScreenCallBack() {
                @Override
                public void onReady(String network, int ecpm) {
                    Log.i(LOG_TAG, "onReady - " + network);
                }

                @Override
                public void onComplete(String network) {
                    Log.i(LOG_TAG, "onComplete - " + network);
                }

                @Override
                public void onFail(int errorCode) {
                    Log.i(LOG_TAG, "onFail - NOT_FILLED - " + errorCode);
                }

                @Override
                public void onDismiss(String message) {
                    Log.i(LOG_TAG, "onDismiss - " + message);
                }
            });
        }
        rewarded.refreshAd(false);
    }

    private void showRewarded() {
        if (rewarded != null && rewarded.isLoaded()) {
            rewarded.show();
        } else {
            Log.i(LOG_TAG, "Rewarded ad is not ready, load ad first ..!");
        }
    }



    private void setClicks() {
        findViewById(R.id.loadInterstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadInterstitial();
            }
        });
        findViewById(R.id.showInterstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        findViewById(R.id.loadRewarded).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewarded();
            }
        });
        findViewById(R.id.showRewarded).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewarded();
            }
        });

        findViewById(R.id.openTestSuite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdMost.getInstance().startTestSuite( new String[] {INTERSTITIAL_ID, REWARDED_ID});
            }
        });
    }
}