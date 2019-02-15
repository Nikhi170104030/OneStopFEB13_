package com.swc.onestop.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.swc.onestop.R;
import com.swc.onestop.Timings_activities.Ferry;
import com.swc.onestop.Timings_activities.IITG_Bus;
import com.swc.onestop.Timings_activities.Internal_bus;


public class timings_fragment extends Fragment implements View.OnClickListener {
    LinearLayout ferry,interalbus,iitgbus;
     public static timings_fragment newInstance() {

        Bundle args = new Bundle();

        timings_fragment fragment = new timings_fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_timings_fragment, container, false);

        ferry = (LinearLayout) view.findViewById(R.id.ferry_ll);
        interalbus = (LinearLayout) view.findViewById(R.id.internalbus_ll);
        iitgbus = (LinearLayout) view.findViewById(R.id.iitgbus_ll);

        Button b = (Button) view.findViewById(R.id.button);
        b.setOnClickListener(this);

        return  view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        ferry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "fdsafas", Toast.LENGTH_SHORT).show();
                Intent ferry =new Intent(getActivity(),Ferry.class);
                startActivity(ferry);
            }
        });
        ferry.setOnClickListener(this);

        interalbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent internaliitg =new Intent(getActivity(),Internal_bus.class);
                startActivity(internaliitg);
            }
        });

        iitgbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iitgbus =new Intent(getActivity(),IITG_Bus.class);
                startActivity(iitgbus);
            }
        });

    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "dafsssadfa", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.ferry_ll:
                Toast.makeText(getActivity(), "fdsafas", Toast.LENGTH_SHORT).show();
                Intent ferry =new Intent(getActivity(),Ferry.class);
                startActivity(ferry);
                break;
        }
    }

}
