package org.nobel.topliftcrm.activities.contacts;

import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.resources.PersonResource;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.data.HighriseApiService;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ContactEditActivity extends SherlockActivity {

    private Person contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_edit);
        contact = HighriseApiService.getInstance(this).getResource(PersonResource.class)
                .get(getIntent().getExtras().getInt("CONTACT_ID"));
        setTitle("Edit " + contact.getFullName());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.contact_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBackToContactDetail();
                return true;
            case R.id.menu_save:
                Toast.makeText(getApplicationContext(), "Contact successfully saved.", Toast.LENGTH_LONG).show();
                goBackToContactDetail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goBackToContactDetail() {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("CONTACT_ID", contact.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
