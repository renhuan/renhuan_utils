package com.renhuan.utils.mmkv

import android.os.Parcelable
import com.blankj.utilcode.util.GsonUtils
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * created by renhuan
 * time : 2022/2/25 09:41
 * describe : mmkv自定义管理类继承MMKVOwner
 * 例如：object MMKVRepository : MMKVOwner {}
 */
interface MMKVOwner {
    val kv: MMKV get() = com.renhuan.utils.mmkv.kv
}

val kv: MMKV = MMKV.defaultMMKV()

fun MMKVOwner.mmkvInt(default: Int = 0) =
    MMKVProperty(MMKV::decodeInt, MMKV::encode, default)

fun MMKVOwner.mmkvLong(default: Long = 0L) =
    MMKVProperty(MMKV::decodeLong, MMKV::encode, default)

fun MMKVOwner.mmkvBool(default: Boolean = false) =
    MMKVProperty(MMKV::decodeBool, MMKV::encode, default)

fun MMKVOwner.mmkvFloat(default: Float = 0f) =
    MMKVProperty(MMKV::decodeFloat, MMKV::encode, default)

fun MMKVOwner.mmkvDouble(default: Double = 0.0) =
    MMKVProperty(MMKV::decodeDouble, MMKV::encode, default)

fun MMKVOwner.mmkvString() =
    MMKVNullableProperty(MMKV::decodeString, MMKV::encode)

fun MMKVOwner.mmkvString(default: String) =
    MMKVNullablePropertyWithDefault(MMKV::decodeString, MMKV::encode, default)

fun MMKVOwner.mmkvStringSet(): ReadWriteProperty<MMKVOwner, Set<String>?> =
    MMKVNullableProperty(MMKV::decodeStringSet, MMKV::encode)

fun MMKVOwner.mmkvStringSet(default: Set<String>) =
    MMKVNullablePropertyWithDefault(MMKV::decodeStringSet, MMKV::encode, default)

fun MMKVOwner.mmkvBytes() =
    MMKVNullableProperty(MMKV::decodeBytes, MMKV::encode)

fun MMKVOwner.mmkvBytes(default: ByteArray) =
    MMKVNullablePropertyWithDefault(MMKV::decodeBytes, MMKV::encode, default)

inline fun <reified T : Parcelable> MMKVOwner.mmkvParcelable() =
    MMKVParcelableProperty(T::class.java)

inline fun <reified T : Parcelable> MMKVOwner.mmkvParcelable(default: T) =
    MMKVParcelablePropertyWithDefault(T::class.java, default)

class MMKVProperty<V>(
    private val decode: MMKV.(String, V) -> V,
    private val encode: MMKV.(String, V) -> Boolean,
    private val defaultValue: V
) : ReadWriteProperty<MMKVOwner, V> {
    override fun getValue(thisRef: MMKVOwner, property: KProperty<*>): V =
        thisRef.kv.decode(property.name, defaultValue)

    override fun setValue(thisRef: MMKVOwner, property: KProperty<*>, value: V) {
        thisRef.kv.encode(property.name, value)
    }
}

class MMKVNullableProperty<V>(
    private val decode: MMKV.(String, V?) -> V?,
    private val encode: MMKV.(String, V?) -> Boolean
) : ReadWriteProperty<MMKVOwner, V?> {
    override fun getValue(thisRef: MMKVOwner, property: KProperty<*>): V? =
        thisRef.kv.decode(property.name, null)

    override fun setValue(thisRef: MMKVOwner, property: KProperty<*>, value: V?) {
        thisRef.kv.encode(property.name, value)
    }
}

class MMKVNullablePropertyWithDefault<V>(
    private val decode: MMKV.(String, V?) -> V?,
    private val encode: MMKV.(String, V?) -> Boolean,
    private val defaultValue: V
) : ReadWriteProperty<MMKVOwner, V> {
    override fun getValue(thisRef: MMKVOwner, property: KProperty<*>): V =
        thisRef.kv.decode(property.name, null) ?: defaultValue

    override fun setValue(thisRef: MMKVOwner, property: KProperty<*>, value: V) {
        thisRef.kv.encode(property.name, value)
    }
}

class MMKVParcelableProperty<V : Parcelable>(
    private val clazz: Class<V>
) : ReadWriteProperty<MMKVOwner, V?> {
    override fun getValue(thisRef: MMKVOwner, property: KProperty<*>): V? =
        thisRef.kv.decodeParcelable(property.name, clazz)

    override fun setValue(thisRef: MMKVOwner, property: KProperty<*>, value: V?) {
        thisRef.kv.encode(property.name, value)
    }
}

class MMKVParcelablePropertyWithDefault<V : Parcelable>(
    private val clazz: Class<V>,
    private val defaultValue: V
) : ReadWriteProperty<MMKVOwner, V> {
    override fun getValue(thisRef: MMKVOwner, property: KProperty<*>): V =
        thisRef.kv.decodeParcelable(property.name, clazz) ?: defaultValue

    override fun setValue(thisRef: MMKVOwner, property: KProperty<*>, value: V) {
        thisRef.kv.encode(property.name, value)
    }
}

/**
 * 读取本地的string转list
 * 例如：
 * //存list
 *      MMKVRepository.listString = GsonUtils.toJson(list)
 *  //取list
 *      val newList = MMKVRepository.listString.toList<User>()
 */
inline fun <reified T> String.toList(): List<T> {
    return GsonUtils.fromJson(this, GsonUtils.getListType(T::class.java))
}