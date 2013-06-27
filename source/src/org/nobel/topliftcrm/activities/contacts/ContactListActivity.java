package org.nobel.topliftcrm.activities.contacts;

import java.util.List;

import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.resources.PersonResource;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityDetailActivity;
import org.nobel.topliftcrm.activities.base.EntityListActivity;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;
import org.nobel.topliftcrm.data.HighriseApiService;
import org.nobel.topliftcrm.data.LoadDataTask;

import android.app.ProgressDialog;
import android.content.Context;

import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;


public class ContactListActivity extends EntityListActivity<Person> {

    class LoadContactsTask extends LoadDataTask<Void, Void, List<Person>> {

        private ProgressDialog progressDialog;

        public LoadContactsTask(Context context) {
            super(context);
        }

        @Override
        protected List<Person> doLoad(Void... arg0) {
            final List<Person> contacts = HighriseApiService.getInstance(ContactListActivity.this)
                    .getResource(PersonResource.class).getAll();
            return contacts;
        }

        @Override
        protected void hideProgessbar() {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPostExecute(List<Person> contacts) {
            super.onPostExecute(contacts);
            setListAdapter(new ContactListAdapter(ContactListActivity.this, contacts));
        }

        @Override
        protected void showProgessbar() {
            progressDialog = ProgressDialog.show(ContactListActivity.this, "Loading contacts", "Loading contacts...");
        }

    }

    @Override
    public ContactListAdapter getListAdapter() {
        return (ContactListAdapter) super.getListAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.searchable_list, menu);
        refreshItem = menu.findItem(R.id.refresh);
        SearchView searchView = (SearchView) ((MenuItemWrapper) menu.findItem(R.id.search)).getActionView();
        searchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                getListAdapter().getFilter().filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                getListAdapter().getFilter().filter(query);
                return true;
            }
        });
        return true;
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
