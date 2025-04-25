package com.example.vrmbyqirtas;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Vrmform extends AppCompatActivity {
   EditText etName,etFatherName,etCNIC,etAddress,etMobile,etUC,etIlaqa;
   Spinner spinnerCategory;
   CheckBox chkSpecialVoter;
   Button btnSubmit;
   DatabaseReference databaseReference;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrmform);
       databaseReference = FirebaseDatabase.getInstance().getReference().child("Voters");
etName = findViewById(R.id.etName);
etFatherName = findViewById(R.id.etFatherName);
etCNIC = findViewById(R.id.etCNIC);
etAddress = findViewById(R.id.etAddress);
etMobile = findViewById(R.id.etMobile);
etUC = findViewById(R.id.etUc);
etIlaqa = findViewById(R.id.etIlaqa);
spinnerCategory = findViewById(R.id.spinnerCategory);
chkSpecialVoter = findViewById(R.id.chkSpecialVoter);
btnSubmit = findViewById(R.id.btnSubmit);
    btnSubmit.setOnClickListener(view -> submitForm());

   }

private void submitForm() {

    String name = etName.getText().toString().trim();
    String fatherName = etFatherName.getText().toString().trim();
    String CNIC = etCNIC.getText().toString().trim();
    String address = etAddress.getText().toString().trim();
    String mobile = etMobile.getText().toString().trim();
    String uc = etUC.getText().toString().trim();
    String ilaqa = etIlaqa.getText().toString().trim();
    String category = spinnerCategory.getSelectedItem().toString();
    boolean specialVoter = chkSpecialVoter.isChecked();
    if (name.isEmpty() || fatherName.isEmpty() || CNIC.isEmpty() || address.isEmpty() || mobile.isEmpty() || uc.isEmpty() || ilaqa.isEmpty()) {
        Toast.makeText(this, "براہ کرم تمام ضروری فیلڈز پر کریں", Toast.LENGTH_SHORT).show();
        return;
    }
    String voterId = databaseReference.push().getKey();

// چیک کریں کہ voterId null تو نہیں
    if (voterId == null) {
        Toast.makeText(this, "Error: Unable to generate Voter ID", Toast.LENGTH_SHORT).show();
        return;
    }

// لاگ میں پرنٹ کریں تاکہ چیک کر سکیں
    Log.d("VoterID", "Generated Voter ID: " + voterId);

    Voter voter = new Voter(name, fatherName, CNIC, address, mobile, uc, ilaqa, category, specialVoter);

    databaseReference.child(voterId).setValue(voter).addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            Toast.makeText(Vrmform.this, "فارم کامیابی سے جمع ہو گیا!", Toast.LENGTH_LONG).show();

            // تھوڑا انتظار کروا کر مین ایکٹیوٹی پر جائیں تاکہ ٹوسٹ نظر آئے
            new android.os.Handler().postDelayed(() -> {
                Intent intent = new Intent(Vrmform.this, MainActivity.class);
                startActivity(intent);
                finish(); // موجودہ activity بند کریں
            }, 2000); // 2 سیکنڈ انتظار کریں

        } else {
            Toast.makeText(Vrmform.this, "فارم سبمٹ کرنے میں مسئلہ آیا", Toast.LENGTH_SHORT).show();
            Log.e("FirebaseError", "Data not saved: " + task.getException());
        }
    });
}}