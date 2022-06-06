package adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class ParkListAdapter extends ArrayAdapter {
    private Context context;
    private String[] nationalParkNames;
    private int no = 1;

    public ParkListAdapter(@NonNull Context context, int resource, String[] nationalParkNames) {
        super(context, resource);
        this.context = context;
        this.nationalParkNames = nationalParkNames;
    }

    @Override
    public int getCount() {
        return nationalParkNames.length;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        String nationalParkName = nationalParkNames[position];
        return String.format(Locale.ENGLISH,"%d. %s", no++, nationalParkName);
    }
}
