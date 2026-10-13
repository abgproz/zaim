package com.example.bingo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private final Button[] cells = new Button[25];
    private final boolean[] marked = new boolean[25];
    private final List<Integer> numbers = new ArrayList<>();
    private TextView status;
    private GridLayout grid;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); buildUi(); newGame();
    }
    private void buildUi() {
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,24,24,24); root.setBackgroundColor(Color.rgb(244,247,251));
        TextView title = new TextView(this); title.setText("BINGO"); title.setTextSize(30); title.setTextColor(Color.rgb(13,71,161)); title.setGravity(Gravity.CENTER); title.setTypeface(null,1); root.addView(title,new LinearLayout.LayoutParams(-1,60));
        status = new TextView(this); status.setTextSize(18); status.setGravity(Gravity.CENTER); status.setTextColor(Color.DKGRAY); root.addView(status,new LinearLayout.LayoutParams(-1,48));
        grid = new GridLayout(this); grid.setColumnCount(5); grid.setRowCount(5); root.addView(grid,new LinearLayout.LayoutParams(-1,0,1));
        Button reset = new Button(this); reset.setText("NEW GAME"); reset.setTextSize(16); reset.setOnClickListener(v->newGame()); root.addView(reset,new LinearLayout.LayoutParams(-1,58)); setContentView(root);
    }
    private void newGame() {
        numbers.clear(); for(int i=1;i<=25;i++) numbers.add(i); Collections.shuffle(numbers); java.util.Arrays.fill(marked,false); grid.removeAllViews();
        for(int i=0;i<25;i++){ Button b=new Button(this); cells[i]=b; b.setText(String.valueOf(numbers.get(i))); b.setTextSize(22); b.setAllCaps(false); b.setPadding(0,0,0,0); final int p=i; b.setOnClickListener(v->toggle(p)); GridLayout.LayoutParams lp=new GridLayout.LayoutParams(GridLayout.spec(i/5,1,1f),GridLayout.spec(i%5,1,1f)); lp.width=0; lp.height=0; lp.setMargins(4,4,4,4); grid.addView(b,lp); styleCell(b,false); }
        status.setText("Tap numbers to mark them");
    }
    private void toggle(int p){ marked[p]=!marked[p]; styleCell(cells[p],marked[p]); int lines=countLines(); if(lines>0){status.setText("BINGO!  "+lines+" line"+(lines==1?"":"s")+" completed"); Toast.makeText(this,"BINGO!",Toast.LENGTH_SHORT).show();} else status.setText("Marked: "+countMarked()+"/25"); }
    private int countMarked(){int n=0;for(boolean b:marked)if(b)n++;return n;}
    private int countLines(){int n=0;for(int r=0;r<5;r++){boolean ok=true;for(int c=0;c<5;c++)ok&=marked[r*5+c];if(ok)n++;}for(int c=0;c<5;c++){boolean ok=true;for(int r=0;r<5;r++)ok&=marked[r*5+c];if(ok)n++;}boolean a=true,d=true;for(int i=0;i<5;i++){a&=marked[i*5+i];d&=marked[i*5+(4-i)];}return n+(a?1:0)+(d?1:0);}
    private void styleCell(Button b,boolean on){GradientDrawable g=new GradientDrawable();g.setCornerRadius(18);g.setColor(on?Color.rgb(67,160,71):Color.WHITE);g.setStroke(2,Color.rgb(25,118,210));b.setTextColor(on?Color.WHITE:Color.DKGRAY);b.setBackground(g);}
}
