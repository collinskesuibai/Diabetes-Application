package com.collinskesuiabi.diakenya;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collinskesuiabi.diakenya.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;


    public SliderAdapter(Context context) {
        this.context = context;
       // this.layoutInflater = layoutInflater;
    }
    public int [] slide_images = {
      R.drawable.chatbubble,
            R.drawable.chatbubble,
            R.drawable.chatbubble

    };
    public String[] slide_headings = {
            "WELCOME",
            "SERVICES",
            "MANAGE LIFE EASILY"

    };
    public String [] slide_description ={
            "Welcome to a new level of health care system ",
            "With fast and easy to use services",
            "Your help is only one click away"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView = view.findViewById(R.id.slider_image);
        TextView textView = view.findViewById(R.id.slider_heading);
        TextView textDescriptiom = view.findViewById(R.id.slider_d);


        slideImageView.setImageResource(slide_images[position]);
//        Typeface customfont = Typeface.createFromAsset(context.getAssets(),"fonts/Cinzel-Regular.ttf");
//        textView.setTypeface(customfont);
//        Typeface customfont2 = Typeface.createFromAsset(container.getContext().getAssets(),"fonts/Philosopher-Regular.ttf");
//        textDescriptiom.setTypeface(customfont2);
        textView.setText(slide_headings[position]);
        textDescriptiom.setText(slide_description[position]);


        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
