package com.example.aninterface.ui.profile;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is member info fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}