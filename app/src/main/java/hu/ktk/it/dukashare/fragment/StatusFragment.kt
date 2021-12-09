package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.FragmentStatusBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.model.ActivityType
import hu.ktk.it.dukashare.service.ActivityService
import hu.ktk.it.dukashare.service.ActivityTypeService

class StatusFragment : Fragment() {
    private var _binding: FragmentStatusBinding? = null
    private var activityTypes: List<ActivityType?>? = emptyList()
    private var activities: List<Activity?>? = emptyList()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentStatusBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivityTypes()
        getActivities()
    }

    private fun getActivityTypes() {
        ActivityTypeService().getActivityTypes {
            if (it != null) activityTypes = it
        }
    }

    private fun getActivities() {
        ActivityService().getActivities {
            if (it != null) {
                activities = it
                loadChart()
            }
        }
    }


    private fun loadChart() {
        val activitiesPerTypes = IntArray(activityTypes?.size!!)
        for (act in activities!!) {
            activitiesPerTypes[activityTypes?.indexOf(act?.activityType)!!] += 1
        }
        val series = Array(activityTypes?.size!!) { i ->

            AASeriesElement().name(activityTypes!![i]?.name).data(arrayOf(activitiesPerTypes[i]))
        }

        val aaPieChart: AAChartModel = AAChartModel()
            .chartType(AAChartType.Bar)
            .title("Activity Types")
            .dataLabelsEnabled(true)
            .backgroundColor(R.color.primaryColor)
            .series(series)
        binding.aaChartView.aa_drawChartWithChartModel(aaPieChart)
    }


}