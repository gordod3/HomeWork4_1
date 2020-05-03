package com.example.homework4_1.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn");
    }

    public LiveData<String> getText() {
        return mText;
    }
}