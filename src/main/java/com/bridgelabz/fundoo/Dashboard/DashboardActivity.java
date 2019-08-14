package com.bridgelabz.fundoo.Dashboard;

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
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bridgelabz.fundoo.LoginSignUp.View.LoginActivity;
import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.add_label_page.view.AddLabelFragment;
import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.View.AddNoteActivity;
import com.bridgelabz.fundoo.add_note_page.View.note_adapter.NotesAdapter;
import com.bridgelabz.fundoo.add_note_page.ViewModel.NoteViewModel;
import com.bridgelabz.fundoo.add_note_page.ViewModel.RestApiNoteViewModel;
import com.bridgelabz.fundoo.common.share_preference.SharePreferencesManager;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bridgelabz.fundoo.utility.AppConstants.GET_ARCHIVE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_REMINDER_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_TRASHED_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_TRASHED_NOTE_ACTION;

public class DashboardActivity extends AppCompatActivity implements NavigationView.
        OnNavigationItemSelectedListener {
    private static final String TAG = DashboardActivity.class.getName();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText mTextTitle;
    private EditText mDescription;
    private boolean isArchived = false;
    private boolean isPinned = false;
    private boolean ifReminder = false;
    private String noteColor = "#ffffff";
    private StringBuilder stringBuilder = new StringBuilder();
    private TextView mTextTakeNote;
    private RecyclerView mRecyclerView;
    private ImageView imageProfile;
    public static final int PICK_REQUEST_CODE = 10;
    private List<NoteResponseModel> noteResponseList = new ArrayList<>();
    NotesAdapter notesAdapter;
    NoteViewModel noteViewModel;
    NavigationView navigationView;
    RestApiNoteViewModel apiNoteViewModel;
    NoteResponseModel noteToDelete;
    private SharePreferencesManager sharePreferencesManager;
    Animation animZoomOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViews();
//        noteViewModel = new NoteViewModel(this);
        apiNoteViewModel = new RestApiNoteViewModel(this);
        sharePreferencesManager = new SharePreferencesManager(this);
        prepareRecycleView();
        registerBroadcastManager();
        setUpDrawer();
        setUpNavigationView();
        setClickOnTakeNoteTextView();
        setNavigationViewListener();
//        setOnClickListenerToProfile();


    }
//
//    private void setOnClickListenerToProfile() {
//
//        navigationView = findViewById(R.id.nav_View);
////        imageProfile = (ImageView) navigationView.getHeaderView(0),findViewById(R.id.);
//        imageProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DashboardActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent,PICK_REQUEST_CODE);
//            }
//        });
//    }

    private void registerBroadcastManager() {

        LocalBroadcastManager.getInstance(this).registerReceiver(getNoteBroadcastReceiver,
                new IntentFilter(GET_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(getArchiveNoteBroadcastReceiver,
                new IntentFilter(GET_ARCHIVE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(setTrashedNoteBroadcastReceiver,
                new IntentFilter(SET_TRASHED_NOTE_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(getTrashedNoteBroadcastReceiver,
                new IntentFilter(GET_TRASHED_NOTE_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(getReminderBroadcastReceiver,
                new IntentFilter(GET_REMINDER_NOTE_ACTION));

    }



    private BroadcastReceiver setTrashedNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: setTrashed LocalBroadcast is working");
            if (intent.hasExtra("isTrashed"));
            noteResponseList = (List<NoteResponseModel>) intent.getSerializableExtra("isTrashed");
            notesAdapter.setNoteModelArrayList(noteResponseList);
            notesAdapter.notifyDataSetChanged();
        }
    };


    public BroadcastReceiver getTrashedNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: getTrashed LocalBroadcast is working");
            if (intent.hasExtra("getTrashed"));
            noteResponseList = (List<NoteResponseModel>) intent.getSerializableExtra("getTrashed");
            notesAdapter.setNoteModelArrayList(noteResponseList);
            notesAdapter.notifyDataSetChanged();
        }
    };



    private BroadcastReceiver getArchiveNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.hasExtra("isArchiveStatus")) {
//                Log.e(TAG, "onReceive: isArchive is coming ");
                noteResponseList = (List<NoteResponseModel>) intent.
                         getSerializableExtra("isArchiveStatus");
                Log.e(TAG, "onReceive: getArchive LocalBroadcast is working");
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
            }
        }
    };

    private BroadcastReceiver getNoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "  getNote LocalBroadcast is working");

            if (intent.hasExtra("NoteResponseList")) {
                Log.e(TAG, "onReceive: Notes are coming");
                noteResponseList = (List<NoteResponseModel>) intent.
                        getSerializableExtra("NoteResponseList");
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
            }
        }
    };

    public BroadcastReceiver getReminderBroadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: getReminder LocalBroadcast is working");
            if (intent.hasExtra("ifReminder")){
                Log.e(TAG, "onReceive: Reminder is success");
                noteResponseList = (List<NoteResponseModel>) intent.
                        getSerializableExtra("ifReminder");
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
            }


        }
    };

    private void findViews() {
        mTextTitle = findViewById(R.id.et_title);
        mDescription = findViewById(R.id.et_description);
    }


    public void setUpDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean deleteNote(BaseNoteModel note) {
//        return noteViewModel.deleteNote(note);
        return true;
    }

    private void prepareRecycleView() {

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        apiNoteViewModel.getAllNotes();

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));// new LinearLayoutManager(DashboardActivity.this)

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesAdapter = new NotesAdapter(noteResponseList, new NotesAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(DashboardActivity.this, AddNoteActivity.class);
                intent.putExtra("noteToEdit", (Serializable) noteResponseList.get(position));
                startActivity(intent);

            }
        });
        mRecyclerView.setAdapter(notesAdapter);
        noteItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    private ItemTouchHelper noteItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT |
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                draggedViewHolder,
                              @NonNull RecyclerView.ViewHolder targetViewHolder) {
            int draggedPosition = draggedViewHolder.getAdapterPosition();
            int targetPosition = targetViewHolder.getAdapterPosition();

            notesAdapter.onItemMove(draggedPosition, targetPosition);
            Collections.swap(noteResponseList, draggedPosition, targetPosition);
            Log.e(TAG, " dragged and moved ");
            return false;

        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder swipeViewHolder, int direction) {
          final  int adapterPosition = swipeViewHolder.getAdapterPosition();
            Log.e(TAG, " The adapter position is " + adapterPosition);
             noteToDelete = notesAdapter.getNoteAt(adapterPosition);
             apiNoteViewModel.trashNotes(noteToDelete);
//
//            boolean isDeleted = deleteNote(noteToDelete);
//            if (isDeleted) {
//                noteResponseList.remove(noteToDelete);
//
////                deleteNote(notesAdapter.getNoteAt(adapterPosition));
               notesAdapter.notifyItemRemoved(adapterPosition);
//                Toast.makeText(DashboardActivity.this, " Note deleted at" + adapterPosition,
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(DashboardActivity.this, "Note couldNot be deleted ",
//                        Toast.LENGTH_SHORT).show();
//            }

        }
    });

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setClickOnTakeNoteTextView() {
        mTextTakeNote = findViewById(R.id.tv_take_note);
        mTextTakeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DashboardActivity.this, AddNoteActivity.class);
                startActivity(intent1);
                finish();
            }

        });
    }

    private void setUpNavigationView() {
        Intent intent = getIntent();

        if (intent.hasExtra("email")) {


            String email = intent.getStringExtra("email");
            String imageUrl = intent.getStringExtra("imageUrl");
            Log.e("Dashboard", email + "");

            navigationView = findViewById(R.id.nav_View);
            TextView tv_email = navigationView.getHeaderView(0).findViewById(R.id.tv_emailid);
            ImageView image = navigationView.getHeaderView(0).findViewById(R.id.image_url);
            tv_email.setText(email);
            Glide.with(this).load(imageUrl).into(image);

        } else {
            Log.e(TAG, "insert does not have extra string \"email id\" ");
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notesAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
//                noteViewModel.deleteAllNotes();
                noteResponseList.clear();
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "items in arraylist is" + noteResponseList.size());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_note:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Notes drawer menu Clicked ", Toast.LENGTH_SHORT).show();
                apiNoteViewModel.getAllNotes();
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
                break;

            case R.id.menu_reminder:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Reminder drawer menu Clicked", Toast.LENGTH_SHORT).show();
                apiNoteViewModel.getReminderNoteList();
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
                break;

            case R.id.menu_create_labels:
                closeDrawer();
                Toast.makeText(this, "Label drawer menu Clicked", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddLabelFragment addLabelFragment = new AddLabelFragment();

                fragmentTransaction.replace(R.id.container_fragment, addLabelFragment, "Add Label Fragment");
                fragmentTransaction.addToBackStack("Add Label Fragment").commit();
                break;

            case R.id.menu_archive:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Archive drawer menu Clicked", Toast.LENGTH_SHORT).show();
//                noteViewModel.getArchiveNotes();
                apiNoteViewModel.getArchiveList();
                notesAdapter.setNoteModelArrayList(noteResponseList);
                notesAdapter.notifyDataSetChanged();
                break;


            case R.id.menu_trash:
                closeDrawer();
                closeFragmentIfShowing();
                Toast.makeText(this, "Trash drawer menu Clicked", Toast.LENGTH_SHORT).show();
//                noteResponseList =noteViewModel.getAllNotes();
                apiNoteViewModel.getTrashedNoteList();
//                notesAdapter.setNoteModelArrayList(noteResponseList);
//                notesAdapter.notifyDataSetChanged();
                break;

            case R.id.menu_logout:
                closeDrawer();
                Toast.makeText(this, "Logout drawer menu Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                break;


        }

        return true;
    }

    private void closeFragmentIfShowing() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Add Label Fragment");
        if (fragment != null) {
            fragment.getFragmentManager().popBackStack();
        }
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getArchiveNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(setTrashedNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getArchiveNoteBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getReminderBroadcastReceiver);

    }
}








