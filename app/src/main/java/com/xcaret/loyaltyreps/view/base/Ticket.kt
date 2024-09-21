package com.xcaret.loyaltyreps.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import com.xcaret.loyaltyreps.R

class Ticket @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    val imagen: Bitmap = BitmapFactory.decodeResource(resources,  R.drawable.xplor_v2)
    val margen = 10f
    var widtRectangleBig:Float = 0.0f
    var myPath: Path = Path()
    val paint: Paint = Paint()
    val paint1: Paint = Paint()
    val paint2: Paint = Paint()
    var img: Bitmap = this.imagen

    init {

        paint.setColor(Color.TRANSPARENT)
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth= 2f
        paint.setShadowLayer(10f, 2f, 3f, Color.BLACK)

        paint2.setColor(Color.BLACK)
        paint.isAntiAlias = true
        paint2.style = Paint.Style.STROKE
        paint2.strokeWidth= 2f
        paint.setShadowLayer(10f, 2f, 3f, Color.BLACK)


        paint1.color = Color.BLACK
        paint1.style = Paint.Style.STROKE
        paint1.strokeWidth= 2f
        paint1.setShadowLayer(20f, 5f, 3f, Color.BLACK)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i("medida", "$measuredHeight - $measuredWidth")
        myPath = createPath()
    }

    fun createPath(): Path {
        widtRectangleBig = measuredWidth.toFloat() * 0.08f
        val rectTopLeft = RectF(
            (0f),
            (0f),
            (0f + ( widtRectangleBig) ),
            (0f + widtRectangleBig))
        val rectTopRight = RectF(
            (measuredWidth - margen - widtRectangleBig),
            (0f),
            (measuredWidth - margen ),
            (0f + widtRectangleBig))

        val rectTopLeftDown = RectF(
            ( 0f - widtRectangleBig / 2),
            ( ((measuredHeight - margen) / 4) * 3  ),
            (0f + widtRectangleBig / 2 ),
            (  ((measuredHeight - margen) / 4) * 3  + (widtRectangleBig)) )



        val rectTopRightDowm = RectF(
            (measuredWidth - margen - (widtRectangleBig / 2)),
            (((measuredHeight - margen) / 4) * 3),
            (measuredWidth - margen + (widtRectangleBig / 2) ),
            (((measuredHeight - margen) / 4) * 3  + (widtRectangleBig)))

        val myPath = Path()

        val point1 = PointF(0f , 0f + ( widtRectangleBig))
        val point2 = PointF(measuredWidth.toFloat()- margen, measuredHeight.toFloat())
        val point4 = PointF(0f , measuredHeight.toFloat())

        myPath.moveTo(point1.x, point1.y)

        myPath.arcTo( rectTopLeft, 180f, 90f, false)
        myPath.arcTo( rectTopRight, 270f, 90f, false)
        myPath.arcTo( rectTopRightDowm, 270f, -180f, false)
        myPath.lineTo(point2.x, point2.y)

        myPath.lineTo(point4.x, point4.y)
        myPath.arcTo( rectTopLeftDown, 90f, -180f, false)
        myPath.close()

        return myPath
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {


//        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OUT))

        canvas.drawPath(myPath, paint)
        canvas.clipPath(myPath)
        paint2.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(img,null, RectF(0f ,0f, measuredHeight.toFloat() - margen, measuredHeight.toFloat()),  paint2)

    }
    fun setImage(newImage: Bitmap){
        img = newImage
    }

}