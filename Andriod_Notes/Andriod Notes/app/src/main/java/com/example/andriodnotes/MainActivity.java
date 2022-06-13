package com.example.andriodnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{
    private RecyclerView rv;
    private NotesAdapter na;
    private final List<JsonNote> notesList = new ArrayList<>();
    public ActivityResultLauncher<Intent> res = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onActivityResult);
    int count = 0;
    //File file = new File("Notes.json");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initScreen();
    }

    public void initData() {
        try {
            InputStream is = getApplicationContext().openFileInput("Notes.json");
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;

            StringBuilder jsonBuilder = new StringBuilder();
            try {
                for (line = null; (line = jsonReader.readLine()) != null;) {
                    jsonBuilder.append(line);
                }
                JSONArray jsonArray = getJsonArray(jsonBuilder);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    JsonNote nt = new JsonNote(jo.getString("title"), jo.getString("text"), jo.getString("svTime"));
                    notesList.add(nt);
                }
                count = notesList.size();
            } catch (Exception e) {
                Log.d("NOTEAPP", "Reading JSON file in initData. Read error");
                notesList.clear();
                //saveToJson();
            }
        } catch (Exception e) {
            Log.d("NOTEAPP", "initData no json file");
            notesList.clear();
            //saveToJson();
        }

    }

    @NonNull
    private JSONArray getJsonArray(@NonNull StringBuilder jsonBuilder) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
        return jsonArray;
    }

    public void initScreen() {
        rv = findViewById(R.id.recycleNoteData);
        na = new NotesAdapter(notesList, this);
        rv.setAdapter(na);
        rv.setLayoutManager(new LinearLayoutManager(this));
        setTitle("Android Notes (" + notesList.size() + ")");

        //        res = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        Log.e("NOTEAPP", "onActivityResult");
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            callback(result);
//                        }
//                    }
//                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_menu, menu);

            return super.onCreateOptionsMenu(menu);
        }

     @Override 
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            super.onOptionsItemSelected(item);

            if(item.getItemId() == R.id.add)
            {
                JsonNote nt = new JsonNote();
                Intent intent = new Intent(MainActivity.this, edit_activity.class);

                intent.putExtra("NOTE", nt);
                intent.putExtra("POS", -1);
                //intent.putExtra("FILE", fileOutputStream);
                res.launch(intent);
                //startActivityForResult(intent, 10);
                return true;
            } else if (item.getItemId() == R.id.info) {
//                JsonNote nt = new JsonNote();
//                Intent intent = new Intent(MainActivity.this, about_activity.class);
//                intent.putExtra("NOTE", nt);
//                intent.putExtra("POS", -1);
//                //intent.putExtra("FILE", fileOutputStream);
//                res.launch(intent);
//                //startActivityForResult(intent, 10);
                Intent intent = new Intent(MainActivity.this, about_activity.class);
                startActivity(intent);
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
     }

     /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("NOTEAPP", "In Callback");
        super.onActivityResult(requestCode, resultCode, data);
        int pos = -1;
        if (resultCode == Activity.RESULT_OK && data != null) {
            JsonNote nt = new JsonNote();
            //Intent i = res.getData();
            if (data.hasExtra("NOTE")) {
                nt = (JsonNote) data.getSerializableExtra("NOTE");
            }
            if (data.hasExtra("POS")) {
                pos = data.getIntExtra("POS", -1);
            }

            if (nt != null && pos == -1) {
                JsonNote newNt = new JsonNote(nt.getNtTitle(), nt.getDisText(), nt.ntTime);
                notesList.add(newNt);
                saveToJson();
            } else if (nt != null && pos != -1) {
                JsonNote newNt = new JsonNote(nt.getNtTitle(), nt.getDisText(), nt.ntTime);
                notesList.set(pos, newNt);
                saveToJson();
            }
            na.notifyDataSetChanged();
        }
    }*/

    //@Override
    public void onActivityResult(@NonNull ActivityResult res) {
        //Log.d("NOTEAPP", "In Intent Callback");
        if (res.getResultCode() == RESULT_OK && res.getData() != null) {
            //Log.e("NOTEAPP", "In Callback 1");
            JsonNote nt = new JsonNote();
            Intent i = res.getData();
            nt = (JsonNote) i.getSerializableExtra("NOTE");
            int pos = i.getIntExtra("POS", -1);
            if (nt != null && pos == -1) {
                //Log.e("NOTEAPP", "In Callback 2");
                JsonNote newNt = new JsonNote(nt.getNtTitle(), nt.getDisText(), nt.ntTime);
                notesList.add(0, newNt);
                //Collections.reverse(notesList);
                //Log.e("NOTEAPP", "In Callback 4");
                saveToJson();
            } else if (nt != null && pos != -1) {
                //Log.e("NOTEAPP", "In Callback 3");
                JsonNote newNt = new JsonNote(nt.getNtTitle(), nt.getDisText(), nt.ntTime);
                notesList.remove(pos);
                notesList.add(0, newNt);
                //Collections.reverse(notesList);
                saveToJson();
            }
            count = notesList.size();
            na.notifyDataSetChanged();
            setTitle("Android Notes (" + notesList.size() + ")");
        }
    }

    private void saveToJson() {
        /*JSONArray jsArray = new JSONArray(notesList);
        try {
            File file = new File(this.getFilesDir(), "Notes.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsArray.toString());
            bufferedWriter.close();
        } catch (Exception e) {
            Log.d("NOTESAPP", "Writing using FileWriter");
        }*/
        try {
            //Log.e("NOTEAPP", "Saving File");
            FileOutputStream fileOutputStream = getApplicationContext().
                    openFileOutput("Notes.json", Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
            //
            writer.setIndent("  ");
            writer.beginArray();
            for (JsonNote nt : notesList) {
                writer.beginObject();
                writer.name("title").value(nt.getNtTitle());
                writer.name("text").value(nt.getNtText());
                writer.name("ntTime").value(nt.getNtTime());
                writer.endObject();
            }
            writer.endArray();
            //Log.e("NOTEAPP", "Saving File end");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        int pos = rv.getChildLayoutPosition(view);
        //JsonNote nt = new JsonNote();
        JsonNote nt = notesList.get(pos);
        Intent intent = new Intent(MainActivity.this, edit_activity.class);

        intent.putExtra("NOTE", nt);
        intent.putExtra("POS", pos);
        res.launch(intent);

    }

    @Override
    public boolean onLongClick(View view) {
        int pos = rv.getChildLayoutPosition(view);
        JsonNote jn = notesList.get(pos);
        //Log.e("DEBUG", "title", jn.getNtTitle().toString());
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //adb.setView(alertDialogView);
        adb.setTitle("Delete Note "+jn.getNtTitle().toString()+"?");
        //adb.setMessage("Press Ok to discard Note. Cancel to return");
        adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                notesList.remove(pos);
                na.notifyDataSetChanged();
                setTitle("Android Notes (" + notesList.size() + ")");
                saveToJson();
                dialog.dismiss();
            }
        });
        adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.show();
        return false;
    }

}