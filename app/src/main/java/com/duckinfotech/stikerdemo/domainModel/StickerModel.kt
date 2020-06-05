package com.duckinfotech.stikerdemo.domainModel

import java.io.Serializable


data class StickerModel(
    val identifier: String,
    val idItemsItem: Int,
    val cat_id: Long,
    val stickers_count: Int? = null,
    val publisher_name: String? = null,
    val share_url: String? = null,
    val name: String? = null,
    val preview_images: List<String>? = null,
    val pack_url: String? = null,
    val download_size: Int? = null,
    val pack_json_url: String? = null,
    val is_premium: Boolean = false
) : Serializable
