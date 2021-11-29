package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.ktk.it.dukashare.ActivityDetailHostActivity
import hu.ktk.it.dukashare.databinding.FragmentActivityDetailBinding
import hu.ktk.it.dukashare.model.Activity


class ActivityDetailFragment : Fragment() {

    private var selectedActivity: Activity? = null

    private lateinit var _binding: FragmentActivityDetailBinding

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {args ->
            selectedActivity = args.getParcelable(ActivityDetailHostActivity.KEY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentActivityDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.activityDetail.text = selectedActivity?.summary
    }
}