package com.coderdr.data.repo

import com.coderdr.domain.repo.MainRepo
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImplementMainRepo @Inject constructor(
    private val scanner: GmsBarcodeScanner
): MainRepo {
    override fun startScanner(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {  barcode ->
                    launch {
                        send(getInfo(barcode))
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }

            awaitClose {  }
        }
    }

    private fun getInfo(barcode: Barcode): String {
        return when(barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi!!.ssid
                val pass = barcode.wifi!!.password
                val type = barcode.wifi!!.encryptionType
                "SSID: $ssid \n Password: $pass \n EncType: $type"
            }
            Barcode.TYPE_CONTACT_INFO -> {
                val info = barcode.contactInfo
                "Contact Details: $info"
            }
            Barcode.TYPE_URL -> {
                val info = barcode.url!!.url
                "URL: $info"
            }
            Barcode.TYPE_PRODUCT -> {
                val info = barcode.displayValue
                "Product ID: $info"
            }
            Barcode.TYPE_EMAIL -> {
                val info = barcode.email
                "Email ID: $info"
            }
            Barcode.TYPE_PHONE -> {
                val info = barcode.contactInfo
                "Phone: $info"
            }
            Barcode.TYPE_CALENDAR_EVENT -> {
                val info = barcode.calendarEvent
                "Calender Event: $info"
            }
            Barcode.TYPE_GEO -> {
                val info = barcode.geoPoint
                "Geo-Point: $info"
            }
            Barcode.TYPE_DRIVER_LICENSE -> {
                val info = barcode.driverLicense
                "Driver's License No.: $info"
            }
            Barcode.TYPE_ISBN -> {
                val info = barcode.displayValue
                "ISBN: $info"
            }
            Barcode.TYPE_SMS -> {
                val info = barcode.sms
                "SMS: $info"
            }
            Barcode.TYPE_TEXT -> {
                val info = barcode.rawValue
                "Text: $info"
            }
            Barcode.TYPE_UNKNOWN -> {
                val info = barcode.rawValue
                "Unknown: $info"
            }
            else -> {
                barcode.rawValue?:"Unable to detect"
            }
        }
    }

}