package apps.daydreams.cinemapp.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"co.cinemapp@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "MailBox");
                i.putExtra(Intent.EXTRA_TEXT   , mailText.getText().toString());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                    mailText.setText("");
                    mailText.clearFocus();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rootView;
    }

}
