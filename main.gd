extends Node2D

func _ready() -> void:
	# Only execute intent logic if running natively on Android
	if OS.get_name() == "Android":
		_launch_dialer()
	
	# Terminate the Godot app immediately so it clears from memory
	get_tree().quit()

func _launch_dialer() -> void:
	# Access Android's Intent class
	var intent_class = JavaClassWrapper.wrap("android.content.Intent")
	var action_dial = intent_class.ACTION_DIAL
	
	# Create new Intent(ACTION_DIAL)
	var intent = intent_class.new(action_dial)
	
	# Fetch the active Godot Android Activity and launch the dialer
	var godot_activity = Engine.get_singleton("AndroidRuntime")
	var current_activity = godot_activity.get_activity()
	current_activity.startActivity(intent)
