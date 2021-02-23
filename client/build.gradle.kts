plugins {
	idea
}

idea {
	module {
		name = "burger-builder-client"
		excludeDirs = excludeDirs + file("react-js")
	}
}
