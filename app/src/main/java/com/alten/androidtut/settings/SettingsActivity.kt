package com.alten.androidtut.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alten.androidtut.R
import com.alten.androidtut.databinding.ActivitySettingsBinding
import com.alten.androidtut.firstApp.FirstAppActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {


  private lateinit var binding: ActivitySettingsBinding
  private var firstTime: Boolean=true

  companion object {
    const val VOLUMEN_LVL = "volumen_lvl"
    const val KEY_BLUETOOTH = "key_bluetooth"
    const val KEY_VIBRATION = "key_vibration"
    const val KEY_DARK_MODE = "key_dark_mode"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    binding = ActivitySettingsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    CoroutineScope(Dispatchers.IO).launch {
   // getSettings().collect{settingsModel ->
    getSettings().filter { firstTime}.collect{settingsModel ->
      if(settingsModel!=null){
        runOnUiThread {
          Log.i(FirstAppActivity.TAG, "inicializar valores : $settingsModel")
          this@SettingsActivity.binding.rsVolume.setValues(settingsModel.volume.toFloat())
          this@SettingsActivity.binding.switchBluetooth.isChecked = settingsModel.bluetooth
          this@SettingsActivity.binding.switchVibration.isChecked = settingsModel.vibration
          this@SettingsActivity.binding.switchDarkMode.isChecked = settingsModel.darkMode
          firstTime=!firstTime
        }
      }
    }
    }
    initUI()
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }

  private suspend fun saveVolume(value: Int) {
    dataStore.edit { preferences ->
      preferences[intPreferencesKey(VOLUMEN_LVL)] = value
    }
  }

  //guardar los switch
  private suspend fun saveOptions(key: String, value: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey(key)] = value
    }
  }

  private fun getSettings(): Flow<SettingsModel> {
    return dataStore.data.map { preferences ->
      SettingsModel(
        volume = preferences[intPreferencesKey(VOLUMEN_LVL)] ?: 50,
        bluetooth = preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
        vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: false,
        darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: true
      )
    }
  }

  private fun initUI() {
    //captura del valor de volumen de la UI
    binding.rsVolume.addOnChangeListener { _, value, _ ->
      Log.i(FirstAppActivity.TAG, "el valor es $value")
      //guardo el valor en el datastore pero en una corutina
      CoroutineScope(Dispatchers.IO).launch {
        saveVolume(value.toInt())
      }
    }
    //capture del valor de los switch de la UI
    binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
      if (value) {
        enableDarkMode()
      } else {
        disableDarkMode()
      }
      CoroutineScope(Dispatchers.IO).launch {
        saveOptions(KEY_DARK_MODE, value)
      }
    }

    binding.switchVibration.setOnCheckedChangeListener { _, value ->
      CoroutineScope(Dispatchers.IO).launch {
        saveOptions(KEY_VIBRATION, value)
      }
    }

    binding.switchBluetooth.setOnCheckedChangeListener { _, value ->
      CoroutineScope(Dispatchers.IO).launch {
        saveOptions(KEY_BLUETOOTH, value)
      }
    }
  }

  private fun enableDarkMode(){
    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
    delegate.applyDayNight()
  }

  private fun disableDarkMode(){
    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
    delegate.applyDayNight()

  }



}