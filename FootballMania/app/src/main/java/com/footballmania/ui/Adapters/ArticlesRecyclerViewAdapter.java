package com.footballmania.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.dbobjects.Article;
import com.footballmania.ui.articles.ArticleActivity;
import com.footballmania.utils.Utility;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<ArticlesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Article> articlesDataStart;
    private ArrayList<Article> articlesDataCurrent;

    public ArticlesRecyclerViewAdapter(Context context, ArrayList<Article> articlesData){
        this.context = context;
        this.articlesDataStart = new ArrayList<>(articlesData);
        this.articlesDataCurrent = articlesData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        holder.layoutClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , ArticleActivity.class);
                intent.putExtra("articleId", articlesDataCurrent.get(pos).getId());
                System.out.println("pos: " + pos + ", id: " + articlesDataCurrent.get(pos).getId());
                context.startActivity(intent);
            }
        });
        holder.title.setText(articlesDataCurrent.get(position).getTitle());
        holder.author.setText("Author: " + articlesDataCurrent.get(position).getJournalistName());
        holder.date.setText(Utility.dateLongToString(articlesDataCurrent.get(position).getDateOfPublication()));
    }

    @Override
    public int getItemCount() {
        if(articlesDataCurrent!=null)  return articlesDataCurrent.size();
        return 0;
    }

    public void setArticlesData(ArrayList<Article> articlesData) {
        this.articlesDataCurrent.clear();
        this.articlesDataCurrent.addAll(articlesData);
        notifyDataSetChanged();
    }

    public ArrayList<Article> getArticlesData() {
        return articlesDataCurrent;
    }

    public ArrayList<Article> getArticlesDataStart() {
        return articlesDataStart;
    }

    public void resetArticleData(){
        this.articlesDataCurrent.clear();
        this.articlesDataCurrent.addAll(new ArrayList<>(articlesDataStart));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layoutClickable;
        TextView title;
        TextView author;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutClickable = itemView.findViewById(R.id.articleListLayout);
            title = itemView.findViewById(R.id.articleListTitle);
            author = itemView.findViewById(R.id.articleListAuthor);
            date = itemView.findViewById(R.id.articleListDate);
        }
    }
}
