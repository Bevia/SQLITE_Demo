package com.beviacode.asqlitebevia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beviacode.asqlitebevia.db.PoetsDBOpenHelper;
import com.beviacode.asqlitebevia.db.PoetsDataSource;
import com.beviacode.asqlitebevia.model.Poets;

import java.util.List;

public class SQLite_List_ModelAdapter extends ArrayAdapter<Poets> {
    Context context;
    List<Poets> poets;
    LayoutInflater vi = null;

    PoetsDataSource datasource;

    LoadList mainActivity = LoadList.getInstance();

    public SQLite_List_ModelAdapter(Context context, List<Poets> poets) {
        //super(context, android.R.id.content, personals);
        super(context, android.R.layout.activity_list_item, poets);
        this.context = context;
        this.poets = poets;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public static class ViewContainer {

        //public TextView categoria_list_adapter_identificador;
        public TextView firstName;
        public TextView lastName;
        public TextView age;
        public ImageView iv;
        public Button delete;

    }

    public View getView(final int position, View view, ViewGroup parent) {

        final ViewContainer viewContainer;
        View rowView = view;

        if (rowView == null) {

            rowView = vi.inflate(R.layout.sql_model_lis_item, parent, false);

            viewContainer = new ViewContainer();

            viewContainer.firstName = (TextView) rowView.findViewById(R.id.firstNameText);
            ((TextView) rowView.findViewById(R.id.firstNameText)).
                    setTypeface(Typeface.createFromAsset(rowView.getContext().getAssets(), "fonts/KaushanScript-Regular.ttf"));

            viewContainer.lastName = (TextView) rowView.findViewById(R.id.lastNameText);
            ((TextView) rowView.findViewById(R.id.lastNameText)).
                    setTypeface(Typeface.createFromAsset(rowView.getContext().getAssets(), "fonts/KaushanScript-Regular.ttf"));

            viewContainer.age = (TextView) rowView.findViewById(R.id.ageText);
            ((TextView) rowView.findViewById(R.id.ageText)).
                    setTypeface(Typeface.createFromAsset(rowView.getContext().getAssets(), "fonts/KaushanScript-Regular.ttf"));

            viewContainer.iv = (ImageView) rowView.findViewById(R.id.imageView1);

            viewContainer.delete = (Button) rowView.findViewById(R.id.btn_delete);

            rowView.setTag(viewContainer);

        } else {
            viewContainer = (ViewContainer) rowView.getTag();
        }


        Poets personal = poets.get(position);

        Bitmap imageView = personal.getBmp();

        viewContainer.firstName.setText(personal.getFistName());

        viewContainer.lastName.setText(personal.getLastName());

        viewContainer.age.setText(Integer.toString(personal.getAge()));

        viewContainer.delete.setTag(personal.getId());

        viewContainer.iv.setVisibility(View.GONE);
        if (imageView != null) {
            viewContainer.iv.setVisibility(View.VISIBLE);
            viewContainer.iv.setImageBitmap(personal.getBmp());
        }


        viewContainer.delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {

                // show a message while loader is loading
                Poets personal = poets.get(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete:  "
                        + personal.getFistName() + "  "
                        + personal.getLastName() + "  from the list?");
                final int user_id = Integer.parseInt(v.getTag().toString());
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok",
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                PoetsDBOpenHelper datasource = new PoetsDBOpenHelper(
                                        context.getApplicationContext());
                                datasource.Delete_Contact(user_id);

                                if (mainActivity != null) {
                                    mainActivity.Set_Referash_Data();
                                }

                            }
                        });
                adb.show();
            }

        });

        return rowView;
    }
}
