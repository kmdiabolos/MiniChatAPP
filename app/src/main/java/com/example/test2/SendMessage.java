package com.example.test2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SendMessage extends MainActivity {

    private Button send,exit,btn2;
    private EditText edittext;
    private TextView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        exit = (Button) findViewById(R.id.exit);
        send = (Button) findViewById(R.id.send);
        edittext = (EditText) findViewById(R.id.field);
        chat = (TextView) findViewById(R.id.chat);




        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String message = edittext.getText().toString(); //메세지에 edittext에있는것을 넣어라

                if(message.equals("")){;} //메세지가 아무것도 없으면 아무것도 하지마라
                else{
                    chat.append(message+"\n"); //텍스트뷰에 메세지 나타냄

                    edittext.setText(""); //edittext 초기화
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //animateFAB();
                finish();
            }
        });


    }
}
