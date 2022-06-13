package com.example.newsaggregator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DebugMainActivity";
    private DrawerLayout mLayout;
    private ListView mList;
    private ActionBarDrawerToggle mToggle;
    private Menu menu;
    ArrayList<String> countryName = new ArrayList<>();
    ArrayList<String> country_code = new ArrayList<>();
    ArrayList<String> langName = new ArrayList<>();
    ArrayList<String> language_code = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> itemsId = new ArrayList<>();
        ArrayList<String> Topics = new ArrayList<>();
    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> language = new ArrayList<>();
    JSONObject full;
    JSONObject current;
    String selectedTopics = "all", selectedCountry = "all", selectedLanguage = "all";
    ArticleViewAdapter articleViewAdapter;
    ArrayList<com.example.newsaggregator.Articles> articlesList = new ArrayList<com.example.newsaggregator.Articles>();
    ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            JSONObject obj = new JSONObject(loadjsondata("country"));
            JSONArray m_jArry = obj.getJSONArray("countries");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                country_code.add(jo_inside.getString("code").toLowerCase());
                countryName.add(jo_inside.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            JSONObject obj = new JSONObject(loadjsondata("language"));
            JSONArray m_jArry = obj.getJSONArray("languages");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                language_code.add(jo_inside.getString("code").toLowerCase());
                langName.add(jo_inside.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLayout = findViewById(R.id.drawer_layout);
        mList = findViewById(R.id.drawermenu);
        mToggle = new ActionBarDrawerToggle(
                this,
                mLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        pager = findViewById(R.id.pager);
        articleViewAdapter = new ArticleViewAdapter(this,articlesList);
        pager.setAdapter(articleViewAdapter);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        NewsSources loaderTaskRunnable = new NewsSources(MainActivity.this);
        new Thread(loaderTaskRunnable).start();

    }

    public void createDrawer(String jsonString){
        // Make sample items for the drawer list
        try {
            full = new JSONObject(jsonString);
            current = new JSONObject(jsonString);
            JSONArray sources = full.getJSONArray("sources");

            for (int i = 0; i < sources.length(); i++) {
                JSONObject jo_inside = sources.getJSONObject(i);
                items.add(jo_inside.getString("name"));
                itemsId.add(jo_inside.getString("id"));
                Topics.add(jo_inside.getString("category"));
                language.add(jo_inside.getString("language"));
                country.add(jo_inside.getString("country"));
            }

            HashSet<String> hashSet = new HashSet<String>();
            hashSet.addAll(Topics);
            Topics.clear();
            Topics.add("all");
            Topics.addAll(hashSet);
            Collections.sort(Topics);

            hashSet.clear();
            hashSet.addAll(language);
            language.clear();
            language.addAll(hashSet);
            Collections.sort(language);

            hashSet.clear();
            hashSet.addAll(country);
            country.clear();
            country.addAll(hashSet);
            Collections.sort(country);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        mList.setAdapter(new ArrayAdapter<>(this,
                R.layout.sidemenu_list, items));

        mList.setOnItemClickListener(
                (parent, view, position, id) -> selectItem(position)
        );

        setTitle("News Gateway ("+items.size()+")");

        SubMenu subm = menu.getItem(0).getSubMenu();
        subm.clear();

        for(int i=0;i<Topics.size();i++){
            subm.add(0, i, i,Topics.get(i));
        }

        subm = menu.getItem(1).getSubMenu();
        subm.clear();

        subm.add(1, 1, 1,"all");
        for(int i=0;i<country.size();i++){
            if(country_code.contains(country.get(i)))
                subm.add(1, i+1, i+1,countryName.get(country_code.indexOf(country.get(i))));
        }

        subm = menu.getItem(2).getSubMenu();
        subm.clear();

        subm.add(2, 1, 1,"all");
        for(int i=0;i<language.size();i++){
            if(language_code.contains(language.get(i)))
                subm.add(2, i+1, i+1,langName.get(language_code.indexOf(language.get(i))));
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        this.menu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Important!
        if (mToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: mToggle " + item);
            return true;
        }
        else {
            if(!item.hasSubMenu()) {
                if(item.getGroupId() == 0){
                    selectedTopics = item.getTitle().toString();
                    update();
                }
                else  if(item.getGroupId() == 1){
                    if(!item.getTitle().toString().equalsIgnoreCase("all")) {
                        selectedCountry = country_code.get(countryName.indexOf(item.getTitle().toString()));
                    }
                    else{
                        selectedCountry = "all";
                    }
                    update();
                }
                else  if(item.getGroupId() == 2){
                    if(!item.getTitle().toString().equalsIgnoreCase("all")) {
                        selectedLanguage = language_code.get(langName.indexOf(item.getTitle().toString()));
                    }
                    else{
                        selectedLanguage = "all";
                    }
                    update();
                }
            }
            return super.onOptionsItemSelected(item);
        }

    }

    private void selectItem(int position) {
        mLayout.closeDrawer(mList);
        setTitle(items.get(position));
        ArticleSources loaderTaskRunnable = new ArticleSources(MainActivity.this,itemsId.get(position));
        new Thread(loaderTaskRunnable).start();
    }

    public String loadjsondata(String name) {
        if(name.equalsIgnoreCase("country")) {
            String json = null;
            try {
                InputStream is = getResources().openRawResource(R.raw.country_codes);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
        else{
            String json = null;
            try {
                InputStream is = getResources().openRawResource(R.raw.language_codes);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }

    public void update(){
        items.clear();
        itemsId.clear();
        try {
            current = full;
            JSONArray sources = current.getJSONArray("sources");
            for (int i = 0; i < sources.length(); i++) {
                JSONObject jo_inside = sources.getJSONObject(i);
                if(selectedTopics.equalsIgnoreCase("all")){
                    if(selectedLanguage.equalsIgnoreCase("all")){
                        if(selectedCountry.equalsIgnoreCase("all")){
                            items.add(jo_inside.getString("name"));
                            itemsId.add(jo_inside.getString("id"));
                        }
                        else{
                            if(jo_inside.getString("country").equalsIgnoreCase(selectedCountry)){
                                items.add(jo_inside.getString("name"));
                                itemsId.add(jo_inside.getString("id"));
                            }
                        }
                    }
                    else{
                        if(jo_inside.getString("language").equalsIgnoreCase(selectedLanguage)){
                            if(selectedCountry.equalsIgnoreCase("all")){
                                items.add(jo_inside.getString("name"));
                                itemsId.add(jo_inside.getString("id"));
                            }
                            else{
                                if(jo_inside.getString("country").equalsIgnoreCase(selectedCountry)){
                                    items.add(jo_inside.getString("name"));
                                    itemsId.add(jo_inside.getString("id"));
                                }
                            }
                        }
                    }
                }
                else{
                    if(jo_inside.getString("category").equalsIgnoreCase(selectedTopics)){
                        if(selectedLanguage.equalsIgnoreCase("all")){
                            if(selectedCountry.equalsIgnoreCase("all")){
                                items.add(jo_inside.getString("name"));
                                itemsId.add(jo_inside.getString("id"));
                            }
                            else{
                                if(jo_inside.getString("country").equalsIgnoreCase(selectedCountry)){
                                    items.add(jo_inside.getString("name"));
                                    itemsId.add(jo_inside.getString("id"));
                                }
                            }
                        }
                        else{
                            if(jo_inside.getString("language").equalsIgnoreCase(selectedLanguage)){
                                if(selectedCountry.equalsIgnoreCase("all")){
                                    items.add(jo_inside.getString("name"));
                                    itemsId.add(jo_inside.getString("id"));
                                }
                                else{
                                    if(jo_inside.getString("country").equalsIgnoreCase(selectedCountry)){
                                        items.add(jo_inside.getString("name"));
                                        itemsId.add(jo_inside.getString("id"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(items.size()<=0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Warning!");
            alert.setMessage("No news source matching the Topics,country or language exists");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }

        mList.setAdapter(new ArrayAdapter<>(this,
                R.layout.sidemenu_list, items));

        setTitle("News Gateway ("+items.size()+")");

        mList.setOnItemClickListener(
                (parent, view, position, id) -> selectItem(position)
        );
    }

    public void acceptResults(ArrayList<com.example.newsaggregator.Articles> articlesList, int count) {
        if (articlesList == null) {
            Toast.makeText(this, "Data loader failed", Toast.LENGTH_LONG).show();
        } else {
            this.articlesList.clear();
            this.articlesList.addAll(articlesList);
            articleViewAdapter.notifyDataSetChanged();
            pager.setAdapter(articleViewAdapter);
        }
    }

}