package com.bridgelabz.fundoo.add_note_page.data_manager.apis;

import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.common.ResponseModel.ResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NoteRestApi
{
    @POST("notes/addNotes")
    Call<Map<String, ResponseData>> addNote(@Header("Authorization") String authKey,
                                            @Body BaseNoteModel noteModel);

    @GET("notes/getNotesList")
    Call<Map<String, ResponseData>> getNotes(@Header("Authorization") String authKey);


//ARCHIVE LOOPBACK

    @GET("notes/getArchiveNotesList")
    Call<Map<String,ResponseData>> getArchiveNotes(@Header("Authorization") String authKey);

    @POST("notes/archiveNotes")
    Call<Map<String ,ResponseData>> setArchiveToNotes(@Header("Authorization") String authKey,
                                                      @Body Map<String,Object> model);

    //REMINDER LOOPBACK
    @GET("notes/getReminderNotesList")
    Call<Map<String,ResponseData>> getReminderNotes(@Header("Authorization") String authKey);

    @POST("notes/addUpdateReminderNotes")
    Call<Map<String,ResponseData>> addReminderNotes(@Header("Authorization") String authKey,
                                                   @Body Map<String,Object> map);

    //PINNED LOOPBACK
    @POST("notes/pinUnpinNotes")
    Call<Map<String,ResponseData>> setPinnedNotes(@Header("Authorization") String authKey, @Body
                                          Map<String,Object> model);

    @POST("notes/updateNotes")
    Call<Map<String,ResponseData>> updateNote(@Header("Authorization") String authKey,
                                              @Body Map<String,String> model);

    //ColorLoopBack
    @POST("notes/changesColorNotes")
    Call<Map<String,ResponseData>> setNotesColor(@Header("Authorization") String authKey,
                                                 @Body Map<String,Object> objectMap);

//TRASHED LOOPBACK
    @GET("notes/getTrashNotesList")
    Call<Map<String,ResponseData>> getTrashedNotes(@Header("Authorization") String authKey);

    @POST("notes/trashNotes")
    Call<Map<String,ResponseData>> trashNotes(@Header("Authorization") String authKey,
                                              @Body Map<String,Object> trashMap);

}
