package com.example.lms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.material.snackbar.Snackbar.*;

public class ChangePasswordActivity extends Fragment {
    FirebaseAuth firebaseAuth;
    public ChangePasswordActivity(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.activity_change_password,container,false);
        firebaseAuth=FirebaseAuth.getInstance();
        Button button=(Button) view.findViewById(R.id.updatePassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePasswordFunc(view);
            }
        });
        return view;//return super.onCreateView(inflater, container, savedInstanceState);
    }



    public void updatePasswordFunc(final View view){
        EditText oldPassword=(EditText) view.findViewById(R.id.changepasswordact_oldpassword);
        final EditText newPassword=(EditText) view.findViewById(R.id.changepasswordact_newpassword);
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String email=firebaseUser.getEmail();
     //   AwesomeValidation awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
      //  awesomeValidation.addValidation(getContext(),R.id.txtISBN,"^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$",R.string.generalerror);

        //this, R.id.txtISBN, "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$", R.string.generalerror

      //  View parentView=(View) findViewById(R.id.changepasswordact_parentlayout);
        AuthCredential credential= EmailAuthProvider.getCredential(email,oldPassword.getText().toString());
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                //    Toast.makeText(getContext(),"error for reauthenticate response suucess"+task.getException(),Toast.LENGTH_SHORT).show();
                    firebaseUser.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar.make(view.findViewById(R.id.changepasswordact_parentlayout),"Password Successfully Modified",
                                    LENGTH_LONG).show();
                        }
                    });

                }
                else{
                 //   Toast.makeText(getContext(),"error for reauthenticate response"+task.getException(),Toast.LENGTH_SHORT).show();
                    Snackbar.make(view.findViewById(R.id.changepasswordact_parentlayout),"Something went wrong. Please try again " +
                                    "later", LENGTH_LONG).show();
                }
            }
        });


    }
}
