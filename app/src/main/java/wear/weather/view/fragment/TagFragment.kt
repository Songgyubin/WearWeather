package wear.weather.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import wear.weather.R
import wear.weather.handler.TagCallbackHandler

class TagFragment : Fragment(), OnTouchListener {
    var closeImageView: ImageView? = null
    var mainImageView: ImageView? = null
    var editText: EditText? = null
    var position: FrameLayout.LayoutParams? = null
    var handler: TagCallbackHandler? = null
    var percentWidth = 0.0
    var percentHeight = 0.0
    var select = false
    var data: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tag, container, false)
        val tagTextView: TextView
        if (position != null) {
            v.layoutParams = position
        }
        val self: TagFragment = this
        mainImageView = v.findViewById<View>(R.id.main_image_view) as ImageView
        mainImageView!!.setOnTouchListener(this)


        editText = v.findViewById<View>(R.id.tag_edit_text) as EditText
        tagTextView = v.findViewById<View>(R.id.tag_text_view) as TextView
        if (data != null) tagTextView!!.text = data.toString()
        closeImageView = v.findViewById<View>(R.id.close_image_view) as ImageView
        closeImageView!!.setOnClickListener { handler!!.onTagEvent(self, "CloseClicked", null) }
        setSelected(true)
        handler!!.onTagCreated(this)
        return v
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handler!!.onTagEvent(this, "ClickDown", null)
                return false
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> return false
            MotionEvent.ACTION_MOVE -> {
                handler!!.onTagEvent(this, "ClickMove", event)
                return false
            }
            MotionEvent.ACTION_UP -> {
                handler!!.onTagEvent(this, "ClickUp", null)
                return false
            }
        }
        return false
    }

    fun insertText(){

    }

    fun setSelected(selected: Boolean) {
        select = selected
        if (select) {
            closeImageView!!.visibility = View.VISIBLE
        } else {
            closeImageView!!.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(
            handler: TagCallbackHandler?,
            data: Any?
        ): TagFragment {
            val tagFragment: TagFragment = TagFragment()
            tagFragment.handler = handler
            tagFragment.data = data
            return tagFragment
        }
    }
}
