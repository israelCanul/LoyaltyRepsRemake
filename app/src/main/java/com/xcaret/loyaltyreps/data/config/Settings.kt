package com.xcaret.loyaltyreps.data.config

import android.content.Context
import com.xcaret.loyaltyreps.R

import com.xcaret.loyaltyreps.data.entity.MenuItemHomeEntity

object Settings: BaseSharePref() {
    private const val APP_SETTINGS = "APP_SETTINGS"
    private const val DARK_THEME_ACTIVE = "DARK_THEME_ACTIVE"

    override fun getSettingName(): String = APP_SETTINGS

    fun activeDarkTheme(value: Boolean, context: Context) = setValue(DARK_THEME_ACTIVE, value, context, ValueType.BOOLEAN)
    fun isActiveDarkTheme(context: Context) = getSharedPreferences(context).getBoolean(
        DARK_THEME_ACTIVE, false)
    fun setParam(key: String, value: String, context: Context) = setValue(key, value, context)
    fun getParam(key: String, context: Context) = getSharedPreferences(context).getString(key, "") ?: ""

    //Settings variables
    const val PUNK_API_TOKEN = "PUNK_API_TOKEN"
    const val EVENTBRITETOKEN = "EventBriteToken"
    const val idUsuarioApi = "idUsuarioApi"
    const val xip = "192.168.1.5"

    const val PUNK_API_TOKEN_VALUE = "ab24d47537ac191b37b7617a98687cb5d5d42d5a"
    const val ID_USER_API_VALUE = "123"
    const val EVENTBRITETOKEN_VALUE = "XMJYAECIDIU767XLJ63U"

    var itemsHome  = arrayListOf<MenuItemHomeEntity>(
        MenuItemHomeEntity(

            icon = R.drawable.parques_menu_icon,
            tag = FragmentTags.PARKS.value
        ),
        MenuItemHomeEntity(
            icon = R.drawable.ticket_menu_icon,
            tag = FragmentTags.CORTESIAS.value
        ),
        MenuItemHomeEntity(
            icon = R.drawable.carrito_compras_menu_icon,
            tag = FragmentTags.STORE.value
        ),
        MenuItemHomeEntity(
            icon = R.drawable.camioneta_menu_icon,
            tag = FragmentTags.PICKUPS.value
        ),
        MenuItemHomeEntity(
            icon = R.drawable.training_menu_icon,
            tag = FragmentTags.Training.value
        ),
        MenuItemHomeEntity(
            icon = R.drawable.ventas_menu_icon,
            tag = FragmentTags.SELLS.value
        ),
    )
}