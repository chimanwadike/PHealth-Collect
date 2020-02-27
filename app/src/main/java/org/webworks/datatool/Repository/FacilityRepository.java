package org.webworks.datatool.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.webworks.datatool.Db.DbAdapter;
import org.webworks.datatool.Model.Facility;

import java.util.ArrayList;

public class FacilityRepository extends DbAdapter {

    private static final String TABLE_NAME = "facility";
    private static final String KEY_ID = "_id";
    private static final String KEY_FACILITY_NAME = "name";
    private static final String KEY_GUID = "guid";
    private static final String KEY_FACILITY_CODE = "code";
    private static final String KEY_CONTACT_PERSON = "contact_person";
    private static final String KEY_LGA_CODE = "lga_code";

    public FacilityRepository(Context _context) {
        super(_context);
    }

    public void saveFacility(String[] facility) {
        SQLiteDatabase db = OpenDb();
        ContentValues values = new ContentValues();

        for (int i = 0; i < facility.length; i++) {
            values.put(KEY_FACILITY_NAME, facility[i]);
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
        values.clear();
    }

    public void addFacility(Facility facility) {
        if (facilityExists(facility.getGuid())) {
            //TODO; check if update date exists and add details if it exist or if it is greater than the one in your database
        }
        else {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_FACILITY_NAME, facility.getName());
            values.put(KEY_FACILITY_CODE, facility.getCode());
            values.put(KEY_GUID, facility.getGuid());
            values.put(KEY_CONTACT_PERSON, facility.getContactPerson());
            values.put(KEY_LGA_CODE, facility.getLgaCode());

            db.insert(TABLE_NAME, null, values);

            values.clear();
            db.close();
        }
    }

    public ArrayList<Facility> getAllFacility()  {
        SQLiteDatabase db = OpenDb();
        ArrayList<Facility> facilities = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Facility facility = new Facility();
                facility.setId(cursor.getInt(0));
                facility.setName(cursor.getString(1));
                facility.setGuid(cursor.getString(2));
                facility.setCode(cursor.getString(3));
                facility.setLgaCode(cursor.getString(5));

                facilities.add(facility);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return facilities;
    }

    public Facility getReferralForm(String guid) {
        SQLiteDatabase db = OpenDb();
        Facility facility = new Facility();
        Cursor cursor = null;
        try{
            cursor = db.query(TABLE_NAME, new String[]{
                    KEY_FACILITY_NAME, KEY_FACILITY_CODE
            }, KEY_GUID + " =? ", new String[]{guid}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                facility.setName(cursor.getString(0));
                facility.setCode(cursor.getString(1));
                cursor.close();
            }
            db.close();
            return facility;
        }finally {
            if(cursor != null)
                cursor.close();
        }
    }

    private boolean facilityExists(String id) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_FACILITY_NAME },
                KEY_GUID + "=?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            cursor.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }

//    public String getFacilityGuid(int id) {
//        SQLiteDatabase db = OpenDb();
//        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_GUID },
//                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            db.close();
//            return cursor.getString(0);
//        }
//        else {
//            db.close();
//            cursor.close();
//            return null;
//        }
//    }

    public String getFacilityGuid(int id, String _lgaCode){
        final ArrayList<Facility> lgaFacilities = new ArrayList<>();
        final ArrayList<Facility> facilities = getAllFacility();
        String guid = "";
        if (id > 0){
            for (Facility facility: facilities
            ) {

                if (facility.getLgaCode().equals(_lgaCode)){
                    lgaFacilities.add(facility);
                }
            }
            id = id - 1;
            guid = lgaFacilities.get(id).getGuid();
        }

        return guid;
    }

    public int getFacilityId(String id) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_ID },
                KEY_GUID + "=?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            return cursor.getInt(0);
        }
        else {
            db.close();
            //cursor.close();
            return 0;
        }
    }

    public String getFacilityName(String guid) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_FACILITY_NAME },
                KEY_GUID + "=?", new String[]{guid}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            return cursor.getString(0);
        }
        else {
            db.close();
            cursor.close();
            return null;
        }
    }

    public String getFacilityCode(String guid) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_FACILITY_CODE },
                KEY_GUID + "=?", new String[]{guid}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            return cursor.getString(0);
        }
        else {
            db.close();
            //cursor.close();
            return null;
        }
    }

    public int getFacilityIdByGuid(String guid) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ KEY_ID },
                KEY_GUID + "=?", new String[]{guid}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            return cursor.getInt(0);
        }
        else {
            db.close();
            //cursor.close();
            return 0;
        }
    }
}
