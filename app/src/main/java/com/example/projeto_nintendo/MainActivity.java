package com.example.projeto_nintendo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        ImageView profileIcon = findViewById(R.id.imageView5);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        View navInicio = findViewById(R.id.navInicio);
        navInicio.setOnClickListener(v -> {
            // Already in MainActivity
        });

        View navPerfil = findViewById(R.id.navPerfil);
        navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        View navExplorar = findViewById(R.id.navExplorar);
        navExplorar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
            startActivity(intent);
        });

        View navComprar = findViewById(R.id.navComprar);
        if (navComprar != null) {
            navComprar.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, GpsActivity.class);
                startActivity(intent);
            });
        }
    }
}
