package com.bridgelabz.fundoo.add_note_page.View;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.BroadCastReciever.AlarmReceiever;
import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.Note;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.ViewModel.NoteViewModel;
import com.bridgelabz.fundoo.add_note_page.ViewModel.RestApiNoteViewModel;
import com.bridgelabz.fundoo.utility.DatePickerFragement;
import com.bridgelabz.fundoo.utility.TimePickerFragement;
import com.bridgelabz.fundoo.utility.ValidationHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.bridgelabz.fundoo.R.menu.add_note_menu;
import static com.bridgelabz.fundoo.utility.AppConstants.ADD_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_REMINDER_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_ARCHIVE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_NOTES_COLOR_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_PINNED_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_UPDATE_NOTE_ACTION;

public class AddNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    EditText mTextTitle;
    EditText mTextDescription;
    Button mButtonSave;
    EditText mTextDate;
    EditText mTextTime;
    Button mButtonDate;
    Button mButtonTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private NoteViewModel noteViewModel;
    ConstraintLayout rootViewGroup;
    RestApiNoteViewModel restApiNoteViewModel;
    private AddNoteModel noteToEdit;
    //    private String notecolor;
    private static final String TAG = "AddNoteActivity";
    MenuItem mArchive;
    MenuItem mPinned;
    MenuItem mReminder;
    private String noteColor = "#ffffff";
    private boolean isEditMode = false;
    private boolean isArchived = false;
    private boolean isPinned = false;
    private boolean isTrashed = false;
    private boolean isColorChange = false;
    private String ifReminder;
    private RadioGroup radioGroup;
    private StringBuilder reminderBuilder = new StringBuilder();
    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        notificationManager = NotificationManagerCompat.from(this);
        noteViewModel = new NoteViewModel(this);
        findViews();
        setOnClickSave();
        checkEditMode();
        registerBroadcast();
        mButtonDate.setOnClickListener(this);
        mButtonTime.setOnClickListener(this);
        notificationManager = NotificationManagerCompat.from(this);
        restApiNoteViewModel = new RestApiNoteViewModel(this);


    }

    private void registerBroadcast() {

        LocalBroadcastManager.getInstance(this).registerReceiver(addNoteBroadcastReceiver,
                new IntentFilter(ADD_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(setArchiveNoteBroadcastReceiver,
                new IntentFilter(SET_ARCHIVE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(updateNoteBroadcastReceiver,
                new IntentFilter(SET_UPDATE_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(changeColorToNoteBroadcastReceiver,
                new IntentFilter(SET_NOTES_COLOR_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(setPinnedNoteBroadcastReceiver,
                new IntentFilter(SET_PINNED_NOTE_ACTION));


        LocalBroadcastManager.getInstance(this).registerReceiver(setReminderNoteBroadcastReceiver,
                new IntentFilter(GET_REMINDER_NOTE_ACTION));

    }

    private void checkEditMode() {

        Intent intent = getIntent();
        if (intent.hasExtra("noteToEdit")) {
            NoteResponseModel noteResponseModel = (NoteResponseModel) intent.
                    getSerializableExtra("noteToEdit");
            if (noteResponseModel.getReminder().isEmpty())
                noteToEdit = AddNoteModel.getNoteFromResponse(noteResponseModel);
            Log.e(TAG, "note is available");
            System.out.println(noteToEdit.toString());
            isEditMode = true;
            setUpEditFields();

        } else {
            isEditMode = false;
        }
    }

    private void setUpEditFields() {
        mTextTitle.setText(noteToEdit.getTitle());
        mTextDescription.setText(noteToEdit.getDescription());
        setColorRadioButton(noteToEdit);
    }

    private void setColorRadioButton(AddNoteModel noteToEdit) {
        String noteColor = noteToEdit.getColor();
        String defaultColor = getString(R.string.white_color_string);
        String whiteColor = getString(R.string.white_color_string);
        String blueColor = getString(R.string.sky_blue_color_string);
        String yellowColor = getString(R.string.yellow_color_string);
        String orangeColor = getString(R.string.orange_color_string);
        String redColor = getString(R.string.red_color_string);


        checkColorRadioButton(defaultColor, noteColor, R.id.radioButton_white, R.id.radioButton_white);
        checkColorRadioButton(whiteColor, noteColor, R.id.radioButton_white, R.id.radioButton_white);
        checkColorRadioButton(blueColor, noteColor, R.id.radioButton_blue, R.id.radioButton_blue);
        checkColorRadioButton(redColor, noteColor, R.id.radioButton_red, R.id.radioButton_red);
        checkColorRadioButton(orangeColor, noteColor, R.id.radioButton_orange, R.id.radioButton_orange);
        checkColorRadioButton(yellowColor, noteColor, R.id.radioButton_yellow, R.id.radioButton_yellow);
    }

    private void checkColorRadioButton(String defaultColor, String noteColor, int viewResId, int colorResId) {
        if (defaultColor.equals(noteColor)) {
            radioGroup.check(viewResId);
            RadioButton radioButton = findViewById(viewResId);
            onRadioButtonClicked(radioButton);
        }
    }

    private void setOnClickSave() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTextTitle.getText().toString().trim();
                String description = mTextDescription.getText().toString().trim();

                if (ValidationHelper.validateTitle(title)) {
                    if (ValidationHelper.validateDescription(description)) {
                        if (isEditMode) {
                            noteToEdit.setTitle(title);
                            noteToEdit.setDescription(description);
                            noteToEdit.setColor(noteColor);
                            setReminderNotification(convertStringToDate(noteToEdit.getReminder(),"EEE ,MMM d hh : mm : ss" ) );
                            updateNoteToDB(noteToEdit);
                        } else {

                            Log.e(TAG, reminderBuilder.toString());

                            Note note = new Note(title, description, noteColor, isArchived,
                                    isPinned, reminderBuilder.toString(), isTrashed);
                            Log.e(TAG, note.toString());
                            addNoteToDb(note);
                        }

                    } else {
                        Toast.makeText(AddNoteActivity.this, "Enter Description",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddNoteActivity.this, "Enter Title",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void findViews() {
        mTextTitle = findViewById(R.id.et_title);
        mTextDescription = findViewById(R.id.et_description);
        rootViewGroup = findViewById(R.id.root_add_note_activity);
        mButtonSave = findViewById(R.id.btn_save);
        mArchive = findViewById(R.id.menu_archive);
        mPinned = findViewById(R.id.menu_pinned);
        mReminder = findViewById(R.id.menu_reminder);
        mTextDate = findViewById(R.id.et_setdate);
        mTextTime = findViewById(R.id.et_settime);
        mButtonDate = findViewById(R.id.set_date);
        mButtonTime = findViewById(R.id.set_time);

    }

    //BroadCast Receiver

    private BroadcastReceiver addNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive : Local broadcasts are working");
            if (intent.hasExtra("isNoteAdded")) {
                boolean isNoteAdded = intent.
                        getBooleanExtra("isNoteAdded", false);
                Log.e(TAG, "onReceive : we got the data" + isNoteAdded);

                if (isNoteAdded) {
                    Toast.makeText(AddNoteActivity.this, "Note is Successfully Saved",
                            Toast.LENGTH_SHORT).show();

                    Intent data = new Intent(AddNoteActivity.this, DashboardActivity.class);
                    startActivity(data);
                    finish();

                } else {
                    Toast.makeText(AddNoteActivity.this, "Something went Wrong..",
                            Toast.LENGTH_SHORT).show();
                }


            }

        }
    };


    private BroadcastReceiver updateNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: LocalBroadcast is working");
            if (intent.hasExtra("isNoteUpdate")) {
                boolean isNoteUpdate = intent.getBooleanExtra("isNoteUpdate", false);
                Log.e(TAG, "onReceive: we got the data" + isNoteUpdate);
                if (isNoteUpdate) {
                    Toast.makeText(AddNoteActivity.this, "Note is Successfully Updated",
                            Toast.LENGTH_SHORT).show();
                    Intent data = new Intent(AddNoteActivity.this, DashboardActivity.class);
                    startActivity(data);
//                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this, "Something went wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    private BroadcastReceiver changeColorToNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: ColorChanging is working");
            if (intent.hasExtra("isColorChange")) ;
            boolean isColorChange = intent.getBooleanExtra("isColorChange", false);
            Log.e(TAG, "onReceive: color is changing" + isColorChange);

        }

    };

        public BroadcastReceiver setArchiveNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: setArchive LocalBroadcast is working");
            if (intent.hasExtra("isNoteArchived")) ;
            boolean isNoteArchived = intent.getBooleanExtra("isNoteArchived", false);
            Log.e(TAG, "onReceive: we got the data" + isNoteArchived);

        }
    };

    public BroadcastReceiver setPinnedNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: setPinned LocalBroadcast  is working");
            if (intent.hasExtra("isPinned")) ;
            boolean isPinned = intent.getBooleanExtra("isPinned", false);
            Log.e(TAG, "onReceive: we got the data" + isPinned);

        }
    };

    public BroadcastReceiver setReminderNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e(TAG, "onReceive: ifReminder LocalBroadcast is working");
            if (intent.hasExtra("ifReminder")) ;
            boolean ifReminder = intent.getBooleanExtra("ifReminder", false);
            Log.e(TAG, "onReceive: we got the data " + ifReminder);
        }
    };


    private void addNoteToDb(Note note) {
        BaseNoteModel noteModel = new AddNoteModel(note.getTitle(), note.getDescription(), note.isPinned(),
                note.isArchive(), note.isTrashed(), "", "", note.getColor(),
                "", "", note.getIfReminder());

        RestApiNoteViewModel noteViewModel = new RestApiNoteViewModel(this);
        noteViewModel.addNotes((AddNoteModel) noteModel);
    }

    private void updateNoteToDB(AddNoteModel noteToEdit) {
        if (isColorChange) {
            restApiNoteViewModel.setNotesColor(noteToEdit);
        }
        restApiNoteViewModel.updateNotes(noteToEdit);
    }


    public void onRadioButtonClicked(View radioButtonView) {
        boolean checked = ((RadioButton) radioButtonView).isChecked();
        switch (radioButtonView.getId()) {
            case R.id.radioButton_white:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    setNoteColor("#FFFFFF");
                }
                break;
            case R.id.radioButton_blue:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.colorBlueNote);
                    setNoteColor("#00FFFF");
                }
                break;
            case R.id.radioButton_red:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.colorRedNote);
                    setNoteColor("#FF1A06");
                }
                break;
            case R.id.radioButton_yellow:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.colorYellowNote);
                    setNoteColor("#FFFF64");
                }
                break;

            case R.id.radioButton_orange:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.colorOrangeNote);
                    setNoteColor("#FF9900");
                }
                break;
            default:
                setNoteColor("#ffffff");
        }
    }

    private void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(add_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_archive:
                Toast.makeText(this, "archive Selected", Toast.LENGTH_SHORT).show();
                if (isEditMode) {
                    AddNoteModel addNoteModel = new AddNoteModel(noteToEdit.getTitle(), noteToEdit.getDescription(),
                            noteToEdit.isPinned(), noteToEdit.isArchived(), noteToEdit.isDeleted(),
                            noteToEdit.getCreateDate(), noteToEdit.getModifiedDate(), noteToEdit.getColor(),
                            noteToEdit.getId(), noteToEdit.getUserId(), noteToEdit.getReminder());

                    restApiNoteViewModel.setArchiveToNotes(addNoteModel);
                    updateNoteToDB(addNoteModel);
                    return true;
                }

            case R.id.menu_pinned:
                Toast.makeText(this, "Pin Selected", Toast.LENGTH_SHORT).show();
                if (isEditMode) {
                    AddNoteModel addNote = new AddNoteModel(noteToEdit.getTitle(), noteToEdit.getDescription(),
                            noteToEdit.isPinned(), noteToEdit.isArchived(), noteToEdit.isDeleted(),
                            noteToEdit.getCreateDate(), noteToEdit.getModifiedDate(), noteToEdit.getColor(),
                            noteToEdit.getId(), noteToEdit.getUserId(), noteToEdit.getReminder());

                    restApiNoteViewModel.setPinToNotes(addNote);
                    updateNoteToDB(addNote);
                }
                return true;

            case R.id.menu_reminder:
                Toast.makeText(this, "Reminder Selected", Toast.LENGTH_SHORT).show();
                if (isEditMode) {
                    DialogFragment datePicker = new DatePickerFragement();
                    datePicker.show(getSupportFragmentManager(), "date Picker");

                    DialogFragment timePicker = new TimePickerFragement();
                    timePicker.show(getSupportFragmentManager(), "time Picker");
                    restApiNoteViewModel.getReminderNoteList();
//                    updateNoteToDB(ad);
                }
                return true;

        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.YEAR, year);
        calendar.set(calendar.MONTH, month);
        calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(calendar.MINUTE, minute);


    }


    @Override
    public void onClick(View v) {
        if (v == mButtonDate) {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(calendar.YEAR);
            mMonth = calendar.get(calendar.MONTH);
            mDay = calendar.get(calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            mTextDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            restApiNoteViewModel.addReminderNoteList(noteToEdit);
            setDateTimeString("EEE ,MMM d ", calendar.getTime());
        }
        if (v == mButtonTime) {
            final Calendar calendar = Calendar.getInstance();
            mHour = calendar.get(calendar.HOUR_OF_DAY);
            mMinute = calendar.get(calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mTextTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);

            timePickerDialog.show();
            restApiNoteViewModel.addReminderNoteList(noteToEdit);
            setDateTimeString("hh : mm : ss", calendar.getTime());
        }

    }

    private void setDateTimeString(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        reminderBuilder.append(simpleDateFormat.format(date)).append(" ");
    }

    private Date convertStringToDate(String stringToConvert,String pattern)
    {
        Date convertedDate = null;
        DateFormat dateFormat = new SimpleDateFormat(pattern,Locale.getDefault());
        try {
            convertedDate = dateFormat.parse(stringToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }


    private void setReminderNotification(Date date) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);

        int requestCode = 100;
        Intent intent = new Intent(this, AlarmReceiever.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
        Log.e(TAG, "setReminderNotification method called");
    }


    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(setArchiveNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(changeColorToNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(setPinnedNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(setReminderNoteBroadcastReceiver);
    }
}


//    String title_1 = mTextTitle.getText().toString().trim();
//                String description_1 = mTextDescription.getText().toString().trim();
//                Note note_1 = new Note(title_1, description_1, noteColor, false,
//                        true, reminderBuilder.toString(), isTrashed);
//    addNoteToDb(note_1);

//    String title = mTextTitle.getText().toString().trim();
//                String description = mTextDescription.getText().toString().trim();
//                Note note = new Note(title, description, noteColor,false,
//                        false, reminderBuilder.toString(),isTrashed);

//                addNoteToDb(note);


//    private void addArchiveNotes(Note note)
//    {
//        BaseNoteModel noteModel = new BaseNoteModel(note.getTitle(),note.getDescription(),note.getColor(),
//                note.isArchive() + " ",note.isPinned() + " ",note.getIfReminder(),
//                note.isTrashed() + " ","","");
//        RestApiNoteViewModel apiNoteViewModel = new RestApiNoteViewModel(this);
//        apiNoteViewModel.getArchiveNotes(noteModel);
//    }