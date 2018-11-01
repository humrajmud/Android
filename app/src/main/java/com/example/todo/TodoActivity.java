package com.example.todo;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;


public class TodoActivity extends AppCompatActivity {


    private static final int IS_SUCCESS = 0;
    private String[] mTodos;
    private int mTodoIndex = 0;

    public static final String TAG = "TodoActivity";
// variables created

    // name, value pair to be returned in an intent
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    private static final String TODO_INDEX = "todoIndex";

    // In case of state change, due to rotating the phone
// store the mTodoIndex to display the same mTodos element post state change
// N.B. small amounts of data, typically IDs can be stored as key, value pairs in a Bundle
// the alternative is to abstract view data to a ViewModel which can be in scope in all
// Activity states and more suitable for larger amounts of data

    //The solution to the rotation problem is to store the value of mTodoIndex integer accross run time changes like rotation of the phone.
    // Android calls the activity method onSaveInstanceState(Bundle) before onStop().
    // We can override this method and add the value mTodoIndex to be saved in the Bundle object.
    // The bundle object requires a key, value pair.

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
        if (savedInstanceState != null) {
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }


        // initialize member TextView so we can manipulate it later
        //once id is defined can be referenced with a method findViewById(R.id.name)
        //Activity has a method findViewById(int id) which returns a View object for the widget id passed to it.
        final TextView TodoTextView;
        TodoTextView = (TextView) findViewById(R.id.textViewTodo);

        setTextViewComplete("");


        // read the todo array from res/values/strings.xml
        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);
        // display the first task from mTodo array in the TodoTextView
        TodoTextView.setText(mTodos[mTodoIndex]);


        Button buttonNext;
        buttonNext = (Button) findViewById(R.id.buttonNext);

        // OnClick listener for the  Next button
        //the onClick(View v) method is implemented as it is required by the OnClickListener interface.
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if statement to cycle the array
                mTodoIndex += 1;
                if (mTodoIndex > 4) {
                    mTodoIndex = 0;
                }
                TodoTextView.setText(mTodos[mTodoIndex]);
                setTextViewComplete("");
            }
        });

        Button buttonPrev;
        buttonPrev = (Button) findViewById(R.id.buttonPrev);

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if statement to cycle the array
                mTodoIndex -= 1;
                if (mTodoIndex < 0) {
                    mTodoIndex = 4;
                }
                TodoTextView.setText(mTodos[mTodoIndex]);
                setTextViewComplete("");
            }
        });

        Button buttonTodoDetail = (Button) findViewById(R.id.buttonTodoDetail);
        buttonTodoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TodoDetailActivity.newIntent(TodoActivity.this, mTodoIndex);
                startActivity(intent);

                //second param request code identifies the call as there could be many intenets
                startActivityForResult(intent, IS_SUCCESS);
                // The result will come back through
                // onActivityResult(requestCode, resultCode, Intent) method
            }
        });


    }

    /*
        requestCode is the integer request code originally supplied to startActivityForResult
        resultCode is the integer result code returned by the child activity through its setResult()
        intent date attached with intent "extras"
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == IS_SUCCESS) {
            if (intent != null) {
                //data is intent from child activity
                boolean isTodoComplete = intent.getBooleanExtra(IS_TODO_COMPLETE, false);

            } else {
                Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "requestCode in intent does not match the parent request . .. . ",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void updateTodoComplete(boolean is_todo_complete) {

        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);

        if (is_todo_complete) {
            textViewTodo.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.backgroundSuccess));
            textViewTodo.setTextColor(
                    ContextCompat.getColor(this, R.color.colorSuccess));

            setTextViewComplete("\u2713");
        }

    }




    private void setTextViewComplete( String message ){
        final TextView textViewComplete;
        textViewComplete = (TextView) findViewById(R.id.textViewComplete);

        textViewComplete.setText(message);
    }

}