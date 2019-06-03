package com.lital.heroappsprojects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";
    private ProgressBar progressBar;
    private RecyclerView herosReyclerView;
    private HeroesAdapter heroesAdapter;
    private ArrayList<Hero> heroArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        herosReyclerView = findViewById(R.id.herosReyclerView);
        NetworkClass networkClass = new NetworkClass();
        networkClass.getAllHeroes(progressBar, new NetworkClass.HeroesRequestInterface() {
            @Override
            public void onHeroesArrived(final ArrayList<Hero> heroArrayList) {
                MainActivity.this.heroArrayList = heroArrayList;
                if(heroesAdapter == null)
                    heroesAdapter = new HeroesAdapter(MainActivity.this, heroArrayList, new HeroesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            int currentHeroIndex = getFavoriteHeroIndex();
                            if(currentHeroIndex == position)return;
                            heroArrayList.get(currentHeroIndex).setFavorite(false);
                            heroArrayList.get(position).setFavorite(true);
                            if(heroesAdapter != null)
                                heroesAdapter.notifyDataSetChanged();
                        }
                    });
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
                herosReyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                herosReyclerView.setLayoutManager(layoutManager);
                herosReyclerView.setAdapter(heroesAdapter);
                Log.d(TAG, "onHeroesArrived: " + heroArrayList);

            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
            }
        });
    }

    private int getFavoriteHeroIndex(){
        for(Hero hero : heroArrayList)
            if(hero.isFavorite())return heroArrayList.indexOf(hero);
        return 0;
    }
}
