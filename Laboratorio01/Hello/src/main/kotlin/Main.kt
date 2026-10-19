package org.emanueldossantos

fun getCommand(cmd: String) : String {
    return cmd.split(" ").get(0)
}

fun main() {
    var cmd: String
    do
    {
        cmd = readln()
        println(getCommand(cmd))
    } while(cmd != "exit")
}