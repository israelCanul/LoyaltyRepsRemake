package com.xcaret.loyaltyreps.view.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log

class TicketBase @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    val margen = 10f
    var widtRectangleBig:Float = 0.0f
    var myPath: Path = Path()
    val paint: Paint = Paint()
    val paint1: Paint = Paint()
    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth= 1f
//        paint.setShadowLayer(5f, 7f, 7f, Color.BLACK)

        paint1.color = Color.BLACK
        paint1.style = Paint.Style.FILL
        paint1.strokeWidth= 2f
        paint1.setShadowLayer(10f, 2f, 3f, Color.BLACK)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i("medida", "$measuredHeight - $measuredWidth")
        myPath = createPath()
    }

    fun  createPath(): Path {
        widtRectangleBig = measuredWidth.toFloat() * 0.08f
        val myPath = Path()
        val point1 = PointF(0f , 0f)
        val point2 = PointF(measuredWidth.toFloat()- margen, 0f)
        val point3 = PointF(measuredWidth.toFloat()- margen, measuredHeight - widtRectangleBig - margen)
        val point5 = PointF(0f , measuredHeight.toFloat() - margen - widtRectangleBig)
        val point6 = PointF(0f , 0f)

        myPath.moveTo(point1.x, point1.y)
        myPath.lineTo(point2.x, point2.y)
        myPath.lineTo(point3.x, point3.y)
        val rectBottomRight = RectF(
            (measuredWidth.toFloat() - margen - widtRectangleBig),
            (measuredHeight.toFloat() - margen - widtRectangleBig),
            (measuredWidth.toFloat() - margen + widtRectangleBig),
            (measuredHeight.toFloat() - margen + widtRectangleBig))

        val rectBottomLeft = RectF(
            (0f - widtRectangleBig),
            (measuredHeight.toFloat() - margen - widtRectangleBig),
            (0f + ( widtRectangleBig) ),
            (measuredHeight.toFloat() - margen + widtRectangleBig))

        val rectBottomMiddle = RectF(
            ( (measuredWidth - margen) / 2) - widtRectangleBig ,
            (measuredHeight.toFloat() - margen - widtRectangleBig),
            ((measuredWidth - margen) / 2) + widtRectangleBig ,
            (measuredHeight.toFloat() - margen + widtRectangleBig))

        val rectBottomMiddleRight = RectF(
            ( (measuredWidth - margen) / 4) - (widtRectangleBig *1.5f) ,
            (measuredHeight.toFloat() - margen -(widtRectangleBig / 2)),
            ((measuredWidth - margen) / 4) - (widtRectangleBig / 2) ,
            (measuredHeight.toFloat() - margen + (widtRectangleBig / 2)))

        val rectBottomMiddleRight2 = RectF(
            ( (measuredWidth - margen) / 4) + (widtRectangleBig / 2) ,
            (measuredHeight.toFloat() - margen -(widtRectangleBig / 2)),
            ((measuredWidth - margen) / 4) + (widtRectangleBig * 1.5f) ,
            (measuredHeight.toFloat() - margen + (widtRectangleBig / 2)))

        val rectBottomMiddleRight3 = RectF(
            (( (measuredWidth - margen) / 4) * 3 ) - (widtRectangleBig *1.5f) ,
            (measuredHeight.toFloat() - margen -(widtRectangleBig / 2)),
            (( (measuredWidth - margen) / 4) * 3 ) - (widtRectangleBig / 2) ,
            (measuredHeight.toFloat() - margen + (widtRectangleBig / 2)))

        val rectBottomMiddleRight4 = RectF(
            (( (measuredWidth - margen) / 4) * 3 ) + (widtRectangleBig / 2) ,
            (measuredHeight.toFloat() - margen -(widtRectangleBig / 2)),
            (( (measuredWidth - margen) / 4) * 3 ) + (widtRectangleBig * 1.5f) ,
            (measuredHeight.toFloat() - margen + (widtRectangleBig / 2)))

        myPath.arcTo( rectBottomRight, 270f, -90f, false)
        myPath.arcTo( rectBottomMiddleRight4, 0f, -180f, false)
        myPath.arcTo( rectBottomMiddleRight3, 0f, -180f, false)
        myPath.arcTo( rectBottomMiddle, 0f, -180f, false)
        myPath.arcTo( rectBottomMiddleRight2, 0f, -180f, false)
        myPath.arcTo( rectBottomMiddleRight, 0f, -180f, false)
        myPath.arcTo( rectBottomLeft, 0f, -90f, false)
        myPath.lineTo(point5.x, point5.y)
        myPath.lineTo(point6.x, point6.y)
        myPath.close()
        return myPath
    }


    override fun onDraw(canvas: Canvas) {

        canvas.drawPath(myPath, paint1)
        canvas.drawPath(myPath, paint)

    }


}