import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;

import com.example.scopoday.Contactdata;
import com.example.scopoday.R;

import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Contactdata> {

    private static final String TAG = "ContactListAdapter";

    private Context mContext;
    int mResource;

    public ContactListAdapter(Context context, int resource, ArrayList<Contactdata> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        Date birthdate = getItem(position).getBirthdayDate();

        Contactdata contact = new Contactdata();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvBirthdate = (TextView) convertView.findViewById(R.id.textViewDate);

        tvName.setText(name);
        tvBirthdate.setText(birthdate.toString());

        return convertView;

    }
}
