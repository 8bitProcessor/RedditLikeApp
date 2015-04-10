package com.example.qbit.projectredapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;




public class SubmitComment extends Fragment {
    SaveSharedPreference pm = new SaveSharedPreference();
    public EditText commentInfo;
    public Button submit_comment_button, back_to_comments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View submit_comment = inflater.inflate(R.layout.submit_comment, container, false);
        return submit_comment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
