package com.renhuan.rhutils

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.core.app.NotificationManagerCompat
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.NotificationUtils
import com.blankj.utilcode.util.Utils
import com.example.rhutils.R
import com.example.rhutils.databinding.FragmentDemoBinding
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.renhuan.utils.Ext
import com.renhuan.utils.base.RBaseFragment
import com.renhuan.utils.setScale
import com.renhuan.utils.tintColor
import com.renhuan.utils.toast
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import me.jingbin.library.adapter.BaseByViewHolder
import me.jingbin.library.adapter.BaseRecyclerAdapter
import kotlin.random.Random

/**
 * created by renhuan
 * time : 2022/9/23 15:36
 * describe :
 */
class DemoFragment : RBaseFragment<FragmentDemoBinding>() {
    companion object {
        const val ONGOING_NOTIFICATION = 1992

        fun getInstance(): DemoFragment {
            return DemoFragment()
        }
    }

    override fun initView() {

        ClickUtils.applyPressedViewScale(binding.mask, -0.2f)


        binding.textView.setOnClickListener {
            NotificationUtils.cancelAll()
            "ddd".toast()
        }

        var mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource("http://oss.renhuan.vip/wx.wav")
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        binding.mask.setOnClickListener {
//            mAdapter.removeData(1)

            it.setScale()

            createNotification("常驻通知", "我是内容", ONGOING_NOTIFICATION, true)

            generateInviteCode()
        }
        binding.byRecyclerView.adapter = ScaleInAnimationAdapter(mAdapter).apply { setFirstOnly(false) }
        binding.imageView.setImageDrawable(binding.imageView.drawable.tintColor(Ext.getColor(R.color.red)))
        binding.imageView2.setImageDrawable(binding.imageView.drawable.tintColor(Ext.getColor(R.color.purple_200)))


//        PermissionUtils.permission(
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.READ_SMS,
//            Manifest.permission.READ_PHONE_NUMBERS,
//        ).callback(object : MediaRouter.SimpleCallback {
//            override fun onGranted() {
////                binding.button.text = PhoneUtils.getIMSI()
//                println("------1=" + PhoneUtils.getIMSI() + "  2=" + PhoneUtils.getIMEI() + " 3=" + PhoneUtils.getMEID() + " 4=" + PhoneUtils.getSerial())
//            }
//
//            override fun onDenied() {
//                TODO("Not yet implemented")
//            }
//
//        }).request()


    }


    fun generateInviteCode(): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val codeLength = 6
        var code = ""
        while (code.length < codeLength) {
            val c = chars[Random.nextInt(chars.length)]
            if (!code.contains(c)) {
                code += c
            }
        }

        binding.editTextWithClear.text?.append(code + ",")
        return code
    }

    /**
     * 创建通知栏通知
     * isOngoing  是否是常驻通知 默认false
     */
    private fun createNotification(title: String, content: String, notificationId: Int, isOngoing: Boolean = false) {
        //生成一个常驻通知
        val notification =
            NotificationUtils.getNotification(NotificationUtils.ChannelConfig.DEFAULT_CHANNEL_CONFIG) { param ->
                param.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(
                        PendingIntent.getActivity(
                            requireActivity(),
                            0,
                            Intent(requireActivity(), MainActivity::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                    //如果是常驻通知 则不设置自动点击取消
                    .setAutoCancel(!isOngoing)
            }
        if (isOngoing) {
            notification.flags = Notification.FLAG_ONGOING_EVENT
        }
        NotificationManagerCompat.from(Utils.getApp()).notify(null, notificationId, notification)
    }


    private val mAdapter by lazy {
        val expansionLayoutCollection = ExpansionLayoutCollection()

        val list = arrayListOf<Int>().apply {
            repeat(50) { add(it) }
        }

        object : BaseRecyclerAdapter<Int>(R.layout.item, list) {
            override fun bindView(holder: BaseByViewHolder<Int>, bean: Int, position: Int) {
                val expansionLayout = holder.getView<ExpansionLayout>(R.id.expansionLayout)
                expansionLayout.collapse(false)
                expansionLayoutCollection.add(expansionLayout)
            }
        }
    }
}