package org.webworks.datatool.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter {

    private static DbManager dbManager;
    private Context context;

    //TODO create individual section in their individual tables
    private static final String CREATE_REFERRAL_TABLE = "CREATE TABLE referral (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, code TEXT, address TEXT, phone TEXT, age TEXT, sex INTEGER, " +
            "referred_to TEXT, referred_service TEXT, comment TEXT, create_date DATE, update_date DATE, referred INTEGER, api_id TEXT, uploaded INTEGER, reported INTEGER, lastname TEXT, dob TEXT, estimated_dob TEXT, " +
            "hiv_result TEXT, date_hiv_result TEXT, testing_point INTEGER, previously_tested INTEGER, date_hiv_tested TEXT, referred_from INTEGER, date_reported TEXT, session_type INTEGER, from_index_client INTEGER, index_testing_type INTEGER, index_client_id TEXT, form_progress INTEGER, pretest TEXT, current_result INTEGER, date_referred TEXT, " +
            "tested_before INTEGER, post_test TEXT, client_identifier TEXT, client_state TEXT, client_lga TEXT, client_village TEXT, client_geo_code TEXT, marital_status INTEGER, " +
            "employment_status INTEGER, religion INTEGER, education_level INTEGER, hiv_recency_test_type INTEGER, hiv_recency_test_date TEXT, final_recency_test_result INTEGER," +
            "stopped_at_pretest INTEGER, traced INTEGER, client_confirmed INTEGER, date_client_confirmed TEXT, user_id TEXT, facility_id TEXT, rst_agegroup,  risk_stratification, " +
            "rst_test_date, rst_test_result, referral_state, referral_lga, risk_level INTEGER, testing_area TEXT)";
    private static final String CREATE_FACILITY_TABLE = "CREATE TABLE facilities (_id INTEGER PRIMARY KEY AUTOINCREMENT, facility_id INTEGER, datim_code TEXT, facility_name TEXT, lga_code TEXT)";
    private static final String CREATE_USER_TABLE = "CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, guid TEXT, email TEXT, facility_guid TEXT, session_expired INTEGER, password TEXT, state TEXT, lga TEXT)";
    private static final String CREATE_REPORT_TABLE = "CREATE TABLE report (_id INTEGER PRIMARY KEY AUTOINCREMENT, month INTEGER, year INTEGER, reports TEXT, uploaded INTEGER, guid TEXT, create_date DATE, update_date DATE)";
    private static final String CREATE_UPDATE_TABLE = "CREATE TABLE appupdate (_id INTEGER PRIMARY KEY AUTOINCREMENT, update_code INTEGER, update_version TEXT, update_date TEXT, last_check DATE)";


    public DbAdapter(Context _context) {
        context = _context.getApplicationContext();
    }

    protected SQLiteDatabase OpenDb() {
        if (dbManager == null) {
            dbManager = new DbManager(context);
        }
        return dbManager.getWritableDatabase();
    }

    public static class DbManager extends SQLiteOpenHelper {
        private static String DATABASE_NAME = "ICF";
        private static int DATABASE_VERSION = 1;
        public DbManager(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_REFERRAL_TABLE);
            db.execSQL(CREATE_FACILITY_TABLE);
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_REPORT_TABLE);
            db.execSQL(CREATE_UPDATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
