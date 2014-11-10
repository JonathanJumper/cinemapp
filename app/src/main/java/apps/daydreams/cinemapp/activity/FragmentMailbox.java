package apps.daydreams.cinemapp.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import apps.daydreams.cinemapp.R;

/**
 * Created by Usuario on 08/11/2014.
 */
public class FragmentMailbox extends Fragment {

    private Button send;
    private EditText mailText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mailbox, container, false);

        send = (Button) rootView.findViewById(R.id.send);
        mailText = (EditText) rootView.findViewById(R.id.mailbox_edit_text);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("text->>>>>>>>>>>>>>>>", mailText.getText().toString());
                mailText.setText("");
                mailText.clearFocus();
            }
        });
        return rootView;
    }

}
