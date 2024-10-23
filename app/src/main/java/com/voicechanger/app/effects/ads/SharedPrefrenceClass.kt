import android.content.Context
import android.content.SharedPreferences
//
//class SharedPreferencesWrapper(context: Context) {
//    private val sharedPreferences: SharedPreferences
//    private val editor: SharedPreferences.Editor
//
//    init {
//        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        editor = sharedPreferences.edit()
//    }
//
//    // Save a string value
//    fun putString(key: String?, value: String?) {
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    // Retrieve a string value
//    fun getString(key: String?, defaultValue: String?): String? {
//        return sharedPreferences.getString(key, defaultValue)
//    }
//
//    // Save an int value
//    fun putInt(key: String?, value: Int) {
//        editor.putInt(key, value)
//        editor.apply()
//    }
//
//    // Retrieve an int value
//    fun getInt(key: String?, defaultValue: Int): Int {
//        return sharedPreferences.getInt(key, defaultValue)
//    }
//
//    // Save a boolean value
//    fun putBoolean(key: String?, value: Boolean) {
//        editor.putBoolean(key, value)
//        editor.apply()
//    }
//
//    // Retrieve a boolean value
//    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
//        return sharedPreferences.getBoolean(key, defaultValue)
//    }
//
//    // Save a float value
//    fun putFloat(key: String?, value: Float) {
//        editor.putFloat(key, value)
//        editor.apply()
//    }
//
//    // Retrieve a float value
//    fun getFloat(key: String?, defaultValue: Float): Float {
//        return sharedPreferences.getFloat(key, defaultValue)
//    }
//
//    // Save a long value
//    fun putLong(key: String?, value: Long) {
//        editor.putLong(key, value)
//        editor.apply()
//    }
//
//    // Retrieve a long value
//    fun getLong(key: String?, defaultValue: Long): Long {
//        return sharedPreferences.getLong(key, defaultValue)
//    }
//
//    // Remove a value
//    fun remove(key: String?) {
//        editor.remove(key)
//        editor.apply()
//    }
//
//    // Clear all values
//    fun clear() {
//        editor.clear()
//        editor.apply()
//    }
//
//    companion object {
//        private const val PREFS_NAME = "changer"
//    }
//}
