package com.duckinfotech.stikerdemo.database

import androidx.room.Embedded
import androidx.room.Relation
import com.duckinfotech.stikerdemo.domainModel.StickerPack

data class StickerPackAndSticker(
    @Embedded val stickerPackEntity: StickerPackEntity,
    @Relation(parentColumn = "identifier", entityColumn = "packIdentifier")
    val sticker: List<StickerEntity>?
)

fun StickerPackAndSticker.asStickerPack(): StickerPack {
    return StickerPack(
        identifier = this.stickerPackEntity.identifier,
        categoryId = this.stickerPackEntity.categoryId,
        name = this.stickerPackEntity.name,
        publisher = this.stickerPackEntity.publisher,
        trayImageFile = this.stickerPackEntity.trayImageFile,
        publisherEmail = this.stickerPackEntity.publisherEmail,
        publisherWebsite = this.stickerPackEntity.publisherWebsite,
        privacyPolicyWebsite = this.stickerPackEntity.privacyPolicyWebsite,
        licenseAgreementWebsite = this.stickerPackEntity.licenseAgreementWebsite,
        imageDataVersion = this.stickerPackEntity.imageDataVersion,
        avoidCache = this.stickerPackEntity.avoidCache,
        stickersCount = this.stickerPackEntity.stickersCount,
        previewImages = this.stickerPackEntity.previewImages!!.split(","),
        packUrl = this.stickerPackEntity.packUrl,
        downloadSize = this.stickerPackEntity.downloadSize,
        isPremium = this.stickerPackEntity.isPremium,
        stickerList = this.sticker?.asStickerList()
    )
}

fun List<StickerPackAndSticker>.asStickerPackList(): List<StickerPack> {
    return map {
        StickerPack(
            identifier = it.stickerPackEntity.identifier,
            categoryId = it.stickerPackEntity.categoryId,
            name = it.stickerPackEntity.name,
            publisher = it.stickerPackEntity.publisher,
            trayImageFile = it.stickerPackEntity.trayImageFile,
            publisherEmail = it.stickerPackEntity.publisherEmail,
            publisherWebsite = it.stickerPackEntity.publisherWebsite,
            privacyPolicyWebsite = it.stickerPackEntity.privacyPolicyWebsite,
            licenseAgreementWebsite = it.stickerPackEntity.licenseAgreementWebsite,
            imageDataVersion = it.stickerPackEntity.imageDataVersion,
            avoidCache = it.stickerPackEntity.avoidCache,
            stickersCount = it.stickerPackEntity.stickersCount,
            previewImages = it.stickerPackEntity.previewImages!!.split(","),
            packUrl = it.stickerPackEntity.packUrl,
            downloadSize = it.stickerPackEntity.downloadSize,
            isPremium = it.stickerPackEntity.isPremium,
            stickerList = it.sticker?.asStickerList()
        )
    }
}