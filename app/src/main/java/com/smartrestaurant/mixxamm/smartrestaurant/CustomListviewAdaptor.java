package com.smartrestaurant.mixxamm.smartrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.smartrestaurant.mixxamm.smartrestaurent.R;

public class CustomListviewAdaptor extends BaseAdapter implements ListAdapter {

    private String[] list;
    private Context context;



    public CustomListviewAdaptor(String[] list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int pos) {
        return list[pos];
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_menu_listview, null);
        }

        //Textviews
        TextView listItemText = view.findViewById(R.id.txtProduct);
        listItemText.setText(list[position]);
        final TextView txtAantal = view.findViewById(R.id.aantal);

        //Buttons
        Button deleteBtn = view.findViewById(R.id.btnVerwijderen);
        deleteBtn.setText("-");
        Button addBtn = view.findViewById(R.id.btnToevoegen);
        addBtn.setText("+");

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intAantal = Integer.parseInt(txtAantal.getText().toString());
                if (intAantal == 0) {
                    //Niets, kan niet lager dan 0
                }else {
                    intAantal--;
                    txtAantal.setText(String.valueOf(intAantal));
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intAantal = Integer.parseInt(txtAantal.getText().toString());
                intAantal++;
                txtAantal.setText(String.valueOf(intAantal));
            }
        });

        return view;
    }
}


