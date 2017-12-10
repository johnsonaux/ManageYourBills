package com.example.marca.justjava;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import Data.BillsDbHelper;
import Data.DBContract;

/**
 * Created by marca on 03.10.2017.
 */

public class DeleteBillsDialog extends DialogFragment {


    public DeleteBillsDialog() {
    }

    public static DeleteBillsDialog newInstance(int title) {
        DeleteBillsDialog frag = new DeleteBillsDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = getArguments().getInt("title");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage("Rechnung wirklich endgültig löschen?")
                .setPositiveButton(R.string.alert_dialog_ja, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (deleteSelectedItem()) {
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Rechnung konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton(R.string.alert_dialog_nein, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                })
                .create();
    }


    private boolean deleteSelectedItem() {
        Bill bill = BillsContainer.instance().getmBill();
        if (deleteFromDB(bill.getmId())) {
            return true;
        }
        return false;

    }

    public boolean deleteFromDB(int id) {
        BillsDbHelper mDbHelper = new BillsDbHelper(getActivity());
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String whereclause = DBContract.DBEntry._ID + " = " + id;
        int i = database.delete(DBContract.DBEntry.TABLE_NAME_BILLS, whereclause, null);
        Toast.makeText(getActivity(), i + " Rechnung wurde gelöscht", Toast.LENGTH_SHORT).show();
        if (i != 1) {
            return false;
        }
        return true;
    }
}
