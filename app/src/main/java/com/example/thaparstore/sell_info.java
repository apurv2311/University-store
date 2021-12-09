package com.example.thaparstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import javax.xml.transform.Result;

public class sell_info extends AppCompatActivity {
    private EditText mItemName;
    private EditText mItemDetails;
    private EditText mItemPrice;
    private Button mSell;
    private Button image_picker;
    private TextView mFileName;

    private String image_url;
    private DatabaseReference databaseReference;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_info);

        mItemName=findViewById(R.id.item_name);
        mItemDetails=findViewById(R.id.item_detail);
        mItemPrice=findViewById(R.id.item_price);
        mSell=findViewById(R.id.create_add);
        image_picker=findViewById(R.id.image_picker);
        image_url="";
        mFileName=findViewById(R.id.file_name);

        mStorageReference= FirebaseStorage.getInstance().getReference().child("add_photos");
        image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSell.setEnabled(false);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference().child("all_adds");
    }

    public void create_add(View view) {
        String name=mItemName.getText().toString();
        String detail=mItemDetails.getText().toString();
        String price=mItemPrice.getText().toString();
        Get_add add=new Get_add(name,detail,price,image_url);
        databaseReference.push().setValue(add);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            //StorageReference storageReference= FirebaseStorage.getInstance().getReference();

            //final StorageReference ref = storageReference.child("picture.jpg");
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;
            
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            String finalDisplayName = displayName;
            mStorageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;

                            mSell.setEnabled(true);
                            image_url = downloadUrl.toString();
                            mFileName.setText(finalDisplayName);
                            Log.d("image_url",image_url);
                        }
                    });                // Set the download URL to the message box, so that the user can send it to the database

                }
            });
        }
        else if(requestCode==2&&resultCode== RESULT_CANCELED)
            mSell.setEnabled(true);
        super.onActivityResult(requestCode, resultCode, data);
    }
}