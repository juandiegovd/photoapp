package com.haystack.news.photoapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haystack.news.photoapp.R
import com.haystack.news.photoapp.data.repository.FlickrApiService
import com.haystack.news.photoapp.domain.mapper.PhotoMapper
import com.haystack.news.photoapp.domain.model.ViewPhoto
import com.haystack.news.photoapp.ui.enums.Direction

class MainActivity : FragmentActivity() {
    private val MAX_ARRAY_SIZE =6

    private lateinit var photoRV: RecyclerView
    lateinit var photoRVAdapter: PhotoRVAdapter
    private lateinit var photoList: ArrayList<ViewPhoto>
    private var photoMapper: PhotoMapper = PhotoMapper()
    lateinit var edtText: EditText
    private lateinit var btnSearch: Button
    private var nextPageSearch = 1
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoRV = findViewById(R.id.rvPhoto)
        edtText = findViewById(R.id.edtTextSearch)
        edtText.isFocusable = false
        btnSearch = findViewById(R.id.btnSearch)

        val layoutManager = GridLayoutManager(this, 3)
        photoRV.layoutManager = layoutManager


        btnSearch.setOnClickListener {
            nextPageSearch = 1
            //PENDING SEARCH PHOTOS FLICKR
            //val searchText = edtText.text.toString()
            //photoList = ArrayList()
            //performSearch(nextPageSearch, searchText)
        }

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, direction : Direction) {
                loadNextDataFromApi(page, Direction.DOWN)
            }
        }
        photoRV.addOnScrollListener(scrollListener)

        photoList = ArrayList()
        photoRVAdapter = PhotoRVAdapter(photoList, this)
        photoRV.adapter = photoRVAdapter

        loadNextDataFromApi(1, Direction.DOWN)
    }

    private fun performSearch(page: Int, searchText: String) {
        val api = FlickrApiService()
        api.searchPhotos(page, MAX_ARRAY_SIZE, searchText){ photoPage ->
            photoPage.photo.let {
                val photoValues = with(photoMapper) { it.toDomain() }
                photoList.addAll(photoValues)
                photoRVAdapter.notifyItemRangeInserted(photoList.size - photoValues.size, photoValues.size)
            }
        }
    }

    fun loadNextDataFromApi(page:Int, direction: Direction){
        val api = FlickrApiService()
        api.fetchPhotos(page, MAX_ARRAY_SIZE){ photoPage ->
            photoPage.photo.let {
                val photoValues = with(photoMapper) { it.toDomain() }
                photoList.addAll(photoValues)
                photoRVAdapter.notifyItemRangeInserted(photoList.size - photoValues.size, photoValues.size)
            }
        }
    }

    /*
    fun loadNextDataFromApi(page:Int, direction: Direction){
        val api = FlickrApiService()
        api.fetchPhotos(page, 3){ photoPage ->
            photoPage.photo.let {
                if (direction == Direction.DOWN) {
                    // Scrolling down, add new data to the end of the array
                    photoList.addAll(it)
                    photoRVAdapter.notifyItemRangeInserted(photoList.size - it.size, it.size)

                    // Remove elements from the start if array size exceeds 12
                    if (photoList.size > MAX_ARRAY_SIZE) {
                        val removeCount = photoList.size - MAX_ARRAY_SIZE
                        photoList.subList(0, removeCount).clear()
                        photoRVAdapter.notifyItemRangeRemoved(0, removeCount)
                    }

                } else {
                    // Scrolling up, add new data to the start of the array
                    photoList.addAll(0, it)
                    photoRVAdapter.notifyItemRangeInserted(0, photoList.size)

                    // Remove elements from the end if array size exceeds 12
                    if (photoList.size > MAX_ARRAY_SIZE) {
                        val removeCount = photoList.size - MAX_ARRAY_SIZE
                        val startRemoveIndex = photoList.size - removeCount
                        photoList.subList(startRemoveIndex, photoList.size).clear()
                        photoRVAdapter.notifyItemRangeRemoved(startRemoveIndex, removeCount)
                    }
                }
                Log.d("PAGE LIST: ", ""+photoList)
                Log.d("PAGE SIZE: ", ""+photoList.size)
            }
        }
    }*/
}