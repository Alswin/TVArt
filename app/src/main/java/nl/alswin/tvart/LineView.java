package nl.alswin.tvart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineView extends View {

    private final Paint[] paint = new Paint[120];
    int i;
    static Point[] pointArrayStart = new Point[120];
    static Point[] pointArrayEnd = new Point[120];
    public static int trsp;
    public static int trspC = 0;
    int cx;
    int cy;
    int cr;

    //    --------------------------------------------------------------------------- De constructors
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //    ------------------------------------------------------------------------- einde constructors
//    ------------------------------------------------------------------------- De Methode onDraw (geeft toegang tot Canvas)
//                                                                              Met Paint wordt de lijn getekend op Canvas
//                                                                              De eigenschappen (kleur, breedte etc.
//                                                                              worden in on onDraw gezet met set...
//                                                                              Evenzo de coordinaten van start en eindpunt van de lijn
    protected void onDraw(Canvas canvas) {
//    ------------------------------------------------------------------------- We trekken 60 lijnen in een loop.
//                                                                              Ook de cirkel wordt meegenomen.
        for (int i = 0; i < 60; i++) {
//    ------------------------------------------------------------------------- Eerst de dikte en kleur van de lijnen vastleggen
            paint[i] = new Paint();
            paint[i].setStrokeWidth(3);
//            ---------------------------------------------------------------- In deze routine worden de lijnkleuren
//                                                                             en de transparantie ingevoerd. (transparantie met een variabele
//                                                                             trsp wordt in Mainactivity ingesteld (uitfaden van de lijnen)
//                                                                             trsp uitfaden wordt bij de 60e lijn in Mainactivity geregeld.
            if (i < 26) {
//                    ---------------------------------------------------------groen wordt opgehoogd... we lopen naar geel tot max geel.
                paint[i].setColor(Color.argb(trsp, 255, 10 * i, 0));
            }
            if (i > 25 && i < 51) {
//                    ---------------------------------------------------------groen loopt terug naar 0, blauw komt erin (loopt naar paars)
                paint[i].setColor(Color.argb(trsp, 255, 255 - (i - 25) * 10, (i - 25) * 10));
            }
            if (i > 50) {
//                    ---------------------------------------------------------rood gaat eruit, we lopen naar blauw met een beetje groen
                paint[i].setColor(Color.argb(trsp, 255 - (i - 50) * 15, (i - 50) * 5, 255 - (i - 50) * 10));
            }
//             ---------------------------------------------------------------- Canvas invullen met eerste  alternatief (boolean geslecteerd)
            try {
                canvas.drawLine(pointArrayStart[i].x, pointArrayStart[i].y, pointArrayEnd[i].x, pointArrayEnd[i].y, paint[i]);
            } catch (Exception e) {
//                    MainActivity.stopbutton.setText("foutje bij: " + i + "/" + e);
            }
//            --------------------- Cirkelkleuren vastleggen
            if (i < 26) {
                paint[i].setColor(Color.argb(trspC, 255, 10 * i, 0));
            }
            if (i > 25 && i < 36) {
                paint[i].setColor(Color.argb(trspC, 255, 255 - (i - 25) * 10, (i - 25) * 10));
            }
            if (i > 35) {
                paint[i].setColor(Color.argb(trspC, 255 - (i - 50) * 10, 0, 255));
            }
//            -------------------- Canvas verder vullen met cirkel
            if (i > 0) {
                canvas.drawCircle(cx, cy, cr - i * 15, paint[i]);
            }
            super.onDraw(canvas);
        }
    }

    //    ----------------------- De setters voor de start en eindcoordinaten van de lijn


    public void setLvpointArrayStart(Point lvpointArrayStart) {
        LineView.pointArrayStart[MainActivity.a] = lvpointArrayStart;
    }

    public void setLvpointArrayEnd(Point lvpointArrayEnd) {
        LineView.pointArrayEnd[MainActivity.a] = lvpointArrayEnd;
    }

    public void draw() {
        invalidate();
        requestLayout();
    }

}
