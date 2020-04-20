package org.webworks.datatool.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.satsuware.usefulviews.LabelledSpinner;

import org.webworks.datatool.Model.ClientForm;
import org.webworks.datatool.R;
import org.webworks.datatool.Repository.FacilityRepository;
import org.webworks.datatool.Repository.ReferralFormRepository;
import org.webworks.datatool.Repository.Repository;
import org.webworks.datatool.Utility.BindingMeths;
import org.webworks.datatool.Utility.GroupButton;
import org.webworks.datatool.Utility.UtilFuns;

import java.util.Calendar;

public class SocialDemoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context context;
    private EditText referralClientName, referralClientIdentifier, referralClientAddress, referralClientPhone, referralClientAge, referralClientDob, referralHivTestingDate, referralHivRecencyTestDate;
    private EditText referralClientLastname, hivTestingDate, referralIndexClientId, referralClientVillage;
    private LabelledSpinner referralHivResult, referralSessionType, referralIndexTestingType,referralClientMaritalStatus;
    private Button saveDemographForm, continueDemographForm, updateDemographForm;
    private RadioButton testedYes, testedNo, indexTestingYes, indexTestingNo;
    private LinearLayout prevTested, indexTest, referralRecencyLayout;
    private String PREFS_NAME;
    private String PREF_VERSION_CODE_KEY;
    private String PREF_USER_GUID;
    private String PREF_FACILITY_GUID;
    private String PREF_LAST_CODE;
    private boolean fromDob = false, estimatedDob;
    private String clientAge, facilityName;
    private int facilityId, clientFormId;
    private ReferralFormRepository referralFormRepository;
    private final String PRE_TEST_INFORMATION = "Pre_Test";
    private final String TEST_RESULTS = "Test_Results";
    private final String EXTRA_FORM_ID = "FORM_ID";
    private boolean formFilled;
    private ClientForm form;
    private LabelledSpinner referralEmploymentStatus, referralEducationLevel,referralFinalRecencyTest,
            referralClientRecencyTestingType;
    private Repository repository;
    private EditText  referralClientAddress2, referralClientAddress3, careGiverName;
    private TextInputLayout care_giver_layout;



    public SocialDemoFragment() {
        // Required empty public constructor
    }

    public static SocialDemoFragment newInstance() {
        SocialDemoFragment fragment = new SocialDemoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Client intake form");
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


        PREFS_NAME = context.getResources().getString(R.string.pref_name);
        PREF_VERSION_CODE_KEY = context.getResources().getString(R.string.pref_version);
        PREF_USER_GUID = context.getResources().getString(R.string.pref_user);
        PREF_FACILITY_GUID = context.getResources().getString(R.string.pref_facility);
        PREF_LAST_CODE = context.getResources().getString(R.string.pref_code);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social_demo, container, false);
        initializeFields(view);
        setListeners();
        if (formFilled) {
            assignValuesToFields(form);
        }
        return view;
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
    public void onResume() {
        getActivity().setTitle("Client intake form");
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initializeFields(View view) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String facilityGuid = "";//sharedPreferences.getString(PREF_FACILITY_GUID, "");

        FacilityRepository facilityRepository = new FacilityRepository(context);
        //facilityId = facilityRepository.getFacilityIdByGuid(facilityGuid);
        hivTestingDate = (EditText)view.findViewById(R.id.hiv_testing_date);
        referralClientName = (EditText)view.findViewById(R.id.referral_client_name);
        referralClientLastname = (EditText)view.findViewById(R.id.referral_client_lastname);
        referralClientIdentifier = (EditText)view.findViewById(R.id.referral_client_identifier);
        referralClientAddress = (EditText)view.findViewById(R.id.referral_client_address);
        referralClientAddress2 = (EditText)view.findViewById(R.id.referral_client_address_2);
        referralClientAddress3 = (EditText)view.findViewById(R.id.referral_client_address_3);
        careGiverName = view.findViewById(R.id.referral_care_giver);
        referralClientPhone = (EditText)view.findViewById(R.id.referral_client_phone);
        referralClientAge = (EditText)view.findViewById(R.id.referral_client_age);
        referralClientAge.setEnabled(false);
        referralClientDob = (EditText)view.findViewById(R.id.referral_client_dob);
        referralHivTestingDate = (EditText)view.findViewById(R.id.referral_prev_hiv_testing_date);
        testedYes = (RadioButton)view.findViewById(R.id.tested_rdbtn_yes);
        testedNo = (RadioButton)view.findViewById(R.id.tested_rdbtn_no);
        referralHivResult = (LabelledSpinner)view.findViewById(R.id.referral_hiv_result);
        prevTested = (LinearLayout)view.findViewById(R.id.previously_tested);
        referralRecencyLayout = (LinearLayout)view.findViewById(R.id.referral_recency_layout);
        referralSessionType = (LabelledSpinner)view.findViewById(R.id.referral_type_of_session);
        indexTestingYes = (RadioButton)view.findViewById(R.id.index_rdbtn_yes);
        indexTestingNo = (RadioButton)view.findViewById(R.id.index_rdbtn_no);
        referralIndexTestingType = (LabelledSpinner)view.findViewById(R.id.referral_index_testing_type);
        referralIndexClientId = (EditText)view.findViewById(R.id.referral_index_client_id);
        indexTest = (LinearLayout)view.findViewById(R.id.index_testing);
        saveDemographForm = (Button)view.findViewById(R.id.btn_demo_save_form);
        continueDemographForm = (Button)view.findViewById(R.id.btn_demo_continue_form);
        updateDemographForm = (Button)view.findViewById(R.id.btn_demo_update_form);
        referralClientMaritalStatus = (LabelledSpinner)view.findViewById(R.id.referral_marital_status);
        referralEmploymentStatus = (LabelledSpinner)view.findViewById(R.id.referral_employment_status);
        referralEducationLevel = (LabelledSpinner)view.findViewById(R.id.referral_education_level);
        referralFinalRecencyTest = (LabelledSpinner)view.findViewById(R.id.referral_final_recency_result);
        referralHivRecencyTestDate = (EditText)view.findViewById(R.id.referral_hiv_recency_test_date);
        referralClientVillage = (EditText)view.findViewById(R.id.referral_client_village);
        referralClientRecencyTestingType = (LabelledSpinner)view.findViewById(R.id.referral_hiv_recency_test_type);
        care_giver_layout = view.findViewById(R.id.care_giver_layout);
        /*
        * Binding DropDown Data
        **/
        BindingMeths binding = new BindingMeths(context);
        binding.bindMaritalStatus(referralClientMaritalStatus);
        binding.bindHivResult(referralHivResult);
        binding.bindSessionType(referralSessionType);
        binding.bindIndexTestingType(referralIndexTestingType);
        binding.bindEmploymentStatus(referralEmploymentStatus);
        binding.bindEducationLevel(referralEducationLevel);
        binding.bindRecencyResult(referralFinalRecencyTest);
        binding.bindRecencyResult(referralClientRecencyTestingType);

        prevTested.setVisibility(View.GONE);
        indexTest.setVisibility(View.GONE);
        referralRecencyLayout.setVisibility(View.GONE);
        continueDemographForm.setVisibility(View.GONE);
        updateDemographForm.setVisibility(View.GONE);
    }

    private void setListeners() {

        hivTestingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        int realmonth = month + 1;
                        hivTestingDate.setText(day + "/" + realmonth + "/" + year);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        referralClientDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    final Calendar calendar = Calendar.getInstance();
                    final int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    estimatedDob = true;
                    DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            int age = yy - year;
                            int realmonth = month + 1;
                            clientAge = day + "/" + realmonth + "/" + year;
                            fromDob = true;
                            referralClientAge.setText(String.valueOf(age));
                            referralClientDob.setText(clientAge);
                            referralClientAge.setEnabled(false);
                            //estimatedDob = false;
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            }
        });

        referralHivTestingDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                            referralHivTestingDate.setText(day + "/" + realmonth + "/" + year);
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            }
        });

        referralHivRecencyTestDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                            referralHivRecencyTestDate.setText(day + "/" + realmonth + "/" + year);
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            }
        });

        testedYes.setOnCheckedChangeListener(new GroupButton(testedNo, prevTested, true));
        testedNo.setOnCheckedChangeListener(new GroupButton(testedYes, prevTested, false, new EditText[]{referralHivTestingDate},
                new LabelledSpinner[] {referralHivResult}));

        indexTestingYes.setOnCheckedChangeListener(new GroupButton(indexTestingNo, indexTest, true));
        indexTestingNo.setOnCheckedChangeListener(new GroupButton(indexTestingYes, indexTest, false, new EditText[]{referralIndexClientId},
                new LabelledSpinner[]{referralIndexTestingType}));

        saveDemographForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSocialDemoForm();
            }
        });

        continueDemographForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validate()){
                   if (mListener != null) {
                       Bundle bundle = new Bundle();
                       bundle.putInt(EXTRA_FORM_ID, form.getId());
                       TestResultFragment testResultFragment = TestResultFragment.newInstance();
                       mListener.onContinueButtonClicked(TEST_RESULTS, testResultFragment, bundle);
                   }
               }
            }
        });
        updateDemographForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSocialDemoForm(form);
            }
        });

        referralHivResult.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (referralHivResult.getSpinner().getSelectedItemPosition() == 1){
                    referralRecencyLayout.setVisibility(View.VISIBLE);
                }else{
                    referralRecencyLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void assignValuesToFields(final ClientForm referralForm) {
        repository = new Repository(context);
        //Disable();
        hivTestingDate.setText(referralForm.getDateOfHivTest());
        referralClientName.setText(referralForm.getClientName());
        referralClientLastname.setText(referralForm.getClientLastname());
        referralClientAddress.setText(referralForm.getClientAddress());
        referralClientAddress2.setText(referralForm.getClientAddress2());
        referralClientAddress3.setText(referralForm.getClientAddress3());
        careGiverName.setText(referralForm.getCareGiverName());
        referralClientPhone.setText(referralForm.getClientPhone());
        if (referralForm.getDob() == null || referralForm.getDob().equals("")) {
           // referralClientAge.setText("");
            referralClientDob.setText(referralForm.getEstimatedDob());
        }
        else {
            referralClientDob.setText(referralForm.getDob());
        }

        if (form.getAge() != null) {
            referralClientAge.setText(form.getAge());
        }
        else if (!(referralForm.getDob() == null) && !referralForm.getDob().equals("")) {
            form.setAge(String.valueOf(UtilFuns.calculateAge(form.getDob())));
            referralClientAge.setText(String.valueOf(form.getAge()));
        }

        referralHivTestingDate.setText(referralForm.getDateOfPrevHivTest());
        referralHivResult.getSpinner().setSelection(referralForm.getHivResult());
        if (!referralForm.getRstAgeGroup().equals("Adult")){
            referralClientMaritalStatus.setVisibility(View.GONE);
            referralEmploymentStatus.setVisibility(View.GONE);
            //careGiverName.setVisibility(View.VISIBLE);
            care_giver_layout.setVisibility(View.VISIBLE);
        }else{
            referralClientMaritalStatus.getSpinner().setSelection(referralForm.getMaritalStatus());
            referralEmploymentStatus.getSpinner().setSelection(referralForm.getEmploymentStatus());
            //careGiverName.setVisibility(View.GONE);
            care_giver_layout.setVisibility(View.GONE);
        }



        referralEducationLevel.getSpinner().setSelection(referralForm.getEducationLevel());
        referralFinalRecencyTest.getSpinner().setSelection(referralForm.getFinalRecencyTestResult());
        referralClientVillage.setText(referralForm.getClientVillage());



        referralClientRecencyTestingType.getSpinner().setSelection(referralForm.getHivRecencyTestType());
        referralHivRecencyTestDate.setText(referralForm.getHivRecencyTestDate());
        referralClientIdentifier.setEnabled(false);
        referralClientIdentifier.setText(referralForm.getClientIdentifier());

        if (referralForm.getPreviouslyTested() == 1) {
            testedYes.setChecked(true);
            prevTested.setVisibility(View.VISIBLE);
        }
        else {
            testedNo.setChecked(true);
            prevTested.setVisibility(View.GONE);
        }
        referralSessionType.getSpinner().setSelection(referralForm.getSessionType());
        referralIndexTestingType.getSpinner().setSelection(referralForm.getIndexClientType());
        referralIndexClientId.setText(referralForm.getIndexClientId());
        if (referralForm.getTraced() == 1){
            referralIndexClientId.setEnabled(false);
        }

        if (referralForm.getIndexClient() == 1) {
            indexTestingYes.setChecked(true);
            indexTest.setVisibility(View.VISIBLE);
        }
        else {
            indexTestingNo.setChecked(true);
            indexTest.setVisibility(View.GONE);
        }

        saveDemographForm.setVisibility(View.GONE);
        if (referralForm.getClientName() != null){
            continueDemographForm.setVisibility(View.VISIBLE);
        }else{
            continueDemographForm.setVisibility(View.GONE);
        }

        updateDemographForm.setVisibility(View.VISIBLE);
        if (referralForm.getClientConfirmed() > 0) {
            updateDemographForm.setVisibility(View.GONE);
        }
    }

    private void submitSocialDemoForm() {
        if (validate()) {
            referralFormRepository = new ReferralFormRepository(context);
            //long saved;

            final ClientForm ClientForm = new ClientForm();
            ClientForm.setDateOfHivTest(hivTestingDate.getText().toString().trim());
            ClientForm.setClientName(referralClientName.getText().toString().trim());
            ClientForm.setClientLastname(referralClientLastname.getText().toString().trim());
            ClientForm.setClientIdentifier(referralClientIdentifier.getText().toString().trim());
            ClientForm.setClientAddress(referralClientAddress.getText().toString().trim());
            ClientForm.setClientAddress2(referralClientAddress2.getText().toString().trim());
            ClientForm.setClientAddress3(referralClientAddress3.getText().toString().trim());
            ClientForm.setCareGiverName(careGiverName.getText().toString().trim());
            ClientForm.setClientPhone(referralClientPhone.getText().toString().trim());
            if (estimatedDob) {
                ClientForm.setEstimatedDob(referralClientDob.getText().toString().trim());
                ClientForm.setDob("");
            }
            else {
                ClientForm.setDob(referralClientDob.getText().toString().trim());
                ClientForm.setEstimatedDob("");
            }
            ClientForm.setAge(referralClientAge.getText().toString());

            ClientForm.setMaritalStatus(referralClientMaritalStatus.getSpinner().getSelectedItemPosition());

            ClientForm.setEmploymentStaus(referralEmploymentStatus.getSpinner().getSelectedItemPosition());
            ClientForm.setEducationLevel(referralEducationLevel.getSpinner().getSelectedItemPosition());
            ClientForm.setFinalRecencyTestResult(referralFinalRecencyTest.getSpinner().getSelectedItemPosition());

            ClientForm.setClientVillage(referralClientVillage.getText().toString().trim());
            ClientForm.setHivRecencyTestType(referralClientRecencyTestingType.getSpinner().getSelectedItemPosition());
            ClientForm.setHivRecencyTestDate(referralHivRecencyTestDate.getText().toString().trim());

            ClientForm.setHivResult(referralHivResult.getSpinner().getSelectedItemPosition());
            ClientForm.setDateOfPrevHivTest(referralHivTestingDate.getText().toString().trim());
            if (referralHivResult.getSpinner().getSelectedItemPosition() != 0) ClientForm.setPreviouslyTested(1);
            else ClientForm.setPreviouslyTested(0);
            ClientForm.setSessionType(referralSessionType.getSpinner().getSelectedItemPosition());
            ClientForm.setIndexClientType(referralIndexTestingType.getSpinner().getSelectedItemPosition());
            ClientForm.setIndexClientId(referralIndexClientId.getText().toString().trim());
            if (referralIndexTestingType.getSpinner().getSelectedItemPosition() != 0) ClientForm.setIndexClient(1);
            else ClientForm.setIndexClient(0);
           // ClientForm.setIndexDomClientType(referralDomIndexTestingType.getSpinner().getSelectedItem().toString().trim());
            ClientForm.setProgress(2);
            ClientForm.setUploaded(0);
            ClientForm.setReferred(0);

            /*if (Connectivity.isConnected(context)) {
                saved = referralFormRepository.saveReferralForm(ClientForm);
                if (saved == -1) {
                    Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
                } else {
                    clientFormId = (int) saved;
                    ClientForm.setId(clientFormId);
                    new PostForm().execute(postForm(ClientForm));
                    Toast.makeText(context, R.string.save_success, Toast.LENGTH_LONG).show();
                }
                clearForm();
            }
            else {*/
                long saves = referralFormRepository.saveReferralForm(ClientForm);
                if (saves == -1) {
                    Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, R.string.save_success, Toast.LENGTH_LONG).show();
                    clearForm();
                    Bundle bundle = new Bundle();
                    bundle.putInt(EXTRA_FORM_ID, (int)saves);
                    PretestFragment fragment = PretestFragment.newInstance();
                    mListener.onContinueButtonClicked(PRE_TEST_INFORMATION, fragment, bundle);
                }
            //}
        }
    }

    private void updateSocialDemoForm(ClientForm ClientForm) {
        if (validate()) {
            referralFormRepository = new ReferralFormRepository(context);
            ClientForm.setDateOfHivTest(hivTestingDate.getText().toString().trim());
            ClientForm.setClientName(referralClientName.getText().toString().trim());
            ClientForm.setClientLastname(referralClientLastname.getText().toString().trim());
            ClientForm.setClientIdentifier(referralClientIdentifier.getText().toString().trim());
            ClientForm.setClientAddress(referralClientAddress.getText().toString().trim());
            ClientForm.setClientAddress2(referralClientAddress2.getText().toString().trim());
            ClientForm.setClientAddress3(referralClientAddress3.getText().toString().trim());
            ClientForm.setCareGiverName(careGiverName.getText().toString().trim());

            ClientForm.setClientPhone(referralClientPhone.getText().toString().trim());
            if (estimatedDob) {
                ClientForm.setEstimatedDob(referralClientDob.getText().toString().trim());
                ClientForm.setDob("");
            }
            else {
                ClientForm.setDob(referralClientDob.getText().toString().trim());
                ClientForm.setEstimatedDob("");
            }
            ClientForm.setAge(referralClientAge.getText().toString().trim());
            ClientForm.setMaritalStatus(referralClientMaritalStatus.getSpinner().getSelectedItemPosition());

            ClientForm.setEmploymentStaus(referralEmploymentStatus.getSpinner().getSelectedItemPosition());
            ClientForm.setEducationLevel(referralEducationLevel.getSpinner().getSelectedItemPosition());
            ClientForm.setFinalRecencyTestResult(referralFinalRecencyTest.getSpinner().getSelectedItemPosition());

            ClientForm.setClientVillage(referralClientVillage.getText().toString().trim());
            ClientForm.setHivRecencyTestType(referralClientRecencyTestingType.getSpinner().getSelectedItemPosition());
            ClientForm.setHivRecencyTestDate(referralHivRecencyTestDate.getText().toString().trim());

            ClientForm.setProgress(2);

            ClientForm.setHivResult(referralHivResult.getSpinner().getSelectedItemPosition());
            ClientForm.setDateOfPrevHivTest(referralHivTestingDate.getText().toString().trim());
            if (referralHivResult.getSpinner().getSelectedItemPosition() != 0) ClientForm.setPreviouslyTested(1);
            else ClientForm.setPreviouslyTested(0);
            ClientForm.setSessionType(referralSessionType.getSpinner().getSelectedItemPosition());
            ClientForm.setIndexClientType(referralIndexTestingType.getSpinner().getSelectedItemPosition());
            ClientForm.setIndexClientId(referralIndexClientId.getText().toString().trim());
            if (referralIndexTestingType.getSpinner().getSelectedItemPosition() != 0) ClientForm.setIndexClient(1);
            else ClientForm.setIndexClient(0);
            //ClientForm.setIndexDomClientType(referralDomIndexTestingType.getSpinner().getSelectedItem().toString().trim());

            long saves = referralFormRepository.updateReferralSocialDemo(ClientForm);
            if (saves == -1) {
                Toast.makeText(context, R.string.save_error, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, R.string.save_success, Toast.LENGTH_LONG).show();
                clearForm();
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_FORM_ID, ClientForm.getId());
                TestResultFragment fragment = TestResultFragment.newInstance();
                mListener.onContinueButtonClicked(TEST_RESULTS, fragment, bundle);
            }
        }
    }

    private boolean validate() {
        if(hivTestingDate.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Hiv Test Date"), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (referralClientName.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Client firstname"), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(referralClientLastname.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Client lastname"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (referralClientDob.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Client date of birth"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (UtilFuns.convertStringToDate(referralClientDob.getText().toString().trim()).after(UtilFuns.convertStringToDate(UtilFuns.getTodaysDate()))) {
            Toast.makeText(context,"Date of birth cannot be in the future", Toast.LENGTH_LONG).show();
            return false;
        }

        if(referralClientAge.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Age"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (referralClientAge.getText().toString().equals(String.valueOf(0))){
            Toast.makeText(context, "Age cannot be 0", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (UtilFuns.convertStringToDate(hivTestingDate.getText().toString().trim()).after(UtilFuns.convertStringToDate(UtilFuns.getTodaysDate()))){
            Toast.makeText(context,"Test date cannot be in the future", Toast.LENGTH_LONG).show();
            return false;
        }

        if (form.getRstAgeGroup().equals("Adult")){
            if (Integer.parseInt(referralClientAge.getText().toString()) < 15){
                Toast.makeText(context,"Adult client can't be less than 15 years of age", Toast.LENGTH_LONG).show();
                return false;
            }

            if (referralClientMaritalStatus.getSpinner().getSelectedItemPosition() == 0){
                Toast.makeText(context, getString(R.string.drop_down_validate, "Marital Status"), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (referralEmploymentStatus.getSpinner().getSelectedItemPosition() == 0){
                Toast.makeText(context, getString(R.string.drop_down_validate, "Employment Status"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (form.getRstAgeGroup().equals("Pediatrics")){
            if (Integer.parseInt(referralClientAge.getText().toString()) > 14){
                Toast.makeText(context,"Pediatrics client can't be over 14 years of age", Toast.LENGTH_LONG).show();
                return false;
            }

            if(careGiverName.getText().toString().equals("")) {
                Toast.makeText(context, getString(R.string.validate_error, "Pediatrics Care Giver"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (referralEducationLevel.getSpinner().getSelectedItemPosition() == 0){
            Toast.makeText(context, getString(R.string.drop_down_validate, "Highest Education Level"), Toast.LENGTH_SHORT).show();
            return false;
        }


        if (referralClientRecencyTestingType.getSpinner().getSelectedItemPosition() != 0){
            if (referralFinalRecencyTest.getSpinner().getSelectedItemPosition() == 0){
                Toast.makeText(context, getString(R.string.drop_down_validate, "Recency Test Result"), Toast.LENGTH_SHORT).show();
                return false;
            }else if(referralHivRecencyTestDate.getText().toString().equals("")) {
                Toast.makeText(context, getString(R.string.validate_error, "Recency Test Date"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(referralClientAddress.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Client Address Line 1"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(referralClientAddress2.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Client Address Line 2"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(referralClientAddress3.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Client Address Line 3"), Toast.LENGTH_SHORT).show();
            return false;
        }


        if(referralClientPhone.getText().toString().equals("")) {
            Toast.makeText(context, getString(R.string.validate_error, "Phone Number"), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!referralClientPhone.getText().toString().trim().equals("")) {
            String phone = referralClientPhone.getText().toString().trim();
            if(!phone.matches("^[0-9]{11}$")) {
                Toast.makeText(context, getString(R.string.invalid_input, "Phone number"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(testedYes.isChecked()) {
            if (referralHivResult.getSpinner().getSelectedItemPosition() == 0){
                Toast.makeText(context, getString(R.string.drop_down_validate, "Client Previous HIV result"), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (referralHivTestingDate.getText().toString().equals("")) {
                Toast.makeText(context, getString(R.string.invalid_input, "Client Previous HIV test date"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(testedNo.isChecked() || testedYes.isChecked())) {
            Toast.makeText(context, getString(R.string.drop_down_validate, "Yes or No for previously tested for HIV"), Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if (referralSessionType.getSpinner().getSelectedItemPosition() == 0) {
            Toast.makeText(context, getString(R.string.drop_down_validate, "Type of session"), Toast.LENGTH_SHORT).show();
            return false;
        }*/
        if (indexTestingYes.isChecked()) {
            if (referralIndexTestingType.getSpinner().getSelectedItemPosition() == 0) {
                Toast.makeText(context, getString(R.string.drop_down_validate, "index testing type"), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (referralIndexClientId.getText().toString().equals("")) {
                Toast.makeText(context, getString(R.string.invalid_input, "Index client Id"), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        /*if (!(indexTestingYes.isChecked() || indexTestingNo.isChecked())) {
            Toast.makeText(context, getString(R.string.drop_down_validate, "Yes or No for index testing"), Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    private void clearForm() {
        hivTestingDate.setText("");
        referralClientName.setText("");
        referralClientLastname.setText("");
        referralClientAddress.setText("");
        referralClientPhone.setText("");
        referralClientAge.setEnabled(true);
        referralClientAge.setText("");
        referralClientDob.setText("");
        referralClientMaritalStatus.getSpinner().setSelection(0);
        referralEducationLevel.getSpinner().setSelection(0);
        referralEmploymentStatus.getSpinner().setSelection(0);
        referralFinalRecencyTest.getSpinner().setSelection(0);
        referralHivTestingDate.setText("");
        testedYes.setChecked(false);
        testedNo.setChecked(false);
        referralHivResult.getSpinner().setSelection(0);
        referralSessionType.getSpinner().setSelection(0);
        indexTestingYes.setChecked(false);
        indexTestingNo.setChecked(false);
        if (referralIndexTestingType.isEnabled()) {
            referralIndexTestingType.getSpinner().setSelection(0);
            referralIndexClientId.setText("");
        }
    }

    private void Disable() {
        referralClientMaritalStatus.getSpinner().setEnabled(false);
        referralHivResult.getSpinner().setEnabled(false);
        referralSessionType.getSpinner().setEnabled(false);
        referralEducationLevel.getSpinner().setEnabled(false);
        referralEmploymentStatus.getSpinner().setEnabled(false);
        referralFinalRecencyTest.getSpinner().setEnabled(false);


        hivTestingDate.setFocusable(false);
        referralClientName.setFocusable(false);
        referralClientAddress.setFocusable(false);
        referralClientPhone.setFocusable(false);
        referralClientAge.setFocusable(false);
        referralHivTestingDate.setFocusable(false);
        referralClientLastname.setFocusable(false);
        referralClientDob.setFocusable(false);
        referralIndexClientId.setFocusable(false);

    }

    public interface OnFragmentInteractionListener {
        void onContinueButtonClicked(String fragmentTag, Fragment fragment, Bundle bundle);
    }
}