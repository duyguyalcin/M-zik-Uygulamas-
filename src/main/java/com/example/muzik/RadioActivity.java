package com.example.muzik;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class RadioActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        Button playButton = findViewById(R.id.play_button);
        Button stopButton = findViewById(R.id.stop_button);

        // WebView'i tanımlayın
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        //String radioUrl = "https://www.canliradyodinle.fm/radyo-fenomen-dinle.html";
       // webView.loadUrl(radioUrl);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Burada medya oynatma kodlarını ekleyebilirsiniz
                // Örneğin, WebView'den radyo yayını başlatmak için ek bir işlem yapılabilir.
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // WebView içeriğini durdurma işlemi
                webView.stopLoading();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
