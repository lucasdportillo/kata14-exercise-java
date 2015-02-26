from sys import stdin

current_key = None
current_value = None

for line in stdin:
	line = line.strip()
	key, value = line.split('\t')

	if current_key == key:
		# increment
		current_value += ", {value}".format(value = value)
	else:
		if current_key:
			# print values
			print "{key}\t{value}".format(key = current_key, value = current_value)
		current_key = key
		current_value = value

if current_key == key:
	print "{key}\t{value}".format(key = current_key, value = current_value)
