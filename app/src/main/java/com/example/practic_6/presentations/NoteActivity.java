package com.example.practic_6.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practic_6.R;
import com.example.practic_6.datas.DbContext;
import com.example.practic_6.datas.NoteContext;
import com.example.practic_6.datas.RepoNotes;
import com.example.practic_6.domains.models.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    Note note;
    EditText etTitle, etText;
    TextView tvDate;
    View btnSelectColor, btnBack, btnTrash;
    DbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Date currentDate = new Date();
        SimpleDateFormat formatForCurrentDate = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

        btnSelectColor = findViewById(R.id.btn_select_color);
        btnBack = findViewById(R.id.btn_back);
        btnTrash = findViewById(R.id.btn_trash);
        etTitle = findViewById(R.id.et_title);
        etText = findViewById(R.id.et_text);
        tvDate = findViewById(R.id.tv_date);
        dbContext = new DbContext(this);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            int position = arguments.getInt("position");
            note = NoteContext.AllNotes().get(position);
            etTitle.setText(note.title);
            etText.setText(note.text);
        }
        else
            btnTrash.setVisibility(View.GONE);

        tvDate.setText("Отредактировано: " + formatForCurrentDate.format(currentDate));

        btnSelectColor.setOnClickListener(v -> {
            Toast.makeText(this, "Выбор цвета недоступен", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String text = etText.getText().toString();

            if(text
                    .replace(" ", "")
                    .replace("\r", "")
                    .replace("\n", "")
                    .isEmpty()){
                Toast.makeText(this, "Нечего сохранять", Toast.LENGTH_SHORT).show();
            }
            else{
                boolean isUpdate = true;
                if(note == null){
                    note = new Note();
                    isUpdate = false;
                }

                note.title = title;
                note.text = text;
                note.date = formatForCurrentDate.format(currentDate);

                if(isUpdate)
                    NoteContext.Save(note, true);
                else
                    NoteContext.Save(note, false);

            }

            finish();
        });

        btnTrash.setOnClickListener(v -> {
            NoteContext.Delete(note);
            finish();
            Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
        });
    }
}