package com.chihan.NumberMagic;

import com.chihan.LsshClassmate.R;
import com.chihan.LsshClassmate.R.layout;
import com.chihan.LsshClassmate.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EasterActivity extends Activity implements OnClickListener{
	private Button startbutton;
	private Button oddbutton;
	private Button evenbutton;
	private Button quitbutton; 
	private Button restart;
	private ImageView answerNumber;
	private MagicCore magiccore;
	private EditText input;
	private TextView answer;
	private boolean even = false;
	private int inputnumber;
	private TextView instruction;
	private int textIndex = 0;
	int wrongtime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        introStage();
        inputnumber = 0;
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    public void onClick(View v){
    	if(v == startbutton){
    		oddOrEvenStage();
    	} else if(v == oddbutton){
    		even = false;
    		answerStage();
    	} else if(v == evenbutton){
    		even = true;
    		answerStage();
    	} else if(v == quitbutton){
    		finish();
    	} else if(v == restart){
    		introStage();
    	} else if(v == instruction){
    		introAnimation(instruction);
    	}
    }
    public void introStage(){
    	setContentView(R.layout.first_stage);
        startbutton = (Button)this.findViewById(R.id.start);
        startbutton.setOnClickListener((OnClickListener) this);
        instruction = (TextView)this.findViewById(R.id.instruction);
        instruction.setText("Welcome to the Magic World!");
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.fadein);
        instruction.setAnimation(fadeInAnimation);
        input = (EditText)this.findViewById(R.id.titleEdittextgeneratorDetail);
        instruction.setOnClickListener((OnClickListener)this);
    }
    public void oddOrEvenStage(){
    	String stringinput = input.getText().toString();
    	try{
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.fadein);
        inputnumber = Integer.parseInt(stringinput);
        setContentView(R.layout.second_stage);
    	TextView text2 = (TextView)this.findViewById(R.id.trollText);
        text2.setAnimation(fadeInAnimation);
		oddbutton = (Button)this.findViewById(R.id.odd);
        evenbutton = (Button)this.findViewById(R.id.even);
        oddbutton.setAnimation(fadeInAnimation);
        evenbutton.setAnimation(fadeInAnimation);
        oddbutton.setOnClickListener((OnClickListener) this);
        evenbutton.setOnClickListener((OnClickListener) this);
    	} catch(NumberFormatException e){
    		if(stringinput.length() > 10){
    			Toast.makeText(getBaseContext(), "Please follow the direction, the number you used is too huge!!! I can't handle this!", Toast.LENGTH_SHORT).show();
    		} else {
        		Toast.makeText(getBaseContext(), "You have to put in number without your magic number before to start the magic!", Toast.LENGTH_SHORT).show();
    		}
    	} 
    }
    public void answerStage(){
    	setContentView(R.layout.third_stage);
    	magiccore = new MagicCore(inputnumber,even);
    	answer = (TextView)this.findViewById(R.id.answer);
    	String finalanswer = magiccore.getAnswer()+"";
		answer.setText(finalanswer);
		quitbutton = (Button)this.findViewById(R.id.quit);
		quitbutton.setOnClickListener((OnClickListener) this);
		restart = (Button)this.findViewById(R.id.restart);
		restart.setOnClickListener((OnClickListener)this);
		answerNumber = (ImageView)this.findViewById(R.id.answerNumber);
		setImage(magiccore.getAnswer());
		Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.fadeinforanswer);
		answerNumber.setAnimation(fadeInAnimation);
		answer.setAnimation(fadeInAnimation);
		quitbutton.setAnimation(fadeInAnimation);
		restart.setAnimation(fadeInAnimation);
    }
    
    public void setImage(int answer){
    	switch(answer){
    	case 0:
    		answerNumber.setImageResource(R.drawable.zero);
    		break;
    	case 1:
    		answerNumber.setImageResource(R.drawable.one);
    		break;
    	case 2:
    		answerNumber.setImageResource(R.drawable.two);
    		break;
    	case 3:
    		answerNumber.setImageResource(R.drawable.three);
    		break;
    	case 4:
    		answerNumber.setImageResource(R.drawable.four);
    		break;
    	case 5:
    		answerNumber.setImageResource(R.drawable.five);
    		break;
    	case 6:
    		answerNumber.setImageResource(R.drawable.six);
    		break;
    	case 7:
    		answerNumber.setImageResource(R.drawable.seven);
    		break;
    	case 8:
    		answerNumber.setImageResource(R.drawable.eight);
    		break;
    	case 9:
    		answerNumber.setImageResource(R.drawable.nine);
    		break;
    	default:
    		break;
    	}
    }
    
    public void introAnimation(TextView text){
    	String [] message = {"First, You will need a calculator. \nYou can use other devices to make sure that I am not recording you input. \nWhen you are ready tap this text to next step.\n",
    			"OK, so now you are ready. \nTry to mutiply any single digit you want, but keep in mind only multiply one single digit number at a time.", 
    			"For example: 3X6X5X7X6X...... etc\n", 
    			"Keep multiplying until you final value falls between million and billion.\n", 
    			"For example:\n45537824", 
    			"When you get ur final value, which is a really huge number now.\n Find a digit and keep it as your secret magic number.\n", 
    			"Are you sure you remeber which digit you pick as your magic number? If yes, tap to next step.\n", 
    			"Now, type in the rest of digits but not your single magic number.\nFor Example:\nThe Value: 346167\nThe Magic Number: 6\nThe number to type in: 34617\n","Click \"Start\" for the next step :D\n"
    			};
    	Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.fadein);
    	text.setText(message[textIndex]);
    	text.startAnimation(fadeInAnimation);
    	textIndex++;
    	if(textIndex == message.length){
    		textIndex = 0;
    	}
    }
    
}
