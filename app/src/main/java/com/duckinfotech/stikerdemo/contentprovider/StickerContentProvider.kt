package com.duckinfotech.stikerdemo.contentprovider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.text.TextUtils
import android.util.Log
import com.drew.imaging.webp.WebpMetadataReader
import com.duckinfotech.stikerdemo.BuildConfig
import com.duckinfotech.stikerdemo.database.AppDataBaseNew
import com.duckinfotech.stikerdemo.database.StickerEntity
import com.duckinfotech.stikerdemo.database.StickerPackEntity
import java.io.File

class StickerContentProvider : ContentProvider() {


    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var appDataBaseNew: AppDataBaseNew

    override fun onCreate(): Boolean {

        appDataBaseNew = AppDataBaseNew.init(context!!.applicationContext)

        val authority = BuildConfig.CONTENT_PROVIDER_AUTHORITY

        //the call to get the metadata for the sticker packs.
        uriMatcher.addURI(authority, METADATA, METADATA_CODE)

        //the call to get the metadata for single sticker pack. * represent the identifier
        uriMatcher.addURI(authority, "$METADATA/*", METADATA_CODE_FOR_SINGLE_PACK)

        //gets the list of stickers for a sticker pack, * respresent the identifier.
        uriMatcher.addURI(authority, "$STICKERS/*", STICKERS_CODE)

        uriMatcher.addURI(authority, "$STICKERS_ASSET/*/*", STICKERS_ASSET_CODE)
        uriMatcher.addURI(authority, "$STICKERS_ASSET/*/*", STICKER_PACK_TRAY_ICON_CODE)

        return true
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            METADATA_CODE -> "vnd.android.cursor.dir/vnd.${BuildConfig.CONTENT_PROVIDER_AUTHORITY}.$METADATA"
            METADATA_CODE_FOR_SINGLE_PACK -> "vnd.android.cursor.item/vnd.${BuildConfig.CONTENT_PROVIDER_AUTHORITY}.$METADATA"
            STICKERS_CODE -> "vnd.android.cursor.dir/vnd.${BuildConfig.CONTENT_PROVIDER_AUTHORITY}.$STICKERS"
            STICKERS_ASSET_CODE -> "image/webp"
            STICKER_PACK_TRAY_ICON_CODE -> "image/webp"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }


    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @SuppressLint("LongLogTag")
    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        when (uriMatcher.match(uri)) {
            METADATA_CODE -> {
                Log.d("${TAG} METADATA_CODE", uri.toString())
            }
            METADATA_CODE_FOR_SINGLE_PACK -> {
                Log.d("${TAG} METADATA_CODE_FOR_SINGLE_PACK", uri.toString())
                return getCursorForSingleStickerPack(uri)
            }
            STICKERS_CODE -> {
                Log.d("${TAG} STICKERS_CODE", uri.toString())
                return getStickersForAStickerPack(uri)
            }
            else -> {
                throw java.lang.IllegalArgumentException("Unknown Uri : $uri")
            }
        }
        return null
    }

    private fun getCursorForSingleStickerPack(uri: Uri): Cursor? {
        val identifier = uri.lastPathSegment
        val stickerPack =
            appDataBaseNew.stickerPackDao().getStickerPackByIdentifierOneShot(identifier!!)
        return getStickerPackInfo(uri, stickerPack)
    }

    private fun getStickerPackInfo(uri: Uri, stickerPackList: List<StickerPackEntity>): Cursor {
        val matrixCursor = MatrixCursor(
            arrayOf(
                STICKER_PACK_IDENTIFIER_IN_QUERY,
                STICKER_PACK_NAME_IN_QUERY,
                STICKER_PACK_PUBLISHER_IN_QUERY,
                STICKER_PACK_ICON_IN_QUERY,
                //ANDROID_APP_DOWNLOAD_LINK_IN_QUERY,
                //IOS_APP_DOWNLOAD_LINK_IN_QUERY,
                PUBLISHER_EMAIL,
                //PUBLISHER_WEBSITE,
                //PRIVACY_POLICY_WEBSITE,
                //LICENSE_AGREENMENT_WEBSITE,
                IMAGE_DATA_VERSION,
                AVOID_CACHE
            )
        )

        stickerPackList.forEach { stickerPack ->
            val builder: MatrixCursor.RowBuilder = matrixCursor.newRow()
            /*   builder.add(stickerPack.identifier)
               builder.add(stickerPack.name)
               builder.add(stickerPack.publisher)
               builder.add(stickerPack.trayImageFile)
               builder.add("Play store link")
               builder.add("Ios app store link")
               builder.add(stickerPack.publisherEmail)
               builder.add(stickerPack.publisherWebsite)
               builder.add(stickerPack.privacyPolicyWebsite)
               builder.add(stickerPack.licenseAgreementWebsite)
               builder.add(stickerPack.imageDataVersion)
               builder.add(stickerPack.avoidCache)*/
            builder.add(stickerPack.identifier)     // Identifier
            builder.add(stickerPack.name)           // Name
            builder.add(stickerPack.publisher)      // Publisher
            builder.add("tray.webp")           // Icon
            //builder.add("https://www.google.com")  // Android playstore download link
            //builder.add("https://www.google.com")  // Ios link
            builder.add("xyz@xyz.com")              // Publisher Email
            //builder.add("https://www.google.com")  // Publisher website
            //builder.add("https://www.google.com")  // Privacy
            //builder.add("https://www.google.com")  // License
            builder.add("1")                        // Image Data Version
            builder.add(false)                      // Allow Caching
        }

        matrixCursor.setNotificationUri(context!!.contentResolver, uri)
        return matrixCursor
    }


    @SuppressLint("LongLogTag")
    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        //Log.d("$TAG openAssetFile", uri.toString())
        return when (uriMatcher.match(uri)) {
            STICKERS_ASSET_CODE -> getImageAsset(uri)
            STICKER_PACK_TRAY_ICON_CODE -> getImageAsset(uri)
            else -> null
        }
    }

    private fun getImageAsset(uri: Uri): AssetFileDescriptor? {
        Log.d(TAG, uri.toString())
        val pathSegments = uri.pathSegments
        if (pathSegments.size != 3) {
            throw java.lang.IllegalArgumentException("path segments should be 3, uri is: $uri")
        }
        val fileName = pathSegments[pathSegments.size - 1]
            ?: throw IllegalArgumentException("file name is empty, uri $uri")
        val identifier = pathSegments[pathSegments.size - 2]
            ?: throw  IllegalArgumentException("identifier is empty, uri:$uri")

        val stickerPackList = getStickerPackByIdentifier(identifier)
        val stickerList = getStickerByIdentifier(identifier)
        val trySticker = stickerList.find {
            it.imageFileName.endsWith("tray.webp")
        }

        stickerPackList.forEach { stickerPackEntity ->
            if (identifier == stickerPackEntity.identifier) {
                if (fileName == "tray.webp") {
                    return fetchFile(uri, fileName, identifier, trySticker)
                } else {
                    stickerList.forEach { sticker ->
                        if (fileName == sticker.imageFileName.split("/").last()) {
                            return fetchFile(uri, fileName, identifier, sticker)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun getStickerByIdentifier(identifier: String): List<StickerEntity> {
        return appDataBaseNew.stickerDao().getStickerByIdentifierOneShot(identifier)
    }


    private fun getStickerPackByIdentifier(identifier: String): List<StickerPackEntity> {
        return appDataBaseNew.stickerPackDao().getStickerPackByIdentifierOneShot(identifier)
    }

    private fun getStickerByPackIdentifier(identifier: String): List<StickerEntity> {
        return appDataBaseNew.stickerDao().getStickerByIdentifierOneShot(identifier)
    }

    private fun fetchFile(
        uri: Uri,
        fileName: String,
        identifier: String,
        stickerEntity: StickerEntity?
    ): AssetFileDescriptor? {


        // Log.d("${TAG} fetch file ", uri.toString())
        if (fileName == "tray.webp") {
            // Log.d(TAG, stickerEntity!!.imageFileName)
            val file = File(
                context!!.getExternalFilesDir(null),
                "${stickerEntity!!.categoryId}/${stickerEntity.packIdentifier}/${uri.lastPathSegment}"
            )
            //Log.d(TAG, file.path)
            //imageMetaDataDetails(file)
            return AssetFileDescriptor(
                ParcelFileDescriptor.open(
                    file,
                    ParcelFileDescriptor.MODE_READ_ONLY
                ), 0, AssetFileDescriptor.UNKNOWN_LENGTH
            )

        }

        if (fileName.endsWith(".webp")) {
            val file = File(
                context!!.getExternalFilesDir(null),
                "${stickerEntity!!.categoryId}/${stickerEntity.packIdentifier}/${uri.lastPathSegment}"
            )
            // Log.d(TAG, file.path)
            imageMetaDataDetails(file)
            return AssetFileDescriptor(
                ParcelFileDescriptor.open(
                    file,
                    ParcelFileDescriptor.MODE_READ_ONLY
                ), 0, AssetFileDescriptor.UNKNOWN_LENGTH
            )

        }

        return null
    }

    private fun imageMetaDataDetails(file: File) {
        val imageMetaData = WebpMetadataReader.readMetadata(file)
        Log.d("MetaData", "-----------------------------")
        Log.d("MetaData", imageMetaData.toString())
        imageMetaData.directories.forEach {
            Log.d("Metadata Directory", it.name)
            it.tags.forEach { tag ->
                Log.d("Metadata Directory TAG", tag.description)
            }
        }

    }

    private fun getStickersForAStickerPack(uri: Uri): Cursor {
        val identifier = uri.lastPathSegment
        val stickerList = getStickerByPackIdentifier(identifier!!)
        val matrixCursor = MatrixCursor(
            arrayOf(
                STICKER_FILE_NAME_IN_QUERY,
                STICKER_FILE_EMOJI_IN_QUERY
            )
        )
        stickerList.filter {
            it.packIdentifier == identifier && it.imageFileName.endsWith(".webp") && it.imageFileName.split(
                "/"
            ).last() != "tray.webp"
        }.also {
            it.forEach { sticker ->
                // Log.d(TAG, sticker.toString())
//                Log.d(TAG, sticker.imageFileName.split("/").last().hashCode().toString())
                matrixCursor.addRow(
                    arrayOf(
                        sticker.imageFileName.split("/").last(),
                        TextUtils.join(",", listOf("☕", "🙂"))
                    )
                )
            }
        }
        matrixCursor.setNotificationUri(context!!.contentResolver, uri)
        val nameColIndex = matrixCursor.getColumnIndex(STICKER_FILE_NAME_IN_QUERY)
        val emojiColIndex = matrixCursor.getColumnIndex(STICKER_FILE_EMOJI_IN_QUERY)
        while (matrixCursor.moveToNext()) {
            Log.d(
                TAG,
                "File Name=${matrixCursor.getString(nameColIndex)}, Emoji=${matrixCursor.getString(
                    emojiColIndex
                )}"
            )
        }
        return matrixCursor
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "StickerCP-"
    }
}