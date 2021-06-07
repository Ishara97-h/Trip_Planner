package com.e.tripplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class suggestion extends AppCompatActivity {

    GridLayout mainGrid;
    Button sub;
    int i;
    int[] arr=new int[6];
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        mainGrid=(GridLayout)findViewById(R.id.mainGrid);
        sub=(Button)findViewById(R.id.sub);

        //setSingleEvent(mainGrid);
        setToggelEvent(mainGrid);
    }

    private void setToggelEvent(GridLayout mainGrid) {
        for(i=0;i<mainGrid.getChildCount();i++){


            final CardView cardview = (CardView)mainGrid.getChildAt(i);

            cardview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(cardview.getCardBackgroundColor().getDefaultColor() == -1){
                        cardview.setCardBackgroundColor(Color.parseColor("#006EFF"));
                        a=i+a;
                    }
                    else
                    {
                        cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        a=a-i;
                    }

                }
            });


        }
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(suggestion.this,"Select : "+a,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setSingleEvent(GridLayout mainGrid){
        for(int i=0;i<mainGrid.getChildCount();i++){
            CardView cardview = (CardView)mainGrid.getChildAt(i);
            final int finalI=i;
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(suggestion.this,"Clicked at index"+finalI,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
