package com.lital.heroappsprojects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeroesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int NORAML_HERO = 1;
    public final static int FAVORITE_HERO = 2;
    private final Context context;
    private final OnItemClickListener onItemClickListener;
    private ArrayList<Hero> heroArrayList;

    public HeroesAdapter(Context context, ArrayList<Hero> heroArrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.heroArrayList = heroArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return FAVORITE_HERO;
        else
            return NORAML_HERO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == FAVORITE_HERO)
            return new FavoriteHeroViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_hero_item, viewGroup, false));
        else
            return new HeroViewHolder(LayoutInflater.from(context).inflate(R.layout.hero_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case FAVORITE_HERO:
                FavoriteHeroViewHolder favoriteHeroViewHolder = (FavoriteHeroViewHolder) viewHolder;
                Hero favoriteHero = getFavoriteHero();
                loadImage(favoriteHero.getImage(), favoriteHeroViewHolder.imageView);
                Picasso.get().load(favoriteHero.getImage()).placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image).into(favoriteHeroViewHolder.imageView);
                favoriteHeroViewHolder.title.setText(favoriteHero.getTitle());
                break;

            case NORAML_HERO:
                HeroViewHolder heroViewHolder = (HeroViewHolder) viewHolder;
                Hero currentHero = heroArrayList.get(i - 1);
                loadImage(currentHero.getImage(), heroViewHolder.imageView);
                heroViewHolder.title.setText(currentHero.getTitle());
                heroViewHolder.abilities.setText(currentHero.getAbilities().toString()
                        .replace("[", "").replace("]", ""));
                heroViewHolder.favoriteIndicatorImageView.setVisibility
                        (currentHero.isFavorite() ? View.VISIBLE : View.INVISIBLE);
                break;
        }
    }

    private void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url).error(R.drawable.no_image).into(imageView);
    }

    private Hero getFavoriteHero() {
        for (Hero hero : heroArrayList)
            if (hero.isFavorite()) return hero;
        heroArrayList.get(0).setFavorite(true);
        return heroArrayList.get(0);
    }

    @Override
    public int getItemCount() {
        return heroArrayList.size() + 1;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private ImageView favoriteIndicatorImageView;
        private TextView title;
        private TextView abilities;

        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.heroImageView);
            favoriteIndicatorImageView = itemView.findViewById(R.id.favoriteIndicatorImageView);
            title = itemView.findViewById(R.id.heroNameTextView);
            abilities = itemView.findViewById(R.id.heroAbilitiesTextView);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition() - 1);
        }
    }

    public class FavoriteHeroViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;

        public FavoriteHeroViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.heroImageView);
            title = itemView.findViewById(R.id.heroNameTextView);
        }
    }
}
