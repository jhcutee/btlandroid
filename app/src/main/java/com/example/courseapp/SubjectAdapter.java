package com.example.courseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.courseapp.database.model.Course;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private Context context;
    private List<Course> subjectList;

    public SubjectAdapter(Context context, List<Course> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Course subject = subjectList.get(position);
        holder.txtSubject.setText(subject.getName());
        Glide.with(context)
                .load(subject.getCourseImage())
                .into(holder.imgSubject);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("CoursePrefs", Context.MODE_PRIVATE);
                boolean isExamCompleted = sharedPreferences.getBoolean("course_" + subject.getId(), false);
                if (!isExamCompleted) {
                    Intent intentCourse = new Intent(context, quizzOfCourseActivity.class);
                    intentCourse.putExtra("course_id", subject.getId());
                    intentCourse.putExtra("subject_name", subject.getName());
                    context.startActivity(intentCourse);
                }else{
                    Intent intentCourse = new Intent(context, DetailSubjectActivity.class);
                    intentCourse.putExtra("course_id", subject.getId());
                    intentCourse.putExtra("subject_name", subject.getName());
                    context.startActivity(intentCourse);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubject;
        ImageView imgSubject;
        CardView cardView;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubject = itemView.findViewById(R.id.item_txt_subject);
            imgSubject = itemView.findViewById(R.id.item_img_subject);
            cardView = itemView.findViewById(R.id.item_card_view);
        }
    }
}
