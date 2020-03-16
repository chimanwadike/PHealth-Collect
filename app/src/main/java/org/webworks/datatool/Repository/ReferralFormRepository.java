package org.webworks.datatool.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.webworks.datatool.Db.DbAdapter;
import org.webworks.datatool.Model.ClientForm;
import org.webworks.datatool.Utility.UtilFuns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReferralFormRepository extends DbAdapter {

    private final String TABLE_NAME = "referral";
    private final String KEY_ID = "_id";
    private final String KEY_API_ID = "api_id";
    private final String KEY_CLIENT_NAME = "name";
    private final String KEY_DATE = "date";
    private final String KEY_CLIENT_CODE = "code";
    private final String KEY_CLIENT_ADDRESS = "address";
    private final String KEY_CLIENT_PHONE = "phone";
    private final String KEY_CLIENT_AGE = "age";
    private final String KEY_CLIENT_SEX = "sex";
    private final String KEY_CLIENT_MARITAL_STATUS = "marital_status";
    private final String KEY_CLIENT_EMPLOYMENT_STATUS = "employment_status";
    private final String KEY_CLIENT_EDUCATION_LEVEL = "education_level";
    private final String KEY_CLIENT_RELIGION = "religion";
    private final String KEY_FINAL_RECENCY_TEST_RESULT = "final_recency_test_result";
    private final String KEY_CLIENT_IDENTIFIER = "client_identifier";
    private final String KEY_CLIENT_STATE = "client_state";
    private final String KEY_CLIENT_LGA = "client_lga";
    private final String KEY_CLIENT_VILLAGE = "client_village";
    private final String KEY_GEO_CODE = "client_geo_code";
    private final String KEY_RECENCY_TEST_TYPE ="hiv_recency_test_type";
    private final String KEY_RECENCY_TEST_DATE = "hiv_recency_test_date";
    private final String KEY_REFERRED_TO = "referred_to";
    private final String KEY_REFERRED_FROM = "referred_from";
    private final String KEY_REFERRED_SERVICE = "referred_service";
    private final String KEY_COMMENT = "comment";
    private final String KEY_CREATE_DATE = "create_date";
    private final String KEY_UPDATE_DATE = "update_date";
    private final String KEY_REFERRED = "referred";
    private final String KEY_UPLOADED = "uploaded";
    private final String KEY_REPORTED = "reported";
    private final String KEY_DATE_REPORTED = "date_reported";
    private final String KEY_DATE_TESTED = "date_hiv_tested";
    private final String KEY_CLIENT_LASTNAME = "lastname";
    private final String KEY_CLIENT_DOB = "dob";
    private final String KEY_CLIENT_ESTIMATED_DOB = "estimated_dob";
    private final String KEY_CLIENT_HIV_RESULT = "hiv_result";
    private final String KEY_CLIENT_HIV_RESULT_DATE = "date_hiv_result";
    private final String KEY_CLIENT_TESTING_POINT = "testing_point";
    private final String KEY_CLIENT_PREVIOUSLY_TESTED = "previously_tested";
    private final String KEY_CLIENT_SESSION_TYPE = "session_type";
    private final String KEY_CLIENT_FROM_INDEX_CLIENT = "from_index_client";
    private final String KEY_CLIENT_INDEX_TESTING_TYPE = "index_testing_type";
    private final String KEY_CLIENT_INDEX_CLIENT_ID = "index_client_id";
    private final String KEY_FORM_PROGRESS = "form_progress";
    private final String KEY_PRETEST = "pretest";
    private final String KEY_CURRENT_RESULT = "current_result";
    private final String KEY_DATE_REFERRED = "date_referred";
    private final String KEY_TESTED_BEFORE = "tested_before";
    private final String KEY_POST_TEST = "post_test";
    private final int FORM_NOT_UPLOADED = 0;
    private final int FORM_UPLOADED_EDITABLE = 1;
    private final int FORM_UPLOADED_NOT_EDITABLE = 2;
    private final int FORM_DOWNLOADED = 3;
    private final String KEY_NAME = "name";
    private final String KEY_STOPPED_AT_PRETEST = "stopped_at_pretest";
    private final String KEY_TRACED = "traced";
    private final String KEY_EMPTY = "";
    private final String KEY_CLIENT_CONFIRMED = "client_confirmed";
    private final String KEY_CLIENT_DATE_CONFIRMED = "date_client_confirmed";
    private final String KEY_USER = "user_id";
    private final String KEY_FACILITY = "facility_id";
    private final String KEY_RST_AGE_GROUP = "rst_agegroup";
    private final String KEY_RST_INFO = "risk_stratification";
    private final String KEY_RST_TEST_DATE = "rst_test_date";
    private final String KEY_RST_TEST_RESULT = "rst_test_result";
    private final String KEY_FACILITY_LGA = "referral_lga";
    private final String KEY_FACILITY_STATE = "referral_state";
    private final String KEY_RISK_LEVEL = "risk_level";
    private final String KEY_TESTING_AREA = "testing_area";

    public ReferralFormRepository(Context _context) {
        super(_context);
    }

    public long saveReferralForm(ClientForm referralForm) {
        SQLiteDatabase db = OpenDb();
        ContentValues values = new ContentValues();
        long saved;

        values.put(KEY_CLIENT_NAME, referralForm.getClientName());
        values.put(KEY_CLIENT_LASTNAME, referralForm.getClientLastname());
        values.put(KEY_DATE, referralForm.getFormDate());
        values.put(KEY_DATE_TESTED, referralForm.getDateOfHivTest());
        values.put(KEY_CLIENT_CODE, referralForm.getClientCode());
        values.put(KEY_CLIENT_ADDRESS, referralForm.getClientAddress());
        values.put(KEY_CLIENT_PHONE, referralForm.getClientPhone());
        values.put(KEY_CLIENT_AGE, referralForm.getAge());
        values.put(KEY_CLIENT_DOB, referralForm.getDob());
        values.put(KEY_CLIENT_ESTIMATED_DOB, referralForm.getEstimatedDob());
        values.put(KEY_CLIENT_HIV_RESULT, referralForm.getHivResult());
        values.put(KEY_CLIENT_HIV_RESULT_DATE, referralForm.getDateOfPrevHivTest());
        values.put(KEY_CLIENT_PREVIOUSLY_TESTED, referralForm.getPreviouslyTested());
        values.put(KEY_CLIENT_SESSION_TYPE, referralForm.getSessionType());
        values.put(KEY_CLIENT_FROM_INDEX_CLIENT, referralForm.getIndexClient());
        values.put(KEY_CLIENT_INDEX_TESTING_TYPE, referralForm.getIndexClientType());
        values.put(KEY_CLIENT_INDEX_CLIENT_ID, referralForm.getIndexClientId());
        values.put(KEY_CLIENT_TESTING_POINT, referralForm.getTestingPoint());
        values.put(KEY_CLIENT_SEX, referralForm.getSex());
        values.put(KEY_REFERRED_TO, referralForm.getRefferedTo());
        values.put(KEY_REFERRED_FROM, referralForm.getRefferedFrom());
        values.put(KEY_REFERRED_SERVICE, referralForm.getServiceNeeded());
        values.put(KEY_COMMENT, referralForm.getComment());
        values.put(KEY_CREATE_DATE, new Date().toString());
        values.put(KEY_FORM_PROGRESS, referralForm.getProgress());
        values.put(KEY_PRETEST, referralForm.getPretest());
        values.put(KEY_REFERRED, referralForm.getReferred());
        values.put(KEY_UPLOADED, referralForm.getUploaded());
        values.put(KEY_CURRENT_RESULT, referralForm.getCurrentHivResult());
        values.put(KEY_TESTED_BEFORE, referralForm.getTestedBefore());
        values.put(KEY_POST_TEST, referralForm.getPostTest());
        values.put(KEY_DATE_REFERRED, referralForm.getDateReferred());

        //newly added elements
        values.put(KEY_CLIENT_IDENTIFIER, referralForm.getClientIdentifier());
        values.put(KEY_CLIENT_STATE, referralForm.getClientState());
        values.put(KEY_CLIENT_LGA, referralForm.getClientLga());
        values.put(KEY_CLIENT_VILLAGE, referralForm.getClientVillage());
        values.put(KEY_GEO_CODE, referralForm.getClientGeoCode());
        values.put(KEY_CLIENT_MARITAL_STATUS, referralForm.getMaritalStatus());
        values.put(KEY_CLIENT_EMPLOYMENT_STATUS, referralForm.getEmploymentStatus());
        values.put(KEY_CLIENT_RELIGION, referralForm.getReligion());
        values.put(KEY_CLIENT_EDUCATION_LEVEL, referralForm.getEducationLevel());
        values.put(KEY_RECENCY_TEST_TYPE, referralForm.getHivRecencyTestType());
        values.put(KEY_RECENCY_TEST_DATE,referralForm.getHivRecencyTestDate());
        values.put(KEY_FINAL_RECENCY_TEST_RESULT, referralForm.getFinalRecencyTestResult());
        values.put(KEY_STOPPED_AT_PRETEST,referralForm.getStoppedAtPreTest());
        values.put(KEY_TRACED, referralForm.getTraced());
        values.put(KEY_USER, referralForm.getUser());
        values.put(KEY_FACILITY, referralForm.getFacility());

        //Risk Stratification Elements
        values.put(KEY_RST_AGE_GROUP,referralForm.getRstAgeGroup());
        values.put(KEY_RST_INFO, referralForm.getRstInformation());
        values.put(KEY_RST_TEST_DATE, referralForm.getRstTestDate());
        values.put(KEY_RST_TEST_RESULT, referralForm.getRstTestResult());
        values.put(KEY_RISK_LEVEL,referralForm.getRiskLevel());
        values.put(KEY_TESTING_AREA, referralForm.getReferralTestingArea());

        saved = db.insert(TABLE_NAME, null, values);
        db.close();
        values.clear();
        return saved;
    }


    public long updateReferralSocialDemo(ClientForm referralForm) {
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_CLIENT_NAME, referralForm.getClientName());
            values.put(KEY_CLIENT_LASTNAME, referralForm.getClientLastname());
            values.put(KEY_DATE_TESTED, referralForm.getDateOfHivTest());
            values.put(KEY_CLIENT_CODE, referralForm.getClientCode());

            values.put(KEY_CLIENT_ADDRESS, referralForm.getClientAddress());
            values.put(KEY_CLIENT_PHONE, referralForm.getClientPhone());
            values.put(KEY_CLIENT_AGE, referralForm.getAge());
            values.put(KEY_CLIENT_DOB, referralForm.getDob());
            values.put(KEY_CLIENT_ESTIMATED_DOB, referralForm.getEstimatedDob());
            values.put(KEY_CLIENT_HIV_RESULT, referralForm.getHivResult());
            values.put(KEY_CLIENT_HIV_RESULT_DATE, referralForm.getDateOfPrevHivTest());
            values.put(KEY_CLIENT_PREVIOUSLY_TESTED, referralForm.getPreviouslyTested());
            values.put(KEY_CLIENT_SESSION_TYPE, referralForm.getSessionType());
            values.put(KEY_CLIENT_FROM_INDEX_CLIENT, referralForm.getIndexClient());
            values.put(KEY_CLIENT_INDEX_TESTING_TYPE, referralForm.getIndexClientType());
            values.put(KEY_CLIENT_INDEX_CLIENT_ID, referralForm.getIndexClientId());
           // values.put(KEY_CLIENT_TESTING_POINT, referralForm.getTestingPoint());
          //  values.put(KEY_CLIENT_INDEX_DOM_TYPE, referralForm.getIndexDomClientType());
            values.put(KEY_CLIENT_SEX, referralForm.getSex());
            values.put(KEY_UPDATE_DATE, new Date().toString());

            //newly added elements
            values.put(KEY_CLIENT_IDENTIFIER, referralForm.getClientIdentifier());
//            values.put(KEY_CLIENT_STATE, referralForm.getClientState());
//            values.put(KEY_CLIENT_LGA, referralForm.getClientLga());
            values.put(KEY_CLIENT_VILLAGE, referralForm.getClientVillage());
            values.put(KEY_GEO_CODE, referralForm.getClientGeoCode());
            values.put(KEY_CLIENT_MARITAL_STATUS, referralForm.getMaritalStatus());
            values.put(KEY_CLIENT_EMPLOYMENT_STATUS, referralForm.getEmploymentStatus());
            values.put(KEY_CLIENT_RELIGION, referralForm.getReligion());
            values.put(KEY_CLIENT_EDUCATION_LEVEL, referralForm.getEducationLevel());
            values.put(KEY_RECENCY_TEST_TYPE, referralForm.getHivRecencyTestType());
            values.put(KEY_RECENCY_TEST_DATE,referralForm.getHivRecencyTestDate());
            values.put(KEY_FINAL_RECENCY_TEST_RESULT, referralForm.getFinalRecencyTestResult());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }

    public long updateReferralPretest(ClientForm referralForm) {
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_PRETEST, referralForm.getPretest());
            values.put(KEY_FORM_PROGRESS, referralForm.getProgress());
            values.put(KEY_CLIENT_SEX, referralForm.getSex());
            values.put(KEY_UPDATE_DATE, new Date().toString());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }

    public long updateReferralResult(ClientForm referralForm) {
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_FORM_PROGRESS, referralForm.getProgress());
            values.put(KEY_CURRENT_RESULT, referralForm.getCurrentHivResult());
            values.put(KEY_UPDATE_DATE, new Date().toString());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }

    public long updateReferralPostTest(ClientForm referralForm) {
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_REFERRED_FROM, referralForm.getRefferedFrom());
            values.put(KEY_REFERRED_TO, referralForm.getRefferedTo());
            values.put(KEY_REFERRED,referralForm.getReferred());
            values.put(KEY_TESTED_BEFORE, referralForm.getTestedBefore());
            values.put(KEY_POST_TEST, referralForm.getPostTest());
            values.put(KEY_FORM_PROGRESS, referralForm.getProgress());
            values.put(KEY_UPDATE_DATE, new Date().toString());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }

    public long updateReferralRefer(ClientForm referralForm) {
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_DATE_REFERRED, referralForm.getDateReferred());
            values.put(KEY_FORM_PROGRESS, referralForm.getProgress());
            values.put(KEY_REFERRED_TO, referralForm.getRefferedTo());
            values.put(KEY_FACILITY_STATE, referralForm.getReferralState());
            values.put(KEY_FACILITY_LGA, referralForm.getReferralLga());
            values.put(KEY_REFERRED_SERVICE, referralForm.getServiceNeeded());
            values.put(KEY_COMMENT, referralForm.getComment());
            values.put(KEY_UPDATE_DATE, new Date().toString());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }

    public long saveApiReferralForm(ClientForm referralForm) {
        if (referralFormApiIdExist(referralForm.getApiId())){
            if (referralForm.getClientConfirmed() == 1){
                long save;
                SQLiteDatabase db = OpenDb();
                ContentValues values = new ContentValues();
                values.put(KEY_CLIENT_CONFIRMED, referralForm.getClientConfirmed());
                values.put(KEY_CLIENT_DATE_CONFIRMED, referralForm.getDateClientConfirmed());
                save = db.update(TABLE_NAME, values, KEY_API_ID + "=?", new String[]{String.valueOf(referralForm.getApiId())});
                db.close();
                values.clear();
                return save;
            }
            return -1;
        }
        else {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();
            long saved;

            values.put(KEY_API_ID, referralForm.getApiId());
            values.put(KEY_CLIENT_NAME, referralForm.getClientName());
            values.put(KEY_CLIENT_LASTNAME, referralForm.getClientLastname());
            values.put(KEY_DATE_TESTED, referralForm.getDateOfHivTest());
            values.put(KEY_DATE, referralForm.getFormDate());
            values.put(KEY_CLIENT_CODE, referralForm.getClientCode());
            values.put(KEY_CLIENT_ADDRESS, referralForm.getClientAddress());
            values.put(KEY_CLIENT_PHONE, referralForm.getClientPhone());
            values.put(KEY_CLIENT_AGE, referralForm.getAge());
            values.put(KEY_CLIENT_DOB, referralForm.getDob());
            values.put(KEY_CLIENT_ESTIMATED_DOB, referralForm.getEstimatedDob());
            values.put(KEY_CLIENT_HIV_RESULT, referralForm.getHivResult());
            values.put(KEY_CLIENT_HIV_RESULT_DATE, referralForm.getDateOfPrevHivTest());
            values.put(KEY_CLIENT_PREVIOUSLY_TESTED, referralForm.getPreviouslyTested());
            values.put(KEY_CLIENT_SESSION_TYPE, referralForm.getSessionType());
            values.put(KEY_CLIENT_FROM_INDEX_CLIENT, referralForm.getIndexClient());
            values.put(KEY_CLIENT_INDEX_TESTING_TYPE, referralForm.getIndexClientType());
            values.put(KEY_CLIENT_INDEX_CLIENT_ID, referralForm.getIndexClientId());
            values.put(KEY_CLIENT_TESTING_POINT, referralForm.getTestingPoint());
            values.put(KEY_CLIENT_SEX, referralForm.getSex());

            values.put(KEY_REPORTED, referralForm.getReported());
            values.put(KEY_REFERRED, referralForm.getReferred());
            values.put(KEY_DATE_REPORTED, referralForm.getDateClientReported());
            values.put(KEY_REFERRED_TO, referralForm.getRefferedTo());
            values.put(KEY_REFERRED_FROM, referralForm.getRefferedFrom());
            values.put(KEY_REFERRED_SERVICE, referralForm.getServiceNeeded());
            values.put(KEY_COMMENT, referralForm.getComment());
            values.put(KEY_CREATE_DATE, new Date().toString());
            values.put(KEY_UPLOADED, referralForm.getUploaded());
            values.put(KEY_CURRENT_RESULT, referralForm.getCurrentHivResult());
            values.put(KEY_TESTED_BEFORE, referralForm.getTestedBefore());
            values.put(KEY_PRETEST, referralForm.getPretest());
            values.put(KEY_POST_TEST, referralForm.getPostTest());
            values.put(KEY_DATE_REFERRED, referralForm.getDateReferred());
            values.put(KEY_FORM_PROGRESS, referralForm.getProgress());
            //newly added elements
            values.put(KEY_CLIENT_IDENTIFIER, referralForm.getClientIdentifier());
            values.put(KEY_CLIENT_STATE, referralForm.getClientState());
            values.put(KEY_CLIENT_LGA, referralForm.getClientLga());
            values.put(KEY_CLIENT_VILLAGE, referralForm.getClientVillage());
            values.put(KEY_GEO_CODE, referralForm.getClientGeoCode());
            values.put(KEY_CLIENT_MARITAL_STATUS, referralForm.getMaritalStatus());
            values.put(KEY_CLIENT_EMPLOYMENT_STATUS, referralForm.getEmploymentStatus());
            values.put(KEY_CLIENT_RELIGION, referralForm.getReligion());
            values.put(KEY_CLIENT_EDUCATION_LEVEL, referralForm.getEducationLevel());
            values.put(KEY_RECENCY_TEST_TYPE, referralForm.getHivRecencyTestType());
            values.put(KEY_RECENCY_TEST_DATE,referralForm.getHivRecencyTestDate());
            values.put(KEY_FINAL_RECENCY_TEST_RESULT, referralForm.getFinalRecencyTestResult());
            values.put(KEY_CLIENT_CONFIRMED, referralForm.getClientConfirmed());
            values.put(KEY_CLIENT_DATE_CONFIRMED, referralForm.getDateClientConfirmed());
            values.put(KEY_USER, referralForm.getUser());
            values.put(KEY_FACILITY, referralForm.getFacility());

            //Risk Stratification Elements
            values.put(KEY_RST_AGE_GROUP,referralForm.getRstAgeGroup());
            values.put(KEY_RST_INFO, referralForm.getRstInformation());
            values.put(KEY_RST_TEST_DATE, referralForm.getRstTestDate());
            values.put(KEY_RST_TEST_RESULT, referralForm.getRstTestResult());
            values.put(KEY_RISK_LEVEL, referralForm.getRiskLevel());
            values.put(KEY_TESTING_AREA, referralForm.getReferralTestingArea());


            try {
                saved = db.insert(TABLE_NAME, null, values);
                db.close();
                values.clear();
                return saved;
            }
            catch (Exception e) {
                db.close();
                values.clear();
                e.printStackTrace();
                return -1;
            }
        }
    }

    public long updateRiskStratification(ClientForm referralForm){
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_RST_AGE_GROUP,referralForm.getRstAgeGroup());
            values.put(KEY_RST_INFO, referralForm.getRstInformation());
            values.put(KEY_RST_TEST_DATE, referralForm.getRstTestDate());
            values.put(KEY_RST_TEST_RESULT, referralForm.getRstTestResult());
            values.put(KEY_RISK_LEVEL, referralForm.getRiskLevel());
            values.put(KEY_CLIENT_STATE, referralForm.getClientState());
            values.put(KEY_CLIENT_LGA, referralForm.getClientLga());
            values.put(KEY_TESTING_AREA, referralForm.getReferralTestingArea());
            values.put(KEY_CLIENT_TESTING_POINT, referralForm.getTestingPoint());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }
    public ArrayList<ClientForm> getAllReferralForm() {
        SQLiteDatabase db = OpenDb();
        ArrayList<ClientForm> forms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED + " AND ("
                + KEY_REFERRED + " = 0 AND " + KEY_REFERRED_FROM + " != " + KEY_REFERRED_TO + ") ORDER BY " + KEY_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                ClientForm form = new ClientForm();
                form.setId(cursor.getInt(0));
                form.setClientName(cursor.getString(1));
                form.setFormDate(cursor.getString(2));
                form.setClientCode(cursor.getString(3));
                form.setClientAddress(cursor.getString(4));
                form.setClientPhone(cursor.getString(5));
                form.setAge(cursor.getString(6));
                form.setSex(cursor.getInt(7));
                form.setRefferedTo(cursor.getInt(8));
                form.setServiceNeeded(cursor.getString(9));
                form.setComment(cursor.getString(10));
                form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
                form.setReferred(cursor.getInt(13));
                form.setApiId(cursor.getString(14));
                form.setUploaded(cursor.getInt(15));
                form.setReported(cursor.getInt(16));
                form.setClientLastname(cursor.getString(17));
                form.setDob(cursor.getString(18));
                form.setEstimatedDob(cursor.getString(19));
                form.setHivResult(cursor.getInt(20));
                form.setTestingPoint(cursor.getString(22));
                form.setPreviouslyTested(cursor.getInt(23));
                form.setDateOfPrevHivTest(cursor.getString(24));
                form.setRefferedFrom(cursor.getInt(25));
                form.setDateClientReported(cursor.getString(26));
                form.setSessionType(cursor.getInt(32));
                form.setIndexClient(cursor.getInt(33));
                form.setIndexClientType(cursor.getInt(34));
                form.setIndexClientId(cursor.getString(35));
                form.setProgress(cursor.getInt(36));
                form.setPretest(cursor.getString(37));
                form.setCurrentHivResult(cursor.getInt(38));
                form.setDateReferred(cursor.getString(39));
                form.setTestedBefore(cursor.getInt(40));
                form.setPostTest(cursor.getString(41));
                form.setClientHospitalNumber(cursor.getString(43));
                form.setClientIdentifier(cursor.getString(44));
                form.setClientState(cursor.getString(45));
                form.setClientLga(cursor.getString(46));
                form.setClientVillage(cursor.getString(47));
                form.setClientGeoCode(cursor.getString(48));
                form.setMaritalStatus(cursor.getInt(49));
                form.setReligion(cursor.getInt(50));
                form.setEducationLevel(cursor.getInt(51));
                form.setHivRecencyTestType(cursor.getType(54));
                form.setHivRecencyTestDate(cursor.getString(55));
                form.setFinalRecencyTestResult(cursor.getInt(56));

                forms.add(form);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return forms;
    }

    public int getAllFormsWithinRange(Date start, Date end){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = 0;
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date mainDate = null;
                try {
                    mainDate = sdf.parse(cursor.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mainDate != null) {
                    //if not a range then that of a single month will be
                    //if (start.getMonth() == mainDate.getMonth())
                    if (start.before(mainDate) && end.after(mainDate)) {
                        count++;
                    }
                }
            }while (cursor.moveToNext());
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getPositiveCountWithinRange(Date start, Date end, int ageBracket, int sex){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + "," + KEY_ID + "," + KEY_CLIENT_AGE + " FROM " + TABLE_NAME + " WHERE " + KEY_CLIENT_SEX + " == " + sex +
                " AND " + KEY_UPLOADED + " != " + FORM_DOWNLOADED, null);
        //Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + "," + KEY_ID + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int actualCount = 0;
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date mainDate = null;
                try {
                    mainDate = sdf.parse(cursor.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mainDate != null) {
                    //if not a range then that of a single month will be
                    //if (start.getMonth() == mainDate.getMonth())
                    if (start.before(mainDate) && end.after(mainDate)) {
                        int dbAge = cursor.getInt(2); //UtilFuns.calculateAge(cursor.getString(0));

                        switch (ageBracket) {
                            case 1:
                                int[] ages1 = {1,2,3,4};
                                for (int a : ages1) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 2:
                                int[] ages2 = {5,6,7,8,9};
                                for (int a : ages2) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 3:
                                int[] ages3 = {10,11,12,13,14};
                                for (int a : ages3) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 4:
                                int[] ages4 = {15,16,17,18,19};
                                for (int a : ages4) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 5:
                                int[] ages5 = {20,21,22,23,24};
                                for (int a : ages5) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 6:
                                int[] ages6 = {25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49};
                                for (int a : ages6) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 7:
                                int[] ages7 = {50};
                                for (int a : ages7) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                        }
                    }
                }
            }while (cursor.moveToNext());
            cursor.close();
            return actualCount;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getReportCount(Calendar date, int ageBracket, int sex){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + "," + KEY_ID + "," + KEY_CLIENT_AGE + " FROM " + TABLE_NAME + " WHERE " + KEY_CLIENT_SEX + " == " + sex +
                " AND " + KEY_UPLOADED + " != " + FORM_DOWNLOADED, null);
        //Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + "," + KEY_ID + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int actualCount = 0;
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date mainDate = null;
                Calendar calendar = Calendar.getInstance();
                try {
                    mainDate = sdf.parse(cursor.getString(0));
                    calendar.setTime(mainDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (calendar != null) {
                    //if not a range then that of a single month will be
                    //if (start.getMonth() == mainDate.getMonth())
                    if (date.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && (calendar.get(Calendar.YEAR) == date.get(Calendar.YEAR))) {
                        int dbAge = cursor.getInt(2); //UtilFuns.calculateAge(cursor.getString(0));

                        switch (ageBracket) {
                            case 1:
                                int[] ages1 = {1,2,3,4};
                                for (int a : ages1) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 2:
                                int[] ages2 = {5,6,7,8,9};
                                for (int a : ages2) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 3:
                                int[] ages3 = {10,11,12,13,14};
                                for (int a : ages3) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 4:
                                int[] ages4 = {15,16,17,18,19};
                                for (int a : ages4) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 5:
                                int[] ages5 = {20,21,22,23,24};
                                for (int a : ages5) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 6:
                                int[] ages6 = {25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49};
                                for (int a : ages6) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                            case 7:
                                int[] ages7 = {50};
                                for (int a : ages7) {
                                    if (dbAge == a) {
                                        actualCount++;
                                    }
                                }
                                break;
                        }
                    }
                }
            }while (cursor.moveToNext());
            cursor.close();
            return actualCount;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getAllFormsWithinMonth(Date start){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = 0;
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date mainDate = null;
                try {
                    mainDate = sdf.parse(cursor.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mainDate != null) {
                    //if not a range then that of a single month will be
                    if (start.getMonth() == mainDate.getMonth()) {
                        count++;
                    }
                }
            }while (cursor.moveToNext());
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getAllFormsWithinDay(Date start){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = 0;
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date mainDate = null;
                try {
                    mainDate = sdf.parse(cursor.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mainDate != null) {
                    //if not a range then that of a single month will be
                    if (start.getDay() == mainDate.getDay()) {
                        count++;
                    }
                }
            }while (cursor.moveToNext());
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public ArrayList<ClientForm> getAllHTSForm()  {
        SQLiteDatabase db = OpenDb();
        ArrayList<ClientForm> forms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED + " OR (" + KEY_REFERRED + " = 1 AND "  + KEY_REFERRED_FROM + " = " + KEY_REFERRED_TO + ") ORDER BY " +
                KEY_ID + " DESC", null);
         //Todo include where facility = this facility in the query statement
        if (cursor.moveToFirst()) {
            do {
                ClientForm form = new ClientForm();
                form.setId(cursor.getInt(0));
                form.setClientName(cursor.getString(1));
                form.setFormDate(cursor.getString(2));
                form.setClientCode(cursor.getString(3));
                form.setClientAddress(cursor.getString(4));
                form.setClientPhone(cursor.getString(5));
                form.setAge(cursor.getString(6));
                form.setSex(cursor.getInt(7));
                form.setRefferedTo(cursor.getInt(8));
                form.setServiceNeeded(cursor.getString(9));
                form.setComment(cursor.getString(10));
                form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
                form.setReferred(cursor.getInt(13));
                form.setApiId(cursor.getString(14));
                form.setUploaded(cursor.getInt(15));
                form.setReported(cursor.getInt(16));
                form.setClientLastname(cursor.getString(17));
                form.setDob(cursor.getString(18));
                form.setEstimatedDob(cursor.getString(19));
                form.setHivResult(cursor.getInt(20));
                form.setDateOfPrevHivTest(cursor.getString(21));
                form.setTestingPoint(cursor.getString(22));
                form.setPreviouslyTested(cursor.getInt(23));
                form.setDateOfHivTest(cursor.getString(24));
                form.setRefferedFrom(cursor.getInt(25));
                form.setDateClientReported(cursor.getString(2));
                form.setSessionType(cursor.getInt(27));
                form.setIndexClient(cursor.getInt(28));
                form.setIndexClientType(cursor.getInt(29));
                form.setIndexClientId(cursor.getString(30));
                form.setProgress(cursor.getInt(31));
                form.setPretest(cursor.getString(32));
                form.setCurrentHivResult(cursor.getInt(33));
                form.setDateReferred(cursor.getString(34));
                form.setTestedBefore(cursor.getInt(35));
                form.setPostTest(cursor.getString(36));
                form.setClientIdentifier(cursor.getString(37));
                form.setClientState(cursor.getString(38));
                form.setClientLga(cursor.getString(39));
                form.setClientVillage(cursor.getString(40));
                form.setClientGeoCode(cursor.getString(41));
                form.setMaritalStatus(cursor.getInt(42));
                form.setEmploymentStaus(cursor.getInt(43));
                form.setEducationLevel(cursor.getInt(45));
                form.setHivRecencyTestType(cursor.getType(46));
                form.setHivRecencyTestDate(cursor.getString(47));
                form.setFinalRecencyTestResult(cursor.getInt(48));
                form.setClientConfirmed(cursor.getInt(51));
                forms.add(form);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return forms;
    }


    public ArrayList<ClientForm> getAllReceivedForm() {
        SQLiteDatabase db = OpenDb();
        ArrayList<ClientForm> forms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " = " + FORM_DOWNLOADED + " AND " + KEY_REFERRED_FROM + " != " + KEY_REFERRED_TO  + " ORDER BY " + KEY_ID + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                ClientForm form = new ClientForm();
                form.setId(cursor.getInt(0));
                form.setClientName(cursor.getString(1));
                form.setFormDate(cursor.getString(2));
                form.setClientCode(cursor.getString(3));
                form.setClientAddress(cursor.getString(4));
                form.setClientPhone(cursor.getString(5));
                form.setAge(cursor.getString(6));
                form.setSex(cursor.getInt(7));
                form.setRefferedTo(cursor.getInt(8));
                form.setServiceNeeded(cursor.getString(9));
                form.setComment(cursor.getString(10));
                form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
                form.setReferred(cursor.getInt(13));
                form.setApiId(cursor.getString(14));
                form.setUploaded(cursor.getInt(15));
                form.setReported(cursor.getInt(16));
                form.setClientLastname(cursor.getString(17));
                form.setDob(cursor.getString(18));
                form.setEstimatedDob(cursor.getString(19));
                form.setHivResult(cursor.getInt(20));
                form.setDateOfPrevHivTest(cursor.getString(21));
                form.setTestingPoint(cursor.getString(22));
                form.setPreviouslyTested(cursor.getInt(23));
                form.setDateOfHivTest(cursor.getString(24));
                form.setRefferedFrom(cursor.getInt(25));
                form.setDateClientReported(cursor.getString(26));
                form.setSessionType(cursor.getInt(32));
                form.setIndexClient(cursor.getInt(33));
                form.setIndexClientType(cursor.getInt(34));
                form.setIndexClientId(cursor.getString(35));
                form.setProgress(cursor.getInt(36));
                form.setPretest(cursor.getString(37));
                form.setCurrentHivResult(cursor.getInt(38));
                form.setDateReferred(cursor.getString(39));
                form.setTestedBefore(cursor.getInt(40));
                form.setPostTest(cursor.getString(41));
                form.setClientHospitalNumber(cursor.getString(43));
                form.setClientIdentifier(cursor.getString(44));
                form.setClientState(cursor.getString(45));
                form.setClientLga(cursor.getString(46));
                form.setClientVillage(cursor.getString(47));
                form.setClientGeoCode(cursor.getString(48));
                form.setMaritalStatus(cursor.getInt(49));
                form.setEmploymentStaus(cursor.getInt(50));
                form.setReligion(cursor.getInt(51));
                form.setEducationLevel(cursor.getInt(52));
                form.setHivRecencyTestType(cursor.getType(55));
                form.setHivRecencyTestDate(cursor.getString(56));
                form.setFinalRecencyTestResult(cursor.getInt(57));


                forms.add(form);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return forms;
    }

    public ArrayList<ClientForm> getTodayReferralForm() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SQLiteDatabase db = OpenDb();
        ArrayList<ClientForm> forms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date strDate = null;
                try {
                    strDate = sdf.parse(cursor.getString(cursor.getColumnIndex("create_date")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(!strDate.equals(null) && strDate.after(cal.getTime())) {
                    ClientForm form = new ClientForm();
                    form.setId(cursor.getInt(0));
                    form.setClientName(cursor.getString(1));
                    form.setFormDate(cursor.getString(2));
                    form.setClientCode(cursor.getString(3));
                    form.setClientAddress(cursor.getString(4));
                    form.setClientPhone(cursor.getString(5));
                    form.setAge(cursor.getString(6));
                    form.setSex(cursor.getInt(7));
                    form.setRefferedTo(cursor.getInt(8));
                    form.setServiceNeeded(cursor.getString(9));
                    form.setComment(cursor.getString(10));
                    form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
                    form.setReferred(cursor.getInt(13));
                    form.setApiId(cursor.getString(14));
                    form.setUploaded(cursor.getInt(15));
                    form.setReported(cursor.getInt(16));
                    form.setClientLastname(cursor.getString(17));
                    form.setDob(cursor.getString(18));
                    form.setEstimatedDob(cursor.getString(19));
                    form.setHivResult(cursor.getInt(20));
                    form.setDateOfPrevHivTest(cursor.getString(21));
                    form.setTestingPoint(cursor.getString(22));
                    form.setPreviouslyTested(cursor.getInt(23));
                    form.setDateOfHivTest(cursor.getString(24));
                    form.setRefferedFrom(cursor.getInt(25));
                    form.setDateClientReported(cursor.getString(26));
                    form.setSessionType(cursor.getInt(32));
                    form.setIndexClient(cursor.getInt(33));
                    form.setIndexClientType(cursor.getInt(34));
                    form.setIndexClientId(cursor.getString(35));
                    form.setProgress(cursor.getInt(36));
                    form.setPretest(cursor.getString(37));
                    form.setCurrentHivResult(cursor.getInt(38));
                    form.setDateReferred(cursor.getString(39));
                    form.setTestedBefore(cursor.getInt(40));
                    form.setPostTest(cursor.getString(41));
                    form.setClientHospitalNumber(cursor.getString(43));
                    form.setClientIdentifier(cursor.getString(44));
                    form.setClientState(cursor.getString(45));
                    form.setClientLga(cursor.getString(46));
                    form.setClientVillage(cursor.getString(47));
                    form.setClientGeoCode(cursor.getString(48));
                    form.setMaritalStatus(cursor.getInt(49));
                    form.setEmploymentStaus(cursor.getInt(50));
                    form.setReligion(cursor.getInt(51));
                    form.setEducationLevel(cursor.getInt(52));
                    form.setHivRecencyTestType(cursor.getType(55));
                    form.setHivRecencyTestDate(cursor.getString(56));
                    form.setFinalRecencyTestResult(cursor.getInt(57));
                    forms.add(form);
                }
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return forms;
    }

    public ClientForm getReferralForm(String code) {
        SQLiteDatabase db = OpenDb();
        ClientForm form = new ClientForm();
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                KEY_CLIENT_NAME, KEY_DATE, KEY_CLIENT_CODE, KEY_CLIENT_ADDRESS, KEY_CLIENT_PHONE, KEY_CLIENT_AGE,
                KEY_CLIENT_SEX, KEY_REFERRED_TO, KEY_REFERRED_SERVICE, KEY_COMMENT, KEY_UPLOADED
        }, KEY_CLIENT_CODE + " LIKE ? ", new String[]{"%" + code + "%"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            form.setClientName(cursor.getString(0));
            form.setFormDate(cursor.getString(1));
            form.setClientCode(cursor.getString(2));
            form.setClientAddress(cursor.getString(3));
            form.setClientPhone(cursor.getString(4));
            form.setAge(cursor.getString(5));
            form.setSex(cursor.getInt(6));
            form.setRefferedTo(cursor.getInt(7));
            form.setServiceNeeded(cursor.getString(8));
            form.setComment(cursor.getString(9));
            form.setUploaded(cursor.getInt(10));
            //form.setSubmitted(cursor.getInt(10));
            cursor.close();
        }
        db.close();
        return form;
    }

    public ArrayList<ClientForm> getAllReferralForm(String code) {
        SQLiteDatabase db = OpenDb();
        ArrayList<ClientForm> forms = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT *  FROM "+ TABLE_NAME + " WHERE " + KEY_CLIENT_IDENTIFIER + " LIKE ? OR " + KEY_CLIENT_LASTNAME + " LIKE ? OR " + KEY_CLIENT_NAME + " LIKE ?", new String[]{"%" + code + "%", "%" + code + "%", "%" + code + "%"});
        if (cursor.moveToFirst()) {
            do {
                ClientForm form = new ClientForm();
                form.setId(cursor.getInt(0));
                form.setClientName(cursor.getString(1));
                form.setFormDate(cursor.getString(2));
                form.setClientCode(cursor.getString(3));
                form.setClientAddress(cursor.getString(4));
                form.setClientPhone(cursor.getString(5));
                form.setAge(cursor.getString(6));
                form.setSex(cursor.getInt(7));
                form.setRefferedTo(cursor.getInt(8));
                form.setServiceNeeded(cursor.getString(9));
                form.setComment(cursor.getString(10));
                form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
                form.setReferred(cursor.getInt(13));
                form.setApiId(cursor.getString(14));
                form.setUploaded(cursor.getInt(15));
                form.setReported(cursor.getInt(16));
                form.setClientLastname(cursor.getString(17));
                form.setDob(cursor.getString(18));
                form.setEstimatedDob(cursor.getString(19));
                form.setHivResult(cursor.getInt(20));
                form.setDateOfPrevHivTest(cursor.getString(21));
                form.setTestingPoint(cursor.getString(22));
                form.setPreviouslyTested(cursor.getInt(23));
                form.setDateOfHivTest(cursor.getString(24));
                form.setRefferedFrom(cursor.getInt(25));
                form.setDateClientReported(cursor.getString(2));
                form.setSessionType(cursor.getInt(27));
                form.setIndexClient(cursor.getInt(28));
                form.setIndexClientType(cursor.getInt(29));
                form.setIndexClientId(cursor.getString(30));
                form.setProgress(cursor.getInt(31));
                form.setPretest(cursor.getString(32));
                form.setCurrentHivResult(cursor.getInt(33));
                form.setDateReferred(cursor.getString(34));
                form.setTestedBefore(cursor.getInt(35));
                form.setPostTest(cursor.getString(36));
                form.setClientIdentifier(cursor.getString(37));
                form.setClientState(cursor.getString(38));
                form.setClientLga(cursor.getString(39));
                form.setClientVillage(cursor.getString(40));
                form.setClientGeoCode(cursor.getString(41));
                form.setMaritalStatus(cursor.getInt(42));
                form.setEmploymentStaus(cursor.getInt(43));
                form.setEducationLevel(cursor.getInt(45));
                form.setHivRecencyTestType(cursor.getType(46));
                form.setHivRecencyTestDate(cursor.getString(47));
                form.setFinalRecencyTestResult(cursor.getInt(48));
                form.setClientConfirmed(cursor.getInt(51));

                forms.add(form);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return forms;
    }

    public ClientForm getReferralFormById(int id) {
        SQLiteDatabase db = OpenDb();
        ClientForm form = new ClientForm();
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                KEY_CLIENT_NAME, KEY_DATE, KEY_CLIENT_CODE, KEY_CLIENT_ADDRESS, KEY_CLIENT_PHONE, KEY_CLIENT_AGE,
                KEY_CLIENT_SEX, KEY_REFERRED_TO, KEY_REFERRED_SERVICE, KEY_COMMENT, KEY_UPLOADED, KEY_API_ID, KEY_REPORTED,
                KEY_CLIENT_HIV_RESULT_DATE, KEY_CLIENT_LASTNAME, KEY_CLIENT_HIV_RESULT, KEY_CLIENT_PREVIOUSLY_TESTED,
                KEY_CLIENT_TESTING_POINT, KEY_DATE_TESTED, KEY_DATE_REPORTED, KEY_ID, KEY_REFERRED_FROM, KEY_CLIENT_DOB,
                KEY_CLIENT_ESTIMATED_DOB, KEY_CLIENT_SESSION_TYPE, KEY_CLIENT_FROM_INDEX_CLIENT, KEY_CLIENT_INDEX_TESTING_TYPE, KEY_CLIENT_INDEX_CLIENT_ID,
                KEY_PRETEST, KEY_FORM_PROGRESS, KEY_CURRENT_RESULT, KEY_POST_TEST, KEY_TESTED_BEFORE, KEY_DATE_REFERRED, KEY_CLIENT_IDENTIFIER,
                KEY_CLIENT_MARITAL_STATUS, KEY_CLIENT_RELIGION, KEY_CLIENT_EDUCATION_LEVEL,KEY_CLIENT_EMPLOYMENT_STATUS,KEY_CLIENT_STATE,KEY_CLIENT_LGA, KEY_GEO_CODE, KEY_CLIENT_VILLAGE,
                KEY_RECENCY_TEST_TYPE, KEY_FINAL_RECENCY_TEST_RESULT, KEY_RECENCY_TEST_DATE, KEY_TRACED, KEY_STOPPED_AT_PRETEST, KEY_CLIENT_CONFIRMED, KEY_USER, KEY_FACILITY, KEY_RST_AGE_GROUP, KEY_RST_INFO, KEY_RST_TEST_DATE, KEY_RST_TEST_RESULT,
                KEY_FACILITY_STATE, KEY_FACILITY_LGA, KEY_RISK_LEVEL, KEY_TESTING_AREA

        }, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            form.setClientName(cursor.getString(0));
            form.setFormDate(cursor.getString(1));
            form.setClientCode(cursor.getString(2));
            form.setClientAddress(cursor.getString(3));
            form.setClientPhone(cursor.getString(4));
            form.setAge(cursor.getString(5));
            form.setSex(cursor.getInt(6));
            form.setRefferedTo(cursor.getInt(7));
            form.setServiceNeeded(cursor.getString(8));
            form.setComment(cursor.getString(9));
            form.setComment(cursor.getString(9));
            form.setUploaded(cursor.getInt(10));
            form.setApiId(cursor.getString(11));
            form.setReported(cursor.getInt(12));
            form.setDateOfPrevHivTest(cursor.getString(13));
            form.setClientLastname(cursor.getString(14));
            form.setHivResult(cursor.getInt(15));
            form.setPreviouslyTested(cursor.getInt(16));
            form.setTestingPoint(cursor.getString(17));
            form.setDateOfHivTest(cursor.getString(18));
            form.setDateClientReported(cursor.getString(19));
            form.setId(cursor.getInt(20));
            form.setRefferedFrom(cursor.getInt(21));
            form.setDob(cursor.getString(22));
            form.setEstimatedDob(cursor.getString(23));
            form.setSessionType(cursor.getInt(24));
            form.setIndexClient(cursor.getInt(25));
            form.setIndexClientType(cursor.getInt(26));
            form.setIndexClientId(cursor.getString(27));
            form.setPretest(cursor.getString(28));
            form.setProgress(cursor.getInt(29));
            form.setCurrentHivResult(cursor.getInt(30));
            form.setPostTest(cursor.getString(31));
            form.setTestedBefore(cursor.getInt(32));
            form.setDateReferred(cursor.getString(33));
            form.setClientIdentifier(cursor.getString(34));
            form.setMaritalStatus(cursor.getInt(35));
            form.setReligion(cursor.getInt(36));
            form.setEducationLevel(cursor.getInt(37));
            form.setEmploymentStaus(cursor.getInt(38));
            form.setClientState(cursor.getString(39));
            form.setClientLga(cursor.getString(40));
            form.setClientGeoCode(cursor.getString(41));
            form.setClientVillage(cursor.getString(42));
            form.setHivRecencyTestType(cursor.getInt(43));
            form.setFinalRecencyTestResult(cursor.getInt(44));
            form.setHivRecencyTestDate(cursor.getString(45));
            form.setTraced(cursor.getInt(46));
            form.setStoppedAtPreTest(cursor.getInt(47));
            form.setClientConfirmed(cursor.getInt(48));
            form.setUser(cursor.getString(49));
            form.setFacility(cursor.getString(50));
            form.setRstAgeGroup(cursor.getString(51));
            form.setRstInformation(cursor.getString(52));
            form.setRstTestDate(cursor.getString(53));
            form.setRstTestResult(cursor.getString(54));
            form.setReferralState(cursor.getString(55));
            form.setReferralLga(cursor.getString(56));
            form.setRiskLevel(cursor.getInt(57));
            form.setReferralTestingArea(cursor.getString(58));
            cursor.close();
        }
        db.close();
        return form;
    }

    public ArrayList<Integer> getReferralFormDetailById(int id) {
        SQLiteDatabase db = OpenDb();
        ArrayList<Integer> details = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                KEY_UPLOADED, KEY_FORM_PROGRESS, KEY_REFERRED
        }, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            details.add(cursor.getInt(0));
            details.add(cursor.getInt(1));
            details.add(cursor.getInt(2));
            cursor.close();
        }
        db.close();
        return details;
    }

    public int getReferralFormCount(){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getTodayReferralFormCount(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CREATE_DATE + " FROM " + TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int actualCount = 0;
            do {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
                Date strDate = null;
                try {
                    strDate = sdf.parse(cursor.getString(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(!strDate.equals(null) && strDate.after(cal.getTime())) {
                    actualCount++;
                }
            }while (cursor.moveToNext());
            cursor.close();
            return actualCount;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getHTSFormCount(){
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED + " OR (" + KEY_REFERRED + " = 1 AND "  + KEY_REFERRED_FROM + " = " + KEY_REFERRED_TO + ")", null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public void updateUploadedFromApi(String guid, int formId) {
        SQLiteDatabase db = OpenDb();
        ContentValues values = new ContentValues();

        values.put(KEY_UPLOADED, 1);
        values.put(KEY_API_ID, guid);
        values.put(KEY_UPDATE_DATE, new Date().toString());
        db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(formId)});
    }

    public void lockForm(int formId) {
        SQLiteDatabase db = OpenDb();
        ContentValues values = new ContentValues();

        values.put(KEY_UPLOADED, 2);
        values.put(KEY_UPDATE_DATE, new Date().toString());
        db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(formId)});
    }

    public long updateReferralForm(ClientForm referralForm) {
        long saved = -1;
        if (referralFormExist(referralForm.getId())) {
            SQLiteDatabase db = OpenDb();
            ContentValues values = new ContentValues();

            values.put(KEY_CLIENT_NAME, referralForm.getClientName());
            values.put(KEY_DATE, referralForm.getFormDate());
            values.put(KEY_CLIENT_CODE, referralForm.getClientCode());
            values.put(KEY_CLIENT_ADDRESS, referralForm.getClientAddress());
            values.put(KEY_CLIENT_PHONE, referralForm.getClientPhone());
            values.put(KEY_CLIENT_AGE, referralForm.getAge());
            values.put(KEY_CLIENT_SEX, referralForm.getSex());
            values.put(KEY_REFERRED_TO, referralForm.getRefferedTo());
            values.put(KEY_REFERRED_SERVICE, referralForm.getServiceNeeded());
            values.put(KEY_COMMENT, referralForm.getComment());
            values.put(KEY_CREATE_DATE, new Date().toString());
            //values.put(KEY_SUBMITTED, referralForm.getSubmitted());
            values.put(KEY_UPLOADED, referralForm.getUploaded());

            saved = db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(referralForm.getId())});
            db.close();
            values.clear();
        }
        return saved;
    }

    public ArrayList<ClientForm> getNonePostedSampleForms()  {
        SQLiteDatabase db = OpenDb();
        ArrayList<ClientForm> forms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE (" + KEY_UPLOADED + " = " + FORM_NOT_UPLOADED + " AND " + KEY_FORM_PROGRESS + " = 5) OR ("+ KEY_STOPPED_AT_PRETEST+" = 1 AND "+KEY_UPLOADED+" = "+FORM_NOT_UPLOADED+")", null);
        if (cursor.moveToFirst()) {
            do {
                ClientForm form = new ClientForm();
//                form.setId(cursor.getInt(0));
//                form.setClientName(cursor.getString(1));
//                form.setFormDate(cursor.getString(2));
//                form.setClientCode(cursor.getString(3));
//                form.setClientAddress(cursor.getString(4));
//                form.setClientPhone(cursor.getString(5));
//                form.setAge(cursor.getString(6));
//                form.setSex(cursor.getInt(7));
//                form.setRefferedTo(cursor.getInt(8));
//                form.setServiceNeeded(cursor.getString(9));
//                form.setComment(cursor.getString(10));
//                form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
//                form.setReferred(cursor.getInt(13));
//                form.setApiId(cursor.getString(14));
//                form.setUploaded(cursor.getInt(15));
//                form.setReported(cursor.getInt(16));
//                form.setClientLastname(cursor.getString(17));
//                form.setDob(cursor.getString(18));
//                form.setEstimatedDob(cursor.getString(19));
//                form.setHivResult(cursor.getInt(20));
//                form.setDateOfPrevHivTest(cursor.getString(21));
//                form.setTestingPoint(cursor.getString(22));
//                form.setPreviouslyTested(cursor.getInt(23));
//                form.setDateOfHivTest(cursor.getString(24));
//                form.setRefferedFrom(cursor.getInt(25));
//                form.setDateClientReported(cursor.getString(26));
//                form.setSessionType(cursor.getInt(32));
//                form.setIndexClient(cursor.getInt(33));
//                form.setIndexClientType(cursor.getInt(34));
//                form.setIndexClientId(cursor.getString(35));
//                form.setProgress(cursor.getInt(36));
//                form.setPretest(cursor.getString(37));
//                form.setCurrentHivResult(cursor.getInt(38));
//                form.setDateReferred(cursor.getString(39));
//                form.setTestedBefore(cursor.getInt(40));
//                form.setPostTest(cursor.getString(41));
//                form.setClientHospitalNumber(cursor.getString(43));
//                form.setClientIdentifier(cursor.getString(44));
//                form.setClientState(cursor.getString(45));
//                form.setClientLga(cursor.getString(46));
//                form.setClientVillage(cursor.getString(47));
//                form.setClientGeoCode(cursor.getString(48));
//                form.setMaritalStatus(cursor.getInt(49));
//                form.setEmploymentStaus(cursor.getInt(50));
//                form.setReligion(cursor.getInt(51));
//                form.setEducationLevel(cursor.getInt(52));
//                form.setHivRecencyTestType(cursor.getType(55));
//                form.setHivRecencyTestDate(cursor.getString(56));
//                form.setFinalRecencyTestResult(cursor.getInt(57));
//                form.setTraced(cursor.getInt(63));
//                form.setStoppedAtPreTest(cursor.getInt(62));
//                form.setUser(cursor.getString(66));
//                form.setFacility(cursor.getString(67));
//                form.setRstAgeGroup(cursor.getString(68));
//                form.setRstInformation(cursor.getString(69));
//                form.setRstTestDate(cursor.getString(70));
//                form.setRstTestResult(cursor.getString(71));
//                form.setReferralState(cursor.getString(72));
//                form.setReferralLga(cursor.getString(73));
//                form.setRiskLevel(cursor.getInt(74));
//                form.setReferralTestingArea(cursor.getString(75));


                form.setId(cursor.getInt(0));
                form.setClientName(cursor.getString(1));
                form.setFormDate(cursor.getString(2));
                form.setClientCode(cursor.getString(3));
                form.setClientAddress(cursor.getString(4));
                form.setClientPhone(cursor.getString(5));
                form.setAge(cursor.getString(6));
                form.setSex(cursor.getInt(7));
                form.setRefferedTo(cursor.getInt(8));
                form.setServiceNeeded(cursor.getString(9));
                form.setComment(cursor.getString(10));
                form.setCreateDate(UtilFuns.formatDate(cursor.getString(11)));
                form.setReferred(cursor.getInt(13));
                form.setApiId(cursor.getString(14));
                form.setUploaded(cursor.getInt(15));
                form.setReported(cursor.getInt(16));
                form.setClientLastname(cursor.getString(17));
                form.setDob(cursor.getString(18));
                form.setEstimatedDob(cursor.getString(19));
                form.setHivResult(cursor.getInt(20));
                form.setDateOfPrevHivTest(cursor.getString(21));
                form.setTestingPoint(cursor.getString(22));
                form.setPreviouslyTested(cursor.getInt(23));
                form.setDateOfHivTest(cursor.getString(24));
                form.setRefferedFrom(cursor.getInt(25));
                form.setDateClientReported(cursor.getString(2));
                form.setSessionType(cursor.getInt(27));
                form.setIndexClient(cursor.getInt(28));
                form.setIndexClientType(cursor.getInt(29));
                form.setIndexClientId(cursor.getString(30));
                form.setProgress(cursor.getInt(31));
                form.setPretest(cursor.getString(32));
                form.setCurrentHivResult(cursor.getInt(33));
                form.setDateReferred(cursor.getString(34));
                form.setTestedBefore(cursor.getInt(35));
                form.setPostTest(cursor.getString(36));
                form.setClientIdentifier(cursor.getString(37));
                form.setClientState(cursor.getString(38));
                form.setClientLga(cursor.getString(39));
                form.setClientVillage(cursor.getString(40));
                form.setClientGeoCode(cursor.getString(41));
                form.setMaritalStatus(cursor.getInt(42));
                form.setEmploymentStaus(cursor.getInt(43));
                form.setEducationLevel(cursor.getInt(45));
                form.setHivRecencyTestType(cursor.getType(46));
                form.setHivRecencyTestDate(cursor.getString(47));
                form.setFinalRecencyTestResult(cursor.getInt(48));
                form.setStoppedAtPreTest(cursor.getInt(49));
                form.setTraced(cursor.getInt(50));
                form.setClientConfirmed(cursor.getInt(51));

                form.setUser(cursor.getString(53));
                form.setFacility(cursor.getString(54));
                form.setRstAgeGroup(cursor.getString(55));
                form.setRstInformation(cursor.getString(56));
                form.setRstTestDate(cursor.getString(57));
                form.setRstTestResult(cursor.getString(58));
                form.setReferralState(cursor.getString(59));
                form.setReferralLga(cursor.getString(60));
                form.setRiskLevel(cursor.getInt(61));
                form.setReferralTestingArea(cursor.getString(62));

                forms.add(form);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return forms;
    }

    private boolean referralFormExist(int id) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor =db.query(TABLE_NAME, new String[]{KEY_ID, KEY_CLIENT_NAME},
                KEY_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        db.close();
        return false;
    }

    public boolean isReferralFormAcknowledged(String guid) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor =db.query(TABLE_NAME, new String[]{KEY_REPORTED},
                KEY_API_ID + " =? ", new String[]{guid}, null, null, null);
        if (cursor.moveToFirst()) {
            if (cursor.getInt(0) == 2) {
                cursor.close();
                return true;
            }
            else{
                cursor.close();
                return false;
            }
        }
        db.close();
        return false;
    }

    private boolean referralFormApiIdExist(String guid) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor =db.query(TABLE_NAME, new String[]{KEY_ID},
                KEY_API_ID + "=?", new String[]{guid}, null, null, null);
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

    public int getReferredFacilityCount() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + KEY_REFERRED_TO + " FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getSummaryCount(int ageBracket, int sex) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_CLIENT_AGE + " FROM " + TABLE_NAME + " WHERE " + KEY_CLIENT_SEX + " == " + sex + " AND " + KEY_UPLOADED + " != " + FORM_DOWNLOADED, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int actualCount = 0;
            do {
                int dbAge = cursor.getInt(0); //UtilFuns.calculateAge(cursor.getString(0));

                switch (ageBracket) {
                    case 1:
                        int[] ages1 = {1,2,3,4};
                        for (int a : ages1) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                    case 2:
                        int[] ages2 = {5,6,7,8,9};
                        for (int a : ages2) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                    case 3:
                        int[] ages3 = {10,11,12,13,14};
                        for (int a : ages3) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                    case 4:
                        int[] ages4 = {15,16,17,18,19};
                        for (int a : ages4) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                    case 5:
                        int[] ages5 = {20,21,22,23,24};
                        for (int a : ages5) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                    case 6:
                        int[] ages6 = {25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49};
                        for (int a : ages6) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                    case 7:
                        int[] ages7 = {50};
                        for (int a : ages7) {
                            if (dbAge == a) {
                                actualCount++;
                            }
                        }
                        break;
                }
            }while (cursor.moveToNext());

            cursor.close();
            return actualCount;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberTestedPositive() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE (" + KEY_UPLOADED + " != " + FORM_DOWNLOADED + " AND "+KEY_CURRENT_RESULT+" = 1) OR (" + KEY_REFERRED + " = 1 AND "  + KEY_REFERRED_FROM + " = " + KEY_REFERRED_TO + ")", null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberPreviouslyTestedPositive() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_CLIENT_PREVIOUSLY_TESTED + " = 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberStoppedAtPreTest() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_STOPPED_AT_PRETEST + " = 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberReferred() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED + " AND " + KEY_REFERRED_TO + ">" + 0, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberRefferedIn() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " = " + FORM_DOWNLOADED + " AND " + KEY_REFERRED_FROM + " != " + KEY_REFERRED_TO, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberRetained() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " = " + FORM_DOWNLOADED + " AND " + KEY_REFERRED_FROM + " = " + KEY_REFERRED_TO, null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberTested() {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + "," + KEY_DATE_TESTED + " FROM " + TABLE_NAME + " WHERE (" + KEY_UPLOADED + " != " + FORM_DOWNLOADED + ") AND " + KEY_CURRENT_RESULT + " > " + 0 +" AND (" + KEY_STOPPED_AT_PRETEST + " != " + 1 + " OR (" + KEY_STOPPED_AT_PRETEST + " IS NULL "  + ")  )", null);
        if (cursor != null && cursor.moveToFirst()) {
            db.close();
            int count = cursor.getCount();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberTestedPositiveByMonthRange(int start, int end, int month) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + "," + KEY_DATE_TESTED + " FROM " + TABLE_NAME + " WHERE " + KEY_UPLOADED + " != " + FORM_DOWNLOADED +
                " AND ("+KEY_CURRENT_RESULT+" = 1) OR (" + KEY_REFERRED + " = 1 AND "  + KEY_REFERRED_FROM + " = " + KEY_REFERRED_TO + ")", null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = 0;
            do {
                String date = cursor.getString(1);
                if (date != null){
                    if(month == UtilFuns.getMonthFromDate(date)) {
                        if (UtilFuns.getDayFromDate(date) >= start && UtilFuns.getDayFromDate(date) <= end) {
                            count++;
                        }
                    }
                }

            }while (cursor.moveToNext());
            db.close();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

    public int getNumberTestedByMonthRange(int start, int end, int month) {
        SQLiteDatabase db = OpenDb();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ID + "," + KEY_DATE_TESTED + " FROM " + TABLE_NAME + " WHERE (" + KEY_UPLOADED + " != " + FORM_DOWNLOADED + ") AND " + KEY_CURRENT_RESULT + " > " + 0 +" AND (" + KEY_STOPPED_AT_PRETEST + " != " + 1 + " OR (" + KEY_STOPPED_AT_PRETEST + " IS NULL "  + ")  )", null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = 0;
            do {
                String date = cursor.getString(1);
                if (date != null){
                    if(month == UtilFuns.getMonthFromDate(date)) {

                        if (UtilFuns.getDayFromDate(date) >= start && UtilFuns.getDayFromDate(date) <= end) {
                            count++;
                        }
                    }
                }

            }while (cursor.moveToNext());
            db.close();
            cursor.close();
            return count;
        }
        else {
            db.close();
            return 0;
        }
    }

}
