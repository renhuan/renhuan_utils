import com.blankj.utilcode.util.*
import com.blankj.utilcode.util.CrashUtils

import java.io.File
import java.lang.reflect.Constructor
import java.text.SimpleDateFormat
import java.util.*

/**
 * CrashUtils 工具类
 * <p>
 * 修改于 Blankj 的 CrashUtils
 * 因为它的有 Bug， 但是还没修复
 */
object CrashUtils {

    private val FILE_SEP = System.getProperty("file.separator")

    private val DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler()

    /**
     * Initialization.
     */
    fun init() {
        init("")
    }

    /**
     * Initialization
     *
     * @param crashDir The directory of saving crash information.
     */
    fun init(crashDir: File) {
        init(crashDir.absolutePath) {}
    }

    /**
     * Initialization
     *
     * @param crashDirPath The directory's path of saving crash information.
     */
    fun init(crashDirPath: String) {
        init(crashDirPath) {}
    }

    /**
     * Initialization
     *
     * @param onCrash The crash listener.
     */
    fun init(onCrash: (crashInfo: CrashUtils.CrashInfo) -> Unit) {
        init("", onCrash)
    }

    /**
     * Initialization
     *
     * @param crashDir The directory of saving crash information.
     * @param onCrash The crash listener.
     */
    fun init(crashDir: File, onCrash: (crashInfo: CrashUtils.CrashInfo) -> Unit) {
        init(crashDir.absolutePath, onCrash)
    }

    /**
     * Initialization
     *
     * @param crashDirPath The directory's path of saving crash information.
     * @param onCrash The crash listener.
     */
    fun init(crashDirPath: String, onCrash: (crashInfo: CrashUtils.CrashInfo) -> Unit) {
        val dirPath = if (crashDirPath.isNullOrBlank()) {
            if (SDCardUtils.isSDCardEnableByEnvironment() && Utils.getApp().getExternalFilesDir(null) != null
            ) {
                Utils.getApp().getExternalFilesDir(null).toString() + FILE_SEP + "crash" + FILE_SEP
            } else {
                Utils.getApp().filesDir.toString() + FILE_SEP + "crash" + FILE_SEP
            }
        } else {
            if (crashDirPath.endsWith(FILE_SEP)) crashDirPath else crashDirPath + FILE_SEP
        }
        Thread.setDefaultUncaughtExceptionHandler(getUncaughtExceptionHandler(dirPath, onCrash))
    }

    private fun getUncaughtExceptionHandler(
        dirPath: String,
        onCrash: (crashInfo: CrashUtils.CrashInfo) -> Unit
    ): Thread.UncaughtExceptionHandler? {
        return Thread.UncaughtExceptionHandler { thread, throwable ->
            val time = SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(Date())
            val info = ReflectUtils.reflect(CrashUtils.CrashInfo::class.java).newInstance(time, throwable)
                .get<CrashUtils.CrashInfo>()
            val crashFile = "$dirPath$time.txt"
            FileIOUtils.writeFileFromString(crashFile, info.toString(), true)
            onCrash(info)
            DEFAULT_UNCAUGHT_EXCEPTION_HANDLER?.uncaughtException(thread, throwable)
        }
    }
}