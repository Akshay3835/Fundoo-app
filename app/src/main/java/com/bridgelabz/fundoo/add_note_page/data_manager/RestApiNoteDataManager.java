package com.bridgelabz.fundoo.add_note_page.data_manager;

import android.content.Context;
import android.util.Log;

import com.bridgelabz.fundoo.add_note_page.Model.AddNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.add_note_page.data_manager.apis.NoteRestApi;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseError;
import com.bridgelabz.fundoo.common.RetrofitRestApiConnection;
import com.bridgelabz.fundoo.common.share_preference.SharePreferencesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApiNoteDataManager {
    public static final String TAG = "RestApiNoteDataManager";

    private SharePreferencesManager sharePreferencesManager;

    public RestApiNoteDataManager(Context context) {
        sharePreferencesManager = new SharePreferencesManager(context);
    }
//
//    private Map<String, Response > dataResponseError = new HashMap<>();
//    private ObservableImpl<Map<String,Response>> observableResponse =
//            new ObservableImpl<>(dataResponseError);


    public void addNote(AddNoteModel noteModel, final ResponseCallback<ResponseData, ResponseError>
            addNoteCallback) {


        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApiService = retrofit.create(NoteRestApi.class);


        String authKey = sharePreferencesManager.getAccessToken();
        Log.e(TAG, "addNote : BaseNoteModel :" + noteModel.toString());

        Call<Map<String, ResponseData>> call = noteRestApiService.addNote(authKey, noteModel);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "RESPONSE DATA SUCCESSFUL");
                    ResponseData responseData = response.body().get("status");
                    Log.e(TAG, response.body().toString());
                    Log.e(TAG, responseData.toString());
                    addNoteCallback.onResponse(responseData, null);
//                    Map<ResponseData,ResponseError> responseErrorMap = new HashMap<>();
//                    responseErrorMap.put(responseData,null);
                } else {
                    try {
                        Log.e(TAG, "Error ResponseModel " + response.errorBody().string());
                        addNoteCallback.onResponse(null, new ResponseError(
                                response.code() + "", response.errorBody().toString(),
                                response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, throwable.getLocalizedMessage());
                addNoteCallback.onFailure(throwable);

            }
        });
    }

    public void getNoteList(final ResponseCallback<List<NoteResponseModel>, ResponseError> getNotesCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();
        Call<Map<String, ResponseData>> call = noteRestApi.getNotes(authKey);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "RESPONSE IS SUCCESSFUL");
                    ResponseData responseData = response.body().get("data");
                    getNotesCallback.onResponse(responseData.getNoteModelList(), null);

                } else {
                    try {
                        getNotesCallback.onResponse(null, new ResponseError(
                                response.code() + "", response.errorBody().string(),
                                response.message(), null)
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {

                Log.e(TAG, "onFailure : " + throwable.getLocalizedMessage());
                getNotesCallback.onFailure(throwable);

            }
        });

    }

    public void setNotesColor(AddNoteModel noteModel, final
    ResponseCallback<ResponseData, ResponseError> setNotesColorCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();

        Map<String, Object> setNotesColor = new HashMap<>();
        List<String> noteIdList = new ArrayList<>();
        noteIdList.add(noteModel.getId());
        setNotesColor.put(" noteId : ", noteIdList);
        setNotesColor.put(" setColor : ", noteModel.getColor());
        Log.e(TAG, "setNotesColor: noteId :" + noteIdList);

        Call<Map<String, ResponseData>> call = noteRestApi.setNotesColor(authKey, setNotesColor);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Response is Successful ");
                    Log.e(TAG, "onResponse: NoteColor" + response.body().toString());
                    ResponseData responseData = response.body().get("data");
                    setNotesColorCallback.onResponse(responseData, null);

                } else {
                    try {
                        Log.e(TAG, "onResponse: Error in Response !!!" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: Throwable" + throwable.getLocalizedMessage());
                setNotesColorCallback.onFailure(throwable);

            }
        });

    }

    public void updateNotes(AddNoteModel addNoteModel, final
    ResponseCallback<ResponseData, ResponseError> responseCallback) {


        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi apiService = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();

        Map<String, String> updateNoteMap = new HashMap<>();

        updateNoteMap.put("noteId", addNoteModel.getId());
        updateNoteMap.put("title", addNoteModel.getTitle());
        updateNoteMap.put("description", addNoteModel.getDescription());

        Call<Map<String, ResponseData>> responseDataCall = apiService.updateNote(authKey, updateNoteMap);
        responseDataCall.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Response is Successful");
                    Log.e(TAG, "onResponse: updateNote" + response.body().toString());
                    ResponseData responseData = response.body().get("data");
                    responseCallback.onResponse(responseData, null);
                } else {

                    try {
                        Log.e(TAG, "Error ResponseModel " + response.errorBody().string());
                        responseCallback.onResponse(null, new ResponseError(response.code() + "",
                                response.errorBody().string(),
                                response.message(),
                                null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: updateNotes" + throwable.getLocalizedMessage());
                responseCallback.onFailure(throwable);
            }
        });

    }


    public void setArchiveList(AddNoteModel noteModel, final
    ResponseCallback<ResponseData, ResponseError> setArchiveCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();

        Map<String, Object> archiveDataMap = new HashMap<>();
        final List<String> noteIdList = new ArrayList<>();
        noteIdList.add(noteModel.getId());
        archiveDataMap.put("isArchived", true);
        archiveDataMap.put("noteIdList", noteIdList);

        Call<Map<String, ResponseData>> call = noteRestApi.setArchiveToNotes(authKey, archiveDataMap);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Response is Successful");
                    Log.e(TAG, "onResponse: setArchiveToNote " + response.body().get("data"));
                    ResponseData responseData = response.body().get("data");
                    setArchiveCallback.onResponse(responseData, null);
                } else {
                    Log.e(TAG, "Error Response Model : " + response.errorBody().toString());
                    try {
                        setArchiveCallback.onResponse(null, new ResponseError(response.code() + "",
                                response.errorBody().string(),
                                response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "Throwable : setArchiveToNote" + throwable.getLocalizedMessage());
                setArchiveCallback.onFailure(throwable);

            }
        });
    }

    public void getReminderNotes(final ResponseCallback<List<NoteResponseModel>, ResponseError>
                                         getReminderCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();


        Call<Map<String, ResponseData>> call = noteRestApi.getReminderNotes(authKey);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String,
                    ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: getReminder is Working");
                    ResponseData responseData = response.body().get("data");
                    getReminderCallback.onResponse(responseData.getNoteModelList(), null);
                } else {
                    try {
                        Log.e(TAG, "onResponse: Error in getReminder" + response.errorBody().string());
                        getReminderCallback.onResponse(null, new ResponseError(response.code() + " ",
                                response.errorBody().string(), response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {

                Log.e(TAG, "onFailure: Throwable" + throwable.getLocalizedMessage());
                getReminderCallback.onFailure(throwable);
            }
        });

    }

    public void addReminderNotes(AddNoteModel addNoteModel,
                                 final ResponseCallback<List<NoteResponseModel>, ResponseError>
                                         addReminderCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();

        Map<String, Object> addReminderMap = new HashMap<>();
        List<String> noteIdList = new ArrayList<>();
        noteIdList.add(addNoteModel.getId());
        addReminderMap.put("ifReminder", true);
        addReminderMap.put("noteIdList", noteIdList);

        Call<Map<String, ResponseData>> call = noteRestApi.addReminderNotes(authKey, addReminderMap);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: Reminder is Successful");
                    Log.e(TAG, "onResponse: ifReminder" + response.body().get("data"));
                    ResponseData responseData = response.body().get("data");
                    addReminderCallback.onResponse(responseData.getNoteModelList(), null);
                } else {
                    Log.e(TAG, "onResponse: Reminder is UnSuccessful");
                    try {
                        addReminderCallback.onResponse(null,
                                new ResponseError(response.code() + " ", response.errorBody().string(),
                                        response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: Throwable" + throwable.getLocalizedMessage());
                addReminderCallback.onFailure(throwable);
            }
        });
    }


    public void setPinnedNotes(AddNoteModel addNoteModel, final
    ResponseCallback<ResponseData, ResponseError> pinnedCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();


        Map<String, Object> pinnedDataMap = new HashMap<>();
        final List<String> noteIdList = new ArrayList<>();
        noteIdList.add(addNoteModel.getId());
        pinnedDataMap.put("isPinned", true);
        pinnedDataMap.put("noteIdList", noteIdList);


        Call<Map<String, ResponseData>> call = noteRestApi.setPinnedNotes(authKey, pinnedDataMap);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call, Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: isSuccessfully is pinned");
                    Log.e(TAG, "onResponse: setPinned : " + response.body().get("data"));
                    ResponseData responseData = response.body().get("data");
                    pinnedCallback.onResponse(responseData, null);
                } else {
                    Log.e(TAG, "onResponse: is not Successfully pinned");
                    try {
                        pinnedCallback.onResponse(null, new ResponseError(response.code() + "",
                                response.errorBody().string(), response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: Throwable" + throwable.getLocalizedMessage());
                pinnedCallback.onFailure(throwable);

            }
        });
    }

    public void getTrashedNotes(final ResponseCallback<List<NoteResponseModel>, ResponseError> getTrashNotesCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();

        Call<Map<String, ResponseData>> call = noteRestApi.getTrashedNotes(authKey);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: isTrashed is getting");
                    Log.e(TAG, "onResponse: isTrashed :" + response.body().get("data"));
                    ResponseData responseData = response.body().get("data");
                    getTrashNotesCallback.onResponse(responseData.getNoteModelList(), null);
                } else {
                    Log.e(TAG, "onResponse: isTrashed is not getting");
                    try {
                        getTrashNotesCallback.onResponse(null, new ResponseError(response.code() + " ",
                                response.errorBody().string(), response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {

                Log.e(TAG, "onFailure getTrashed Throwable :" + throwable.getLocalizedMessage());
                getTrashNotesCallback.onFailure(throwable);
            }
        });
    }

    public void setTrashNotes(NoteResponseModel noteResponseModel, final ResponseCallback<ResponseData,
            ResponseError> trashNotesCallback) {

        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);
        String authKey = sharePreferencesManager.getAccessToken();

        List<String> noteIdList = new ArrayList<>();
        noteIdList.add(noteResponseModel.getId());
        Map<String, Object> trashNotes = new HashMap<>();
        trashNotes.put("isTrashed", true);
        trashNotes.put("noteIdList", noteIdList);

        Call<Map<String, ResponseData>> call = noteRestApi.trashNotes(authKey, trashNotes);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: isTrashed is Successful");
                    Log.e(TAG, "onResponse: isTrashed  : " + response.body().get("data"));
                    ResponseData responseData = response.body().get("data");
                    trashNotesCallback.onResponse(responseData, null);

                } else {
                    Log.e(TAG, "onResponse: isTrashed is not Successful");
                    try {
                        trashNotesCallback.onResponse(null, new ResponseError
                                (response.code() + " ", response.errorBody().string(),
                                        response.message(), null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {
                Log.e(TAG, "onFailure: isTrashed Throwable" + throwable.getLocalizedMessage());
                trashNotesCallback.onFailure(throwable);

            }
        });
    }

    public void getArchiveList(final ResponseCallback<List<NoteResponseModel>, ResponseError> getArchiveCallback) {


        Retrofit retrofit = RetrofitRestApiConnection.openRetrofitConnection();
        NoteRestApi noteRestApi = retrofit.create(NoteRestApi.class);

        String authKey = sharePreferencesManager.getAccessToken();

        Call<Map<String, ResponseData>> call = noteRestApi.getArchiveNotes(authKey);
        call.enqueue(new Callback<Map<String, ResponseData>>() {
            @Override
            public void onResponse(Call<Map<String, ResponseData>> call,
                                   Response<Map<String, ResponseData>> response) {

                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: is successfully Archived");
                    Log.e(TAG, "onResponse: getArchive" + response.body().get("data"));
                    ResponseData responseData = response.body().get("data");
                    getArchiveCallback.onResponse(responseData.getNoteModelList(), null);
                } else {
                    Log.e(TAG, "onResponse : is not successful Archived");

                    try {
                        getArchiveCallback.onResponse(null, new ResponseError(
                                response.code() + " ", response.errorBody().string(),
                                response.message(), null));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//        noteIdList.add(noteModel.getId());
                }
            }

            @Override
            public void onFailure(Call<Map<String, ResponseData>> call, Throwable throwable) {

                Log.e(TAG, "onFailure: is Failed" + throwable.getLocalizedMessage());
                getArchiveCallback.onFailure(throwable);
            }
        });

    }


    public interface ResponseCallback<T, E> {
        void onResponse(T Data, E Error);

        void onFailure(Throwable throwable);
    }
}