package com.pwn233.digitalgamestore.common

import com.pwn233.digitalgamestore.common.CommonUtils.checkRegexCharacter
import com.pwn233.digitalgamestore.exception.DigitalGameStoreException

object ValidateUtils {

    private const val WHITE_SPACE = "\u0020"
    private const val NUMBER_PATTERN = "0-9"
    private const val ENGLISH_ALPHABET_PATTERN = "a-zA-Z"
    private const val THAI_ALPHABET_PATTERN = "\u0E00-\u0E7F"
    private const val SPECIAL_CHARACTER_PATTERN= "!@#$%&*()'+,-./:;<=>?\\[\\]^_`{|}"

    fun validateIdIsNullOrBlank(id: String) {
        if(id.isNullOrBlank()) {
            val errorMessage = "Product Name must not be empty"
            throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                it.description = errorMessage
                it.message = errorMessage
            })
        }

    }

    fun validateProductNameNullOrBlank(name: String) {
        if(name.isNullOrBlank()) {
            val errorMessage = "Product Name must not be empty"
            throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                it.description = errorMessage
                it.message = errorMessage
            })
        }
    }

    fun validateProductPriceNullOrBlank(price: Double) {
        if(price !is Double) {
            val errorMessage = "Product Price must be a number"
            throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                it.description = errorMessage
                it.message = errorMessage
            })
        }
        if(price < 0) {
            val errorMessage = "Product Price must more than 0.0"
            throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                it.description = errorMessage
                it.message = errorMessage
            })
        }
    }

    fun validateProductDescription(description: String?) {
        if(!description.isNullOrBlank()) {
            if(description.length < 3 || description.length > 255) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "The Length of Product category is invalid"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
        }
    }

    fun validateProductCategory(category: String?) {
        if(!category.isNullOrBlank()) {
            if(!checkRegexCharacter("^[$ENGLISH_ALPHABET_PATTERN$WHITE_SPACE$SPECIAL_CHARACTER_PATTERN]*", category)) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "Product Category must contain English alphabets or Numbers or Special characters"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
            if(category.length < 3 || category.length > 40) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "The Length of Product category is invalid"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
        }
    }

    fun validateProductBrand(brand: String?) {
        if(!brand.isNullOrBlank()) {
            if(!checkRegexCharacter("^[$ENGLISH_ALPHABET_PATTERN$NUMBER_PATTERN$WHITE_SPACE]*", brand)) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "Product Brand must contain English alphabets or Numbers"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
            if(brand.length < 3 || brand.length > 40) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "The Length of Product Brand is invalid"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
        }
    }

    fun validateProductBarcode(brand: String?) {
        if(!brand.isNullOrBlank()) {
            if(!checkRegexCharacter("^[$ENGLISH_ALPHABET_PATTERN$NUMBER_PATTERN]*", brand)) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "Product Barcode must contain English alphabets or Numbers"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
            if(brand.length < 3 || brand.length > 40) {
                throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                    val errorMessage = "The Length of Product Brand is invalid"
                    it.description = errorMessage
                    it.message = errorMessage
                })
            }
        }
    }

    fun validateCreatedByNullOrBlank(createdBy: String) {
        if(createdBy.isNullOrBlank()) {
            val errorMessage = "Created by must not be empty"
            throw DigitalGameStoreException(HttpConstants.REQUIRED_DATA_IS_INVALID.also {
                it.description = errorMessage
                it.message = errorMessage
            })
        }
    }
}