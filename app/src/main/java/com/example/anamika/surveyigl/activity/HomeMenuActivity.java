package com.example.anamika.surveyigl.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.anamika.surveyigl.R;

import java.util.ArrayList;
import java.util.List;

public class HomeMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<MenuData> dataList;
    private HomeCardAdapter gridCardAdapter;
    CardView surveyCard,cameraCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

       /* surveyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeMenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });*/

        dataList = new ArrayList<>();
        gridCardAdapter = new HomeCardAdapter(this, dataList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(gridCardAdapter);

        gridCardAdapter.setOnItemClickListener(new HomeCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(HomeMenuActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        prepareCards();
        try {
            Glide.with(this).load(R.drawable.image).into((ImageView) findViewById(R.id.card_image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void prepareCards() {
        int[] covers = new int[]{
                R.drawable.survey_form,
                R.drawable.camera,
                /* R.drawable.image,
                 R.drawable.image,
                 R.drawable.image,
                 R.drawable.image,
                 R.drawable.image,
                 R.drawable.image,*/
        };

        MenuData a = new MenuData("In Time", "h", covers[0]);
        dataList.add(a);

        a = new MenuData("Out Time", "j", covers[1]);
        dataList.add(a);

/*        a = new Data("Honeymoon", "er", covers[4]);
        dataList.add(a);

        a = new Data("I Need a Doctor", "sd", covers[5]);
        dataList.add(a);

        a = new Data("Loud", "fg", covers[6]);
        dataList.add(a);

        a = new Data("Legend", "c", covers[7]);
        dataList.add(a);

        a = new Data("Hello", "sd", covers[8]);
        dataList.add(a);

        a = new Data("Greatest Hits", "sx", covers[9]);
        dataList.add(a);*/

        gridCardAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    }

