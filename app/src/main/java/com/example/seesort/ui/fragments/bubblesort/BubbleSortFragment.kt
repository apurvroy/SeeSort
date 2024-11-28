package com.example.seesort.ui.fragments.bubblesort

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.example.seesort.ui.theme.SeeSortTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.seesort.R
import com.example.seesort.getActivity
import com.example.seesort.ui.components.BottomButtons
import com.example.seesort.ui.components.Header
import com.example.seesort.ui.theme.sortedGreen

class BubbleSortFragment : Fragment() {

    companion object {
        fun newInstance() = BubbleSortFragment()
    }

    private val viewModel: BubbleSortViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SeeSortTheme {
                    BubbleSortScreen()
                }
            }
        }
    }
}

@Composable
fun BubbleSortScreen(
    sortViewModel: BubbleSortViewModel = hiltViewModel()
) {
    val listToSort: BubbleSortList by sortViewModel.listToSort.collectAsState()
    val isSorting: Boolean by sortViewModel.isSorting.collectAsState()
    val isInLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val activity = LocalContext.current.getActivity()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        ) {
        Spacer(modifier = Modifier.height(10.dp))
        Header(
            headerText = stringResource(id = R.string.bubble_sort),
            animResId = R.raw.anim_eye,
            isCancelAvailable = true,
            onCancelCLicked = { activity?.onBackPressed() }
        )
        Spacer(modifier = Modifier.height(10.dp))
        SortingList(
            modifier = Modifier.then(
                if (isInLandscape) {
                    Modifier.defaultMinSize(minHeight = 200.dp)
                } else {
                    Modifier.weight(1f)
                }
            ),
            listToSort = listToSort.list
        )
        Spacer(modifier = Modifier.height(10.dp))
        BottomButtons(
            startSorting = sortViewModel::startSorting,
            randomList = sortViewModel::randomizeCurrentList,
            sliderChange = sortViewModel::randomizeCurrentList,
            speedSliderChange = sortViewModel::speedSliderChange,
            listSizeInit = listToSort.list.size,
            isSorting = isSorting
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SortingList(
    listToSort: List<BubbleSortItem>,
    modifier: Modifier
) {
    val localDensity = LocalDensity.current
    var heightIs by remember {
        mutableStateOf(0.dp)
    }
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                heightIs = with(localDensity) { coordinates.size.height.toDp() }
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        items(
            listToSort,
            key = {
                it.id
            }
        ) {
            BubbleSortItem(
                item = it,
                modifier = Modifier.animateItemPlacement(
                    tween(durationMillis = 200)
                ),
                totalHeight = heightIs
            )
        }
    }
}

@Composable
private fun BubbleSortItem(
    item: BubbleSortItem,
    modifier: Modifier = Modifier,
    totalHeight: Dp
) {
    val borderStroke = if (item.isComparing) {
        BorderStroke(width = 3.dp, Color.Red)
    } else {
        BorderStroke(width = 0.dp, Color.Transparent)
    }
    val itemColor = if(item.isSorted) sortedGreen else MaterialTheme.colorScheme.primaryContainer
    val itemHeight = (item.value * totalHeight.value / 100)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(itemHeight.dp)
                .width(30.dp)
                .background(itemColor, RoundedCornerShape(5.dp))
                .border(borderStroke, RoundedCornerShape(5.dp))
        )
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${item.value}",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview
@Composable
fun BubbleSortScreenPreview() {
    MaterialTheme {
        BubbleSortScreen()
    }
}