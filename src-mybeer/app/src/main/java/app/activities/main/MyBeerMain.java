package app.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class MyBeerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    /**
     * Show the progress bar component visible and put invisble the rest of the view.
     */
    protected void setProgressBar(String text, View goneView, View visibleView, TextView textView){

        goneView.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);

        if (textView != null) textView.setText(text);
    }

    /**
     * Show the view visible and put invisble progress bar component.
     */
    protected void removeProgressBar(View goneView, View visibleView){

        goneView.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
    }

    /**
     * Set the action bar in the view
     */
    protected abstract void setAppBar();

    /**
     * Inicializate all components of the view
     */
    protected abstract void inicializateView();

    /**
     * Throw a information message
     *
     * @param msg the message to show
     */
    protected void throwToast(int msg) { Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); }
    protected void throwToast(String msg) { Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); }
}
