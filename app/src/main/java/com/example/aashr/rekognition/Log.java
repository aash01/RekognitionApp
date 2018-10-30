package com.example.aashr.rekognition;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Log extends AppCompatActivity {
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("People");
    public String biggest = "07-09-18 12:07:07";
    public String username = "HIIIII";
    public boolean first = true;
    TableRow tr;
    TextView date;
    TextView time;
    TextView name;
    ArrayList<Button> arrayList = new ArrayList<>();
    TableLayout t1;
    RelativeLayout rl;
    private String count = "1";
    Button picture;
    private  String pic;
    private TextView pictureTxt;
    private TextView nameTxt;
    private TextView dateTxt;
    private TextView timeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_log);

        super.onCreate(savedInstanceState);
        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
        t1 = (TableLayout) findViewById(R.id.tableLayout);


        picture = new Button(this);

        date = new TextView(this);
        name = new TextView(this);
        time = new TextView(this);


//        pictureTxt = (TextView)findViewById(R.id.textView2);
//        nameTxt = (TextView) findViewById(R.id.nameTxtView);
//        dateTxt = (TextView) findViewById(R.id.textView);
//        timeTxt = (TextView) findViewById(R.id.timeTxtView);



//        View mainLayout = inflater.inflate(R.layout.main_layout, null, false);





//
//        if(rl == null){
//            Toast.makeText(Log.this, "RIP", Toast.LENGTH_LONG).show();
//        }
        //rl.addView(name);

//        value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//        name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(first){

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        tr = new TableRow(Log.this);

                        time = new TextView(Log.this);
                        name = new TextView(Log.this);
                        date = new TextView(Log.this);
                        picture = new Button(Log.this);


                        name.setPadding(20,0,0,0);
                        time.setPadding(45,0,0,0);
                        date.setPadding(45,0,0,0);
                        picture.setPadding(20,0,0,0);
//                        picture.setBackgroundColor(Color.RED);
//                        picture.text
                        picture.setText("VIEW");
                        picture.setTextSize(20);
                        picture.setHeight(25);


//                        picture.setContentDescription(d.getKey());
//                        }catch (java.lang.NullPointerException e){
//                            picture.setTag(d.getKey());
//
//                        }
                        picture.setTag(d.getKey());
                        picture.setWidth(3);

//                        picture.setX(pictureTxt.getX());
//                        name.setX(nameTxt.getX());
//                        time.setX(timeTxt.getX());
//                        date.setX(dateTxt.getX());


                        time.setText(d.getKey().substring(d.getKey().indexOf(" ")));

                        name.setText(d.getValue().toString());
                        tr.addView(name);


                        String dateTxt = d.getKey().substring(d.getKey().indexOf('-')+1,d.getKey().lastIndexOf('-')) +d.getKey().substring(d.getKey().lastIndexOf('-'),d.getKey().indexOf(" ")) +"-" +d.getKey().substring(0,d.getKey().indexOf('-'));

                        date.setText(dateTxt);


                        tr.addView(time);
                        tr.addView(date);
                        tr.addView(picture);
                        t1.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        first = false;
                        pic = d.getKey();


                        picture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getApplicationContext(),GuestPicture.class);
                                i.putExtra("time", view.getTag().toString());

                                startActivity(i);
                            }
                        });



                    }
                }else {


                    tr = new TableRow(Log.this);

                    date = new TextView(Log.this);
                    name = new TextView(Log.this);
                    time = new TextView(Log.this);
                    picture = new Button(Log.this);
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        if (d.getKey().toString().compareTo(biggest) > 0) {
                            //Toast.makeText(Log.this, d.getValue().toString(), Toast.LENGTH_SHORT).show();
                            biggest = d.getKey();
                            username = d.getValue().toString();
                            //aToast.makeText(Log.this, biggest, Toast.LENGTH_LONG).show();
                        }

                        //
                    }


                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    params.leftMargin = 107;

                    String dateTxt = biggest.substring(biggest.indexOf('-')+1,biggest.lastIndexOf('-')) +biggest.substring(biggest.lastIndexOf('-'),biggest.indexOf(" ")) +"-" +biggest.substring(0,biggest.indexOf('-'));


                    name.setText(username);
                    date.setText(dateTxt);
                    time.setText(biggest.substring(biggest.indexOf(" ")));

                    name.setPadding(20,0,0,0);
                    time.setPadding(45,0,0,0);
                    date.setPadding(45,0,0,0);
                    picture.setPadding(20,0,0,0);

                    picture.setTextSize(20);
                    picture.setText("VIEW");
                    picture.setTag(biggest);
                    tr.addView(name);
                    //name.setPadding(100, 0, 100, 0);
                    picture.setWidth(3);
                    tr.addView(time);
                    tr.addView(date);
                    tr.addView(picture);
                    picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(),GuestPicture.class);
                            i.putExtra("time", view.getTag().toString());

                            startActivity(i);
                        }
                    });
                    t1.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }







//                NotificationManager notificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                List<NotificationChannel> notificationChannels = notificationManager.getNotificationChannels();
//
//
//                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Log.this)
//                        .setContentTitle("ALERT")
//                        .setContentText("NEW GUEST")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                mBuilder.notify();



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
