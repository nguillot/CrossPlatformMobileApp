package com.stylingandroid.materialrss.presenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylingandroid.materialrss.R;
import com.stylingandroid.materialrss.mvp.models.entities.FeedItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());

    private List<FeedItem> items;
    private ItemClickListener itemClickListener;

    public FeedAdapter(List<FeedItem> objects, @NonNull ItemClickListener itemClickListener) {
        this.items = objects;
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.feed_list_item, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final FeedItem item = items.get(position);
        viewHolder.setTitle(item.getTitle());
        viewHolder.setDescription(Html.fromHtml(item.getDescription()));
        viewHolder.setDate(dateFormat.format(new Date(item.getPubDate())));
        viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getPubDate();
    }

    public void moveItem(int start, int end) {
        int max = Math.max(start, end);
        int min = Math.min(start, end);
        if (min >= 0 && max < items.size()) {
            FeedItem item = items.remove(min);
            items.add(max, item);
            notifyItemMoved(min, max);
        }
    }

    public int getPositionForId(long id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPubDate() == id) {
                return i;
            }
        }
        return -1;
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView title;
        private final TextView description;
        private final TextView date;

        public static ViewHolder newInstance(View parent) {
            TextView title = (TextView) parent.findViewById(R.id.feed_item_title);
            TextView description = (TextView) parent.findViewById(R.id.feed_item_description);
            TextView date = (TextView) parent.findViewById(R.id.feed_item_date);
            return new ViewHolder(parent, title, description, date);
        }

        private ViewHolder(View parent, TextView title, TextView description, TextView date) {
            super(parent);
            this.parent = parent;
            this.title = title;
            this.description = description;
            this.date = date;
        }

        public void setTitle(CharSequence text) {
            title.setText(text);
        }

        public void setDescription(CharSequence text) {
            description.setText(text);
        }

        public void setDate(CharSequence text) {
            date.setText(text);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            parent.setOnClickListener(listener);
        }

        public View getTitleView() {
            return title;
        }

        public View getDateView() {
            return date;
        }

        public View getBodyView() {
            return description;
        }
    }

    public interface ItemClickListener {
        void itemClicked(FeedItem item);
    }
}
