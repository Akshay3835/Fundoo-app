package com.bridgelabz.fundoo.add_note_page.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.data_manager.RestApiNoteDataManager;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseError;

import java.io.Serializable;
import java.util.List;

import static com.bridgelabz.fundoo.utility.AppConstants.ADD_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_ARCHIVE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_REMINDER_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.GET_TRASHED_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_ARCHIVE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_NOTES_COLOR_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_PINNED_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_TRASHED_NOTE_ACTION;
import static com.bridgelabz.fundoo.utility.AppConstants.SET_UPDATE_NOTE_ACTION;

public class RestApiNoteViewModel {
    public static final String TAG = "RestApiNoteViewModel";


    private RestApiNoteDataManager noteDataManager;
    private LocalBroadcastManager localBroadcastManager;


    public RestApiNoteViewModel(Context context) {
        noteDataManager = new RestApiNoteDataManager(context);
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    public void addNotes(AddNoteModel noteModel) {

        noteDataManager.addNote(noteModel,
                new RestApiNoteDataManager.ResponseCallback<ResponseData, ResponseError>() {
                    Intent localIntent = new Intent(ADD_NOTE_ACTION);

                    @Override
                    public void onResponse(ResponseData responseData, ResponseError responseError) {
                        boolean isAddedStatus;
                        isAddedStatus = (responseData != null);
                        Log.e(TAG, " Response isAdded " + isAddedStatus);

                        localIntent.putExtra("isNoteAdded", isAddedStatus);
                        localBroadcastManager.sendBroadcast(localIntent);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        localIntent.putExtra("throwable", throwable);
                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                });
    }

    public void getAllNotes() {
        noteDataManager.getNoteList
                (new RestApiNoteDataManager.ResponseCallback<List<NoteResponseModel>, ResponseError>() {

                    Intent localIntent = new Intent(GET_NOTE_ACTION);

                    @Override
                    public void onResponse(List<NoteResponseModel> noteResponseList, ResponseError responseError) {
                        if (noteResponseList != null) {
                            Log.e(TAG, "onResponse: AllNotes are displayed");
                            localIntent.putExtra("NoteResponseList", (Serializable) noteResponseList);
                            localBroadcastManager.sendBroadcast(localIntent);
                        } else {
                            Log.e(TAG, "onResponse UnSuccessful");
                            Log.e(TAG, "Response Error" + responseError.getName());
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        localIntent.putExtra("throwable", throwable);
                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                });

    }

    public void getArchiveList() {
        noteDataManager.getArchiveList(new RestApiNoteDataManager.ResponseCallback<List<NoteResponseModel>, ResponseError>() {
            Intent intent = new Intent(GET_ARCHIVE_ACTION);

            @Override
            public void onResponse(List<NoteResponseModel> responseData, ResponseError responseError) {

             if (responseData != null);
                Log.e(TAG, "onResponse: Archive is working");

                intent.putExtra("isArchiveStatus", (Serializable) responseData);
                localBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onFailure(Throwable throwable) {

                intent.putExtra("throwable", throwable);
                localBroadcastManager.sendBroadcast(intent);

            }
        });

   }

        public void setNotesColor (AddNoteModel addNoteModel){
            noteDataManager.setNotesColor(addNoteModel, new RestApiNoteDataManager.
                    ResponseCallback<ResponseData, ResponseError>() {

                Intent localIntent = new Intent(SET_NOTES_COLOR_ACTION);

                @Override
                public void onResponse(ResponseData responseData, ResponseError responseError) {
                    boolean isColorChange;
                    isColorChange = (responseData != null);
                    Log.e(TAG, "onResponse: Is Note Color Change : " + isColorChange);
                    localIntent.putExtra("isColorChange", isColorChange);
                    localBroadcastManager.sendBroadcast(localIntent);

                }

                @Override
                public void onFailure(Throwable throwable) {

                    localIntent.putExtra("throwable", throwable);
                    localBroadcastManager.sendBroadcast(localIntent);

                }
            });
        }

        public void updateNotes (AddNoteModel addNoteModel){
            noteDataManager.updateNotes(addNoteModel, new RestApiNoteDataManager.
                    ResponseCallback<ResponseData, ResponseError>() {

                Intent localIntent = new Intent(SET_UPDATE_NOTE_ACTION);

                @Override
                public void onResponse(ResponseData responseData, ResponseError responseError) {
                    boolean isNoteUpdate;
                    isNoteUpdate = (responseData != null);
                    Log.e(TAG, "Is Note isUpdated " + isNoteUpdate);
                    localIntent.putExtra("isNoteUpdate", isNoteUpdate);
                    localBroadcastManager.sendBroadcast(localIntent);

                }

                @Override
                public void onFailure(Throwable throwable) {

                    localIntent.putExtra("throwable", throwable);
                    localBroadcastManager.sendBroadcast(localIntent);

                }
            });
        }

        public void setArchiveToNotes (AddNoteModel addNoteModel){
            noteDataManager.setArchiveList(addNoteModel, new
                    RestApiNoteDataManager.ResponseCallback<ResponseData, ResponseError>() {

                        Intent localIntent = new Intent(SET_ARCHIVE_ACTION);

                        @Override
                        public void onResponse(ResponseData responseData, ResponseError responseErrorn) {
                            boolean isAddedArchive;
                            isAddedArchive = (responseData != null);
                            Log.e(TAG, "onResponse: isAddedArchive " + isAddedArchive);
                            localIntent.putExtra("isNoteArchived", isAddedArchive);
                            localBroadcastManager.sendBroadcast(localIntent);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            localIntent.putExtra("throwable", throwable);
                            localBroadcastManager.sendBroadcast(localIntent);

                        }
                    });
        }

        public void setPinToNotes (AddNoteModel addNoteModel){
            noteDataManager.setPinnedNotes(addNoteModel,
                    new RestApiNoteDataManager.ResponseCallback<ResponseData, ResponseError>() {

                        Intent localIntent = new Intent(SET_PINNED_NOTE_ACTION);

                        @Override
                        public void onResponse(ResponseData responseData, ResponseError responseError) {

                            boolean isPinned;
                            isPinned = (responseData != null);
                            Log.e(TAG, "onResponse: isPinned :" + isPinned);
                            localIntent.putExtra("isPinned", isPinned);
                            localBroadcastManager.sendBroadcast(localIntent);

                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Log.e(TAG, "onFailure: Failure in isPinned");
                            localIntent.putExtra("Throwable", throwable.getLocalizedMessage());
                            localBroadcastManager.sendBroadcast(localIntent);

                        }
                    });
        }

        public void trashNotes(NoteResponseModel noteResponseModel){
            noteDataManager.setTrashNotes( noteResponseModel,new RestApiNoteDataManager.
                    ResponseCallback<ResponseData, ResponseError>() {

                Intent localIntent = new Intent(SET_TRASHED_NOTE_ACTION);

                @Override
                public void onResponse(ResponseData responseData, ResponseError responseError) {
                    boolean isTrashed;
                    isTrashed = (responseData != null);
                    Log.e(TAG, "onResponse: isTrashed :" + isTrashed);
                    localIntent.putExtra("isTrashed", isTrashed);
                    localBroadcastManager.sendBroadcast(localIntent);


                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e(TAG, "onFailure: Failure in TrashNotes");
                    localIntent.putExtra("Throwable", throwable.getLocalizedMessage());
                    localBroadcastManager.sendBroadcast(localIntent);

                }
            });

        }

        public void addReminderNoteList(AddNoteModel addNoteModel) {
        noteDataManager.addReminderNotes(addNoteModel, new RestApiNoteDataManager.
                ResponseCallback<List<NoteResponseModel>, ResponseError>() {

            Intent localIntent = new Intent(GET_REMINDER_NOTE_ACTION);
            @Override
            public void onResponse(List<NoteResponseModel> responseData, ResponseError responseError) {
                boolean ifReminder;
                ifReminder = (responseData != null);
                Log.e(TAG, "onResponse: ifReminder :" + ifReminder );
                localIntent.putExtra("ifReminder",ifReminder);
                localBroadcastManager.sendBroadcast(localIntent);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: Failure in addReminderNotes" );
                localIntent.putExtra("Throwable",throwable.getLocalizedMessage());
                localBroadcastManager.sendBroadcast(localIntent);

            }
        });

        }


        public void getReminderNoteList() {
            noteDataManager.getReminderNotes(new RestApiNoteDataManager.ResponseCallback<List<NoteResponseModel>, ResponseError>() {

                Intent localIntent = new Intent(GET_REMINDER_NOTE_ACTION);
                @Override
                public void onResponse(List<NoteResponseModel> responseData, ResponseError responseError) {
                    if (responseData != null){
                        Log.e(TAG, "onResponse: getReminder is working");
                        localIntent.putExtra("getReminder", (Serializable) responseData);
                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                    else{
                        Log.e(TAG, "onResponse: getReminder is not working");
                        Log.e(TAG, "onResponse: getReminder :" + responseError.getMessage() );
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    localIntent.putExtra("Throwable",throwable.getLocalizedMessage());
                    localBroadcastManager.sendBroadcast(localIntent);

                }

            });
        }


        public void getTrashedNoteList(){
        noteDataManager.getTrashedNotes(new RestApiNoteDataManager.ResponseCallback<List<NoteResponseModel>, ResponseError>() {

            Intent localIntent = new Intent(GET_TRASHED_NOTE_ACTION);
            @Override
            public void onResponse(List<NoteResponseModel> responseModelList, ResponseError responseError) {
                if (responseModelList != null){
                    Log.e(TAG, "onResponse: getTrashed is working");
                    localIntent.putExtra("getTrashed", (Serializable) responseModelList);
                    localBroadcastManager.sendBroadcast(localIntent);
                }
                else{
                    Log.e(TAG, "onResponse: getTrashed is not working");
                    Log.e(TAG, "onResponse: getTrashed :" + responseError.getMessage() );
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                localIntent.putExtra("Throwable",throwable.getLocalizedMessage());
                localBroadcastManager.sendBroadcast(localIntent);

            }
        });
        }

    }




