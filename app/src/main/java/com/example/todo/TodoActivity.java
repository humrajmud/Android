package com.example.todo;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;


public class TodoActivity extends AppCompatActivity {

    private String[] mTodos;
    private int mTodoIndex = 0;
// variables created


    // In case of state change, due to rotating the phone
// store the mTodoIndex to display the same mTodos element post state change
// N.B. small amounts of data, typically IDs can be stored as key, value pairs in a Bundle
// the alternative is to abstract view data to a ViewModel which can be in scope in all
// Activity states and more suitable for larger amounts of data

    //The solution to the rotation problem is to store the value of mTodoIndex integer accross run time changes like rotation of the phone.
    // Android calls the activity method onSaveInstanceState(Bundle) before onStop().
    // We can override this method and add the value mTodoIndex to be saved in the Bundle object.
    // The bundle object requires a key, value pair.


    private static final String TODO_INDEX = "todoIndex";

    //define the final variable TAG
    public static final String TAG = "TodoActivity";


    // override to write the value of mTodoIndex into the Bundle with TODO_INDEX as its key
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);
    }

    // onSaveInstanceState method from parent class passes savedInstanceState
    // and a type bundle to declare
// super. is called to instantiate onSaveInstanceState from parent .putInt called and
    //variables passed through TODO_INDEX, mTodoIndex.

    @Override

    //class has one Activity method, onCreate(Bundle) which is called when an instance of the activity subclass is created.
    protected void onCreate(Bundle savedInstanceState) {

        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        // call the super class onCreate to complete the creation of Activity
        // like the view hierarchy
        super.onCreate(savedInstanceState);

        //The android.util.log class sends log messages to a shared log.
        // There are a variety of option; here is an example of a useful log for debugging while monitoring change of state at run time.
        Log.d(TAG, " *** Just to say the PC is in onCreate!");


        // UI, setContentView(R.layout.activity_todo) is called
        //method inflates each widget in the layout XML resources file and instantiate it as defined by its attributes, hence the equivalent object.
        //once id is defined can be referenced with a method findViewById(R.id.name)
        setContentView(R.layout.activity_todo);

        //in the onCreate(Bundle) method, restore the index value
        // check for saved state due to changes such as rotation or back button
        // and restore any saved state such as the todo index
        //the index integer is passed between the state of the activity and is not reset to the first item after rotating the phone.
        if (savedInstanceState != null){
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }






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