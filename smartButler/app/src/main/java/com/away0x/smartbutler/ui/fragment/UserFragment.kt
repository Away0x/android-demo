package com.away0x.smartbutler.ui.fragment


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.away0x.smartbutler.R
import com.away0x.smartbutler.entity.MyUser
import com.away0x.smartbutler.ui.activity.CourierActivity
import com.away0x.smartbutler.ui.activity.LoginActivity
import com.away0x.smartbutler.ui.activity.PhoneActivity
import com.away0x.smartbutler.ui.view.CustomDialog
import com.away0x.smartbutler.utils.UITools
import kotlinx.android.synthetic.main.fragment_user.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.File

/**
 * 个人中心
 */
class UserFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mDialog: CustomDialog
    private var mTempFile: File? = null

    companion object {
        private const val PHOTO_IMAGE_FILE_NAME = "fileImg.jpg"
        private const val CAMERA_REQUEST_CODE = 100
        private const val IMAGE_REQUEST_CODE = 101
        private const val RESULT_REQUEST_CODE = 102
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_CANCELED) return

        when (requestCode) {
            // 相册数据
            IMAGE_REQUEST_CODE -> {
                if (data?.data != null) startPhotoZoom(data.data!!)
            }
            // 相机数据
            CAMERA_REQUEST_CODE -> {
                mTempFile = File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)
                startPhotoZoom(Uri.fromFile(mTempFile))
            }

            RESULT_REQUEST_CODE -> {
                // 有可能点击舍弃
                if (data == null) return
                setImageToView(data)
                mTempFile?.delete()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 保存
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            UITools.putImageToShare(activity!!, mProfileIV)
        }
    }

    fun initView() {
        mLogoutBtn.setOnClickListener(this)
        mUpdateBtn.setOnClickListener(this)
        mCourierTV.setOnClickListener(this)
        mPhoneTV.setOnClickListener(this)
        mEditUserTV.setOnClickListener(this)
        mProfileIV.setOnClickListener(this)

        mDialog = CustomDialog(activity!!, R.layout.dialog_photo, R.style.pop_anim_style)
        mDialog.setCancelable(false) // 提示框以外点击无效
        mDialog.run {
            findViewById<Button>(R.id.btn_camera).setOnClickListener(this@UserFragment)
            findViewById<Button>(R.id.btn_picture).setOnClickListener(this@UserFragment)
            findViewById<Button>(R.id.btn_cancel).setOnClickListener(this@UserFragment)
        }

        // 默认是不可点击的/不可输入
        setEnabled(false)

        val user = BmobUser.getCurrentUser(MyUser::class.java)

        if (user != null) {
            setUser(user)
        }

        // 设置图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            UITools.getImageToShare(activity!!, mProfileIV)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            // 登出
            R.id.mLogoutBtn -> {
                BmobUser.logOut()
                activity?.startActivity<LoginActivity>()
                activity?.finish()
            }
            // 修改确定
            R.id.mUpdateBtn -> {
                val name = mUserNameET.text.toString().trim()
                val age = mAgeET.text.toString().trim()
                val sex = mSexET.text.toString().trim()
                val desc = mDescET.text.toString().trim()

                if (name.isEmpty() || age.isEmpty() || sex.isEmpty()) {
                    activity?.toast(R.string.text_tost_empty)
                    return
                }

                val user = MyUser()
                user.username = name
                user.age = age.toInt()
                user.sex = sex == getString(R.string.text_boy)
                user.desc = if (!desc.isNullOrEmpty()) desc else getString(R.string.text_nothing)

                user.update(BmobUser.getCurrentUser(MyUser::class.java).objectId, object : UpdateListener() {
                    override fun done(e: BmobException?) {
                        if (e == null) {
                            setEnabled(false)
                            setUser(user)
                            mUpdateBtn.visibility = View.GONE
                            activity?.toast(R.string.text_editor_success)
                        } else {
                            activity?.toast(R.string.text_editor_failure)
                        }
                    }
                })
            }
            // 编辑资料
            R.id.mEditUserTV -> {
                setEnabled(true)
                mUpdateBtn.visibility = View.VISIBLE
            }
            // 物流查询
            R.id.mCourierTV -> {
                activity?.startActivity<CourierActivity>()
            }
            // 归属地查询
            R.id.mPhoneTV -> {
                activity?.startActivity<PhoneActivity>()
            }
            // 头像
            R.id.mProfileIV -> {
                mDialog.show()
            }
            // 弹窗按钮: 取消
            R.id.btn_cancel -> {
                mDialog.dismiss()
            }
            // 弹窗按钮: 相机
            R.id.btn_camera -> {
                toCamera()
            }
            // 弹窗按钮: 相册
            R.id.btn_picture -> {
                toPicture()
            }
        }
    }

    // 控制焦点
    private fun setEnabled(status: Boolean) {
        mUserNameET.isEnabled = status
        mSexET.isEnabled = status
        mAgeET.isEnabled = status
        mDescET.isEnabled = status
    }

    private fun setUser(u: MyUser) {
        mUserNameET.setText(u.username)
        mAgeET.setText(u.age.toString())
        mSexET.setText(if (u.sex == true) getString(R.string.text_boy) else getString(R.string.text_girl))
        mDescET.setText(u.desc)
    }

    // 打开相机
    private fun toCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            Uri.fromFile(File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)))

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
        mDialog.dismiss()
    }

    // 打开相册
    private fun toPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
        mDialog.dismiss()
    }

    // 裁切图片
    private fun startPhotoZoom(uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        //设置裁剪
        intent.putExtra("crop", "true")
        //裁剪宽高比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        //裁剪图片的质量
        intent.putExtra("outputX", 320)
        intent.putExtra("outputY", 320)
        //发送数据
        intent.putExtra("return-data", true)

        startActivityForResult(intent, RESULT_REQUEST_CODE)
    }

    // 设置图片
    private fun setImageToView(data: Intent) {
        data.extras?.run {
            mProfileIV.setImageBitmap(getParcelable("data"))
        }
    }
}
