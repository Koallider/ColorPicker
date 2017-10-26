package ru.ryakovlev.colorpicker

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.wrapContent


/**
 * Created by roma on 21.10.2017.
 */
open class ColorPicker : FrameLayout {

    enum class LayoutType {
        HORIZONTAL, VERTICAL, GRID;
    }

    private val defaultColors = listOf(Color.parseColor("#EF9A9A"), Color.parseColor("#F48FB1"), Color.parseColor("#CE93D8"), Color.parseColor("#B39DDB"), Color.parseColor("#9FA8DA"), Color.parseColor("#90CAF9"), Color.parseColor("#81D4FA"), Color.parseColor("#A5D6A7"), Color.parseColor("#C5E1A5"), Color.parseColor("#FFF59D"), Color.parseColor("#FFCC80"), Color.parseColor("#FFAB91"));
    private var colorPickedListener: ((Int) -> Unit)? = null
    private val colorList = ArrayList<Int>()
    protected var recyclerView: RecyclerView? = null

    var colors: List<Int>
        set(value) {
            colorList.clear()
            colorList.addAll(value)
            recyclerView?.adapter?.notifyDataSetChanged()
        }
        get() = colorList

    constructor(context: Context, layoutType: LayoutType = LayoutType.HORIZONTAL, colors: List<Int>) : super(context) {
        colorList.addAll(colors)
        initView(layoutType)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SimpleColorPicker, 0, 0);

        val layoutType: LayoutType

        try {
            layoutType = LayoutType.values()[a.getInteger(R.styleable.SimpleColorPicker_layoutType, 0)]
            val id = a.getResourceId(R.styleable.SimpleColorPicker_colors, 0);
            if (id != 0) {
                colorList.addAll(getResources().getIntArray(id).asList())
            } else {
                colorList.addAll(defaultColors)
            }
        } finally {
            a.recycle();
        }
        initView(layoutType)
    }

    open fun initView(layoutType: LayoutType) {
        recyclerView = recyclerView {
            adapter = Adapter(colors = colorList, clickListener = { colorPickedListener?.invoke(colorList.get(it)) })
            layoutManager = when (layoutType) {
                ColorPicker.LayoutType.HORIZONTAL -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                ColorPicker.LayoutType.VERTICAL -> LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                ColorPicker.LayoutType.GRID -> GridLayoutManager(context, calculateNoOfColumns(context), LinearLayoutManager.VERTICAL, false)
            }
            layoutParams = RecyclerView.LayoutParams(matchParent, matchParent)
        }
    }

    fun onColorSelected(listener: ((Int) -> Unit)) {
        colorPickedListener = listener
    }

    class Holder(view: View, clickListener: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val icon: FrameLayout
        val button: FrameLayout

        init {
            icon = view.find(R.id.icon)
            button = view.find(R.id.button)
            button.onClick {
                clickListener(adapterPosition)
            }
        }
    }

    class Adapter(val colors: List<Int> = ArrayList<Int>(), val clickListener: (Int) -> Unit) : RecyclerView.Adapter<Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder? {
            val inflater = LayoutInflater.from(parent.context)
            val contactView = inflater.inflate(R.layout.color_item, parent, false)
            return Holder(contactView, clickListener)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val mDrawable = holder.icon.backgroundDrawable
            mDrawable?.setColorFilter(PorterDuffColorFilter(colors.get(position), PorterDuff.Mode.SRC_IN));
        }

        override fun getItemCount(): Int {
            return colors.size
        }
    }

    private fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 50).toInt()
    }
}

