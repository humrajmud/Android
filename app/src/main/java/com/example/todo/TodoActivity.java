package com.example.todo;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {

    private String[] mTodos;
    private int mTodoIndex = 0;

    @Override

    //class has one Activity method, onCreate(Bundle) which is called when an instance of the activity subclass is created.
    protected void onCreate(Bundle savedInstanceState) {

        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        // call the super class onCreate to complete the creation of Activity
        // like the view hierarchy
        super.onCreate(savedInstanceState);

        // UI, setContentView(R.layout.activity_todo) is called
        //method inflates each widget in the layout XML resources file and instantiate it as defined by its attributes, hence the equivalent object.
        //once id is defined can be referenced with a method findViewById(R.id.name)
        setContentView(R.layout.activity_todo);

        // initialize member TextView so we can manipulate it later
        //once id is defined can be referenced with a method findViewById(R.id.name)
        //Activity has a method findViewById(int id) which returns a View object for the widget id passed to it.
        final TextView TodoTextView;
        TodoTextView = (TextView) findViewById(R.id.textViewTodo);


        // read the todo array from res/values/strings.xml
        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);
        // display the first task from mTodo array in the TodoTextView
        TodoTextView.setText(mTodos[mTodoIndex]);

        Button buttonNext;
        buttonNext = (Button) findViewById(R.id.buttonNext);

        // OnClick listener for the  Next button
        //the onClick(View v) method is implemented as it is required by the OnClickListener interface.
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTodoIndex += 1;
                TodoTextView.setText(mTodos[mTodoIndex]);
            }
        });
    }
}