package com.example.practic_6.presentations;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practic_6.R;
import com.example.practic_6.datas.DbContext;
import com.example.practic_6.datas.NoteContext;
import com.example.practic_6.datas.RepoNotes;
import com.example.practic_6.domains.models.Note;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NotesActivity extends AppCompatActivity {
    GridLayout itemsParent;
    View btnAddNotes;
    EditText etSearch;
    DbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        btnAddNotes = findViewById(R.id.btn_add_notes);
        itemsParent = findViewById(R.id.gl_notes);
        etSearch = findViewById(R.id.et_search);

        btnAddNotes.setOnClickListener(v -> {
            Intent intentActivityNote = new Intent(this, NoteActivity.class);
            startActivity(intentActivityNote);
        });
        etSearch.setOnKeyListener(SearchListener);

        dbContext = new DbContext(this);

        LoadNotes(NoteContext.AllNotes());
    }

    @Override
    protected void onResume(){
        super.onResume();
        LoadNotes(NoteContext.AllNotes());
    }

    public void LoadNotes(ArrayList<Note> notes){
        itemsParent.removeAllViews();

        for(int i = 0; i< notes.size(); i ++){
            View item_notes = LayoutInflater.from(this).inflate(R.layout.item_note, itemsParent, false);

            TextView tvTitle = item_notes.findViewById(R.id.tv_title);
            TextView tvText = item_notes.findViewById(R.id.tv_text);
            TextView tvDate = item_notes.findViewById(R.id.tv_date);

            tvTitle.setText(notes.get(i).title);
            tvText.setText(notes.get(i).text);
            tvDate.setText(notes.get(i).date);

            int Position = i;

            item_notes.setOnClickListener(v -> {
                Intent intentActivityNote = new Intent(this, NoteActivity.class);
                intentActivityNote.putExtra("position", Position);
                startActivity(intentActivityNote);
            });

            itemsParent.addView(item_notes);
        }
    }

    View.OnKeyListener SearchListener = new View.OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            String Search = etSearch.getText().toString();

            ArrayList<Note> FindNotes = NoteContext.AllNotes().stream().filter(
                    item -> item.text.contains(Search)
            ).collect(Collectors.toCollection(ArrayList::new));

            LoadNotes(FindNotes);

            return false;
        }
    };
}