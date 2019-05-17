package com.realdolmen.bestellingapp

class User {
    var userid: String? = null
    var username: String? = null

    var useremail: String? = null

    var usermobileno: String? = null

    constructor(userid: String, username: String, useremail: String, usermobileno: String) {
        this.userid = userid
        this.username = username
        this.useremail = useremail
        this.usermobileno = usermobileno
    }
}