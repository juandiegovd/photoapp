package com.haystack.news.photoapp.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haystack.news.photoapp.ui.enums.Direction


abstract class EndlessRecyclerViewScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var visibleItemCount = 0
    private var firstVisibleItem = 0
    private var totalItemCount = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private var visibleThreshold = 9
    private var startingPageIndex = 1

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, direction: Direction)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = layoutManager.childCount;
        totalItemCount = layoutManager.itemCount;
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached, load more data
            // Call your method to load more data here
            startingPageIndex+=1
            onLoadMore(startingPageIndex, totalItemCount, Direction.DOWN)
            loading = true;
        }
    }

}
