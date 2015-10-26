package com.example.lenovo.testhorizontalrecycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inflater=LayoutInflater.from(MainActivity.this);
        ArrayList<String> itemList=new ArrayList<>();
        for (int i=0;i<3;i++){
            itemList.add("item "+i);
        }

        RecyclerView recycleView= (RecyclerView) findViewById(R.id.id_recyclerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),3);
        recycleView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
        recycleView.setLayoutManager(gridLayoutManager);
        RecycleAdapter recycleAdapter=new RecycleAdapter(itemList);
        recycleView.setAdapter(recycleAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    class RecycleAdapter extends RecyclerView.Adapter<ViewHolder>{


        ArrayList<String> list;

        public RecycleAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.item,parent,false);
            MViewHolder mViewHolder=new MViewHolder(view);
            mViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"hhahah ",Toast.LENGTH_SHORT).show();
                }
            });



            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MViewHolder mViewHolder= (MViewHolder) holder;
            String item=list.get(position);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    static class MViewHolder extends RecyclerView.ViewHolder{
        View view;



        public MViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
        }
    }

     class DividerGridItemDecoration extends RecyclerView.ItemDecoration{
        private  final int[] ATTRS = new int[] { android.R.attr.listDivider };
        private Drawable mDivider;

        public DividerGridItemDecoration(Context context)
        {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);

            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
        {

          //  drawHorizontal(c, parent);
            int childCount=parent.getChildCount();

               for (int i=0;i<childCount;i++){
                   if (i==1){
                       Log.v("PLU","-----I==1 draw vertical");
                       drawVertical(c, parent);

                   }
               }

           // }

        }

        private int getSpanCount(RecyclerView parent)
        {
            // 列数
            int spanCount = -1;
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {

                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                spanCount = ((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount();
            }
            return spanCount;
        }

        /*public void drawHorizontal(Canvas c, RecyclerView parent)
        {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++)
            {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getLeft() - params.leftMargin;
                final int right = child.getRight() + params.rightMargin
                        + mDivider.getIntrinsicWidth();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
*/
        public void drawVertical(Canvas c, RecyclerView parent)
        {
           /* final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++)
            {*/
                final View child = parent.getChildAt(1);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getTop() - params.topMargin;
                final int bottom = child.getBottom() + params.bottomMargin;
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);

            final int l_left=child.getLeft()+params.leftMargin;
            final int l_top=child.getTop()-params.topMargin;
            final int l_right=l_left+dp2px(MainActivity.this.getApplicationContext(),1);
            final int l_bottom=child.getBottom()+params.bottomMargin;
           // c.drawColor(Color.BLACK);
            mDivider.draw(c);
            mDivider.setBounds(l_left, l_top, l_right, l_bottom);

            mDivider.draw(c);
           // }
        }

        private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                    int childCount)
        {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                int orientation = ((StaggeredGridLayoutManager) layoutManager)
                        .getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL)
                {
                    if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                    {
                        return true;
                    }
                } else
                {
                    childCount = childCount - childCount % spanCount;
                    if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                        return true;
                }
            }
            return false;
        }

        private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                                  int childCount)
        {
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager)
            {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                    return true;
            } else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                int orientation = ((StaggeredGridLayoutManager) layoutManager)
                        .getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == StaggeredGridLayoutManager.VERTICAL)
                {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一行，则不需要绘制底部
                    if (pos >= childCount)
                        return true;
                } else
                // StaggeredGridLayoutManager 且横向滚动
                {
                    // 如果是最后一行，则不需要绘制底部
                    if ((pos + 1) % spanCount == 0)
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition,
                                   RecyclerView parent)
        {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();
            if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
            {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else
            {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                        mDivider.getIntrinsicHeight());
            }
        }
    }


    public  int dp2px(Context context,int dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
