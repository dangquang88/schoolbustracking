package com.school.schoolbustracking.View.FragMent;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Presenter.UserPreSenter;
import com.school.schoolbustracking.Presenter.UserView;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.Account.AuthPhoneActivity;
import com.school.schoolbustracking.View.Account.ChangePassActivity;
import com.school.schoolbustracking.View.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragMent_Profile extends Fragment implements UserView {
    View view;
    private ShareF shareF;
    private UserPreSenter userPreSenter;
    private CircleImageView circle_avatar;
    private TextView txtfullname, txtgender, txtdate, txtaddress, txtgrade,txtphonenumber,txtpass;
    private String uid_teacher_contact = "";
    private StorageReference storageReference;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        InitWidget();
        Init();
        return view;
    }

    private void Init() {
         sharedPreferences = getContext().getSharedPreferences("MENU", Context.MODE_PRIVATE);
        userPreSenter = new UserPreSenter(this);
        shareF = new ShareF(getContext());

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                shareF.putToken(task.getResult());
                if (sharedPreferences.getString("TASK", "").equalsIgnoreCase("Student")) {
                    userPreSenter.HandleGetProFile(shareF.getUID());
                    userPreSenter.HandleUpdateToken(shareF.getUID(), 1, task.getResult());
                    userPreSenter.HandleUpdateCall(shareF.getUID(), 1, "Active");

                } else {
                    userPreSenter.HandleGetProFileTeacher(shareF.getUID());
                    userPreSenter.HandleUpdateToken(shareF.getUID(), 2, task.getResult());
                    userPreSenter.HandleUpdateCall(shareF.getUID(), 2, "Active");
                }
            }
        });

        circle_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                } else {
                    PickGallary();
                }
            }
        });
        txtphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AuthPhoneActivity.class));

            }
        });
        txtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), ChangePassActivity.class));

            }
        });



    }

    private void DiaLogUpdate(int i, int i1) {
        Dialog dialog  = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_update);
        dialog.show();
        EditText editvalue = dialog.findViewById(R.id.edittext);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        switch (i){
            case 7:
            editvalue.setHint("Enter Phone Number");
          editvalue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);break;
        }
        dialog.findViewById(R.id.btnupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i){
                    case  7 :
                        if(editvalue.getText().toString().trim().length()> 9 &&
                                editvalue.getText().toString().trim().length()<12){
                            
                        }break;
                    case  6:
                        if(editvalue.getText().toString().trim().length()>0){
                            
                        }else{
                            Toast.makeText(getContext(), "Grade", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
    }

    private void PickGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data_image = baos.toByteArray();
                 storageReference = FirebaseStorage.getInstance("gs://schoolbus-2a9a1.appspot.com").getReference();
                storageReference.child(shareF.getUID()).putBytes(data_image)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    storageReference = FirebaseStorage.getInstance("gs://schoolbus-2a9a1.appspot.com").getReference();
                                    storageReference.child(shareF.getUID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(@NonNull Uri uri) {
                                            if (uri != null) {
                                                if (sharedPreferences.getString("TASK", "").equalsIgnoreCase("Student")) {
                                                    userPreSenter.HandleUpdatePhoto(uri.toString(),1,shareF.getUID());
                                                } else {
                                                    userPreSenter.HandleUpdatePhoto(uri.toString(),2,shareF.getUID());
                                                }

                                                Picasso.with(getContext()).load(uri).into(circle_avatar);
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            circle_avatar.setImageResource(R.drawable.avatar);
                                        }
                                    });
                                }

                            }
                        });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitWidget() {
        circle_avatar = view.findViewById(R.id.avatar);
        txtaddress = view.findViewById(R.id.txtaddress);
        txtdate = view.findViewById(R.id.txtdate);
        txtgender = view.findViewById(R.id.txtgender);
        txtfullname = view.findViewById(R.id.txtname);
        txtgrade = view.findViewById(R.id.txtgrade);
        txtphonenumber = view.findViewById(R.id.txtphone);
        txtpass = view.findViewById(R.id.txtpass);


    }

    @Override
    public void OnSucess(String uid) {

    }

    @Override
    public void OnFail(String s) {

    }

    @Override
    public void getDataProfile(String uid, String address, String gender, String date_of_birth, String fullname,
                               String grade, double lat, double lo, String tokenfcm, String photo, String uid_teacher,
                               String phonenumber) {

        Picasso.with(getContext()).load(photo).into(circle_avatar);
        txtfullname.setText(fullname);
        txtgender.setText(gender);
        txtdate.setText(date_of_birth);
        txtaddress.setText(address);
        txtgrade.setText(grade);
        shareF.PutUIDTC(uid_teacher);
        shareF.PutUID(uid);
        txtphonenumber.setText(phonenumber);


    }

    @Override
    public void getDataproFileTeacher(String uid, String address, String gender, String date_of_birth,
                                      String fullname, String grade, double lat, double lo, String tokenfcm, String photo,
                                      String phonenumber) {
        Picasso.with(getContext()).load(photo).into(circle_avatar);
        txtfullname.setText(fullname);
        txtgender.setText(gender);
        txtdate.setText(date_of_birth);
        txtaddress.setText(address);
        txtgrade.setText(grade);
        shareF.putLa(lat);
        shareF.putLo(lo);
        shareF.PutUID(uid);
        txtphonenumber.setText(phonenumber);


    }
}
