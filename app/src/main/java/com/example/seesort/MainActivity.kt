package com.example.seesort

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.seesort.databinding.ActivityMainBinding
import com.example.seesort.ui.components.CustomCard
import com.example.seesort.ui.components.Header
import com.example.seesort.ui.fragments.bubblesort.BubbleSortFragment
import com.example.seesort.ui.fragments.selectionsort.SelectionSortFragment
import com.example.seesort.ui.theme.SeeSortTheme
import com.example.seesort.ui.util.AlgorithmScreens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var isSortingFragmentActive = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            isSortingFragmentActive = savedInstanceState.getBoolean(IS_SORTING_FRAGMENT_ACTIVE)
            if (isSortingFragmentActive) {
                hideMainActivityView()
            }
        }
        binding.composeView.setContent {
            SeeSortTheme {
                HomeScreen()
            }
        }
    }

    fun openSortingFragment(screen: String) {
        when (screen) {
            AlgorithmScreens.BUBBLE_SORT -> openFragment(
                BubbleSortFragment.newInstance(),
                AlgorithmScreens.BUBBLE_SORT
            )

            AlgorithmScreens.SELECTION_SORT -> openFragment(
                SelectionSortFragment.newInstance(),
                AlgorithmScreens.SELECTION_SORT
            )

            else -> openFragment(BubbleSortFragment.newInstance(), AlgorithmScreens.BUBBLE_SORT)
        }
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            hideMainActivityView()
            addToBackStack(tag)
            isSortingFragmentActive = true
            replace(R.id.fragment_layout, fragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_SORTING_FRAGMENT_ACTIVE, isSortingFragmentActive)
    }

    private fun hideMainActivityView() {
        binding.composeView.visibility = View.GONE
    }

    private fun showMainActivityView() {
        binding.composeView.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            showMainActivityView()
        } else super.onBackPressed()
    }
}

@Composable
fun HomeScreen() {
    val activity: AppCompatActivity? = LocalContext.current.getActivity()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(headerText = stringResource(id = R.string.main_title), animResId = R.raw.anim_eye)

        Spacer(modifier = Modifier.height(15.dp))
        LineItems(
            R.string.bubble_sort,
            icon = R.drawable.ic_sort,
            iconDescription = R.string.sort_icon,
            onClick = {
                (activity as? MainActivity)?.openSortingFragment(AlgorithmScreens.BUBBLE_SORT)
            }
        )
        LineItems(
            R.string.selection_sort,
            icon = R.drawable.ic_sort,
            iconDescription = R.string.sort_icon,
            onClick = {
                (activity as? MainActivity)?.openSortingFragment(AlgorithmScreens.SELECTION_SORT)
            }
        )
        LineItems(
            R.string.insertion_sort,
            icon = R.drawable.ic_sort,
            iconDescription = R.string.sort_icon,
            onClick = {
                (activity as? MainActivity)?.openSortingFragment(AlgorithmScreens.INSERTION_SORT)
            }
        )
    }
}

@Composable
fun LineItems(
    title: Int,
    icon: Int,
    iconDescription: Int,
    onClick: () -> Unit
) {
    CustomCard(
        modifier = Modifier.padding(10.dp),
        text = stringResource(id = title),
        icon = icon,
        iconDescription = iconDescription,
        onClick = { onClick() }
    )
}

@PreviewLightDark
@Composable
fun mainActivityPreview() {
    SeeSortTheme {
        HomeScreen()
    }
}
