package com.example.muzik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerListActivity extends AppCompatActivity {

    ListView listView;
    List<String> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_list);

        listView = findViewById(R.id.listView);

        // Müzik dosyalarını raw klasöründen al
        musicList = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            musicList.add(field.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musicList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMusic = musicList.get(position);

                int resId = getResources().getIdentifier(musicList.get(position), "raw", getPackageName());
                if (resId == 0) {
                    Log.e("MusicPlayerActivity", "Invalid resource ID for: " + musicList.get(position));
                    finish();
                }


                Intent intent = new Intent(MusicPlayerListActivity.this, com.example.muzik.MusicPlayerActivity.class);
                intent.putExtra("musicName", selectedMusic);
                intent.putExtra("resId", resId);
                startActivity(intent);
            }
        });
    }
}
