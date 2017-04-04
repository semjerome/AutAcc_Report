package com.example.autoacc_reportfinal;

/**
 * Created by Zaido on 2016-11-13.
 */
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PageFragmentVid extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    // Identifier for the permission request
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;


    public static PageFragmentVid create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentVid fragment = new PageFragmentVid();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(mPage==1) //information
        {
            view = inflater.inflate(R.layout.fragment_add_info, container, false);
        }
        else if(mPage==2)
        { //location
             view = inflater.inflate(R.layout.fragment_eventlocation, container, false);
            Button btnShow= (Button) view.findViewById(R.id.btnShowLocation);


            btnShow.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(getActivity(),  LocationActivity.class);
                    ((VidActivity) getActivity()).startActivity(intent);
                }
            });
        }
        else if (mPage == 3)
        { //video
            view = inflater.inflate(R.layout.fragment_eventvideos, container, false);
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView textView = (TextView) view;
            textView.setText("Fragment #" + mPage);
        }

        return view;
    }

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI

        }
    }



}