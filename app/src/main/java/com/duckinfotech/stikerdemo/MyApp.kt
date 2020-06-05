package com.duckinfotech.stikerdemo

import android.content.Context
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.duckinfotech.stikerdemo.database.AppDataBaseNew
import com.duckinfotech.stikerdemo.utility.ApiManagerImpl
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Method

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        // Stetho.initializeWithDefaults(this)

        Fresco.initialize(this)
        AppDataBaseNew.init(applicationContext)
        GlobalScope.launch {
            println(AppDataBaseNew.getInstance().stickerCategoryDao().getCategory())
        }
        ApiManagerImpl.init(
            applicationContext,
            AppDataBaseNew.getInstance()
        )
        // showDebugDBAddressLogToast(this)
    }

    companion object {
        private const val TAG = "Sticker Application"
    }


    private fun showDebugDBAddressLogToast(context: Context?) {
        if (BuildConfig.DEBUG) {
            try {
                val debugDB = Class.forName("com.amitshekhar.DebugDB")
                val getAddressLog: Method = debugDB.getMethod("getAddressLog")
                val value: Any = getAddressLog.invoke(null)
                Toast.makeText(context, value as String, Toast.LENGTH_LONG).show()
            } catch (ignore: Exception) {
            }
        }
    }
}
