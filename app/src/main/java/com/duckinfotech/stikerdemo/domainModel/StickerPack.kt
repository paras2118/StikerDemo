package com.duckinfotech.stikerdemo.domainModel

import com.google.gson.annotations.SerializedName

data class StickerPack(
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("cat_id")
    val categoryId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("publisher_name")
    val publisher: String,
    @SerializedName("trayImageFile")
    val trayImageFile: String?,
    @SerializedName("publisherEmail")
    val publisherEmail: String?,
    @SerializedName("publisherWebsite")
    val publisherWebsite: String?,
    @SerializedName("privacyPolicyWebsite")
    val privacyPolicyWebsite: String?,
    @SerializedName("licenseAgreementWebsite")
    val licenseAgreementWebsite: String?,
    @SerializedName("imageDataVersion")
    val imageDataVersion: String?,
    @SerializedName("avoidCache")
    val avoidCache: Boolean?,
    @SerializedName("stickers_count")
    val stickersCount: Int?,
    @SerializedName("preview_images")
    val previewImages: List<String?>,
    @SerializedName("pack_url")
    val packUrl: String?,
    @SerializedName("download_size")
    val downloadSize: Long?,
    @SerializedName("is_premium")
    val isPremium: Boolean,
    @SerializedName("stickerList")
    val stickerList: List<Sticker>?
)