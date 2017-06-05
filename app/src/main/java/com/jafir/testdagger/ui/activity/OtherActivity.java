package com.jafir.testdagger.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jafir.testdagger.R;
import com.jafir.testdagger.app.AppContext;
import com.jafir.testdagger.component.DaggerOtherComponent;
import com.jafir.testdagger.component.OtherComponent;
import com.jafir.testdagger.model.User;
import com.jafir.testdagger.utils.ReyclerViewTouchAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class OtherActivity extends AppCompatActivity {
    @Inject
    User user;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Context context = AppContext.get().getAppComponent().getContext();
        inject();

        Log.d("debug", "OtherActivity user:" + user);
        Log.d("debug", "OtherActivity user:" + AppContext.get().getAppComponent().getUser());
        Log.d("debug", "OtherActivity getAppComponent:" + AppContext.get().getAppComponent());


        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);


        for (int i = 0; i < 100; i++) {
            data.add(i + "");
        }

        MyAdapter adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();

        ItemTouchHelper.Callback callback = new MyTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }

    List<String> data = new ArrayList<>();

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements ReyclerViewTouchAdapter {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.text.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onItemDragDone(int fromposition, int toposition) {
            Collections.swap(data,fromposition,toposition);
            notifyItemMoved(fromposition, toposition);
        }

        @Override
        public void onItemSwapDone(int position) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textv);

        }
    }

    private void inject() {
        Log.d("debug", "MainActivity getAppComponent:" + AppContext.get().getAppComponent());
        OtherComponent maincomponent = DaggerOtherComponent.builder()
                .appComponent(AppContext.get().getAppComponent())
                .build();
        maincomponent.inject(this);

    }

    class MyTouchHelper extends ItemTouchHelper.Callback {


        ReyclerViewTouchAdapter adapter;


        public MyTouchHelper(ReyclerViewTouchAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            /**
             * 定义上下拖拽的flag
             */
            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

            /**
             * 定义左右滑动的flag
             */
            int swapFlag = ItemTouchHelper.START | ItemTouchHelper.END;


            /**
             * 应用
             * !!!注意这里不是makeFlag 而是makeMovementFlags
             */
            return makeMovementFlags(dragFlag, swapFlag);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            /**
             * 回调接口
             */
            adapter.onItemDragDone(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.onItemSwapDone(viewHolder.getAdapterPosition());
        }
    }


}
