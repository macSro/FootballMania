package com.footballmania.ui.articles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.footballmania.R;
import com.footballmania.database.dbobjects.Article;
import com.footballmania.utils.Utility;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static com.footballmania.database.FootballManiaContract.getDbHelperInstance;

public class ArticleActivity extends AppCompatActivity {

    private int ARG_ARTICLE_ID;

    private TextView title;
    private TextView author;
    private TextView category;
    private TextView date;
    private TextView content;
    private TextView tags;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_article);

        ARG_ARTICLE_ID = getIntent().getIntExtra("articleId",-1);
        System.out.println(ARG_ARTICLE_ID);

        article = getDbHelperInstance(getApplicationContext()).getArticle(ARG_ARTICLE_ID);

        title = findViewById(R.id.articleTitle);
        author = findViewById(R.id.articleAuthor);
        category = findViewById(R.id.articleCategory);
        date = findViewById(R.id.articleDate);
        content = findViewById(R.id.articleContent);
        tags = findViewById(R.id.articleTags);

        title.setText(article.getTitle());
        author.setText(article.getJournalistName());
        category.setText(article.getCategory());
        date.setText(Utility.dateLongToString(article.getDateOfPublication()));
        content.setText(article.getContent());
        tags.setText(article.getTagsToString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            content.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

    }
}
