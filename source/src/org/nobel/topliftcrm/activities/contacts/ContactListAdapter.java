package org.nobel.topliftcrm.activities.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.nobel.highriseapi.entities.Person;
import org.nobel.highriseapi.entities.base.EntityImage;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;
import org.nobel.topliftcrm.util.AndroidEntityImage;
import org.nobel.topliftcrm.util.LoadImageTask;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListAdapter extends EntityListAdapter<Person> implements Filterable {

    private final List<Person> originalPersonList;

    public ContactListAdapter(Context context, List<Person> entities) {
        super(context, entities);
        originalPersonList = entities;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.values = filterContacts(originalPersonList, constraint);
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                setEntities((List<Person>) results.values);
                notifyDataSetChanged();
            }
        };
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

    @Override
    protected void sort(List<Person> entities) {
        Collections.sort(entities, new Comparator<Person>() {

            @Override
            public int compare(Person lhs, Person rhs) {
                String lhsLastName = lhs.getLastName();
                String rhsLastName = rhs.getLastName();
                if (lhsLastName != null) {
                    return lhsLastName.compareTo(rhsLastName);
                }
                else if(rhsLastName != null) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    private boolean containsStringCaseInsensitive(String word, CharSequence value) {
        return word != null
                && value != null
                && word.toUpperCase(Locale.getDefault()).contains(
                        String.valueOf(value).toUpperCase(Locale.getDefault()));
    }

    private List<Person> filterContacts(List<Person> persons, CharSequence constraint) {
        List<Person> filteredPersons = new ArrayList<Person>();
        for (Person person : persons) {

            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String companyName = person.getCompanyName();

            if (containsStringCaseInsensitive(firstName, constraint)
                    || containsStringCaseInsensitive(lastName, constraint)
                    || containsStringCaseInsensitive(companyName, constraint)) {
                filteredPersons.add(person);
            }
        }

        return filteredPersons;
    }
}
