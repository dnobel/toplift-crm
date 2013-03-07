package org.nobel.highriseandroid.activities.contacts;

import java.util.List;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.base.EntityDetailActivity;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseandroid.util.ImageUtil;
import org.nobel.highriseapi.entities.ContactData;
import org.nobel.highriseapi.entities.EMailAddress;
import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.entities.PhoneNumber;
import org.nobel.highriseapi.resources.PersonResource;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailActivity extends EntityDetailActivity<Person> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected String getActivityTitle(Person entity) {
        return getTitle() + " " + entity.getFullName();
    }

    @Override
    protected Person getEntity(Integer id) {
        return HighriseApiService.getInstance(this).getResource(PersonResource.class).get(id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contact_detail;
    }

    @Override
    protected Class<? extends Activity> getListActivity() {
        return ContactListActivity.class;
    }

    @Override
    protected void renderView(Person contact) {

        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText(contact.getFirstName() + " " + contact.getLastName());

        TextView positionText = (TextView) findViewById(R.id.position);
        positionText.setText(contact.getTitle());

        TextView companyText = (TextView) findViewById(R.id.company);
        companyText.setText(contact.getCompanyName());

        ContactData contactData = contact.getContactData();
        if (contactData != null) {
            List<EMailAddress> mailAddresses = contactData.getEMailAddresses();
            if (mailAddresses != null && !mailAddresses.isEmpty()) {
                TextView mailText = (TextView) findViewById(R.id.mail);
                mailText.setText(mailAddresses.get(0).getAddress());
            }
            List<PhoneNumber> phoneNumbers = contactData.getPhoneNumbers();
            if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                TextView phoneText = (TextView) findViewById(R.id.phone);
                phoneText.setText(phoneNumbers.get(0).getNumber());
            }
        }

        TextView backgroundText = (TextView) findViewById(R.id.background);
        backgroundText.setText(contact.getBackground());

        ImageUtil.setImage(this, (ImageView) findViewById(R.id.icon), contact);
    }

}
