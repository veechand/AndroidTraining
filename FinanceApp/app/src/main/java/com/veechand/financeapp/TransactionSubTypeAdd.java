package com.veechand.financeapp;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.veechand.financeapp.db.DBHelper;

public class TransactionSubTypeAdd extends Fragment {

    private Button addButton;
    private RadioGroup subTypeRadioGroup;
    private EditText subTypeName;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_sub_type_add);*/
        View view =  inflater.inflate(R.layout.activity_transaction_sub_type_add
                , container, false);

        this.dbHelper = new DBHelper(getActivity());

        this.addButton = (Button) view.findViewById(R.id.addButton);
        this.subTypeRadioGroup = (RadioGroup) view.findViewById(R.id.addSubTypeRadioGroup);
        this.subTypeName = (EditText) view.findViewById(R.id.subTypeName);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curSubTypeName = subTypeName.getText().toString();
                int checkedRadioButtonId = subTypeRadioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == -1){
                    Toast.makeText(getActivity(),R.string.error_no_sub_type_selected,Toast.LENGTH_LONG).show();
                } else if (curSubTypeName == null || curSubTypeName.isEmpty()) {
                    Toast.makeText(getActivity(),R.string.error_no_sub_type_name,Toast.LENGTH_LONG).show();
                } else {
                    //Now handle adding to DB
                    int isIncome = (checkedRadioButtonId == R.id.addIncomeRadioButton)?getResources().getInteger(R.integer.income_true):getResources().getInteger(R.integer.income_false);
                    long userID = 0;
                    long insertResult = dbHelper.insertNewSubType(curSubTypeName, isIncome, userID);
                    if (insertResult == -1){
                        Toast.makeText(getActivity(),R.string.error_sub_type_add_failure,Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),R.string.error_sub_type_add_success,Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        return  view;
    }
}
