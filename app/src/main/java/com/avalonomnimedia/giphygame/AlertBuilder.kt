package com.avalonomnimedia.giphygame

import android.app.AlertDialog
import android.content.Context

fun getSpacesAlert(context: Context): AlertDialog.Builder? = AlertDialog.Builder(context)
    .setTitle(R.string.search_dialog_title)
    .setMessage(R.string.search_dialog_message)
    .setPositiveButton(android.R.string.ok, null)
    .setIcon(android.R.drawable.ic_dialog_alert)