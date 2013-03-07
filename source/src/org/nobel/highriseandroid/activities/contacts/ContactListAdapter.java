package org.nobel.highriseandroid.activities.contacts;

import java.util.List;

import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.activities.base.EntityListAdapter;
import org.nobel.highriseandroid.util.AndroidEntityImage;
import org.nobel.highriseandroid.util.LoadImageTask;
import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.entities.base.EntityImage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListAdapter extends EntityListAdapter<Person> {

    public ContactListAdapter(Context context, List<Person> entities) {
        super(context, entities);
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.contact_list_item;
    }

    @Override
    public void renderListItem(View contactListItemView, Person contact) {

        TextView nameText = (TextView) contactListItemView.findViewById(R.id.name);
        nameText.setText(contact.getFirstName() + " " + contact.getLastName());

        TextView positionText = (TextView) contactListItemView.findViewById(R.id.position);
        positionText.setText(contact.getTitle());

        TextView companyText = (TextView) contactListItemView.findViewById(R.id.company);
        companyText.setText(contact.getCompanyName());

        ImageView userIcon = (ImageView) contactListItemView.findViewById(R.id.user_icon);

        EntityImage image = contact.getImage();

        if (image != null) {
            userIcon.setImageDrawable(((AndroidEntityImage) contact.getImage()).getDrawable());
        }
        else {
            new LoadImageTask((Activity) context).execute(userIcon, contact.getAvatarUrl(), contact);
        }

    }
}
