package com.example.homework4_1.dialogAlert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homework4_1.R;

public class NoticeDialogFragment extends DialogFragment {
    public interface NoticeDialogListener {
        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }
}
