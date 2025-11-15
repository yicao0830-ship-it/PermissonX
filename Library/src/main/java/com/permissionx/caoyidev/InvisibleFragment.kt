package com.permissionx.caoyidev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment


typealias Permissioncallback=(Boolean,List<String>)->Unit
class InvisibleFragment : Fragment() {
    private var callback:Permissioncallback ?= null
    fun requestNow(cb:Permissioncallback,vararg permissions:String){
        callback=cb
        requestPermissions(permissions,1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val deniedList = ArrayList<String>()
        for ((index, result) in grantResults.withIndex()) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permissions[index])
            }
        }
        val allGranted = deniedList.isEmpty()
        callback?.let { it(allGranted, deniedList) }
    }
}