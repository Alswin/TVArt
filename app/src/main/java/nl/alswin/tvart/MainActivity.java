package nl.alswin.tvart;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;


public class MainActivity extends FragmentActivity {
    Point[] pointArrayStart = new Point[120];
    Point[] pointArrayEnd = new Point[120];
    public static int a;
    public static boolean zwart = false;
    public int scrWidth;
    public int scrHeight;
    private LineView mlineView;
    static int k = 0;
    int xa1 = 30, xa2 = 30, ya1 = 10, ya2 = 2, xb1 = 1920, xb2 = -5, yb1 = 1200, yb2 = -2;
    public static Button stopbutton;
    public int ms = 75;
    public int crhulp = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        scrWidth = displayMetrics.widthPixels;
        scrHeight = displayMetrics.heightPixels;

        stopbutton = findViewById(R.id.stopbutton);
        stopbutton.setText("Stop");
//        -------------------------------------------Declaratie van de class LineView (de primaire declaratie is verwerkt in de xml-file)
//        -------------------------------------------Lijnen trekken (van start x-y naar eind x-y) te plaatsen in LineView-raam (class)
        mlineView = (LineView) findViewById(R.id.mlineView);
        LineView.trsp = 255;
        een();
    }

    public void een() {
//        ------------------------------------------------------------------Start en eindpunten van de lijnen definieren
        xa1 = Randomizer.generate(0, (int) 3 * scrWidth / 20);
        xa2 = Randomizer.generate(1, (int) scrWidth / 40);
        ya1 = Randomizer.generate(0, (int) 3 * scrHeight / 10);
        ya2 = Randomizer.generate(0, (int) scrHeight / 100);
        xb1 = Randomizer.generate((int) 10 * scrWidth / 20, scrWidth);
        xb2 = Randomizer.generate((int) (-0.5 * scrWidth / 200), 30);
        yb1 = Randomizer.generate((int) 8 * scrHeight / 10, (int) 12 * scrHeight / 10);
        yb2 = Randomizer.generate((int) -scrHeight / 100, (int) (0.5 * scrHeight / 100));
//-------------------------------------------------------------------------Radiaal, posities en transparantie cirkel definieren
        crhulp = Randomizer.generate(40, scrWidth/10);
        mlineView.cr = crhulp;
        LineView.trspC = 0;
//                                                                         Positie is tussen schemranden +/- de radiaallengte
        mlineView.cx = Randomizer.generate(crhulp, scrWidth - (crhulp));
        mlineView.cy = Randomizer.generate(crhulp, scrHeight - (crhulp));
        a = 0;
//        stopbutton.setText("x=" + mlineView.cx + ", y=" + mlineView.cy + ", r=" + mlineView.cr);
//------------------------------------------------------------------------ Een voor een trekken van lijnen: pauze voor volgende lijn
//                                                                         Bij deze eerste kiezen we een grotere pauze
//                                                                         die komt tussen de verschilende runs
        Handler h = new Handler();
        Runnable r = () -> bundel();
        h.postDelayed(r, 2000);
    }

    public void bundel(){
        if (LineView.trspC <245){
            LineView.trspC +=10;
        }
        a+=1;
        verzamel();
        if (a>59){
            zestig();
            return;
        }
        Handler hh = new Handler();
        Runnable rr = () -> bundel();
        hh.postDelayed(rr,75);
    }
    public void zestig() {
        ms=75;
//        ------------------------------------------------------------------- Laatste lijn van de 60 (0-59)
        a = 59;
//        ------------------------------------------------------------------- De lijndefinities en tekenopdracht
//                                                                            zijn in een verzamelroutine (onderaan in Mainactivity) opgenomen
//                                                                            ------
//                                                                            De verzamel-routine wordt ook bij de terugverwijzingen naar deze
//                                                                            routine gebruikt om de transparantie (uitfaden) te doorlopen.
//                                                                            De LineView draw-routine bewerkt de transparantie in elke
//                                                                            herhaling van deze routine dan voor alle lijnen (i: 1-60)
//                                                                            Elke lijn (en zon) wordt dan steeds opnieuw getekend..
        verzamel();
//                ----------------------------------------------------------- Check op bool Zwart (start als false)
//                ----------------------------------------------------------- De teller is de transparantie-component.
//                                                                            Deze routine herhaalt zichzelf totdat de teller
//                                                                            van 255 tot <0 is teruggelopen.
//
//                                                                            Eerst checken we op zwart = true, zo nee dan wordt
//                                                                            die in else op true gezet.
            if (zwart) {
//                    ------------------------------------------------------- Teller wordt bij elke nieuwe run met één verlaagd tot <0
                LineView.trsp -= 10;
                LineView.trspC = LineView.trsp;
                if (LineView.trsp < 0) {
                    LineView.trsp = 0;
                    LineView.trspC = LineView.trsp;
//                        --------------------------------------------------- @@@@@  Zodra teller <0
//                                                                            1. Verwijderen we de oude lijnen (reductie tot 0.0 punten)
//                                                                               (anders blijven die staan tot de vervanging in de nieuwe ronde),
//                                                                            2. zetten we zwart terug op false
//                                                                            3. zetten we de teller weer op 255
//                                                                            4. keren we terug naar de uitgangsopsitie (opnieuw vanuit leeg)
//                                                                            5. --- en lopen we bovendien met return uit deze routine
//                                                                               NB (weet niet waarom, maar deze return blijkt nodig)
                    for (a = 0; a < 60; a++) {
                        pointArrayStart[a] = new Point(0, 0);
                        pointArrayEnd[a] = new Point(0, 0);
                        mlineView.setLvpointArrayStart(pointArrayStart[a]);
                        mlineView.setLvpointArrayEnd(pointArrayEnd[a]);
                        mlineView.draw();
                    }
                    zwart = false;
                    LineView.trsp = 255;
                    LineView.trspC = LineView.trsp;
                    ms=75;
                    mlineView.refreshDrawableState();
                    een();
                    return; // ----------------------------------------- Nodig, maar onbekend waarom omdat eerder naar feen()
                    //                                                   wordt doorverwezen.
                    //                                                   Kennelijk wordt desondanks de routine afgemaakt als de
                    //                                                   laatste ronde is doorlopen en ná de doorverwijzing naar feen().
//                        -------------------------------------------------- Hier eindigt dus de routine
                }
            } else {
//                    ------------------------------------------------------ Als zwart in deze routine nog niet true is
//                                                                           ...en we dus in de start zitten, gaat zwart op true,
                zwart = true;
            }
//                ---------------------------------------------------------- We vervolgen dan met het instellen van de transparantie
//                                                                           ... die start op 255 (niet transparant),
//                                                                           ... en verwijzen we door naar dezelfde routine.
//                                                                           Daarin wordt de teller/transparantie steeds met één verlaagd
//                                                                           totdat die < 0, en dus in een slotronde
//                                                                           de loop hierboven: @@@@ wordt ingezet:
//                                                                           ...zet de lijnen naar 0-posities en de teller terug op 255.
            Handler hh = new Handler();
            Runnable rr = () -> zestig();
            hh.postDelayed(rr,75);
    }

    public void stopbutton(View view) {
            System.exit(0);
    }

    public void verzamel() {
        pointArrayStart[a] = new Point(xa1 + xa2 * a, ya1 + ya2 * a);
        pointArrayEnd[a] = new Point(xb1 - xb2 * a, yb1 - yb2 * a);
        mlineView.setLvpointArrayStart(pointArrayStart[a]);
        mlineView.setLvpointArrayEnd(pointArrayEnd[a]);
        mlineView.draw();
    }
}

// Vraag gesteld op: https://stackoverflow.com/questions/70739869/android-studio-java-drawing-multiple-lines


