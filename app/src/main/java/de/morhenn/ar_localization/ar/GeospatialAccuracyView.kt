package de.morhenn.ar_localization.ar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.ar.core.GeospatialPose
import de.morhenn.ar_localization.R
import de.morhenn.ar_localization.databinding.ViewGeospatialAccuracyBinding

class GeospatialAccuracyView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    companion object {
        //Horizontal accuracy thresholds
        private const val H_ACC_1 = 10
        private const val H_ACC_2 = 2.5
        private const val H_ACC_3 = 1.5
        private const val H_ACC_4 = 1.0

        //Vertical accuracy thresholds
        private const val V_ACC_1 = 2.0
        private const val V_ACC_2 = 1.5
        private const val V_ACC_3 = 1.0
        private const val V_ACC_4 = 0.75

        //Heading accuracy thresholds
        private const val HEAD_ACC_1 = 20.0
        private const val HEAD_ACC_2 = 10.0
        private const val HEAD_ACC_3 = 5.0
        private const val HEAD_ACC_4 = 2.5
    }

    //viewBinding
    private var _binding: ViewGeospatialAccuracyBinding? = null
    private val binding get() = _binding!!

    var collapsed = false
        set(value) {
            field = value
            if (value) {
                binding.accuracyHeading.visibility = GONE
                binding.accuracyHorizontal.visibility = GONE
                binding.accuracyVertical.visibility = GONE
                binding.accuracyCollapsedView.visibility = VISIBLE
            } else {
                binding.accuracyHeading.visibility = VISIBLE
                binding.accuracyHorizontal.visibility = VISIBLE
                binding.accuracyVertical.visibility = VISIBLE
                binding.accuracyCollapsedView.visibility = GONE
            }
        }

    init {
        _binding = ViewGeospatialAccuracyBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        binding.viewGeospatialAccuracy.setOnClickListener {
            collapsed = !collapsed
        }
    }

    fun updateView(geoPose: GeospatialPose) {
        if (!collapsed) {

            //UI elements for horizontal geospatial accuracy
            binding.viewAccHorizontalValue.text = String.format("%.2f", geoPose.horizontalAccuracy)
            binding.viewAccHorizontal1.visibility = if (geoPose.horizontalAccuracy > H_ACC_1) INVISIBLE else VISIBLE
            binding.viewAccHorizontal2.visibility = if (geoPose.horizontalAccuracy > H_ACC_2) INVISIBLE else VISIBLE
            binding.viewAccHorizontal3.visibility = if (geoPose.horizontalAccuracy > H_ACC_3) INVISIBLE else VISIBLE
            binding.viewAccHorizontal4.visibility = if (geoPose.horizontalAccuracy > H_ACC_4) INVISIBLE else VISIBLE

            //UI elements for vertical geospatial accuracy
            val altText = String.format("%.2fm", geoPose.verticalAccuracy) + String.format(" %.2fm", geoPose.altitude)
            binding.viewAccVerticalValue.text = altText
            binding.viewAccVertical1.visibility = if (geoPose.verticalAccuracy > V_ACC_1) INVISIBLE else VISIBLE
            binding.viewAccVertical2.visibility = if (geoPose.verticalAccuracy > V_ACC_2) INVISIBLE else VISIBLE
            binding.viewAccVertical3.visibility = if (geoPose.verticalAccuracy > V_ACC_3) INVISIBLE else VISIBLE
            binding.viewAccVertical4.visibility = if (geoPose.verticalAccuracy > V_ACC_4) INVISIBLE else VISIBLE

            //UI elements for horizontal geospatial accuracy
            binding.viewAccHeadingValue.text = String.format("%.2f", geoPose.headingAccuracy)
            binding.viewAccHeading1.visibility = if (geoPose.horizontalAccuracy > HEAD_ACC_1) INVISIBLE else VISIBLE
            binding.viewAccHeading2.visibility = if (geoPose.horizontalAccuracy > HEAD_ACC_2) INVISIBLE else VISIBLE
            binding.viewAccHeading3.visibility = if (geoPose.horizontalAccuracy > HEAD_ACC_3) INVISIBLE else VISIBLE
            binding.viewAccHeading4.visibility = if (geoPose.horizontalAccuracy > HEAD_ACC_4) INVISIBLE else VISIBLE

        } else {
            with(geoPose) {
                if (horizontalAccuracy < H_ACC_4 && verticalAccuracy < V_ACC_4 && headingAccuracy < HEAD_ACC_4) {
                    binding.accuracyCollapsedView.setBackgroundColor(ContextCompat.getColor(context, R.color.acc_4))
                } else if (horizontalAccuracy < H_ACC_3 && verticalAccuracy < V_ACC_3 && headingAccuracy < HEAD_ACC_3) {
                    binding.accuracyCollapsedView.setBackgroundColor(ContextCompat.getColor(context, R.color.acc_3))
                } else if (horizontalAccuracy < H_ACC_2 && verticalAccuracy < V_ACC_2 && headingAccuracy < HEAD_ACC_2) {
                    binding.accuracyCollapsedView.setBackgroundColor(ContextCompat.getColor(context, R.color.acc_2))
                } else if (horizontalAccuracy < H_ACC_1 && verticalAccuracy < V_ACC_1 && headingAccuracy < HEAD_ACC_1) {
                    binding.accuracyCollapsedView.setBackgroundColor(ContextCompat.getColor(context, R.color.acc_1))
                } else {
                    binding.accuracyCollapsedView.setBackgroundColor(ContextCompat.getColor(context, R.color.acc_0))
                }
            }
        }
    }

}