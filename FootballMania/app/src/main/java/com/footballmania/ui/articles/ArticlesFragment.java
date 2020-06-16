package com.footballmania.ui.articles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.footballmania.MainActivity;
import com.footballmania.R;
import com.footballmania.database.dbobjects.Article;
import com.footballmania.ui.Adapters.ArticlesRecyclerViewAdapter;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.footballmania.database.FootballManiaContract.getDbHelperInstance;

public class ArticlesFragment extends Fragment {

    private TextView search;
    private EditText input;
    private TextView result;
    private TextView removeFilter;

    private TextView noArticles1;
    private TextView noArticles2;
    private TextView noArticles3;
    private TextView noArticles4;
    private TextView noArticles5;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;

    private ArticlesRecyclerViewAdapter adapter1;
    private ArticlesRecyclerViewAdapter adapter2;
    private ArticlesRecyclerViewAdapter adapter3;
    private ArticlesRecyclerViewAdapter adapter4;
    private ArticlesRecyclerViewAdapter adapter5;

    private ArrayList<Article> articleList = new ArrayList<>();
    private ArrayList<Article> articleListTransfers = new ArrayList<>();
    private ArrayList<Article> articleListCompetitions = new ArrayList<>();
    private ArrayList<Article> articleListPlayers = new ArrayList<>();
    private ArrayList<Article> articleListInjuries = new ArrayList<>();
    private ArrayList<Article> articleListAwards = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_articles, container, false);

        search = root.findViewById(R.id.searchTextView);
        input = root.findViewById(R.id.searchEditText);
        result = root.findViewById(R.id.searchResult);
        removeFilter = root.findViewById(R.id.removeFilter);

        noArticles1 = root.findViewById(R.id.textView17);
        noArticles2 = root.findViewById(R.id.textView18);
        noArticles3 = root.findViewById(R.id.textView19);
        noArticles4 = root.findViewById(R.id.textView20);
        noArticles5 = root.findViewById(R.id.textView21);

        result.setVisibility(View.GONE);
        removeFilter.setVisibility(View.GONE);

        noArticles1.setVisibility(View.GONE);
        noArticles2.setVisibility(View.GONE);
        noArticles3.setVisibility(View.GONE);
        noArticles4.setVisibility(View.GONE);
        noArticles5.setVisibility(View.GONE);

        recyclerView1 = root.findViewById(R.id.articlesTransfers);
        recyclerView2 = root.findViewById(R.id.articlesCompetitions);
        recyclerView3 = root.findViewById(R.id.articlesPlayers);
        recyclerView4 = root.findViewById(R.id.articlesInjuries);
        recyclerView5 = root.findViewById(R.id.articlesAwards);

        articleList = getDbHelperInstance(getActivity()).getArticleList();

        categorizeArticles();

        RecyclerView[] recyclerViews = {recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5};

        for(RecyclerView recyclerView : recyclerViews){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
            itemDecorator.setDrawable(getDrawable(getContext(), R.drawable.divider));
            recyclerView.addItemDecoration(itemDecorator);
        }

        adapter1 = new ArticlesRecyclerViewAdapter(getContext(), articleListTransfers);
        recyclerView1.setAdapter(adapter1);

        adapter2 = new ArticlesRecyclerViewAdapter(getContext(), articleListCompetitions);
        recyclerView2.setAdapter(adapter2);

        adapter3 = new ArticlesRecyclerViewAdapter(getContext(), articleListPlayers);
        recyclerView3.setAdapter(adapter3);

        adapter4 = new ArticlesRecyclerViewAdapter(getContext(), articleListInjuries);
        recyclerView4.setAdapter(adapter4);

        adapter5 = new ArticlesRecyclerViewAdapter(getContext(), articleListAwards);
        recyclerView5.setAdapter(adapter5);

        final ArticlesRecyclerViewAdapter[] adapters = {adapter1,adapter2,adapter3,adapter4,adapter5};

        checkEmptyCategories(adapters);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = input.getText().toString();
                if(!tag.isEmpty()){
                    result.setText("Articles tagged with: " + tag);
                    result.setVisibility(View.VISIBLE);
                    removeFilter.setVisibility(View.VISIBLE);
                    filterArticles(tag,adapters);
                    checkEmptyCategories(adapters);
                }
            }
        });

        removeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
                result.setVisibility(View.GONE);
                removeFilter.setVisibility(View.GONE);
                removeFilters(adapters);
                checkEmptyCategories(adapters);
            }
        });

        return root;
    }

    private void checkEmptyCategories(ArticlesRecyclerViewAdapter[] adapters){
        if(adapters[0].getArticlesData().isEmpty()) noArticles1.setVisibility(View.VISIBLE);
        else noArticles1.setVisibility(View.GONE);
        if(adapters[1].getArticlesData().isEmpty()) noArticles2.setVisibility(View.VISIBLE);
        else noArticles2.setVisibility(View.GONE);
        if(adapters[2].getArticlesData().isEmpty()) noArticles3.setVisibility(View.VISIBLE);
        else noArticles3.setVisibility(View.GONE);
        if(adapters[3].getArticlesData().isEmpty()) noArticles4.setVisibility(View.VISIBLE);
        else noArticles4.setVisibility(View.GONE);
        if(adapters[4].getArticlesData().isEmpty()) noArticles5.setVisibility(View.VISIBLE);
        else noArticles5.setVisibility(View.GONE);
    }

    private void categorizeArticles(){
        for(Article a : articleList){
            String cat = a.getCategory();
            if(cat.equals("transfers")) articleListTransfers.add(a);
            else if(cat.equals("competitions")) articleListCompetitions.add(a);
            else if(cat.equals("players")) articleListPlayers.add(a);
            else if(cat.equals("injuries")) articleListInjuries.add(a);
            else if(cat.equals("awards")) articleListAwards.add(a);
        }
    }

    private void filterArticles(String tag, ArticlesRecyclerViewAdapter[] adapters){
        ArrayList<Article> temp = new ArrayList<>();
        for(ArticlesRecyclerViewAdapter adapter : adapters){
            for (Article a : adapter.getArticlesDataStart()) {
                if (a.isTagged(tag)) temp.add(a);
            }
            adapter.setArticlesData(temp);
            temp.clear();
        }
    }

    private void removeFilters(ArticlesRecyclerViewAdapter[] adapters){
        for(ArticlesRecyclerViewAdapter adapter : adapters){
            adapter.resetArticleData();
        }
        Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_articles);
    }
}