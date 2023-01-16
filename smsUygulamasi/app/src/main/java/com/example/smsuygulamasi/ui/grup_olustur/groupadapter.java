package com.example.smsuygulamasi.ui.grup_olustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smsuygulamasi.GrupModel;
import com.example.smsuygulamasi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class  groupadapter extends RecyclerView.Adapter<groupadapter .GroupViewHolder> {

    List<GrupModel> grupmodelList;
    public groupadapter(List<GrupModel> grupmodelList) {
        this.grupmodelList = grupmodelList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       GroupViewHolder groupViewHolder = new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupolustur_grup,parent,false));
       return  groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
     GrupModel grupModel = grupmodelList.get(position) ;
         holder.setData(grupModel);
    }

    @Override
    public int getItemCount() {
        return grupmodelList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView grupImageView;
        TextView grupnameTextView , grupDescriptiontextView;
        public GroupViewHolder(View itemViev) {
            super(itemViev);

            grupImageView = itemViev.findViewById(R.id.item_grupolustur_resim);
            grupnameTextView=itemViev.findViewById(R.id.item_grupolustur_isimedittext);
            grupDescriptiontextView = itemViev.findViewById(R.id.ite_grupolustur_aciklamaedittext);

        }
        public  void setData(GrupModel grupModel){
            grupnameTextView.setText(grupModel.getName());
            grupDescriptiontextView.setText(grupModel.getAciklama());

            if(grupModel.getResim() != null){
                Picasso.get().load(grupModel.getResim()).into(grupImageView);
            }
        }
    }
}
