package com.adriantache.bigfivepersonalitytest;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //define answers array
    int[] answers = new int[51];
    //define all radio buttons for later use
    RadioButton question1radioButton1;
    RadioButton question1radioButton2;
    RadioButton question1radioButton3;
    RadioButton question1radioButton4;
    RadioButton question1radioButton5;
    RadioButton question2radioButton1;
    RadioButton question2radioButton2;
    RadioButton question2radioButton3;
    RadioButton question2radioButton4;
    RadioButton question2radioButton5;
    RadioButton question3radioButton1;
    RadioButton question3radioButton2;
    RadioButton question3radioButton3;
    RadioButton question3radioButton4;
    RadioButton question3radioButton5;
    RadioButton question4radioButton1;
    RadioButton question4radioButton2;
    RadioButton question4radioButton3;
    RadioButton question4radioButton4;
    RadioButton question4radioButton5;
    RadioButton question5radioButton1;
    RadioButton question5radioButton2;
    RadioButton question5radioButton3;
    RadioButton question5radioButton4;
    RadioButton question5radioButton5;
    RadioButton question6radioButton1;
    RadioButton question6radioButton2;
    RadioButton question6radioButton3;
    RadioButton question6radioButton4;
    RadioButton question6radioButton5;
    RadioButton question7radioButton1;
    RadioButton question7radioButton2;
    RadioButton question7radioButton3;
    RadioButton question7radioButton4;
    RadioButton question7radioButton5;
    RadioButton question8radioButton1;
    RadioButton question8radioButton2;
    RadioButton question8radioButton3;
    RadioButton question8radioButton4;
    RadioButton question8radioButton5;
    RadioButton question9radioButton1;
    RadioButton question9radioButton2;
    RadioButton question9radioButton3;
    RadioButton question9radioButton4;
    RadioButton question9radioButton5;
    RadioButton question10radioButton1;
    RadioButton question10radioButton2;
    RadioButton question10radioButton3;
    RadioButton question10radioButton4;
    RadioButton question10radioButton5;
    RadioButton question11radioButton1;
    RadioButton question11radioButton2;
    RadioButton question11radioButton3;
    RadioButton question11radioButton4;
    RadioButton question11radioButton5;
    RadioButton question12radioButton1;
    RadioButton question12radioButton2;
    RadioButton question12radioButton3;
    RadioButton question12radioButton4;
    RadioButton question12radioButton5;
    RadioButton question13radioButton1;
    RadioButton question13radioButton2;
    RadioButton question13radioButton3;
    RadioButton question13radioButton4;
    RadioButton question13radioButton5;
    RadioButton question14radioButton1;
    RadioButton question14radioButton2;
    RadioButton question14radioButton3;
    RadioButton question14radioButton4;
    RadioButton question14radioButton5;
    RadioButton question15radioButton1;
    RadioButton question15radioButton2;
    RadioButton question15radioButton3;
    RadioButton question15radioButton4;
    RadioButton question15radioButton5;
    RadioButton question16radioButton1;
    RadioButton question16radioButton2;
    RadioButton question16radioButton3;
    RadioButton question16radioButton4;
    RadioButton question16radioButton5;
    RadioButton question17radioButton1;
    RadioButton question17radioButton2;
    RadioButton question17radioButton3;
    RadioButton question17radioButton4;
    RadioButton question17radioButton5;
    RadioButton question18radioButton1;
    RadioButton question18radioButton2;
    RadioButton question18radioButton3;
    RadioButton question18radioButton4;
    RadioButton question18radioButton5;
    RadioButton question19radioButton1;
    RadioButton question19radioButton2;
    RadioButton question19radioButton3;
    RadioButton question19radioButton4;
    RadioButton question19radioButton5;
    RadioButton question20radioButton1;
    RadioButton question20radioButton2;
    RadioButton question20radioButton3;
    RadioButton question20radioButton4;
    RadioButton question20radioButton5;
    RadioButton question21radioButton1;
    RadioButton question21radioButton2;
    RadioButton question21radioButton3;
    RadioButton question21radioButton4;
    RadioButton question21radioButton5;
    RadioButton question22radioButton1;
    RadioButton question22radioButton2;
    RadioButton question22radioButton3;
    RadioButton question22radioButton4;
    RadioButton question22radioButton5;
    RadioButton question23radioButton1;
    RadioButton question23radioButton2;
    RadioButton question23radioButton3;
    RadioButton question23radioButton4;
    RadioButton question23radioButton5;
    RadioButton question24radioButton1;
    RadioButton question24radioButton2;
    RadioButton question24radioButton3;
    RadioButton question24radioButton4;
    RadioButton question24radioButton5;
    RadioButton question25radioButton1;
    RadioButton question25radioButton2;
    RadioButton question25radioButton3;
    RadioButton question25radioButton4;
    RadioButton question25radioButton5;
    RadioButton question26radioButton1;
    RadioButton question26radioButton2;
    RadioButton question26radioButton3;
    RadioButton question26radioButton4;
    RadioButton question26radioButton5;
    RadioButton question27radioButton1;
    RadioButton question27radioButton2;
    RadioButton question27radioButton3;
    RadioButton question27radioButton4;
    RadioButton question27radioButton5;
    RadioButton question28radioButton1;
    RadioButton question28radioButton2;
    RadioButton question28radioButton3;
    RadioButton question28radioButton4;
    RadioButton question28radioButton5;
    RadioButton question29radioButton1;
    RadioButton question29radioButton2;
    RadioButton question29radioButton3;
    RadioButton question29radioButton4;
    RadioButton question29radioButton5;
    RadioButton question30radioButton1;
    RadioButton question30radioButton2;
    RadioButton question30radioButton3;
    RadioButton question30radioButton4;
    RadioButton question30radioButton5;
    RadioButton question31radioButton1;
    RadioButton question31radioButton2;
    RadioButton question31radioButton3;
    RadioButton question31radioButton4;
    RadioButton question31radioButton5;
    RadioButton question32radioButton1;
    RadioButton question32radioButton2;
    RadioButton question32radioButton3;
    RadioButton question32radioButton4;
    RadioButton question32radioButton5;
    RadioButton question33radioButton1;
    RadioButton question33radioButton2;
    RadioButton question33radioButton3;
    RadioButton question33radioButton4;
    RadioButton question33radioButton5;
    RadioButton question34radioButton1;
    RadioButton question34radioButton2;
    RadioButton question34radioButton3;
    RadioButton question34radioButton4;
    RadioButton question34radioButton5;
    RadioButton question35radioButton1;
    RadioButton question35radioButton2;
    RadioButton question35radioButton3;
    RadioButton question35radioButton4;
    RadioButton question35radioButton5;
    RadioButton question36radioButton1;
    RadioButton question36radioButton2;
    RadioButton question36radioButton3;
    RadioButton question36radioButton4;
    RadioButton question36radioButton5;
    RadioButton question37radioButton1;
    RadioButton question37radioButton2;
    RadioButton question37radioButton3;
    RadioButton question37radioButton4;
    RadioButton question37radioButton5;
    RadioButton question38radioButton1;
    RadioButton question38radioButton2;
    RadioButton question38radioButton3;
    RadioButton question38radioButton4;
    RadioButton question38radioButton5;
    RadioButton question39radioButton1;
    RadioButton question39radioButton2;
    RadioButton question39radioButton3;
    RadioButton question39radioButton4;
    RadioButton question39radioButton5;
    RadioButton question40radioButton1;
    RadioButton question40radioButton2;
    RadioButton question40radioButton3;
    RadioButton question40radioButton4;
    RadioButton question40radioButton5;
    RadioButton question41radioButton1;
    RadioButton question41radioButton2;
    RadioButton question41radioButton3;
    RadioButton question41radioButton4;
    RadioButton question41radioButton5;
    RadioButton question42radioButton1;
    RadioButton question42radioButton2;
    RadioButton question42radioButton3;
    RadioButton question42radioButton4;
    RadioButton question42radioButton5;
    RadioButton question43radioButton1;
    RadioButton question43radioButton2;
    RadioButton question43radioButton3;
    RadioButton question43radioButton4;
    RadioButton question43radioButton5;
    RadioButton question44radioButton1;
    RadioButton question44radioButton2;
    RadioButton question44radioButton3;
    RadioButton question44radioButton4;
    RadioButton question44radioButton5;
    RadioButton question45radioButton1;
    RadioButton question45radioButton2;
    RadioButton question45radioButton3;
    RadioButton question45radioButton4;
    RadioButton question45radioButton5;
    RadioButton question46radioButton1;
    RadioButton question46radioButton2;
    RadioButton question46radioButton3;
    RadioButton question46radioButton4;
    RadioButton question46radioButton5;
    RadioButton question47radioButton1;
    RadioButton question47radioButton2;
    RadioButton question47radioButton3;
    RadioButton question47radioButton4;
    RadioButton question47radioButton5;
    RadioButton question48radioButton1;
    RadioButton question48radioButton2;
    RadioButton question48radioButton3;
    RadioButton question48radioButton4;
    RadioButton question48radioButton5;
    RadioButton question49radioButton1;
    RadioButton question49radioButton2;
    RadioButton question49radioButton3;
    RadioButton question49radioButton4;
    RadioButton question49radioButton5;
    RadioButton question50radioButton1;
    RadioButton question50radioButton2;
    RadioButton question50radioButton3;
    RadioButton question50radioButton4;
    RadioButton question50radioButton5;
    // todo remove this after testing
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find all radio button views for later use
        question1radioButton1 = findViewById(R.id.question1radioButton1);
        question1radioButton2 = findViewById(R.id.question1radioButton2);
        question1radioButton3 = findViewById(R.id.question1radioButton3);
        question1radioButton4 = findViewById(R.id.question1radioButton4);
        question1radioButton5 = findViewById(R.id.question1radioButton5);
        question2radioButton1 = findViewById(R.id.question2radioButton1);
        question2radioButton2 = findViewById(R.id.question2radioButton2);
        question2radioButton3 = findViewById(R.id.question2radioButton3);
        question2radioButton4 = findViewById(R.id.question2radioButton4);
        question2radioButton5 = findViewById(R.id.question2radioButton5);
        question3radioButton1 = findViewById(R.id.question3radioButton1);
        question3radioButton2 = findViewById(R.id.question3radioButton2);
        question3radioButton3 = findViewById(R.id.question3radioButton3);
        question3radioButton4 = findViewById(R.id.question3radioButton4);
        question3radioButton5 = findViewById(R.id.question3radioButton5);
        question4radioButton1 = findViewById(R.id.question4radioButton1);
        question4radioButton2 = findViewById(R.id.question4radioButton2);
        question4radioButton3 = findViewById(R.id.question4radioButton3);
        question4radioButton4 = findViewById(R.id.question4radioButton4);
        question4radioButton5 = findViewById(R.id.question4radioButton5);
        question5radioButton1 = findViewById(R.id.question5radioButton1);
        question5radioButton2 = findViewById(R.id.question5radioButton2);
        question5radioButton3 = findViewById(R.id.question5radioButton3);
        question5radioButton4 = findViewById(R.id.question5radioButton4);
        question5radioButton5 = findViewById(R.id.question5radioButton5);
        question6radioButton1 = findViewById(R.id.question6radioButton1);
        question6radioButton2 = findViewById(R.id.question6radioButton2);
        question6radioButton3 = findViewById(R.id.question6radioButton3);
        question6radioButton4 = findViewById(R.id.question6radioButton4);
        question6radioButton5 = findViewById(R.id.question6radioButton5);
        question7radioButton1 = findViewById(R.id.question7radioButton1);
        question7radioButton2 = findViewById(R.id.question7radioButton2);
        question7radioButton3 = findViewById(R.id.question7radioButton3);
        question7radioButton4 = findViewById(R.id.question7radioButton4);
        question7radioButton5 = findViewById(R.id.question7radioButton5);
        question8radioButton1 = findViewById(R.id.question8radioButton1);
        question8radioButton2 = findViewById(R.id.question8radioButton2);
        question8radioButton3 = findViewById(R.id.question8radioButton3);
        question8radioButton4 = findViewById(R.id.question8radioButton4);
        question8radioButton5 = findViewById(R.id.question8radioButton5);
        question9radioButton1 = findViewById(R.id.question9radioButton1);
        question9radioButton2 = findViewById(R.id.question9radioButton2);
        question9radioButton3 = findViewById(R.id.question9radioButton3);
        question9radioButton4 = findViewById(R.id.question9radioButton4);
        question9radioButton5 = findViewById(R.id.question9radioButton5);
        question10radioButton1 = findViewById(R.id.question10radioButton1);
        question10radioButton2 = findViewById(R.id.question10radioButton2);
        question10radioButton3 = findViewById(R.id.question10radioButton3);
        question10radioButton4 = findViewById(R.id.question10radioButton4);
        question10radioButton5 = findViewById(R.id.question10radioButton5);
        question11radioButton1 = findViewById(R.id.question11radioButton1);
        question11radioButton2 = findViewById(R.id.question11radioButton2);
        question11radioButton3 = findViewById(R.id.question11radioButton3);
        question11radioButton4 = findViewById(R.id.question11radioButton4);
        question11radioButton5 = findViewById(R.id.question11radioButton5);
        question12radioButton1 = findViewById(R.id.question12radioButton1);
        question12radioButton2 = findViewById(R.id.question12radioButton2);
        question12radioButton3 = findViewById(R.id.question12radioButton3);
        question12radioButton4 = findViewById(R.id.question12radioButton4);
        question12radioButton5 = findViewById(R.id.question12radioButton5);
        question13radioButton1 = findViewById(R.id.question13radioButton1);
        question13radioButton2 = findViewById(R.id.question13radioButton2);
        question13radioButton3 = findViewById(R.id.question13radioButton3);
        question13radioButton4 = findViewById(R.id.question13radioButton4);
        question13radioButton5 = findViewById(R.id.question13radioButton5);
        question14radioButton1 = findViewById(R.id.question14radioButton1);
        question14radioButton2 = findViewById(R.id.question14radioButton2);
        question14radioButton3 = findViewById(R.id.question14radioButton3);
        question14radioButton4 = findViewById(R.id.question14radioButton4);
        question14radioButton5 = findViewById(R.id.question14radioButton5);
        question15radioButton1 = findViewById(R.id.question15radioButton1);
        question15radioButton2 = findViewById(R.id.question15radioButton2);
        question15radioButton3 = findViewById(R.id.question15radioButton3);
        question15radioButton4 = findViewById(R.id.question15radioButton4);
        question15radioButton5 = findViewById(R.id.question15radioButton5);
        question16radioButton1 = findViewById(R.id.question16radioButton1);
        question16radioButton2 = findViewById(R.id.question16radioButton2);
        question16radioButton3 = findViewById(R.id.question16radioButton3);
        question16radioButton4 = findViewById(R.id.question16radioButton4);
        question16radioButton5 = findViewById(R.id.question16radioButton5);
        question17radioButton1 = findViewById(R.id.question17radioButton1);
        question17radioButton2 = findViewById(R.id.question17radioButton2);
        question17radioButton3 = findViewById(R.id.question17radioButton3);
        question17radioButton4 = findViewById(R.id.question17radioButton4);
        question17radioButton5 = findViewById(R.id.question17radioButton5);
        question18radioButton1 = findViewById(R.id.question18radioButton1);
        question18radioButton2 = findViewById(R.id.question18radioButton2);
        question18radioButton3 = findViewById(R.id.question18radioButton3);
        question18radioButton4 = findViewById(R.id.question18radioButton4);
        question18radioButton5 = findViewById(R.id.question18radioButton5);
        question19radioButton1 = findViewById(R.id.question19radioButton1);
        question19radioButton2 = findViewById(R.id.question19radioButton2);
        question19radioButton3 = findViewById(R.id.question19radioButton3);
        question19radioButton4 = findViewById(R.id.question19radioButton4);
        question19radioButton5 = findViewById(R.id.question19radioButton5);
        question20radioButton1 = findViewById(R.id.question20radioButton1);
        question20radioButton2 = findViewById(R.id.question20radioButton2);
        question20radioButton3 = findViewById(R.id.question20radioButton3);
        question20radioButton4 = findViewById(R.id.question20radioButton4);
        question20radioButton5 = findViewById(R.id.question20radioButton5);
        question21radioButton1 = findViewById(R.id.question21radioButton1);
        question21radioButton2 = findViewById(R.id.question21radioButton2);
        question21radioButton3 = findViewById(R.id.question21radioButton3);
        question21radioButton4 = findViewById(R.id.question21radioButton4);
        question21radioButton5 = findViewById(R.id.question21radioButton5);
        question22radioButton1 = findViewById(R.id.question22radioButton1);
        question22radioButton2 = findViewById(R.id.question22radioButton2);
        question22radioButton3 = findViewById(R.id.question22radioButton3);
        question22radioButton4 = findViewById(R.id.question22radioButton4);
        question22radioButton5 = findViewById(R.id.question22radioButton5);
        question23radioButton1 = findViewById(R.id.question23radioButton1);
        question23radioButton2 = findViewById(R.id.question23radioButton2);
        question23radioButton3 = findViewById(R.id.question23radioButton3);
        question23radioButton4 = findViewById(R.id.question23radioButton4);
        question23radioButton5 = findViewById(R.id.question23radioButton5);
        question24radioButton1 = findViewById(R.id.question24radioButton1);
        question24radioButton2 = findViewById(R.id.question24radioButton2);
        question24radioButton3 = findViewById(R.id.question24radioButton3);
        question24radioButton4 = findViewById(R.id.question24radioButton4);
        question24radioButton5 = findViewById(R.id.question24radioButton5);
        question25radioButton1 = findViewById(R.id.question25radioButton1);
        question25radioButton2 = findViewById(R.id.question25radioButton2);
        question25radioButton3 = findViewById(R.id.question25radioButton3);
        question25radioButton4 = findViewById(R.id.question25radioButton4);
        question25radioButton5 = findViewById(R.id.question25radioButton5);
        question26radioButton1 = findViewById(R.id.question26radioButton1);
        question26radioButton2 = findViewById(R.id.question26radioButton2);
        question26radioButton3 = findViewById(R.id.question26radioButton3);
        question26radioButton4 = findViewById(R.id.question26radioButton4);
        question26radioButton5 = findViewById(R.id.question26radioButton5);
        question27radioButton1 = findViewById(R.id.question27radioButton1);
        question27radioButton2 = findViewById(R.id.question27radioButton2);
        question27radioButton3 = findViewById(R.id.question27radioButton3);
        question27radioButton4 = findViewById(R.id.question27radioButton4);
        question27radioButton5 = findViewById(R.id.question27radioButton5);
        question28radioButton1 = findViewById(R.id.question28radioButton1);
        question28radioButton2 = findViewById(R.id.question28radioButton2);
        question28radioButton3 = findViewById(R.id.question28radioButton3);
        question28radioButton4 = findViewById(R.id.question28radioButton4);
        question28radioButton5 = findViewById(R.id.question28radioButton5);
        question29radioButton1 = findViewById(R.id.question29radioButton1);
        question29radioButton2 = findViewById(R.id.question29radioButton2);
        question29radioButton3 = findViewById(R.id.question29radioButton3);
        question29radioButton4 = findViewById(R.id.question29radioButton4);
        question29radioButton5 = findViewById(R.id.question29radioButton5);
        question30radioButton1 = findViewById(R.id.question30radioButton1);
        question30radioButton2 = findViewById(R.id.question30radioButton2);
        question30radioButton3 = findViewById(R.id.question30radioButton3);
        question30radioButton4 = findViewById(R.id.question30radioButton4);
        question30radioButton5 = findViewById(R.id.question30radioButton5);
        question31radioButton1 = findViewById(R.id.question31radioButton1);
        question31radioButton2 = findViewById(R.id.question31radioButton2);
        question31radioButton3 = findViewById(R.id.question31radioButton3);
        question31radioButton4 = findViewById(R.id.question31radioButton4);
        question31radioButton5 = findViewById(R.id.question31radioButton5);
        question32radioButton1 = findViewById(R.id.question32radioButton1);
        question32radioButton2 = findViewById(R.id.question32radioButton2);
        question32radioButton3 = findViewById(R.id.question32radioButton3);
        question32radioButton4 = findViewById(R.id.question32radioButton4);
        question32radioButton5 = findViewById(R.id.question32radioButton5);
        question33radioButton1 = findViewById(R.id.question33radioButton1);
        question33radioButton2 = findViewById(R.id.question33radioButton2);
        question33radioButton3 = findViewById(R.id.question33radioButton3);
        question33radioButton4 = findViewById(R.id.question33radioButton4);
        question33radioButton5 = findViewById(R.id.question33radioButton5);
        question34radioButton1 = findViewById(R.id.question34radioButton1);
        question34radioButton2 = findViewById(R.id.question34radioButton2);
        question34radioButton3 = findViewById(R.id.question34radioButton3);
        question34radioButton4 = findViewById(R.id.question34radioButton4);
        question34radioButton5 = findViewById(R.id.question34radioButton5);
        question35radioButton1 = findViewById(R.id.question35radioButton1);
        question35radioButton2 = findViewById(R.id.question35radioButton2);
        question35radioButton3 = findViewById(R.id.question35radioButton3);
        question35radioButton4 = findViewById(R.id.question35radioButton4);
        question35radioButton5 = findViewById(R.id.question35radioButton5);
        question36radioButton1 = findViewById(R.id.question36radioButton1);
        question36radioButton2 = findViewById(R.id.question36radioButton2);
        question36radioButton3 = findViewById(R.id.question36radioButton3);
        question36radioButton4 = findViewById(R.id.question36radioButton4);
        question36radioButton5 = findViewById(R.id.question36radioButton5);
        question37radioButton1 = findViewById(R.id.question37radioButton1);
        question37radioButton2 = findViewById(R.id.question37radioButton2);
        question37radioButton3 = findViewById(R.id.question37radioButton3);
        question37radioButton4 = findViewById(R.id.question37radioButton4);
        question37radioButton5 = findViewById(R.id.question37radioButton5);
        question38radioButton1 = findViewById(R.id.question38radioButton1);
        question38radioButton2 = findViewById(R.id.question38radioButton2);
        question38radioButton3 = findViewById(R.id.question38radioButton3);
        question38radioButton4 = findViewById(R.id.question38radioButton4);
        question38radioButton5 = findViewById(R.id.question38radioButton5);
        question39radioButton1 = findViewById(R.id.question39radioButton1);
        question39radioButton2 = findViewById(R.id.question39radioButton2);
        question39radioButton3 = findViewById(R.id.question39radioButton3);
        question39radioButton4 = findViewById(R.id.question39radioButton4);
        question39radioButton5 = findViewById(R.id.question39radioButton5);
        question40radioButton1 = findViewById(R.id.question40radioButton1);
        question40radioButton2 = findViewById(R.id.question40radioButton2);
        question40radioButton3 = findViewById(R.id.question40radioButton3);
        question40radioButton4 = findViewById(R.id.question40radioButton4);
        question40radioButton5 = findViewById(R.id.question40radioButton5);
        question41radioButton1 = findViewById(R.id.question41radioButton1);
        question41radioButton2 = findViewById(R.id.question41radioButton2);
        question41radioButton3 = findViewById(R.id.question41radioButton3);
        question41radioButton4 = findViewById(R.id.question41radioButton4);
        question41radioButton5 = findViewById(R.id.question41radioButton5);
        question42radioButton1 = findViewById(R.id.question42radioButton1);
        question42radioButton2 = findViewById(R.id.question42radioButton2);
        question42radioButton3 = findViewById(R.id.question42radioButton3);
        question42radioButton4 = findViewById(R.id.question42radioButton4);
        question42radioButton5 = findViewById(R.id.question42radioButton5);
        question43radioButton1 = findViewById(R.id.question43radioButton1);
        question43radioButton2 = findViewById(R.id.question43radioButton2);
        question43radioButton3 = findViewById(R.id.question43radioButton3);
        question43radioButton4 = findViewById(R.id.question43radioButton4);
        question43radioButton5 = findViewById(R.id.question43radioButton5);
        question44radioButton1 = findViewById(R.id.question44radioButton1);
        question44radioButton2 = findViewById(R.id.question44radioButton2);
        question44radioButton3 = findViewById(R.id.question44radioButton3);
        question44radioButton4 = findViewById(R.id.question44radioButton4);
        question44radioButton5 = findViewById(R.id.question44radioButton5);
        question45radioButton1 = findViewById(R.id.question45radioButton1);
        question45radioButton2 = findViewById(R.id.question45radioButton2);
        question45radioButton3 = findViewById(R.id.question45radioButton3);
        question45radioButton4 = findViewById(R.id.question45radioButton4);
        question45radioButton5 = findViewById(R.id.question45radioButton5);
        question46radioButton1 = findViewById(R.id.question46radioButton1);
        question46radioButton2 = findViewById(R.id.question46radioButton2);
        question46radioButton3 = findViewById(R.id.question46radioButton3);
        question46radioButton4 = findViewById(R.id.question46radioButton4);
        question46radioButton5 = findViewById(R.id.question46radioButton5);
        question47radioButton1 = findViewById(R.id.question47radioButton1);
        question47radioButton2 = findViewById(R.id.question47radioButton2);
        question47radioButton3 = findViewById(R.id.question47radioButton3);
        question47radioButton4 = findViewById(R.id.question47radioButton4);
        question47radioButton5 = findViewById(R.id.question47radioButton5);
        question48radioButton1 = findViewById(R.id.question48radioButton1);
        question48radioButton2 = findViewById(R.id.question48radioButton2);
        question48radioButton3 = findViewById(R.id.question48radioButton3);
        question48radioButton4 = findViewById(R.id.question48radioButton4);
        question48radioButton5 = findViewById(R.id.question48radioButton5);
        question49radioButton1 = findViewById(R.id.question49radioButton1);
        question49radioButton2 = findViewById(R.id.question49radioButton2);
        question49radioButton3 = findViewById(R.id.question49radioButton3);
        question49radioButton4 = findViewById(R.id.question49radioButton4);
        question49radioButton5 = findViewById(R.id.question49radioButton5);
        question50radioButton1 = findViewById(R.id.question50radioButton1);
        question50radioButton2 = findViewById(R.id.question50radioButton2);
        question50radioButton3 = findViewById(R.id.question50radioButton3);
        question50radioButton4 = findViewById(R.id.question50radioButton4);
        question50radioButton5 = findViewById(R.id.question50radioButton5);

    }

    int extraversion;
    int agreeableness;
    int conscientiousness;
    int emotionalStability;
    int intellectImagination;

    public void submit(View view) {
        //todo add check whether to reset or not

        //todo remove test function
        int i=0;
        while (i<50){
            i++;
            answers[i]=1;
        }
        calculate();
        //todo remove test function

        //verify all questions are answered
        //verify();


    }

    // verify all checkboxes and codify them into an array
    // if a question is not filled it, the method stops, and calls notCompleted(question, view)
    public void verify() {
        if (question1radioButton1.isChecked()) answers[1] = 1;
        else if (question1radioButton2.isChecked()) answers[1] = 2;
        else if (question1radioButton3.isChecked()) answers[1] = 3;
        else if (question1radioButton4.isChecked()) answers[1] = 4;
        else if (question1radioButton5.isChecked()) answers[1] = 5;
        else {
            notCompleted(1, question1radioButton1);
            return;
        }
        if (question2radioButton1.isChecked()) answers[2] = 1;
        else if (question2radioButton2.isChecked()) answers[2] = 2;
        else if (question2radioButton3.isChecked()) answers[2] = 3;
        else if (question2radioButton4.isChecked()) answers[2] = 4;
        else if (question2radioButton5.isChecked()) answers[2] = 5;
        else {
            notCompleted(2, question2radioButton1);
            return;
        }
        if (question3radioButton1.isChecked()) answers[3] = 1;
        else if (question3radioButton2.isChecked()) answers[3] = 2;
        else if (question3radioButton3.isChecked()) answers[3] = 3;
        else if (question3radioButton4.isChecked()) answers[3] = 4;
        else if (question3radioButton5.isChecked()) answers[3] = 5;
        else {
            notCompleted(3, question3radioButton1);
            return;
        }
        if (question4radioButton1.isChecked()) answers[4] = 1;
        else if (question4radioButton2.isChecked()) answers[4] = 2;
        else if (question4radioButton3.isChecked()) answers[4] = 3;
        else if (question4radioButton4.isChecked()) answers[4] = 4;
        else if (question4radioButton5.isChecked()) answers[4] = 5;
        else {
            notCompleted(4, question4radioButton1);
            return;
        }
        if (question5radioButton1.isChecked()) answers[5] = 1;
        else if (question5radioButton2.isChecked()) answers[5] = 2;
        else if (question5radioButton3.isChecked()) answers[5] = 3;
        else if (question5radioButton4.isChecked()) answers[5] = 4;
        else if (question5radioButton5.isChecked()) answers[5] = 5;
        else {
            notCompleted(5, question5radioButton1);
            return;
        }
        if (question6radioButton1.isChecked()) answers[6] = 1;
        else if (question6radioButton2.isChecked()) answers[6] = 2;
        else if (question6radioButton3.isChecked()) answers[6] = 3;
        else if (question6radioButton4.isChecked()) answers[6] = 4;
        else if (question6radioButton5.isChecked()) answers[6] = 5;
        else {
            notCompleted(6, question6radioButton1);
            return;
        }
        if (question7radioButton1.isChecked()) answers[7] = 1;
        else if (question7radioButton2.isChecked()) answers[7] = 2;
        else if (question7radioButton3.isChecked()) answers[7] = 3;
        else if (question7radioButton4.isChecked()) answers[7] = 4;
        else if (question7radioButton5.isChecked()) answers[7] = 5;
        else {
            notCompleted(7, question7radioButton1);
            return;
        }
        if (question8radioButton1.isChecked()) answers[8] = 1;
        else if (question8radioButton2.isChecked()) answers[8] = 2;
        else if (question8radioButton3.isChecked()) answers[8] = 3;
        else if (question8radioButton4.isChecked()) answers[8] = 4;
        else if (question8radioButton5.isChecked()) answers[8] = 5;
        else {
            notCompleted(8, question8radioButton1);
            return;
        }
        if (question9radioButton1.isChecked()) answers[9] = 1;
        else if (question9radioButton2.isChecked()) answers[9] = 2;
        else if (question9radioButton3.isChecked()) answers[9] = 3;
        else if (question9radioButton4.isChecked()) answers[9] = 4;
        else if (question9radioButton5.isChecked()) answers[9] = 5;
        else {
            notCompleted(9, question9radioButton1);
            return;
        }
        if (question10radioButton1.isChecked()) answers[10] = 1;
        else if (question10radioButton2.isChecked()) answers[10] = 2;
        else if (question10radioButton3.isChecked()) answers[10] = 3;
        else if (question10radioButton4.isChecked()) answers[10] = 4;
        else if (question10radioButton5.isChecked()) answers[10] = 5;
        else {
            notCompleted(10, question10radioButton1);
            return;
        }
        if (question11radioButton1.isChecked()) answers[11] = 1;
        else if (question11radioButton2.isChecked()) answers[11] = 2;
        else if (question11radioButton3.isChecked()) answers[11] = 3;
        else if (question11radioButton4.isChecked()) answers[11] = 4;
        else if (question11radioButton5.isChecked()) answers[11] = 5;
        else {
            notCompleted(11, question11radioButton1);
            return;
        }
        if (question12radioButton1.isChecked()) answers[12] = 1;
        else if (question12radioButton2.isChecked()) answers[12] = 2;
        else if (question12radioButton3.isChecked()) answers[12] = 3;
        else if (question12radioButton4.isChecked()) answers[12] = 4;
        else if (question12radioButton5.isChecked()) answers[12] = 5;
        else {
            notCompleted(12, question12radioButton1);
            return;
        }
        if (question13radioButton1.isChecked()) answers[13] = 1;
        else if (question13radioButton2.isChecked()) answers[13] = 2;
        else if (question13radioButton3.isChecked()) answers[13] = 3;
        else if (question13radioButton4.isChecked()) answers[13] = 4;
        else if (question13radioButton5.isChecked()) answers[13] = 5;
        else {
            notCompleted(13, question13radioButton1);
            return;
        }
        if (question14radioButton1.isChecked()) answers[14] = 1;
        else if (question14radioButton2.isChecked()) answers[14] = 2;
        else if (question14radioButton3.isChecked()) answers[14] = 3;
        else if (question14radioButton4.isChecked()) answers[14] = 4;
        else if (question14radioButton5.isChecked()) answers[14] = 5;
        else {
            notCompleted(14, question14radioButton1);
            return;
        }
        if (question15radioButton1.isChecked()) answers[15] = 1;
        else if (question15radioButton2.isChecked()) answers[15] = 2;
        else if (question15radioButton3.isChecked()) answers[15] = 3;
        else if (question15radioButton4.isChecked()) answers[15] = 4;
        else if (question15radioButton5.isChecked()) answers[15] = 5;
        else {
            notCompleted(15, question15radioButton1);
            return;
        }
        if (question16radioButton1.isChecked()) answers[16] = 1;
        else if (question16radioButton2.isChecked()) answers[16] = 2;
        else if (question16radioButton3.isChecked()) answers[16] = 3;
        else if (question16radioButton4.isChecked()) answers[16] = 4;
        else if (question16radioButton5.isChecked()) answers[16] = 5;
        else {
            notCompleted(16, question16radioButton1);
            return;
        }
        if (question17radioButton1.isChecked()) answers[17] = 1;
        else if (question17radioButton2.isChecked()) answers[17] = 2;
        else if (question17radioButton3.isChecked()) answers[17] = 3;
        else if (question17radioButton4.isChecked()) answers[17] = 4;
        else if (question17radioButton5.isChecked()) answers[17] = 5;
        else {
            notCompleted(17, question17radioButton1);
            return;
        }
        if (question18radioButton1.isChecked()) answers[18] = 1;
        else if (question18radioButton2.isChecked()) answers[18] = 2;
        else if (question18radioButton3.isChecked()) answers[18] = 3;
        else if (question18radioButton4.isChecked()) answers[18] = 4;
        else if (question18radioButton5.isChecked()) answers[18] = 5;
        else {
            notCompleted(18, question18radioButton1);
            return;
        }
        if (question19radioButton1.isChecked()) answers[19] = 1;
        else if (question19radioButton2.isChecked()) answers[19] = 2;
        else if (question19radioButton3.isChecked()) answers[19] = 3;
        else if (question19radioButton4.isChecked()) answers[19] = 4;
        else if (question19radioButton5.isChecked()) answers[19] = 5;
        else {
            notCompleted(19, question19radioButton1);
            return;
        }
        if (question20radioButton1.isChecked()) answers[20] = 1;
        else if (question20radioButton2.isChecked()) answers[20] = 2;
        else if (question20radioButton3.isChecked()) answers[20] = 3;
        else if (question20radioButton4.isChecked()) answers[20] = 4;
        else if (question20radioButton5.isChecked()) answers[20] = 5;
        else {
            notCompleted(20, question20radioButton1);
            return;
        }
        if (question21radioButton1.isChecked()) answers[21] = 1;
        else if (question21radioButton2.isChecked()) answers[21] = 2;
        else if (question21radioButton3.isChecked()) answers[21] = 3;
        else if (question21radioButton4.isChecked()) answers[21] = 4;
        else if (question21radioButton5.isChecked()) answers[21] = 5;
        else {
            notCompleted(21, question21radioButton1);
            return;
        }
        if (question22radioButton1.isChecked()) answers[22] = 1;
        else if (question22radioButton2.isChecked()) answers[22] = 2;
        else if (question22radioButton3.isChecked()) answers[22] = 3;
        else if (question22radioButton4.isChecked()) answers[22] = 4;
        else if (question22radioButton5.isChecked()) answers[22] = 5;
        else {
            notCompleted(22, question22radioButton1);
            return;
        }
        if (question23radioButton1.isChecked()) answers[23] = 1;
        else if (question23radioButton2.isChecked()) answers[23] = 2;
        else if (question23radioButton3.isChecked()) answers[23] = 3;
        else if (question23radioButton4.isChecked()) answers[23] = 4;
        else if (question23radioButton5.isChecked()) answers[23] = 5;
        else {
            notCompleted(23, question23radioButton1);
            return;
        }
        if (question24radioButton1.isChecked()) answers[24] = 1;
        else if (question24radioButton2.isChecked()) answers[24] = 2;
        else if (question24radioButton3.isChecked()) answers[24] = 3;
        else if (question24radioButton4.isChecked()) answers[24] = 4;
        else if (question24radioButton5.isChecked()) answers[24] = 5;
        else {
            notCompleted(24, question24radioButton1);
            return;
        }
        if (question25radioButton1.isChecked()) answers[25] = 1;
        else if (question25radioButton2.isChecked()) answers[25] = 2;
        else if (question25radioButton3.isChecked()) answers[25] = 3;
        else if (question25radioButton4.isChecked()) answers[25] = 4;
        else if (question25radioButton5.isChecked()) answers[25] = 5;
        else {
            notCompleted(25, question25radioButton1);
            return;
        }
        if (question26radioButton1.isChecked()) answers[26] = 1;
        else if (question26radioButton2.isChecked()) answers[26] = 2;
        else if (question26radioButton3.isChecked()) answers[26] = 3;
        else if (question26radioButton4.isChecked()) answers[26] = 4;
        else if (question26radioButton5.isChecked()) answers[26] = 5;
        else {
            notCompleted(26, question26radioButton1);
            return;
        }
        if (question27radioButton1.isChecked()) answers[27] = 1;
        else if (question27radioButton2.isChecked()) answers[27] = 2;
        else if (question27radioButton3.isChecked()) answers[27] = 3;
        else if (question27radioButton4.isChecked()) answers[27] = 4;
        else if (question27radioButton5.isChecked()) answers[27] = 5;
        else {
            notCompleted(27, question27radioButton1);
            return;
        }
        if (question28radioButton1.isChecked()) answers[28] = 1;
        else if (question28radioButton2.isChecked()) answers[28] = 2;
        else if (question28radioButton3.isChecked()) answers[28] = 3;
        else if (question28radioButton4.isChecked()) answers[28] = 4;
        else if (question28radioButton5.isChecked()) answers[28] = 5;
        else {
            notCompleted(28, question28radioButton1);
            return;
        }
        if (question29radioButton1.isChecked()) answers[29] = 1;
        else if (question29radioButton2.isChecked()) answers[29] = 2;
        else if (question29radioButton3.isChecked()) answers[29] = 3;
        else if (question29radioButton4.isChecked()) answers[29] = 4;
        else if (question29radioButton5.isChecked()) answers[29] = 5;
        else {
            notCompleted(29, question29radioButton1);
            return;
        }
        if (question30radioButton1.isChecked()) answers[30] = 1;
        else if (question30radioButton2.isChecked()) answers[30] = 2;
        else if (question30radioButton3.isChecked()) answers[30] = 3;
        else if (question30radioButton4.isChecked()) answers[30] = 4;
        else if (question30radioButton5.isChecked()) answers[30] = 5;
        else {
            notCompleted(30, question30radioButton1);
            return;
        }
        if (question31radioButton1.isChecked()) answers[31] = 1;
        else if (question31radioButton2.isChecked()) answers[31] = 2;
        else if (question31radioButton3.isChecked()) answers[31] = 3;
        else if (question31radioButton4.isChecked()) answers[31] = 4;
        else if (question31radioButton5.isChecked()) answers[31] = 5;
        else {
            notCompleted(31, question31radioButton1);
            return;
        }
        if (question32radioButton1.isChecked()) answers[32] = 1;
        else if (question32radioButton2.isChecked()) answers[32] = 2;
        else if (question32radioButton3.isChecked()) answers[32] = 3;
        else if (question32radioButton4.isChecked()) answers[32] = 4;
        else if (question32radioButton5.isChecked()) answers[32] = 5;
        else {
            notCompleted(32, question32radioButton1);
            return;
        }
        if (question33radioButton1.isChecked()) answers[33] = 1;
        else if (question33radioButton2.isChecked()) answers[33] = 2;
        else if (question33radioButton3.isChecked()) answers[33] = 3;
        else if (question33radioButton4.isChecked()) answers[33] = 4;
        else if (question33radioButton5.isChecked()) answers[33] = 5;
        else {
            notCompleted(33, question33radioButton1);
            return;
        }
        if (question34radioButton1.isChecked()) answers[34] = 1;
        else if (question34radioButton2.isChecked()) answers[34] = 2;
        else if (question34radioButton3.isChecked()) answers[34] = 3;
        else if (question34radioButton4.isChecked()) answers[34] = 4;
        else if (question34radioButton5.isChecked()) answers[34] = 5;
        else {
            notCompleted(34, question34radioButton1);
            return;
        }
        if (question35radioButton1.isChecked()) answers[35] = 1;
        else if (question35radioButton2.isChecked()) answers[35] = 2;
        else if (question35radioButton3.isChecked()) answers[35] = 3;
        else if (question35radioButton4.isChecked()) answers[35] = 4;
        else if (question35radioButton5.isChecked()) answers[35] = 5;
        else {
            notCompleted(35, question35radioButton1);
            return;
        }
        if (question36radioButton1.isChecked()) answers[36] = 1;
        else if (question36radioButton2.isChecked()) answers[36] = 2;
        else if (question36radioButton3.isChecked()) answers[36] = 3;
        else if (question36radioButton4.isChecked()) answers[36] = 4;
        else if (question36radioButton5.isChecked()) answers[36] = 5;
        else {
            notCompleted(36, question36radioButton1);
            return;
        }
        if (question37radioButton1.isChecked()) answers[37] = 1;
        else if (question37radioButton2.isChecked()) answers[37] = 2;
        else if (question37radioButton3.isChecked()) answers[37] = 3;
        else if (question37radioButton4.isChecked()) answers[37] = 4;
        else if (question37radioButton5.isChecked()) answers[37] = 5;
        else {
            notCompleted(37, question37radioButton1);
            return;
        }
        if (question38radioButton1.isChecked()) answers[38] = 1;
        else if (question38radioButton2.isChecked()) answers[38] = 2;
        else if (question38radioButton3.isChecked()) answers[38] = 3;
        else if (question38radioButton4.isChecked()) answers[38] = 4;
        else if (question38radioButton5.isChecked()) answers[38] = 5;
        else {
            notCompleted(38, question38radioButton1);
            return;
        }
        if (question39radioButton1.isChecked()) answers[39] = 1;
        else if (question39radioButton2.isChecked()) answers[39] = 2;
        else if (question39radioButton3.isChecked()) answers[39] = 3;
        else if (question39radioButton4.isChecked()) answers[39] = 4;
        else if (question39radioButton5.isChecked()) answers[39] = 5;
        else {
            notCompleted(39, question39radioButton1);
            return;
        }
        if (question40radioButton1.isChecked()) answers[40] = 1;
        else if (question40radioButton2.isChecked()) answers[40] = 2;
        else if (question40radioButton3.isChecked()) answers[40] = 3;
        else if (question40radioButton4.isChecked()) answers[40] = 4;
        else if (question40radioButton5.isChecked()) answers[40] = 5;
        else {
            notCompleted(40, question40radioButton1);
            return;
        }
        if (question41radioButton1.isChecked()) answers[41] = 1;
        else if (question41radioButton2.isChecked()) answers[41] = 2;
        else if (question41radioButton3.isChecked()) answers[41] = 3;
        else if (question41radioButton4.isChecked()) answers[41] = 4;
        else if (question41radioButton5.isChecked()) answers[41] = 5;
        else {
            notCompleted(41, question41radioButton1);
            return;
        }
        if (question42radioButton1.isChecked()) answers[42] = 1;
        else if (question42radioButton2.isChecked()) answers[42] = 2;
        else if (question42radioButton3.isChecked()) answers[42] = 3;
        else if (question42radioButton4.isChecked()) answers[42] = 4;
        else if (question42radioButton5.isChecked()) answers[42] = 5;
        else {
            notCompleted(42, question42radioButton1);
            return;
        }
        if (question43radioButton1.isChecked()) answers[43] = 1;
        else if (question43radioButton2.isChecked()) answers[43] = 2;
        else if (question43radioButton3.isChecked()) answers[43] = 3;
        else if (question43radioButton4.isChecked()) answers[43] = 4;
        else if (question43radioButton5.isChecked()) answers[43] = 5;
        else {
            notCompleted(43, question43radioButton1);
            return;
        }
        if (question44radioButton1.isChecked()) answers[44] = 1;
        else if (question44radioButton2.isChecked()) answers[44] = 2;
        else if (question44radioButton3.isChecked()) answers[44] = 3;
        else if (question44radioButton4.isChecked()) answers[44] = 4;
        else if (question44radioButton5.isChecked()) answers[44] = 5;
        else {
            notCompleted(44, question44radioButton1);
            return;
        }
        if (question45radioButton1.isChecked()) answers[45] = 1;
        else if (question45radioButton2.isChecked()) answers[45] = 2;
        else if (question45radioButton3.isChecked()) answers[45] = 3;
        else if (question45radioButton4.isChecked()) answers[45] = 4;
        else if (question45radioButton5.isChecked()) answers[45] = 5;
        else {
            notCompleted(45, question45radioButton1);
            return;
        }
        if (question46radioButton1.isChecked()) answers[46] = 1;
        else if (question46radioButton2.isChecked()) answers[46] = 2;
        else if (question46radioButton3.isChecked()) answers[46] = 3;
        else if (question46radioButton4.isChecked()) answers[46] = 4;
        else if (question46radioButton5.isChecked()) answers[46] = 5;
        else {
            notCompleted(46, question46radioButton1);
            return;
        }
        if (question47radioButton1.isChecked()) answers[47] = 1;
        else if (question47radioButton2.isChecked()) answers[47] = 2;
        else if (question47radioButton3.isChecked()) answers[47] = 3;
        else if (question47radioButton4.isChecked()) answers[47] = 4;
        else if (question47radioButton5.isChecked()) answers[47] = 5;
        else {
            notCompleted(47, question47radioButton1);
            return;
        }
        if (question48radioButton1.isChecked()) answers[48] = 1;
        else if (question48radioButton2.isChecked()) answers[48] = 2;
        else if (question48radioButton3.isChecked()) answers[48] = 3;
        else if (question48radioButton4.isChecked()) answers[48] = 4;
        else if (question48radioButton5.isChecked()) answers[48] = 5;
        else {
            notCompleted(48, question48radioButton1);
            return;
        }
        if (question49radioButton1.isChecked()) answers[49] = 1;
        else if (question49radioButton2.isChecked()) answers[49] = 2;
        else if (question49radioButton3.isChecked()) answers[49] = 3;
        else if (question49radioButton4.isChecked()) answers[49] = 4;
        else if (question49radioButton5.isChecked()) answers[49] = 5;
        else {
            notCompleted(49, question49radioButton1);
            return;
        }
        if (question50radioButton1.isChecked()) answers[50] = 1;
        else if (question50radioButton2.isChecked()) answers[50] = 2;
        else if (question50radioButton3.isChecked()) answers[50] = 3;
        else if (question50radioButton4.isChecked()) answers[50] = 4;
        else if (question50radioButton5.isChecked()) answers[50] = 5;
        else {
            notCompleted(50, question50radioButton1);
            return;
        }

        //if all ok goto calculation
        calculate();
    }

    //shows a message with the first question that hasn't been answered
    //todo move ScrollView to that question
    public void notCompleted(int i, RadioButton v) {
        Toast toast = Toast.makeText(MainActivity.this, "Please answer question " + i + " before proceeding!", Toast.LENGTH_SHORT);
        toast.show();

    }

    //todo function that calculates score using mod 5 and mod 2 and special cases
    public void calculate() {
        int i = 0;
        extraversion = 0;
        agreeableness = 0;
        conscientiousness = 0;
        emotionalStability = 0;
        intellectImagination = 0;

        while (i < 50) {
            i++;
            switch (i % 5) {
                case 1:
                    if (i % 2 == 0) extraversion -= answers[i];
                    else extraversion += answers[i];
                    break;
                case 2:
                    if (i == 42) agreeableness += answers[i];
                    else if (i % 2 == 0) agreeableness -= answers[i];
                    else agreeableness += answers[i];
                    break;
                case 3:
                    if (i == 48) conscientiousness += answers[i];
                    else if (i % 2 == 0) conscientiousness -= answers[i];
                    else conscientiousness += answers[i];
                    break;
                case 4:
                    if (i == 29 | i == 39 | i == 49) emotionalStability -= answers[i];
                    else if (i % 2 == 0) emotionalStability -= answers[i];
                    else emotionalStability += answers[i];
                    break;
                case 0:
                    if (i == 40 | i == 50) intellectImagination += answers[i];
                    else if (i % 2 == 0) intellectImagination -= answers[i];
                    else intellectImagination += answers[i];
                    break;
            }
        }

        //todo decide if negative values are allowed

        //todo decide if this is where we call for score display
        displayScore();
    }

    //this function plots the Big Five Markers onto a GraphView graph
    public void graph() {
        GraphView graph = findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, extraversion),
                new DataPoint(1, agreeableness),
                new DataPoint(2, conscientiousness),
                new DataPoint(3, emotionalStability),
                new DataPoint(4, intellectImagination)
        });
        graph.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Extraversion", "Agreeableness", "Conscientiousness", "Emotional Stability", "Intellect/Imagination"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.setVisibility(View.VISIBLE);
    }

    //todo function that creates score display (use graphs?)
    public void displayScore() {
        //hide instructions and questions
        ScrollView scroll = findViewById(R.id.scrollView2);
        scroll.setVisibility(View.GONE);
        TextView textView = findViewById(R.id.textView2);
        textView.setVisibility(View.GONE);

        //generate text for automatic assessment

        //show graph and text and wiki button
        graph();
        TextView textView2 = findViewById(R.id.results_text);
        textView2.setVisibility(View.VISIBLE);
        Button button = findViewById(R.id.wiki);
        button.setVisibility(View.VISIBLE);

        //change Submit button to reset


    }

    //todo wiki() function to open wikipedia article
    public void wiki(View v) {
        Uri webpage = Uri.parse("https://en.wikipedia.org/wiki/Big_Five_personality_traits");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //todo function that hides test and shows results

    //todo reset function


}
