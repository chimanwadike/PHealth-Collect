package org.webworks.datatool.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webworks.datatool.BuildConfig;
import org.webworks.datatool.Model.ClientForm;
import org.webworks.datatool.R;
import org.webworks.datatool.Repository.ReferralFormRepository;
import org.webworks.datatool.Utility.BindingMeths;
import org.webworks.datatool.Utility.UtilFuns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HtsFormSyncService extends IntentService {

    Context appContext;
    private String PREF_SPOKE_ID;
    private static final String ACTION_SUBMIT_FORM = "SUBMIT_FORM_WHEN_INTERNET";
    public HtsFormSyncService() {
        super("HtsFormSyncService");
        appContext = this;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SUBMIT_FORM.equals(action)) {
                handleActionSubmitForm(appContext);
            }
        }
    }

    private void handleActionSubmitForm(Context context) {
        ReferralFormRepository referralFormRepository = new ReferralFormRepository(context);
        ArrayList<ClientForm> clientReferralForms = referralFormRepository.getNonePostedSampleForms();
        if(clientReferralForms.size() > 0) {
            new PostForms().execute(postForm(clientReferralForms, context));
        }
    }

    private String postForm(ArrayList<ClientForm> forms, Context _context) {
        Context context = _context.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_name), 0);
        String userID = sharedPreferences.getString(context.getResources().getString(R.string.pref_user), "");
        PREF_SPOKE_ID = context.getString(R.string.pref_spoke_id);

        int spokeID = sharedPreferences.getInt(PREF_SPOKE_ID, 0);
        JSONArray array=new JSONArray();

        for (int i = 0; i < forms.size(); i++){
            ClientForm form = forms.get(i);
            JSONObject json = new JSONObject();
            try {
                json.put("user_id", userID);
                json.put("spoke_id", spokeID == 0 ? "" : spokeID);
                json.put("form_id", form.getId());
                json.put("firstname", form.getClientName());
                json.put("surname", form.getClientLastname());
                json.put("form_date", form.getFormDate());
                json.put("estimated", form.getEstimatedDob());
                json.put("date_of_birth", form.getDob());
                json.put("code", form.getClientCode());
                json.put("address", form.getClientAddress());
                json.put("phone_number", form.getClientPhone());
                json.put("age", form.getAge());
                json.put("sex", new BindingMeths(context).getSexType(form.getSex()));
                json.put("reffered_to", form.getRefferedTo());
                json.put("testing_point", form.getTestingPoint());
                json.put("services", form.getServiceNeeded());
                json.put("comment", form.getComment());
                json.put("hiv_test_date", form.getDateOfHivTest());
                json.put("previously_tested", form.getPreviouslyTested());
                json.put("previous_result", new BindingMeths(context).getHivResult(form.getHivResult()));
                json.put("date_of_test", form.getDateOfPrevHivTest());
                json.put("session_type", new BindingMeths(context).getSessionType(form.getSessionType()));
                json.put("is_index_client", form.getIndexClient());
                json.put("index_type", new BindingMeths(context).getClientIndextType(form.getIndexClientType()));
                json.put("index_client_id", form.getIndexClientId());
                json.put("pre_test_counsel", form.getPretest());
                json.put("current_result", new BindingMeths(context).getHivResult(form.getCurrentHivResult()));
                json.put("post_tested_before_within_this_year", new BindingMeths(context).getPreviouslyTestedWithinYear(form.getTestedBefore()));
                json.put("post_test_councel", form.getPostTest());
                json.put("referral_date", form.getDateReferred());
                json.put("client_identifier",form.getClientIdentifier());
                json.put("client_state_code", form.getClientState());
                json.put("client_lga_code", form.getClientLga());
                json.put("client_village",form.getClientVillage());
                json.put("client_geo_code",form.getClientGeoCode());
                json.put("marital_status", new BindingMeths(context).getMaritalStatus(form.getMaritalStatus()));
                json.put("employment_status", new BindingMeths(context).getEmploymentStatus(form.getEmploymentStatus()));
                json.put("religion", form.getReligion());
                json.put("education_level", new BindingMeths(context).getEducationLevel(form.getEducationLevel()) );
                json.put("hiv_recency_test_type", new BindingMeths(context).getRecencyResults(form.getHivRecencyTestType()));
                json.put("hiv_recency_test_date",form.getHivRecencyTestDate());
                json.put("final_recency_test_result",  new BindingMeths(context).getRecencyResults(form.getFinalRecencyTestResult()));
                json.put("traced", form.getTraced());
                json.put("stopped_at_pre_test", form.getStoppedAtPreTest());
                json.put("app_version_number", BuildConfig.VERSION_CODE);
                json.put("facility_id", form.getFacility());
                json.put("risk_age_group",form.getRstAgeGroup());
                json.put("risk_stratification",form.getRstInformation());
                json.put("risk_test_date",form.getRstTestDate());
                json.put("risk_test_result",form.getRstTestResult());
                json.put("referral_state", form.getReferralState());
                json.put("referral_lga", form.getReferralLga());
                json.put("eligibility_level", form.getRiskLevel());
                array.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  array.toString();
    }
    /**
     * Class for synchronizing forms
     * */
    private class PostForms extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... param) {
            // Create data variable for sent values to server
            String data = param[0];
            String text = "";
            BufferedReader reader;
            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL(appContext.getString(R.string.api_url) + appContext.getString(R.string.post_referrals));
                // Send POST data request
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {   // Append server response in string
                    sb.append(line);
                }
                text = sb.toString();
                conn.disconnect();
                return text;
            }
            catch(Exception e) {
                return "Error";
            }
        }

        protected void onPostExecute(String result) {
            if(!result.equals(null) && !result.isEmpty() && !result.equals("Error")) {
                ArrayList<String> ids = UtilFuns.getUploadedId(result);
                for (int i = 0; i < ids.size(); i++) {
                    ReferralFormRepository referralFormRepository = new ReferralFormRepository(appContext);
                    String[] id = ids.get(i).split(":");
                    if (!id[0].equals("")) {
                        referralFormRepository.updateUploadedFromApi(id[1], Integer.parseInt(id[0]));
                    }
                }
            }
            super.onPostExecute(result);
        }
    }
}
