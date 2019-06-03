package com.lital.heroappsprojects;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NetworkClass {
    int requestsCounter = 0;

    public void getAllHeroes(final ProgressBar progressBar,
                             final HeroesRequestInterface listener) {
        requestsCounter++;
        RequestTask heroesReqTask = new RequestTask(progressBar,
                new RequestTask.RequestListener() {
                    @Override
                    public void onRequestDone(String result) {
                        if(result == null){
                            listener.onError();
                            return;
                        }
                        requestsCounter = 0;
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<Hero>>(){}.getType();
                        ArrayList<Hero> heroArrayList = gson.fromJson(result, listType);
                        listener.onHeroesArrived(heroArrayList);
                    }

                    @Override
                    public void onError(String msg) {
                        if(requestsCounter < 2)
                            getAllHeroes(progressBar, listener);
                        else{
                            requestsCounter = 0;
                            listener.onError();
                        }
                    }
                });
        heroesReqTask.execute("https://heroapps.co.il/employee-tests/android/androidexam.json");
    }

    public interface HeroesRequestInterface{
        void onHeroesArrived(ArrayList<Hero> heroArrayList);
        void onError();
    }
}
