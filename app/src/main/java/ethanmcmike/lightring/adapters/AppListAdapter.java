package ethanmcmike.lightring.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ethanmcmike.lightring.R;
import ethanmcmike.lightring.models.AppModel;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppViewHolder> {

    private Context mContext;
    private ArrayList<AppModel> apps;

    public AppListAdapter(Context context, ArrayList<AppModel> apps){
        mContext = context;
        this.apps = apps;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int pos) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_card, viewGroup, false);
        AppViewHolder holder = new AppViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int i) {
        holder.update(apps.get(i));
        holder.setItemClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, View.OnLongClickListener {

        ItemClickListener itemClickListener;

        CardView card;
        ImageView icon;
        TextView title, description;

        public AppViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            initViews();
        }

        private void initViews(){
            card = itemView.findViewById(R.id.appCard);
            icon = itemView.findViewById(R.id.appCardIcon);
            title = itemView.findViewById(R.id.appCardTitle);
            description = itemView.findViewById(R.id.appCardDescription);
        }

        private void update(AppModel app){

            if(app.iconRes != 0)
                icon.setImageResource(app.iconRes);

            title.setText(app.title);
            description.setText(app.description);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
//            itemClickListener.onClick(v, getAdapterPosition());
//            card.setCardElevation(5);
            return true;
        }
    }

    ItemClickListener itemClickListener = new ItemClickListener(){
        @Override
        public void onClick(View view, int pos) {
            Intent intent = new Intent(mContext, apps.get(pos).activity);
            mContext.startActivity(intent);
        }
    };

    private interface ItemClickListener{
        void onClick(View view, int pos);
    }
}
