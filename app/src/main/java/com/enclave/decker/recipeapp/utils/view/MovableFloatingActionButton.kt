package com.enclave.decker.recipeapp.utils.view

import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.MotionEvent
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.View.OnTouchListener
import kotlin.math.max
import kotlin.math.min

class MovableFloatingActionButton : FloatingActionButton, OnTouchListener {

    private var downRawX: Float = 0.toFloat()
    private var downRawY: Float = 0.toFloat()
    private var dX: Float = 0.toFloat()
    private var dY: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {

                downRawX = motionEvent.rawX
                downRawY = motionEvent.rawY
                dX = view.x - downRawX
                dY = view.y - downRawY

                return true // Consumed

            }
            MotionEvent.ACTION_MOVE -> {

                val viewWidth = view.width
                val viewHeight = view.height

                val viewParent = view.parent as View
                val parentWidth = viewParent.width
                val parentHeight = viewParent.height

                var newX = motionEvent.rawX + dX
                newX = max(
                    layoutParams.leftMargin.toFloat(),
                    newX
                )
                newX = min(
                    (parentWidth - viewWidth - layoutParams.rightMargin).toFloat(),
                    newX
                )

                var newY = motionEvent.rawY + dY
                newY = max(
                    layoutParams.topMargin.toFloat(),
                    newY
                )
                newY = min(
                    (parentHeight - viewHeight - layoutParams.bottomMargin).toFloat(),
                    newY
                )

                view.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start()

                return true

            }
            MotionEvent.ACTION_UP -> {

                val upRawX = motionEvent.rawX
                val upRawY = motionEvent.rawY

                val upDX = upRawX - downRawX
                val upDY = upRawY - downRawY

                return if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                    performClick()
                } else {
                    true
                }

            }
            else -> return super.onTouchEvent(motionEvent)
        }
    }


    companion object {
        private const val CLICK_DRAG_TOLERANCE = 10f
    }

}