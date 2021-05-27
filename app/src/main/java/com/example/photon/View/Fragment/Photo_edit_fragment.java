package com.example.photon.View.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.photon.R;

public class Photo_edit_fragment extends Fragment {

    String imgString;
    ImageView imageView, backButton;

    private Bitmap bitmap;

    public Photo_edit_fragment(String imgString) {
        this.imgString = imgString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.photo_edit_fragment, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageViewID);
        backButton = (ImageView) view.findViewById(R.id.backButtonID);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeScreen_fragment()).addToBackStack(null).commit();
            }
        });

        StringToBitMap(imgString);

        imageView.setImageBitmap(bitmap);

        return view;
    }

    public void StringToBitMap(String encodedString) {

        byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);


    }
}