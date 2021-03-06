package com.modiwu.mah.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.modiwu.mah.R;

import java.io.File;
import java.util.List;

import top.jplayer.baseprolibrary.glide.GlideUtils;

/**
 * Created by Obl on 2018/9/3.
 * com.modiwu.mah.ui.adapter
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class DecorateItemPicAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    public DecorateItemPicAdapter(List<File> data) {
        super(R.layout.adapter_item_decorate_pic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {

        ImageView ivItemSrc = helper.itemView.findViewById(R.id.ivItemSrc);
        Glide.with(mContext).load(item).apply(GlideUtils.init().options(R.drawable.placeholder)).into(ivItemSrc);
    }
}
