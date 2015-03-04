package com.example.marion.stocks;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private Stock stock;
    private Button done;
    private  TextView priceValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText input = (EditText)findViewById(R.id.inputSymbol);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                  MyThread thread = new MyThread();
                    thread.doInBackground(stock);
                }

                return false;
            }
        });

        stock = new Stock(input.toString());
        try {
            stock.load();
        } catch (IOException e) {
            e.printStackTrace();

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        class MyThread extends AsyncTask<Stock,Integer, String>{


            @Override
            protected String doInBackground(Stock... params) {
                try {
                    stock.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String priceTemp  = stock.getLastTradePrice();
                CharSequence charSequence = new String(priceTemp);

             priceValue = (TextView)findViewById(R.id.priceValue);
                priceValue.setText(charSequence);

                TextView rangeTemp = (TextView)findViewById(R.id.rangeLabel);
                CharSequence raTemp = stock.getRange();
                rangeTemp.setText(raTemp);

                String changeTemp = stock.getChange();
                TextView changeText = (TextView)findViewById(R.id.changeLabel);
                changeText.setText((CharSequence)changeTemp);

                String nameTemp = stock.getName();
                TextView nameText = (TextView)findViewById(R.id.nameView);
                nameText.setText((CharSequence)nameTemp);

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }
        }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Stock stock = (Stock)savedInstanceState.getSerializable("obj");
        if(stock !=null)
            this.stock = stock;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("obj", (java.io.Serializable) stock);
    }
}