package com.footballmania.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.footballmania.R;
import com.footballmania.database.dbobjects.User;
import com.footballmania.serverConnection.TomcatConnection;
import com.footballmania.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {

    TextView loginTextView, emailTextView, nameTextView, lastUpdateDateTextView;
    EditText changeNameEditText, changeEmailEditText, changePasswordEditText, confirmChangePasswordEditText;

    Button applyChangesButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        prepareLayout(root);

        return root;
    }

    private void prepareLayout(View root){

        loginTextView = root.findViewById(R.id.loginTextView);
        emailTextView = root.findViewById(R.id.emailTextView);
        nameTextView = root.findViewById(R.id.nameTextView);
        lastUpdateDateTextView = root.findViewById(R.id.lastUpdateDateTextView);

        changeNameEditText = root.findViewById(R.id.changeNameEditText);
        changeEmailEditText = root.findViewById(R.id.changeEmailEditText);
        changePasswordEditText = root.findViewById(R.id.changePasswordEditText);
        confirmChangePasswordEditText = root.findViewById(R.id.confrimChangePasswordEditText);

        applyChangesButton = root.findViewById(R.id.applyChangesButton);

        loginTextView.setText(TomcatConnection.loggedUser.getLogin());
        emailTextView.setText(TomcatConnection.loggedUser.getEmail());
        nameTextView.setText(TomcatConnection.loggedUser.getFirstName());
        lastUpdateDateTextView.setText(Utility.getDateString(new Date(TomcatConnection.loggedUser.getLastUpdateDate())));

        applyChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkMatchingPasswords()) {
                    Toast.makeText(getContext(), R.string.register_password_missmatch, Toast.LENGTH_SHORT).show();
                } else {
                    User loggedUser = TomcatConnection.loggedUser;
                    String newName, newEmail, newPassword;

                    if(changeNameEditText.getText() != null)
                        newName = changeNameEditText.getText().toString();
                    else
                        newName = loggedUser.getFirstName();

                    if (changeEmailEditText.getText() != null)
                        newEmail = changeEmailEditText.getText().toString();
                    else
                        newEmail = loggedUser.getEmail();

                    if(changePasswordEditText.getText() != null)
                        newPassword = changePasswordEditText.getText().toString();
                    else
                        newPassword = loggedUser.getPassword();

                    TomcatConnection.updateAccountChanges(getContext(), newName, newEmail, newPassword);

                }

            }
        });

    }

    private boolean checkMatchingPasswords(){
        return changePasswordEditText.getText().toString().equals(confirmChangePasswordEditText.getText().toString());
    }

}