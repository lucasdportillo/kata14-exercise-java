from sys import stdin

for line in stdin:
	line = line.strip()
	words = line.split()

	word_index = 0
	while word_index < len(words) - 2:
		key = "{first} {second}".format(first = words[word_index], second = words[word_index + 1])
		value = words[word_index + 2]
		print "{key}\t{value}".format(key = key, value = value)
		
		word_index += 1
