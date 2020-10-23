package roxwin.tun.baseui.customview

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView

class CommonMultiAutoCompleteTextView : AppCompatMultiAutoCompleteTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    override fun enoughToFilter(): Boolean {
//        val text: Editable = text
//        val end = selectionEnd
//        if (end < 0 || mTokenizer == null) {
//            return false
//        }
//        val start: Int = mTokenizer.findTokenStart(text, end)
        return true
    }
}