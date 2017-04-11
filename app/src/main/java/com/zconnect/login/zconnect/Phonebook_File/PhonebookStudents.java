package com.zconnect.login.zconnect.Phonebook_File;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zconnect.login.zconnect.R;

import java.util.Vector;


public class PhonebookStudents extends Fragment {
    Vector<PhonebookStudentHostelItem> phonebookStudentHostelItems;
    private PhonebookStudentHostelRV adapter;
    private RecyclerView recyclerView;
    private String hostel[] = {"AH-1", "AH-2", "AH-3", "AH-4", "AH-5", "AH-6", "AH-7", "AH-8", "CH-1", "CH-2", "CH-3", "CH-4", "CH-5", "CH-6"};
    public PhonebookStudents() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Create Fragment view
        View view = inflater.inflate(R.layout.fragment_phonebook_students, container, false);

        //Reference views---------------------------------------------------------------------------
        recyclerView = (RecyclerView) view.findViewById(R.id.student_phone_rv);


        //MAIN--------------------------------------------------------------------------------------


        //setHasFixedSize is used to optimise RV if we know for sure that this view's bounds do not
        // change with data
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        phonebookStudentHostelItems = data();      //Setup layout manager. VERY IMP ALWAYS
        adapter = new PhonebookStudentHostelRV(phonebookStudentHostelItems, getContext());
        recyclerView.setAdapter(adapter);


        return view;
    }

    public Vector<PhonebookStudentHostelItem> data() {
        Vector<PhonebookStudentHostelItem> phonebookStudentHostelItems = new Vector<>();
        for (int i = 0; i < 14; i++) {
            PhonebookStudentHostelItem phonebookStudentHostelItem1 = new PhonebookStudentHostelItem();
            phonebookStudentHostelItem1.setHostel(hostel[i]);
            phonebookStudentHostelItems.add(phonebookStudentHostelItem1);
    }
        return phonebookStudentHostelItems;
    }

}
