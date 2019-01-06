package call.basic.com.basiccall;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mUserId,mId,mTitle,mCompleted;
    private Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = findViewById(R.id.call);
        mUserId = findViewById(R.id.userId);
        mId = findViewById(R.id.id);
        mTitle = findViewById(R.id.title);
        mCompleted = findViewById(R.id.completed);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackGroundTask().execute();
            }
        });


    }


    public class BackGroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();

            //Create a request
            Request request = new Request.Builder()
                    .url("https://jsonplaceholder.typicode.com/todos/1")
                    .build();

            //Create a response with Gson
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new Gson();
               if (response.isSuccessful()){
                    //Serialize Obj
                   JsonData jsonData  = gson.fromJson(response.body().charStream(),JsonData.class);

                   //Deserialize Obj and set to TextView(Obviously this should be implemented onPostExecute)
                   mUserId.setText(gson.toJson(jsonData.getUserId()));
                   mId.setText(gson.toJson(jsonData.getId()));
                   mTitle.setText(gson.toJson(jsonData.getTitle()));
                   mCompleted.setText(gson.toJson(jsonData.getCompleted().toString()));
                   return null;
               }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String gistClass) {
            super.onPostExecute(gistClass);

        }
    }

}
