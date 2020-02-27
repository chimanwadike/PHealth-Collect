//package org.webworks.datatool.Adapter;
//
//import android.content.Context;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckedTextView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;
//
//import org.mgic_nigeria.hts.Model.ClientReferralForm;
//import org.mgic_nigeria.hts.Model.DashboardSorter;
//import org.mgic_nigeria.hts.Model.Facility;
//import org.mgic_nigeria.hts.R;
//import org.mgic_nigeria.hts.Repository.FacilityRepository;
//
//import java.util.ArrayList;
//
///**
// * Created by Johnbosco on 28/06/2017.
// */
//
//public class DashboardViewAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {
//
//    private Context context;
//    private ArrayList<ClientReferralForm> forms;
//    private TextView name, code, referredTo, toOrFrom, uploadedTxt;
//    private View relativeLayer;
//    private CheckedTextView uploaded;
//    private int fragmentId;
//    private ArrayList<String> formGroupText;
//    private ArrayList<Integer> formGroups;
//    ArrayList<DashboardSorter> sorters;
//
//
//    public DashboardViewAdapter(Context _context, ArrayList<ClientReferralForm> _forms, int _fragmentId) {
//        this.forms = _forms;
//        context = _context;
//        this.fragmentId = _fragmentId;
//    }
//
//    public DashboardViewAdapter(Context _context, ArrayList<ClientReferralForm> _forms, ArrayList<DashboardSorter> _sorters, int _fragmentId) {
//        this.forms = _forms;
//        context = _context;
//        this.fragmentId = _fragmentId;
//        this.sorters = _sorters;
//    }
//
//    public DashboardViewAdapter(Context _context, ArrayList<ClientReferralForm> _forms,
//                                ArrayList<Integer> _formGroups, ArrayList<String> _formGroupText,
//                                int _fragmentId) {
//        this.forms = _forms;
//        context = _context;
//        this.fragmentId = _fragmentId;
//        this.formGroups = _formGroups;
//        this.formGroupText = _formGroupText;
//    }
//
//    @Override
//    public int getCount() {
//        return forms.size();
//    }
//
//    @Override
//    public ClientReferralForm getItem(int position) {
//        return forms.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.grid_single_layout, null);
//        init(convertView);
//        assign(forms.get(position));
//        return convertView;
//    }
//
//    private void init(View convertView) {
//        //cardView = (CardView)convertView.findViewById(R.id.cv);
//        name = (TextView)convertView.findViewById(R.id.name);
//        code = (TextView)convertView.findViewById(R.id.code);
//        referredTo = (TextView)convertView.findViewById(R.id.referred);
//        relativeLayer = (LinearLayout)convertView.findViewById(R.id.lay2);
//        //relativeLayer.setBackgroundColor(UtilFuns.getColors(context).get(fragmentId));
//        //relativeLayer.setBackgroundColor(UtilFuns.getColors(context).get(new Random().nextInt(3)));
//        toOrFrom = (TextView)convertView.findViewById(R.id.to_or_from);
//        uploaded = (CheckedTextView)convertView.findViewById(R.id.uploaded);
//        uploadedTxt = convertView.findViewById(R.id.uploadedtxt);
//    }
//
//    private void assign(ClientReferralForm form) {
//
//        name.setText(form.getClientName());
//        code.setText(form.getClientIdentifier());
//        FacilityRepository repository = new FacilityRepository(context);
//        if (!(form.getRefferedTo()== null)){
//            Facility facility = repository.getReferralForm(form.getRefferedTo());
//            if (facility != null)
//                referredTo.setText(facility.getName());
//        }
//
//        if(form.getUploaded() == 0) {
//            if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){
//                uploaded.setCheckMarkDrawable(R.drawable.ic_cancel_red);
//            }else{
//                uploadedTxt.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_red));
//                uploadedTxt.setText("Not Uploaded");
//            }
//
//            toOrFrom.setText("Reffered to");
//
//        }else if(form.getClientConfirmed() == 1){
//            uploadedTxt.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_green));
//            uploadedTxt.setText("Completed");
//        }else if(form.getUploaded() == 1 || form.getUploaded() == 2) {
//            if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){
//                uploaded.setCheckMarkDrawable(R.drawable.ic_done_green);
//            }else{
//               uploadedTxt.setBackground(context.getResources().getDrawable(R.drawable.rounded_corner_blue));
//                uploadedTxt.setText("Uploaded");
//            }
//            toOrFrom.setText("Reffered to");
//        }
//        else if (form.getUploaded() == 3) {
//            uploaded.setVisibility(View.GONE);
//            toOrFrom.setText("Received from");
//           // Facility fac = repository.getReferralForm(form.getRefferedFrom());
//           // referredTo.setText(fac.getName());
//        }
//    }
//
//    @Override
//    public int getCountForHeader(int i) {
//        return sorters.get(i).getFormCount();
//        //return formGroups.get(i);
//    }
//
//    @Override
//    public int getNumHeaders() {
//        return sorters.size();
//        //return formGroups.size();
//    }
//
//    @Override
//    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.grid_header, null);
//        TextView textView = (TextView)view.findViewById(R.id.grid_header_text);
//        //textView.setText(String.valueOf(formGroupText.get(i)));
//        textView.setText(String.valueOf(sorters.get(i).getFormDate()));
//        return view;
//    }
//}