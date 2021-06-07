
package com.e.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GroupApplication extends AppCompatActivity {

    private Button cancel,apply;
    EditText name,mobile,city,address;
    String mail,a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_application);

        cancel = findViewById(R.id.cancel);
        apply = findViewById(R.id.apply);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        mail= "1234rexexe@gmail.com";
        a="dsds";
        b="sasad";

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW
                , Uri.parse("mailto:"+mail));
                intent.putExtra(Intent.EXTRA_SUBJECT,name.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,city.getText().toString());
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupApplication.this, grouptrip.class);
                startActivity(intent);
            }
        });
    }
}
