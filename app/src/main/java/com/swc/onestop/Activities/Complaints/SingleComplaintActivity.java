package com.swc.onestop.Activities.Complaints;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.swc.onestop.Activities.Main2Activity;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleComplaintActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Comment> myDataset;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails;
    private ComplaintCard card;
    private  String complainId,complainerName,complaintBody,complaintSubject,complaintSuppotCount;
    private  String complaintCommentCount,complaintDate;
    private Drawable supported;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_complaint);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                startActivity(new Intent(SingleComplaintActivity.this, ComplaintActivity.class));
            }
        });

        Intent i = getIntent();
        complainId = i.getStringExtra("complaintID");
        complainerName = i.getStringExtra("name");
        complaintBody = i.getStringExtra("body");
        complaintSubject = i.getStringExtra("sub");
        complaintSuppotCount = i.getStringExtra("supports");
        complaintCommentCount = i.getStringExtra("commentsCount");

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("supported");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);







        Log.i("shivam", "onCreate: "+complainId);
        sessionManager = new SessionManager(getApplicationContext());
        userDetails = sessionManager.getUserDetails();
        /* retrieve all data */


        card = new ComplaintCard(complainerName, complaintDate, getDrawable(R.drawable.ic_launcher_foreground),
                complaintSubject, complaintBody,complainId, complaintSuppotCount, complaintCommentCount, supported);

//        FirebaseFirestore.getInstance().collection("Complaint").document("Lohit").collection("complaint")
//                .document(complainId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("shivam", "DocumentSnapshot data: " + document.getData());
//                        card = new ComplaintCard(document.getData().get("name").toString(),document.getData().get("time_stamp").toString(),getDrawable(R.drawable.ic_launcher_foreground),
//                                document.getData().get("complain").toString(),document.getId(),"12","14",false);
//                    } else {
//                        Log.d("shivam", "No such document");
//                    }
//                } else {
//                    Log.d("shivam", "get failed with ", task.getException());
//                }
//            }
//        });
//        card = new ComplaintCard("Ankur","07 Jan",getDrawable(R.drawable.ic_launcher_foreground),
//                "Subject","Body","12","14",false);

        TextView x = findViewById(R.id.commentsInTotal);
        x.setText(card.getComments());

        x = findViewById(R.id.supporters);
        x.setText(card.getLikes());

        x = findViewById(R.id.nameOfComplainer);
        x.setText(card.getName());

        x = findViewById(R.id.dateOfComplaint);
        x.setText(card.getDate());

        x = findViewById(R.id.subTF);
        x.setText(card.getSubject());

        x = findViewById(R.id.msgTF);
        x.setText(card.getBody());

        LinearLayout comemnts = findViewById(R.id.layout_comment);
        comemnts.setVisibility(View.INVISIBLE);

        final ImageView y = findViewById(R.id.youSupport);
        final ImageView z = findViewById(R.id.supporters_img);

        y.setImageBitmap(bmp);



        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap current = getBitmapFromVectorDrawable(getApplicationContext(),y.getDrawable());
                final Bitmap supported = getBitmapFromVectorDrawable(getApplicationContext(),z.getDrawable());
                Bitmap unsupported = BitmapFactory.decodeResource(getResources(),R.drawable.ic_favorite_border_black_24dp);
                if (current.sameAs(supported)) {
                    //remove support
                    y.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp));

                    //supportcomplaint();
                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Complaint").document(complainId);

                    documentReference.update("supports", FieldValue.arrayRemove(userDetails.get("rollNo")));
                    TextView x;
                    x = findViewById(R.id.supporters);
                    x.setText(String.valueOf(Integer.parseInt(card.getLikes())-1));
                    Log.i("Likes",String.valueOf(Integer.parseInt(card.getLikes())-1));
                    card.setLikes(String.valueOf(Integer.parseInt(card.getLikes())-1));

                } else {

                    //add support
                    y.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp));
                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Complaint").document(complainId);

                    documentReference.update("supports", FieldValue.arrayUnion(userDetails.get("rollNo")));
                    TextView x;
                    x = findViewById(R.id.supporters);
                    x.setText(String.valueOf(Integer.parseInt(card.getLikes())+1));
                    Log.i("Likes",String.valueOf(Integer.parseInt(card.getLikes())+1));
                    card.setLikes(String.valueOf(Integer.parseInt(card.getLikes())+1));

                    //supportcomplaint(complainId);
                }

            }
        });


        mRecyclerView = findViewById(R.id.commentRecycler);
        myDataset = new ArrayList<>();

//        public void fetchcomplaints(){
            String hostel;

        myDataset.add(new Comment("Shivam","Shivam kr"));
        hostel=userDetails.get("hostel");
        DocumentReference d;
        d=FirebaseFirestore.getInstance().collection("Complaint").document(complainId);
        d.collection("comment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("Shivam","in1");
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                        Log.i("Shivam",documentSnapshot.getData().get("name").toString()+documentSnapshot.getData().get("comment").toString());

                        myDataset.add(new Comment("Shivam","Shivam kr"));
                        myDataset.add(new Comment(documentSnapshot.getData().get("name").toString(),documentSnapshot.getData().get("comment").toString()));
                    }

                } else {
                    Log.i("Shivam", "failed");
                }
            }
        });

        //live update but not working properly

//        d.collection("comment")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.i("Shivam", "Listen failed.", e);
//                            return;
//                        }
//
//
//                        for (QueryDocumentSnapshot doc : value) {
//                            Log.i("Shivam",doc.getId()+"->"+doc.getData().toString());
//                            myDataset.add(new Comment(doc.getData().get("name").toString(),doc.getData().get("comment").toString()));
//                        }
////                        Log.d(TAG, "Current cites in CA: " + cities);
//                    }
//                });
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        mAdapter = new CommentAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);


        Button add = findViewById(R.id.addCommentButton);
        final EditText com = findViewById(R.id.addCommentET);
//        com.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (com.getText().toString().isEmpty()) {
                    Toast.makeText(SingleComplaintActivity.this, "Empty Comment.", Toast.LENGTH_SHORT).show();
                } else {

                    TextView x;
   //                 x = findViewById(R.id.commentsInTotal);
//                    x.setText(String.valueOf(Integer.parseInt(card.getComments())+1));
 //                   card.setComments(String.valueOf(Integer.parseInt(card.getComments())+1));
                    Comment c = new Comment(userDetails.get("name"), com.getText().toString());


                    card.addComment(c);
                    myDataset.add(c);
                    mAdapter.notifyItemInserted(myDataset.size() - 1);
                    String comment;
                    comment = com.getText().toString();
                    addcomment(complainId, comment);


                    //add to database according to timestamp
                }
                com.setText("");
                hideKeyboard(view);


            }
        });
    }

    public void addcomment(String id, String commentText) {
        String hostel;
        Map<String, Object> commentDetail = new HashMap<>();
        String userid;


        userid = userDetails.get("id");
        String name = userDetails.get("name");
        hostel = userDetails.get("hostel");
        String comment;
        comment = commentText;
        commentDetail.put("user", userid);
        commentDetail.put("comment", comment);
        commentDetail.put("name", name);
        commentDetail.put("time_stamp", FieldValue.serverTimestamp());

        FirebaseFirestore.getInstance().collection("Complaint").document(id)
                .collection("comment").add(commentDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i("Shivam", "commented");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Shivam", "error while commenting");
            }

        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void supportcomplaint(String id){
        String hostel;
    Log.i("shivam","sc"+id);

//        final Map<String,Object> supportDetail = new HashMap<>();
        String userid;


        userid=userDetails.get("rollNo");

        hostel=userDetails.get("hostel");
//        supportDetail.put("user",userid);
//
        final DocumentReference d;

        d=FirebaseFirestore.getInstance().collection("Complaint")
                .document(hostel).collection("complaint").document(id);
        d.update("supports", FieldValue.arrayUnion(userid));
        d.update("support",true);
//        d.collection("support").whereEqualTo("user", userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    QuerySnapshot document = task.getResult();
//                    if (document.isEmpty()) {  //if not already supported
//
//                        d.collection("support").add(supportDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.i("Shivam","supported");
//                                Toast.makeText(complaints.this, "supported", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.i("Shivam","error while supporting");
//                            }
//                        });
//
//                    }
//                    else{
//                        //check support
//                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                            Log.i("Shivam","supports"+documentSnapshot.getId() + "=> " + documentSnapshot.getData());
//
//                        }
//                        Toast.makeText(complaints.this, "already supported", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Log.i("Shivam", "failed");
//                }
//            }
//        });
//
//
    }



    public static Bitmap getBitmapFromVectorDrawable(Context context, Drawable drawableId) {
        Drawable drawable = drawableId;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}