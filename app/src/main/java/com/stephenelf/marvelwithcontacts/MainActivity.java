/*
 * Copyright 2019 stephenelf@gmail.com(EB). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stephenelf.marvelwithcontacts;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.stephenelf.marvelwithcontacts.repositories.Repository;
import com.stephenelf.marvelwithcontacts.simpleinterviewtestapp.R;
import com.stephenelf.marvelwithcontacts.util.GlideApp;
import com.stephenelf.marvelwithcontacts.util.People;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @BindView(R.id.people_view)
    RecyclerView peopleView;

    @BindView(R.id.logo)
    View logo;

    @BindView(R.id.sub_title)
    View subTitle;

    @Inject
    Repository repository;

    private PeopleAdapter peopleAdapter;

    private final String TEST_RULE="rochedc.accentureanalytics.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApplication.getInstance().getNetComponent().inject(this);
        checkPermissions();
        setupView();
        startAnimation();
    }

    private void checkPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else
            fillData();
    }


    private void setupView() {
        peopleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        peopleAdapter = new PeopleAdapter();
        peopleView.setAdapter(peopleAdapter);
    }

    private void fillData() {
        repository.getPeople().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<People>>() {
                    @Override
                    public void onSuccess(List<People> people) {
                        peopleAdapter.setPeopleList(people);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "", e);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                fillData();
            } else {
                Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        peopleView.setY(displayMetrics.heightPixels);
        SpringAnimation peopleAnim = new SpringAnimation(peopleView, DynamicAnimation.TRANSLATION_Y,
                (logo.getHeight() + subTitle.getHeight()));
        VelocityTracker vt = VelocityTracker.obtain();
        // Compute velocity in the unit pixel/second
        vt.computeCurrentVelocity(5);
        peopleAnim.setStartVelocity(vt.getYVelocity());

        peopleAnim.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);

        logo.setAlpha(0);
        subTitle.setAlpha(0);
        logo.animate().alpha(1).setInterpolator(new DecelerateInterpolator()).setDuration(2000)
                .start();

        subTitle.animate().alpha(1).setInterpolator(new AccelerateInterpolator())
                .setDuration(1000).setStartDelay(2000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        peopleAnim.start();
                    }
                })
                .start();
    }


    private class PeopleAdapter extends RecyclerView.Adapter<PeopleViewHolder> {

        private List<People> peopleList = new ArrayList<>();
        private LayoutInflater inflater = getLayoutInflater();


        public void setPeopleList(List<People> peopleList) {
            this.peopleList = peopleList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PeopleViewHolder(inflater.inflate(R.layout.recycler_view_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
            holder.onBind(peopleList.get(position).thumbnail, peopleList.get(position).name,
                    peopleList.get(position).isChecked,
                    peopleList.get(position).phone);
            holder.itemView.setOnClickListener(v -> {
                if (!peopleList.get(position).isChecked) {
                    peopleList.get(position).isChecked = true;
                    holder.check.setVisibility(View.VISIBLE);
                } else {
                    peopleList.get(position).isChecked = false;
                    holder.check.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return peopleList.size();
        }
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.people_icon)
        ImageView icon;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.checkbox)
        ImageView check;

        @BindView(R.id.phone)
        TextView phoneNumber;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void onBind(Uri thumbnail, String text, boolean isCheched, String phone) {
            name.setText(text);

            GlideApp.with(itemView.getContext())
                    .load(thumbnail)
                    .placeholder(R.drawable.ic_person_outline_24dp)
                    .centerCrop()
                    .apply(new RequestOptions().transform(new CircleCrop()))
                    .into(icon);

            if (isCheched)
                check.setVisibility(View.VISIBLE);
            else check.setVisibility(View.GONE);

            if (phone != null) {
                phoneNumber.setText(phone);
                phoneNumber.setVisibility(View.VISIBLE);
            } else
                phoneNumber.setVisibility(View.GONE);
        }
    }
}
