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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.fsoteam.eshop.LoginActivity;
import com.fsoteam.eshop.R;
import com.fsoteam.eshop.PaymentMethodActivity;
import com.fsoteam.eshop.SettingsActivity;
import com.fsoteam.eshop.ShippingAddressActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;
import de.hdodenhof.circleimageview.CircleImageView;
import com.fsoteam.eshop.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private LottieAnimationView animationView;
    private CircleImageView profileImage_profileFrag;
    private static final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private Button uploadImage_profileFrag;
    private TextView profileName_profileFrag;
    private TextView profileEmail_profileFrag;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private Button logoutBtn_profileFrag;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private ProfileViewModel profileViewModel;
    private CardView myOrdersCard_profileFrag, PurchaseHistoryCard_profileFrag, settingCd_profileAct, shippingAddressCard_ProfilePage, paymentMethod_ProfilePage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage_profileFrag = view.findViewById(R.id.profileImage_profileFrag);
        settingCd_profileAct = view.findViewById(R.id.settingCd_profileAct);
        uploadImage_profileFrag = view.findViewById(R.id.uploadImage_profileFrag);
        profileName_profileFrag = view.findViewById(R.id.profileName_profileFrag);
        profileEmail_profileFrag = view.findViewById(R.id.profileEmail_profileFrag);
        logoutBtn_profileFrag = view.findViewById(R.id.logoutBtn_profileFrag);
        myOrdersCard_profileFrag = view.findViewById(R.id.myOrdersCard_ProfilePage);
        PurchaseHistoryCard_profileFrag = view.findViewById(R.id.PurchaseHistoryCard_profileFrag);
        animationView = view.findViewById(R.id.animationView);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        linearLayout4 = view.findViewById(R.id.linearLayout4);
        shippingAddressCard_ProfilePage = view.findViewById(R.id.shippingAddressCard_ProfilePage);
        //paymentMethod_ProfilePage = view.findViewById(R.id.paymentMethod_ProfilePage);

        myOrdersCard_profileFrag.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_fragment, new OrdersFragment()).addToBackStack(null).commit();
        });
        shippingAddressCard_ProfilePage.setOnClickListener(v -> startActivity(new Intent(getContext(), ShippingAddressActivity.class)));

        //paymentMethod_ProfilePage.setOnClickListener(v -> startActivity(new Intent(getContext(), PaymentMethodActivity.class)));

        PurchaseHistoryCard_profileFrag.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_fragment, new PurchaseHistoryFragment()).addToBackStack(null).commit();
        });

        settingCd_profileAct.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);

        });
        hideLayout();

        uploadImage_profileFrag.setVisibility(View.GONE);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {

            if (user.getUserImage() != null) {
                profileName_profileFrag.setText(user.getUserName());
            }
            if (user.getUserEmail() != null) {
                profileEmail_profileFrag.setText(user.getUserEmail());
            }
            if (user.getUserName() != null) {
                if (isAdded()) {
                    Glide.with(ProfileFragment.this)
                            .load(user.getUserImage())
                            .placeholder(R.drawable.ic_profile)
                            .into(profileImage_profileFrag);
                }
            }

            showLayout();
        });

        profileViewModel.loadUserData();

        logoutBtn_profileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        uploadImage_profileFrag.setOnClickListener(v -> uploadImage());

        settingCd_profileAct.setOnClickListener(v -> startActivity(new Intent(getContext(), SettingsActivity.class)));

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

    private void launchGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {
            StorageReference ref = storageReference.child("Users/profiles/" + UUID.randomUUID().toString());
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
                        profileViewModel.updateUserImage(downloadUri.toString())
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                                    uploadImage_profileFrag.setVisibility(View.GONE);

                                })
                                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
}