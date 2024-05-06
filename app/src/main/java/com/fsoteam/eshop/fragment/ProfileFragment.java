package com.fsoteam.eshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.PaymentMethodActivity;
import com.fsoteam.eshop.SettingsActivity;
import com.fsoteam.eshop.ShipingAddressActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private LottieAnimationView animationView;
    private CircleImageView profileImage_profileFrag;
    private static final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private Button uploadImage_profileFrag;
    private TextView profileName_profileFrag;
    private TextView profileEmail_profileFrag;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage_profileFrag = view.findViewById(R.id.profileImage_profileFrag);
        CardView settingCd_profileFrag = view.findViewById(R.id.settingCd_profileFrag);
        uploadImage_profileFrag = view.findViewById(R.id.uploadImage_profileFrag);
        profileName_profileFrag = view.findViewById(R.id.profileName_profileFrag);
        profileEmail_profileFrag = view.findViewById(R.id.profileEmail_profileFrag);
        animationView = view.findViewById(R.id.animationView);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        linearLayout4 = view.findViewById(R.id.linearLayout4);
        CardView shippingAddressCard_ProfilePage = view.findViewById(R.id.shippingAddressCard_ProfilePage);
        CardView paymentMethod_ProfilePage = view.findViewById(R.id.paymentMethod_ProfilePage);

        shippingAddressCard_ProfilePage.setOnClickListener(v -> startActivity(new Intent(getContext(), ShipingAddressActivity.class)));

        paymentMethod_ProfilePage.setOnClickListener(v -> startActivity(new Intent(getContext(), PaymentMethodActivity.class)));

        hideLayout();

        uploadImage_profileFrag.setVisibility(View.GONE);

        getUserData();

        uploadImage_profileFrag.setOnClickListener(v -> uploadImage());

        settingCd_profileFrag.setOnClickListener(v -> startActivity(new Intent(getContext(), SettingsActivity.class)));

        profileImage_profileFrag.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), profileImage_profileFrag);
            popupMenu.getMenuInflater().inflate(R.menu.profile_photo_storage, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.galleryMenu) {
                    launchGallery();
                    return true;
                }
                if(item.getItemId() == R.id.cameraMenu) {
                    uploadImage();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        return view;
    }

    private void hideLayout() {
        animationView.playAnimation();
        animationView.loop(true);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);
        linearLayout4.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
    }

    private void showLayout() {
        animationView.pauseAnimation();
        animationView.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout3.setVisibility(View.VISIBLE);
        linearLayout4.setVisibility(View.VISIBLE);
    }

    private void getUserData() {
        db.child("Users").child(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userImage = dataSnapshot.child("userImage").getValue(String.class);
                        String userName = dataSnapshot.child("userName").getValue(String.class);
                        String userEmail = dataSnapshot.child("userEmail").getValue(String.class);

                        if (userName != null) {
                            profileName_profileFrag.setText(userName);
                        }
                        if (userEmail != null) {
                            profileEmail_profileFrag.setText(userEmail);
                        }
                        if (userImage != null) {
                            Glide.with(ProfileFragment.this)
                                    .load(userImage)
                                    .placeholder(R.drawable.ic_profile)
                                    .into(profileImage_profileFrag);
                        }

                        showLayout();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Failed to load user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void launchGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {
            StorageReference ref = storageReference.child("profile_Image/" + UUID.randomUUID().toString());
            UploadTask uploadTask = ref.putFile(filePath);

            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (downloadUri != null) {
                        addUploadRecordToDb(downloadUri.toString());
                    }
                } else {
                    Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(getContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) {
                return;
            }

            filePath = data.getData();
            try {
                android.graphics.Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                profileImage_profileFrag.setImageBitmap(bitmap);
                uploadImage_profileFrag.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addUploadRecordToDb(String uri) {
        db.child("Users").child(firebaseAuth.getUid()).child("userImage")
                .setValue(uri)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}