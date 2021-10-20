package com.example.contentproviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int CONTACT_PERMISSIONCODE=101;
    ContentResolver contentResolver;
    Cursor cursor;
    ListView contactList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> stringArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=findViewById(R.id.contactList);
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,stringArrayList);
        contactList.setAdapter(arrayAdapter);

        Toast.makeText(this, ""+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
    }

    public void loadcontacts(View view) {
        getPermisssionMethod();
    }

    private void getPermisssionMethod() {

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},CONTACT_PERMISSIONCODE);
        }
        else

        {
            retriveAllContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case CONTACT_PERMISSIONCODE:

                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    retriveAllContacts();
                }
                else
                {
                    Toast.makeText(this, "Permission Deined", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void retriveAllContacts() {
        contentResolver=getContentResolver();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection=null;
        String[] arg=null;
        String order=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";

        cursor=contentResolver.query(uri,projection,selection,arg,order);

        if(cursor!=null&&cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String Number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                stringArrayList.add(name+"\n"+
                Number+"\n");

              //  Toast.makeText(this, ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
                arrayAdapter.notifyDataSetChanged();
            }
        }
        else
        {
            Toast.makeText(this, "No Contacts Found", Toast.LENGTH_SHORT).show();
        }

    }
}