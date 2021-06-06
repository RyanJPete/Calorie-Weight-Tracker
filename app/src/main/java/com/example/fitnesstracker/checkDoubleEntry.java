package com.example.fitnesstracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class checkDoubleEntry extends DialogFragment {
    public interface checkDoubleEntryListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    checkDoubleEntryListener listener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (checkDoubleEntryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Overwrite previous value?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(checkDoubleEntry.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(checkDoubleEntry.this);
                    }
                });
        return builder.create();
    }
}
