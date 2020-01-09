package com.adriantache.bigfivepersonalitytest.utils

/**
 * Contains common constants used in the app
 **/

const val ERROR = "ERROR"
const val JSON_FILE = "JSON_FILE"
const val ANSWER_SUMMARY = "ANSWER_SUMMARY"

//files
const val IPIP_20 = "mini_ipip.json"
const val IPIP_50 = "ipip50.json"
const val NEO_50 = "neo_pi_r50.json"
const val NEO_100 = "neo_pi_r100.json"
const val DYP_100 = "deyoung_quilty_peterson100.json"
const val JOHN_120 = "johnson120.json"
const val MAPLES_120 = "maples120.json"
const val CM_300 = "costa_mccrae300.json"
val FILES = listOf(IPIP_20, IPIP_50, NEO_50, NEO_100, DYP_100, JOHN_120, MAPLES_120, CM_300)

//database constants
const val ROOM_TABLE_NAME = "questions"
const val JSON_STORED_IN_DB = "json_stored"
const val EXPECTED_DB_ENTRIES = 860
