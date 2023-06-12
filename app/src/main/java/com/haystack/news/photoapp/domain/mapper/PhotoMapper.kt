package com.haystack.news.photoapp.domain.mapper

import com.haystack.news.photoapp.data.dao.Photo
import com.haystack.news.photoapp.domain.model.ViewPhoto
import com.haystack.news.photoapp.utils.DateUtils
import com.haystack.news.photoapp.utils.StringUtils

class PhotoMapper  {
    fun List<Photo>.toDomain(): List<ViewPhoto>{
        return map { remotePhoto -> remotePhoto.toDomain() }
    }

    private fun Photo.toDomain(): ViewPhoto {
        return ViewPhoto(
            title = StringUtils.formatString(title, 30),
            subtitle = StringUtils.formatString(ownername, 20)+"/"+ DateUtils.formatDate(datetaken),
            url = url_h.orEmpty()
        )
    }
}