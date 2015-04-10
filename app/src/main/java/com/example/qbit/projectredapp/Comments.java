package com.example.qbit.projectredapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Comments extends ActionBarActivity{
    SaveSharedPreference pm = new SaveSharedPreference();
    private TextView comments_score, comments_category, username_comments, title_comments;
    private RecyclerView commentsRecyclerView;
    private Button submit_comment_call;
    private EditText comment_details;
    private CommentsAdaptor commentAdaptor;
    private static String submit_comment_url = "http://192.168.1.21/QueryFiles/submit_comment.php";
    private static String commentsURL = "http://192.168.1.21/QueryFiles/comments.php";
    private RecyclerView.LayoutManager commentsLayoutMgr;
    private ArrayList<CommentClass> comments = new ArrayList<CommentClass>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.comments_layout);
        comments_score = (TextView) findViewById(R.id.comments_score);
        comments_category = (TextView) findViewById(R.id.comments_category);
        username_comments = (TextView) findViewById(R.id.username_comments);
        title_comments = (TextView) findViewById(R.id.title_comments);
        comment_details = (EditText) findViewById(R.id.comment_details);
        submit_comment_call = (Button) findViewById(R.id.submit_comment_call);
        final Bundle extras= getIntent().getExtras();
        comments_score.setText(extras.getString("score"));
        comments_category.setText(extras.getString("category"));
        username_comments.setText(extras.getString("username"));
        title_comments.setText(extras.getString("title"));
        String threadID  = extras.getString("threadID");
        try {
            getComments(threadID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submit_comment_call.setOnClickListener(new View.OnClickListener() {
            final String threadID = extras.getString("threadID");
            @Override
            public void onClick(View v) {
                if (pm.getUsername(getApplicationContext())==""){
                    Toast.makeText(getApplicationContext(), "Please login to comment.", Toast.LENGTH_LONG).show();
                }
                else{
                String comment_text = comment_details.getText().toString();
                try {
                    postComment(comment_text, threadID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }
        });
        commentsRecyclerView = (RecyclerView) findViewById(R.id.comments_rec_view);
        commentsLayoutMgr = new LinearLayoutManager(this);
        commentsRecyclerView.setLayoutManager(commentsLayoutMgr);
        commentAdaptor= new CommentsAdaptor(this);
        commentsRecyclerView.setAdapter(commentAdaptor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontpage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()){
            case R.id.submit:
                SubmitThread submitThread = new SubmitThread();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.comments_layout,submitThread, "submitThread");
                transaction.commit();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.logout:
                Intent i = new Intent(getApplicationContext(), LoginAndRegister.class);
                pm.logout(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Logging you out!", Toast.LENGTH_LONG).show();
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void postComment(String comment_text, final String threadID) throws JSONException {
        JSONObject comment_details = new JSONObject();
        comment_details.put("commentText", comment_text);
        comment_details.put("threadID", threadID);
        comment_details.put("username", pm.getUsername(getApplicationContext()));
        comment_details.put("password", pm.getPassword(getApplicationContext()));
        RequestQueue requestQueue =VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,submit_comment_url,comment_details,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("success")==1) {
                        Toast.makeText(Comments.this, "Response: " + response.getString("message"), Toast.LENGTH_LONG).show();
                        getComments(threadID);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Comments.this, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        });
        requestQueue.add(request);
    }



    public void getComments(String threadID) throws JSONException {
        JSONObject ID = new JSONObject();
        ID.put("threadID",threadID);

        RequestQueue requestQueue =VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,commentsURL,ID,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                comments=  parseComments(response);
                commentAdaptor.setCommentData(comments);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Comments.this, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        });
        requestQueue.add(request);
    }
    public ArrayList<CommentClass> parseComments(JSONObject response){
        ArrayList<CommentClass> parsedComments = new ArrayList<CommentClass>();
        if(response==null || response.length()==0){
            return null;
        }else{
            try {
                JSONArray arrayThreads =response.getJSONArray("comments");
                for (int i= 0; i<arrayThreads.length(); i++){
                    JSONObject currentThread=arrayThreads.getJSONObject(i);
                    CommentClass commentClass = new CommentClass();
                    String username=currentThread.getString("username");
                    String score = currentThread.getString("score");
                    String commentID = currentThread.getString("commentID");
                    String comment = currentThread.getString("comment");
                    commentClass.setCommentID(commentID);
                    commentClass.setScore(score);
                    commentClass.setUsername(username);
                    commentClass.setComment(comment);
                    parsedComments.add(commentClass);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return parsedComments;
        }
    }
}
