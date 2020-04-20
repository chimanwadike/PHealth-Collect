package org.webworks.datatool.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.satsuware.usefulviews.LabelledSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import org.webworks.datatool.Activity.TestingActivity;
import org.webworks.datatool.BuildConfig;
import org.webworks.datatool.Model.ClientForm;
import org.webworks.datatool.Model.Facility;
import org.webworks.datatool.Model.ServicesNeeded;
import org.webworks.datatool.Model.User;
import org.webworks.datatool.R;
import org.webworks.datatool.Repository.FacilityRepository;
import org.webworks.datatool.Repository.ReferralFormRepository;
import org.webworks.datatool.Repository.Repository;
import org.webworks.datatool.Utility.BindingMeths;
import org.webworks.datatool.Utility.UtilFuns;
import org.webworks.datatool.Web.Connectivity;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class ReferralFragment extends Fragment {

    Context context;
    private OnFragmentInteractionListener mListener;
    private EditText referralClientDateReferred, referralClientComment;
    private LabelledSpinner referralClientReferredTo, referralClientServiceNeeded;
    Button saveReferralForm, updateReferralForm;
    private String PREFS_NAME;
    private String PREF_VERSION_CODE_KEY;
    private LabelledSpinner referralFacilityLga, referralFacilityState;
    private String PREF_USER_GUID;
    private String PREF_FACILITY_GUID;
    private String PREF_SPOKE_ID;
    private final String POST_TEST_INFORMATION = "Post_Test";
    ReferralFormRepository referralFormRepository;
    private final String EXTRA_FORM_ID = "FORM_ID";
    private boolean formFilled;
    private ClientForm form;
    ProgressDialog progress;
    public static ArrayList<String> selectedServices;
    private ArrayList<ServicesNeeded> servicesNeededs;
    FacilityRepository facilityRepository;
    private Repository repository;


    public ReferralFragment() {
        // Required empty public constructor
    }

    public static ReferralFragment newInstance() {
        ReferralFragment fragment = new ReferralFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Referral form");
        context = getActivity().getApplicationContext();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            int formId = bundle.getInt(EXTRA_FORM_ID);
            if (formId != 0) {
                formFilled = true;
                form = new ClientForm();
                referralFormRepository = new ReferralFormRepository(context);
                form = referralFormRepository.getReferralFormById(formId);
            }
        }
        facilityRepository = new FacilityRepository(context);
        repository = new Repository(context);
        PREFS_NAME = context.getResources().getString(R.string.pref_name);
        PREF_VERSION_CODE_KEY = context.getResources().getString(R.string.pref_version);
        PREF_USER_GUID = context.getResources().getString(R.string.pref_user);
        PREF_FACILITY_GUID = context.getResources().getString(R.string.pref_facility);
        PREF_SPOKE_ID = getString(R.string.pref_spoke_id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referral, container, false);
        initializeFields(view);
        setListeners();
        if (formFilled) {
            assignValuesToFields(form);
        }
        return view;
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Referral form");
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initializeFields(View view) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String facilityGuid = sharedPreferences.getString(PREF_FACILITY_GUID, "");

        FacilityRepository facilityRepository = new FacilityRepository(context);
//        String facilityCode = facilityRepository.getFacilityCode(facilityGuid);
        //facilityId = facilityRepository.getFacilityIdByGuid(facilityGuid);
        referralClientReferredTo = (LabelledSpinner)view.findViewById(R.id.referral_referred_to);
        referralClientDateReferred = (EditText)view.findViewById(R.id.referral_date);
        referralClientServiceNeeded = (LabelledSpinner)view.findViewById(R.id.referral_service_needed);
        referralClientComment = (EditText)view.findViewById(R.id.referral_comment);
        saveReferralForm = (Button)view.findViewById(R.id.btn_refer_save_form);
        updateReferralForm = (Button) view.findViewById(R.id.btn_refer_update_form);

        referralFacilityState = (LabelledSpinner)view.findViewById((R.id.referral_facility_state));
        referralFacilityLga = (LabelledSpinner)view.findViewById(R.id.referral_facility_lga);
        /*
        * Binding DropDown Data
        **/
        BindingMeths binding = new BindingMeths(context);
        binding.bindStateData(referralFacilityState, referralFacilityLga);
        binding.bindServices(referralClientServiceNeeded);

        String[] services = context.getResources().getStringArray(R.array.services);
        servicesNeededs = new ArrayList<>();
        for (String service : services) {
            ServicesNeeded serviceNeeded = new ServicesNeeded(service);
            servicesNeededs.add(serviceNeeded);
        }
        /*
        * Initial states
        * */
        updateReferralForm.setVisibility(View.GONE);
    }

    private void setListeners() {
        referralClientDateReferred.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    final Calendar calendar = Calendar.getInstance();
                    final int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            int realmonth = month + 1;
                            referralClientDateReferred.setText(day + "/" + realmonth + "/" + year);
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            }
        });

        referralFacilityLga.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stateCode = null;
                String lgaCode = null;
                try {
                    stateCode = repository.bindStateData().get(referralFacilityState.getSpinner().getSelectedItemPosition()).getState_code();
                    if (!stateCode.equals("000")){
                        lgaCode = repository.getLgaCodeByStateAndIndex(stateCode, referralFacilityLga.getSpinner().getSelectedItemPosition());
                        new BindingMeths(context).bindFacilities(lgaCode,referralClientReferredTo);
                    }

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveReferralForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form.setDateReferred(referralClientDateReferred.getText().toString().trim());
               // form.setRefferedTo(referralClientReferredTo.getSpinner().getSelectedItemPosition());
                form.setServiceNeeded(((ServicesNeeded)referralClientServiceNeeded.getSpinner().getSelectedItem()).getSelectedServices());
                form.setComment(referralClientComment.getText().toString());

                String stateCode;
                String lgaCode;
                try {
                    stateCode = repository.bindStateData().get(referralFacilityState.getSpinner().getSelectedItemPosition()).getState_code();
                    lgaCode = repository.getLgaCodeByStateAndIndex(stateCode, referralFacilityLga.getSpinner().getSelectedItemPosition());
                    form.setReferralState(stateCode);
                    form.setReferralLga(lgaCode);
                    Facility facility = (Facility) referralClientReferredTo.getSpinner().getSelectedItem();
                    int spinnerPosition = referralClientReferredTo.getSpinner().getSelectedItemPosition();
                    form.setRefferedTo(facility.getFacilityId());
  //                  form.setRefferedTo(facilityRepository.getFacilityGuid(spinnerPosition, lgaCode));
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    submitReferralForm(form);
            }
        });
        updateReferralForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form.setDateReferred(referralClientDateReferred.getText().toString().trim());
                form.setServiceNeeded(((ServicesNeeded)referralClientServiceNeeded.getSpinner().getSelectedItem()).getSelectedInnerServices());
                form.setComment(referralClientComment.getText().toString());

                String stateCode;
                String lgaCode;
                try {
                    stateCode = repository.bindStateData().get(referralFacilityState.getSpinner().getSelectedItemPosition()).getState_code();
                    lgaCode = repository.getLgaCodeByStateAndIndex(stateCode, referralFacilityLga.getSpinner().getSelectedItemPosition());
                    if (!stateCode.equals("000") && !lgaCode.equals("")){
                        form.setReferralState(stateCode);
                        form.setReferralLga(lgaCode);

                        int spinnerPosition = referralClientReferredTo.getSpinner().getSelectedItemPosition();
    //                    form.setRefferedTo(facilityRepository.getFacilityGuid(spinnerPosition, lgaCode));
                    }

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    updateReferredForm(form);
            }
        });
    }

    private void submitReferralForm(ClientForm ClientForm) {
        if (validate()) {
            referralFormRepository = new ReferralFormRepository(context);
            long saved;

            ClientForm.setProgress(5);

            if (Connectivity.isConnected(context)) {
                saved = referralFormRepository.updateReferralRefer(ClientForm);
                if (saved == -1) {
                    Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
                } else {
                    ClientForm.setId(ClientForm.getId());
                    new PostForm().execute(postForm(ClientForm));
                }
                clearForm();
            }
            else {
                long saves = referralFormRepository.updateReferralRefer(ClientForm);
                if (saves == -1) {
                    Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, R.string.save_success, Toast.LENGTH_LONG).show();
                }
                clearForm();
            }
            Intent intent = new Intent(context, TestingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void updateReferredForm(ClientForm ClientForm) {
        if (validate()) {
            referralFormRepository = new ReferralFormRepository(context);
            ClientForm.setProgress(5);
            long saved;

            if (Connectivity.isConnected(context)) {
                saved = referralFormRepository.updateReferralRefer(ClientForm);
                if (saved == -1) {
                    Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
                } else {
                    ClientForm.setId(ClientForm.getId());
                    new PostForm().execute(postForm(ClientForm));
                }
            }
            else {
                long saves = referralFormRepository.updateReferralRefer(ClientForm);
                if (saves == -1) {
                    Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, R.string.save_success, Toast.LENGTH_LONG).show();
                }
            }
            clearForm();
            Intent intent = new Intent(context, TestingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void assignValuesToFields(final ClientForm referralForm) {
        referralClientDateReferred.setText(referralForm.getDateReferred());
        referralClientComment.setText(referralForm.getComment());

        try{
            referralFacilityState.getSpinner().setSelection(repository.getIndexOfSelectedState(referralForm.getReferralState()));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        referralFacilityState.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stateCode = null;
                try {
                    stateCode = repository.bindStateData().get(i).getState_code();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new BindingMeths(context).getSelectedStateLgaSelected(stateCode, referralFacilityLga, referralForm.getReferralLga());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (referralForm.getReferralLga() != null){
            referralFacilityLga.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String stateCode;
                    String lgaCode = null;
                    try {
                        stateCode = repository.bindStateData().get(referralFacilityState.getSpinner().getSelectedItemPosition()).getState_code();
                        lgaCode = repository.getLgaCodeByStateAndIndex(stateCode, referralFacilityLga.getSpinner().getSelectedItemPosition());

                        if (lgaCode != null){
                            if (lgaCode.equals(referralForm.getReferralLga())){
                                new BindingMeths(context).getSelectedFacilitySelected(referralForm.getReferralLga(), referralClientReferredTo, referralForm.getRefferedTo());
                            }else{
                                new BindingMeths(context).bindFacilities(lgaCode,referralClientReferredTo);
                            }
                        }
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        if(!(form.getServiceNeeded()== null) && !form.getServiceNeeded().equals("null")&& !form.getServiceNeeded().equals("")) {
            referralClientServiceNeeded.getSpinner().setAdapter(new ServiceAdapter(context,
                    servicesNeededs, UtilFuns.getSelectServices(UtilFuns.prepareSelectServices(form.getServiceNeeded()))));
            selectedServices = UtilFuns.prepareSelectServices(form.getServiceNeeded());
        }

        if (referralForm.getProgress() >= 5 || referralForm.getRefferedTo() != 0) {
            saveReferralForm.setVisibility(View.GONE);
            updateReferralForm.setVisibility(View.VISIBLE);
            if (referralForm.getClientConfirmed() > 0) {
                updateReferralForm.setVisibility(View.GONE);
            }
        }
    }

    private boolean validate() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //String facilityGuid = sharedPreferences.getString(PREF_FACILITY_GUID, "");
        referralFormRepository = new ReferralFormRepository(context);
       // FacilityRepository facilityRepository = new FacilityRepository(context);
       // int facId = facilityRepository.getFacilityIdByGuid(facilityGuid);

        if (referralClientDateReferred.getText().toString().equals("") || referralClientDateReferred.getText().toString().isEmpty()) {
            Toast.makeText(context, getString(R.string.drop_down_validate, "Client referred date"), Toast.LENGTH_SHORT).show();
            return false;
        }else  if (UtilFuns.convertStringToDate(referralClientDateReferred.getText().toString().trim()).before(UtilFuns.convertStringToDate(form.getDateOfHivTest().trim()))){
            Toast.makeText(context,"Referral date cannot be before HIV test date", Toast.LENGTH_LONG).show();
            return false;
        }

        if (referralFacilityState.getSpinner().getSelectedItemPosition() == 0){
            Toast.makeText(context, getString(R.string.drop_down_validate, "facility state"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (referralFacilityLga.getSpinner().getSelectedItemPosition() == 0){
            Toast.makeText(context, getString(R.string.drop_down_validate, "facility LGA"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (referralClientReferredTo.getSpinner().getSelectedItemPosition() == 0){
            Toast.makeText(context, getString(R.string.drop_down_validate, "facility to refer client"), Toast.LENGTH_SHORT).show();
            return false;
        }

        try{
            if ((((ServicesNeeded)referralClientServiceNeeded.getSpinner().getSelectedItem()).getSelectedServices()).isEmpty() && (((ServicesNeeded)referralClientServiceNeeded.getSpinner().getSelectedItem()).getSelectedInnerServices()).isEmpty() ){
                Toast.makeText(context, getString(R.string.drop_down_validate, "Services for client"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, "Error occurred with services required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearForm() {
        referralClientReferredTo.getSpinner().setSelection(0);
        referralClientDateReferred.setText("");
        referralClientDateReferred.setFocusable(false);
        referralClientServiceNeeded.getSpinner().setSelection(0);
        referralClientComment.setText("");
    }

    public interface OnFragmentInteractionListener {

        void onSkipButtonClicked(String fragmentTag, Bundle bundle);
    }

    /**
     * inner Classes
     * **/
    private class ServiceAdapter extends BaseAdapter {
        Context context;
        private ArrayList<ServicesNeeded> services = new ArrayList<>();
        private ArrayList<ServicesNeeded> s = new ArrayList<>();

        public ServiceAdapter(Context _context, ArrayList<ServicesNeeded> _services, ArrayList<ServicesNeeded> selectedService) {
            context = _context;
            this.services = _services;
            this.s = selectedService;
        }

        @Override
        public int getCount() {
            return services.size();
        }

        @Override
        public ServicesNeeded getItem(int i) {
            return services.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ServicesNeeded servicesNeeded = getItem(i);
            for (int j = 0; j < s.size(); j++) {
                if (servicesNeeded.getService().equals(s.get(j).getService())) {
                    servicesNeeded.setChecked(true);
                }
            }
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.checkbox_spinner_adapter, null);
            final CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);
            final TextView textView = (TextView)view.findViewById(R.id.select);
            final EditText othersSpecify = (EditText)view.findViewById(R.id.enter_others);
            othersSpecify.setVisibility(View.GONE);
            textView.setPadding(5, 5, 0, 5);
            textView.setTextSize(16);

            if(i == 0) {
                checkBox.setVisibility(View.INVISIBLE);
                checkBox.setChecked(false);
                textView.setText(servicesNeeded.getService());
                textView.setTextColor(Color.BLACK);
            }
            else {
                textView.setVisibility(View.INVISIBLE);
                checkBox.setText(servicesNeeded.getService());
                checkBox.setId(i);
                checkBox.setTextColor(Color.BLACK);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()) {
                            if(compoundButton.getText().toString().equalsIgnoreCase("Others(Specify)")) {
                                othersSpecify.setVisibility(View.VISIBLE);
                                compoundButton.setVisibility(View.INVISIBLE);
                                //Todo: get text with text change listener
                                selectedServices.add(othersSpecify.getText().toString().trim());
                            }
                            if (selectedServices.contains(servicesNeeded.getService())) {
                            }
                            else {
                                selectedServices.add(servicesNeeded.getService());
                            }
                        }
                        else {
                            selectedServices.remove(servicesNeeded.getService());
                        }
                        servicesNeeded.setChecked(b);
                        servicesNeeded.setId(checkBox.getId());
                    }
                });
                if (servicesNeeded.isChecked()) {
                    checkBox.setChecked(true);
                }
            }
            return view;
        }
    }

    /**
     * Form posting to api
     **/
    private String postForm(ClientForm form) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString(PREF_USER_GUID, "");
        int spokeID = sharedPreferences.getInt(PREF_SPOKE_ID, 0);
        JSONObject json = new JSONObject();
        try {
            json.put("user_id", userID);
            json.put("spoke_id", spokeID == 0 ? "" : spokeID );
            json.put("form_id", form.getId());
            json.put("firstname", form.getClientName());
            json.put("surname", form.getClientLastname());
            json.put("form_date", form.getFormDate());
            json.put("estimated", form.getEstimatedDob());
            json.put("date_of_birth", form.getDob());
            json.put("code", form.getClientCode());
            json.put("address", form.getClientAddress());
            json.put("address_2", form.getClientAddress2());
            json.put("address_3", form.getClientAddress3());
            json.put("care_giver_name", form.getCareGiverName());
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  json.toString();
    }

    /**
     * * Posts a single form to the server
     * */
    private class PostForm extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setMessage("processing request...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);

            if (!(getActivity()).isFinishing()) {
                try {
                    progress.show();
                } catch (WindowManager.BadTokenException e) {
                    Log.e("WindowManagerBad ", e.toString());
                }
            }

        }

        @Override
        protected String doInBackground(String... params) {
            // Create data variable for sent values to server
            String data = params[0];
            String text;
            BufferedReader reader=null;
            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL(context.getResources().getString(R.string.api_url) + context.getResources().getString(R.string.post_referral));
                // Send POST data request
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setRequestProperty("Authorization", "Bearer " + UtilFuns.getPrefAuthToken(context));

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
//                int responseCode = conn.getResponseCode();
//                if (responseCode == Constants.UnauthorizedCode){
//                    return "Unauthorized";
//                }
                // Get the server response
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                text = sb.toString();
                conn.disconnect();
                return text;
            }
            catch(Exception ex) {
                return "Error";
            }
        }

        protected void onPostExecute(String result) {

            if(!(result.equals(null) || result.isEmpty()) && !result.equals("Error")) {
                String guid = UtilFuns.getOneUploadedId(result);
                if (!guid.equals("")) {
                    ReferralFormRepository referralFormRepository = new ReferralFormRepository(context);
                    referralFormRepository.updateUploadedFromApi(guid, form.getId());
                    Toast.makeText(context, R.string.save_success, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, context.getString(R.string.submission_failed), Toast.LENGTH_SHORT).show();
                }
                if (progress.isShowing())
                        progress.dismiss();
            }
            else {
                Toast.makeText(context, context.getString(R.string.submission_failed), Toast.LENGTH_LONG).show();
                if (progress.isShowing())
                        progress.dismiss();
            }
            super.onPostExecute(result);
        }
    }
}
