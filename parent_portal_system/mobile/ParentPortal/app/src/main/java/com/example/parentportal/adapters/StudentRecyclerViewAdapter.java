package com.example.parentportal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parentportal.R;
import com.example.parentportal.model.Data;
import com.example.parentportal.model.Pic;
import com.example.parentportal.model.Student;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class StudentRecyclerViewAdapter extends RecyclerView.Adapter<StudentRecyclerViewAdapter.StudentViewHolder> {

    private ArrayList<Student> students;
    private Context context;

    public interface ClickLongClick {

        void click(Student student, int position);
        void longClick(Student student);

    }

    public ClickLongClick clickLongClick;

    public StudentRecyclerViewAdapter(Context context, ArrayList<Student> students, ClickLongClick clickLongClick) {
        this.context = context;
        this.students = students;
        this.clickLongClick = clickLongClick;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kid, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {

        Student student = students.get(i);
        String fullName = student.getFname() + " " + student.getLname();
        viewHolder.tv_item_kid.setText(fullName);
//        Pic pic = student.getPic();
//        Data data = pic.getData();
//        Drawable d = Drawable.createFromStream(new ByteArrayInputStream(data.getData()), null);

//        for (int j = 0; j < data.getData().length; j++) {
//            Log.i("the buffers", String.valueOf(data.getData()[i]));
//        }
//        }
//        byte[] bytes = new byte[data.getData().length];
//        ByteBuffer buffer = ByteBuffer.allocate(data.getData().length);
//        for (int j = 0; j < data.getData().length; j++) {
//            bytes[j] = data.getData()[j];
//            buffer.putLong(data.getData()[j]);
//        }

//        byte[] bytes = Longs.toByteArray
//        for (int j = 0; j < buffer.capacity(); j++) {
//            Log.i("The buffers", String.valueOf(buffer.get(j)));
//        }
//
//        Log.i("the buffers", String.valueOf(data.getData().length));
//        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer.array(), 0, data.getData().length);
//        viewHolder.iv_item_kid.setImageBitmap(bitmap);
//
//        viewHolder.iv_item_kid.setImageDrawable(d);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_item_kid;
        private ImageView iv_item_kid;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_kid = itemView.findViewById(R.id.tv_item_kid);
            iv_item_kid = itemView.findViewById(R.id.iv_item_kid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickLongClick.click(students.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
