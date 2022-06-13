package com.example.andriodnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class edit_activity extends AppCompatActivity {
    private EditText noteTitle;
    private EditText noteContent;
    private JsonNote nt;
    private int pos;
    private int change = -1;
    private String prevTitle, prevContent;
    //private static final String TAG = "edit_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initData();
        initScreen();
    }

    private void initScreen() {
        noteTitle = findViewById(R.id.notetitle);
        noteContent = findViewById(R.id.noteDetails);

        noteTitle.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Log.e("NOTEAPP", "text changed title");
                change = 1;
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //Log.e("NOTEAPP", "text changed title 1");
                change = -1;
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //Log.e("NOTEAPP", "text changed title 2");
            }
        });

        noteContent.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Log.e("NOTEAPP", "text changed content");
                change = 1;
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //Log.e("NOTEAPP", "text changed content 1");
                change = -1;
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //Log.e("NOTEAPP", "text changed content 2");
            }
        });

        noteTitle.setText(nt.getNtTitle());
        noteContent.setText(nt.getNtText());

        prevTitle = nt.getNtTitle();
        prevContent = nt.getNtText();
        change = -1;
    }

    private void initData() {
        if (getIntent().hasExtra("NOTE")) {
            nt = (JsonNote) getIntent().getSerializableExtra("NOTE");
        } else {
            nt = new JsonNote();
        }

        pos = -1;
        if (getIntent().hasExtra("POS")) {
            pos = getIntent().getIntExtra("POS", pos);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        //Log.d("NOTEAPP", "Saving Note");
        if (item.getItemId() == R.id.save) {
            if (noteTitle.getText().toString().isEmpty()) {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                //adb.setView(alertDialogView);
                adb.setTitle("Saving note without a Title?");
                adb.setMessage("Note will not be saved without a Title");
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(this, "Discarding Note", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                        return;
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();
            } else {
                //Log.e("NOTEAPP", "DEBUG Saving Note");
                nt.save(noteTitle.getText().toString(), noteContent.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("NOTE", nt);
                intent.putExtra("POS", pos);
                setResult(Activity.RESULT_OK, intent);
                change = -1;
                //finishActivity(0);
                finish();
                //return true;
            }
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
//            if (!prevTitle.equals(nt.getNtTitle().toString()) ||
//                    !prevContent.equals(nt.getNtText().toString())) {
            //Log.e("NOTEAPP", "change value: "+change);
                if (change != -1) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this);
                    adb.setTitle("Your Note is not saved!");
                    adb.setMessage("Save note '" + noteTitle.getText().toString() + "'?");
                    adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            nt.save(noteTitle.getText().toString(), noteContent.getText().toString());
                            Intent intent = new Intent();
                            intent.putExtra("NOTE", nt);
                            intent.putExtra("POS", pos);
                            setResult(Activity.RESULT_OK, intent);
                            change = -1;
                            finish();
                        }
                    });
                    adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            edit_activity.super.onBackPressed();
                        }
                    });
                    adb.show();
            } else {
                edit_activity.super.onBackPressed();
            }
        } catch (Exception e) {
            Log.d("NOTEAPP","Edit Activity on back key press", e);
        }
    }

}
