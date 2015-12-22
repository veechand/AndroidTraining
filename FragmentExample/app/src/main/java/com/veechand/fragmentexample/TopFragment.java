package com.veechand.fragmentexample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    Button topButton ;
    EditText titleText;
    TopFragmentMessagePasser callback;
    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TopFragmentMessagePasser){
            callback = (TopFragmentMessagePasser) context;
        } else {
            throw new IllegalStateException ("Not implemented TopFragmentMessagePasser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        topButton = (Button) getView().findViewById(R.id.buttonTop);
        titleText = (EditText) getView().findViewById(R.id.editTextTitle);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Top button clicked",Toast.LENGTH_SHORT).show();
                callback.setTitle(String.valueOf(titleText.getText()));
            }
        });
    }

}

interface TopFragmentMessagePasser {
    public void setTitle(String title);
}