package com.example.terabox;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.terabox.databinding.ActivityDashboardBinding;
public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            }
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });

        binding.fabAdd.setOnClickListener(v ->
                Toast.makeText(this, R.string.content_desc_add, Toast.LENGTH_SHORT).show());

        binding.buttonUpgrade.setOnClickListener(v ->
                Toast.makeText(this, R.string.upgrade, Toast.LENGTH_SHORT).show());
    }
}
