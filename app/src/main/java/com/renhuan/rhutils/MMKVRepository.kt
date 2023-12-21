package com.android.akzixun.mmkv

import com.renhuan.utils.mmkv.MMKVOwner
import com.renhuan.utils.mmkv.mmkvBool
import com.renhuan.utils.mmkv.mmkvInt
import com.renhuan.utils.mmkv.mmkvString

/**
 * created by renhuan
 * time : 2022/2/25 09:29
 * describe :
 */
object MMKVRepository : MMKVOwner {
    var str by mmkvString("")
    var num by mmkvInt(0)
    var b by mmkvBool(false)
}