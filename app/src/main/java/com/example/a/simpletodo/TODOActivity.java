package com.example.a.simpletodo;
import com.example.a.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOExceptionWithCause;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.Toast;

public class TODOActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public final int REQUEST_CODE = 20;
    public static final int RESULT_CODE_OK = 200;
    public final int RESULT_CODE_ERR = 404;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView list_label;
    EditText et_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();

        list_label = (ListView) findViewById(R.id.list_label);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list_label.setAdapter(itemsAdapter);
        et_label = (EditText)findViewById(R.id.et_label);
        list_label.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        list_label.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
       // list_label.setOnItemClickListener(new View.OnClickListener(){
            @Override
            //public void onItemClick(android.view.View view)
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {


                Intent int_act = new Intent(TODOActivity.this, EditItemActivity.class);
                int_act.putExtra(EXTRA_MESSAGE, items.get(i));

               // Toast.makeText(TODOActivity.this, i, Toast.LENGTH_SHORT).show();
                int_act.putExtra("item_no", i);
                startActivityForResult(int_act, REQUEST_CODE);
                //onActivityResult();

            }



        });
    }

    private void readItems()
    {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try
        {
            items = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (IOException e)
        {

        }
    }

    private void writeItems()
    {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, items);
        }
        catch (IOException e)
        {}

    }

    public void onSubmitAction(View view) {
        String itemText = et_label.getText().toString();
        itemsAdapter.add(itemText);
        writeItems();

        et_label.setText("");
    }


    public void onActivityResult(int request_code, int result_code, Intent i)
    {
        try {
            if (request_code == REQUEST_CODE && result_code == RESULT_CODE_OK) {
                String s = i.getStringExtra(EditItemActivity.NEWITEM);
                int item_no = i.getExtras().getInt("item_no");
                items.set(item_no, s);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(TODOActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
