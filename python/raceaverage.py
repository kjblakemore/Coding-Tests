class RaceAverage:
	"""A class to calculate average competitor race time"""
	def avgMinutes(self, times):
		num = len(times)												# the number of competitors

		splittimes = [x.split(' ') for x in times]						# split strings into ['hh:mm', 'xM,', 'n']
		
		hoursmins = [map(int, x[0].split(':')) for x in splittimes]		# split hh:mm into [hours, minutes]

		# Calculate the offset hours for PM.  Special case for 12:00 PM, no offset; and 12:00 AM has offset.
		offsets = [720 if ((x[1] == 'PM,' and x[0] != '12:00') or (x[1] == 'AM,' and x[0] == '12:00')) else 0 for x in splittimes]		
		
		days = [int(x[3]) for x in splittimes]

		# Compute the average race time, using a start time of day 1, 8:00 AM
		racetimes = [hoursmins[i][0]*12 + hoursmins[i][1] + offsets[i] + (days[i]-1)*1440 - 480 for i in range(num)]
		avgracetime = sum(racetimes)/num
		return avgracetime

x = RaceAverage()

print x.avgMinutes(["12:00 PM, DAY 1","12:01 PM, DAY 1"])