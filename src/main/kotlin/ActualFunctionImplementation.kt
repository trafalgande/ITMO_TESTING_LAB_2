class ActualFunctionImplementation {
    companion object : Functions() {
        override fun sin(x: Double, eps: Double): Double = kotlin.math.sin(x)

        override fun cos(x: Double, eps: Double): Double = kotlin.math.cos(x)

        override fun sec(x: Double, eps: Double): Double = 1 / kotlin.math.cos(x)

        override fun csc(x: Double, eps: Double): Double = 1 / kotlin.math.sin(x)

        override fun ln(x: Double, eps: Double): Double = kotlin.math.ln(x)

        override fun log(x: Double, base: Double, eps: Double): Double = kotlin.math.ln(x) / kotlin.math.ln(base)

        override fun log_2(x: Double, eps: Double): Double = kotlin.math.ln(x) / kotlin.math.ln(2.0)

        override fun log_3(x: Double, eps: Double): Double = kotlin.math.ln(x) / kotlin.math.ln(3.0)

        override fun log_5(x: Double, eps: Double): Double = kotlin.math.ln(x) / kotlin.math.ln(5.0)

        override fun log_10(x: Double, eps: Double): Double = kotlin.math.ln(x) / kotlin.math.ln(10.0)
    }

}