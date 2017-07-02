import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import org.w3c.dom.get
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document
import kotlin.browser.window

/**
 * Created by Antonio Yee <yee.antonio@gmail.com> on 01/07/17.
 */

fun main(args: Array<String>) {

	window.onload = {
		fetch("1")

		val head = document.getElementsByTagName("head")
		head[0]?.appendChild(createStylesheetLink("style.css"))

		val input = document.getElementById("count_id") as HTMLInputElement
		val button = document.getElementById("button_id")

		button?.addEventListener("click", fun(event: Event) {
			fetch(input.value)
		})
	}

}

fun fetch(count: String): Unit {
	val url = "http://localhost:8080/api/ping/$count"
	val req = XMLHttpRequest()

	req.onloadend = fun(event: Event) {
		val text = req.responseText
		println(text)

		val objArray = JSON.parse<Array<JSON>>(text)
		val textarea = document.getElementById("textarea_id") as HTMLTextAreaElement

		textarea.value = ""

		objArray.forEach {
			val message = it["message"]
			textarea.value += "$message\n"
		}
	}
}

fun createStylesheetLink(filePath: String): Element {
	val style = document.createElement("link")

	style.setAttribute("rel", "stylesheet")
	style.setAttribute("href", filePath)

	return style
}

external fun alert(message: Any?): Unit