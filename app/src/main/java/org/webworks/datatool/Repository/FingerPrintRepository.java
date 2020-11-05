package org.webworks.datatool.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webworks.datatool.Db.DbAdapter;
import org.webworks.datatool.Model.FingerPrint;


import java.util.ArrayList;

public class FingerPrintRepository extends DbAdapter {
    private static final String TABLE_NAME = "fingerprints";
    private static final String KEY_ID = "_id";
    private static final String KEY_FINGER_POSITION = "finger_position";
    private static final String KEY_FINGER_CAPTURE = "finger_print_capture";
    private static final String KEY_CLIENT_IDENTIFIER = "fp_client_identifier";

    public FingerPrintRepository(Context _context) {
        super(_context);
    }

    public void saveBulkFingerprint(ArrayList<FingerPrint> fingerPrints){
        if (!hasFingerPrintCaptured(fingerPrints.get(0).getFpClientIdentifier())){
            SQLiteDatabase db = OpenDb();
            db.beginTransaction();
            try{
                ContentValues values = new ContentValues();
                for (FingerPrint fingerPrint : fingerPrints)
                {
                    values.put(KEY_FINGER_POSITION, fingerPrint.getFingerPosition());
                    values.put(KEY_FINGER_CAPTURE, fingerPrint.getFingerPrintCapture());
                    values.put(KEY_CLIENT_IDENTIFIER, fingerPrint.getFpClientIdentifier());
                    db.insert(TABLE_NAME, null, values);
                }
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }

        }
    }

    public ArrayList<String> getAllFingerPrints(){
        SQLiteDatabase db = OpenDb();
        ArrayList<String> fingerprints = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT "+ KEY_FINGER_CAPTURE + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                fingerprints.add(cursor.getString(0));
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return fingerprints;
    }

    public boolean hasFingerPrintCaptured(String clientIdentifier){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT "+ KEY_FINGER_CAPTURE + " FROM " + TABLE_NAME + " WHERE "+ KEY_CLIENT_IDENTIFIER  + " =?", new String[]{String.valueOf(clientIdentifier)});
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            db.close();

            return true;
        }
        return false;
    }

    public String getClientFingerPrints(String clientIdentifier){
        SQLiteDatabase db = OpenDb();
        JSONArray jsonArray = new JSONArray();
        Cursor cursor = db.rawQuery("SELECT "+ KEY_FINGER_POSITION +","+ KEY_FINGER_CAPTURE +" FROM " + TABLE_NAME + " WHERE "+ KEY_CLIENT_IDENTIFIER  + " =?", new String[]{String.valueOf(clientIdentifier)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                JSONObject json = new JSONObject();
                try{
                    json.put(cursor.getString(0), cursor.getString(1));
                    jsonArray.put(json);
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        if (jsonArray != null)
            return jsonArray.toString();
        return null;
    }

}
