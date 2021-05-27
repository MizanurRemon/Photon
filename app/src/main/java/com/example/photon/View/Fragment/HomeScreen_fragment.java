package com.example.photon.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.photon.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class HomeScreen_fragment extends Fragment {

    LinearLayout cameraButton, galleryButton;

    final int IMAGE_REQUEST_CODE = 1;
    int check;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 1, CAMERA_REQUEST = 1, REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;

    private  Bitmap bitmap;
    private Uri filepath;

    Dialog dialog;
    ImageView showImage;


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                check = 1;
                //dialog.show();
                //showImage.setImageBitmap(bitmap);


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    //addimage.setImageBitmap(bitmap);
                    check = 1;
                   // dialog.show();
                    //showImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            imageToString(bitmap);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_screen_fragment, container, false);

        cameraButton = (LinearLayout) view.findViewById(R.id.cameraButtonID);
        galleryButton = (LinearLayout) view.findViewById(R.id.galleryButtonID);

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.imageshow_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        showImage = (ImageView) dialog.findViewById(R.id.imageAlertViewID);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                Intent intent = new Intent(new Intent(Intent.ACTION_PICK));
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);
            }
        });
        return view;
    }


    private void imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String image = Base64.encodeToString(imgbytes, Base64.DEFAULT);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Photo_edit_fragment(image)).addToBackStack(null).commit();
    }

}