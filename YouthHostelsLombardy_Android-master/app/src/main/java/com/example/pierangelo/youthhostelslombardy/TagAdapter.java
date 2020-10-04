package com.example.pierangelo.youthhostelslombardy;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pierangelo on 01/08/15.
 */

public class TagAdapter extends BaseAdapter {


    private Bitmap bm;
    // Declare Variables
    Context context;

    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public TagAdapter(Context context,
                      ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    public Context getContext() {
        return context;
    }


    public class ViewHolder{
        TextView txtDenominazio;
        TextView txtComune;
        TextView txtIndirizzo;
        TextView txtDistanza;
        TextView txtTemp;
        ImageView txtImage;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        resultp = data.get(position);
        if (convertView == null) {
            Log.v("posizionami", ""+position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contenuto_lista, null);
            holder = new ViewHolder();
            /*LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            resultp = data.get(position);
            //convertView = inflater.inflate(R.layout.contenuto_lista, parent, false);
            convertView = inflater.inflate(R.layout.contenuto_lista, null);*/
            holder.txtDenominazio = (TextView) convertView.findViewById(R.id.labelDenominazione);
            holder.txtComune = (TextView) convertView.findViewById(R.id.labelComune);
            holder.txtIndirizzo = (TextView) convertView.findViewById(R.id.labelIndirizzo);
            holder.txtDistanza = (TextView) convertView.findViewById(R.id.labelDistanza);
            holder.txtTemp = (TextView) convertView.findViewById(R.id.labelTemperatura);
            holder.txtImage = (ImageView) convertView.findViewById(R.id.list_image);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDenominazio.setText(resultp.get(IndiceActivity.TAG_DENOMINAZIO));
        holder.txtComune.setText(resultp.get(IndiceActivity.TAG_COMUNE));
        holder.txtIndirizzo.setText(resultp.get(IndiceActivity.TAG_INDIRIZZO));
        holder.txtTemp.setText(resultp.get(IndiceActivity.TAG_TEMP) + "°C");
        holder.txtDistanza.setText(resultp.get(IndiceActivity.TAG_DISTANZA) + " km.");

        String url = resultp.get(IndiceActivity.TAG_ICON);
        // Loader image - will be shown before loading image
        int loader = R.drawable.powered_by_google_dark;

        ImageLoader imgLoader = new ImageLoader(context.getApplicationContext());
        imgLoader.DisplayImage(url, loader, holder.txtImage);

        return convertView;

    }


}
