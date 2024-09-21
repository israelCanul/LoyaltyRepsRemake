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

class TicketBaseTopBottom @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    private val margin = 10f
    private var widthRectangleBig:Float = 0.0f
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
        widthRectangleBig = measuredWidth.toFloat() * 0.12f
        val myPath = Path()
        val point1 = PointF(0f , widthRectangleBig / 2)
        val point1_1 = PointF(widthRectangleBig, 0f)
        val point2 = PointF(measuredWidth.toFloat()- margin, 0f)
        val point3 = PointF(measuredWidth.toFloat()- margin, measuredHeight - widthRectangleBig - margin)
        val point5 = PointF(0f , measuredHeight.toFloat() - margin - widthRectangleBig)
        val point6 = PointF(0f , 0f)

        myPath.moveTo(point1.x, point1.y)

        val rectTopLeft = RectF(
            (0f),
            (0f),
            (0f + widthRectangleBig  ),
            (0f + widthRectangleBig  ))

        myPath.arcTo( rectTopLeft, -180f, 90f, false)


        val rectTopMiddle = RectF(
            ( (measuredWidth - margin) / 2) - widthRectangleBig ,
            (0f - widthRectangleBig),
            ((measuredWidth - margin) / 2) + widthRectangleBig ,
            (0f + widthRectangleBig))

        myPath.arcTo( rectTopMiddle, 180f, -180f, false)

        val rectTopRight = RectF(
            (measuredWidth.toFloat() - margin - widthRectangleBig),
            (0f ),
            (measuredWidth - margin)  ,
            (0f + widthRectangleBig))

        myPath.arcTo( rectTopRight, -90f, 90f, false)

//        myPath.lineTo(point2.x, point2.y)
        myPath.lineTo(point3.x, point3.y)
        val rectBottomRight = RectF(
            (measuredWidth.toFloat() - margin - widthRectangleBig),
            (measuredHeight.toFloat() - margin - widthRectangleBig),
            (measuredWidth.toFloat() - margin ),
            (measuredHeight.toFloat() - margin ))

        val rectBottomLeft = RectF(
            (0f ),
            (measuredHeight.toFloat() - margin - widthRectangleBig),
            (0f + ( widthRectangleBig) ),
            (measuredHeight.toFloat() - margin))

        val rectBottomMiddle = RectF(
            ( (measuredWidth - margin) / 2) - widthRectangleBig ,
            (measuredHeight.toFloat() - margin - widthRectangleBig),
            ((measuredWidth - margin) / 2) + widthRectangleBig ,
            (measuredHeight.toFloat() - margin + widthRectangleBig))

        val rectBottomMiddleRight = RectF(
            ( (measuredWidth - margin) / 4) - (widthRectangleBig *1.5f) ,
            (measuredHeight.toFloat() - margin -(widthRectangleBig / 2)),
            ((measuredWidth - margin) / 4) - (widthRectangleBig / 2) ,
            (measuredHeight.toFloat() - margin + (widthRectangleBig / 2)))

        val rectBottomMiddleRight2 = RectF(
            ( (measuredWidth - margin) / 4) + (widthRectangleBig / 2) ,
            (measuredHeight.toFloat() - margin -(widthRectangleBig / 2)),
            ((measuredWidth - margin) / 4) + (widthRectangleBig * 1.5f) ,
            (measuredHeight.toFloat() - margin + (widthRectangleBig / 2)))

        val rectBottomMiddleRight3 = RectF(
            (( (measuredWidth - margin) / 4) * 3 ) - (widthRectangleBig *1.5f) ,
            (measuredHeight.toFloat() - margin -(widthRectangleBig / 2)),
            (( (measuredWidth - margin) / 4) * 3 ) - (widthRectangleBig / 2) ,
            (measuredHeight.toFloat() - margin + (widthRectangleBig / 2)))

        val rectBottomMiddleRight4 = RectF(
            (( (measuredWidth - margin) / 4) * 3 ) + (widthRectangleBig / 2) ,
            (measuredHeight.toFloat() - margin -(widthRectangleBig / 2)),
            (( (measuredWidth - margin) / 4) * 3 ) + (widthRectangleBig * 1.5f) ,
            (measuredHeight.toFloat() - margin + (widthRectangleBig / 2)))

        myPath.arcTo( rectBottomRight, 0f, 90f, false)
//        myPath.arcTo( rectBottomMiddleRight4, 0f, -180f, false)
//        myPath.arcTo( rectBottomMiddleRight3, 0f, -180f, false)
        myPath.arcTo( rectBottomMiddle, 0f, -180f, false)
//        myPath.arcTo( rectBottomMiddleRight2, 0f, -180f, false)
//        myPath.arcTo( rectBottomMiddleRight, 0f, -180f, false)
        myPath.arcTo( rectBottomLeft, 90f, 90f, false)
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