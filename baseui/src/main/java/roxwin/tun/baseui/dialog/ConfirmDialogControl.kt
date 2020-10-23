package roxwin.tun.baseui.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import roxwin.tun.baseui.R
import roxwin.tun.baseui.utils.DialogUtils

class ConfirmDialogControl(
    @NonNull private val context: Context,
    private var _title: String?,
    private var _messages: String?,
    private var positiveButton: String?,
    private var negativeButton: String?,
    private var _listener: DialogUtils.ConfirmDialogListener?
) : Cloneable {
    var listener: DialogUtils.ConfirmDialogListener?
        get() = _listener
        set(value) {
            _listener = value
        }
    var messages: String?
        get() = _messages
        set(value) {
            _messages = value
            alertDialog.setMessage(_messages)
        }
    var title: String?
        get() = _title
        set(value) {
            _title = value
            alertDialog.setTitle(_title)
        }
    val alertDialog: AlertDialog =
        MaterialAlertDialogBuilder(
            context,
            R.style.Material_Alert_ThemeDefault
        ).setCancelable(true)
            .create()

    init {
        alertDialog.setTitle(_title)
        alertDialog.setMessage(_messages)

        listener?.let {
            setPositiveButton(positiveButton) {
                it.allowClick()
            }
            setNegativeButton(negativeButton) {
                it.cancelClick()
            }
        }
    }

    constructor(
        @NonNull context: Context,
        title: String?,
        messages: String?,
        confirmButtonMessage: String?
    ) : this(
        context,
        title,
        messages,
        confirmButtonMessage,
        null,
        null
    ) {
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, confirmButtonMessage) { _, _ ->
            dismiss()
        }
    }

    fun show() {
        alertDialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        alertDialog.show()
    }

    fun show(title: String? = null, messages: String? = null) {
        alertDialog.setMessage(messages)
        title?.let {
            this.title = it
        }
        show()
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    fun setPositiveButton(messages: String?, listener: () -> Unit?) =
        setButton(AlertDialog.BUTTON_POSITIVE, messages, listener)

    fun setDefaultSingleButton() {
        setPositiveButton(BUTTON_POSITIVE_DEFAULT) {
            alertDialog.dismiss()
        }
        setNegativeButton(null)
    }

    fun setDefaultDoubleButton(listener: () -> Unit?) {
        setPositiveButton(BUTTON_POSITIVE_DEFAULT, listener)
        setNegativeButton(BUTTON_NEGATIVE_DEFAULT) { alertDialog.dismiss() }
    }

    fun setDefaultDoubleButton(allowListener: () -> Unit?, cancelListener: () -> Unit?) {
        setPositiveButton(BUTTON_POSITIVE_DEFAULT, allowListener)
        setNegativeButton(BUTTON_NEGATIVE_DEFAULT, cancelListener)
    }

    fun setPositiveButton(messages: String?) = setButton(AlertDialog.BUTTON_POSITIVE, messages) {
        listener?.allowClick()
    }

    fun setNegativeButton(title: String?, listener: () -> Unit?) =
        setButton(AlertDialog.BUTTON_NEGATIVE, title, listener)

    fun setNegativeButton(title: String?) =
        setButton(AlertDialog.BUTTON_NEGATIVE, title) { listener?.cancelClick() }

    fun setOnDismissListener(listener: () -> Unit?) {
        alertDialog.setOnDismissListener {
            listener()
        }
    }

    private fun setButton(where: Int, messages: String?, listener: () -> Unit?) = alertDialog.setButton(
        where,
        messages
    ) { _: DialogInterface?, _: Int -> listener() }

    class Builder(@NonNull private val context: Context) {
        private var title: String? = null
        private var messages: String? = null
        private var positiveButton: String? = null
        private var negativeButton: String? = null
        private var onAllowClick: (() -> Unit)? = null
        private var onCancelClick: (() -> Unit)? = null
        private var confirmDialogControl: ConfirmDialogControl? = null
        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        fun message(messages: String?): Builder {
            this.messages = messages
            return this
        }

        fun setPositiveButton(btnTitle: String?, onClick: () -> Unit): Builder {
            positiveButton = btnTitle
            this.onAllowClick = onClick
            return this
        }

        fun setPositiveButton(btnTitle: String?): Builder {
            positiveButton = btnTitle
            return this
        }

        fun setNegativeButton(btnTitle: String?, onClick: () -> Unit): Builder {
            negativeButton = btnTitle
            this.onCancelClick = onClick
            return this
        }

        fun setNegativeButton(btnTitle: String?): Builder {
            negativeButton = btnTitle
            return this
        }

        fun build(): ConfirmDialogControl {
            confirmDialogControl = ConfirmDialogControl(
                context,
                title,
                messages,
                positiveButton,
                negativeButton,
                object : DialogUtils.ConfirmDialogListener {
                    override fun allowClick() {
                        if (onAllowClick != null) {
                            onAllowClick?.let { it() }
                        } else {
                            confirmDialogControl?.dismiss()
                        }
                    }

                    override fun cancelClick() {
                        if (onCancelClick != null) {
                            onCancelClick?.let { it() }
                        } else {
                            confirmDialogControl?.dismiss()
                        }
                    }

                })
            return confirmDialogControl!!
        }

        fun show(): ConfirmDialogControl {
            val alert = build()
            alert.show()
            return alert
        }
    }

    companion object {
        const val BUTTON_POSITIVE_DEFAULT = "OK"
        const val BUTTON_NEGATIVE_DEFAULT = "Cancel"
        const val WRONG_TITLE = "Some things went wrong"
        const val CONFIRM_TITLE = "Confirm"
    }
}