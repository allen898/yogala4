/*
 * Copyright 2018 Zihua Zeng (edvard_hua@live.com), Lang Feng (tearjeaker@hotmail.com)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edvard.poseestimation
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.lang.Math.cos
import java.lang.Math.sin


/**
 * Created by edvard on 18-3-23.
 */

class DrawView : View {
    //private var ImageView: ImageView ?= null
    private var course: Array<Array<String>> = arrayOf<Array<String>>(arrayOf("腰部放鬆", "頸部放鬆", "腿部放鬆"), arrayOf("早晨甦醒 1", "早晨甦醒 2", "早晨甦醒 3"), arrayOf("健身美體 1", "健身美體 2", "健身美體 3"))
    var themeAdapter: ArrayAdapter<String>? = null
    var courseAdapter: ArrayAdapter<String>? = null

    private var returntext : String =""
    private var mark : Int = 0
    private var mRatioWidth = 0
    private var mRatioHeight = 0
    private val mDrawPoint = ArrayList<PointF>() //儲存繪製前各keypoint的位置
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mRatioX: Float = 0.toFloat()
    private var mRatioY: Float = 0.toFloat()
    private var mImgWidth: Int = 0
    private var mImgHeight: Int = 0
    private var NewArray = ArrayList<PointF>()
    private var StdPoseNum = ArrayList<Int>()
    private var check_pose: Int = 0




    private val mColorArray = intArrayOf(
            resources.getColor(R.color.color_top, null),
            resources.getColor(R.color.color_neck, null),
            resources.getColor(R.color.color_l_shoulder, null),
            resources.getColor(R.color.color_l_elbow, null),
            resources.getColor(R.color.color_l_wrist, null),
            resources.getColor(R.color.color_r_shoulder, null),
            resources.getColor(R.color.color_r_elbow, null),
            resources.getColor(R.color.color_r_wrist, null),
            resources.getColor(R.color.color_l_hip, null),
            resources.getColor(R.color.color_l_knee, null),
            resources.getColor(R.color.color_l_ankle, null),
            resources.getColor(R.color.color_r_hip, null),
            resources.getColor(R.color.color_r_knee, null),
            resources.getColor(R.color.color_r_ankle, null),
            resources.getColor(R.color.color_background, null)
    )

    private val circleRadius: Float by lazy {
        dip(3).toFloat()
    }

    private val mPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = FILL
            strokeWidth = dip(2).toFloat()
            textSize = sp(13).toFloat()
        }
    }

    constructor(context: Context) : super(context)

    constructor(
            context: Context,
            attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun photo() : Int{
        return check_pose
    }
    fun returnTexts() : String{
        return returntext
    }


    fun slope(prePoint: PointF,Point: PointF ): Float {
        var slope  = (Point.y-prePoint.y)/(Point.x-prePoint.x)*(-1)
        //算出兩個點的斜率
        return slope
    }
    fun newMark(marks :Int){
        mark=marks
    }
    fun check( m0 : Float, prePoint:PointF,point :PointF ): Int {
        var index = 1
        var x = prePoint.x - point.x
        var y = prePoint.y - point.y
        var x1= (x * cos(10.0)).toFloat() - (y * sin(10.0).toFloat())
        var y1= (x * sin(10.0)).toFloat() + (y * cos(10.0).toFloat())
        var x2= (x * cos(-10.0)).toFloat() - (y * sin(-10.0).toFloat())
        var y2= (x * sin(-10.0)).toFloat() + (y * cos(-10.0).toFloat())
        var m3 = y/x *(-1)
        var m1=y1/x1*(-1)
        var m2=y2/x2*(-1)
        if (m3 > 5.671 || m3 < -5.671 ){
            if ( m1 > m2){
                if(m0 >=m1 ||m0 <= m2){
                    index=1
                }
                else {
                    index=-1
                }
            }
            else {
                //m1<=m2
                if (m0 >= m2 || m0 <= m1){
                    index=1
                }
                else{
                    index=-1
                }
            }
        }
        else{
            if (m1 > m2){
                if (m1 >= m0 && m0 >= m2){
                    index=1
                }
                else{
                    index=-1
                }
            }
            else{
                if(m2 >= m0 && m0 >= m1){
                    index=1
                }
                else{
                    index=-1
                }
            }
        }
        return index


    }
    //新增要做辨識的點的DATA
    fun setposeID(poseID : Int){

    }
    //另一個方法
    fun NewStdPoseNums(arrays : ArrayList<Int>) {
        StdPoseNum.clear()
        //var arrays = pose?.getStdPoseNum()
        for (j in 0 until arrays.size) {
            var x = arrays[j]
            StdPoseNum.add(x)
        }

    }

    fun NewStdPoseNum(arrays : ArrayList<Int>, marks : Int) {
        StdPoseNum.clear()
        //var arrays = pose?.getStdPoseNum()
        for (j in 0 until arrays.size) {
            var x = arrays[j]
            StdPoseNum.add(x)
        }
        mark = marks
    }
    //新增標準動作的DATA

    fun NewData(arrays2 : ArrayList<ArrayList<Float>>) {
        //var arrays2 = pose?.getArray()
        NewArray.clear()
        for (j in 0 until arrays2.size) {
            var x = arrays2[j][0]
            var y = arrays2[j][1]
            NewArray.add(PointF(x, y))
        }

    }

    fun Comparision(viewArrays :ArrayList<PointF>,actualArrays: ArrayList<PointF>,canvas : Canvas ): ArrayList<Int>?{
        //if (viewArrays.isEmpty()) return
        var correct = ArrayList<Int>()
        correct.clear()
        var prePointF: PointF? = null
        //var prepointF: PointF? =null
        var point1 = PointF(actualArrays[1].x,actualArrays[1].y)
        val p1 = viewArrays[1]
        var lineId = 9999
        for ((index, pointF) in viewArrays.withIndex()) {
            if (index == 1) continue
            when (index) {
                //0-1
                0 -> {
                    var point = PointF(actualArrays[index].x, actualArrays[index].y)
                    var m1 = slope(pointF,p1)
                    var index1 = check(m1,point1 ,point)
                    DrawLines(p1,pointF,canvas,index1)
                    if (index1==1){
                        lineId = 1
                        correct.add(lineId)
                    }

                }
                // 1-2, 1-5, 1-8, 1-11
                2, 5, 8, 11 -> {
                    //canvas.drawLine(p1.x, p1.y, pointF.x, pointF.y, mPaint)
                    var point = PointF(actualArrays[index].x,actualArrays[index].y)
                    var m2 = slope(pointF, p1)
                    var index2 = check(m2,point1 ,point)
                    DrawLines(p1,pointF,canvas,index2)
                    if (index2==1){
                        lineId = index
                        correct?.add(lineId)
                    }

                }
                else -> {
                    if (prePointF != null) {

                        var prepoint = PointF(actualArrays[index-1].x,actualArrays[index-1].y)
                        var point =PointF(actualArrays[index].x,actualArrays[index].y)
                        var m3 = slope(pointF, prePointF)
                        var index3 = check(m3,prepoint,point)
                        DrawLines(prePointF,pointF,canvas,index3)
                        if (index3==1){
                            lineId = index
                            correct?.add(lineId)
                        }

                    }
                }
                //canvas.scale(3.0f,3.0f)
            }
            prePointF = pointF
        }
        return correct
    }

    fun DrawLines(prepoint : PointF,Point : PointF, canvas: Canvas,index :Int) {
        when (index) {
            1 -> {
                mPaint.color = 0xff6fa8dc.toInt()
            }
            else -> {
                mPaint.color = 0xffcb4154.toInt()
            }

        }
        if(pose.getAssistLine()==1) {
            canvas.drawLine(prepoint.x, prepoint.y, Point.x, Point.y, mPaint)
        }
    }
    fun showWrong(StdPoseNums : ArrayList<Int> , corrections :ArrayList<Int>? ) : ArrayList<Int> {

        var  tests = IntArray(StdPoseNums.size)
        for(i in 0 until StdPoseNums.size){
            tests[i] = StdPoseNums[i]
        }
        if (corrections != null) {
            for (i in 0 until StdPoseNums.size){
                for (j  in 0 until  corrections.size){
                    if (StdPoseNums[i]==corrections[j]){

                        tests[i]=0

                    }
                }
            }
        }
        var testsAL = ArrayList<Int>()
        if (tests != null) {
            for (i in 0 until tests.size) {
                testsAL.add(tests[i])
            }
        }
        else {testsAL.add(0)}
        return testsAL
    }

    fun showhint(canvas: Canvas, testsAL: ArrayList<Int> ) {
        var texts: String = ""
        var hint1 = testsAL.contains(1)
        var hint2 = testsAL.contains(2)
        var hint3 = testsAL.contains(3)
        var hint4 = testsAL.contains(4)
        var hint5 = testsAL.contains(5)
        var hint6 = testsAL.contains(6)
        var hint7 = testsAL.contains(7)
        var hint8 = testsAL.contains(8)
        var hint9 = testsAL.contains(9)
        var hint10 = testsAL.contains(10)
        var hint11 = testsAL.contains(11)
        var hint12 = testsAL.contains(12)
        var hint13 = testsAL.contains(13)
        if(mark == 0) {
            if (hint1 == true) {
                texts += "頭跟脖子不正確\n"
                check_pose = 0
            }
            if (hint2==true || hint3==true || hint4==true) {
                texts += "左手臂不正確\n"
                check_pose = 0
            }
            if (hint5 == true || hint6 == true || hint7 == true) {
                texts += "右手臂不正確\n"
                check_pose = 0
            }
            if (hint8 == true || hint9 == true || hint10 == true) {
                texts += "左腳不正確\n"
                check_pose = 0
            }
            if (hint11 == true || hint12 == true || hint13 == true) {
                texts += "右腳不正確\n"
                check_pose = 0
            }
            if(hint1 != true&&hint2 != true&&hint3 != true&&hint4 != true&&hint5 != true&&hint6 != true&&hint7 != true&&hint8 != true&&hint9 != true&&hint10 != true&&hint11 != true&&hint12 != true&& hint13 != true){
                texts = "做的很棒"
                check_pose = 1
            }
            //canvas.drawText("$texts", 300f, 500f, mPaint)
        }
     else if (mark == 1){
            if(hint12 == true || hint13 == true){
                if(hint9 == true || hint10 == true){
                    texts = "姿勢不正確"
                    check_pose = 0
                }
                else {
                    texts = "做的很棒"
                    check_pose = 1
                }
            }
      //canvas.drawText("$texts",300f,500f,mPaint)
        }
        returntext = texts
        texts=""
    }

    fun CheckLine(StdPoseNums : ArrayList<Int> , correction :ArrayList<Int>? ) : Int  {
        var index : Int
        var item = 0

        if (correction != null) {
            for (i in 0 until StdPoseNums.size){
                for (j  in 0 until  correction.size){
                    if (StdPoseNums[i]==correction[j]){
                        item +=1

                    }
                }
            }
        }
        if(item == StdPoseNums.size){
            index =1
        }
        else {
            index =-1
        }
        return index
    }

/*fun Drawframe(prePoint: PointF,Point: PointF,canvas :Canvas,slope: Float,index : Int){
    when(index){
        1 ->{
            mPaint.color = 0xff6fa8dc.toInt()
        }
        else ->{
            mPaint.color = 0xffcb4154.toInt()
        }
    }
  //var slopes : Double = slope.toDouble() //：轉換為Double類型。
  //根據斜率決定要向左右還是上下延伸
  if (slope <=1  &&  slope >= -1){
    canvas.drawLine(prePoint.x, prePoint.y+50, Point.x, Point.y+50, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-50, Point.x, Point.y-50, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-50, prePoint.x, prePoint.y+50, mPaint)
    canvas.drawLine(Point.x, Point.y-50, Point.x, Point.y+50, mPaint)
  }
  else if (slope<-1 || slope >1){
    canvas.drawLine(prePoint.x+50, prePoint.y, Point.x+50, Point.y, mPaint)
    canvas.drawLine(prePoint.x-50, prePoint.y, Point.x-50, Point.y, mPaint)
    canvas.drawLine(prePoint.x-50, prePoint.y, prePoint.x+50, prePoint.y, mPaint)
    canvas.drawLine(Point.x-50, Point.y, Point.x+50, Point.y, mPaint)
  }
  else{
    canvas.drawLine(prePoint.x, prePoint.y+50, Point.x, Point.y+50, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-50, Point.x, Point.y-50, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-50, prePoint.x, prePoint.y+50, mPaint)
    canvas.drawLine(Point.x, Point.y-50, Point.x, Point.y+50, mPaint)
  }
}
fun DrawHeadframe(prePoint: PointF,Point: PointF,canvas :Canvas,slope: Float,index : Int){
  when(index){
    1 ->{
      mPaint.color = 0xff6fa8dc.toInt()
    }
    else ->{
      mPaint.color = 0xffcb4154.toInt()
    }
  }

  //var slopes : Double = slope.toDouble() //：轉換為Double類型。
  //根據斜率決定要向左右還是上下延伸
  if (slope <=1  &&  slope >= -1){
    canvas.drawLine(prePoint.x, prePoint.y+100, Point.x, Point.y+100, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-100, Point.x, Point.y-100, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-100, prePoint.x, prePoint.y+100, mPaint)
    canvas.drawLine(Point.x, Point.y-100, Point.x, Point.y+100, mPaint)
  }
  else if (slope<-1 || slope >1){
    canvas.drawLine(prePoint.x+100, prePoint.y, Point.x+100, Point.y, mPaint)
    canvas.drawLine(prePoint.x-100, prePoint.y, Point.x-100, Point.y, mPaint)
    canvas.drawLine(prePoint.x-100, prePoint.y, prePoint.x+100, prePoint.y, mPaint)
    canvas.drawLine(Point.x-100, Point.y, Point.x+100, Point.y, mPaint)
  }
  else{
    canvas.drawLine(prePoint.x, prePoint.y+100, Point.x, Point.y+100, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-100, Point.x, Point.y-100, mPaint)
    canvas.drawLine(prePoint.x, prePoint.y-100, prePoint.x, prePoint.y+100, mPaint)
    canvas.drawLine(Point.x, Point.y-100, Point.x, Point.y+100, mPaint)
  }
}*/

    fun setImgSize(
            width: Int,
            height: Int
    ) {
        mImgWidth = width
        mImgHeight = height
        requestLayout()
    }

    /**
     * Scale according to the device.
     * @param point 2*14
     */
    fun setDrawPoint(
            point: Array<FloatArray>,
            ratio: Float
    ) {
        mDrawPoint.clear()

        var tempX: Float
        var tempY: Float
        for (i in 0..13) {
            tempX = point[0][i] /  ratio / mRatioX
            tempY = point[1][i] / ratio / mRatioY
            mDrawPoint.add(PointF(tempX, tempY))
        }
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that is,
     * calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    fun setAspectRatio(
            width: Int,
            height: Int
    ) {
        if (width < 0 || height < 0) {
            throw IllegalArgumentException("Size cannot be negative.")
        }
        mRatioWidth = width
        mRatioHeight = height
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDrawPoint.isEmpty()) return
        var StandardArray : ArrayList<PointF> = NewArray
        var array : ArrayList<ArrayList<Float>>  = pose.getArray()
        var StdPoseNums = pose.getStdPoseNum()

        var ID = pose.getPose()
        //canvas.drawText("$ID",400f,400f,mPaint)
        NewStdPoseNums(StdPoseNums)
        NewData(array)


        val correction = Comparision(mDrawPoint,StandardArray,canvas)
        //canvas.drawText("$correction", 100f, 100f, mPaint)
        var prePointF: PointF? = null
        var prepointF: PointF? =null
        //mPaint.color = 0xff6fa8dc.toInt()
        //var point1 = PointF(NewArray[1].x,NewArray[1].y)
        //val p1 = mDrawPoint[1]
        var test1 = showWrong(StdPoseNum,correction)
        //canvas.drawText("$test1",500f,1200f,mPaint)
        showhint(canvas,test1)

        /*var checks = CheckLine(StdPoseNum,correction)
        if (checks ==1) {
          mPaint.color = 0xff6fa8dc.toInt()
          canvas.drawText("姿勢很標準繼續保持", 500f, 1400f, mPaint)
        }
        else if (checks == -1) {
          mPaint.color = 0xffcb4154.toInt()
          canvas.drawText("姿勢不正確,試著調整一下吧", 500f, 1400f, mPaint)
        }*/


        for ((index, pointF) in mDrawPoint.withIndex()) {
            if (index == 1) continue
            when (index) {
                //0-1
                0 -> {
                    //canvas.drawLine(pointF.x, pointF.y, p1.x, p1.y, mPaint)
                    //var test = PointF(472.5f,299.9f)
                    //var test2 =PointF(472f ,540.0f)
                    //var point = PointF(NewArray[index].x,NewArray[index].y)
                    //canvas.drawLine(pointF.x, pointF.y, p1.x, p1.y, mPaint)
                    //var m1 = slope(pointF, p1)
                    //canvas.drawText("$m1", (pointF.x+p1.x)/2, (pointF.y+p1.y)/2, mPaint)
                    //var index1 = check(m1,point1 ,point)DrawLines(prePointF,pointF,canvas,index1)
                    //DrawLines(p1,pointF,canvas,index1)

                    //DrawHeadframe(p1,pointF,canvas,m1,index1)
                    //var slopes : Double = slope.toDouble() //：轉換為Double類型。


                    // canvas.drawText(text1,100f,100f,mPaint)

                }
                // 1-2, 1-5, 1-8, 1-11
                2, 5, 8, 11 -> {
                    //canvas.drawLine(p1.x, p1.y, pointF.x, pointF.y, mPaint)
                    //var point = PointF(NewArray[index].x,NewArray[index].y)
                    //var m2 = slope(pointF, p1)
                    //var index1 = check(m2,point1 ,point)
                    //canvas.drawText("$m2", (pointF.x+p1.x)/2, (pointF.y+p1.y)/2, mPaint)
                    //canvas.drawLine(p1.x, p1.y, pointF.x, pointF.y, mPaint)
                    //DrawLines(p1,pointF,canvas,index1)
                    // Drawframe(pointF,p1,canvas,m2,index1)

                }
                else -> {
                    if (prePointF != null) {
                        //mPaint.color = 0xff6fa8dc.toInt()
                        //canvas.drawLine(prePointF.x, prePointF.y, pointF.x, pointF.y, mPaint)
                        // var prepoint = PointF(NewArray[index-1].x,NewArray[index-1].y)
                        // var point =PointF(NewArray[index].x,NewArray[index].y)
                        //var m3 = slope(pointF, prePointF)
                        //canvas.drawText("$m3", (pointF.x+prePointF.x)/2, (pointF.y+prePointF.y)/2, mPaint)
                        // var index1 = check(m3,prepoint,point)
                        //  Drawframe(pointF,prePointF,canvas,m3,index1)
                        //canvas.drawLine(prePointF.x, prePointF.y, pointF.x, pointF.y, mPaint)
                        //
                        //canvas.drawOval( pointF.x, pointF.y, p1.x, p1.y, mPaint)
                        //  canvas.drawRect(pointF.x+100, pointF.y,prePointF.x-100, prePointF.y, mPaint)
                    }
                }
                //canvas.scale(3.0f,3.0f)
            }
            prePointF = pointF


        }

        for ((index, pointF) in mDrawPoint.withIndex()) {

            //var test_x = 700.2f
            mPaint.color = mColorArray[index]//讀取mcolorarray裡宣告好的顏色
            if(pose.getAssistLine()==1) {
                canvas.drawCircle(pointF.x, pointF.y, circleRadius, mPaint)//畫點，X軸，Y軸，圓半徑，顏色
            }


                /*if (slopes <= -1) {
                  canvas.drawText("yes", 200f, 200f, mPaint)
                } else {
                  canvas.drawText("no", 200f, 200f, mPaint)
                }
                */

        }
        //var test = 100 * cos(30)
        //canvas.drawText(test, 100, 200, mPaint)

    }

    override fun onMeasure(
            widthMeasureSpec: Int,
            heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height)
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                mWidth = width
                mHeight = width * mRatioHeight / mRatioWidth
            } else {
                mWidth = height * mRatioWidth / mRatioHeight
                mHeight = height
            }
        }

        setMeasuredDimension(mWidth, mHeight)

        mRatioX = mImgWidth.toFloat() / mWidth
        mRatioY = mImgHeight.toFloat() / mHeight
    }
}

// 哲維你好嗎
//fuck you