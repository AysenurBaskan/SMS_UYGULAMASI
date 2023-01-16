package com.example.smsuygulamasi.ui.grup_olustur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smsuygulamasi.GrupModel;
import com.example.smsuygulamasi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class grupolustur extends Fragment {

    Button butongrupolustur;
    EditText grupadigrupolustur,grupaciklamagrupolustur;
    RecyclerView recyclerViewgrupolustur;
    ImageView grupfotogrupolustur;

    Uri dosyayolu;

    FirebaseAuth mAuth;
    FirebaseStorage mStorage;
    FirebaseFirestore mFireStore;

    ArrayList<GrupModel> grupModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grupolustur, container, false);

        grupadigrupolustur = view.findViewById(R.id.grupolustur_grupadiedittext);
        grupaciklamagrupolustur = view.findViewById(R.id.grupolustur_aciklamaedittext);
        butongrupolustur =view.findViewById(R.id.grupolustur_grupolusturbuton);
        grupfotogrupolustur =view.findViewById(R.id.grupolustur_grupsimgesi);
        recyclerViewgrupolustur=view.findViewById(R.id.grupolustur_recyclerview);

        grupModelArrayList = new ArrayList<>();

        mAuth=  FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mFireStore= FirebaseFirestore.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult() ,
                result ->  {
                    if(result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        dosyayolu = data.getData();
                        grupfotogrupolustur.setImageURI(dosyayolu);
                    }
                }
        );


        grupfotogrupolustur.setOnClickListener(v -> {
            Intent intent =new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });

        butongrupolustur.setOnClickListener(view1 -> {
            String grupadi = grupadigrupolustur.getText().toString();
            String grupaciklama = grupaciklamagrupolustur.getText().toString();

            if (grupadi.isEmpty()) {
                return;
            }
            if (grupaciklama.isEmpty()) {
                return;
            }
            if (dosyayolu != null) {
                StorageReference storageReference = mStorage.getReference().child("resimler/"  +  UUID.randomUUID().toString());
                storageReference.putFile(dosyayolu).addOnSuccessListener(taskSnapshot  ->  {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri ->  {
                        String downloadUrl = uri.toString();

                        GrupOlustur(grupadi, grupaciklama, downloadUrl);
                    });
                });
                return;
            } else {
                GrupOlustur(grupadi, grupaciklama, null);
            }
        });
        FetchGroups();
        return  view;
    }
private void GrupOlustur(String name, String description , String image) {
        String userId = mAuth.getCurrentUser().getUid();

        mFireStore.collection("/userData/" +  userId  +   "/"  +  "groups").add(new HashMap<String ,  Object>(){{
            put("name" ,name);
            put("description" , description);
            put("image",image);
            put("numbers", new ArrayList<String>());
        }}).addOnSuccessListener(documentReference  ->  {
            Toast.makeText(getContext(), "Grup Oluştu" , Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                GrupModel grupModel = new GrupModel(name,description,image,(List<String>) documentSnapshot.get("numbers"), documentSnapshot.getId());
                grupModelArrayList.add(grupModel);
                recyclerViewgrupolustur.getAdapter().notifyItemInserted(grupModelArrayList.size()-1);
            });

        }).addOnFailureListener( e ->  {
            Toast.makeText(getContext(), "Grup Oluşmadı" , Toast.LENGTH_SHORT).show();
        });
    }

    private void FetchGroups(){
        String userId = mAuth.getCurrentUser().getUid();

        mFireStore.collection("/userdata/ " + userId + "/" + "groups").get().addOnSuccessListener(queryDocumentSnapshots ->  {
            grupModelArrayList.clear();
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                GrupModel grupModel= new GrupModel(documentSnapshot.getString("name"), documentSnapshot.getString("description"),documentSnapshot.getString("image") ,(List<String>) documentSnapshot.get("numbers"),documentSnapshot.getId());
                grupModelArrayList.add(grupModel);
            }

            recyclerViewgrupolustur.setAdapter(new groupadapter(grupModelArrayList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerViewgrupolustur.setLayoutManager(linearLayoutManager);


        });
    }
}







