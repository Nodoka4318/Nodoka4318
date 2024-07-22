package net.kankantari.ojima.ojimizing.easings

import com.google.gson.GsonBuilder

class Easing(val name: String, val expression: String) {
    companion object {
        private val defaultEasings = listOf(
            Easing("EaseInSine", "1 - Math.cos((x * Math.PI) / 2)"),
            Easing("EaseOutSine", "Math.sin((x * Math.PI) / 2)"),
            Easing("EaseInOutSine", "-(Math.cos(Math.PI * x) - 1) / 2"),
            Easing("EaseInQuad", "x * x"),
            Easing("EaseOutQuad", "1 - (1 - x) * (1 - x)"),
            Easing("EaseInOutQuad", "x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2"),
            Easing("EaseInCubic", "x * x * x"),
            Easing("EaseOutCubic", "1 - Math.pow(1 - x, 3)"),
            Easing("EaseInOutCubic", "x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2"),
            Easing("EaseInQuart", "x * x * x * x"),
            Easing("EaseOutQuart", "1 - Math.pow(1 - x, 4)"),
            Easing("EaseInOutQuart", "x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2"),
            Easing("EaseInQuint", "x * x * x * x * x"),
            Easing("EaseOutQuint", "1 - Math.pow(1 - x, 5)"),
            Easing("EaseInOutQuint", "x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2"),
            Easing("EaseInExpo", "x === 0 ? 0 : Math.pow(2, 10 * x - 10)"),
            Easing("EaseOutExpo", "x === 1 ? 1 : 1 - Math.pow(2, -10 * x)"),
            Easing("EaseInOutExpo", "x === 0 ? 0 : x === 1 ? 1 : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2 : (2 - Math.pow(2, -20 * x + 10)) / 2"),
            Easing("EaseInCirc", "1 - Math.sqrt(1 - Math.pow(x, 2))"),
            Easing("EaseOutCirc", "Math.sqrt(1 - Math.pow(x - 1, 2))"),
            Easing("EaseInOutCirc", "x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2"),
            Easing("EaseInBack", "2.70158 * x * x * x - 1.70158 * x * x"),
            Easing("EaseOutBack", "1 + 2.70158 * Math.pow(x - 1, 3) + 1.70158 * Math.pow(x - 1, 2)"),
            Easing("EaseInOutBack", "x < 0.5 ? (Math.pow(2 * x, 2) * ((1.70158 * 1.525 + 1) * 2 * x - 1.70158 * 1.525)) / 2 : (Math.pow(2 * x - 2, 2) * ((1.70158 * 1.525 + 1) * (x * 2 - 2) + 1.70158  * 1.525) + 2) / 2"),
            Easing("EaseInElastic", "x === 0 ? 0 : x === 1 ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * (2 * Math.PI) / 3)"),
            Easing("EaseOutElastic", "x === 0 ? 0 : x === 1 ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * (2 * Math.PI) / 3) + 1"),
            Easing("EaseInOutElastic", "x === 0 ? 0 : x === 1 ? 1 : x < 0.5 ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * (2 * Math.PI) / 4.5)) / 2 : (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * (2 * Math.PI) / 4.5)) / 2 + 1"),
            Easing("EaseInBounce", "1 - (x = 1 - x) < 1 / 2.75 ? 7.5625 * x * x : x < 2 / 2.75 ? 7.5625 * (x -= 1.5 / 2.75) * x + 0.75 : x < 2.5 / 2.75 ? 7.5625 * (x -= 2.25 / 2.75) * x + 0.9375 : 7.5625 * (x -= 2.625 / 2.75) * x + 0.984375"),
            Easing("EaseOutBounce", "x < 1 / 2.75 ? 7.5625 * x * x : x < 2 / 2.75 ? 7.5625 * (x -= 1.5 / 2.75) * x + 0.75 : x < 2.5 / 2.75 ? 7.5625 * (x -= 2.25 / 2.75) * x + 0.9375 : 7.5625 * (x -= 2.625 / 2.75) * x + 0.984375"),
            Easing("EaseInOutBounce", "x < 0.5 ? (1 - (x = 1 - 2 * x) < 1 / 2.75 ? 7.5625 * x * x : x < 2 / 2.75 ? 7.5625 * (x -= 1.5 / 2.75) * x + 0.75 : x < 2.5 / 2.75 ? 7.5625 * (x -= 2.25 / 2.75) * x + 0.9375 : 7.5625 * (x -= 2.625 / 2.75) * x + 0.984375) / 2 : (1 + (x = 2 * x - 1) < 1 / 2.75 ? 7.5625 * x * x : x < 2 / 2.75 ? 7.5625 * (x -= 1.5 / 2.75) * x + 0.75 : x < 2.5 / 2.75 ? 7.5625 * (x -= 2.25 / 2.75) * x + 0.9375 : 7.5625 * (x -= 2.625 / 2.75) * x + 0.984375) / 2")
        )

        fun json(): String {
            return GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(defaultEasings)
        }
    }
}
