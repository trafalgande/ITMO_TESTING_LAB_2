class Utility {
    companion object {
        fun f(
            x: Double,
            map: HashMap<String, () -> Double>,
        ): Double {
            val sin = map["sin"]!!.invoke()
            val csc = map["csc"]!!.invoke()
            val sec = map["sec"]!!.invoke()
            val ln = map["ln"]!!.invoke()
            val log_2 = map["log_2"]!!.invoke()
            val log_3 = map["log_3"]!!.invoke()
            val log_5 = map["log_5"]!!.invoke()
            val log_10 = map["log_10"]!!.invoke()

            return if (x <= 0)
                ((((sin * csc) - sin) / sin) * (sec / sin))
            else
                (((((log_5 * log_2) + log_10) / log_3) - (log_2 + ln)) + log_5)
        }

        fun HashMap<String, () -> Double>.replaceWith(key: String, value: () -> Double): HashMap<String, () -> Double> {
            this[key] = value
            return this
        }
    }

}