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

    //The solution to the rotation problem is to store the value of mTodoIndex integer accross run time changes like rotation of the phone.
    // Android calls the activity method onSaveInstanceState(Bundle) before onStop().
    // We can override this method and add the value mTodoIndex to be saved in the Bundle object.
    // The bundle object requires a key, value pair.

    // override to write the value of mTodoIndex into the Bundle with TODO_INDEX as its key

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState); // when rotates it jumps to this so view can be serialized
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex); // view gets stored in here as a bundle
    }

    // onSaveInstanceState method from parent class passes savedInstanceState
    // and a type bundle to declare
// super. is called to instantiate onSaveInstanceState from parent .putInt called and
    //variables passed through TODO_INDEX, mTodoIndex.
//call back methods are onstop onresume oncreate activity manager controls this (android manager)

    @Override  //compiler override ( parent can check ifthis code is right?)
    //override oncreate and set content view
    //class has one Activity method, onCreate(Bundle) which is called when an instance of the activity subclass is created.
    protected void onCreate(Bundle savedInstanceState) {

        // call the super class onCreate from parent and pass paramater savedinstanceState which is a bundle type to complete the creation of Activity
        super.onCreate(savedInstanceState);

        //in the onCreate(Bundle) method, restore the index value
        // check for saved state due to changes such as rotation or back button
        // and restore any saved state such as the todo index
        //the index integer is passed between the state of the activity and is not reset to the first item after rotating the phone.

        // if saved instance not = to null do the bottem line ( get the param and put in savedinstance so we can reconstruct later)
        // when phone rotates
        if (savedInstanceState != null) {
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        //The android.util.log class sends log messages to a shared log.
        // There are a variety of option; here is an example of a useful log for debugging while monitoring change of state at run time.
        Log.d(TAG, " *** Just to say the PC is in onCreate!");

        // UI, setContentView(R.layout.activity_todo) is called
        //method inflates each widget in the layout XML resources file and instantiate it as defined by its attributes, hence the equivalent object.
        //once id is defined can be referenced with a method findViewById(R.id.name)
        setContentView(R.layout.activity_todo);

        // initialize member TextView so we can manipulate it later
        //once id is defined can be referenced with a method findViewById(R.id.name)
        //Activity has a method findViewById(int id) which returns a View object for the widget id passed to it.
        final TextView TodoTextView;
        // xml file(textviewtodo) casted onto text view and an object todotextview is created
        TodoTextView = (TextView) findViewById(R.id.textViewTodo);
        setTextViewComplete("");

        // read the todo array from res/values/strings.xml
        //class called resources, instantiating object res
        //use method getstringarray on res to put into mtodos
        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);
        // display the first task from mTodo array in the TodoTextView
        TodoTextView.setText(mTodos[mTodoIndex]);

        Button buttonNext;
        buttonNext = (Button) findViewById(R.id.buttonNext);
// R is a static class
        // OnClick listener for the  Next button
        //the onClick(View v) method is implemented as it is required by the OnClickListener interface.
        //define a listener onto object buttonnext
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