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



//import android.support.v13.app.FragmentCompat
//import android.support.v4.content.ContextCompat
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.*
import android.hardware.camera2.*
import android.media.AudioManager
import android.media.ImageReader
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.legacy.app.FragmentCompat
import com.example.aninterface.ToolbarActivity
import com.example.aninterface.ui.Video.VideoFragment
import com.example.aninterface.ui.Video.VideoIdentificationFragment
import kotlinx.android.synthetic.main.fragment_camera2_basic.*
import java.io.IOException
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit


/**
 * Basic fragments for the Camera.
 */
class Camera2BasicFragment : Fragment(), FragmentCompat.OnRequestPermissionsResultCallback {
  private var returnTexts : String =""
  private var curTime : Long = 50000

  private val lock = Any()
  private var Timer : CountDownTimer? = null
  private var Timer2 : CountDownTimer? = null
  private var runClassifier = false
  private var checkedPermissions = false
  private var textView: TextView? = null
  private var textureView: AutoFitTextureView? = null
  private var layoutFrame: AutoFitFrameLayout? = null
  private var drawView: DrawView? = null
  private var classifier: ImageClassifier? = null
  private var layoutBottom: ViewGroup? = null
  private var radiogroup: RadioGroup? = null
  private var imageView1: ImageView? = null

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


  /**
   * [TextureView.SurfaceTextureListener] handles several lifecycle events on a [ ].
   */
  private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {

    override fun onSurfaceTextureAvailable(
            texture: SurfaceTexture,
            width: Int,
            height: Int
    ) {
      openCamera(width, height)
    }

    override fun onSurfaceTextureSizeChanged(
            texture: SurfaceTexture,
            width: Int,
            height: Int
    ) {
      configureTransform(width, height)
    }

    override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
      return true
    }

    override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {}
  }

  /**
   * ID of the current [CameraDevice].
   */
  private var cameraId: String? = null

  /**
   * A [CameraCaptureSession] for camera preview.
   */
  private var captureSession: CameraCaptureSession? = null

  /**
   * A reference to the opened [CameraDevice].
   */
  private var cameraDevice: CameraDevice? = null

  /**
   * The [android.util.Size] of camera preview.
   */
  private var previewSize: Size? = null

  /**
   * [CameraDevice.StateCallback] is called when [CameraDevice] changes its state.
   */
  private val stateCallback = object : CameraDevice.StateCallback() {

    override fun onOpened(currentCameraDevice: CameraDevice) {
      // This method is called when the camera is opened.  We start camera preview here.
      cameraOpenCloseLock.release()
      cameraDevice = currentCameraDevice
      createCameraPreviewSession()
    }

    override fun onDisconnected(currentCameraDevice: CameraDevice) {
      cameraOpenCloseLock.release()
      currentCameraDevice.close()
      cameraDevice = null
    }

    override fun onError(
            currentCameraDevice: CameraDevice,
            error: Int
    ) {
      cameraOpenCloseLock.release()
      currentCameraDevice.close()
      cameraDevice = null
      val activity = activity
      activity?.finish()
    }
  }

  /**
   * An additional thread for running tasks that shouldn't block the UI.
   */
  private var backgroundThread: HandlerThread? = null

  /**
   * A [Handler] for running tasks in the background.
   */
  private var backgroundHandler: Handler? = null

  /**
   * An [ImageReader] that handles image capture.
   */
  private var imageReader: ImageReader? = null

  /**
   * [CaptureRequest.Builder] for the camera preview
   */
  private var previewRequestBuilder: CaptureRequest.Builder? = null

  /**
   * [CaptureRequest] generated by [.previewRequestBuilder]
   */
  private var previewRequest: CaptureRequest? = null

  /**
   * A [Semaphore] to prevent the app from exiting before closing the camera.
   */
  private val cameraOpenCloseLock = Semaphore(1)

  /**
   * A [CameraCaptureSession.CaptureCallback] that handles events related to capture.
   */
  private val captureCallback = object : CameraCaptureSession.CaptureCallback() {

    override fun onCaptureProgressed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            partialResult: CaptureResult
    ) {
    }

    override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
    ) {
    }
  }

  private val requiredPermissions: Array<String>
    get() {
      val activity = activity
      return try {
        val info = activity
                .packageManager
                .getPackageInfo(activity.packageName, PackageManager.GET_PERMISSIONS)
        val ps = info.requestedPermissions
        if (ps != null && ps.isNotEmpty()) {
          ps
        } else {
          arrayOf()
        }
      } catch (e: Exception) {
        arrayOf()
      }

    }

  /**
   * Takes photos and classify them periodically.
   */
  private val periodicClassify = object : Runnable {
    override fun run() {
      synchronized(lock) {
        if (runClassifier) {
          classifyFrame()
        }
      }
      backgroundHandler!!.post(this)
    }
  }

  /**
   * Shows a [Toast] on the UI thread for the classification results.
   *
   * @param text The message to show
   */
  private fun showToast(text: String) {
    val activity = activity
    //hint1!!.setText("請根據畫面提示做出動作")
    activity?.runOnUiThread {
      //textView!!.text = text
      drawView!!.invalidate()
      var index = 1
      var soundPool: SoundPool? = null
      var soundId = 1
      soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
      soundPool!!.load(context, R.raw.bingo, 1)
        do {
          var photo =drawView!!.photo()
          if (photo == 1) {
                if (Timer == null) {
                  Timer = object : CountDownTimer(curTime, 1000) {

                    override fun onFinish() {
                      //使用setText()方法修改文本
                      text1!!.setText("finished")
                      soundPool?.play(soundId, 1F, 1F, 0, 0, 1F)
                      check_mark!!.setImageResource(R.drawable.shit)
                      curTime= 50000
                      val intent = Intent(context, FinishActivity::class.java)
                      startActivity(intent)

                    }

                    override fun onTick(millisUntilFinished: Long) {
                      var test1 = millisUntilFinished / 1000
                       curTime = millisUntilFinished
                      text1!!.setText("請繼續維持$test1" + "秒")
                    }
                  }.start()
                }

          }
          else if (photo == 0) {
            check_mark!!.setImageDrawable(null)
            if (Timer != null) {
              Timer!!.cancel()
              Timer = null
              text1?.setText("請根據畫面提示做出動作")
            }
          }
           returnTexts = drawView!!.returnTexts()
          textView3?.setText(returnTexts)
        }
       while (index != 1)

      /*do{
        var returnTexts = drawview!!.returnTexts()
        textView3!!.setText(returnTexts)
      }
        while(index !=1)*/
    }
  }

  /**
   * Layout the preview and buttons.
   */
  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_camera2_basic, container, false)
  }

  /**
   * Connect the buttons to their event handler.
   */
  override fun onViewCreated(
          view: View,
          savedInstanceState: Bundle?
  ) {
    //textView = view.findViewById(R.id.text)椹樹
    textureView = view.findViewById(R.id.texture)
    layoutFrame = view.findViewById(R.id.layout_frame)
    drawView = view.findViewById(R.id.drawview)
    //layoutBottom = view.findViewById(R.id.layout_bottom)
//    radiogroup = view.findViewById(R.id.radiogroup);


    /*radiogroup!!.setOnCheckedChangeListener { group, checkedId ->
      if(checkedId==R.id.radio_cpu){
        startBackgroundThread(Runnable { classifier!!.initTflite(false) })
      } else {
        startBackgroundThread(Runnable { classifier!!.initTflite(true) })
      }
    }*/

  }

  /**
   * Load the model and labels.
   */
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    text1!!.setText("請根據畫面提示做出動作")
    // 產生音效
     var soundPool: SoundPool? = null
     var soundId = 1
    soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
    soundPool!!.load(context, R.raw.bingo, 1)
    soundPool!!.load(context, R.raw.flip, 2)
    Timer2 = object : CountDownTimer(curTime, 1000) {

      override fun onFinish() {
        //使用setText()方法修改文本

        /*if(soundId<3) {
          soundPool?.play(soundId, 1F, 1F, 0, 0, 1F)
          soundId++
        }
        curTime= 10000
        (Timer2 as CountDownTimer).start()
      */
      }


        override fun onTick(millisUntilFinished: Long) {
          var test1 = millisUntilFinished / 1000
          curTime = millisUntilFinished

        }
      }
    (Timer2 as CountDownTimer).start()
    //產生資料data
    PoseFrame.setImageDrawable(null)
    btn_video_last_step.setOnClickListener(object :View.OnClickListener{
      override fun onClick(v: View?) {
        if(pose.getAssistLine()==1) {
          pose.setAssisLine(0)
        }
        else
          pose.setAssisLine(1)

      }
    })
    btn_video_skip_step.setOnClickListener(object : View.OnClickListener {
      override fun onClick(v: View?) {
        val intent = Intent(context, FinishActivity::class.java)
        startActivity(intent)

        //Pose?.setPose(2)
        //drawView!!.NewData()
       // drawView!!.NewStdPoseNum( 1)
       // PoseFrame!!.setImageResource(R.drawable.pose2)
      }
    })
      //動態更新姿勢資料
      pose?.setPose(1)
    //drawView!!.NewData(arrays1_1)
    //drawView!!.NewStdPoseNum( StdPoseNum1_1,0)
    drawView!!.newMark(0)
    PoseFrame!!.setImageResource(R.drawable.pose4)

    try {
      // create either a new ImageClassifierQuantizedMobileNet or an ImageClassifierFloatInception
      //      classifier = new ImageClassifierQuantizedMobileNet(getActivity());
      classifier = ImageClassifierFloatInception.create(activity)
      if (drawView != null)

        drawView!!.setImgSize(classifier!!.imageSizeX, classifier!!.imageSizeY)
    } catch (e: IOException) {
      Log.e(TAG, "Failed to initialize an image classifier.", e)
    }
  }

  @Synchronized
  override fun onResume() {
    super.onResume()

    backgroundThread = HandlerThread(HANDLE_THREAD_NAME)
    backgroundThread!!.start()
    backgroundHandler = Handler(backgroundThread!!.getLooper())
    runClassifier = true

    startBackgroundThread(Runnable { classifier!!.initTflite(true) })
    startBackgroundThread(periodicClassify)

    // When the screen is turned off and turned back on, the SurfaceTexture is already
    // available, and "onSurfaceTextureAvailable" will not be called. In that case, we can open
    // a camera and start preview from here (otherwise, we wait until the surface is ready in
    // the SurfaceTextureListener).
    if (textureView!!.isAvailable) {
      openCamera(textureView!!.width, textureView!!.height)
    } else {
      textureView!!.surfaceTextureListener = surfaceTextureListener
    }
  }

  override fun onPause() {
    closeCamera()
    stopBackgroundThread()
    super.onPause()
  }

  override fun onDestroy() {
    classifier!!.close()
    super.onDestroy()
  }

  /**
   * Sets up member variables related to camera.
   *
   * @param width  The width of available size for camera preview
   * @param height The height of available size for camera preview
   */
  private fun setUpCameraOutputs(
          width: Int,
          height: Int
  ) {
    val activity = activity
    val manager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    try {
      for (cameraId in manager.cameraIdList) {
        val characteristics = manager.getCameraCharacteristics(cameraId)

        // We don't use a front facing camera in this sample.
        val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT)//設置前鏡頭
        {
          continue
        }

        val map =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue

        // // For still image captures, we use the largest available size.
        val largest = Collections.max(
                Arrays.asList(*map.getOutputSizes(ImageFormat.JPEG)), CompareSizesByArea()
        )
        imageReader = ImageReader.newInstance(
                largest.width, largest.height, ImageFormat.JPEG, /*maxImages*/ 2
        )

        // Find out if we need to swap dimension to get the preview size relative to sensor
        // coordinate.
        val displayRotation = activity.windowManager.defaultDisplay.rotation

        /* Orientation of the camera sensor */
        val sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        var swappedDimensions = false
        when (displayRotation) {
          Surface.ROTATION_0, Surface.ROTATION_180 -> if (sensorOrientation == 90 || sensorOrientation == 270) {
            swappedDimensions = true
          }
          Surface.ROTATION_90, Surface.ROTATION_270 -> if (sensorOrientation == 0 || sensorOrientation == 180) {
            swappedDimensions = true
          }
          else -> Log.e(TAG, "Display rotation is invalid: $displayRotation")
        }

        val displaySize = Point()
        activity.windowManager.defaultDisplay.getSize(displaySize)
        var rotatedPreviewWidth = width
        var rotatedPreviewHeight = height
        var maxPreviewWidth = displaySize.x
        var maxPreviewHeight = displaySize.y

        if (swappedDimensions) {
          rotatedPreviewWidth = height
          rotatedPreviewHeight = width
          maxPreviewWidth = displaySize.y
          maxPreviewHeight = displaySize.x
        }

        if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
          maxPreviewWidth = MAX_PREVIEW_WIDTH
        }

        if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
          maxPreviewHeight = MAX_PREVIEW_HEIGHT
        }

        previewSize = chooseOptimalSize(
                map.getOutputSizes(SurfaceTexture::class.java),
                rotatedPreviewWidth,
                rotatedPreviewHeight,
                maxPreviewWidth,
                maxPreviewHeight,
                largest
        )

        // We fit the aspect ratio of TextureView to the size of preview we picked.
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
          layoutFrame!!.setAspectRatio(previewSize!!.width, previewSize!!.height)
          textureView!!.setAspectRatio(previewSize!!.width, previewSize!!.height)
          drawView!!.setAspectRatio(previewSize!!.width, previewSize!!.height)
        } else {
          layoutFrame!!.setAspectRatio(previewSize!!.height, previewSize!!.width)
          textureView!!.setAspectRatio(previewSize!!.height, previewSize!!.width)
          drawView!!.setAspectRatio(previewSize!!.height, previewSize!!.width)
        }

        this.cameraId = cameraId
        return
      }
    } catch (e: CameraAccessException) {
      Log.e(TAG, "Failed to access Camera", e)
    } catch (e: NullPointerException) {
      // Currently an NPE is thrown when the Camera2API is used but not supported on the
      // device this code runs.
      ErrorDialog.newInstance(getString(R.string.camera_error))
              .show(childFragmentManager, FRAGMENT_DIALOG)
    }

  }

  /**
   * Opens the camera specified by [Camera2BasicFragment.cameraId].
   */
  @SuppressLint("MissingPermission")
  private fun openCamera(
          width: Int,
          height: Int
  ) {
    if (!checkedPermissions && !allPermissionsGranted()) {
      FragmentCompat.requestPermissions(this, requiredPermissions, PERMISSIONS_REQUEST_CODE)
      return
    } else {
      checkedPermissions = true
    }
    setUpCameraOutputs(width, height)
    configureTransform(width, height)
    val activity = activity
    val manager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    try {
      if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
        throw RuntimeException("Time out waiting to lock camera opening.")
      }
      manager.openCamera(cameraId!!, stateCallback, backgroundHandler)
    } catch (e: CameraAccessException) {
      Log.e(TAG, "Failed to open Camera", e)
    } catch (e: InterruptedException) {
      throw RuntimeException("Interrupted while trying to lock camera opening.", e)
    }

  }

  private fun allPermissionsGranted(): Boolean {
    for (permission in requiredPermissions) {
      if (ContextCompat.checkSelfPermission(
                      activity, permission
              ) != PackageManager.PERMISSION_GRANTED
      ) {
        return false
      }
    }
    return true
  }

  override fun onRequestPermissionsResult(
          requestCode: Int,
          permissions: Array<String>,
          grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  /**
   * Closes the current [CameraDevice].
   */
  private fun closeCamera() {
    try {
      cameraOpenCloseLock.acquire()
      if (null != captureSession) {
        captureSession!!.close()
        captureSession = null
      }
      if (null != cameraDevice) {
        cameraDevice!!.close()
        cameraDevice = null
      }
      if (null != imageReader) {
        imageReader!!.close()
        imageReader = null
      }
    } catch (e: InterruptedException) {
      throw RuntimeException("Interrupted while trying to lock camera closing.", e)
    } finally {
      cameraOpenCloseLock.release()
    }
  }

  /**
   * Starts a background thread and its [Handler].
   */
  @Synchronized
  protected fun startBackgroundThread(r: Runnable) {
    if (backgroundHandler != null) {
      backgroundHandler!!.post(r)
    }
  }

  /**
   * Stops the background thread and its [Handler].
   */
  private fun stopBackgroundThread() {
    backgroundThread!!.quitSafely()
    try {
      backgroundThread!!.join()
      backgroundThread = null
      backgroundHandler = null
      synchronized(lock) {
        runClassifier = false
      }
    } catch (e: InterruptedException) {
      Log.e(TAG, "Interrupted when stopping background thread", e)
    }

  }

  /**
   * Creates a new [CameraCaptureSession] for camera preview.
   */
  private fun createCameraPreviewSession() {
    try {
      val texture = textureView!!.surfaceTexture!!

      // We configure the size of default buffer to be the size of camera preview we want.
      texture.setDefaultBufferSize(previewSize!!.width, previewSize!!.height)

      // This is the output Surface we need to start preview.
      val surface = Surface(texture)

      // We set up a CaptureRequest.Builder with the output Surface.
      previewRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
      previewRequestBuilder!!.addTarget(surface)

      // Here, we create a CameraCaptureSession for camera preview.
      cameraDevice!!.createCaptureSession(
              Arrays.asList(surface),
              object : CameraCaptureSession.StateCallback() {

                override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                  // The camera is already closed
                  if (null == cameraDevice) {
                    return
                  }

                  // When the session is ready, we start displaying the preview.
                  captureSession = cameraCaptureSession
                  try {
                    // Auto focus should be continuous for camera preview.
                    previewRequestBuilder!!.set(
                            CaptureRequest.CONTROL_AF_MODE,
                            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                    )

                    // Finally, we start displaying the camera preview.
                    previewRequest = previewRequestBuilder!!.build()
                    captureSession!!.setRepeatingRequest(
                            previewRequest!!, captureCallback, backgroundHandler
                    )
                  } catch (e: 
                                                 
                                                 
                                                 
                                                 
                                                 
                                                 CameraAccessException) {
                    Log.e(TAG, "Failed to set up config to capture Camera", e)
                  }

                }

                override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                  showToast("Failed")
                }
              }, null
      )
    } catch (e: CameraAccessException) {
      Log.e(TAG, "Failed to preview Camera", e)
    }

  }

  /**
   * Configures the necessary [android.graphics.Matrix] transformation to `textureView`. This
   * method should be called after the camera preview size is determined in setUpCameraOutputs and
   * also the size of `textureView` is fixed.
   *
   * @param viewWidth  The width of `textureView`
   * @param viewHeight The height of `textureView`
   */
  private fun configureTransform(
          viewWidth: Int,
          viewHeight: Int
  ) {
    val activity = activity
    if (null == textureView || null == previewSize || null == activity) {
      return
    }
    val rotation = activity.windowManager.defaultDisplay.rotation
    val matrix = Matrix()
    val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
    val bufferRect = RectF(0f, 0f, previewSize!!.height.toFloat(), previewSize!!.width.toFloat())
    val centerX = viewRect.centerX()
    val centerY = viewRect.centerY()
    if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
      bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
      matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
      val scale = Math.max(
              viewHeight.toFloat() / previewSize!!.height,
              viewWidth.toFloat() / previewSize!!.width
      )
      matrix.postScale(scale, scale, centerX, centerY)
      matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
    } else if (Surface.ROTATION_180 == rotation) {
      matrix.postRotate(180f, centerX, centerY)
    }
    textureView!!.setTransform(matrix)
  }

  /**
   * Classifies a frame from the preview stream.
   */
  private fun classifyFrame() {
    if (classifier == null || activity == null || cameraDevice == null) {
      showToast("Uninitialized Classifier or invalid context.")
      return
    }
    val bitmap = textureView!!.getBitmap(classifier!!.imageSizeX, classifier!!.imageSizeY)
    val textToShow = classifier!!.classifyFrame(bitmap)
    bitmap.recycle()


    drawView!!.setDrawPoint(classifier!!.mPrintPointArray!!, 0.5f)

    showToast(textToShow)
  }

  /**
   * Compares two `Size`s based on their areas.
   */
  private class CompareSizesByArea : Comparator<Size> {

    override fun compare(
            lhs: Size,
            rhs: Size
    ): Int {
      // We cast here to ensure the multiplications won't overflow
      return java.lang.Long.signum(
              lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height
      )
    }
  }

  /**
   * Shows an error message dialog.
   */
  class ErrorDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
      val activity = activity
      return AlertDialog.Builder(activity)
              .setMessage(arguments.getString(ARG_MESSAGE))
              .setPositiveButton(
                      android.R.string.ok
              ) { dialogInterface, i -> activity.finish() }
              .create()
    }

    companion object {

      private val ARG_MESSAGE = "message"

      fun newInstance(message: String): ErrorDialog {
        val dialog = ErrorDialog()
        val args = Bundle()
        args.putString(ARG_MESSAGE, message)
        dialog.arguments = args
        return dialog
      }
    }
  }

  companion object {

    /**
     * Tag for the [Log].
     */
    private const val TAG = "TfLiteCameraDemo"

    private const val FRAGMENT_DIALOG = "dialog"

    private const val HANDLE_THREAD_NAME = "CameraBackground"

    private const val PERMISSIONS_REQUEST_CODE = 1

    /**
     * Max preview width that is guaranteed by Camera2 API
     */
    private const val MAX_PREVIEW_WIDTH = 1920

    /**
     * Max preview height that is guaranteed by Camera2 API
     */
    private const val MAX_PREVIEW_HEIGHT = 1080

    /**
     * Resizes image.
     *
     *
     * Attempting to use too large a preview size could  exceed the camera bus' bandwidth limitation,
     * resulting in gorgeous previews but the storage of garbage capture data.
     *
     *
     * Given `choices` of `Size`s supported by a camera, choose the smallest one that is
     * at least as large as the respective texture view size, and that is at most as large as the
     * respective max size, and whose aspect ratio matches with the specified value. If such size
     * doesn't exist, choose the largest one that is at most as large as the respective max size, and
     * whose aspect ratio matches with the specified value.
     *
     * @param choices           The list of sizes that the camera supports for the intended output class
     * @param textureViewWidth  The width of the texture view relative to sensor coordinate
     * @param textureViewHeight The height of the texture view relative to sensor coordinate
     * @param maxWidth          The maximum width that can be chosen
     * @param maxHeight         The maximum height that can be chosen
     * @param aspectRatio       The aspect ratio
     * @return The optimal `Size`, or an arbitrary one if none were big enough
     */
    private fun chooseOptimalSize(
            choices: Array<Size>,
            textureViewWidth: Int,
            textureViewHeight: Int,
            maxWidth: Int,
            maxHeight: Int,
            aspectRatio: Size
    ): Size {

      // Collect the supported resolutions that are at least as big as the preview Surface
      val bigEnough = ArrayList<Size>()
      // Collect the supported resolutions that are smaller than the preview Surface
      val notBigEnough = ArrayList<Size>()
      val w = aspectRatio.width
      val h = aspectRatio.height
      for (option in choices) {
        if (option.width <= maxWidth
                && option.height <= maxHeight
                && option.height == option.width * h / w
        ) {
          if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
            bigEnough.add(option)
          } else {
            notBigEnough.add(option)
          }
        }
      }

      // Pick the smallest of those big enough. If there is no one big enough, pick the
      // largest of those not big enough.
      return when {
        bigEnough.size > 0 -> Collections.min(bigEnough, CompareSizesByArea())
        notBigEnough.size > 0 -> Collections.max(notBigEnough, CompareSizesByArea())
        else -> {
          Log.e(TAG, "Couldn't find any suitable preview size")
          choices[0]
        }
      }
    }

    fun newInstance(): Camera2BasicFragment {
      return Camera2BasicFragment()
    }
  }
}
//123