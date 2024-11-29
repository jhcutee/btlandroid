package com.example.courseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseapp.database.model.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private Context context;
    private List<Lesson> lessonList;

    public LessonAdapter(Context context, List<Lesson> lessonList) {
        this.context = context;
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        holder.txtLessonName.setText(lesson.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intentQuizz = new Intent(context, LessonActivity.class);
            intentQuizz.putExtra("lesson_id", lesson.getId());
            context.startActivity(intentQuizz);
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView txtLessonName;
        CardView cardView;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLessonName = itemView.findViewById(R.id.lesson_txt_lesson);
            cardView = itemView.findViewById(R.id.lesson_card_view);
        }
    }
}
