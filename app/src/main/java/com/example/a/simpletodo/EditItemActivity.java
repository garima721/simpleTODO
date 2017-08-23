package com.example.a.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {
    public static final String NEWITEM = "editeditem";
    private static int item_no;
    EditText et_edititem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        et_edititem = (EditText)findViewById(R.id.et_edititem);
        Intent intent = getIntent();
        String message = intent.getStringExtra(TODOActivity.EXTRA_MESSAGE);
        item_no = intent.getExtras().getInt("item_no");
        CharSequence cs_msg = message;

        et_edititem.setText(cs_msg);


    }

    public void onSaveAction(View view) {
        Intent i = new Intent(this, TODOActivity.class);
        et_edititem = (EditText)findViewById(R.id.et_edititem);
        i.putExtra(NEWITEM, et_edititem.getText().toString());
        i.putExtra("item_no", item_no);
        setResult(TODOActivity.RESULT_CODE_OK, i);
        finish();
        //startActivityForResult(i, 20);

    }
}
