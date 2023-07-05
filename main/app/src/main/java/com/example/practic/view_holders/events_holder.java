package com.example.practic.view_holders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.zachetnoe_zadanie.Interfaces.ItemClickListener;
import com.example.zachetnoe_zadanie.R;

public class events_holder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView event_name, date, direction, organizer, place, id;
    public ItemClickListener listener;

    public events_holder(View itemView) {
        super(itemView);
        event_name = itemView.findViewById(R.id.eventNameFieldV);
        date = itemView.findViewById(R.id.dateField1);
        direction = itemView.findViewById(R.id.directionField1);
        organizer = itemView.findViewById(R.id.organizerField1);
        place = itemView.findViewById(R.id.placeField1);
        id = itemView.findViewById(R.id.idField1);
    }

    public void setItemClickListener(ItemClickListener listener){this.listener = listener; }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
