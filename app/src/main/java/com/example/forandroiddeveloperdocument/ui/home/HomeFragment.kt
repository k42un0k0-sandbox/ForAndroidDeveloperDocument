package com.example.forandroiddeveloperdocument.ui.home

import android.animation.AnimatorInflater
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forandroiddeveloperdocument.R
import java.text.Annotation

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val button = root.findViewById<Button>(R.id.home_button)
        val image: ImageView = root.findViewById(R.id.home_image)

        button.setOnClickListener {
            AnimatorInflater.loadAnimator(context, R.animator.property_animator).apply {
                setTarget(button)
                start()
            }
            val hyperspaceJump: Animation = AnimationUtils.loadAnimation(context, R.anim.hyperspace_jump)
            image.startAnimation(hyperspaceJump)
        }

        val text: String = getString(R.string.welcome_messages, "Bob", 10)
        val styledText: Spanned = Html.fromHtml(text, FROM_HTML_MODE_LEGACY)
        root.findViewById<TextView>(R.id.welcome).apply {
            setText(styledText)
        }
        root.findViewById<TextView>(R.id.styledText).apply {
// get the text as SpannedString so we can get the spans attached to the text
            val titleText = getText(R.string.title) as SpannedString

// get all the annotation spans from the text
            val annotations = titleText.getSpans(0, titleText.length, android.text.Annotation::class.java)

// create a copy of the title text as a SpannableString.
// the constructor copies both the text and the spans. so we can add and remove spans
            val spannableString = SpannableString(titleText)

// iterate through all the annotation spans
            for (annotation in annotations) {
                // look for the span with the key font
                if (annotation.key == "font") {
                    val fontName = annotation.value
                    // check the value associated to the annotation key
                    if (fontName == "title_emphasis") {
                        // create the typeface
                        val typeface = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            resources.getFont(R.font.abrifatface_regular)
                        } else {
                            null
                        }
                        // set the span at the same indices as the annotation
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                            spannableString.setSpan(TypefaceSpan(typeface!!),
                                    titleText.getSpanStart(annotation),
                                    titleText.getSpanEnd(annotation),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                    }
                }
            }
            setText(spannableString)

        }
        return root
    }
}