package com.beviacode.asqlitebevia;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.beviacode.asqlitebevia.db.PoetsDataSource;
import com.beviacode.asqlitebevia.model.Poets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LoadList extends ListActivity {

    // first thing is leave this activity and create your DBOpenHelper class and then your Source, You need no Model to create
    // your db. Then come here and finish your db by opening and closing it!

    public static final String LOGTAG = "PERSONAL";

    private String one_poets_1;
    private String one_poets_2;
    private String one_poets_3;
    private String one_poets_4;


    private PoetsDataSource datasource;
    private List<Poets> personals;

    static LoadList mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        one_poets_1 = "Poems/Poem-1.jpg";
        one_poets_2 = "Poems/Poem-2.jpg";
        one_poets_3 = "Poems/Poem-0.jpg";
        one_poets_4 = "Poems/Poem-4.jpg";


        //instantiating the data source
        loadList(this, personals);

    }

    public static LoadList getInstance() {
        return mainActivity;
    }


    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();

    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.close();
    }

    private void createData() {

        Poets personal = new Poets(); //Declare personal as an instance of Poets
        personal.setFistName("Vincent");
        personal.setLastName("Bevia");
        personal.setImage("1");
        personal.setAge(49);
        getPicture(personal, one_poets_1);

        personal = datasource.create(personal);
        Log.i(LOGTAG, "person created with id: " + personal.getId());

        personal = new Poets();  //personal it's already been declared so take the declaration away!
        personal.setFistName("Tin");
        personal.setLastName("Bevia");
        personal.setImage("2");
        personal.setAge(17);
        getPicture(personal, one_poets_2);
        personal = datasource.create(personal);
        Log.i(LOGTAG, "person created with id: " + personal.getId());

        personal = new Poets();
        personal.setFistName("Santa");
        personal.setLastName("Escrig");
        personal.setImage("3");
        personal.setAge(32);
        getPicture(personal, one_poets_3);
        personal = datasource.create(personal);
        Log.i(LOGTAG, "person created with id: " + personal.getId());

        personal = new Poets();
        personal.setFistName("Guille");
        personal.setLastName("Bevia");
        personal.setAge(13);
        personal.setImage("4");
        getPicture(personal, one_poets_4);
        personal = datasource.create(personal);
        Log.i(LOGTAG, "person created with id: " + personal.getId());

    }

    private void getPicture(Poets personal, String poetPictures) {
        try {
            InputStream bitmap = getAssets().open(poetPictures);
            Bitmap bit = BitmapFactory.decodeStream(bitmap);
            personal.setBmp(bit);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void Set_Referash_Data() {

        //I'm going to declare my list of personal!
        personals = datasource.findAll();
        if (personals.size() == 0) {

            promptUserAboutWhatToDoNext();

        }
        ArrayAdapter<Poets> adapter = new SQLite_List_ModelAdapter(this, personals);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void promptUserAboutWhatToDoNext() {

        AlertDialog.Builder adb = new AlertDialog.Builder(LoadList.this);
        adb.setTitle("Reload?");
        adb.setMessage("Do you want to reload the list?  ");

        adb.setNegativeButton("Cancel",
                new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        finish();

                    }
                });
        adb.setPositiveButton("Ok",
                new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        loadList(LoadList.this, personals);
                    }
                });
        adb.show();
    }

    private void loadList(LoadList context, List<Poets> personals) {
        //instantiating the data source
        datasource = new PoetsDataSource(context); //I'll pass in this as the context
        //now what? The data base is created but is empty! and we aren't writing nor reading!
        datasource.open();

        //I'm going to declare my list of personal!
        personals = datasource.findAll();
        if (personals.size() == 0) {
            createData();
            personals = datasource.findAll(); //don't need to redeclared the list!
        }

        ArrayAdapter<Poets> adapter = new SQLite_List_ModelAdapter(context, personals);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}