package org.nobel.topliftcrm.activities.contacts;

import java.util.List;

import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.resources.PersonResource;
import org.nobel.topliftcrm.activities.base.EntityDetailActivity;
import org.nobel.topliftcrm.activities.base.EntityListActivity;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;
import org.nobel.topliftcrm.data.HighriseApiService;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ContactListActivity extends EntityListActivity<Person> {

    class LoadContactsTask extends AsyncTask<Void, Void, List<Person>> {

        private ProgressDialog progressDialog;

        @Override
        protected List<Person> doInBackground(Void... arg0) {
            final List<Person> contacts = HighriseApiService.getInstance(ContactListActivity.this)
                    .getResource(PersonResource.class).getAll();
            return contacts;
        }

        @Override
        protected void onPostExecute(List<Person> contacts) {
            setListAdapter(new ContactListAdapter(ContactListActivity.this, contacts));
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ContactListActivity.this, "Loading contacts", "Loading contacts...");
        }

    }

    @Override
    protected void clearEntityList() {
        HighriseApiService.getInstance(this).getResource(PersonResource.class).clear();
    }

    @Override
    protected EntityListAdapter<Person> createListAdapter(List<Person> contacts) {
        return new ContactListAdapter(this, contacts);
    }

    @Override
    protected Class<? extends EntityDetailActivity<Person>> getEntityDetailActivity() {
        return ContactDetailActivity.class;
    }

    @Override
    protected List<Person> loadEntityList() {
        return HighriseApiService.getInstance(this).getResource(PersonResource.class).getAll();
    }
}
