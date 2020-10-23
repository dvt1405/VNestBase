package roxwin.tun.baseui.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import roxwin.tun.baseui.R

fun Fragment.showSuccessDialog(
    onSuccessListener: (() -> Unit?)? = null,
    content: String? = null,
    delayMillis: Int? = 1900
) {
    val successAlert = SweetAlertDialog(requireActivity(), SweetAlertDialog.SUCCESS_TYPE)
        .showCancelButton(false)
        .hideConfirmButton()
    successAlert.showContentText(content != null)
    successAlert.setCancelable(false)
    successAlert.contentText = content
    successAlert.titleText = null
    successAlert.confirmText = null
    successAlert.setBackground(ColorDrawable(Color.TRANSPARENT))
    successAlert.show()
    Handler().postDelayed({ successAlert.dismissWithAnimation() }, 1500)
    Handler().postDelayed({ onSuccessListener?.let { it() } }, (delayMillis ?: 1900).toLong())
}

fun Fragment.showErrorDialog(
    onSuccessListener: () -> Unit?,
    content: String? = null,
    delayMillis: Int? = 1900
) {
    val successAlert = SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE)
        .showCancelButton(false)
        .hideConfirmButton()
    successAlert.showContentText(content != null)
    successAlert.setCancelable(true)
    successAlert.titleText = null
    successAlert.confirmText = null
    successAlert.setBackground(ColorDrawable(Color.TRANSPARENT))
    successAlert.contentText = content
    successAlert.show()
//    Handler().postDelayed({ successAlert.dismissWithAnimation() }, 1500)
//    Handler().postDelayed({ onSuccessListener() }, (delayMillis ?: 1900).toLong())
}
