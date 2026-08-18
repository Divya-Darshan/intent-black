extends Node2D

func _ready() -> void:
	# Launch default dialer
	OS.shell_open("tel:")
	
	# Wait 100ms for Android to process intent before quitting
	await get_tree().create_timer(0.1).timeout
	
	# Close app immediately
	get_tree().quit()
