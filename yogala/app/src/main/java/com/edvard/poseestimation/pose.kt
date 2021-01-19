package com.edvard.poseestimation
public object pose{
    //身體放鬆_腰部放鬆
    private var arrays1_1= arrayListOf(arrayListOf(585f,285f),arrayListOf(596.25f,405f),arrayListOf(540f,420f),
            arrayListOf(528.75f,285f),arrayListOf(540f,165f),arrayListOf(663.75f,405f),arrayListOf(641.25f,255f),
            arrayListOf(630f,135f),arrayListOf(551.25f,705f),arrayListOf(270f,780f),arrayListOf(1057.5f,780f),
            arrayListOf(630f,705f),arrayListOf(855f,720f),arrayListOf(945f,720f))
    private var arrays1_2_left= arrayListOf(arrayListOf(438.75f,255f),arrayListOf(495f,360f),arrayListOf(461.25f,405f),
            arrayListOf(371.25f,300f),arrayListOf(315f,180f),arrayListOf(540f,300f),arrayListOf(472.5f,225f),
            arrayListOf(393.75f,135f),arrayListOf(517.5f,660f),arrayListOf(596.25f,720f),arrayListOf(618.75f,720f),
            arrayListOf(585f,645f),arrayListOf(585f,705f),arrayListOf(900f,660f))
    private var arrays1_2_right= arrayListOf(arrayListOf(540f,210f),arrayListOf(495f,300f),arrayListOf(438.75f,300f),
            arrayListOf(483.75f,180f),arrayListOf(573.75f,75f),arrayListOf(540f,345f),arrayListOf(596.25f,240f),
            arrayListOf(652.5f,120f),arrayListOf(416.25f,630f),arrayListOf(405f,660f),arrayListOf(720f,660f),
            arrayListOf(495f,615f),arrayListOf(708.75f,660f),arrayListOf(810f,645f))
    private var arrays1_3_left= arrayListOf(arrayListOf(315f,240f),arrayListOf(405f,300f),arrayListOf(371.25f,360f),
            arrayListOf(371.25f,525f),arrayListOf(281.25f,660f),arrayListOf(438.75f,225f),arrayListOf(326.25f,165f),
            arrayListOf(191.25f,120f),arrayListOf(528.75f,525f),arrayListOf(360f,615f),arrayListOf(213.75f,720f),
            arrayListOf(630f,495f),arrayListOf(675f,705f),arrayListOf(675f,660f))
    private var arrays1_3_right= arrayListOf(arrayListOf(675f,375f),arrayListOf(585f,420f),arrayListOf(540f,375f),
            arrayListOf(573.75f,360f),arrayListOf(675f,285f),arrayListOf(607.5f,495f),arrayListOf(630f,645f),
            arrayListOf(708.75f,780f),arrayListOf(371.25f,660f),arrayListOf(393.75f,855f),arrayListOf(382.5f,840f),
            arrayListOf(450f,660f),arrayListOf(630f,735f),arrayListOf(776.25f,840f))
    private var arrays1_4_left= arrayListOf(arrayListOf(270f,420f),arrayListOf(371.25f,420f),arrayListOf(393.75f,495f),
            arrayListOf(405f,615f),arrayListOf(405f,720f),arrayListOf(371.25f,345f),arrayListOf(258.75f,300f),
            arrayListOf(135f,285f),arrayListOf(585f,540f),arrayListOf(585f,720f),arrayListOf(630f,720f),
            arrayListOf(630f,465f),arrayListOf(810f,600f),arrayListOf(945f,705f))
    private var arrays1_4_right= arrayListOf(arrayListOf(832.5f,420f),arrayListOf(720f,435f),arrayListOf(720f,375f),
            arrayListOf(843.75f,300f),arrayListOf(933.75f,285f),arrayListOf(720f,525f),arrayListOf(686.25f,630f),
            arrayListOf(686.25f,750f),arrayListOf(450f,510f),arrayListOf(292.5f,660f),arrayListOf(146.25f,765f),
            arrayListOf(506.25f,585f),arrayListOf(528.75f,780f),arrayListOf(135f,780f))
    //---------------------------------------------------------------------------------------------------//
//肌肉鍛鍊_核心肌群
    private var arrays2_1_left= arrayListOf(arrayListOf(315f,405f),arrayListOf(405f,420f),arrayListOf(416.25f,480f),
            arrayListOf(450f,555f),arrayListOf(438.75f,660f),arrayListOf(405f,345f),arrayListOf(315f,300f),
            arrayListOf(180f,270f),arrayListOf(540f,480f),arrayListOf(776.25f,600f),arrayListOf(900f,660f),
            arrayListOf(596.25f,480f),arrayListOf(776.25f,600f),arrayListOf(922.5f,660f))
    private var arrays2_1_right= arrayListOf(arrayListOf(810f,345f),arrayListOf(731.25f,375f),arrayListOf(708.75f,345f),
            arrayListOf(720f,300f),arrayListOf(843.75f,240f),arrayListOf(731.25f,465f),arrayListOf(720f,585f),
            arrayListOf(720f,600f),arrayListOf(540f,435f),arrayListOf(371.25f,555f),arrayListOf(270f,645f),
            arrayListOf(540f,495f),arrayListOf(405f,555f),arrayListOf(270f,645f))
    private var arrays2_2_left= arrayListOf(arrayListOf(315f,600f),arrayListOf(416.25f,630f),arrayListOf(438.75f,660f),
            arrayListOf(450f,780f),arrayListOf(438.75f,855f),arrayListOf(438.75f,600f),arrayListOf(360f,525f),arrayListOf(236.25f,480f),
            arrayListOf(585f,705f),arrayListOf(708.75f,780f),arrayListOf(765f,840f),arrayListOf(618.75f,660f),
            arrayListOf(742.5f,615f),arrayListOf(720f,765f))
    private var arrays2_2_right= arrayListOf(arrayListOf(675f,480f),arrayListOf(585f,525f),arrayListOf(540f,480f),
            arrayListOf(708.75f,390f),arrayListOf(731.25f,375f),arrayListOf(573.75f,600f),arrayListOf(551.25f,705f),arrayListOf(540f,780f),
            arrayListOf(348.75f,540f),arrayListOf(146.25f,480f),arrayListOf(213.75f,660f),arrayListOf(360f,600f),
            arrayListOf(225.5f,660f),arrayListOf(123.75f,750f))
    private var arrays2_3= arrayListOf(arrayListOf(135f,720f),arrayListOf(270f,735f),arrayListOf(270f,795f),
            arrayListOf(315f,825f),arrayListOf(258.75f,825f),arrayListOf(303.75f,675f),arrayListOf(191.25f,630f),arrayListOf(168.75f,705f),
            arrayListOf(483.75f,727.5f),arrayListOf(675f,727.5f),arrayListOf(888.75f,780f),arrayListOf(506.25f,727.5f),
            arrayListOf(675f,727.5f),arrayListOf(900f,780f))
    private var arrays2_4_left= arrayListOf(arrayListOf(236.25f,405f),arrayListOf(326.25f,435f),arrayListOf(360f,480f),
            arrayListOf(371.25f,600f),arrayListOf(360f,705f),arrayListOf(348.75f,360f),arrayListOf(337.5f,225f),arrayListOf(303.75f,75f),
            arrayListOf(517.5f,480f),arrayListOf(720f,585f),arrayListOf(810f,660f),arrayListOf(551.25f,420f),
            arrayListOf(697.5f,405f),arrayListOf(911.25f,360f))
    private var arrays2_4_right= arrayListOf(arrayListOf(855f,300f),arrayListOf(765f,330f),arrayListOf(720f,240f),
            arrayListOf(708.75f,120f),arrayListOf(708.75f,60f),arrayListOf(765f,420f),arrayListOf(731.25f,540f),arrayListOf(731.25f,600f),
            arrayListOf(528.75f,390f),arrayListOf(360f,420f),arrayListOf(135f,495f),arrayListOf(540f,465f),
            arrayListOf(416.25f,540f),arrayListOf(225f,660f))
    private var arrays2_5_left= arrayListOf(arrayListOf(180f,300f),arrayListOf(315f,315f),arrayListOf(348.75f,420f),
            arrayListOf(326.25f,540f),arrayListOf(326.25f,525f),arrayListOf(360f,225f),arrayListOf(461.25f,135f),arrayListOf(506.25f,255f),
            arrayListOf(573.75f,375f),arrayListOf(731.25f,405f),arrayListOf(900f,420f),arrayListOf(551.25f,345f),
            arrayListOf(686.25f,360f),arrayListOf(900f,420f))
    private var arrays2_5_right= arrayListOf(arrayListOf(855f,240f),arrayListOf(753.75f,270f),arrayListOf(708.75f,180f),
            arrayListOf(596.25f,120f),arrayListOf(528.75f,210f),arrayListOf(720f,360f),arrayListOf(720f,465f),arrayListOf(720f,480f),
            arrayListOf(472.5f,300f),arrayListOf(303.75f,420f),arrayListOf(135f,480f),arrayListOf(483.75f,360f),
            arrayListOf(281.25f,420f),arrayListOf(135f,480f))
    //-----------------------------------------------------------------------
    private var StdPoseNum1_1 = arrayListOf(1,2,3,4,5,6,7)
    private var StdPoseNum1_2_left = arrayListOf(1,2,3,4,5,6,7)
    private var StdPoseNum1_2_right = arrayListOf(1,2,3,4,5,6,7)
    private var StdPoseNum1_3_left = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12)
    private var StdPoseNum1_3_right = arrayListOf(1,2,3,4,5,6,7,8,9,11,12,13)
    private var StdPoseNum1_4_left = arrayListOf(1,2,3,4,5,6,7,8,9,11,12,13)
    private var StdPoseNum1_4_right = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12)
    //---------------------------------------------------------------------------------------------------//
    //肌肉鍛鍊_核心肌群
    private var StdPoseNum2_1_left = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var StdPoseNum2_1_right = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var StdPoseNum2_2_left = arrayListOf(1,2,3,4,5,6,7,8,9,11,12,13)
    private var StdPoseNum2_2_right = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var StdPoseNum2_3 = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)//寫一個OR判定左或右邊
    private var StdPoseNum2_4_left = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var StdPoseNum2_4_right = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var StdPoseNum2_5_left = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var StdPoseNum2_5_right = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var poseID : Int = 0
    private var assistLine : Int = 0
    fun setPose( poseNum : Int){
        poseID = poseNum
    }
    fun getPose() : Int {
        return  poseID
    }
    fun getArray () : ArrayList<ArrayList<Float>> {
        var stdPose : ArrayList<ArrayList<Float>>
        when (poseID){
            1-> stdPose = arrays1_1
            else -> {stdPose=arrays2_3}
        }
        return stdPose

    }
    fun getStdPoseNum () :ArrayList<Int>{
        var returnArray : ArrayList<Int>
        when(poseID){
            1-> returnArray=StdPoseNum1_1
            else -> {returnArray=StdPoseNum2_3}
        }
        return returnArray

    }
    fun setAssisLine(setting:Int){
        assistLine=setting
    }
    fun getAssistLine():Int{
        return assistLine
    }

}