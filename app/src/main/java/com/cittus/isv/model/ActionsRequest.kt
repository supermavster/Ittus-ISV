package com.cittus.isv.model

class ActionsRequest {


    companion object {

        val REQUEST_IMAGE_CAPTURE = 1

        val PERMISSION_REQUEST_CODE: Int = 2


        val TAKE_PHOTO_REQUEST:Int = 3
        val TAKE_PHOTO_REQUEST_FRONT = 30
        val TAKE_PHOTO_REQUEST_BACK = 31
        val TAKE_PHOTO_REQUEST_PLAQUE = 32

        val GET_HORIZONTAL_VALUES = 4
        val GET_HORIZONTAL_IMAGES_VALUES = 41



        val GET_IMAGES:Int = 5

        val GET_INIT = 6

        val GET_VERTICAL_VALUES = 7
        val GET_VERTICAL_IMAGES_VALUES = 71
        val GET_VERTICAL_INFORMATIVE_IMAGES_VALUES = 72


        var PICK_FILE_REQUEST: Int = 8;
    }
}