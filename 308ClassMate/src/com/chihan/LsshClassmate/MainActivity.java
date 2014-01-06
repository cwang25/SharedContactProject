package com.chihan.LsshClassmate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.auth.AuthenticationException;

import com.chihan.NumberMagic.EasterActivity;
import com.chihan.TextGenerator.Text;
import com.chihan.TextGenerator.TextGeneratorMain;
import com.google.gdata.util.ServiceException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * @author Chi-Han Wang
 * 
 */

public class MainActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	public static final int SENT = 0;
	public static final int DELIVERED = 1;
	// homepage
	private Button quit;
	private Button createEvent;
	private Button composeGroupText;
	private Button syncSpreadSheet;
	private Button savedText;
	private int dummy2 = 0;
	// info
	private Button contactInfoBackbtn;
	private Button contactInfoEditbtn;
	private Button call;
	private Button contactInfoComposeText;
	// edit contact and related instance
	private Button contactEditBackbtn;
	private CheckBox checkBoxContactInfo;
	private Button save;
	private EditText nameEdit;
	private EditText bdayEdit;
	private EditText phoneEdit;
	private EditText emailEdit;
	private boolean isPickedEdit;
	private long contactEditID;
	private String seatnumEdit;
	// create event ----
	private Button createEventBackbtn;
	private Button createEventEditReceiptbtn;
	private Button createEventNextbtn;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private EditText locOfEvent;
	// event text review page ----
	private Button eventTextReviewBackbtn;
	private Button eventTextReviewConfirmbtn;
	private EditText eventTextReviewText;
	// group composing text page ---
	private Button groupTextSendbtn;
	private Button groupTextBackBtn;
	private EditText groupTextContent;
	// credits page
	private Button creditsBackbtn;
	// about page
	private Button aboutBackbtn;
	private TextView info;
	// edit receipt page
	int mt;
	int dt;
	int ht;
	int mint;
	String loct;
	private Button editReceiptConfirmbtn;
	private Button editReceiptBackbtn;
	// text generator page.
	private Button backTextGeneratorbtn;
	private Button newTextGeneratorbtn;
	// text generator detailed page.
	private Button deleteTextGeneratorbtn;
	private Button saveTextGeneratorbtn;
	private Button cancelTextGeneratorbtn;
	private Button copyTextGeneratorbtn;
	private Button sendToMateTextGeneratorbtn;
	private Text curText;
	private TextView title;
	private TextView content;
	private Button editReceiptBackbtnForTextGenerator;
	private Button editReceiptConfirmbtnForTextGenerator;
	// compose text
	private Button composeTextSendbtn;
	private Button composeTextCancelbtn;
	private EditText composeTextSMS;

	// troll face
	private ImageView trollFace;
	private Button backTrollbtn;
	// ---
	DBAdapter db;
	private MainSystem system;
	private TextGeneratorMain textSystem;
	private ListView clist;
	private LazyAdapter itemAdapter;
	
	// windows over panel instances
	private ProgressBar pBar;
	private LayoutParams windowParams;
	// ----
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ----- create the contact list

		system = new MainSystem();
		textSystem = new TextGeneratorMain();
		db = new DBAdapter(this);

		db.open();
		Cursor c = db.getAllRecordsFromContacts();
		if (c.moveToFirst()) {
			AddFromDataBase(c);
			while (c.moveToNext()) {
				AddFromDataBase(c);
			}
		}
		Cursor t = db.getAllRecordsFromTexts();
		if(t.moveToFirst()){
			AddFromDBText(t);
			while(t.moveToNext()){
				AddFromDBText(t);
			}
		}
		db.close();
		homePage();
		
		// windows over panel
		pBar = new ProgressBar(MainActivity.this);
		pBar.setBackgroundColor(0xAF000000);
		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.CENTER;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
		        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		windowParams.format = PixelFormat.TRANSLUCENT;
		windowParams.windowAnimations = 0;
		
		//-- end of windows
		

	}

	// below will be the onclick methods
	public void onClick(View v) {
		if (v == contactInfoBackbtn) {
			homePage();
		} else if (v == call) {
			int cPos = system.getCurrentPos();
			call(system.getContact(cPos));
		} else if (v == contactEditBackbtn) {
			int cPos = system.getCurrentPos();
			contactInfoPage(system.getContact(cPos));
		} else if (v == contactInfoEditbtn) {
			int cPos = system.getCurrentPos();
			contactEditPage(system.getContact(cPos));
		} else if (v == save) {
			int cPos = system.getCurrentPos();
			String p = "1";
			if (isPickedEdit) {
				p = "1";
			} else {
				p = "0";
			}
			// long i, String sn, String n, String bd, String p, String e,
			// String pick
			Contact temp = new Contact(contactEditID, seatnumEdit, nameEdit
					.getText().toString(), bdayEdit.getText().toString(),
					phoneEdit.getText().toString(), emailEdit.getText()
							.toString(), p);
			system.setContact(cPos, temp);
			db.open();
			db.updateRecordContacts(temp.getID(), temp.getSeatNumber(),
					temp.getName(), temp.getBday(), temp.getPhone(),
					temp.getEmail(), p);
			db.close();
			homePage();
		} else if (v == quit) {
			finish();
		} else if (v == createEvent) {
			createEventPage();
		} else if (v == createEventBackbtn) {
			homePage();
		} else if (v == this.createEventEditReceiptbtn) {
			mt = this.datePicker.getMonth();
			dt = this.datePicker.getDayOfMonth();
			ht = this.timePicker.getCurrentHour();
			mint = this.timePicker.getCurrentMinute();
			loct = this.locOfEvent.getText().toString();
			editReceiptPage();
		} else if (v == this.createEventNextbtn) {
			int m = this.datePicker.getMonth();
			int d = this.datePicker.getDayOfMonth();
			int h = this.timePicker.getCurrentHour();
			int min = this.timePicker.getCurrentMinute();
			String loc = this.locOfEvent.getText().toString();
			Event upcominE = new Event(m, d, h, min, loc);
			eventTextReview(upcominE);
		} else if (v == this.eventTextReviewBackbtn) {
			createEventPage();
		} else if (v == this.eventTextReviewConfirmbtn) {
			Contact[] pickedContacts = system.getAllPickedContacts();
			for (int i = 0; i < pickedContacts.length; i++) {
				sendSMS(pickedContacts[i].getPhone(), eventTextReviewText
						.getText().toString());
			}
			homePage();
		} else if (v == this.editReceiptBackbtn
				|| v == this.editReceiptConfirmbtn) {
			createEventPage();
			this.datePicker.updateDate(datePicker.getYear(), mt, dt);
			this.timePicker.setCurrentHour(ht);
			this.timePicker.setCurrentMinute(mint);
			this.locOfEvent.setText(loct);
		} else if (v == contactInfoComposeText) {
			int cPos = system.getCurrentPos();
			composeTextPage(system.getContact(cPos));
		} else if (v == this.composeTextSendbtn) {
			int cPos = system.getCurrentPos();
			String t = this.composeTextSMS.getText().toString();
			sendSMS(system.getContact(cPos).getPhone(), t);
			homePage();
		} else if (v == this.composeTextCancelbtn) {
			int cPos = system.getCurrentPos();
			contactInfoPage(system.getContact(cPos));
		} else if (v == this.groupTextBackBtn) {
			homePage();
		} else if (v == this.groupTextSendbtn) {
			Contact[] pickedContacts = system.getAllPickedContacts();
			for (int i = 0; i < pickedContacts.length; i++) {
				sendSMS(pickedContacts[i].getPhone(), groupTextContent
						.getText().toString());
			}
			homePage();
		} else if (v == this.composeGroupText) {
			groupTextPage();
		} else if (v == this.creditsBackbtn) {
			homePage();
		} else if (v == this.savedText) {
			textGeneratorPage();
		} else if (v == this.syncSpreadSheet) {
			SyncSpreadSheet temp = new SyncSpreadSheet();
			temp.execute();
		} else if (v == this.trollFace) {
			Intent intent = new Intent(this, EasterActivity.class);
			startActivity(intent);
		} else if (v == this.backTrollbtn) {
			homePage();
		} else if (v == this.aboutBackbtn) {
			homePage();
		} else if( v == this.backTextGeneratorbtn){
			homePage();
		} else if( v == this.newTextGeneratorbtn){
			textGeneratorDetailPage(null);
		} else if( v == this.saveTextGeneratorbtn){
			saveText(this.title.getText().toString(), this.content.getText().toString());
			textGeneratorPage();
		} else if( v == this.deleteTextGeneratorbtn){
			deleteText(curText);
			textGeneratorPage();
		} else if( v == this.sendToMateTextGeneratorbtn){
			sendToMate();
		} else if( v == this.cancelTextGeneratorbtn){
			textGeneratorPage();
		} else if( v == this.copyTextGeneratorbtn){
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText(this.title.getText(),this.content.getText());
			clipboard.setPrimaryClip(clip);
			Toast.makeText(this,"Copied!",Toast.LENGTH_SHORT).show();
		} else if( v == this.editReceiptBackbtnForTextGenerator){
			textGeneratorPage();
		} else if( v == this.editReceiptConfirmbtnForTextGenerator){
			Contact[] pickedContacts = system.getAllPickedContacts();
			for (int i = 0; i < pickedContacts.length; i++) {
				sendSMS(pickedContacts[i].getPhone(), this.content.getText().toString());
			}
			homePage();
		}
	}

	// below will be the oncheckedchangelistner.
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView == checkBoxContactInfo) {
			int cPos = system.getCurrentPos();
			Contact c = system.setPickContactAt(cPos, isChecked);
			String p = "1";
			if (c.isPicked()) {
				p = "1";
			} else {
				p = "0";
			}
			db.open();
			db.updateRecordContacts(c.getID(), c.getSeatNumber(), c.getName(),
					c.getBday(), c.getPhone(), c.getEmail(), p);
			db.close();
		} else {
			try{
				int position = clist.getPositionForView(buttonView);
				if (isChecked) {
					Toast.makeText(this,
							"選取 " + Integer.toString(position + 1) + " 號",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this,
							"取消選取 " + Integer.toString(position + 1) + " 號",
							Toast.LENGTH_SHORT).show();
				}
				if (position != ListView.INVALID_POSITION) {
					Contact c = system.setPickContactAt(position, isChecked);
					String p = "1";
					if (c.isPicked()) {
						p = "1";
					} else {
						p = "0";
					}
					itemAdapter.data[position].setPick(isChecked);
					db.open();
					db.updateRecordContacts(c.getID(), c.getSeatNumber(),
							c.getName(), c.getBday(), c.getPhone(), c.getEmail(), p);
					db.close();
				}
			} catch(NullPointerException e){
				
			}
		}
	}

	/**
	 * home page.
	 */
	public void homePage() {
		//
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		this.composeGroupText = (Button) this
				.findViewById(R.id.sendGroupSMSbtn);
		composeGroupText.setOnClickListener(this);
		this.syncSpreadSheet = (Button) this.findViewById(R.id.syncSpreadSheet);
		syncSpreadSheet.setOnClickListener(this);
		this.savedText = (Button) this.findViewById(R.id.premadeTextbtn);
		savedText.setOnClickListener(this);
		quit = (Button) this.findViewById(R.id.quitMain);
		quit.setOnClickListener(this);
		createEvent = (Button) this.findViewById(R.id.createEventbtn);
		createEvent.setOnClickListener(this);
		clist = (ListView) findViewById(R.id.scrollView1);
		clist.setScrollingCacheEnabled(false);
		Contact[] cArray = system.getContactListArray();
		itemAdapter = new LazyAdapter(this, cArray, db);
		// ------
		// ArrayAdapter<Contact> itemAdapter = new ArrayAdapter<Contact>(this,
		// android.R.layout.simple_list_item_1, test);
		clist.setAdapter(itemAdapter);

		// ---below will be the listener of the contact list----
		clist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				system.setCurrentPos(pos);
				contactInfoPage(system.getContact(pos));
			}
		});

	}

	/**
	 * ContactInfoPage algorithm
	 * 
	 * @param c
	 */
	public void contactInfoPage(Contact c) {
		setContentView(R.layout.contact_info);
		contactInfoBackbtn = (Button) this
				.findViewById(R.id.contactInfoBackbtn);
		contactInfoBackbtn.setOnClickListener((OnClickListener) this);
		contactInfoEditbtn = (Button) this
				.findViewById(R.id.contactInfoEditbtn);
		contactInfoEditbtn.setOnClickListener(this);
		TextView name = (TextView) this.findViewById(R.id.contactInfoName);
		name.setText(c.getName());
		TextView bday = (TextView) this.findViewById(R.id.contactInfoBday);
		bday.setText(c.getBday());
		TextView phone = (TextView) this
				.findViewById(R.id.contactInfoPhoneNumber);
		phone.setText(c.getPhone());
		TextView email = (TextView) this.findViewById(R.id.contactInfoEmail);
		email.setText(c.getEmail());
		ImageView portrait = (ImageView) this
				.findViewById(R.id.photoContactInfo);
		portrait.setImageResource(c.getPhotoCode());
		checkBoxContactInfo = (CheckBox) this
				.findViewById(R.id.pickedContactInfo);
		checkBoxContactInfo.setChecked(c.isPicked());
		checkBoxContactInfo.setOnCheckedChangeListener(this);
		call = (Button) this.findViewById(R.id.contactInfoCallbtn);
		call.setOnClickListener(this);
		contactInfoComposeText = (Button) this
				.findViewById(R.id.contactInfoSendTextbtn);
		contactInfoComposeText.setOnClickListener(this);
	}

	/**
	 * Contact edit algorithm
	 * 
	 * @param c
	 */
	public void contactEditPage(Contact c) {
		setContentView(R.layout.contact_info_edit);
		ImageView img = (ImageView) this.findViewById(R.id.contactEditePhoto);
		img.setImageResource(c.getPhotoCode());
		contactEditBackbtn = (Button) this
				.findViewById(R.id.contactEditBackbtn);
		contactEditBackbtn.setOnClickListener(this);
		nameEdit = (EditText) this.findViewById(R.id.contactEditName);
		nameEdit.setText(c.getName());
		bdayEdit = (EditText) this.findViewById(R.id.contactEditBdayInput);
		bdayEdit.setText(c.getBday());
		phoneEdit = (EditText) this.findViewById(R.id.contactEditPhoneInput);
		phoneEdit.setText(c.getPhone());
		emailEdit = (EditText) this.findViewById(R.id.contactEditEmailInput);
		emailEdit.setText(c.getEmail());
		isPickedEdit = c.isPicked();
		contactEditID = c.getID();
		seatnumEdit = c.getSeatNumber();
		save = (Button) this.findViewById(R.id.contactEditSavebtn);
		save.setOnClickListener(this);

	}

	public void createEventPage() {
		setContentView(R.layout.create_event);
		this.createEventBackbtn = (Button) this
				.findViewById(R.id.backCreateEvent);
		createEventBackbtn.setOnClickListener(this);
		this.createEventEditReceiptbtn = (Button) this
				.findViewById(R.id.editReceiptCreateEvenbtn);
		createEventEditReceiptbtn.setOnClickListener(this);
		this.createEventNextbtn = (Button) this
				.findViewById(R.id.nextCreateEvent);
		createEventNextbtn.setOnClickListener(this);
		this.datePicker = (DatePicker) this
				.findViewById(R.id.datePickerCreateEvent);
		this.timePicker = (TimePicker) this.findViewById(R.id.timePicker1);
		this.locOfEvent = (EditText) this
				.findViewById(R.id.createEvenTextInput);
	}

	public void eventTextReview(Event e) {
		setContentView(R.layout.event_text_review);
		this.eventTextReviewBackbtn = (Button) this
				.findViewById(R.id.backReviewbtn);
		eventTextReviewBackbtn.setOnClickListener(this);
		this.eventTextReviewConfirmbtn = (Button) this
				.findViewById(R.id.confirmReviewbtn);
		eventTextReviewConfirmbtn.setOnClickListener(this);
		this.eventTextReviewText = (EditText) this
				.findViewById(R.id.editTextEventTextReview);
		eventTextReviewText.setText(e.getTextSMS());
	}

	public void editReceiptPage() {
		setContentView(R.layout.edit_receipt);
		clist = (ListView) findViewById(R.id.editReceiptlistView);
		clist.setScrollingCacheEnabled(false);
		Contact[] test = system.getContactListArray();
		itemAdapter = new LazyAdapter(this, test, db);
		clist.setAdapter(itemAdapter);
		this.editReceiptBackbtn = (Button) this
				.findViewById(R.id.backEditReceiptbtn);
		editReceiptBackbtn.setOnClickListener(this);
		this.editReceiptConfirmbtn = (Button) this
				.findViewById(R.id.editReceiptComfirm);
		editReceiptConfirmbtn.setOnClickListener(this);

	}

	public void composeTextPage(Contact c) {
		setContentView(R.layout.compose_text);
		TextView name = (TextView) this.findViewById(R.id.composeTextName);
		name.setText(c.getName());
		this.composeTextSendbtn = (Button) this
				.findViewById(R.id.compsoeTextSendbtn);
		composeTextSendbtn.setOnClickListener(this);
		this.composeTextCancelbtn = (Button) this
				.findViewById(R.id.composeTextCancelbtn);
		composeTextCancelbtn.setOnClickListener(this);
		this.composeTextSMS = (EditText) this
				.findViewById(R.id.composeTextField);
	}

	public void groupTextPage() {
		setContentView(R.layout.group_sms);
		this.groupTextBackBtn = (Button) this
				.findViewById(R.id.groupSmsBackbtn);
		groupTextBackBtn.setOnClickListener(this);
		this.groupTextContent = (EditText) this.findViewById(R.id.groupSmsText);
		this.groupTextSendbtn = (Button) this
				.findViewById(R.id.groupSmsSendbtn);
		groupTextSendbtn.setOnClickListener(this);

	}

	public void creditsPage() {
		setContentView(R.layout.credit_page);
		this.creditsBackbtn = (Button) this.findViewById(R.id.creditbackbtn);
		creditsBackbtn.setOnClickListener(this);
	}

	public void trollFacePage() {
		setContentView(R.layout.trollface);
		this.trollFace = (ImageView) this.findViewById(R.id.trollface);
		trollFace.setOnClickListener(this);
		TextView t = (TextView) this.findViewById(R.id.trollText);
		Animation fadeInAnimation = AnimationUtils.loadAnimation(this,
				R.animator.fadeinforanswer);
		t.startAnimation(fadeInAnimation);
		t.setText("一次按這麼多次....真有你的!  彩蛋發現哈~\"~ ㄎㄎ \n (But the following contents will be English only.)");
		this.backTrollbtn = (Button) this.findViewById(R.id.backTrollFace);
		backTrollbtn.setOnClickListener(this);
		backTrollbtn.setVisibility(View.VISIBLE);

	}

	public void aboutPage() {
		setContentView(R.layout.about);
		this.aboutBackbtn = (Button) this.findViewById(R.id.aboutBackbtn);
		aboutBackbtn.setOnClickListener(this);
		this.info = (TextView) this.findViewById(R.id.aboutTextView);
		info.setText("1.為什麼我會想寫這個程式? "
				+ "\n就只是因為看到有人在臉書上, 要大家填通訊錄, 然後我就突然想說寫一下程式。\n"
				+ "雖然沒有說很高檔的程式, 不過我想說也可以藉此練習自己的CODING能力。"
				+ "\n"
				+ "\n2.為什麼背景是一隻海豚?\n"
				+ "因為蹄膀太醜....\n"
				+ "\n3.這個程式已經完成了嗎?\n"
				+ "很明顯是NO, \n"
				+ "不過因為要開學了, 所以我只能把做到哪就弄到哪..\n"
				+ "往後我還是會找時間慢慢將一些尚未加入的功能實現。\n"
				+ "通常要長假才比較會有時間寫....\n"
				+ "\n4.往後會加入的功能:\n"
				+ "a. \"已存簡訊\"(其實這個我已經寫好的 再另外一個APP裡面...只是要開學忙搬東西跟一些有的沒的, 所以沒有融合在這個APP中 >~< 拍謝!拍謝!)\n"
				+ "\nb. \"雲端同步通訊錄\" (這個的話...我還在研究, 目前還是沒辦法很順利的實行, 不過預計在未來的改版終將此功能新增到APP中)\n"
				+ "\nc. \"大頭照同部\" (雖然這個感覺沒啥用, 不過能同步且不耗資元的話 一定很酷 :D)\n"
				+ "\nd. \"優化通訊錄列表\" (其實我一開始測試的時候就發現, 列表有點LAG, 不順暢, 我試著使用 holder class去優化 以及去除不必要的 記憶體使用"
				+ "但是始終沒法使之流暢到\"順暢\" >~< 拍謝,  之後我會繼續努力找其他優化的方法)\n"
				+ "\n5.如果有發現BUG, 或者是有什麼想法, 請 EMAIL給我喔 :D  感謝!! (email: fattydolphinhans@gmail.com)\n"
				+ "\n背景 是我親手畫的!! :D可愛吼? 花了我兩天去畫的圖==!\n"
				+ "\n\nP.S. 我順勢地, 在這個APP中 塞了一個彩蛋喔XD, 來找找看! 看誰先發現 哈哈!!");
	}
	
	public void textGeneratorPage(){
		setContentView(R.layout.textgenerator_interface);
		ListView lv = (ListView)this.findViewById(R.id.savedTextsTextgenerator);
		this.backTextGeneratorbtn = (Button)this.findViewById(R.id.backbtn_textgeneratorInterface);
		this.newTextGeneratorbtn = (Button)this.findViewById(R.id.new_textgeneratorInterface);
		backTextGeneratorbtn.setOnClickListener(this);
		newTextGeneratorbtn.setOnClickListener(this);
		ArrayList<String> listItems = textSystem.getTitleslist();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Text t = textSystem.getSelectedText(pos);
				textGeneratorDetailPage(t);
			}
		});
		
		
	}
	
	public void textGeneratorDetailPage(Text txt){
		setContentView(R.layout.textgenerator_detail);
		this.title = (TextView)this.findViewById(R.id.titleEdittextgeneratorDetail);
		this.content = (TextView)this.findViewById(R.id.contentEditTextgeneratorDetail);
		this.deleteTextGeneratorbtn = (Button)this.findViewById(R.id.deletTextgeneratorDetail);
		this.cancelTextGeneratorbtn = (Button)this.findViewById(R.id.backTextgeneratorDetail);
		this.saveTextGeneratorbtn = (Button)this.findViewById(R.id.saveTextgeneratorDetail);
		this.copyTextGeneratorbtn = (Button)this.findViewById(R.id.copyTextgeneratorDetail);
		this.sendToMateTextGeneratorbtn = (Button)this.findViewById(R.id.groupTextgeneratorDetail);
		if(txt != null){
			this.curText = txt;
			title.setText(txt.getTitle());
			content.setText(txt.getContent());
		} else {
			this.curText = null;
			deleteTextGeneratorbtn.setVisibility(View.INVISIBLE);
		}
		deleteTextGeneratorbtn.setOnClickListener(this);
		cancelTextGeneratorbtn.setOnClickListener(this);
		saveTextGeneratorbtn.setOnClickListener(this);
		copyTextGeneratorbtn.setOnClickListener(this);
		sendToMateTextGeneratorbtn.setOnClickListener(this);
		
	}
	
	public void saveText(String t, String c){
		db.open();
		if(textSystem.lookUp(t) == null){
			long id = db.insertRecordsToTexts(t, c);
			textSystem.addText(id, t, c);
		} else {
			Text temp = textSystem.lookUp(t);
			long id = temp.getId();
			db.updateRecordTexts(id, t, c);
			temp.setTitle(t);
			temp.setContent(c);
		}
		
		db.close();
	}
	
	public void deleteText(Text txt){
		db.open();
		long id = txt.getId();
		db.deleteRecordFromTexts(id);
		textSystem.removeById(id);
		db.close();
	}
	
	public void sendToMate() {
		setContentView(R.layout.edit_receipt);
		clist = (ListView) findViewById(R.id.editReceiptlistView);
		clist.setScrollingCacheEnabled(false);
		Contact[] test = system.getContactListArray();
		itemAdapter = new LazyAdapter(this, test, db);
		clist.setAdapter(itemAdapter);
		this.editReceiptBackbtnForTextGenerator = (Button) this
				.findViewById(R.id.backEditReceiptbtn);
		editReceiptBackbtnForTextGenerator.setOnClickListener(this);
		this.editReceiptConfirmbtnForTextGenerator = (Button) this
				.findViewById(R.id.editReceiptComfirm);
		editReceiptConfirmbtnForTextGenerator.setOnClickListener(this);

	}
	public void AddFromDataBase(Cursor c) {
		long id = c.getLong(0);
		// KEY_ROWID, KEY_SEATNUMBER, KEY_NAME, KEY_BDAY, KEY_PHONE, KEY_EMAIL,
		// KEY_PICK
		system.addContact(new Contact(id, c.getString(1), c.getString(2), c
				.getString(3), c.getString(4), c.getString(5), c.getString(6)));
	}
	/**
	 * The method to add text from DB
	 * @param c Cursor
	 */
	public void AddFromDBText(Cursor c){
		long id = c.getLong(0);
		textSystem.addText(id, c.getString(1), c.getString(2));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 0, "Sync From Google SpreadSheet");
		menu.add(Menu.NONE, Menu.FIRST + 2, 1, "製作人");
		menu.add(Menu.NONE, Menu.FIRST + 3, 2, "關於");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			SyncSpreadSheet temp = new SyncSpreadSheet();
			temp.execute();
			break;
		case Menu.FIRST + 2:
			this.dummy2++;
			if(dummy2 > 5){
				trollFacePage();
				dummy2 = 0;
			} else {
				creditsPage();	
			}
			break;
		case Menu.FIRST + 3:
			aboutPage();
			break;
		}
		return true;
	}

	// -------------call method
	public void call(Contact c) {
		String pn = "tel:" + c.getPhone();
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse(pn));
			startActivity(callIntent);
		} catch (ActivityNotFoundException activityException) {
			Toast.makeText(getBaseContext(), "Call Failed", Toast.LENGTH_SHORT)
					.show();
		}
	}

	// syne the data base algorithm
	
	public void SyncDB(Contact[] cL){
		db.open();
		for(int i = 0 ; i < cL.length ; i++){
			Contact fromLocal = system.getContact(i);
			long idFromLocal = fromLocal.getID();
			boolean pickStateLocal = fromLocal.isPicked();
			Contact fromCloud = cL[i];
			fromCloud.setID(idFromLocal);
			fromCloud.setPick(pickStateLocal);
			system.setContact(i, fromCloud);
			String p = "1";
			if(fromCloud.isPicked()){
				p = "1";
			} else {
				p = "0";
			}
			db.updateRecordContacts(fromCloud.getID(), fromCloud.getSeatNumber(),
					fromCloud.getName(), fromCloud.getBday(), fromCloud.getPhone(),
					fromCloud.getEmail(), p);
		}
		db.close();		
	}
	
	private class SyncSpreadSheet extends AsyncTask<URL, Integer, String> {
		SpreadSheetReader t;
		@Override
		protected String doInBackground(URL... arg0) {
			try {
				t = new SpreadSheetReader();
			} catch (AuthenticationException e) {
				return "AuthenticationException";
			} catch (MalformedURLException e) {
				return "MalformedURLException";
			} catch (IOException e) {
				return "IOException";
			} catch (ServiceException e) {
				return "ServiceException";
			}
			Contact [] temp = t.getContactsListFromCloud();
			SyncDB(temp);
			return "Contact List Synced";
		}
		
		@Override
		protected void onPreExecute(){
			getWindowManager().addView(pBar, windowParams);
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub	
			Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
			homePage();
			getWindowManager().removeView(pBar);
			super.onPostExecute(result);
			
		}

	}

	// -----------------------------------------send SMS

	/**
	 * send sms msg
	 * 
	 * @param number
	 * @param message
	 */
	// ---sends an SMS message to another device---
	private void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered",
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}
	

}
