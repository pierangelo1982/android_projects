package com.sviluppo.pierangelo.siticluniacensilombardi;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pierangelo on 09/06/16.
 */
public class TagAdapter extends BaseAdapter {

    private Bitmap bm;
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public TagAdapter(Context context,
                      ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }


    public class ViewHolder{
        TextView txtDenominazio;
        TextView txtComune;
        ImageView txtImage;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        resultp = data.get(position); // fuori dall'if altrimenti replica sempre gli stessi in listView

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.contenuto_lista, parent, false);
            holder.txtDenominazio = (TextView) convertView.findViewById(R.id.labelTitolo);
            holder.txtComune = (TextView) convertView.findViewById(R.id.labelLocation);
            holder.txtImage = (ImageView) convertView.findViewById(R.id.labelImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDenominazio.setText(resultp.get(IndiceActivity.TAG_TITOLO));
        holder.txtComune.setText(resultp.get(IndiceActivity.TAG_CITTA));

        String url = resultp.get(IndiceActivity.TAG_IMAGE);

        if (holder.txtImage != null) {
            // uso libreria Picasso x evitare problema di Lazy Loading
            Picasso.with(context).load(url).into(holder.txtImage);
        }

        return convertView;
    }



}