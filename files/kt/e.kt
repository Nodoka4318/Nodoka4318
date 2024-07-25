val easedList = easing.ease(frameIndexSet)

val defaultOjimizer = DefaultOjimizer()
defaultOjimizer.initialize(score, bpm, fps, inputVideoFile, easedList)
// setOptionsをする必要はない

return defaultOjimizer.ojimizeIndex()
