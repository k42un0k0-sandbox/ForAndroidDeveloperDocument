package com.example.forandroiddeveloperdocument.ui.home

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forandroiddeveloperdocument.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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
        return root
    }

    fun noop(){
        val icons: TypedArray = resources.obtainTypedArray(R.array.icons)
        val drawable: Drawable? = icons.getDrawable(0)

        icons.recycle()
        val colors: TypedArray = resources.obtainTypedArray(R.array.colors)
        val color: Int = colors.getColor(0,0)
        colors.recycle()
    }
}