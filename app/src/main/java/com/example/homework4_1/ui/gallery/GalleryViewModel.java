package com.example.homework4_1.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Тут не будет ничего но пусть называеться месседжер");
    }

    public LiveData<String> getText() {
        return mText;
    }
}