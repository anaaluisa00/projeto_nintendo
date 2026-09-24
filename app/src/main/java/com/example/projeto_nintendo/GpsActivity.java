package com.example.projeto_nintendo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class GpsActivity extends AppCompatActivity {

    private TextView textGps;
    private WebView webViewMapa;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String[]> localizacaoLauncher;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gps);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

        textGps = findViewById(R.id.textGps);

        TextView textTitle = findViewById(R.id.textTitle);
        if (textTitle != null) {
            textTitle.setText(Html.fromHtml("Retire seu <font color='#E60012'>produto</font>", Html.FROM_HTML_MODE_LEGACY));
        }

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        webViewMapa = findViewById(R.id.webViewMapa);
        WebSettings webSettings = webViewMapa.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webViewMapa.loadUrl("file:///android_asset/mapa.html");

        webViewMapa.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                android.util.Log.d(
                        "WebView",
                        consoleMessage.message() + " -- From line "
                                + consoleMessage.lineNumber()
                                + " of "
                                + consoleMessage.sourceId()
                );

                return true;
            }
        });

        Button btnPegarLocalizacao = findViewById(R.id.btnPegarLocalizacao);
        if (btnPegarLocalizacao != null) {
            btnPegarLocalizacao.setOnClickListener(v -> localizacaoLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }));
        }

        Button btnVerRota = findViewById(R.id.btnVerRota);
        if (btnVerRota != null) {
            btnVerRota.setOnClickListener(v -> {
                double destLat = -23.5596360;
                double destLng = -46.6593540;
                Uri gmmIntentUri = Uri.parse("geo:" + destLat + "," + destLng + "?q=" + Uri.encode("Game Center Paulista, Alameda Santos, 2300, São Paulo"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + destLat + "," + destLng));
                    startActivity(webIntent);
                }
            });
        }

        // Bottom Navigation Logic
        setupBottomNavigation();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null) return;

                for (android.location.Location location :
                        locationResult.getLocations()) {

                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    textGps.setText(
                            "Lat: " + lat + " / Long: " + lng
                    );

                    webViewMapa.evaluateJavascript(
                            "updateLocation(" + lat + ", " + lng + ")",
                            null
                    );
                }

                fusedLocationClient.removeLocationUpdates(locationCallback);
            }
        };

        localizacaoLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                resultado -> {
                    Boolean fineConcedida =
                            resultado.get(Manifest.permission.ACCESS_FINE_LOCATION);

                    Boolean coarseConcedida =
                            resultado.get(Manifest.permission.ACCESS_COARSE_LOCATION);

                    if (Boolean.TRUE.equals(fineConcedida)
                            || Boolean.TRUE.equals(coarseConcedida)) {

                        solicitarAtualizacaoLocalizacao();

                    } else {
                        textGps.setText("Permissão de localização negada");
                    }
                }
        );
    }

    private void setupBottomNavigation() {
        android.view.View navInicio = findViewById(R.id.navInicio);
        if (navInicio != null) {
            navInicio.setOnClickListener(v -> {
                Intent intent = new Intent(GpsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        android.view.View navExplorar = findViewById(R.id.navExplorar);
        if (navExplorar != null) {
            navExplorar.setOnClickListener(v -> {
                Intent intent = new Intent(GpsActivity.this, ExploreActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        android.view.View navComprar = findViewById(R.id.navComprar);
        if (navComprar != null) {
            navComprar.setOnClickListener(v -> {
                // Already in GpsActivity
            });
        }

        android.view.View navPerfil = findViewById(R.id.navPerfil);
        if (navPerfil != null) {
            navPerfil.setOnClickListener(v -> {
                Intent intent = new Intent(GpsActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }
    }

    private void solicitarAtualizacaoLocalizacao() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        textGps.setText("Buscando localização...");

        LocationRequest locationRequest =
                new LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        2000
                )
                        .setMaxUpdates(1)
                        .build();

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        );
    }
}
