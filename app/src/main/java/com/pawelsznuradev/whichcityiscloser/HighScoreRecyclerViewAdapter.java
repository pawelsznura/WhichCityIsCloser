package com.pawelsznuradev.whichcityiscloser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pawelsznuradev.whichcityiscloser.highscore.Highscore;

import java.util.List;

/**
 * Created by Pawel Sznura on 07/12/2021.
 */
public class HighScoreRecyclerViewAdapter extends RecyclerView.Adapter<HighScoreRecyclerViewAdapter.HighScoreViewHolder> {

    private final Context context;
    private final List<Highscore> highscores;

    public HighScoreRecyclerViewAdapter(Context context, List<Highscore> highscores) {
        super();
        this.context = context;
        this.highscores = highscores;
    }


    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.high_score_list_item, parent, false);

        HighScoreViewHolder viewHolder = new HighScoreViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position) {
        // get the highscore to display
        Highscore highscore = this.highscores.get(position);

        // update the View being held by holder with details of highscore
        View itemView = holder.itemView;

        TextView tvName = itemView.findViewById(R.id.tvName);
        tvName.setText(highscore.getName());

        TextView tvScore = itemView.findViewById(R.id.tvScore);
        tvScore.setText(String.valueOf(highscore.getScore()));

    }

    @Override
    public int getItemCount() {
        return this.highscores.size();
    }

    public class HighScoreViewHolder extends RecyclerView.ViewHolder {
        public HighScoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
