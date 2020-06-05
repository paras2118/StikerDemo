package com.duckinfotech.stikerdemo.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duckinfotech.stikerdemo.domainModel.StickerPack
import com.google.gson.annotations.SerializedName

@Entity
data class StickerPackEntity(
    @NonNull
    @PrimaryKey
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
    val previewImages: String,
    @SerializedName("pack_url")
    val packUrl: String?,
    @SerializedName("download_size")
    val downloadSize: Long?,
    @SerializedName("is_premium")
    val isPremium: Boolean
) {
}

fun StickerPackEntity.asStickerPack(): StickerPack {
    return StickerPack(
        identifier = this.identifier,
        categoryId = this.categoryId,
        name = this.name,
        publisher = this.publisher,
        trayImageFile = this.trayImageFile,
        publisherEmail = this.publisherEmail,
        publisherWebsite = this.publisherWebsite,
        privacyPolicyWebsite = this.privacyPolicyWebsite,
        licenseAgreementWebsite = this.licenseAgreementWebsite,
        imageDataVersion = this.imageDataVersion,
        avoidCache = this.avoidCache,
        stickersCount = this.stickersCount,
        previewImages = this.previewImages!!.split(","),
        packUrl = this.packUrl,
        downloadSize = this.downloadSize,
        isPremium = this.isPremium,
        stickerList = emptyList()
    )
}

fun List<StickerPackEntity>.asStickerPackList(): List<StickerPack> {
    return map {
        StickerPack(
            identifier = it.identifier,
            categoryId = it.categoryId,
            name = it.name,
            publisher = it.publisher,
            trayImageFile = it.trayImageFile,
            publisherEmail = it.publisherEmail,
            publisherWebsite = it.publisherWebsite,
            privacyPolicyWebsite = it.privacyPolicyWebsite,
            licenseAgreementWebsite = it.licenseAgreementWebsite,
            imageDataVersion = it.imageDataVersion,
            avoidCache = it.avoidCache,
            stickersCount = it.stickersCount,
            previewImages = it.previewImages!!.split(","),
            packUrl = it.packUrl,
            downloadSize = it.downloadSize,
            isPremium = it.isPremium,
            stickerList = emptyList()
        )
    }
}
