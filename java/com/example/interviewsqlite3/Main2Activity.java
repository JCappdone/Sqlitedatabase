package com.example.interviewsqlite3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.interviewsqlite3.Models.UserModel;
import com.example.interviewsqlite3.database.DBOperations;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.imgUser)
    ImageView mImgUser;
    @BindView(R.id.txtUser)
    EditText mTxtUser;
    @BindView(R.id.txtPhone)
    EditText mTxtPhone;
    @BindView(R.id.btnAdd)
    Button mBtnAdd;
    @BindView(R.id.btnEdit)
    Button mBtnEdit;
    @BindView(R.id.btnDelete)
    Button mBtnDelete;
    private DBOperations mDbOperations;
    private UserModel mModel;
    private String mPicturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
         //   Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            checkPermissionRunTime();
        }
        if(getIntent().hasExtra("MODEL")) {
            mModel = getIntent().getExtras().getParcelable("MODEL");
            mTxtUser.setText(mModel.getUserName());
            mTxtPhone.setText(mModel.getUserPhone());
            mPicturePath = mModel.getUserImage();
            mImgUser.setImageURI(Uri.parse(mPicturePath));
        }

        boolean isAddClicked = getIntent().getExtras().getBoolean("IS_ADD_CLICKED");
        if(isAddClicked){
            mBtnDelete.setVisibility(View.INVISIBLE);
            mBtnEdit.setVisibility(View.INVISIBLE);
        }else{
            mBtnAdd.setVisibility(View.INVISIBLE);
        }

        mDbOperations = new DBOperations(this);
        mDbOperations.openDB();


    }

    private void checkPermissionRunTime() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                3003);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3003) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDbOperations.clodeDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDbOperations.openDB();
    }

    @OnClick({R.id.imgUser, R.id.btnAdd, R.id.btnEdit, R.id.btnDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgUser:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                   // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1001);
                } else {
                    checkPermissionRunTime();
                }


                break;
            case R.id.btnAdd:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                  //  Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    checkPermissionRunTime();
                }
                saveData(1);
                break;
            case R.id.btnEdit:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                 //   Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    checkPermissionRunTime();
                }
                saveData(2);
                break;
            case R.id.btnDelete:
                mDbOperations.deleteUserByID(mModel.getUserId());
                Intent intent =  new Intent();
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    private void saveData(int type) {
        boolean isError = false;
        String name, phone, image;
        name = mTxtUser.getText().toString();
        phone = mTxtPhone.getText().toString();
        image = mPicturePath;

        if (name.isEmpty()) {
            mTxtUser.setError("Enter Name");
            isError = true;
        }

        if (phone.isEmpty()) {
            mTxtPhone.setError("Enter Phone");
            isError = true;
        }

        if (image.isEmpty()){
            Toast.makeText(this, "Plz Select Image", Toast.LENGTH_SHORT).show();
            isError = true;
        }

        if (isError) {
            return;
        }

        if (type == 1) {
            mModel = new UserModel(name, phone, image);
            long status = mDbOperations.createUser(mModel);
            if(status == -1){
                Toast.makeText(this, "Plz insert Unique Entry", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        if (type == 2) {
            mModel.setUserName(name);
            mModel.setUserPhone(phone);
            mModel.setUserImage(image);
            mDbOperations.updateUserByID(mModel.getUserId(), mModel);
        }

        Intent intent =  new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            cursor.close();
            mImgUser.setImageURI(Uri.parse(mPicturePath));

        }
    }
}
